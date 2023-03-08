package com.example.chapter01.enity;

public class Cart {
    public int id;
    // 商品编号
    public int goodsId;
    // 商品数量
    public int count;

    public Cart(){}

    public Cart(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
    }
}
