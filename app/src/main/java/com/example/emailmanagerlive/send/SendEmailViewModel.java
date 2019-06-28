package com.example.emailmanagerlive.send;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.EmailApplication;
import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.multifile.XRMultiFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendEmailViewModel extends ViewModel implements EmailDataSource.CallBack {
    public final MutableLiveData<List<Attachment>> items = new MutableLiveData<>();
    public final MutableLiveData<String> receiver = new MutableLiveData<>();
    public final MutableLiveData<String> copy = new MutableLiveData<>();
    public final MutableLiveData<String> secret = new MutableLiveData<>();
    public final MutableLiveData<String> send = new MutableLiveData<>();
    public final MutableLiveData<String> subject = new MutableLiveData<>();
    public final MutableLiveData<String> content = new MutableLiveData<>();
    public final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();

    private EmailRepository mRepository;
    private Account mAccount;
    private SendEmailNavigator mNavigator;
    private List<Attachment> mAttachments;
    private long id;
    private String mContent;

    public SendEmailViewModel(EmailRepository repository, Account account) {
        this.mRepository = repository;
        this.mAccount = account;
        mAttachments = new ArrayList<>();
    }

    void handleOnActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 715 && data != null) {
            ArrayList<String> list = XRMultiFile.getSelectResult(data);
            for (String str : list) {
                Attachment attachment = new Attachment();
                attachment.setPath(str);
                attachment.setFileName(str.substring(str.lastIndexOf("/") + 1));
                attachment.setSize(getPrintSize(str));
                attachment.setDownload(true);
                attachment.setEnable(true);
                mAttachments.add(attachment);
            }
            items.setValue(mAttachments);
        }
    }

    void setNavigator(SendEmailNavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    public void onSuccess() {
        snackBarText.postValue(new Event<>("发送成功"));
        mNavigator.onSent();
    }

    @Override
    public void onError() {
        mNavigator.onError();
        snackBarText.postValue(new Event<>("发送失败"));
    }

    public void start(int type, Email data) {
        receiver.setValue("1099805713@qq.com");
        send.setValue(EmailApplication.getAccount().getAccount());
        if (data != null) {
            if (type == SendEmailActivity.REPLY) {
                receiver.setValue(data.getFrom());
                subject.setValue("回复:" + data.getSubject());
            } else if (type == SendEmailActivity.FORWARD) {
                subject.setValue("转发:" + data.getSubject());
                if (data.getAttachments() != null && data.getAttachments().size() > 0) {
                    items.setValue(data.getAttachments());
                }
            }
            this.id = data.getId();
            this.mContent = data.getContent();
        }
    }

    public void send() {
        mNavigator.onSending("正在发送...");
        final Email email = new Email();
        email.setFrom(TextUtils.isEmpty(send.getValue()) ? null : send.getValue());
        email.setTo(TextUtils.isEmpty(receiver.getValue()) ? null : receiver.getValue());
        email.setCc(TextUtils.isEmpty(copy.getValue()) ? null : copy.getValue());
        email.setBcc(TextUtils.isEmpty(secret.getValue()) ? null : secret.getValue());
        email.setSubject(subject.getValue());
        email.setContent(content.getValue());
        email.setAttachments(items.getValue());
        new Thread() {
            @Override
            public void run() {
                mRepository.send(mAccount, email, SendEmailViewModel.this);
            }
        }.start();

    }

    public void reply() {
        mNavigator.onSending("正在回复...");
        final Email email = new Email();
        email.setId(id);
        email.setFrom(TextUtils.isEmpty(send.getValue()) ? null : send.getValue());
        email.setTo(TextUtils.isEmpty(receiver.getValue()) ? null : receiver.getValue());
        email.setCc(TextUtils.isEmpty(copy.getValue()) ? null : copy.getValue());
        email.setBcc(TextUtils.isEmpty(secret.getValue()) ? null : secret.getValue());
        email.setSubject(subject.getValue());
        email.setAppend(content.getValue());
        email.setContent(mContent);
        email.setAttachments(items.getValue());
        new Thread() {
            @Override
            public void run() {
                mRepository.reply(mAccount, email, SendEmailViewModel.this);
            }
        }.start();
    }

    public void forward() {
        mNavigator.onSending("正在转发...");
        final Email email = new Email();
        email.setId(id);
        email.setFrom(TextUtils.isEmpty(send.getValue()) ? null : send.getValue());
        email.setTo(TextUtils.isEmpty(receiver.getValue()) ? null : receiver.getValue());
        email.setCc(TextUtils.isEmpty(copy.getValue()) ? null : copy.getValue());
        email.setBcc(TextUtils.isEmpty(secret.getValue()) ? null : secret.getValue());
        email.setSubject(subject.getValue());
        email.setAppend(content.getValue());
        email.setContent(mContent);
        email.setAttachments(items.getValue());
        new Thread() {
            @Override
            public void run() {
                mRepository.forward(mAccount, email, SendEmailViewModel.this);
            }
        }.start();
    }

    public void save2Drafts() {
        mNavigator.onSaving("正在保存...");
        final Email email = new Email();
        email.setFrom(TextUtils.isEmpty(send.getValue()) ? null : send.getValue());
        email.setTo(TextUtils.isEmpty(receiver.getValue()) ? null : receiver.getValue());
        email.setCc(TextUtils.isEmpty(copy.getValue()) ? null : copy.getValue());
        email.setBcc(TextUtils.isEmpty(secret.getValue()) ? null : secret.getValue());
        email.setSubject(subject.getValue());
        email.setContent(content.getValue());
        email.setAttachments(items.getValue());
        new Thread() {
            @Override
            public void run() {
                mRepository.save2Drafts(mAccount, email, new EmailDataSource.CallBack() {
                    @Override
                    public void onSuccess() {
                        snackBarText.postValue(new Event<>("发送成功"));
                        mNavigator.onSaved();
                    }

                    @Override
                    public void onError() {
                        mNavigator.onError();
                    }
                });
            }
        }.start();
    }

    public void delete(Attachment item) {
        mAttachments.remove(item);
        items.setValue(mAttachments);
    }

    public MutableLiveData<Event<String>> getSnackBarText() {
        return snackBarText;
    }

    public static String getPrintSize(String path) {
        long size = getSize(path);
        //如果字节数少于1024，则直接以B为单位，否则先除于1024，后3位因太少无意义
        if (size < 1024) {
            return String.valueOf(size) + " B";
        } else {
            size = size / 1024;
        }
        //如果原字节数除于1024之后，少于1024，则可以直接以KB作为单位
        //因为还没有到达要使用另一个单位的时候
        //接下去以此类推
        if (size < 1024) {
            return String.valueOf(size) + " KB";
        } else {
            size = size / 1024;
        }
        if (size < 1024) {
            //因为如果以MB为单位的话，要保留最后1位小数，
            //因此，把此数乘以100之后再取余
            size = size * 100;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size * 100 / 1024 % 100)) + "MB";
        } else {
            //否则如果要以GB为单位的，先除于1024再作同样的处理
            size = size * 100 / 1024;
            return String.valueOf((size / 100)) + "."
                    + String.valueOf((size % 100)) + " GB";
        }
    }

    public static long getSize(String path) {
        long size = 0;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File(path));
            size = fis.getChannel().size();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return size;
    }
}
