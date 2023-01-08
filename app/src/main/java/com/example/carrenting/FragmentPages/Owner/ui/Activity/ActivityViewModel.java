package com.example.carrenting.FragmentPages.Owner.ui.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ActivityViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ActivityViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("HOẠT ĐỘNG");
    }

    public LiveData<String> getText() {
        return mText;
    }
}