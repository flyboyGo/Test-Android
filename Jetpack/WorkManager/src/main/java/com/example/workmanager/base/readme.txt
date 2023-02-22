
WorkManager的作用
 1、在后台执行任务的需求是非常常见的，Android也提供了多种解决方案如JobScheduler、Loader、Service等，
     如果这些API没有被恰当使用，则可能会消耗大量电量。Android在解决应用程序耗电问题上做了各种尝试，从Doze到App Standby，通过各种方式限
     制和管理应用程序，以保证应用程序不会在后台消耗过多的设备电量。WorkManager为应用程序中那些不需要及时完成的任务提供了一个统一的解决方案，
     以便在设备电量和用户体验之间达到一个比较好的平衡。

  WorkManager的重要特点
  1、针对的是不需要及时完成的任务
      例如，发送应用程序日志、同步应用程序数据、备份用户数据等，这些任务一般都不需要立即完成，如果我们自己来管理这些任务，逻辑可能会非常复杂，
      若API使用不恰当可能会消耗大量电量。

  2、保证任务一定会执行
      WorkManager能保证任务一定会被执行，即使应用程序当前不在运行中，甚至在设备重启过后，任务仍然会在适当的时刻被执行。
      这是因为WorkManager有自己的数据库，关于任务的所有信息和数据都保存在该数据库中。
      因此只要任务交给了WorkManager，哪怕应用程序彻底退出，或者设备被重新启动，WorkManager依然能够保证完成任务。

  3、兼容范围广
      WorkManager最低能兼容APl Level 14，并且不需要你的设备安装Google Play Services。
      因此，不用过于担心兼容性问题，因为APl Level 14已经能够兼容几乎100%的设备了。

WorkManager的兼容方案
 1、WorkManager能根据设备的情况，选择不同的执行方案。在API Level 23以上的设备中，通过JobScheduler完成任务，
    在API Level 23以下的设备中，通过AlarmManager和Broadcast Receivers组合来完成任务。但无论采用哪种方案，任务最终都是由Executor来执行的。
 2、另外，WorkManager不是一种新的工作线程，它的出现不是为了替代其他类型的工作线程。工作线程通常立即运行，并在任务执行完成后给用户反馈。
    而WorkManager不是即时的，它不能保证任务能立即得到执行。

                        WorkManager
                           |
                    是     V      否
    JobScheduler <----- API 23+  ----> AlarmManager + Broadcast Receivers

                        Executor

  3、WorkManager能根据设备的情况，选择不同的执行方案。在API Level 23以上的设备中,通过JobScheduler完成任务，
    在APl Level 23以下的设备中，通过AlarmManager和Broadcast Receivers组合来完成任务。但无论采用哪种方案，任务最终都是由Executor来执行的。
  4、另外，WorkManager不是一种新的工作线程，它的出现不是为了替代其他类型的工作线程。工作线程通常立即运行，并在任务执行完成后给用户反馈。
     而WorkManager不是即时的，它不能保证任务能立即得到执行。

WorkManager的使用方法
   1、添加依赖  implementation 'androidx.work:work-runtime:2.4.0-alpha03'
   2、使用Work类定义任务
   3、使用WorkRequest配置任务
      设置任务触发条件
      将任务触发条件设置到WorkRequest
      设置延迟执行任务
      设置指数退避策略
      为任务设置tag标签

      将任务提交给系统
      观察任务的状态
      取消任务
      参数传递

      周期性任务!!!!!!!!
      任务链!!!!
      任务链组合!!!!(先执行A、B和C、D,再执行E)


        /**
         *  SystemClock.sleep()方法与Thread.sleep()方法的区别
         *
         * Thread.sleep()是java的方法, 可能会抛出InterruptedException异常, 并且可能会被中断;
         *
         * SystemClock.sleep()是Android的方法,不会抛出异常, 并且无论如何都会让当前线程休眠指定的时间。
         */

         // 定时器
         new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                     workManager.cancelWorkById(workRequest.getId());
                 }
          },2000);




