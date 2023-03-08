
配置参数
        数据绑定参数配置
        dataBinding{
            enabled = true
        }

DataBinding的意义
    1、让布局文件承担了部分原本属于页面的工作，使页面与行局耦合度进一步降低

DataBinding的优势
    1、不再需要findViewById，项目更加简洁，可读性更高
    2、布局文件可以包含简单的业务逻辑

    View
      ^
      |
  DataBinding
      |
      V
  ViewModel
      ^
      |
      V
    Model


ActivityFirstBinding由来
               activity_first(布局文件)
     Activity                          First
              ActivityFirstBinding


二级页面的绑定
    1、<include>标签引用二级页面


自定义BindingAdapter
    1、加载网络图片
    2、方法重载，加载本地图片
    3、多参数重载

双向绑定

    1、BaseObservable   与  ObservableField

                     单向绑定
    Field   ——字段变化，TextView自动更新——>   TextView

                     双向绑定
    Field   ——字段变化，EditText自动更新——>    EditText

            <——用户编辑EditText,字段自动同步——

DataBinding  +  ViewModel  +  LiveData
    篮球计分板





