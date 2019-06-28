package com.example.emailmanagerlive.send;

public interface SendEmailNavigator {
    void onSending(String msg);
    void onSent();
    void onSaving(String msg);
    void onSaved();
    void onError();
}
