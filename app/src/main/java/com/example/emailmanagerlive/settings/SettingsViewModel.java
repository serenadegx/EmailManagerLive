package com.example.emailmanagerlive.settings;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.AccountDao;
import com.example.emailmanagerlive.data.ConfigurationDao;

import java.util.List;

public class SettingsViewModel extends ViewModel {
    public final MutableLiveData<List<Account>> mItems = new MutableLiveData<>();
    public final MutableLiveData<String> personal = new MutableLiveData<>();
    public final MutableLiveData<String> receiveHost = new MutableLiveData<>();
    public final MutableLiveData<String> receivePort = new MutableLiveData<>();
    public final MutableLiveData<String> sendHost = new MutableLiveData<>();
    public final MutableLiveData<String> sendPort = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isNotify = new MutableLiveData<>();
    public final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();

    private AccountDao mAccountDao;
    private ConfigurationDao mConfigurationDao;

    public SettingsViewModel(AccountDao mAccountDao, ConfigurationDao mConfigurationDao) {
        this.mAccountDao = mAccountDao;
        this.mConfigurationDao = mConfigurationDao;
    }

    public void editSignature(View view) {
    }

    public Account modify() {
        return null;
    }

    public void start() {
        List<Account> accounts = mAccountDao.loadAll();
        mItems.setValue(accounts);
    }
}
