package com.example.emailmanagerlive.account;

import android.os.SystemClock;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.source.AccountRepository;
import com.example.emailmanagerlive.utils.ThreadPoolFactory;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

public class VerifyModel extends ViewModel {
    public final MutableLiveData<String> account = new MutableLiveData<>();
    public final MutableLiveData<String> pwd = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();
    private final MutableLiveData<Event<Object>> verified = new MutableLiveData<>();
    private final AccountRepository mRepository;
    private final long mCategory;

    public VerifyModel(AccountRepository repository, long category) {
        this.mRepository = repository;
        this.mCategory = category;
    }

    public MutableLiveData<Event<String>> getSnackBarText() {
        return snackBarText;
    }

    public MutableLiveData<Event<Object>> getVerified() {
        return verified;

    }

    public void addAccount(View view) {
        ThreadPoolFactory.getNormalThreadPoolProxy().execute(new Runnable() {
            @Override
            public void run() {
                Properties props = System.getProperties();
                props.put("mail.imap.host", "imap.exmail.qq.com");
                props.put("mail.imap.port", "993");
                props.put("mail.imap.ssl.enable", true);
                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                account.getValue(), pwd.getValue());
                    }
                });
                session.setDebug(true);
                Store store = null;
                try {
                    store = session.getStore("imap");
                    store.connect();
                    //储存账号
                    saveAccount();
                    snackBarText.postValue(new Event<>("登录成功"));
                    SystemClock.sleep(1000);
                    verified.postValue(new Event<>(new Object()));
                } catch (NoSuchProviderException e) {
                    snackBarText.postValue(new Event<>("验证失败"));
                    e.printStackTrace();
                } catch (MessagingException e) {
                    snackBarText.postValue(new Event<>("验证失败"));
                    e.printStackTrace();
                } finally {
                    try {
                        store.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    private void saveAccount() {
        Account data = new Account();
        data.setAccount(account.getValue());
        data.setPwd(pwd.getValue());
        data.setConfigId(mCategory);
        data.setCur(true);
        mRepository.add(data);
        EmailApplication.setAccount(data);
    }


}
