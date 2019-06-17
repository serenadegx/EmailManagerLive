package com.example.emailmanagerlive.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.MainActivity;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.databinding.ActivityVerifyBinding;
import com.google.android.material.snackbar.Snackbar;

public class VerifyActivity extends AppCompatActivity implements VerifyNavigator {

    private VerifyModel mViewModel;
    private ActivityVerifyBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_verify);
        mViewModel = new VerifyModel();
        mBinding.setViewModel(mViewModel);
        setupSnackBar();
        subscribeToNavigationChanges();
    }

    @Override
    public void onAccountVerify() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void subscribeToNavigationChanges() {
        mViewModel.getVerified().observe(this, new Observer<Event<Object>>() {
            @Override
            public void onChanged(Event<Object> objectEvent) {
                if (objectEvent.getContentIfNotHandled() != null) {
                    VerifyActivity.this.onAccountVerify();
                }
            }
        });
    }

    private void setupSnackBar() {
        mViewModel.getSnackBarText().observe(this, new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                String msg = stringEvent.getContentIfNotHandled();
                if (!TextUtils.isEmpty(msg)) {
                    Snackbar.make(mBinding.getRoot(), msg, Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
