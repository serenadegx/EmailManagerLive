package com.example.emailmanagerlive.settings.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;

import com.example.emailmanagerlive.BR;
import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.account.EmailCategoryActivity;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.source.AccountRepository;
import com.example.emailmanagerlive.databinding.ItemAccountBinding;
import com.example.emailmanagerlive.utils.BaseAdapter;
import com.example.emailmanagerlive.utils.BaseViewHolder;

public class AccountListAdapter extends BaseAdapter<Account, BaseViewHolder> {
    private static final int NORMAL_VIEW_TYPE = 1;
    private static final int FOOTER_VIEW_TYPE = 2;
    private final AccountRepository mRepository;

    private final LifecycleOwner mLifecycleOwner;
    private ViewDataBinding dataBinding;

    public AccountListAdapter(Context context, LifecycleOwner lifecycleOwner, AccountRepository repository) {
        super(context);
        this.mRepository = repository;
        this.mLifecycleOwner = lifecycleOwner;
    }

    @Override
    public BaseViewHolder onCreateVH(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW_TYPE) {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_account, parent, false);
        } else {
            dataBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_add_account, parent, false);
        }
        return new BaseViewHolder(dataBinding);
    }

    @Override
    public void onBindVH(BaseViewHolder baseViewHolder, int position) {
        ViewDataBinding binding = baseViewHolder.getBinding();
        if (binding instanceof ItemAccountBinding) {
            binding.setVariable(BR.item, mData.get(position));
            binding.setVariable(BR.position, position);
        }
        binding.setLifecycleOwner(mLifecycleOwner);
        binding.setVariable(BR.adapter, this);
        binding.executePendingBindings(); //防止闪烁


    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return FOOTER_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public void switchAccount(Account item, int position) {
        for (Account account : mData) {
            account.setCur(false);
        }
        item.setCur(true);
        mRepository.setCurAccount(item);
        EmailApplication.setAccount(item);
        notifyDataSetChanged();
    }

    public void addAccount() {
        EmailCategoryActivity.start2EmailCategoryActivity(mContext);
        ((Activity) mContext).finish();
    }

}
