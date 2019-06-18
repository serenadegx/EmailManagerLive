package com.example.emailmanagerlive;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.DaoMaster;
import com.example.emailmanagerlive.data.DaoSession;

public class EmailApplication extends Application {

    private static DaoSession daoSession;
    private static Account mAccount;

    public static DaoSession getDaoSession() {
        return daoSession;
    }

    public static Account getAccount() {
        return mAccount;
    }

    public static void setAccount(Account account) {
        mAccount = account;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "email.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }
}
