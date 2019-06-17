package com.example.emailmanagerlive.data.source;

import com.example.emailmanagerlive.data.Account;

import java.util.List;

public interface AccountDataSource {
    interface CallBack {
        void onSuccess();

        void onError(String ex);
    }

    interface AccountsCallBack {
        void onAccountsLoaded(List<Account> accounts);

        void onDataNotAvailable();
    }

    interface AccountCallBack {
        void onAccountLoaded(Account account);

        void onDataNotAvailable();
    }

    void add(Account account);

    void update(Account account);

    void delete(Account account);

    void deleteAll();

    void getAccounts(AccountsCallBack callBack);

    void getAccount(AccountCallBack callBack);
}
