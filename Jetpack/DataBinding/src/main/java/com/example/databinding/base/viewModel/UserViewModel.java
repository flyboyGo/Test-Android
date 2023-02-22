package com.example.databinding.base.viewModel;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.databinding.BR;
import com.example.databinding.base.enity.User;

public class UserViewModel extends BaseObservable {

    private User user;

    public UserViewModel(){
        this.user = new User("Jack");
    }

    @Bindable
    public String getUserName()
    {
        return user.username;
    }

    public void setUserName(String userName)
    {
        if(userName != null && !userName.equals(user.username))
        {
            user.username = userName;
            Log.d("test","set username : " + userName);
            notifyPropertyChanged(BR.userName);
        }
    }
}
