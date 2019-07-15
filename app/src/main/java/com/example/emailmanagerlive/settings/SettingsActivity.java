package com.example.emailmanagerlive.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.ViewModelFactory;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity implements SettingsNavigator {

    public static final String SETTINGS = "settings";
    public static final String SIGN_TAG = "editSignature";

    public static final int REQUEST_CODE = 715;
    private ActivitySettingsBinding binding;
    private SettingsViewModel mSettingsViewModel;
    private Account mAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setupToolbar();
        replaceFragmentInActivity(SETTINGS);
        mSettingsViewModel = obtainViewModel(this);
        mSettingsViewModel.setNavigator(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof SettingsFragment) {
                mSettingsViewModel.modify();
            } else {
                mSettingsViewModel.saveSignature();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onModifySuccess(Account account) {
        EmailApplication.setAccount(account);
        setResult(63);
        finish();
    }

    @Override
    public void editSignature() {
        binding.toolbar.setTitle("修改签名");
        replaceFragmentInActivity(SIGN_TAG);
    }

    @Override
    public void editSignatureSuccess() {
        binding.toolbar.setTitle("设置");
        replaceFragmentInActivity(SETTINGS);
    }

    @Override
    public void onBackPressed() {
        backSelf();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSelf();
            }
        });
    }

    private void replaceFragmentInActivity(String tag) {
        Fragment fragment = findOrCreateFragment(tag);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    private Fragment findOrCreateFragment(String tag) {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment == null) {
            if (SETTINGS.equals(tag)) {
                fragment = SettingsFragment.newInstance();
            } else {
                fragment = EditSignatureFragment.newInstance();
            }
        }
        return fragment;
    }

    private void backSelf() {
        if (getSupportFragmentManager().findFragmentById(R.id.content) instanceof SettingsFragment) {
            finish();
        } else {
            binding.toolbar.setTitle("设置");
            replaceFragmentInActivity(SETTINGS);
        }
    }

    public static SettingsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance();
        return ViewModelProviders.of(activity, viewModelFactory).get(SettingsViewModel.class);
    }

    public static void startForResult2SettingsActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, SettingsActivity.class), REQUEST_CODE);
    }
}
