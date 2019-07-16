package com.example.emailmanagerlive.data.source;

import android.content.Context;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.R;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Configuration;
import com.example.emailmanagerlive.data.source.local.AccountLocalDataSource;

public class AccountRepository implements AccountDataSource {

    private static AccountRepository INSTANCE;

    private AccountLocalDataSource mLocalDataSource;

    public AccountRepository(AccountLocalDataSource mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static AccountRepository provideRepository() {
        if (INSTANCE == null) {
            synchronized (AccountRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new AccountRepository(AccountLocalDataSource.getInstance(
                            EmailApplication.getDaoSession().getAccountDao()));
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void add(Account account) {
        mLocalDataSource.add(account);
    }

    @Override
    public void update(Account account) {
        mLocalDataSource.update(account);
    }

    @Override
    public void delete(Account account) {
        mLocalDataSource.delete(account);
    }

    @Override
    public void deleteAll() {
        mLocalDataSource.deleteAll();
    }

    @Override
    public void getAccounts(AccountsCallBack callBack) {
        mLocalDataSource.getAccounts(callBack);
    }

    @Override
    public void getAccount(AccountCallBack callBack) {
        mLocalDataSource.getAccount(callBack);
    }

    public void config(Context context){
        Configuration config1 = new Configuration();
        String[] array = context.getResources().getStringArray(R.array.qq_email);
        config1.setCategoryId(1);//主键不能重复
        config1.setName(array[0]);
        config1.setReceiveProtocol(array[1]);
        config1.setReceiveHostKey(array[2]);
        config1.setReceiveHostValue(array[3]);
        config1.setReceivePortKey(array[4]);
        config1.setReceivePortValue(array[5]);
        config1.setReceiveEncryptKey(array[6]);
        config1.setReceiveEncryptValue("1".equals(array[7]));
        config1.setSendProtocol(array[8]);
        config1.setSendHostKey(array[9]);
        config1.setSendHostValue(array[10]);
        config1.setSendPortKey(array[11]);
        config1.setSendPortValue(array[12]);
        config1.setSendEncryptKey(array[13]);
        config1.setSendEncryptValue("1".equals(array[14]));
        config1.setAuthKey(array[15]);
        config1.setAuthValue("1".equals(array[16]));
        EmailApplication.getDaoSession().getConfigurationDao().insert(config1);
        String[] array2 = context.getResources().getStringArray(R.array.qq_exmail);
        Configuration config2 = new Configuration();
        config2.setCategoryId(2);
        config2.setName(array2[0]);
        config2.setReceiveProtocol(array2[1]);
        config2.setReceiveHostKey(array2[2]);
        config2.setReceiveHostValue(array2[3]);
        config2.setReceivePortKey(array2[4]);
        config2.setReceivePortValue(array2[5]);
        config2.setReceiveEncryptKey(array2[6]);
        config2.setReceiveEncryptValue("1".equals(array2[7]));
        config2.setSendProtocol(array2[8]);
        config2.setSendHostKey(array2[9]);
        config2.setSendHostValue(array2[10]);
        config2.setSendPortKey(array2[11]);
        config2.setSendPortValue(array2[12]);
        config2.setSendEncryptKey(array2[13]);
        config2.setSendEncryptValue("1".equals(array2[14]));
        config2.setAuthKey(array2[15]);
        config2.setAuthValue("1".equals(array2[16]));
        EmailApplication.getDaoSession().getConfigurationDao().insert(config2);
    }

    public void setCurAccount(Account account){
        EmailApplication.getDaoSession().getEmailDao().deleteAll();
        mLocalDataSource.setCurAccount(account);
    }
}
