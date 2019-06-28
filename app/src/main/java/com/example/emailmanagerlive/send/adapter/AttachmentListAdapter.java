package com.example.emailmanagerlive.send.adapter;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.send.SendEmailViewModel;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;
import com.google.android.material.snackbar.Snackbar;
import com.sun.mail.imap.protocol.ID;

import java.io.File;
import java.util.List;

public class AttachmentListAdapter extends BaseAdapter<Attachment, BaseViewHolder> implements
        EmailDataSource.DownloadCallback {

    private static final int PROGRESS = 1;
    private static final int FINISH = 2;
    private static final int ERROR = 3;
    private final long id;
    private final Account mAccount;
    private LifecycleOwner mLifecycleOwner;
    private ViewDataBinding dataBinding;
    private SendEmailViewModel mViewModel;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == PROGRESS) {
                notifyItemChanged(msg.arg1);
            } else if (what == FINISH) {
                notifyItemChanged(msg.arg1);
            } else {
                notifyItemChanged(msg.arg1);
            }
        }
    };

    public AttachmentListAdapter(Context context, LifecycleOwner lifecycleOwner, long id, Account account) {
        super(context);
        this.mLifecycleOwner = lifecycleOwner;
        this.id = id;
        this.mAccount = account;
    }

    public void setViewModel(SendEmailViewModel viewModel) {
        this.mViewModel = viewModel;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_add_attachment,
                parent, false);
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
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/EmailManager", mData.get(position).getFileName());
        if (!file.exists()) {
            realDownload(mData.get(position), position);
        }
    }

    public void deleteOrDownload(Attachment item, int position) {
        if (item.isDownload()) {
            mViewModel.delete(item);
        } else {
            realDownload(item, position);
        }

    }

//    public void download() {
//        Log.i("mango","download");
//        for (int i = 0; i < mData.size(); i++) {
//            final Attachment attachment = mData.get(i);
//            final File file = new File(attachment.getPath());
//            if (!file.exists()) {
//                realDownload(attachment, i);
//            }
//        }
//    }

    @Override
    public void onProgress(int index, float percent) {
        Attachment attachment = mData.get(index);
        String size = attachment.getSize();
        String substring = size.substring(size.indexOf("/") == -1 ? 0 : size.indexOf("/") + 1);
        Log.i("mango", "substring:" + substring);
        attachment.setSize((int) (percent * 100) + "%/" + substring);
        Message message = Message.obtain();
        message.what = PROGRESS;
        message.arg1 = index;
        mHandler.sendMessage(message);
    }

    @Override
    public void onFinish(int index) {
        String size = mData.get(index).getSize();
        mData.get(index).setDownload(true);
        mData.get(index).setEnable(true);
        mData.get(index).setSize(size.substring(size.indexOf("/") + 1));
        Message message = Message.obtain();
        message.what = FINISH;
        message.arg1 = index;
        mHandler.sendMessage(message);
    }

    @Override
    public void onError(int index) {
        mData.get(index).setEnable(true);
        Snackbar.make(dataBinding.getRoot(), mData.get(index).getFileName() + "下载失败",
                Snackbar.LENGTH_LONG).show();
        Message message = Message.obtain();
        message.what = ERROR;
        message.arg1 = index;
        mHandler.sendMessage(message);
    }

    private void realDownload(final Attachment item, final int index) {
        new Thread() {
            @Override
            public void run() {
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/EmailManager", item.getFileName());
                EmailRepository.provideRepository().download(mAccount, file, id, index,
                        item.getTotal(), AttachmentListAdapter.this);
            }
        }.start();
    }
}
