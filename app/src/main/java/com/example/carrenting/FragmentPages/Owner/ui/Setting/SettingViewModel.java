package com.example.carrenting.FragmentPages.Owner.ui.Setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SettingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("QUẢN LÝ XE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
