package com.example.chapter01.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chapter01.MyApplication;
import com.example.chapter01.enity.Cart;
import com.example.chapter01.enity.Goods;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDBHelper extends SQLiteOpenHelper {
    // 数据库名
    private static final String DB_NAME = "shopping.db";
    // 表名
    // 商品信息表
    private static final String TABLE_GOODS_INFO = "goods_info";
    // 购物车信息表
    private static final String TABLE_CART_INFO = "cart_info";

    // 版本号,版本号发生改变时，会自动执行onUpgrade函数
    private static final int DB_VERSION = 1;

    // 数据库帮助器
    private static ShoppingDBHelper mHelper = null;

    //数据库连接
    private SQLiteDatabase mRDB = null;
    private SQLiteDatabase mWDB = null;

    private ShoppingDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private ShoppingDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static ShoppingDBHelper getInstance(Context context)
    {
        if(mHelper == null)
            mHelper = new ShoppingDBHelper(context);

        return mHelper;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink()
    {
        if(mRDB == null || !mRDB.isOpen())
        {
            mRDB = mHelper.getReadableDatabase();
        }
        return mRDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink()
    {
        if(mWDB == null || !mWDB.isOpen())
        {
            mWDB = mHelper.getWritableDatabase();
        }
        return mWDB;
    }

    // 关闭数据库
    public void closeLink()
    {
        if(mRDB != null && mRDB.isOpen())
        {
            mRDB.close();
            mRDB = null;
        }
        if(mWDB != null && mWDB.isOpen())
        {
            mWDB.close();
            mWDB = null;
        }
    }

    // 创建数据库，执行建表语句
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "";
        // 创建商品信息表
           sql = "CREATE TABLE IF NOT EXISTS " + TABLE_GOODS_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name VARCHAR NOT NULL," +
                " description VARCHAR NOT NULL," +
                " price FLOAT NOT NULL," +
                " pic_path VARCHAR NOT NULL);";
        db.execSQL(sql);

        // 创建购物车信息表
           sql = "CREATE TABLE IF NOT EXISTS " + TABLE_CART_INFO + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " goods_id INTEGER NOT NULL," +
                " count INTEGER NOT NULL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // 添加多条商品信息
    public void insertGoods(List<Goods> list)
    {
        // 插入多条数据，要么都超过，要么都失败
        try {
            mWDB.beginTransaction();
            for(Goods goods : list)
            {
                ContentValues values = new ContentValues();
                values.put("name",goods.name);
                values.put("description",goods.description);
                values.put("price",goods.price);
                values.put("pic_path",goods.picPath);
                mWDB.insert(TABLE_GOODS_INFO,null,values);
            }
            mWDB.setTransactionSuccessful();
        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            mWDB.endTransaction();
        }
    }

    // 查询所有商品的信息
    public List<Goods> queryAllGoods(){
        String sql = "select * from " + TABLE_GOODS_INFO;
        List<Goods> goodsList = new ArrayList<>();
        Cursor cursor = mRDB.rawQuery(sql, null);
        while(cursor.moveToNext())
        {
            Goods goods = new Goods();
            goods.id = cursor.getInt(0);
            goods.name = cursor.getString(1);
            goods.description = cursor.getString(2);
            goods.price = cursor.getFloat(3);
            goods.picPath = cursor.getString(4);
            goodsList.add(goods);
        }
        cursor.close();
        return  goodsList;
    }

    // 添加商品到购物车表
    public void insertCart(int goodsId) {
        Cart cart = queryCartByGoodsId(goodsId);
        ContentValues values = new ContentValues();
        values.put("goods_id",goodsId);
        if(cart == null)
        {
            // 如果购物车中不存在该商品，添加商品信息
            values.put("count",1);
            mWDB.insert(TABLE_CART_INFO,null,values);
        }
        else
        {
            // 如果商品存在，更新商品数量
            values.put("_id",cart.id);
            values.put("count", ++cart.count);
            mWDB.update(TABLE_CART_INFO,values,"_id = ?",new String[]{String.valueOf(cart.id)});
        }

    }

    // 查询购物车中的商品信息，通过商品id
    public Cart queryCartByGoodsId(int goodsId) {
        Cursor cursor = mRDB.query(TABLE_CART_INFO, null, "goods_id = ?", new String[]{String.valueOf(goodsId)}, null, null, null);
        Cart cart = null;
        if(cursor.moveToNext())
        {
            cart = new Cart();
            cart.id = cursor.getInt(0);
            cart.goodsId = cursor.getInt(1);
            cart.count = cursor.getInt(2);
        }
        return cart;
    }

    // 统计购物车中的商品数量
    public int countCartInfo() {
        int count = 0;
        String sql = "select sum(count) from " + TABLE_CART_INFO;
        Cursor cursor = mRDB.rawQuery(sql, null);
        if(cursor.moveToNext())
        {
            count = cursor.getInt(0);
        }
        return count;
    }

    // 查询购物车中所有的商品信息
    public List<Cart> queryAllCartGoods() {
        List<Cart> cartList = new ArrayList<>();
        Cart cart = null;

        Cursor cursor = mRDB.query(TABLE_CART_INFO,null,null,null,null,null,null);
        while(cursor.moveToNext())
        {
            cart = new Cart();
            cart.id = cursor.getInt(0);
            cart.goodsId = cursor.getInt(1);
            cart.count = cursor.getInt(2);
            cartList.add(cart);
        }
        cursor.close();
        return cartList;
    }

    // 根据商品的编号，查询商品的详细信息
    public Goods queryGoodsById(int goodsId) {
        Goods goods = null;
        Cursor cursor = mRDB.query(TABLE_GOODS_INFO, null, "_id = ?", new String[]{String.valueOf(goodsId)}, null, null, null);
        if(cursor.moveToNext())
        {
            goods = new Goods();
            goods.id = cursor.getInt(0);
            goods.name = cursor.getString(1);
            goods.description = cursor.getString(2);
            goods.price = cursor.getFloat(3);
            goods.picPath = cursor.getString(4);
        }
        return goods;
    }

    // 根据商品的id,从购物车中删除某个商品
    public void deleteCartByGoodsId(int goodsId) {
        mWDB.delete(TABLE_CART_INFO,"goods_id=?",new String[]{String.valueOf(goodsId)});
    }

    // 删除所有购物车的信息
    public void deleteAllCart()
    {
        mWDB.delete(TABLE_CART_INFO,"1=1",null);
    }
}
