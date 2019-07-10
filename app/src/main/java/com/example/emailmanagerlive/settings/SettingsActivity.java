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
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.ViewModelFactory;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.databinding.ActivitySettingsBinding;

public class SettingsActivity extends AppCompatActivity implements SettingsNavigator {

    private static final int REQUEST_CODE = 715;
    private ActivitySettingsBinding binding;
    private SettingsViewModel mSettingsViewModel;
    private Account mAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings);
        setupToolbar();
        replaceFragmentInActivity();
        mSettingsViewModel = obtainViewModel(this);

    }

    private void replaceFragmentInActivity() {
        SettingsFragment fragment = findOrCreateFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_done) {
            mAccount = mSettingsViewModel.modify();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onModifySuccess() {

    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private SettingsFragment findOrCreateFragment() {
        SettingsFragment fragment = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == null) {
            fragment = SettingsFragment.newInstance();
        }
        return fragment;
    }

    public static SettingsViewModel obtainViewModel(FragmentActivity activity) {
        ViewModelFactory viewModelFactory = ViewModelFactory.getInstance();
        return ViewModelProviders.of(activity, viewModelFactory).get(SettingsViewModel.class);
    }

    public static void startForResult2SettingsActivity(Activity activity) {
        activity.startActivityForResult(new Intent(activity, SettingsActivity.class), REQUEST_CODE);
    }
}
