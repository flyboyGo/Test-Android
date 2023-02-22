
ViewModel的诞生
   1、瞬态数据丢失
   2、异步调用的内存泄漏
   3、类膨胀提高维护难度和测试难度

ViewModel的作用
  1、它是介于View（视图）和Model（数据模型）之间的桥梁
  2、使视图和数据能够分离，也能保持通信

  Model  <---->  ViewModel  <----->  View <-----> 用户


ViewModel的应用
  1、屏幕旋转之后用户操作数据仍然存在

ViewModel的生命周期特性
  1、独立于配置变化

AndroidViewModel
  1、不要向ViewModel中传入Context，会导致内存泄漏
  2、如果要使用Context，请使用AndroidViewModel(继承此类)中的Application(Application继承与context)

  public class MyViewModel extends AndroidViewModel {

      public MyViewModel(@NonNull Application application) {
          super(application);
      }
  }
