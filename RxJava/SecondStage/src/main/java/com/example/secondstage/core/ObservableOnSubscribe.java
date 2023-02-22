package com.example.secondstage.core;

public interface ObservableOnSubscribe<T> {

    void subscribe(Emitter<T> emitter);
}
