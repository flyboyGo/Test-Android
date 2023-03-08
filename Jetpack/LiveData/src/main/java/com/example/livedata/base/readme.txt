
LiveData与ViewModel的关系
   1、在ViewModel中的数据发生变化时通知页面
    Model <------>  ViewModel <------> View <------>  用户
                       |                  ^
                       |                  |
                       |----->LiveData----|


LiveData的优势
 1、确保界面符合数据状态
 2、不会发生内存泄漏
 3、不会因Activity停止而导致崩溃,不再需要手动处理生命周期
 4、数据始终保持最新状态
 5、适当的配置更改
 6、共享资源

  postValue 既可以在子线程中调用,主线程中也可以
  setValue 只能在主线程中调用

  二者最后在回调中都是在主线程,postValue()在源码中还是通过Handler切换到主线程中去的

