package com.example.chapter07_shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_shopping.adapter.GoodsAdapter;
import com.example.chapter07_shopping.database.ShoppingDBHelper;
import com.example.chapter07_shopping.enity.Goods;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener, GoodsAdapter.AddCartListener {
    // 声明一个商品数据库的帮助对象
    private ShoppingDBHelper shoppingDBHelper = null;
    private TextView tv_count;
    private GridView gV_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.tv_count);
        gV_channel = findViewById(R.id.gv_channel);

        shoppingDBHelper = ShoppingDBHelper.getInstance(this);
        shoppingDBHelper.openReadLink();
        shoppingDBHelper.openWriteLink();

        // 从数据库中查询商品信息，并展示
        showGoods();

        // 设置返回按钮、购物车按钮的点击事件
        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 查询购物车商品的总数量，并展示
        showCartInfoTotal();
    }

    // 查询购物车商品的总数量，并展示
    private void showCartInfoTotal() {
        int count = shoppingDBHelper.countCartInfo();
        MyApplication.getInstance().goodsCount = count;
        tv_count.setText(String.valueOf(count));
    }

    // 加载商品页面
    private void showGoods() {
        // 查询商品数据库中所有的商品记录
        List<Goods> goodsList = shoppingDBHelper.queryAllGoods();
        GoodsAdapter goodsAdapter = new GoodsAdapter(this,goodsList,this);
        gV_channel.setAdapter(goodsAdapter);
    }

    // 把指定的编号的商品添加到购物车中
    @Override
    public void addToCart(int goodsId, String goodName) {
        shoppingDBHelper.insertCart(goodsId);
        // 购物车数量加一
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        Toast.makeText(this,"已添加到购物车",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shoppingDBHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                Intent intent = new Intent(this,ShoppingCartActivity.class);
                // 设置启动标识，避免多次返回同一个页面
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}