package com.example.emailmanagerlive.send.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.send.SendEmailViewModel;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;

import java.util.List;

public class AttachmentListAdapter extends BaseAdapter<Attachment, BaseViewHolder> {
    private LifecycleOwner mLifecycleOwner;
    private ViewDataBinding dataBinding;
    private SendEmailViewModel mViewModel;

    public AttachmentListAdapter(Context context, LifecycleOwner lifecycleOwner) {
        super(context);
        this.mLifecycleOwner = lifecycleOwner;
    }

    public void setViewModel(SendEmailViewModel viewModel) {
        this.mViewModel = viewModel;
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
        mViewModel.delete(item);
    }
}
