
  Okhttp使用中的缺陷
    1、用户网络请求的接口配置繁琐，尤其是需要配置复杂请求body，请求头，参数的时候;
    2、数据解析过程需要用户手动拿到responseBody进行解析，不能复用;
    3、无法适配自动进行线程的切换;
    4、万一我们的存在嵌套网络请求就会陷入"回调陷阱";

  Retrofit是什么(意义是优化网络的使用流程,不是优化网络本身)
    准确来说,Retrofit是一个RESTful的HTTP网络请求框架的封装。
    原因:网络请求的工作本质上是OkHttp完成，而Retrofit仅负责网络请求接口的封装

              发起网络请求
    App应用层   <----->   Retrofit <------>  OkHttp  <----->   服务器
                                                    返回响应数据

    1、App应用程序通过Retrofit请求网络，实际上是使用Retrofit接口层封装请求参数、Header、Url等信息，之后由OkHttp完成后续的请求操作。
    2、在服务端返回数据之后，OkHttp将原始的结果交给Retrofit，Retrofit根据用户的需求对结果进行解析。

 Retrofit封装的特点
 1、okhttp创建的是OkhttpClient，然而retrofit创建的是Retrofit实例
 2、构建蓝色的Request的方案，retrofit是通过注解来进行的适配
 3、配置Call的过程中,retrofit是利用Adapter适配的Okhttp的Call,为call的适配提供了多样性(防止回调陷阱)
 4、相对okhttp，retrofit会对responseBody进行自动的Gson解析(Converter自动完成数据转换)，提供了可复用，易拓展的数据解析方案
 5、相对okhttp，retrofit(Executor自动完成线程的切换)会自动的完成线程的切换。


 RESTful API
   1、REST (Representational State Transfer)，表述性状态转移。
   2、REST只是风格而没有标准，其核心是一切皆资源。这里的资源不一定只是数据，而是数据加表现形式的组合。任何可命名的抽象概念都可以定义为一个资源。
   3、符合REST风格的API就可以叫做RESTful API。
   4、如访问http://xxxxx/user/10001/info，对应找到userId为10001的这条用户的数据:

    // GET/POST/PUT/DELETE
    @GET("/user/{userId}/info")
    Ca11<UserInfo> getUserInfo(@Path("userId") String userId);

    RESTful对资源操作
     1、POST:用于在服务器中新建一个资源，对应资源操作是INSERT，非幂等且不安全。
     2、DELETE:用于从服务器删除资源，对应资源操作是DELETE，幂等且不安全。
     3、PUT:用于在服务器中更新资源，客户端提供改变后的完整资源，对应资源操作是UPDATE，幂等且不安全。
     4、GET:用于从服务器中取出资源，对应资源操作是查询SELECT，幂等且安全。



  1、Retrofit简介

    (1): A type-safe HTTP client for Android and Java。
    (2): Retrofit和OkHttp都是由Square公司开发并开源共享的网络框架。Retrofit基于OkHttp，并支持RESTful API设计风格。
         它的网络请求工作实际由OkHttp完成，而Retrofit主要负责请求接口的封装。
    (3): Retrofit不仅具备了OkHttp的高效特性，还有以下优势:支持RESTfulAPI设计风格。
         通过注解配置请求:包括请求方法、请求参数、请求头，返回值等。
         可以搭配多种Converter将获得的数据自动解析和序列化:支持Gson，Jackson,Protobuff等。
         提供了对RxJava的支持。

    官方地址: https://square.github.io/retrofit
    依赖: implementation "com.squareup.retrofit2:retrofit:2.9.0"

  2、基本使用

   Retrofit支持同步/异步方式进行网络访问，掌握以下内容的基本实现:
   (1): GET/POST请求方式。
   (2): 表单数据提交。
   (3): Body内容提交。
   (4): MutipartBody上传文件。


      服务器域名:https://www.httpbin.org/
      接口:post
      参额:usernames password
      接口:get
      多数:username, password

    一、根据Http接口创建Java接口:
      public interface HttpbinService {

          @GET("get")
          call<ResponseBody> get(@Query("userName") string username，@Query("password") String pwd);

          @PoST("post")
          @FormUr1Encoded
          call<ResponseBody> postForm(@Field("userName") string username，@Field("password") string pwd);
       }

   二、创建Retrofit对象，并生成接口实现类对象:
       Retrofit retrofit = new Retrofit.Builder().baseUr1("https://wow.httpbin.org/").build();
       HttpbinService httpbinService = retrofit.create(HttpbinService.class);

   三、接口实现类对象调用对应方法获得响应︰

       call<ResponseBody> call = httpbinService.get( username:"lance"，pwd:"123");

       //异步请求，同步请求使用execute方法
       cal1.enqueue(new callback<ResponseBody>(){

          @override
          public void onResponse(Call<ResponseBody> call，Response<ResponseBody> response){
              //response.body().string()
           }

          @override
          public void onFailure(call<ResponseBody> cal1，Throwable t) {

          }
       });


   3、Retrofit的注解
       方法注解:@GET，@POST，@PUT，@DELETE，@PATH，@HEAD，@OPTIONS，@HTTP
       标记注解:@FormUrlEncoded, (文件上传、下载相关)@Multipart, @Streaming
       参数注解︰@Query，@QueryMap，@Body，@Field，@FieldMap，@Part，@PartMap
       其他注解:@Path，@Header,@Headers，@Url


   4、Retrofit的转换器
       在我们接到服务器的响应后，目前无论是OkHttp还是Retrofit都只能接收到String字符串类型的数据，在实际开发中,
       我们经常需要对字符串进行解析将其转变为一个Java Bean对象。比如服务器响应数据为JSON格式字符串，那么我
       们可以自己利用GSON库完成反序列化的操作。而Retrofit提供了多个转换器使得响应能够完成自动的数据转换。

       以json解析为例:
       添加依赖:
        implementation 'com.squareup.retrofit2:converter-gson:2.9.0'

       修改接口方法:
       @POST("post"")
       FormUrlEncoded
       Call<JavaBean> post(@Field("username") String userName,@Field("password") String pwd);

   5、Retrofit的适配器

       Retrofit的接口方法返回类型必须是Call，如果能够将Call改为RxJava中的Observable或者Flowable，对于嵌套的情况，就能得到非常方便优雅的解决。
       这就是适配器的功能，如果我们想要返回的不是Call适配器就能够帮助我们转换为其他类型。
       以RxJava3为例

       添加依赖:
       implementation 'com.squareup.retrofit2:adapter-rxjava3:2.9.0'
       implementation 'io.reactivex.rxjava3:rxandroid:3.0.0'

       修改接口方法:
       @POST(post")
       @FormUrlEncoded
       Observable<JavaBean> post(@Field("username"") String userName,@Field("password")String pwd);




       //先登求,再请求
       call<BaseResponse> call = wanAndroidService2.login( username: "lanceedu"，pwd: "123123");
       cal1.enqueue( new cal1back<BaseResponse>(){
            @override
            public void onResponse(Call<BaseResponse> call,Response<BaseResponse> response) {
            //登录成功请求收藏的文常
            if(response.isSuccessful()){
                 wanAndroidService2.getArticle(pageNum: 0).enqueue(new callback<ResponseBody>() {
                 @override
                 public void onResponse(Call<ResponseBody> call，Response<ResponseBody> response) {

                 }

                @Override
                public void onFailure(call<ResponseBody> call，Throwable t){

                }
              });
            }
       }
        @override
        public void onFailure(Call<BaseResponse> call，Throwable t) {

        }
     });

 网络模块搭建
     1、Retrofit基于OkHttp具备高效特性等优势，但由于其高度封装的原因，导致其拓展性稍差。
        由于解析数据都是使用统一的Converter，如果服务器不能给出同一的API形式将很难进行处理。
     2、配合使用Hilt进行依赖注入，使存Retrofit的灵活性提高。
     3、RxJava支持了线程切换，Retrofit对于RxJava的无缝支持，几乎可以满足所有网络请求业务的实现，可以利用这点搭建一个完善的网络访问模块。




