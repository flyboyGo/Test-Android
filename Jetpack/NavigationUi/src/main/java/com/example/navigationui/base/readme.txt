
NavigationUl的作用
     Fragment的切换，除了Fragment页面本身的切换，通常还伴有 "App bar" 的变化。为了方便统一管理，Navigation组件引入了 "NavigationUl类" 。


更多支持
 1、App bar
     ActionBar
     Toolbar
     CollapsingToolbarLayout
 2、menu
    抽屉菜单 (DrawLayout+ Navigation View)
    底部菜单(BottomNavigationView)


深层链接DeepLink
    1、PendingIntent方式
       当App收到某个通知推送，我们希望用户在点击该通知时，能够直接跳转到展示该通知内容的页面，可以通过PendingIntent来完成。

       Intent英文意思是意图，pending表示即将发生或来临的事情。
       PendingIntent这个类用于处理即将发生的事情。比如在通知Notification中用于跳转页面，但不是马上跳转。

       Intent 是及时启动，intent 随所在的activity 消失而消失。
       PendingIntent 可以看作是对intent的包装，通常通过getActivity,getBroadcast ,getService来得到pendingIntent的实例，
       当前activity并不能马上启动它所包含的intent,而是在外部执行 pendingIntent时，调用intent的。
       正由于pendingIntent中 保存有当前App的Context，使它赋予外部App一种能力，使得外部App可以如同当前App一样的执行pendingIntent里的 Intent，
       就算在执行时当前App已经不存在了，也能通过存在pendingIntent里的Context照样执行Intent。
       另外还可以处理intent执行后的操作。常和alarManager 和notificationManager一起使用。

       Intent一般是用作Activity、Service、BroadcastReceiver之间传递数据，
       而PendingIntent，一般用在 Notification上，可以理解为延迟执行的intent，PendingIntent是对Intent一个包装。

    2、URL方式
       当用户通过手机浏览器浏览网站上某个页面时，可以在网页上放置一个类似于"在应用内打开" 的按钮，如果用户的手机安装有我们的App,
       那么通过DeepLink就能打开相应的页面;如果没有安装，那么网站可以导航到应用程序的下载页面，引导用户安装应用程序。
       adb shell am start -a android.intent.action.VIEW -d "http://www.dongnaoedu.com/fromWeb"