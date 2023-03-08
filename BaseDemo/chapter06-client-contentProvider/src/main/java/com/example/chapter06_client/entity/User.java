package com.example.chapter06_client.entity;

public class User {
    public int id;
    public String name;
    public int age;
    public float height;
    public float weight;
    public boolean married;

    public User()
    {

    }

    public User(String name, int age, float height, float weight, boolean married) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.married = married;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", weight=" + weight +
                ", married=" + married +
                '}';
    }

}
