package com.example.emailmanagerlive.data.source.local;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.EmailDao;
import com.example.emailmanagerlive.data.source.EmailDataSource;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class EmailLocalDataSource implements EmailDataSource {

    private static EmailLocalDataSource INSTANCE;

    private EmailDao dao;

    private EmailLocalDataSource(EmailDao dao) {
        this.dao = dao;
    }

    public static EmailLocalDataSource getInstance(EmailDao dao) {
        if (INSTANCE == null) {
            INSTANCE = new EmailLocalDataSource(dao);
        }
        return INSTANCE;
    }

    @Override
    public void getEmails(Account account, GetEmailsCallBack callBack) {
        List<Email> data = dao.loadAll();
        if (data != null && data.size() > 0) {
            callBack.onEmailsLoaded(data);
        } else {
            callBack.onDataNotAvailable();
        }
    }

    @Override
    public void getEmail(Account account, long id, GetEmailCallBack callBack) {
        QueryBuilder<Email> qb = dao.queryBuilder();
        List<Email> data = qb.where(EmailDao.Properties.Id.eq(id)).list();
        if (data != null && data.size() > 0) {
            callBack.onEmailLoaded(data.get(0));
        } else {
            callBack.onDataNotAvailable();
        }
    }

    @Override
    public void delete(Account account, long id, CallBack callBack) {
        dao.deleteByKey(id);
        callBack.onSuccess();
    }

    public void deleteAll(){
        dao.deleteAll();
    }

    public void saveAll(List<Email> emails){
        dao.insertInTx(emails);
    }
}
