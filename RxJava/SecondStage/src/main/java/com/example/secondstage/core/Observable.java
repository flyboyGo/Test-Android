package com.example.secondstage.core;

/**
 *  被观察者核心抽象类,也是是使用框架的接口
 */
public abstract class Observable<T> implements ObservableSource{
    @Override
    public void subscribe(Observer observer) {

        // 和谁建立订阅
        // 怎么建立订阅
        // 保证拓展性,交给具体的开发人员实现
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer observer);

    public static <T> Observable<T> create(ObservableOnSubscribe<T> subscribe){
        return new ObservableCreate<>(subscribe);
    }

}
