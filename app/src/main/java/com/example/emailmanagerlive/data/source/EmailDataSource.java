package com.example.emailmanagerlive.data.source;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.remote.EmailRemoteDataSource;

import java.util.List;

public interface EmailDataSource {
    interface CallBack {
        void onSuccess();

        void onError();
    }

    interface GetEmailsCallBack {

        void onEmailsLoaded(List<Email> emails);

        void onDataNotAvailable();
    }

    interface GetEmailCallBack {

        void onEmailLoaded(Email email);

        void onDataNotAvailable();
    }

    interface DownloadCallback {

        void onProgress(float percent);

        void onFinish();

        void onError();
    }

    void getEmails(Account account, GetEmailsCallBack callBack);

    void getEmail(Account account, long id, GetEmailCallBack callBack);

    void delete(Account account, long id, CallBack callBack);

}
