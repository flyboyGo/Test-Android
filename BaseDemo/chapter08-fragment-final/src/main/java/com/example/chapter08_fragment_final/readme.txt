
    1、BottomNavigationView

(1): app:menu声明导航按钮，它的值是一个menu文件
(2): app:labelVisibilityMode导航按钮的显示模式，取值有auto、selected、labeled、unlabeled
     labeled :几个导航按钮全部显示文字
     unlabeled :几个导航按钮全部不显示文字
     selected:只有选中的按钮显示文字
     auto:小于等于3个按钮时全部显示文字，即labeled;大于3个按钮时只有选中的按钮显示文字，即selected

    2、ViewPager与BottomNavigationView的联动
       (1): ViewPager联动BottomNavigationView
            mViewPager.addOnPageChangeListener(...)
            mBottomNavigationView.setSelectedItemId(R.id.menu_xxx);

       (2): BottomNavigationView联动ViewPager
            mBottomNavigationView.setOnItemSelectedListener(...)
            mViewPager.setCurrentItem(index)


    3、TabLayout
            <com.google.android.material.tabs.TabLayout
              android:id="@+id/home_tab_layout"
              android:layout_width="match_parent"
              android:layout_height="wrap_content"
              app:tabGravity="start"“
              app:tabMode="auto" />

            app:tabMode设置tab的模式的，有这几个取值
               fixed:固定的，也就是标题不可滑动，有多少展示多少，平均分配长度
               scrollable:可滑动的，小于等于5个默认靠左固定，大于五个tab后就可以滑动
               auto:自动选择是否可滑动，小于等于5个默认居中固定，大于5个自动滑动app:tabGravity设置tab的位置
               start:居左fill:平均分配，铺满屏幕宽度center:居中

            app:tabGravity 设置tab的位置
               start:居左
               fill:平均分配，铺满屏幕宽度
               center:居中

    4、NavigationView的用法
            (1): app:headerLayout="@layout/drawer_header_layout"
                  侧滑菜单的头部部分，也就是常见的会有一个头像昵称之类的布局。

            (2): app:menu="@menu/drawer_nav_menu"
                  侧滑菜单的菜单项部分，只需要用menu文件就可以定义出菜单项了。


    5、Fragment基础-总结
             (1): Fragment直观认识
                  屏幕上多个View统一管理的集合体
             (2): Fragment创建
                  静态、动态
             (3): Fragment增删替查
                  用命令行查看Fragment"adb shell dumpsys activity fragment"
             (4): Fragment应用
                  实现基本底部导航
                  Fragment+DrawerLayout+NavigationView实现侧滑菜单导航结合Viewpager使用
                  Fragment+ViewPager实现页面底部导航
                  Fragment+ViewPager+BottomNavigationView实现页面底部导航
                  Fragment+ViewPager+TabLayout+BottomNavigationView实现页面底部导航




