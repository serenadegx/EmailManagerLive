package com.example.emailmanagerlive.emaildetail;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.utils.BaseAdapter;

import java.util.List;

public class AttachmentsBindings {
    @SuppressWarnings("unchecked")
    @BindingAdapter("android:items")
    public static void setItems(RecyclerView rv, List<Attachment> data){
        BaseAdapter listAdapter = (BaseAdapter) rv.getAdapter();
        listAdapter.refreshData(data);
    }
}
