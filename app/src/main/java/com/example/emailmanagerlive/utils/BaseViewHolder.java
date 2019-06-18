package com.example.emailmanagerlive.utils;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
    private B mBinding;

    public BaseViewHolder(B mBinding) {
        super(mBinding.getRoot());
        this.mBinding = mBinding;
    }

    /**
     * @return viewDataBinding
     */
    public B getBinding() {
        return mBinding;
    }
}
