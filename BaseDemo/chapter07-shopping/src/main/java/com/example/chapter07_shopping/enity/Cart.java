package com.example.chapter07_shopping.enity;

public class Cart {
    public int id;
    // 商品编号
    public int goodsId;
    // 商品数量
    public int count;
    // 商品信息
    public Goods goods;

    public Cart(){}

    public Cart(int id, int goodsId, int count) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goods = new Goods();
    }
}
