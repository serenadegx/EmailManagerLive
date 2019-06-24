package com.example.emailmanagerlive.emaildetail.adapter;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;
import com.example.multifile.XRMultiFile;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;

public class AttachmentListAdapter extends BaseAdapter<Attachment, BaseViewHolder> implements EmailDataSource.DownloadCallback {
    public static final int REQUEST_PERMISSIONS = 1;
    private static final int NOTIFY = 2;
    private long id;
    private LifecycleOwner mLifecycleOwner;
    private EmailRepository mRepository;
    private int position = -1;
    private ProgressDialog progressDialog;
    private ViewDataBinding dataBinding;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            notifyItemChanged(position);
        }
    };

    public AttachmentListAdapter(Context context, long id, LifecycleOwner lifecycleOwner) {
        super(context);
        this.id = id;
        this.mLifecycleOwner = lifecycleOwner;
        mRepository = EmailRepository.provideRepository();
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_attachment, parent, false);
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ViewDataBinding binding = baseViewHolder.getBinding();
        binding.setLifecycleOwner(mLifecycleOwner);
        binding.setVariable(BR.item, mData.get(position));
        binding.setVariable(BR.adapter, this);
        binding.setVariable(BR.position, position);
        binding.executePendingBindings(); //防止闪烁

    }

    @Override
    public void onProgress(float percent) {
        if (progressDialog != null)
            progressDialog.setProgress((int) (percent * 100));
    }

    @Override
    public void onFinish() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        if (position != -1) {
            mData.get(position).setDownload(true);
            mHandler.sendEmptyMessage(NOTIFY);
        }
    }

    @Override
    public void onError() {
        if (progressDialog != null) {
            progressDialog.cancel();
        }
        Snackbar.make(dataBinding.getRoot(), "下载失败", Snackbar.LENGTH_SHORT).show();
    }

    public void downloadOrOpen(Attachment item, int position) {
        this.position = position;
        //判断是否有存储权限(6.0适配)
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (!item.isDownload()) {
                realDownloadOrOpen();
            } else {
//                Toast.makeText(mContext, "/EmailManager/" + item.getFileName(), Toast.LENGTH_LONG).show();
                XRMultiFile.get().with(mContext).custom(new File(Environment.getExternalStorageDirectory(), "/EmailManager")).browse();
            }
        } else {
            ActivityCompat.requestPermissions((Activity) mContext, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSIONS);
        }
    }

    public void realDownloadOrOpen() {
        File dir = new File(Environment.getExternalStorageDirectory(), "EmailManager");
        if (!dir.exists()) {
            dir.mkdir();
        }
        final File file = new File(dir, mData.get(position).getFileName());
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("下载中...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new Thread() {
            @Override
            public void run() {
                mRepository.download(EmailApplication.getAccount(), file, id, position, mData.get(position).getTotal(), AttachmentListAdapter.this);
            }
        }.start();
    }
}
