package com.example.emailmanagerlive.data.source.remote;

import android.util.Log;

import androidx.core.app.NavUtils;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Attachment;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.EmailDataSource;
import com.sun.mail.smtp.SMTPTransport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

public class EmailRemoteDataSource implements EmailDataSource {
    private static EmailRemoteDataSource INSTANCE;
    static boolean showStructure = false;
    static boolean saveAttachments = false;
    static int level = 0;

    private EmailRemoteDataSource() {
    }

    public static EmailRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EmailRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getEmails(final Account account, GetEmailsCallBack callBack) {
        List<Email> data = new ArrayList<>();
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {

                Email emailDetail = new Email();
                //仅支持imap
                emailDetail.setRead(message.getFlags().contains(Flags.Flag.SEEN));
                dumpPart(message, emailDetail);
//                dumpEnvelope(message, emailDetail);
                data.add(emailDetail);
            }
            //排序
        } catch (NoSuchProviderException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (Exception e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        callBack.onEmailsLoaded(data);
    }

    public void getSentEmails(final Account account, GetEmailsCallBack callBack) {
        List<Email> data = new ArrayList<>();
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("Sent Messages");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {

                Email emailDetail = new Email();
//                dumpPart(message, emailDetail);
                dumpEnvelope(message, emailDetail);
                data.add(emailDetail);
            }
            //排序
        } catch (NoSuchProviderException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (Exception e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        callBack.onEmailsLoaded(data);
    }

    public void getDrafts(final Account account, GetEmailsCallBack callBack) {
        List<Email> data = new ArrayList<>();
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("Drafts");
            inbox.open(Folder.READ_ONLY);
            Message[] messages = inbox.getMessages();
            for (Message message : messages) {

                Email emailDetail = new Email();
//                dumpPart(message, emailDetail);
                dumpEnvelope(message, emailDetail);
                data.add(emailDetail);
            }
            //排序
        } catch (NoSuchProviderException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } catch (Exception e) {
            callBack.onDataNotAvailable();
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        callBack.onEmailsLoaded(data);
    }

    @Override
    public void getEmail(final Account account, long id, GetEmailCallBack callBack) {
        showStructure = true;
        Email data;
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message message = inbox.getMessage((int) id);
            //标记已读
            message.setFlag(Flags.Flag.SEEN, true);
            data = new Email();
            data.setAttachments(new ArrayList<Attachment>());
            dumpPart(message, data);
            showStructure = false;
            callBack.onEmailLoaded(data);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void getSentEmail(final Account account, long id, GetEmailCallBack callBack) {
        showStructure = true;
        Email data;
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("Sent Messages");
            inbox.open(Folder.READ_ONLY);
            Message message = inbox.getMessage((int) id);
            data = new Email();
            data.setAttachments(new ArrayList<Attachment>());
            dumpPart(message, data);
            showStructure = false;
            callBack.onEmailLoaded(data);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void getDraft(final Account account, long id, GetEmailCallBack callBack) {
        showStructure = true;
        Email data;
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("Drafts");
            inbox.open(Folder.READ_ONLY);
            Message message = inbox.getMessage((int) id);
            data = new Email();
            data.setAttachments(new ArrayList<Attachment>());
            dumpPart(message, data);
            showStructure = false;
            callBack.onEmailLoaded(data);
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onDataNotAvailable();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(final Account account, Email email, CallBack callBack) {
        Properties props = System.getProperties();
        props.put(account.getConfig().getSendHostKey(), account.getConfig().getSendHostValue());
        props.put(account.getConfig().getSendPortKey(), account.getConfig().getSendPortValue());
        props.put(account.getConfig().getSendEncryptKey(), account.getConfig().getSendEncryptValue());
        props.put(account.getConfig().getAuthKey(), account.getConfig().getAuthValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        SMTPTransport t = null;
        try {
            Message msg = new MimeMessage(session);
            if (email.getFrom() != null) {
                try {
                    msg.setFrom(new InternetAddress(email.getFrom(), "serenade"));
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email.getTo(), false));
            if (email.getCc() != null)
                //抄送人
                msg.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(email.getCc(), false));
            if (email.getBcc() != null)
                //秘密抄送人
                msg.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(email.getBcc(), false));

            msg.setSubject(email.getSubject());

            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(email.getContent());
            mp.addBodyPart(mbp1);
            if (email.getAttachments() != null && email.getAttachments().size() > 0) {
                for (Attachment detail1 : email.getAttachments()) {
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    mbp2.attachFile(detail1.getPath());
                    mp.addBodyPart(mbp2);
                }
            }
            msg.setContent(mp);
            msg.setSentDate(new Date());
            t = (SMTPTransport) session.getTransport(account.getConfig().getSendProtocol());
            t.connect();
            t.sendMessage(msg, msg.getAllRecipients());
            callBack.onSuccess();
        } catch (SendFailedException e) {
            e.printStackTrace();
            callBack.onError();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (t != null)
                    t.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void reply(final Account account, Email email, CallBack callBack) {
        Properties props = System.getProperties();
        props.put(account.getConfig().getSendHostKey(), account.getConfig().getSendHostValue());
        props.put(account.getConfig().getSendPortKey(), account.getConfig().getSendPortValue());
        props.put(account.getConfig().getSendEncryptKey(), account.getConfig().getSendEncryptValue());
        props.put(account.getConfig().getAuthKey(), account.getConfig().getAuthValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        Folder folder = null;
        Store store = null;
        SMTPTransport t = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            folder = store.getFolder("inbox");
            folder.open(Folder.READ_ONLY);
            Message message = folder.getMessage((int) email.getId().longValue());
            Message forward = new MimeMessage(session);
            if (email.getFrom() != null) {
                forward.setFrom(new InternetAddress(email.getFrom(), "serenade"));
            }

            forward.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email.getTo(), false));
            if (email.getCc() != null)
                //抄送人
                forward.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(email.getCc(), false));
            if (email.getBcc() != null)
                //秘密抄送人
                forward.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(email.getBcc(), false));

            forward.setSubject(email.getSubject());

            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setDataHandler(collect(email, message));
            mp.addBodyPart(mbp1);
            if (email.getAttachments() != null && email.getAttachments().size() > 0) {
                for (Attachment detail1 : email.getAttachments()) {
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    mbp2.attachFile(detail1.getPath());
                    mp.addBodyPart(mbp2);
                }
            }
            forward.setContent(mp);
            forward.saveChanges();
            forward.setSentDate(new Date());
            t = (SMTPTransport) session.getTransport(account.getConfig().getSendProtocol());
            t.connect();
            t.sendMessage(forward, forward.getAllRecipients());
            callBack.onSuccess();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onError();
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            callBack.onError();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (t != null)
                    t.close();
                if (folder != null)
                    folder.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void forward(Account account, Email email, CallBack callBack) {

    }

    @Override
    public void delete(final Account account, long id, CallBack callBack) {
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);
            Message message = inbox.getMessage((int) id);
            message.setFlag(Flags.Flag.DELETED, true);
            callBack.onSuccess();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onError();
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteByType(final Account account, long id, int type, CallBack callBack) {
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            if (type == 2) {
                inbox = store.getFolder("Sent Messages");
            } else {
                inbox = store.getFolder("Drafts");
            }
            inbox.open(Folder.READ_WRITE);
            Message message = inbox.getMessage((int) id);
            message.setFlag(Flags.Flag.DELETED, true);
            callBack.onSuccess();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            callBack.onError();
            e.printStackTrace();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void save2Drafts(final Account account, Email data, CallBack callBack) {
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
        session.setDebug(true);
        MimeMessage msg = new MimeMessage(session);
        Folder drafts = null;
        Store store = null;
        try {
            //打开草稿箱
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            drafts = store.getFolder("Drafts");

            if (data.getFrom() != null) {
                msg.setFrom(new InternetAddress(data.getFrom(), "果心蕊菜牙xr"));
            }
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(data.getTo(), false));
            if (data.getCc() != null)
                //抄送人
                msg.setRecipients(Message.RecipientType.CC,
                        InternetAddress.parse(data.getCc(), false));
            if (data.getBcc() != null)
                //秘密抄送人
                msg.setRecipients(Message.RecipientType.BCC,
                        InternetAddress.parse(data.getBcc(), false));

            msg.setSubject(data.getSubject());

            MimeMultipart mp = new MimeMultipart();
            MimeBodyPart mbp1 = new MimeBodyPart();
            mbp1.setText(data.getContent());
            mp.addBodyPart(mbp1);
            if (data.getAttachments() != null && data.getAttachments().size() > 0) {
                for (Attachment detail1 : data.getAttachments()) {
                    MimeBodyPart mbp2 = new MimeBodyPart();
                    mbp2.attachFile(detail1.getPath());
                    mp.addBodyPart(mbp2);
                }
            }
            msg.setContent(mp);
            msg.setSentDate(new Date());

            //保存到草稿箱
            msg.saveChanges();
            msg.setFlag(Flags.Flag.DRAFT, true);
            MimeMessage[] draftMessages = {msg};
            drafts.appendMessages(draftMessages);
            callBack.onSuccess();
        } catch (MessagingException e) {
            e.printStackTrace();
            callBack.onError();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
//                if (drafts!=null)
//                    drafts.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    public void downloadAttachment(final Account account, File file, long id, int index, long total, DownloadCallback callback) {
        List<InputStream> data = new ArrayList<>();
        Properties props = System.getProperties();
        props.put(account.getConfig().getReceiveHostKey(), account.getConfig().getReceiveHostValue());
        props.put(account.getConfig().getReceivePortKey(), account.getConfig().getReceivePortValue());
        props.put(account.getConfig().getReceiveEncryptKey(), account.getConfig().getReceiveEncryptValue());
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        account.getAccount(), account.getPwd());
            }
        });
//        session.setDebug(true);
        Store store = null;
        Folder inbox = null;
        try {
            store = session.getStore(account.getConfig().getReceiveProtocol());
            store.connect();
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            Message message = inbox.getMessage((int) id);
            download(message, data);
            if (data.size() >= index) {
                realDownload(file, total, data.get(index), callback);
            } else {
                callback.onError();
            }
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            callback.onError();
        } catch (Exception e) {
            e.printStackTrace();
            callback.onError();
        } finally {
            try {
                if (inbox != null)
                    inbox.close();
                if (store != null)
                    store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private static DataHandler collect(Email data, Message msg) throws MessagingException, IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(data.getAppend() + "<br>");
        sb.append("<div style=\"line-height:1.5\"><br><br>-------- 原始邮件 --------<br>");
        sb.append("主题：" + msg.getSubject() + "<br>");
        sb.append("发件人：" + ((InternetAddress) msg.getFrom()[0]).getAddress() + "<br>");

        if (msg.getRecipients(Message.RecipientType.TO) != null) {
            sb.append("收件人：");
            for (Address recipient : msg.getRecipients(Message.RecipientType.TO)) {
                sb.append(((InternetAddress) recipient).getAddress() + ";");
            }
            sb.append("<br>");
        }

        if (msg.getRecipients(Message.RecipientType.CC) != null) {
            sb.append("抄送：");
            for (Address recipient : msg.getRecipients(Message.RecipientType.CC)) {
                sb.append(((InternetAddress) recipient).getAddress() + ";");
            }
            sb.append("<br>");
        }

        if (msg.getRecipients(Message.RecipientType.BCC) != null) {
            sb.append("密送：");
            for (Address recipient : msg.getRecipients(Message.RecipientType.BCC)) {
                sb.append(((InternetAddress) recipient).getAddress() + ";");
            }
            sb.append("<br>");
        }
        sb.append("发件时间：" + dateFormat(msg.getReceivedDate()) + "<br>");
        sb.append("</div><br>");
        sb.append(data.getContent());
        return new DataHandler(
                new ByteArrayDataSource(sb.toString(), "text/html"));
    }

    private static void realDownload(File file, long total, InputStream is, DownloadCallback callback) {
        FileOutputStream fos = null;
        int len;
        long sum = 0;
        byte[] bys = new byte[1024 * 2];
        try {
            fos = new FileOutputStream(file);
            while ((len = is.read(bys)) != -1) {
                sum += len;
                fos.write(bys, 0, len);
                android.os.Message message = android.os.Message.obtain();
                callback.onProgress(sum * 1.0f / total);
                Log.i("mango", "percent:" + sum * 1.0f / total);
            }
            fos.flush();
            callback.onFinish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callback.onError();
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError();
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (is != null)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void download(Part p, List<InputStream> data) {
        try {
            if (p.isMimeType("multipart/*")) {
//            This is a Multipart
                Multipart mp = (Multipart) p.getContent();
                level++;
                int count = mp.getCount();
                for (int i = 0; i < count; i++)
                    download(mp.getBodyPart(i), data);
                level--;
            } else if (p.isMimeType("message/rfc822")) {
//            This is a Nested Message
                level++;
                download((Part) p.getContent(), data);
                level--;
            }
            if (level != 0 && p instanceof MimeBodyPart &&
                    !p.isMimeType("multipart/*")) {
                String disp = p.getDisposition();
                // many mailers don't include a Content-Disposition
                if (disp != null && disp.equalsIgnoreCase(Part.ATTACHMENT)) {
                    String filename = p.getFileName();
                    if (filename != null) {
                        //原生下载附件代码无进度监听
                        data.add(p.getInputStream());
//                        ((MimeBodyPart) p).saveFile(file);
                    }
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dumpPart(Part p, Email data) {
        try {
            if (p instanceof Message) {
                dumpEnvelope((Message) p, data);
                Log.i("mango", ((Message) p).getMessageNumber() + "    MimeType:" + p.getContentType());
            }
            if (p.isMimeType("text/plain")) {
//            This is plain text
                data.setContent((String) p.getContent());
//            System.out.println((String) p.getContent());
            } else if (p.isMimeType("multipart/*")) {
//            This is a Multipart
                Multipart mp = (Multipart) p.getContent();
                level++;
                int count = mp.getCount();
                for (int i = 0; i < count; i++)
                    dumpPart(mp.getBodyPart(i), data);
                level--;
            } else if (p.isMimeType("message/rfc822")) {
//            This is a Nested Message
                level++;
                dumpPart((Part) p.getContent(), data);
                level--;
            } else {
                if (showStructure) {
                    /*
                     * If we actually want to see the data, and it's not a
                     * MIME type we know, fetch it and check its Java type.
                     */
                    Object o = p.getContent();
                    if (o instanceof String) {
//                    This is a string
                        System.out.println((String) o);
                        data.setContent((String) p.getContent());
                    } else if (o instanceof InputStream) {
//                    This is just an input stream
                        InputStream is = (InputStream) o;
                    } else {
//                    "This is an unknown type"
                    }
                } else {
                    // other
                }
            }
            if (level != 0 && p instanceof MimeBodyPart &&
                    !p.isMimeType("multipart/*")) {
                String disp = p.getDisposition();
                // many mailers don't include a Content-Disposition
                if (disp != null && disp.equalsIgnoreCase(Part.ATTACHMENT)) {
                    String filename = p.getFileName();
                    if (filename != null) {
                        data.setHasAttach(true);
                        if (showStructure) {
                            data.getAttachments().add(new Attachment(MimeUtility.decodeText(filename), getPrintSize(p.getSize()), p.getSize()));
                        }
                    }
                }
            }
        } catch (MessagingException e) {
            try {
                data.setContent((String) p.getContent());
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (MessagingException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void dumpEnvelope(Message message, Email data) throws MessagingException, UnsupportedEncodingException {
        data.setId((long) message.getMessageNumber());
        Address[] recipients = message.getRecipients(Message.RecipientType.TO);
        if (recipients != null) {
            StringBuffer sb = new StringBuffer();
            for (Address recipient : recipients) {
                sb.append(((InternetAddress) recipient).getAddress() + ";");
            }
            data.setTo(sb.toString());
        }
        Address[] ccs = message.getRecipients(Message.RecipientType.CC);
        if (ccs != null) {
            StringBuffer sbCc = new StringBuffer();
            for (Address recipient : ccs) {
                sbCc.append(((InternetAddress) recipient).getAddress() + ";");
            }
            data.setCc(sbCc.toString());
        }
        Address[] bccs = message.getRecipients(Message.RecipientType.BCC);
        if (bccs != null) {
            StringBuffer sbBcc = new StringBuffer();
            for (Address recipient : bccs) {
                sbBcc.append(((InternetAddress) recipient).getAddress() + ";");
            }
            data.setBcc(sbBcc.toString());
        }
        InternetAddress address = (InternetAddress) message.getFrom()[0];
        data.setFrom(address.getAddress());
        data.setPersonal(address.getPersonal());
        data.setSubject(MimeUtility.decodeText(message.getHeader("subject")[0]));
        data.setDate(dateFormat(message.getReceivedDate()));
    }

    private static String getPrintSize(long size) {

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

    private static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }
}
