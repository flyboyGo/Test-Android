package com.example.chapter12_network_communication.gson.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    private String username;
    @Expose
    private String password;
    @Expose
    private int age;
    @Expose
    private boolean isStudent;

    @Expose(serialize = false,deserialize = false)
    private Job job;


    // serialize:是否参与序列化,deserialize是否参与反序列化
    // 默认两个选项是true
    @Expose(serialize = false,deserialize = false)
    // 借助@SerializedName注解控制JSON子段中Key的命名。
    @SerializedName("class")
    private int cls;

    public User() {
    }

    public User(String username, String password, int age, boolean isStudent) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.isStudent = isStudent;
    }

    public User(String username, String password, int age, boolean isStudent, Job job) {
        this.username = username;
        this.password = password;
        this.age = age;
        this.isStudent = isStudent;
        this.job = job;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", isStudent=" + isStudent +
                ", job=" + job +
                '}';
    }
}
