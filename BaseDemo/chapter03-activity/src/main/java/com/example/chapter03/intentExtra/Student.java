package com.example.chapter03.intentExtra;

import android.os.Parcel;
import android.os.Parcelable;

// 此类成为Parcelable接口的子类, 此类的实例对象就具备传递的资格
// Android推荐使用Parcelable (Serializable接口是基于JVM平台), 比Serializable 的性能高很多
public class Student implements Parcelable {

    // 自定义的成员属性
    private String name;
    private int age;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


    // 注意：读取的顺序 和 写入的顺序 必须一致，否则报错 (先写 后读)
    // ParcelableStartActivity 完成写入(将数据写入到Parcel对象,传递)
    // ParcelableEndActivity   完成读取(从Parcel对象中读取数据,完成Student对象的拼接)

    // 构造函数
    // 从Parcel对象读取成员，赋值给name、age
    protected Student(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    // 把属性写入到Parcel对象中
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    // 系统扩展函数,无序关心
    @Override
    public int describeContents() {
        return 0;
    }

    // CREATOR 系统内部调用 名称是一定的(CREATOR)
    public static final Creator<Student> CREATOR = new Creator<Student>() {

        // 创建Student对象,并且Parcel完成构建，传递给Student的构造函数(成员属性就可以从Parcel对象中获取)
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };
}
