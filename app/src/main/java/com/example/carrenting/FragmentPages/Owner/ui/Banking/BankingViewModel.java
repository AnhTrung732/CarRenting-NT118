package com.example.carrenting.FragmentPages.Owner.ui.Banking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BankingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public BankingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("TÊN TÀI KHOẢN");
    }

    public LiveData<String> getText() {
        return mText;
    }
}