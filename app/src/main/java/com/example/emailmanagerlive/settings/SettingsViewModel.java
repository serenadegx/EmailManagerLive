package com.example.emailmanagerlive.settings;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.widget.CompoundButton;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.MainActivity;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.AccountDao;
import com.example.emailmanagerlive.data.Configuration;
import com.example.emailmanagerlive.data.ConfigurationDao;
import com.example.emailmanagerlive.utils.ThreadPoolFactory;

import java.util.List;

public class SettingsViewModel extends ViewModel {
    public final MutableLiveData<List<Account>> mItems = new MutableLiveData<>();
    public final MutableLiveData<String> personal = new MutableLiveData<>();
    public final MutableLiveData<String> signature = new MutableLiveData<>();
    public final MutableLiveData<String> receiveHost = new MutableLiveData<>();
    public final MutableLiveData<String> receivePort = new MutableLiveData<>();
    public final MutableLiveData<String> sendHost = new MutableLiveData<>();
    public final MutableLiveData<String> sendPort = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isNotify = new MutableLiveData<>();
    public final MutableLiveData<Boolean> save2Sent = new MutableLiveData<>();
    public final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();

    private AccountDao mAccountDao;
    private ConfigurationDao mConfigurationDao;
    private SharedPreferences sp;
    private SettingsNavigator mNavigator;

    public CompoundButton.OnCheckedChangeListener saveListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            sp.edit().putBoolean("save2Sent", isChecked).commit();
        }
    };

    public CompoundButton.OnCheckedChangeListener notifyListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            sp.edit().putBoolean("isNotify", isChecked).commit();
            if (isChecked)
                MainActivity.startNewEmailWorker();
        }
    };

    public SettingsViewModel(AccountDao mAccountDao, ConfigurationDao mConfigurationDao) {
        this.mAccountDao = mAccountDao;
        this.mConfigurationDao = mConfigurationDao;
    }


    public void editSignature(View view) {
        mNavigator.editSignature();
    }

    public void editPersonal(View view) {
        mNavigator.editPersonal();
    }

    public void start(SharedPreferences sp) {
        this.sp = sp;
        List<Account> accounts = mAccountDao.loadAll();
        mItems.setValue(accounts);
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            personal.setValue(curAccount.getPersonal());
            signature.setValue(curAccount.getRemark());
        }
        save2Sent.setValue(sp.getBoolean("save2Sent", false));
        isNotify.setValue(sp.getBoolean("isNotify", false));
    }

    public void setNavigator(SettingsNavigator navigator) {
        this.mNavigator = navigator;
    }

    public void modify() {
        ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
                if (list != null && list.size() > 0) {
                    Account curAccount = list.get(0);
                    curAccount.setPersonal(personal.getValue());
                    curAccount.setRemark(signature.getValue());
                    List<Configuration> configurations = mConfigurationDao.queryBuilder()
                            .where(ConfigurationDao.Properties.CategoryId.eq(curAccount.getConfigId())).list();
                    if (configurations != null && configurations.size() > 0) {
                        Configuration configuration = configurations.get(0);
                        configuration.setReceiveHostValue(receiveHost.getValue());
                        configuration.setReceivePortValue(receivePort.getValue());
                        configuration.setSendHostValue(sendHost.getValue());
                        configuration.setSendPortValue(sendPort.getValue());
                        mConfigurationDao.update(configuration);
                    }
                    mAccountDao.update(curAccount);
                    snackBarText.postValue(new Event<>("修改成功"));
                    SystemClock.sleep(500);
                    mNavigator.onModifySuccess(curAccount);
                }
            }
        });
    }

    public void bindSignature() {
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            signature.setValue(curAccount.getRemark());
        }
    }

    public void bindPersonal() {
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            personal.setValue(curAccount.getPersonal());
        }
    }

    public void savePersonal() {
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            curAccount.setPersonal(personal.getValue());
            mAccountDao.update(curAccount);
            EmailApplication.setAccount(curAccount);
        }
        mNavigator.editPersonalSuccess();
    }

    public void saveSignature() {
        List<Account> list = mAccountDao.queryBuilder().where(AccountDao.Properties.IsCur.eq(true)).list();
        if (list != null && list.size() > 0) {
            Account curAccount = list.get(0);
            curAccount.setRemark(signature.getValue());
            mAccountDao.update(curAccount);
            EmailApplication.setAccount(curAccount);
        }
        mNavigator.editSignatureSuccess();
    }
}
