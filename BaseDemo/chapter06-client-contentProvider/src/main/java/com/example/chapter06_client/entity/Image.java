package com.example.chapter06_client.entity;

public class Image {
    public long id;
    public String name;
    public long size;
    public String path; // 文件的路径

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size=" + size +
                ", path='" + path + '\'' +
                '}';
    }
}
