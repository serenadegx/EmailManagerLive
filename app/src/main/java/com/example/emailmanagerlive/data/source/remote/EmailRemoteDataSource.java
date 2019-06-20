package com.example.emailmanagerlive.data.source.remote;

import com.example.emailmanagerlive.data.Account;
import com.example.emailmanagerlive.data.Email;
import com.example.emailmanagerlive.data.source.EmailDataSource;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

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
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;

public class EmailRemoteDataSource implements EmailDataSource {
    private static EmailRemoteDataSource INSTANCE;
    static boolean showStructure = false;
    static boolean saveAttachments = false;
    static int level = 0;
    static int attnum = 1;

    interface DownloadCallback {

        void onProgress(float percent);

        void onFinish();

        void onError();


    }

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
        session.setDebug(true);
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
    public void getEmail(Account account, long id, GetEmailCallBack callBack) {

    }

    public void send(Account account, Email email, CallBack callBack) {

    }

    public void reply(Account account, Email email, CallBack callBack) {

    }

    public void forward(Account account, Email email, CallBack callBack) {

    }

    @Override
    public void delete(Account account, long id, CallBack callBack) {

    }

    public void save2Drafts(Account account, Email data, CallBack callBack) {

    }

    public void downloadAttachment(Account account, long id, int index, DownloadCallback callback) {

    }

    public static String dateFormat(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public static void dumpPart(Part p, Email data) throws Exception {
        if (p instanceof Message)
            dumpEnvelope((Message) p, data);

        String filename = p.getFileName();
        if (filename != null)
            data.setHasAttach(true);

        /*
         * Using isMimeType to determine the content type avoids
         * fetching the actual content data until we need it.
         */
        if (p.isMimeType("text/plain")) {
//            This is plain text
            data.setContent((String) p.getContent());
            System.out.println((String) p.getContent());
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
            if (!showStructure && !saveAttachments) {
                /*
                 * If we actually want to see the data, and it's not a
                 * MIME type we know, fetch it and check its Java type.
                 */
                Object o = p.getContent();
                if (o instanceof String) {
//                    This is a string
                    System.out.println((String) o);
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

        /*
         * If we're saving attachments, write out anything that
         * looks like an attachment into an appropriately named
         * file.  Don't overwrite existing files to prevent
         * mistakes.
         */
        if (level != 0 && p instanceof MimeBodyPart &&
                !p.isMimeType("multipart/*")) {
            String disp = p.getDisposition();
            // many mailers don't include a Content-Disposition
            if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {
                if (filename == null)
                    filename = "Attachment" + attnum++;

            }
        }
    }

    private static void dumpEnvelope(Message message, Email data) throws MessagingException {
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
        data.setSubject(message.getSubject());
        data.setDate(dateFormat(message.getReceivedDate()));
    }


}
