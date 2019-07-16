package com.example.emailmanagerlive.data.source;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.EmailParams;
import com.example.emailmanagerlive.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerlive.data.source.remote.EmailRemoteDataSource;

import java.io.File;
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
    public void getEmail(Account account, EmailParams params, final GetEmailCallBack callBack) {
        mRemoteDataSource.getEmail(account, params, new GetEmailCallBack() {
            @Override
            public void onEmailLoaded(Email email) {
                mLocalDataSource.signRead(email);
                callBack.onEmailLoaded(email);
            }

            @Override
            public void onDataNotAvailable() {
                callBack.onDataNotAvailable();
            }
        });
    }

    @Override
    public void delete(final Account account, final EmailParams params, final CallBack callBack) {
        mRemoteDataSource.delete(account, params, new CallBack() {
            @Override
            public void onSuccess() {
                mLocalDataSource.delete(account, params, callBack);
            }

            @Override
            public void onError() {
                callBack.onError();
            }
        });
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

    /**
     * 发送邮件
     *
     * @param account
     * @param email
     * @param callBack
     */
    public void send(Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.send(account, email, save2Sent, callBack);
    }

    /**
     * 回复
     *
     * @param account
     * @param email
     * @param callBack
     */
    public void reply(Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.reply(account, email, save2Sent, callBack);
    }

    public void forward(Account account, Email email, boolean save2Sent, CallBack callBack) {
        mRemoteDataSource.forward(account, email, save2Sent, callBack);
    }

    public void save2Drafts(Account account, Email data, CallBack callBack) {
        mRemoteDataSource.save2Drafts(account, data, callBack);
    }

    public void download(Account account, File file, EmailParams params, int index, long total, DownloadCallback callback) {
        mRemoteDataSource.downloadAttachment(account, file, params, index, total, callback);
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
