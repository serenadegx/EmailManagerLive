package com.example.emailmanagerlive.emaildetail;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.data.Email;

import java.util.List;

public class EmailViewModel extends ViewModel {
    public final MutableLiveData<List<Email>> items = new MutableLiveData<>();
    public final MutableLiveData<String> title = new MutableLiveData<>();
    public final MutableLiveData<String> receivers = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isCc = new MutableLiveData<>();
    public final MutableLiveData<String> cc = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isBcc = new MutableLiveData<>();
    public final MutableLiveData<String> bcc = new MutableLiveData<>();
    public final MutableLiveData<String> subject = new MutableLiveData<>();
    public final MutableLiveData<String> date = new MutableLiveData<>();
    public final MutableLiveData<String> url = new MutableLiveData<>();


    public void reply(View view){

    }

    public void forward(View view){

    }

    public void delete(View view){

    }
}
