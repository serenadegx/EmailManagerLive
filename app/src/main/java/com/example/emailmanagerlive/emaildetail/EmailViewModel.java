package com.example.emailmanagerlive.emaildetail;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.EmailParams;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.example.emailmanagerlive.data.source.EmailRepository;
import com.example.emailmanagerlive.send.SendEmailActivity;

import java.io.File;
import java.util.List;

public class EmailViewModel extends ViewModel implements EmailDataSource.GetEmailCallBack {
    public final MutableLiveData<List<Attachment>> items = new MutableLiveData<>();
    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> receivers = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isCc = new MutableLiveData<>();
    public final MutableLiveData<String> cc = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isBcc = new MutableLiveData<>();
    public final MutableLiveData<String> bcc = new MutableLiveData<>();
    public final MutableLiveData<String> subject = new MutableLiveData<>();
    public final MutableLiveData<String> date = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isAttach = new MutableLiveData<>();
    public final MutableLiveData<String> accessory = new MutableLiveData<>();
    public final MutableLiveData<String> html = new MutableLiveData<>();
    private final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();

    private EmailRepository mRepository;
    private Account mAccount;
    private Context mContext;
    private EmailDetailNavigator mNavigator;
    private Email email;
    private EmailParams mEmailParams;

    public EmailViewModel(EmailRepository mRepository, Account account, Context context) {
        this.mRepository = mRepository;
        this.mAccount = account;
        this.mContext = context;
        receivers.setValue("");
        subject.setValue("");
        date.setValue("");
    }

    public void setNavigator(EmailDetailNavigator navigator) {
        this.mNavigator = navigator;
    }

    @Override
    public void onEmailLoaded(Email email) {
        this.email = email;
        if (mEmailParams.getType() == EmailParams.Type.INBOX) {
            title.postValue(TextUtils.isEmpty(email.getPersonal()) ? email.getFrom() : email.getPersonal());
        }
        receivers.postValue(email.getTo());
        isCc.postValue(!TextUtils.isEmpty(email.getCc()));
        if (!TextUtils.isEmpty(email.getCc())) {
            cc.postValue(email.getCc());
        }
        isBcc.postValue(!TextUtils.isEmpty(email.getBcc()));
        if (!TextUtils.isEmpty(email.getBcc())) {
            bcc.postValue(email.getBcc());
        }
        subject.postValue(email.getSubject());
        date.postValue(email.getDate());
        isAttach.postValue(email.getAttachments().size() > 0);
        accessory.postValue(email.getAttachments().size() + "个附件");
        for (Attachment attachment : email.getAttachments()) {
            attachment.setDownload(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/EmailManager", attachment.getFileName()).exists());
        }
        items.postValue(email.getAttachments());
        html.postValue(email.getContent());
    }

    @Override
    public void onDataNotAvailable() {
        snackBarText.postValue(new Event<>("获取失败"));
    }

    public Email getEmail() {
        return email;
    }

    public void getEmail(EmailParams params) {
        this.mEmailParams = params;
        if (params.getType() == EmailParams.Type.INBOX) {
            title.postValue("收件箱");
        } else if (params.getType() == EmailParams.Type.SENT) {
            title.postValue("发件箱");
        } else {
            title.postValue("草稿箱");
        }
        new Thread() {
            @Override
            public void run() {
                mRepository.getEmail(mAccount, mEmailParams, EmailViewModel.this);
            }
        }.start();

    }

    public void reply(View view) {
        mEmailParams.setFunction(EmailParams.Function.REPLY);
        if (email != null)
            SendEmailActivity.start2SendEmailActivity(mContext, mEmailParams, email);
    }

    public void forward(View view) {
        mEmailParams.setFunction(EmailParams.Function.FORWARD);
        if (email != null)
            SendEmailActivity.start2SendEmailActivity(mContext, mEmailParams, email);
    }

    public void delete(View view) {
        new AlertDialog.Builder(mContext)
                .setTitle("提示信息")
                .setMessage("是否删除邮件")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        realDelete();
                    }
                }).show();

    }

    public MutableLiveData<Event<String>> getSnackBarText() {
        return snackBarText;
    }

    private void realDelete() {
        new Thread() {
            @Override
            public void run() {
                mRepository.delete(mAccount, mEmailParams, new EmailDataSource.CallBack() {
                    @Override
                    public void onSuccess() {
                        snackBarText.postValue(new Event<>("删除成功"));
                        SystemClock.sleep(500);
                        mNavigator.onEmailDeleted();
                    }

                    @Override
                    public void onError() {
                        snackBarText.setValue(new Event<>("删除失败"));
                    }
                });
            }
        }.start();
    }
}
