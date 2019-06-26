package com.example.emailmanagerlive.send;

public interface SendEmailNavigator {
    void onSending(String msg);
    void onSent();
    void onSentError();
    void onSaved();
}
