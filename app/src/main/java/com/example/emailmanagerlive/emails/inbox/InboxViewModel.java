package com.example.emailmanagerlive.emails.inbox;

import android.os.SystemClock;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.emails.EmailsViewModel;
import com.example.emailmanagerlive.utils.ThreadPoolFactory;

import java.util.Collections;
import java.util.List;

public class InboxViewModel extends ViewModel implements EmailsViewModel, EmailDataSource.GetEmailsCallBack {
    public final MutableLiveData<List<Email>> items = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDataLoading = new MutableLiveData<>();
    public final LiveData<Boolean> mEmpty = Transformations.map(items,
            new Function<List<Email>, Boolean>() {
                @Override
                public Boolean apply(List<Email> input) {
                    return input.isEmpty();

                }
            });
    private EmailRepository mRepository;
    private Account mAccount;

    public InboxViewModel(EmailRepository repository) {
        this.mRepository = repository;

    }

    @Override
    public void refresh() {
        mRepository.refresh();
        loadEmails(EmailApplication.getAccount());
    }

    @Override
    public void onEmailsLoaded(List<Email> emails) {
        Collections.sort(emails);
        mDataLoading.postValue(false);
        items.postValue(emails);
    }

    @Override
    public void onDataNotAvailable() {
        mDataLoading.postValue(false);
    }

    public void loadEmails(Account account) {
        this.mAccount = account;
        mDataLoading.setValue(true);
        ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(500);
                mRepository.getEmails(mAccount, InboxViewModel.this);
            }
        });
    }

    public LiveData<Boolean> isDataLoading() {
        return mDataLoading;
    }

}
