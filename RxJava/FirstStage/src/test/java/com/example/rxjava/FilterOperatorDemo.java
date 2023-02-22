package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;

public class FilterOperatorDemo {

    @Test
    public void test1()
    {
        System.out.println("==========");
        filterTest();
        System.out.println("==========");
    }

    Observer<Object> observer = new Observer<Object>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe ... ");
        }

        @Override
        public void onNext(@NonNull  Object o) {
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

    private void filterTest(){
        Observable.range(1, 10)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(@NonNull Integer integer) throws Exception {
                        return integer < 5;
                    }
                })
                .subscribe(observer);
    }
}
