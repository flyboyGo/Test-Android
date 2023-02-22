package com.example.chapter06_client;

import android.net.Uri;
import android.provider.BaseColumns;

public class UserInfoContent implements BaseColumns {

    public static final String AUTHORITIES = "com.example.chapter06_server.UserInfoProvider";

    // 数据格式完整格式:  content://com.example.chapter06_server.provider.UserInfoProvider/user

    // 访问内容提供器的URI
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    // 下面是该表的各个字段名称
    public static final String USER_NAME = "name";
    public static final String USER_AGE = "age";
    public static final String USER_HEIGHT = "height";
    public static final String USER_WEIGHT = "weight";
    public static final String USER_MARRIED = "married";
}
