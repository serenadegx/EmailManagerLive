package com.example.emailmanagerlive.send;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.Event;
import com.example.emailmanagerlive.data.Attachment;

import java.util.List;

public class SendEmailViewModel extends ViewModel {
    public final MutableLiveData<List<Attachment>> items = new MutableLiveData<>();
    public final MutableLiveData<String> receiver = new MutableLiveData<>();
    public final MutableLiveData<String> copy = new MutableLiveData<>();
    public final MutableLiveData<String> secret = new MutableLiveData<>();
    public final MutableLiveData<String> send = new MutableLiveData<>();
    public final MutableLiveData<String> subject = new MutableLiveData<>();
    public final MutableLiveData<String> content = new MutableLiveData<>();
    public final MutableLiveData<Event<String>> snackBarText = new MutableLiveData<>();
}
