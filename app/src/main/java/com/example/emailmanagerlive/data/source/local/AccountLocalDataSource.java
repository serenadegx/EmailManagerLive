package com.example.emailmanagerlive.data.source.local;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.AccountDao;
import com.example.emailmanagerlive.data.source.AccountDataSource;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class AccountLocalDataSource implements AccountDataSource {

    private AccountDao dao;

    public AccountLocalDataSource(AccountDao dao) {
        this.dao = dao;
    }

    @Override
    public void add(Account account) {
        dao.insert(account);

    }

    @Override
    public void update(Account account) {
        dao.update(account);
    }

    @Override
    public void delete(Account account) {
        dao.delete(account);
    }

    @Override
    public void deleteAll() {
        dao.deleteAll();
    }

    @Override
    public void getAccounts(AccountsCallBack callBack) {
        List<Account> accounts = dao.loadAll();
        if (accounts != null && accounts.size() > 0) {
            callBack.onAccountsLoaded(accounts);
        } else {
            callBack.onDataNotAvailable();
        }
    }

    @Override
    public void getAccount(AccountCallBack callBack) {
        QueryBuilder<Account> qb = dao.queryBuilder().where(AccountDao.Properties.IsCur.eq("true"));
        List<Account> data = qb.list();
        if (data != null && data.size() > 0) {
            callBack.onAccountLoaded(data.get(0));
        } else {
            callBack.onDataNotAvailable();
        }
    }
}
