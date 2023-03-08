package com.example.chapter12_network_communication.gson;

import com.example.chapter12_network_communication.gson.bean.Job;
import com.example.chapter12_network_communication.gson.bean.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.http.PartMap;

public class ObjectUnitTest {

    @Test
    public void testObject()
    {
        // java对象
        User user = new User("flyboy","086319",23,false);
        // Gson提供的Gson对象
        Gson gson = new Gson();

        // 序列化
        String jsonObject = gson.toJson(user);
        System.out.println("序列化对象: " + jsonObject);

        // 反序列化
        User user2 = gson.fromJson(jsonObject, User.class);
        System.out.println("User: " + user2);
        System.out.println(user2.getUsername() + " : " + user2.getAge());
    }

    @Test
    public void testNestedObject()
    {
        // java对象
        Job job = new Job("Android工程师",6500);
        User user = new User("flyboy","086319",23,false, job);
        // Gson提供的Gson对象
        Gson gson = new Gson();
        // 序列化
        String jsonObject = gson.toJson(user);
        System.out.println("序列化对象: " + jsonObject);
        // 反序列化
        User user2 = gson.fromJson(jsonObject, User.class);
        System.out.println("User: " + user2);
        System.out.println(user2.getUsername() + " : " + user2.getAge() + " : " + user2.getJob());
    }

    @Test
    public void testArray()
    {
        // 数组类型对象和普通对象一样，使用toJson/fromJson即可完成序列化与反序列化。

        User[] userArray = new User[4];
        // java对象
        userArray[0] = new User("flyboy","086319",23,false,new Job("Android工程师",6500));
        userArray[1] = new User("jack","123456",17,true,new Job());
        userArray[2] = new User("rose","fadffd",16,false);
        // Gson提供的Gson对象
        Gson gson = new Gson();
        // 序列化
        String json = gson.toJson(userArray);
        System.out.println("序列化数据 : " +json);
        // 反序列化
        User[] userArray2 = gson.fromJson(json, User[].class);

        System.out.println(userArray2[0]);
        System.out.println(userArray2[1]);
        System.out.println(userArray2[2]);
        System.out.println(userArray2[3]);
    }

    @Test
    public void testListObject()
    {
        // List集合类型对象需要注意的是，在反序列化时因为Java是伪泛型，泛型擦除会导致无法反序列化为List<User>，
        // 需要使用 Gson中的 TypeToken 完成反序列化。

        List<User> list = new ArrayList<>();
        list.add(new User("flyboy","086319",23,false, new Job("Android工程师",6500)));
        list.add(new User("jack","123456",18,true));
        list.add(null);
        // Gson提供的Gson对象
        Gson gson = new Gson();
        // 序列化
        String jsonObject = gson.toJson(list);
        System.out.println("序列化对象 : " + jsonObject);

        // 反序列化
        Type type = new TypeToken<List<User>>(){}.getType();
        List<User> list2 = gson.fromJson(jsonObject, type);

        System.out.println("反序列化0 : " + list2.get(0).getUsername());
        System.out.println("反序列化1 : " + list2.get(1));
        System.out.println("反序列化2 : " + list2.get(2));
    }

    @Test
    public void testSet()
    {
        // Set在反序列化时同样需要使用TypeToken完成反序列化。
        Set<User> set = new HashSet<>();
        set.add(new User("flyboy", "086319", 23,false, new Job("Android工程师",6500)));
        set.add(new User("jack", "123456", 18,true));
        set.add(null);
        // Gson提供的Gson对象
        Gson gson = new Gson();
        // 序列化
        String json = gson.toJson(set);
        System.out.println("序列化对象 : " + json);

        // 反序列化
        // 方式一:
        // 如果HashSet类型，则完全可以使用反序列为 List，因为两者序列化后的Json数据一致!
//        Type type = new TypeToken<List<User>>(){}.getType();
//        List<User> list = gson.fromJson(json,type);
//        System.out.println(list.get(0));
//        System.out.println(list.get(1));
//        System.out.println(list.get(2));

        // 方式二:
        Type type2 = new TypeToken<Set<User>>(){}.getType();
        Set<User> set2 = gson.fromJson(json, type2);
        Iterator<User> iterator = set2.iterator();
        while (iterator.hasNext())
        {
            User user = iterator.next();
            System.out.println("反序列化" +  user);
        }
    }

    @Test
    public void testExpose()
    {
        // java对象
        Job job = new Job("Android工程师",6500);
        User user = new User("flyboy","086319",23,false, job);

        // 如果希望指定GSON对某些字段配置是否参与序列化与反序列化.可以使用@Expose注解控制．
        // 同时使用GsonBuilder创建Gson对象:
        Gson gson = new GsonBuilder( ).excludeFieldsWithoutExposeAnnotation( ).create();
        // 序列化
        String jsonObject = gson.toJson(user);
        System.out.println("序列化对象: " + jsonObject);
        // 反序列化
        User user2 = gson.fromJson(jsonObject, User.class);
        System.out.println("User: " + user2);
        System.out.println(user2.getUsername() + " : " + user2.getAge() + " : " + user2.getJob());
    }

    @Test
    public void testJsonObject() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username","李鹏飞");
        jsonObject.put("password","123456");
        String string = jsonObject.toString();
        System.out.println(string);
    }
}
