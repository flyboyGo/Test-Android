package com.example.chapter03.intentExtra;

import java.io.Serializable;

// 必须实现Serializable接口,此类的实例对象才有传递的资格
public class User implements Serializable {
    private int id;
    private String name;
    private int age;

    public User() {

    }

    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
