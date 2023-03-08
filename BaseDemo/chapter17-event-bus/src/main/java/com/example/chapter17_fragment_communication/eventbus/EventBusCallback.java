package com.example.chapter17_fragment_communication.eventbus;

public interface EventBusCallback<T> {
    void onSuccess(T event);
    void onFailure(Throwable throwable);
}
