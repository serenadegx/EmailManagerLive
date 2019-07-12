package com.example.emailmanagerlive.settings;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.CompoundButton;

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
    private SharedPreferences sp;
    private SettingsNavigator mNavigator;

    public CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            sp.edit().putBoolean("isNotify", isChecked).commit();
        }
    };

    public SettingsViewModel(AccountDao mAccountDao, ConfigurationDao mConfigurationDao) {
        this.mAccountDao = mAccountDao;
        this.mConfigurationDao = mConfigurationDao;
    }


    public void editSignature(View view) {
        mNavigator.editSignature();
    }

    public Account modify() {
        return null;
    }

    public void start(SharedPreferences sp) {
        this.sp = sp;
        List<Account> accounts = mAccountDao.loadAll();
        mItems.setValue(accounts);
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            receiveHost.setValue(curAccount.getConfig().getReceiveHostValue());
            receivePort.setValue(curAccount.getConfig().getReceivePortValue());
            sendHost.setValue(curAccount.getConfig().getSendHostValue());
            sendPort.setValue(curAccount.getConfig().getSendPortValue());
        }
        isNotify.setValue(sp.getBoolean("isNotify", false));
    }

    public void setNavigator(SettingsNavigator navigator) {
        this.mNavigator = navigator;
    }
}
