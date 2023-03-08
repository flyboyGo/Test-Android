
1、GsON简介

  SON(JavaScript Object Notation)是一种轻量级的数据交换格式。易于人阅读和编写。同时也易于机器解析和生成。

  Gson是Google 提供的用来在Java对象和JSON数据之间进行映射的Java类库。可以将一个JSON字符串转成一个Java对象（反序列化)，或者反过来(序列化)。

  https://github.com/google/gson

  Gradle:
      dependencies {
            implementation 'com.google.code.gson:gson:2.10'
      }


2、Java对象序列化与反序列化

      User类，拥有name, password, age, isStudent 四个属性，对User类型对象进行序列化与反序列化,
      使用new Gson().toJson/fromJson即可完成序列化与反序列化。

      //java对象
      user u1 = new User( userName: "Lance", password: "123", age: 18, isStudent: false);

      // Gson提供的Gson对象
      Gson gson = new Gson();

      //序列化
      string json = gson.to]son(u1);
      system.out.print1n(json);

      //反序列化
      User u2 = gson.fromJson(json,User.class);
      System.out.println(u2);


3、Java嵌套对象序列化与反序列化

      往User类中增加字段: Job (工作name，薪资salary) . 对User类型对象进行序列化与反序列化。

      //java对象
      User u1 = new user( userName: "Lance"，password:"123"，age: 18，isStudent: false);
      Job job = new Job( name:"工人",salary: 1eeee) ;
      u1.setJob(job);

      // Gson提供的Gson对象
      Gson gson = new Gson();
      //序列化
      string json = gson.to]son(u1);
      system.out.println(json);
      // 反序列化
      User u2 = gson.fromJson(json，User.class);
      system.out.println(u2);

4、Array数组的序列化与反序列化

   数组类型对象和普通对象一样，使用toJson/fromJson即可完成序列化与反序列化。

   User[ ] users1 = new User[3];
   //java对象
   users1[0] = new user( userName:"Lance",password:"123",age: 18，isStudent: false);
   users1[1] = new User( userName: "Alex"，password:"123"，age: 88，isStudent: true);

   //Gson提供的Gson对象
   Gson gson = new Gson();
   //序列化
   String json = gson.toJson(users1);
   system.out.print1n(json);
   //反序列化
   User[ ] users2 = gson.fromJson(json，user[].class);
   system.out.println(users2[0]);
   system.out.println(users2[1]);
   system.out.println(users2[2]);

5、List的序列化与反序列化

   List集合类型对象需要注意的是，在反序列化时因为Java是伪泛型，泛型擦除会导致无法反序列化为List<User>，需要使用 TypeToken 完成反序列化。

   List<User> list1 = new ArrayList<>();
   list1.add(new User( userName: "Lance",password:"123",age:1 isstudent: false));
   list1.add(new User( userName: "Alex",password:"123"，age: 88a, jsStudent: true));
   list1.add(null);

   //Gson提供的Gson对象
    Gson gson = new Gson( );
   //序列化
   string json = gson.toJson(list1);
   system.out.println(json);
   //反序列化
   Type type = new TypeToken<List<User>>() { }.getType();
   List<User> list2 = gson.fromJson(json，type);
   system.out.print1n(list2.get(e) ) ;
   system.out.print1n(list2.get(1));
   system.out.println(list2.get(2));

6、Map的序列化与反序列化

   Map集合类型对象在反序列化时与List一样，需要用TypeToken完成反序列化。

   Map<String,User> map1 = new HashMap<>();
   // java对象
   map1.put("1" ,new User( userName:"Lance",password:"123"，age: 18，isStudent: false));
   map1.put( "2" ,new User( userName: "Alex"，password: "123"，age:88，isStudent: true));
   map1.put( "3" ,null);
   map1.put(nu11,null);

   // Gson提供的Gson对象
   Gson gson = new Gson();
   //序列化
   string json = gson.toJson(map1);
   system.out.print1n(json);
   //反序列化
   Type type = new TypeToken<Map<String,User>>() {}.getType();
   Map<String,User> map2 = gson.from]son(json，type);
   system.out.println(map2.get(nu11));
   system.out.print1n(map2.get("1"));

7、Set的序列化与反序列化

   Set在反序列化时同样需要使用TypeToken完成反序列化。

   Set<User> set1 = new HashSet<>();
   set1.add(new User( userName: "Lance"，password: "123",age: 18，isStudent: false));
   set1.add(new User( userName: "Alex",password:"123"，age: 88，isStudent:true));
   set1.add(nul1);

   // Gson提供的Gson对象
   Gson gson = new Gson();
   //序列化
   string json = gson.toJson(set1);
   system.out.print1n(json);

   //反序列化
   Type type = new TypeToken<List<User>>() {}.getType();
   List<User> set2 = gson.from.son(json，type);

   system.out.println(set2.get(0));
   system.out.println(set2.get(1));
   system.out.println(set2.get(2));

   如果HashSet类型，则完全可以使用反序列为 List，因为两者序列化后的Json数据一致!

8、NULL的序列化与反序列化

   如果一个变量为NUL，那么按照GSON默认的处理方式为忽略这个字段
   User u1 = new user(userName:"Lance" , password:"123",age: 18，isStudent: false);
   // Gson提供的Gson对象
   Gson gson = new Gson();
   // 序列化
   string json = gson.to]son(u1);
   system.out.println(json);
   User u2 = gson.fromJson(json，User.class);
   system.out.println(u2);

   如果一个集合(数组)中的存储的元素为NULL，那么按照GSON默认的处理方式为不会忽略这个元素,显示为null
   List<User> list1 = new ArrayList<>();
   list1.add(new user( userName: "Lance"，password: "123"，age: 18，isStudent: false));
   list1.add(nul1);

   //Gson提供的Gson对象
   Gson gson = new Gson();
   //序列化
   string json = gson.toJson(list1);
   system.out.print1n(序列化:"+json) ;

   序列化: [{"userName":"Lance"."password":"123" ,"age":18,"isStudent":false},null]

9、控制序列化/反序列化的变量名称

   如果希望JSON字符串字段名不以变量名作为Key，比如JSON字符串中的Key存在Java中的关键字时，
   可以借助@SerializedName注解控制JSON子段中Key的命名。

   serialize:是否参与序列化. deserialize是否参与反序列化
   @Expose( serialize = false,deserialize = false)
   private int test1;

   "userName":"Lance" ," password":""123" ,"age":18,"isStudent":false,"class":2]

   如果希望指定GSON对某些字段配置是否参与序列化与反序列化.可以使用@Expose注解控制．
   同时使用GsonBuilder创建Gson对象:
   Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create();

   另外transient关键字，也可以直接让变量不参与序列化/反序列化

   private transient int test2;













