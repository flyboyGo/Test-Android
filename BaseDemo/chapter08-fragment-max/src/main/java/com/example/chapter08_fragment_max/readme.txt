

1、Fragment直观印象化
   (1): Fragment:片段、碎片。是一部分内容构成的片段,体现在屏幕上是一块内容区域

2、为什么要有Fragment?
   (1): 在Fragment之前，我们通常把一个Activity作为一个页面。
   (2): 但随着页面元素的增加以及场景的复杂，单个页面已经不能满足需要，在屏幕上通常要同时展示多个区域、多个页面内容，这些内容的切换通常是整体的。
   (3): 因此，为了让屏幕展示更多内容，以及对这些内容统一管理，引入了Fragment。


3.Fragment的详细介绍
   (1): Fragment，就是将一块内容区域封装在一起，统一管理，构成一个Fragment。
   (2): Fragment是依附在Activity上而存在的。一个Activity中可以有多个Fragment，各个Fragment之间可以传递数据、互相切换。
   (3): Fragment与Activity很相似，也有生命周期函数(onCreate、onPause、onDestroy等)
   (4): 如下是一个Fragment从开始到结束的生命周期流程
        onAttach --> onCreate --> onCreateView --> onViewCreated --> onActivityCreated
    --> onStart --> onResume --> onPause --> onStop --> onDestroyView --> onDestroy --> onDetach

4.Fragment的静态创建
    注意
    (1): fragment标签 必须声明android:id 或者 android:tag属性，否则会有错
         tools:layout="@layout/example_fragment"，可在预览视图中看到fragment预览界面

    (2): FragmentContainerView标签
         fragment标签内必须声明android:id属性(只有id,没有tag)，否则会有错

    (3): name属性也可以用class代替，即class="com.example.fragment.static_fragment"

5、Fragment的动态创建
    (1): 添加依赖
    (2): 创建一个Fragment
    (3): 布局代码中用一个容器承接，但不直接绑定
    (4): 代码中，使用FragmentManager、FragmentTransaction添加Fragment到容器中

    注意
     (4): 只有在FragmentActivity及其子类（如，AppCompatActivity)下才有getSupportFragmentManager这个方法。

     (5): 普通的Activity是没有该方法的，它有的是getFragmentManager。顾名思义，它获取的是非兼容的Fragment。
          而我们这里用的是AndroidX里面的，具有兼容性的Fragment,自然需要同样具有兼容性的FragmentManager，
          即需要用getSupportFragmentManager



6、两种方式创建Fragment的生命周期的区别
   (1): 静态创建
          Fragment: Construct、onInflate(注意)、onCreate、onCreateView、onViewCreated
          Activity: onCreate
          Fragment: onActivityCreated、onStart
          Activity: onStart、onResume
          Fragment: onResume

       销毁:
          Fragment: onPause
          Activity: onPause
          Fragment: onDestroyView、onDestroy
          Activity: onDestroy

   (2)动态创建
          Activity: onCreate
          Fragment: Construct、onCreate、onCreateView、cnViewCreated、onActivityCreated、onStart
          Activity: onStart、onResume
          Fragment: onResume

          销毁：
          Fragment: onPause
          Activity: onPause
          Fragment: onDestroyView、onDestroy
          Activity: onDestroy


7、Fragment的添加--add
          (1): 一个容器里可以添加多个Fragment，依次盖在上面。
          (2): 类比FrameLayout

           注意:由于事件分发机制，同时添加多个Fragment时，在空白处点击，会产生穿透的效果，也就是作用到下层的Fragment。
           解决方法:在Fragment里拦截onTouch事件。
           https://blog.csdn.net/alex440681/article/details/102240699
           因此，最好不要用add方式，而用replace添加fragment,保证容器中只有一个Fragment

8、Fragment的添加--addToBackStack

    (1): addToBackStack的作用: 将Fragment添加到容器 ,同时也添加到BackStack里。
    (2): 效果是:这时点击手机的back按钮，就会依次弹出BackStack中的Fragment，而不是直接退出Activity。
    (3): 不同名字的BacKStack,也是按添加的先后顺序依次盖在上面的(其实只有一个栈,先前添加的更像是fragment的名字)

9、Fragment的删除与查找(删除是以查找为前提)

   fragmentManager.findFragmentById(R.id.fragment)
   fragmentManager.findFragmentByTag("fragment")

   根据id/tag查找fragment，返回找到的最上面的一个
       (1): 如果没有，则从BackStack里找，并返回最先添加的一个
       (2): 如果没有BackStack，则返回null


   依次移除当前容器最上面的一个Fragment
   类比栈的退出
   fragmentTransaction.remove(fragment).commit();

   (1): 依次移除当前容器最上面的一个Fragment

   (2): 只是将其从容器中移除,
         backStack里仍然存在，也就是findFragmentById、findFragmentByTag依然能找到，只不过其状态是不可见(用户也不可见)，需要按返回键才能彻底移除(依次出栈)

10、Fragment的替换

   不带addToBackStack
   1.将当前容器上的Fragment全部移除
   2.添加新的Fragment

   带addToBackStack
    1、将当前容器上的Fragment全部移除
    2、添加新的Fragment

   BackStack里的Fragment仍然存在。
   按返回键依然会响应到BackStack，也就是弹出BackStack里的Fragment。
   而此时前台看到的是ReplaceFragment,所以看起来的效果是好像什么也没发生一样。

11、Fragment的显示、隐藏

    1、show、hide
    只是把Fragment显示/隐藏，Fragment生命周期不发生变化，相当于View的显示/隐藏。

    2、attach、detach
    把Fragment从容器中移除/装载，Fragment生命周期发生变化，执行到onDestroyView，其View被销毁，但Fragment仍存在。


    show/hide、attach/detach、add/remove 的比较
    操作              Fragment的变化
    Show/hide        仅隐藏View，什么都不销毁
    attach/detach    仅销毁View
    add/remove       销毁View和整个Fragment

    remove:会把Fragment移除，并且整个Fragment销毁，执行到onDestroy
    因此show/hide、attach/detach、add/remove是对Fragment影响依次增强的操作


12、数据的传递

    1、Fragment与Activity之间传递数据

       (1): Activity向Fragment
            通过构造方法
            通过public方法
            通过Argument
            通过接口(类似于观察者模式)

       (2): Fragment向Activity
            通过getActivity
              由于Activity是Fragment的承载者，每个Fragment都可以通过getActivity()方法获得承载它的Activity对象，
              从而调用这个Activity的方法向Activity传递数据。
            通过接口

    2、Fragment之间传递数据(ViewModel、FragmentResultListener)

       (1): 通过Activity中转
            由于Activity是各个Fragment的承载者，所以可以作为中间桥梁为各个Fragment转递数据。

            上面我们得知:
            在Fragment中可以通过getActivity()得到Activity对象。
            在Activity中可以通过findFragmentById或者findFragmentByTag找到指定的Fragment;或者Fragment作为Activity的成员变量直接使用。
            所以，一个Fragment先拿到其Activity对象，再通过这个Activity找到指定的Fragment对象，然后调用其方法，从而传递数据

       (2): 通过接口
















