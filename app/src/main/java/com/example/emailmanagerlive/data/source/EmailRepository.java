package com.example.emailmanagerlive.data.source;

import android.util.Log;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerlive.data.source.remote.EmailRemoteDataSource;
import com.example.emailmanagerlive.emails.drafts.DraftsViewModel;
import com.example.emailmanagerlive.emails.sent.SentViewModel;

import java.util.List;

public class EmailRepository implements EmailDataSource {

    private static EmailRepository INSTANCE;
    private EmailLocalDataSource mLocalDataSource;
    private EmailRemoteDataSource mRemoteDataSource;
    private boolean isCache = true;

    private EmailRepository(EmailLocalDataSource localDataSource, EmailRemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    public static EmailRepository provideRepository() {
        if (INSTANCE == null) {
            synchronized (EmailRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new EmailRepository(EmailLocalDataSource.getInstance(EmailApplication
                            .getDaoSession().getEmailDao()), EmailRemoteDataSource.getInstance());
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getEmails(final Account account, final GetEmailsCallBack callBack) {
        if (isCache) {
            mLocalDataSource.getEmails(account, new GetEmailsCallBack() {
                @Override
                public void onEmailsLoaded(List<Email> emails) {

                    callBack.onEmailsLoaded(emails);
                }

                @Override
                public void onDataNotAvailable() {
                    //读取不到本地仓库，再去远程仓库拿，所谓的二级缓存
                    getEmailsFromRemoteDataSource(account, callBack);
                }
            });
        } else {
            getEmailsFromRemoteDataSource(account, callBack);
        }
    }

    @Override
    public void getEmail(Account account, long id, final GetEmailCallBack callBack) {

    }

    @Override
    public void delete(Account account, long id, CallBack callBack) {

    }

    /**
     * 刷新数据
     */
    public void refresh() {
        isCache = false;
    }

    /**
     * 加载发件箱
     *
     * @param account
     * @param callBack
     */
    public void loadSent(Account account, GetEmailsCallBack callBack) {
        mRemoteDataSource.getSentEmails(account, callBack);
    }

    /**
     * 加载草稿箱
     *
     * @param account
     * @param callBack
     */
    public void loadDrafts(Account account, GetEmailsCallBack callBack) {
        mRemoteDataSource.getDrafts(account, callBack);
    }

    private void getEmailsFromRemoteDataSource(Account detail, final GetEmailsCallBack callBack) {
        mRemoteDataSource.getEmails(detail, new GetEmailsCallBack() {
            @Override
            public void onEmailsLoaded(List<Email> emails) {
                refreshLocalDataSource(emails);
                callBack.onEmailsLoaded(emails);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    private void refreshLocalDataSource(List<Email> emails) {
        mLocalDataSource.deleteAll();
        mLocalDataSource.saveAll(emails);
        isCache = true;
    }
}
