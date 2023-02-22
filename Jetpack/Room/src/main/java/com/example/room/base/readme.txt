
Android官方ORM库Room
    Android采用SQLite作为数据库存储，开源社区常见的ORM (Object Relational Mapping）库有ORMLite、GreenDAO等。
    Room和其他库一样，也是在SQLite上提供了一层封装。

Room重要概念
  1、Entity:实体类，对应的是数据库的一张表结构，使用注解@Entity标记。
  2、Dao:包含访问一系列访问数据库的方法，使用注解@Dao标记。
  3、Database:数据库持有者,作为与应用持久化相关数据的底层连接的主要接入点。
    使用注解@Database标记，另外需满足以下条件:定义的类必须是一个继承于RoomDatabase的抽象类，在注解中需要定义与数据库相关联的实体类列表。
    包含一个没有参数的抽象方法并且返回一个Dao对象。


进一步优化
  1、问题:每当数据库数据发生变化时，都需要开启一个工作线程去重新获取数据库中的数据。
  2、解决:当数据发生变化时，通过LiveData通知View层，实现数据自动更新。


  Room + ViewModel + LiveData 结合使用

                   Activity/Fragment
                         |
                         V
                   ViewModel(LiveData)
                         |
                         V
                 ————Repository(DBEngine)————
                |                          |
                V                          V
            Model(Room)             Remote Data Source(Retrofit/OKHttp)
            SOLite                  WebService

  使用Migration升级数据库
   1、问题:如果用户设备上数据库版本为1，而当前要安装的App数据库版本为3，怎么办?
      (1): Room会先判断当前有没有直接从1到3的升级方案; 如果有,就直接执行从1到3的升级方案;
           如果没有,那么Room会按照顺序先后执行Migration(1,2)、Migration(2,3)以完成升级。
  异常处理
       假设我们将数据库版本升级到4，却没有为此写相应的Migration，则会出现一个illegalStateException异常,
       我们手动加入fallbackToDestructiveMigration(),该方法在出现升级异常时，重建数据表，同时数据也会丢失。

  Schema文件
     exportSchema = true(默认是导出的,是true)
     Room在每次数据库升级过程中，都会导出一个Schema文件，这是一个json格式的文件，
     其中包含了数据库的基本信息，有了该文件，开发者能清楚的知道数据库的历次变更情况，极大地方便了开发者排查问题。

     在build.grade文件中配置相关信息
             javaCompileOptions {
                 annotationProcessorOptions {
                     arguments = ["room.schemaLocation" : "$projectDir/schemas".toString()]
                 }
             }

销毁和重建策略
     在SQLite中修改表结构比较麻烦，例如，我们想将Student表中sex字段类型从INTEGER改为TEXT，最好的方式是采用销毁与重建策略,
     大致分为以下步骤:
        1、创建一张符合表结构要求的临时表temp_student
        2、将数据从旧表student复制到临时表temp_student
        3、删除旧表student
        4、将临时表temp student重命名为student

预填充数据库
        有时候我们希望应用自带一些数据供我们使用，我们可以将数据库文件放入assets目录一起打包发布，
        在用户首次打开App时，使用createFromAsset()和createFromFile()创建Room数据库。


