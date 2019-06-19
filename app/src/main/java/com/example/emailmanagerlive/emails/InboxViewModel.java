package com.example.emailmanagerlive.emails;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.emailmanagerlive.data.Email;

import java.util.List;

public class InboxViewModel extends ViewModel implements EmailsViewModel {
    private final MutableLiveData<List<Email>> mItems = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mDataLoading = new MutableLiveData<>();
    public final LiveData<Boolean> mEmpty = Transformations.map(mItems,
            new Function<List<Email>, Boolean>() {
                @Override
                public Boolean apply(List<Email> input) {
                    return input.isEmpty();

                }
            });


    public LiveData<Boolean> isDataLoading() {
        return mDataLoading;
    }

    public LiveData<List<Email>> getItems() {
        return mItems;
    }

    @Override
    public void refresh() {

    }
}
