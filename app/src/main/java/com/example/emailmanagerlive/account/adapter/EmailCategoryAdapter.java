package com.example.emailmanagerlive.account.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.account.VerifyActivity;
import com.example.emailmanagerlive.data.Configuration;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;

public class EmailCategoryAdapter extends BaseAdapter<Configuration, BaseViewHolder> {

    public EmailCategoryAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding inflate = DataBindingUtil.inflate(inflater, R.layout.item_email_catogory, parent, false);
        return new BaseViewHolder(inflate);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ViewDataBinding binding = baseViewHolder.getBinding();
        binding.setVariable(BR.item, mData.get(position));
        binding.setVariable(BR.adapter, this);
        binding.setVariable(BR.position, position);
        binding.executePendingBindings(); //防止闪烁
    }

    public void goNext(Configuration item, int position) {
        VerifyActivity.start2VerifyActivity(mContext, item.getCategoryId());
    }
}

