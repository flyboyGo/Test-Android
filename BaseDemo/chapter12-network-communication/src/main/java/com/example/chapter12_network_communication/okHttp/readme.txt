
1、什么是OkHttp
   https://square.github.io/okhttp/
   由Square公司贡献的一个处理网络请求的开源项目，是目前Android使用最广泛的网络框架。
   从Android4.4开HttpURLConnection的底层实现采用的是OkHttp。

   (1): 支持HTTP/2并允许对同一主机的所有请求共享一个套接字;
   (2): 如果非HTTP/2，则通过 "连接池" ,减少了请求延迟;
   (3): 默认请求GZip压缩数据;
   (4): 响应缓存,避免了重复请求的网络;

   implementation("com.squareup.okhttp3:okhttp:4.10.0")

   测试URL:https://www.httpbin.org/

2、okHttp的基本用法
   (1): 同步请求
   (2): 异步请求

3、POST请求

     协议规定POST提交的数据必须放在 "请求体中" ，但协议并没有规定数据必须使用什么编码方式。而常用的数据编码方式有:
     https://www.runoob.com/http/http-content-type.html.

     (1): Content-Type: application/x-www-form-urlencoded
          数据被编码为名称/值对，默认类型;

     (2): Content-Type: multipart/form-data
          数据被编码为一条消息，一般用于文件上传;

     (3): Content-Type: application/octet-stream
          提交二进制数据,如果用于文件上传，只能上传一个文件;

     (4): Content-Type: application/json
          提交json数据

 4、Builder构建者

    OkHttpClient okHttpClient = new OKHttpClient.Builder().build();

    Builder()构建者模式中包含了许多额外的功能方法

    (1): 拦截器
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new XXX).build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new XXX).build();



    (2): 缓存、Cookie

       缓存:
       OkHttp按照Http协议规则实现了缓存的处理，缓存是比如:当我们发起第一次请求之后，如果后续还需要进
       行同样的请求，此时如果符合缓存规则，则可以减少与服务器的网络通信，直接从本地文件缓存中读取响应
       返回给请求者。但是默认情况下，OkHttp的缓存是关闭状态，需要我们开启。

       pathname :缓存文件地址     maxsize:缓存最大容量字节

       OkHttpclient OkHttpclient = new OkHttpclient.Builder( ).cache(
       new Cache(new File( pathname: "/path/cache"), maxSize: 100)).build();


       Cookie:
       Cookie是某些网站为了辨别用户身份，进行会话跟踪(比如确定登陆状态)而储存在用户本地终端上的数据(也可以临时保存在内存中)（通常经过加密)，
           由用户客户端计算机暂时或永久保存的信息

       临时保存服务器发送的cookie数据(内存中),不保存成文件
       Map<String,List<Cookie>> cookies = new HashMap<>();

    @Test
    public void cookie()
    {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJar() {
                    @Override
                    public void saveFromResponse(@NonNull HttpUrl httpUrl, @NonNull List<Cookie> list) {
                        cookies.clear();
                        cookies.put(httpUrl.host(),list);
                    }

                    @NonNull
                    @Override
                    public List<Cookie> loadForRequest(@NonNull HttpUrl httpUrl) {
                        List<Cookie> cookieList = CookieUnitTest.this.cookies.get(httpUrl.host());
                        return cookieList == null ? new ArrayList<>() : cookieList;
                    }
                }).build();


 5、OKHttp双任务队列机制
    (1): OkHttp实现异步请求采用了双任务队列机制，通过Dispatcher来调度任务。
    (2): 新加入的异步任务AsyncCall进入等待队列readyAsyncCalls。
    (3): 遍历readyAsyncCalls判断当前情况:是否超过最大并发数﹖是否超过同个主机最大请求数?
    (4): 满足条件直接把AsyncCall加入到正在执行的队列RunningAsyncCalls,并且使用线程池执行新加入的异步任务AsyncCall。

    (5): AsyncCall执行结束，再次回到Dispatcher的promoteAndExecute()。

                         | ------------- dispatcher.finished -------------------------------------------------------
                         |                                                                                          |
                         v                   遍历                                                                    |
 AsyncCall   ----->  Dispatcher ----> readyAsyncCalls ---> 判断是否满足条件? ---> runningAsyncCalls.add() ----> ThreadPoolExecutor.execute()
                                    promoteAndExecute()


  6、OKHttp的拦截器
     (1): OkHttp提供了一系列的拦截器来处理相应的业务。也可以通过自定义拦截器，来实现自己的拦截业务。
     (2): OkHttp中完整的拦截链如右图所示，在真正发起请求之前(CallServerInterceptor)，将经过各个拦截器处理业务。

    RealInterceptorChain proceed     完整拦截器链

   (1): 可选
     用户自定义Interceptor
     OkHttpClient.Builder#addInterceptor
   (2):
     RetryAndFollowUpInterceptor
     处理错误，重定向等
   (3):
     BridgeInterceptor
     添加必要的请求头信息、 gzip处理等
   (4):
     CacheUpInterceptor
          缓存处理
   (5):
     ConnectionInterceptor
         打开一个链接
   (6):可选
     非网页Interceptor
     OkHttpClient.Builder#addNetworkInterceptor
   (7):
     CallServerInterceptor
         访问服务器

   7、责任链模式与拦截器
      (1): 责任链模式:为了避免请求发送者与多个请求处理者耦合在一起，于是将所有请求的处理者通过前一对象记住其下一个对象的引用而连成一条链;
           当有请求发生时，可将请求沿着这条链传递，直到有对象处理它为止。

      (2): OkHttp拦截器就是基于责任链模式来实现的。


                  Client发起请求

      Request                        Response
         |         拦截器1              ^
         |                             |
         |         拦截器2              |
         |                             |
         |         拦截器3              |
         |                             |
         |         拦截器4              |
         |                             |
         V         拦截器5              |



   8、TCP三次握手和四次挥手
     (1): 三次握手简单理解:客户端发送请求建立连接，服务端收到请求后立即回应，
          客户端收到回应后打开连接并通知服务端，服务端再次收到消息也打开连接。

     (2): 四次挥手:客户端数据发送完成告知服务端申请断连，服务端收到断连并回应，客户端继续等待最后数据的传送,
          服务端业务完成再次发送回应消息并断开连接，客户端收到回应，再次发送一次确认，并断开。


   9、Socket连接池复用

     (1): 每次建立连接关闭都要三次握手四次挥手，显然会造成效率低下，Http协议中
         一种叫做KeepAlive机制，它可以在传输数据后仍然保持连接状态，当客户端需
         要再次传输数据时，直接使用空闲下来的连接而不需要重新建立连接。

     (2): OkHttp默认支持5个并发KeepAlive，链路默认的存活时间为5分钟。


                       -------------复用connection--------------------
                       |                                              |
                       V                                              |
     打开连接       写请求数据        读相应数据          timeout?    -----Y---->   关闭连接


