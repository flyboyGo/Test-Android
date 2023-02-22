
View和ViewGroup的区别

   先看ViewGroup，就是View组的意思是吧！也就是说，ViewGroup里面有很多子View，用于管理/摆放子View的View就叫ViewGroup。
   什么是View呢？严格上来说，都是View，为什么呢？有了前面的定义，ViewGroup里面的全是View，因为你是View-Group呀！
   而ViewGroup里也可以放ViewGroup，所以说，ViewGroup也可以认为是View。比如说LinearLayout里面还可以放LinearLayout吧！

   但是通常来说，我们说自定义View指的是定那些需要自己绘制的控件，重点是绘制，比如说时钟呀，波浪效果这些...View里面不再摆放其他的子View，我们把这种称为View,
   而ViewGroup，则是用于控制View的摆放，比如说LinearLayout，让子View成线性摆放，RelativeLayout让子View以相对的位置进行摆放!

总结
    对于Android的View体系，了解继承关系，知道怎么区分是View，什么是ViewGroup即可。View注重的是绘制内容，ViewGroup注重子View的摆放。

    有了这些基础知识，我们后面写自定义控件就可以步骤化了。第一步就是要判断你写的这个控件属于ViewGroup还是View.

Android自定义控件的分类
   自定义View
   自定义ViewGroup
   自定义组合控件
   对现有的控件进行修改