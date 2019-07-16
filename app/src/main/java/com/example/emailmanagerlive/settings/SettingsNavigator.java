package com.example.emailmanagerlive.settings;

import com.example.emailmanagerlive.data.Account;

public interface SettingsNavigator {
    void onModifySuccess(Account account);

    void editSignature();

    void editSignatureSuccess();

    void editPersonal();

    void editPersonalSuccess();
}
