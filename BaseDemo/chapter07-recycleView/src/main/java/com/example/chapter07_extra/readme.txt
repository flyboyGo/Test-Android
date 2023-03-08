筛选条件的规则
Action:动作
Category:类别，是对intent的一种额外的描述。
Data:数据，也是对intent的一种额外的描述。

Action:动作
    描述intent的动作。必须有。只能set一个，必须和过滤条件里的action值一模一样才能匹配
    比如:
    打开一个能看图片的页面，action取值android.intent.action.VIEW
    打开一个能发送短信的页面，action取值android.intent.action.SEND


Category:类别
    对intent的一种额外的描述，是可选的，可以add多个。不写的话，默认也有一个Default的。每个都要和过滤条件里的一样才能匹配。

    需要注意的是:
    如果你想要当前activity可以被隐式意图匹配，那么必须在Intent-filter里声明android.intent.category.DEFAULT 这个category，否则会匹配不到。
    (默认自动添加,intent.addCategory(Intent.CATEGORY_DEFAULT))


Data:数据
    目标页面需要有处理该数据的能力。也是Intent条件的附加条件，也是可选的

    数据地址:URI，比如: http://www.baidu.com  tel:110等
    类型:mimeType，比如: image/jpeg、image/*、video/*等

    URI格式:
       <scheme>://<host>:<port>/[<path>|<pathPrefix>|<pathPattern>]


