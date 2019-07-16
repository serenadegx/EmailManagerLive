package com.example.emailmanagerlive;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.local.EmailLocalDataSource;
import com.example.emailmanagerlive.data.source.remote.EmailRemoteDataSource;
import com.sun.mail.imap.IMAPFolder;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.InternetAddress;

public class NewEmailWorker extends Worker {

    private final int PUSH_ID = 715;
    public static final String PRIMARY_CHANNEL = "default";
    private final Session session;
    private final Account account;
    private final EmailLocalDataSource dataSource;

    public NewEmailWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dataSource = EmailLocalDataSource.getInstance(EmailApplication.getDaoSession().getEmailDao());
        account = EmailApplication.getAccount();
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        session.setDebug(true);
    }

    @NonNull
    @Override
    public Result doWork() {
        Store store = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            folder.addMessageCountListener(new MessageCountAdapter() {
                @Override
                public void messagesAdded(MessageCountEvent e) {
                    final Message[] messages = e.getMessages();
                    if (messages != null && messages.length > 0) {
                        Log.i("Mango", "您有" + messages.length + "条新邮件");
                        try {
                            save2Local(messages);
                            notifyNewEmail(messages);
                        } catch (MessagingException e1) {
                            e1.printStackTrace();
                        }
                    }
                    super.messagesAdded(e);
                }
            });
            boolean supportsIdle = false;
            if (folder instanceof IMAPFolder) {
                IMAPFolder f = (IMAPFolder) folder;
                f.idle();
                supportsIdle = true;
            }
            for (; ; ) {
                if (supportsIdle && folder instanceof IMAPFolder) {
                    IMAPFolder f = (IMAPFolder) folder;
                    f.idle();
                } else {
                    folder.getMessageCount();
                }
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return Result.success();
    }

    private void save2Local(Message[] messages) {
//        List<Email> data = new ArrayList<>();
        for (Message message : messages) {
            Email email = new Email();
            EmailRemoteDataSource.dumpPart(message, email);
            dataSource.save(email);
        }
    }

    private void notifyNewEmail(Message[] messages) throws MessagingException {
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder;
        for (Message message : messages) {
            //8.0通知栏适配
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                        "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
                chan1.setLightColor(Color.GREEN);
                chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
                manager.createNotificationChannel(chan1);
                mBuilder = new NotificationCompat.Builder(getApplicationContext(), PRIMARY_CHANNEL);
            } else {
                mBuilder = new NotificationCompat.Builder(getApplicationContext());
            }
            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                    | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0,
                    notificationIntent, 0);
            mBuilder.setContentTitle(((InternetAddress) message.getFrom()[0]).getAddress())//设置通知栏标题
                    .setContentText(message.getSubject())//设置通知栏内容
                    .setContentIntent(intent) //设置通知栏点击意图
                    .setTicker(message.getSubject()) //通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                    .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
                    .setSmallIcon(R.mipmap.ic_launcher);//设置通知小ICON
            Notification notify = mBuilder.build();
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            manager.notify(PUSH_ID, notify);
        }
    }
}
