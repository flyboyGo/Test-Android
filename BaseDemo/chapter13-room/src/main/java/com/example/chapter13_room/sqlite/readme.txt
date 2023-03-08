
1、SQLite介绍

   SQLite 关系型数据库
   嵌入式的数据库，体积小,功能强大,几十kb.
   嵌入式设备上: 计算器手表
   iso:Sqlite数据库

   在Android平台上，集成了一个嵌入式关系型数据库--—SQLite, SQLite3支持 NULL、INTEGER、REAL（浮点数字)、TEXT(字符串文本) 和 BLOB(二进制对象)数据类型，
   虽然它支持的类型只有五种，但实际上sqlite3也接受 varchar(n)、char(n)、decimal(p,s)等数据类型，只不过在运算或保存时会转成对应的五种数据类型。
   SQLite最大的特点是你可以把各种类型的数据保存到任何字段中，但是主键只能是Integer类型的。
   Sqlite数据库一般要求主键是 _id ,当然也可以是id.

   原来:数据库
   安装一个数据库的软件。
   android里面的数据库是由底层的sqlite.c的代码来动态生成的。


