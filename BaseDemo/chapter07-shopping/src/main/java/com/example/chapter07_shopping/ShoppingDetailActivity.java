package com.example.chapter07_shopping;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.chapter07_shopping.database.ShoppingDBHelper;
import com.example.chapter07_shopping.enity.Goods;

public class ShoppingDetailActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private ShoppingDBHelper shoppingDBHelper;
    private int goods_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_detail);

        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);

        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        shoppingDBHelper = ShoppingDBHelper.getInstance(this);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        showGoodsDetail();
    }

    private void showGoodsDetail() {
        goods_id = getIntent().getIntExtra("goods_id", 0);
        if(goods_id > 0)
        {
            // 根据商品的id,从数据库中查询商品的详细信息
            Goods goods = shoppingDBHelper.queryGoodsById(goods_id);
            tv_title.setText(goods.name);
            tv_goods_desc.setText(goods.description);
            tv_goods_price.setText(String.valueOf((int)goods.price));
            iv_goods_pic.setImageURI(Uri.parse(goods.picPath));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_cart:
                Intent intent = new Intent(this, ShoppingCartActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_add_cart:
                addToCart(goods_id);
                break;
        }
    }

    private void addToCart(int goods_id) {
        shoppingDBHelper.insertCart(goods_id);
        // 购物车数量加一
        int count = ++MyApplication.getInstance().goodsCount;
        tv_count.setText(String.valueOf(count));
        Toast.makeText(this,"已添加到购物车",Toast.LENGTH_SHORT).show();
    }
}