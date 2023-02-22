package com.example.databinding.base.viewModel;

import android.util.Log;

import androidx.databinding.ObservableField;
import com.example.databinding.base.enity.User;


public class UserViewModelField {

    private ObservableField<User> userObservableField;

    public UserViewModelField()
    {
        User user = new User("Jack");
        userObservableField = new ObservableField<>();
        userObservableField.set(user);
    }

    public String getUserName()
    {
        return userObservableField.get().username;
    }

    public void setUserName(String userName)
    {
        Log.d("test","userObservableField : " + userName);
        userObservableField.get().username = userName;
    }

}
