package com.example.livedata.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyFragmentViewModel extends ViewModel {

    private MutableLiveData<Integer> progress;

    public MutableLiveData<Integer> getProgress()
    {
        if(progress == null)
        {
            progress = new MutableLiveData<>();
            progress.setValue(0);
        }
        return progress;
    }
}
