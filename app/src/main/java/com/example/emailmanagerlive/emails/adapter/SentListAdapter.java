package com.example.emailmanagerlive.emails.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.EmailParams;
import com.example.emailmanagerlive.emaildetail.EmailDetailActivity;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;

public class SentListAdapter extends BaseAdapter<Email, BaseViewHolder> {
    private LifecycleOwner mLifecycleOwner;
    public SentListAdapter(Context context, LifecycleOwner activity) {
        super(context);
        mLifecycleOwner = activity;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_sent, parent, false);
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

    public void goNext(Email item, int position) {
        EmailParams params = new EmailParams();
        params.setType(EmailParams.Type.SENT);
        params.setId(item.getId());
//        EmailDetailActivity.start2EmailDetailActivity(mContext, item.getId(),EmailDetailActivity.SENT);
        EmailDetailActivity.start2EmailDetailActivity(mContext, params);
    }
}
