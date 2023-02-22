package com.example.secondstage.core;

public interface Observer<T> {

    void onSubscribe();

    void onNext(T t);

    void onComplete();

    void onError(Throwable throwable);

}
