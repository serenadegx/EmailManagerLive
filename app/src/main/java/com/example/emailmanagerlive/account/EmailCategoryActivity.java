package com.example.emailmanagerlive.account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.account.adapter.EmailCategoryAdapter;
import com.example.emailmanagerlive.databinding.ActivityEmailCategoryBinding;
import com.example.multifile.ui.EMDecoration;

public class EmailCategoryActivity extends AppCompatActivity {

    private ActivityEmailCategoryBinding binding;
    private EmailCategoryAdapter listAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_category);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.addItemDecoration(new EMDecoration(this, EMDecoration.VERTICAL_LIST, R.drawable.list_divider, 0));
        initToolbar();
        initAdapter();
        iniData();
    }

    private void iniData() {
        listAdapter.refreshData(EmailApplication.getDaoSession().getConfigurationDao().loadAll());
    }

    private void initAdapter() {
        listAdapter = new EmailCategoryAdapter(this);
        binding.rv.setAdapter(listAdapter);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static void start2EmailCategoryActivity(Context context) {
        context.startActivity(new Intent(context, EmailCategoryActivity.class));
    }
}
