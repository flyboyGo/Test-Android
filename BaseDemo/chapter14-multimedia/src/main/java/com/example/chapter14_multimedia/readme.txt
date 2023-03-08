

1、MediaRecorder

   使用MediaRecorder能够编写从设备麦克风与相机捕获音视频、保存音视频并（使用MediaPlayer)进行播放的应用。

   权限:
   <uses-permission android:name="android.permission.RECORD_AUDIO" />
   <uses-permission android:name="android.permission.CAMERA"/>


   MediaRecorder 本质是状态机(状态切换图)
   使用new关键字实例化之后进入initial状态，在使用prepare()方法之前要进行其他的配置经过initialized和dataSourceConfigured两个状态。
   一次使用完调用reset()重复使用的时候，就算音频源、编码什么的都没有变化，仍然要再次调用setXXX()完成状态的转变，否则就会报错。
   最后不再使用mediaRecorder，释放资源，release()之后要跟上赋值为空。

2、MediaPlayer简介

   MediaPlayer类是媒体框架最重要的组成部分之一。此类的对象能够获取、解码以及播放音频和视频，而且只需极少量设置。
   它支持多种不同的媒体源,例如:

   (1): 本地资源
   (2): 内部URI,例如您可能从内容解析器(ContentResolver)那获取的URI
   (3): 外部网址(流式传输)

   媒体格式列表
   https://developer.android.google.cn/guide/topics/media/media-formats?hI=zh_cn

   MediaPlayer 本质是状态机(状态切换图)
   和recorder不同，若数据源配置不变，stop之后调用prepare()start()就可以重新开始播放；
   数据源变了，就reset()进入idle状态，重新set,prepare,start。
   可以设置播放完毕和出错的事件监听器


3、SoundPool
   MediaPlayer虽然也能播放音频，但是它有资源占用量较高、延迟时间较长、不支持多个音频同时播放等缺点。
   这些缺点决定了MediaPlayer在某些场合的使用情况不会很理想，例如在对时间精准度要求相对较高的场景。
   而SoundPool一般用来播放密集、急促而又短暂的音效．比如:“滴滴一下，马上出发"。

   注意:
   load方法都会返回一个SoundId值，这个值可以用来播放和卸载音乐



