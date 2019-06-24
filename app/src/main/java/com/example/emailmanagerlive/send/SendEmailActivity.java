package com.example.emailmanagerlive.send;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.emailmanagerlive.R;

public class SendEmailActivity extends AppCompatActivity implements SendEmailNavigator {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_send_email);
    }

    @Override
    public void onSent() {

    }

    @Override
    public void onSaved() {

    }
}
