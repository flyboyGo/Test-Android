package com.example.room.base.enity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "student")
public class Student {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private int id;

    @ColumnInfo(name = "name", typeAffinity = ColumnInfo.TEXT)
    private String name;

    @ColumnInfo(name = "age", typeAffinity = ColumnInfo.INTEGER)
    private int age;

    @ColumnInfo(name = "sex", typeAffinity = ColumnInfo.INTEGER)
    private int sex;

    @Ignore
    private boolean flag;

    @Ignore
    public Student() {
    }

    @Ignore
    public Student(int id) {
        this.id = id;
    }

    @Ignore
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Student(int id, String name, int age, int sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    @Ignore
    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getSex() {
        return sex;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", flag=" + flag +
                '}';
    }
}
