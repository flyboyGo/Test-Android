package com.example.rxjava;

import androidx.annotation.NonNull;

import org.junit.Test;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ToolOperatorDemo {

    @Test
    public void test(){
        System.out.println("============");
        subscribeTest();
        System.out.println("============");
    }

    private void subscribeTest(){
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Object> emitter) throws Exception {

                System.out.println("subscribe ... " + Thread.currentThread());

                // 创建事件,并发射事件
                // 模拟网络请求，必须在子线程中执行
                Thread.sleep(2000);
                emitter.onNext("first");
                emitter.onNext("second");
                emitter.onComplete();
            }
        })
                .doOnNext(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        System.out.println("before onNext " + Thread.currentThread() + " == " + o.toString());
                    }
                })
                .subscribeOn(Schedulers.newThread()) // 线程切换(新的线程,Android中就是子线程),主要决定我执行subscribe方法所处于的线程，也就是产生事件发射所在的线程
                .observeOn(Schedulers.newThread()) // 下游执行程序的线程切换(新的线程,Android中就是主线程,AndroidSchedulers.mainThread())
                .map(new Function<Object, Object>() {
                    @Override
                    public Object apply(@NonNull Object o) throws Exception {
                        System.out.println("apply ... " + Thread.currentThread());
                        return o.toString() + " deal with";
                    }
                })
                .observeOn(Schedulers.io()) // 线程池可以出现线程复用
                .subscribe(new Observer<Object>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe ... " + Thread.currentThread());
            }

            @Override
            public void onNext(@NonNull Object o) {
                System.out.println("onNext ... " + Thread.currentThread() + " === " + o.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError ... " + Thread.currentThread());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete ... " + Thread.currentThread());
            }
        });

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
