package com.example.carrenting.FragmentPages.Owner.ui.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MessageViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public MessageViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("TIN NHẮN");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
