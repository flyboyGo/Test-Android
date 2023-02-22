package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MergeOperatorDemo {

    @Test
    public void test1()
    {
        System.out.println("============");
        concatTest();
        concatArrayTest();
        System.out.println("============");
    }

    Observer<Object> observer  = new Observer<Object>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe ... ");
        }

        @Override
        public void onNext(@NonNull Object o) {
            System.out.println("onNext ... " + o.toString());
        }

        @Override
        public void onError(@NonNull Throwable e) {
            System.out.println("onError ... " + e.getMessage());
        }

        @Override
        public void onComplete() {
            System.out.println("onComplete ... ");
        }
    };

    private void concatTest(){
        Observable.concat(Observable.just("first"),
                          Observable.just("second"))
                .subscribe(observer);

    }
    private void concatArrayTest(){
        Observable.concatArray(Observable.just("first"),
                Observable.just("second"),
                Observable.just("third"),
                Observable.just("fourth"),
                Observable.just("five"))
                .subscribe(observer);

    }
}
