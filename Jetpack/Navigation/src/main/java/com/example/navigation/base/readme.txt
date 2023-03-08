
Navigation的诞生
  1、Activity嵌套多个Fragment的UI架构模式已经非常普遍，但是对Fragment的管理一直是一件比较麻烦的事情。
    我们需要通过FragmentManager和FragmentTransaction来管理Fragment之间的切换。
    页面的切换通常还包括对应用程序App bar的管理、Fragment间的切换动画，以及Fragment间的参数传递。
    纯代码的方式使用起来不是特别友好，并且Fragment和App bar在管理和使用的过程中显得混乱。
  2、为此，Jetpack提供了Navigation组件，旨在方便我们 "管理页面" 和 "AppBar"。


Navigation的优势
  1、可视化的页面导航图，类似于Apple Xcode中的StoryBoard，便于我们理清页面关系。
  2、通过destination和action完成页面间的导航。
  3、方便添加页面切换动画。
  4、页面间类型安全的参数传递。
  5、通过NavigationUl，对菜单、底部导航、抽屉菜单导航进行统一的管理。
  6、支持深层链接DeepLink。

Navigation的主要元素
  1、Navigation Graph，一种新的XML资源文件，包含应用程序所有的页面，以及页面间的关系。
  2、NavHostFragment，一个特殊的Fragment，可以将它看作是其他Fragment的容器，Navigation Graph中的Fragment正是通过NavHostFragment进行展示的。
  3、NavController，用于在代码中完成Navigation Graph中具体的页面切换工作。
  4、他们三责之间的关系
     当你想切换Fragment时，使用NavController对象，告诉它你想要去Navigation Graph中的哪个Fragment,
     NavController会将你想去的Fragment展示NavHostFragment中。


Navigation应用
 1、创建NavHostFragment
 2、创建Fragment
 3、创建Navigation Graph，指定destination
 4、完成Fragment页面切换
 5、使用NavController完成导航
 6、添加页面切换动画效果
 7、普通方式与safe args插件方式参数传递
    (项目添加依赖:classpath "androidx.navigation:navigation-safe-args-gradle-plugin:2.3.0-alpha06"
     模块添加插件的引用: id 'androidx.navigation.safeargs')

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
    2、URL方式
       当用户通过手机浏览器浏览网站上某个页面时，可以在网页上放置一个类似于"在应用内打开" 的按钮，如果用户的手机安装有我们的App,
       那么通过DeepLink就能打开相应的页面;如果没有安装，那么网站可以导航到应用程序的下载页面，引导用户安装应用程序。
       adb shell am start -a android.intent.action.VIEW -d "http://www.dongnaoedu.com/fromWeb"




