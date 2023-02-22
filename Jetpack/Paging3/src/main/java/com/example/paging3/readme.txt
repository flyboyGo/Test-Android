
Paging组件的意义
  1、分页加载是在应用程序开发过程中十分常见的需求，Paging就是Google为了方便Android开发者完成分页加载而设计的一个组件，
     它为几种常见的分页机制提供了统一的解决方案，让我们可以把更多的精力专注在业务代码上。

Paging支持的架构类型

     网络数据    ---->   Paging
     网络数据    ---->   数据库   ----> Paging
     数据库      ---->   Paging

     网络
         1、对网络数据进行分页加载，是最常见的一种分页需求，也是我们学习的重点。
            不同的公司针对分页机制所设计的API接口通常也不太一样，但总体而言可以归纳为3种。
             Paging组件提供了3种不同的方案，以应对不同的分页机制。
             分别是PositionDataSource、PageKeyedDataSource、ItemKeyedDataSource。
     数据库
          1、掌握了网络数据分页之后，数据库数据分页将会容易很多，无非就是数据源的替换。

     网络＋数据库
          出于用户体验的考虑，我们通常会对网络数据进行缓存，以便用户在下次打开应用程序时，应用程序可以先展示缓存数据。
          我们通常会利用数据库对网络数据进行缓存，这意味着我们需要同时处理好网络和数据库这两个数据源。
          多数据源会让业务逻辑变得更为复杂，所以，我们通常采用单一数据源作为解决方案。
          即从网络获取的数据，直接缓存进数据库，列表只从数据库这个唯一的数据源获取数据,这里我们会学习BoundaryCallback。


Paging的工作原理


                            ---1---->   onBindViewHolder ----2---->  PagedList.Config ----3---->                 <------ 网络数据

     (向上滑动)RecycleView              PageListAdapter              PagedList                  DataSource

                            <---6----     DiffUtil       <----5----   Datas           <----4----                 <------ 数据库

Paging的3个核心类
    1、PagedListAdapter
           RecyclerView需要搭配适配器使用，如果希望使用Paging组件，适配器需要继承自 PagedListAdapter。
    2、PagedList
           PagedList负责通知DataSource何时获取数据，以及如何获取数据。
           例如，何时加载第一页/下一页，第一页加载的数量、提前多少条数据开始执行预加载等，从DataSource获取的数据将存储在PagedList中。
    3、DataSource
      (1): 在DataSource中执行具体的数据载入工作数据可以来自网络，也可以来自本地数据库，根据分页机制的不同，Paging为我们提供了3种DataSource。
      (2): 数据的载入需要在工作线程中进行。


 PositionalDataSource使用
         适用于可通过任意位置加载数据(且目标数据源数量固定的情况例如，请求时携带的参数为start=2&count=5，
          则表示向服务端请求从第2条数据开始往后的5条数据。
         0  1  2  3  4  5  6  7  8  9  10 11 ...  49(总数固定)
                  |           |
                  start=2&count=5
       1、API接口
          http://192.168.0.117:8080/pagingserver_ war/pds.do?start=0&count=8

       2、接口返回的数据
        {
           "count": 5,
           "start": 2,
           "total": 50,
           "subjects": [
                  {
                     "id": 35076714,
                     "title": "扎克·施奈德版正义联盟",
                     "cover": "https://img9.doubanio.com/xxx.webp",
                     "rate": "8.9"
                   },
                   {
                      .....
                   }
                   .
                   .
                   .
           ]
        }

 PageKeyedDataSource使用

           适用于数据源以 页 的方式进行请求的情况，例如，若请求时携带的参数为page=2&pageSize=5，则表示数据源以5条数据为一页，当前返回第二页的5条数据。
           0  1  2  3  4  5  6  7  8  9  10  11  12  13  14  15  16  17  18  ...
           |           |  |           |
              page1         page2(page=2&pageSize=5)


         1、API接口
            http://192.168.0.117:8080/pagingserver_ war/pkds.do?page=2&pageSize=5
         2、接口返回的数据
        {
           "has more": true,
           "subjects": [
                  {
                     "id": 35076714,
                     "title": "扎克·施奈德版正义联盟",
                     "cover": "https://img9.doubanio.com/xxx.webp",
                     "rate": "8.9"
                   },
                   {
                      .....
                   }
                   .
                   .
                   .
           ]
        }

 ItemKeyedDataSource使用
          1、适用于当目标数据的下一页需要依赖于上一页数据中最后一个对象中的某个字段作为key的情况，此类分页形式常见于评论功能的实现，
              例如,若上一页数据中最后一个对象的key为9001，那么在请求下一页时，需要携带参数since=9001&pageSize=5，
              则服务器会返回key=9001之后的5条数据。

                 key=9001
              ...  key  key  key  key  key  key  key  key  key  key  key  key  key  ...
                         |                   |
                          since=9001&pageSize=5
          1、API接口
              http://192.168.0.117:8080/pagingserver_war/ikds.do?since=0&pagesize=8
          2、接口返回的数据
        [
                  {
                     "id": 35076714,
                     "title": "扎克·施奈德版正义联盟",
                     "cover": "https://img9.doubanio.com/xxx.webp",
                     "rate": "8.9"
                   },
                   {
                      .....
                   }
                   .
                   .
                   .
        ]


