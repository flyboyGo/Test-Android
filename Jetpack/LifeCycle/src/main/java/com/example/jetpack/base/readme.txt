
LifeCycle的诞生
   解耦的问题
     activity等系统组件,包含许多生命周期的方法,普通组件(textView等)的正确使用必须高度依赖与其的生命周期方法，导致耦合度非常的高


LifeCycle应用
 1、使用Lifecycle解耦页面与组件
 2、使用LifecycleService解耦Service与组件
 3、使用ProcessLifecycleOwner监听应用程序生命周期

     (1): 针对整个应用程序的监听，与Activity数量无关。

     (2): Lifecycle.Event.ON_CREATE只会被调用一次，Lifecycle.Event.ON_DESTROY永远不会被调用。

LifeCycle的好处
 1、帮助开发者建立可感知生命周期的组件
 2、组件在其内部管理自己的生命周期，从而降低模块耦合度
 3、降低内存泄漏发生的可能性
 4、Activity、Fragment(自我补充)、Service、Application均有LifeCycle支持
