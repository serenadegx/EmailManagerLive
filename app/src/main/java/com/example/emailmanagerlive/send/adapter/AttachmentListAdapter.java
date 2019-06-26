package com.example.emailmanagerlive.send.adapter;

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

public class AttachmentListAdapter extends BaseAdapter<Attachment, BaseViewHolder> {
    private LifecycleOwner mLifecycleOwner;
    private ViewDataBinding dataBinding;

    public AttachmentListAdapter(Context context, LifecycleOwner lifecycleOwner) {
        super(context);
        this.mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_add_attachment, parent, false);
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

    public void delete(Attachment item, int position) {

    }
}
