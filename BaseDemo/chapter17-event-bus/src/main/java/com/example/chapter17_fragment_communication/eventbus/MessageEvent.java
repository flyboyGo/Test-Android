package com.example.chapter17_fragment_communication.eventbus;

import android.os.Message;

public class MessageEvent {

    private String message = null;

    public MessageEvent() {
    }

    public MessageEvent(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
