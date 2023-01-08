package com.example.carrenting.FragmentPages.Owner.ui.Vehicle;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VehicleViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public VehicleViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("QUẢN LÝ XE");
    }

    public LiveData<String> getText() {
        return mText;
    }
}