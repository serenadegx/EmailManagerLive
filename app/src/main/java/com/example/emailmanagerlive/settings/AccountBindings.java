package com.example.emailmanagerlive.settings;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.utils.BaseAdapter;

import java.util.List;

public class AccountBindings {
    @SuppressWarnings("unchecked")
    @BindingAdapter("android:items")
    public static void setItems(RecyclerView rv, List<Account> data) {
        BaseAdapter listAdapter = (BaseAdapter) rv.getAdapter();
        listAdapter.refreshData(data);
    }
}
