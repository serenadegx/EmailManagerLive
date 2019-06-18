package com.example.emailmanagerlive.data;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.Observable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Configuration extends BaseObservable {
    @Id
    private long categoryId;
    private String name;

    private String receiveProtocol;
    private String receiveHostKey;
    private String receiveHostValue;
    private String receivePortKey;
    private String receivePortValue;
    private String receiveEncryptKey;
    private boolean receiveEncryptValue;

    private String sendProtocol;
    private String sendHostKey;
    private String sendHostValue;
    private String sendPortKey;
    private String sendPortValue;
    private String sendEncryptKey;
    private boolean sendEncryptValue;
    private String authKey;
    private boolean authValue;

    @Generated(hash = 121756130)
    public Configuration(long categoryId, String name, String receiveProtocol,
                         String receiveHostKey, String receiveHostValue, String receivePortKey,
                         String receivePortValue, String receiveEncryptKey,
                         boolean receiveEncryptValue, String sendProtocol, String sendHostKey,
                         String sendHostValue, String sendPortKey, String sendPortValue,
                         String sendEncryptKey, boolean sendEncryptValue, String authKey,
                         boolean authValue) {
        this.categoryId = categoryId;
        this.name = name;
        this.receiveProtocol = receiveProtocol;
        this.receiveHostKey = receiveHostKey;
        this.receiveHostValue = receiveHostValue;
        this.receivePortKey = receivePortKey;
        this.receivePortValue = receivePortValue;
        this.receiveEncryptKey = receiveEncryptKey;
        this.receiveEncryptValue = receiveEncryptValue;
        this.sendProtocol = sendProtocol;
        this.sendHostKey = sendHostKey;
        this.sendHostValue = sendHostValue;
        this.sendPortKey = sendPortKey;
        this.sendPortValue = sendPortValue;
        this.sendEncryptKey = sendEncryptKey;
        this.sendEncryptValue = sendEncryptValue;
        this.authKey = authKey;
        this.authValue = authValue;
    }

    @Generated(hash = 365253574)
    public Configuration() {
    }

    public long getCategoryId() {
        return categoryId;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public String getReceiveProtocol() {
        return receiveProtocol;
    }

    public String getReceiveHostKey() {
        return receiveHostKey;
    }

    public String getReceiveHostValue() {
        return receiveHostValue;
    }

    public String getReceivePortKey() {
        return receivePortKey;
    }

    public String getReceivePortValue() {
        return receivePortValue;
    }

    public String getReceiveEncryptKey() {
        return receiveEncryptKey;
    }

    public boolean isReceiveEncryptValue() {
        return receiveEncryptValue;
    }

    public String getSendProtocol() {
        return sendProtocol;
    }

    public String getSendHostKey() {
        return sendHostKey;
    }

    public String getSendHostValue() {
        return sendHostValue;
    }

    public String getSendPortKey() {
        return sendPortKey;
    }

    public String getSendPortValue() {
        return sendPortValue;
    }

    public String getSendEncryptKey() {
        return sendEncryptKey;
    }

    public boolean isSendEncryptValue() {
        return sendEncryptValue;
    }

    public String getAuthKey() {
        return authKey;
    }

    public boolean isAuthValue() {
        return authValue;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReceiveProtocol(String receiveProtocol) {
        this.receiveProtocol = receiveProtocol;
    }

    public void setReceiveHostKey(String receiveHostKey) {
        this.receiveHostKey = receiveHostKey;
    }

    public void setReceiveHostValue(String receiveHostValue) {
        this.receiveHostValue = receiveHostValue;
    }

    public void setReceivePortKey(String receivePortKey) {
        this.receivePortKey = receivePortKey;
    }

    public void setReceivePortValue(String receivePortValue) {
        this.receivePortValue = receivePortValue;
    }

    public void setReceiveEncryptKey(String receiveEncryptKey) {
        this.receiveEncryptKey = receiveEncryptKey;
    }

    public void setReceiveEncryptValue(boolean receiveEncryptValue) {
        this.receiveEncryptValue = receiveEncryptValue;
    }

    public void setSendProtocol(String sendProtocol) {
        this.sendProtocol = sendProtocol;
    }

    public void setSendHostKey(String sendHostKey) {
        this.sendHostKey = sendHostKey;
    }

    public void setSendHostValue(String sendHostValue) {
        this.sendHostValue = sendHostValue;
    }

    public void setSendPortKey(String sendPortKey) {
        this.sendPortKey = sendPortKey;
    }

    public void setSendPortValue(String sendPortValue) {
        this.sendPortValue = sendPortValue;
    }

    public void setSendEncryptKey(String sendEncryptKey) {
        this.sendEncryptKey = sendEncryptKey;
    }

    public void setSendEncryptValue(boolean sendEncryptValue) {
        this.sendEncryptValue = sendEncryptValue;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setAuthValue(boolean authValue) {
        this.authValue = authValue;
    }

    public boolean getReceiveEncryptValue() {
        return this.receiveEncryptValue;
    }

    public boolean getSendEncryptValue() {
        return this.sendEncryptValue;
    }

    public boolean getAuthValue() {
        return this.authValue;
    }
}
