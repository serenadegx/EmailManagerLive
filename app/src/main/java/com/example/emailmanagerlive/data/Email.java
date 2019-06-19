package com.example.emailmanagerlive.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Email extends BaseObservable {
    @Id
    private Long id;
    private boolean isRead;
    private String subject;
    private String date;
    private String from;
    private String personal;
    private String to;
    private String cc;
    private String bcc;
    private String content;
    private boolean hasAttach;
    @Transient
    public List<Attachment> attachments;

    @Generated(hash = 366761468)
    public Email(Long id, boolean isRead, String subject, String date, String from,
            String personal, String to, String cc, String bcc, String content,
            boolean hasAttach) {
        this.id = id;
        this.isRead = isRead;
        this.subject = subject;
        this.date = date;
        this.from = from;
        this.personal = personal;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.content = content;
        this.hasAttach = hasAttach;
    }

    @Generated(hash = 272676561)
    public Email() {
    }

    public Long getId() {
        return id;
    }

    @Bindable
    public boolean isRead() {
        return isRead;
    }

    @Bindable
    public String getSubject() {
        return subject;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    @Bindable
    public String getFrom() {
        return from;
    }

    @Bindable
    public String getPersonal() {
        return personal;
    }
    @Bindable
    public String getTo() {
        return to;
    }
    @Bindable
    public String getCc() {
        return cc;
    }
    @Bindable
    public String getBcc() {
        return bcc;
    }
    @Bindable
    public String getContent() {
        return content;
    }

    @Bindable
    public boolean isHasAttach() {
        return hasAttach;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getHasAttach() {
        return this.hasAttach;
    }
}
