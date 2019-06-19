package com.example.emailmanagerlive.emails;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.utils.BaseAdapter;

import java.util.List;

public class EmailsBindings {
    @SuppressWarnings("unchecked")
    @BindingAdapter("android:items")
    public static void setItems(RecyclerView rv, List<Email> data){
        BaseAdapter listAdapter = (BaseAdapter) rv.getAdapter();
        listAdapter.refreshData(data);
    }
}
