package com.example.emailmanagerlive;

import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.emailmanagerlive.account.EmailCategoryActivity;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Configuration;
import com.example.emailmanagerlive.data.source.AccountDataSource;
import com.example.emailmanagerlive.data.source.AccountRepository;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private AccountRepository mRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_splash);
        mRepository = AccountRepository.provideRepository();
        initData();
        new Thread(){
            @Override
            public void run() {
                SystemClock.sleep(1000);
                mRepository.getAccount(new AccountDataSource.AccountCallBack() {
                    @Override
                    public void onAccountLoaded(Account account) {
                        EmailApplication.setAccount(account);
                        MainActivity.start2MainActivity(SplashActivity.this);
                        finish();
                    }

                    @Override
                    public void onDataNotAvailable() {
                        EmailCategoryActivity.start2EmailCategoryActivity(SplashActivity.this);
                        finish();
                    }
                });
            }
        }.start();

    }

    private void initData() {
        //配置邮箱格式
        List<Configuration> configs = EmailApplication.getDaoSession().getConfigurationDao().loadAll();
        if (configs == null || configs.size() < 2) {
            mRepository.config(this);
        }
    }
}
