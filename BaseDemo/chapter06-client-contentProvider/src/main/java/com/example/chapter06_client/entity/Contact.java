package com.example.chapter06_client.entity;

public class Contact {
    public String name; // 联系人姓名
    public String phone; // 联系号码
    public String email; // 邮箱

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
