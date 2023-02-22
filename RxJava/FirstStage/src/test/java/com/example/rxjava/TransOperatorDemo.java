package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;

public class TransOperatorDemo {
    @Test
    public void test1()
    {
        System.out.println("==========");
        MapTest();
        System.out.println("==========");
    }

    Observer<String> observer = new Observer<String>() { // 订阅观察者
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe ... ");
        }

        @Override
        public void onNext(@NonNull String s) {
            System.out.println("onNext ... " + s);
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

    private void MapTest(){

        // map: 对被观察者进行处理，把原来的发射的事件进行处理并且产生新的事件,再次创建新的被观察者，再次发射事件
        Observable.just("first")
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {
                        System.out.println("function处理 : " + s);
                        return "second";
                    }
                }).subscribe(observer);
    }


    @Test
    public void test2()
    {
        System.out.println("==========");
        FlatMapTest();
        ConcatMapTest();
        BufferTest();
        System.out.println("==========");
    }

    Observer<Object> observerO  = new Observer<Object>() {
        @Override
        public void onSubscribe(@NonNull Disposable d) {
            System.out.println("onSubscribe ... " + d.toString());
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

    private void FlatMapTest(){

        // 网络嵌套请求的常用方式
        Observable.just("register")
                .flatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        System.out.println("function处理了 " + s);
                        return Observable.just("login");
                    }
                }).subscribe(observerO);
    }

    private void ConcatMapTest(){

        Observable.just("first"," second","third")
                .concatMap(new Function<String, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull String s) throws Exception {
                        System.out.println("function处理了 " + s);
                        return Observable.just(s + "deal with");
                    }
                }).subscribe(observerO);
    }

    private void BufferTest()
    {
        Observable.just("1","2","3","4","6","7","8","9","10")
                .buffer(3)
        .subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<String> strings) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
