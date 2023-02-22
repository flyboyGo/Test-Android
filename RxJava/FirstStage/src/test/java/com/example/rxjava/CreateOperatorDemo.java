package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class CreateOperatorDemo {

    @Test
    public void test1()
    {
        System.out.println("================");
        createObservableTest();
        System.out.println("================");
    }

    private void createObservableTest()
    {
        // 创建被观察者
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {
                // 事件产生的地方
                // 发送事件,触发观察者的onNext回调函数
                emitter.onNext("first");
                emitter.onNext("second");
                emitter.onNext("third");
                // 抛出异常,触发观察者的onError回调函数(与onComplete()回调函数是互斥的)
                emitter.onError(new Throwable("手动抛出异常"));
                // 事件发送完毕,触发观察者的onComplete回调函数
                // 如果在onError()之前执行onComplete(),onError()回调不会被执行,但异常仍然是会被抛出的,不会被捕获,处理
                emitter.onComplete();
            }
        }).subscribe(new Observer<Object>() { // 被观察则通过subscribe()添加观察者,触发观察者的onSubscribe回调函数(首先触发)

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe ... ");
            }

            @Override
            public void onNext(@NonNull Object o) {
                System.out.println("onNext ... " + o);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError ... " + e.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete ... ");
            }
        });
    }

    @Test
    public void test2()
    {
        System.out.println("================");
        createConsumerTest();
        System.out.println("================");
    }

    private void createConsumerTest(){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {
                // 事件产生的地方
                emitter.onNext("first");
                emitter.onNext("second");
                emitter.onNext("third");
                emitter.onError(new Throwable("手动抛出异常"));

                // 耗时操作
                // 网络请求
                // 异步操作
            }
        }).subscribe(new Consumer<Object>() { // 添加第一个消费者(特殊的观察者),负责接收消息
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("accept ... " + o);
            }
        }, new Consumer<Throwable>() { // 添加第二个消费者(特殊的观察者),负责捕获异常,并处理异常,不会再次抛出异常
            @Override
            public void accept(Throwable throwable) throws Exception {
                System.out.println("accept .. " + throwable.getMessage());
            }
        });
    }


    @Test
    public void test3()
    {
        System.out.println("================");
        createObservableJustTest();
        System.out.println("================");
    }

    // 创建观察者
    Observer<String> observer = new Observer<String>() {
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

    private void createObservableJustTest()
    {
        // 产生事件,just最多发送十个事件
        Observable.just("first","second","third")
                .subscribe(observer);// 订阅观察者
    }

    @Test
    public void test4()
    {
        System.out.println("================");
        createObservableFromTest();
        createObservableFromFutureTest();
        createObservableFromCallableTest();
        System.out.println("================");
    }

    private void createObservableFromTest()
    {
        // fromArray发送事件,可以发送无限制事件
        Observable.fromArray("first","second","third")
                .subscribe(observer);

        // fromArray发送事件,可以发送无限制事件
        ArrayList<String> list = new ArrayList<>();
        list.add("first");
        list.add("second");
        list.add("third");
        Observable.fromIterable(list)
                .subscribe(observer);
    }

    private void createObservableFromFutureTest()
    {
        Observable.fromFuture(new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws ExecutionException, InterruptedException {
                return "first";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws ExecutionException, InterruptedException, TimeoutException {
                return null;
            }
        }).subscribe(observer);
    }

    private void createObservableFromCallableTest()
    {
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "first";
            }
        }).subscribe(observer);
    }
}
