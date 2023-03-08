package com.example.chapter01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter01.database.ShoppingDBHelper;
import com.example.chapter01.enity.Goods;

import java.util.List;

public class ShoppingChannelActivity extends AppCompatActivity implements View.OnClickListener {
    // 声明一个商品数据库的帮助对象
    private ShoppingDBHelper shoppingDBHelper = null;
    private TextView tv_count;
    private GridLayout gl_channel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_channel);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("手机商场");

        tv_count = findViewById(R.id.tv_count);
        gl_channel = findViewById(R.id.gl_channel);

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

    private void showGoods() {
        // 商品条目是一个线性布局，设置布局的宽度为屏幕的一半
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth / 2, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 查询商品数据库中所有的商品记录
        List<Goods> goodsList = shoppingDBHelper.queryAllGoods();
        // 移除下面的所有的子视图
        gl_channel.removeAllViews();

        for(Goods goods : goodsList)
        {
            // 获取布局文件item_goods.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_goods, null);

            // 给各个控件设置值
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            iv_thumb.setImageURI(Uri.parse(goods.picPath));

            TextView tv_name = view.findViewById(R.id.tv_name);
            tv_name.setText(goods.name);

            TextView tv_price = view.findViewById(R.id.tv_price);
            tv_price.setText(String.valueOf((int)goods.price));

            // 给加入购物车添加点击事件
            Button btn_add = view.findViewById(R.id.btn_add);
            btn_add.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    addToCart(goods.id,goods.name);
                }
            });

            // 点击商品图片，跳转到商品详情页面
            iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShoppingChannelActivity.this,ShoppingDetailActivity.class);
                    intent.putExtra("goods_id",goods.id);
                    startActivity(intent);
                }
            });

            // 把商品视图添加到网格视图
            gl_channel.addView(view,layoutParams);
        }
    }

    private void addToCart(int goodsId, String goodName) {
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