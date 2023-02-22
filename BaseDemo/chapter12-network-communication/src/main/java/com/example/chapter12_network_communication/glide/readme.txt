
1、Glide的引入

        Glide是一个快速高效的Android图片加载库，可以自动加载网络、本地文件，app资源中的图片，注重于平滑的滚动。

        开源地址: https://github.com/bumptech/glide
        中文文档: https://muyangmin.github.iolglide-docs-cn/

        引入Glide:
        implementation 'com.github.bumptech.glide.glide:4.12.0'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'

        基本使用:
        Glide.with([fragment/Context/View])
             .load(url)
             .into(imageView);

2、Glide占位符

        Glide4中占位图的使用方法，包括(placeholder，error，fallback）三种占位图

        1. placeholder 正在请求图片的时候展示的图片
        2. error 如果请求失败的时候展示的图片(如果没有设置，还是展示placeholder的占位符)
        3. fallback如果请求的url/model为null的时候展示的图片(如果没有设置，还是展示placeholder的占位符)

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.hold)
                .error(R.drawable.error)
                .fallback(R.drawable.fallback)
                .override(100,100);  //override指定加载图片大小

        Glide.with([fragment/Context/View])
            .load(url)
            .apply(requestOptions)
            .into(imageView);

3、过渡动画
       定义Glide 如何从占位符到新加载的图片，或从缩略图到全尺寸图像过渡。

       交叉淡入(避免占位图还能显示)

       DrawableCrossFadeFactory factory =
            new DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build0);

       Glide.with(context)
            .load(URL)
            .apply(requestOptions)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .into((ImageView) holder.itemView);
            (默认传入的就是Drawable)

       为了提升性能，请在使用Glide向ListView , GridView,或RecyclerView加载图片时考虑避免使用过度效果

 4、变换
       获取资源并修改它，然后返回被修改后的资源。通常变换操作是用来完成剪裁或对位图应用过滤器。
       比如对图片进行圆角配置。

       Glide.with(this)
          .load(URL)
          .transform(...)
          .into(iv);

       1. CircleCrop:圆角
       2. RoundedCorners:四个角度统一指定
       3. GranularRoundedCorners:四个角度单独指定
       4. Rotate:旋转


5、Generated APl
       1、添加 Glide注解处理器的依赖:
          dependencies {
            annotationProcessor 'com.github.bumptech.glide:compiler:4.12.0'
          }

       2、在Application模块中包含一个AppGlideModule 的实现:

          @GlideModule
          public final class MyAppGlideModule extends AppGlideModule { }

       此时我们能够更简单的完成占位符等配置:

        GlideApp.with(fragment)
           .load(myUrl)
           .placeholder(R.drawable.placeholder)
           .into(imageView);


6、GlideExtension与GlideOption

        定义一个在频繁使用的选项集合。
        @GlideExtension
        public class MyAppExtension {

           private MyAppExtension() {}  // utility class

           @GlideOption
           public static BaseRequestOptions<?> defaultImg (BaseRequestOptions<?> options){
              return options
                  .placeholder(R.drawable.hold)
                  .error(R.drawable.error)
                  .fallback(R.drawable.fallback);
             }
        }

        使用对比︰
        GlideApp.with().load().placeholder(R.drawable.holder).error(R.drawable.error),fallback(R.drawable.fallback)
        GlideApp.with().load().defaultImg()







