package com.example.chapter01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chapter01.database.ShoppingDBHelper;
import com.example.chapter01.enity.Cart;
import com.example.chapter01.enity.Goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_count;
    // 商品列表根视图
    private LinearLayout ll_cart;
    private ShoppingDBHelper shoppingDBHelper;
    //  声明一个购物车商品信息
    private List<Cart> cartList;
    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库
    private Map<Integer,Goods> goodsMap = new HashMap<>();
    // 总金额
    private TextView tv_total_price;
    // 空视图
    private LinearLayout ll_empty;
    private LinearLayout ll_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));

        // 获取商品列表区的布局，以便添加子视图
        ll_cart = findViewById(R.id.ll_cart);

        // 获取数据库帮助器
        shoppingDBHelper = ShoppingDBHelper.getInstance(this);

        //总金额
        tv_total_price = findViewById(R.id.tv_total_price);

        //返回按钮
        findViewById(R.id.iv_back).setOnClickListener(this);
        
        // 获取视图
        ll_empty = findViewById(R.id.ll_empty);
        // 获取商品表头视图
        ll_content = findViewById(R.id.ll_content);

        // 清空按钮
        findViewById(R.id.btn_clear).setOnClickListener(this);
        // 结算按钮
        findViewById(R.id.btn_settle).setOnClickListener(this);
        // 返回商场按钮
        findViewById(R.id.btn_shopping_channel).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 展示购物车中的商品列表
        showCart();
    }

    // 展示购物车中的商品列表
    private void showCart() {
        // 移除下面的所有的子视图
        ll_cart.removeAllViews();
        // 查询购物车数据库表中所有的商品信息
         cartList = shoppingDBHelper.queryAllCartGoods();
        if(cartList.size() == 0)
        {
            return;
        }

        for(Cart cart : cartList)
        {
            // 根据商品的编号，查询商品的详细信息
            Goods goods = shoppingDBHelper.queryGoodsById(cart.goodsId);
            goodsMap.put(goods.id,goods);

            // 获取布局文件item_cart.xml的根视图
            View view = LayoutInflater.from(this).inflate(R.layout.item_cart, null);
            ImageView iv_thumb = view.findViewById(R.id.iv_thumb);
            TextView tv_name = view.findViewById(R.id.tv_name);
            TextView tv_desc = view.findViewById(R.id.tv_desc);
            TextView tv_count = view.findViewById(R.id.tv_count);
            TextView tv_price = view.findViewById(R.id.tv_price);
            TextView tv_sum = view.findViewById(R.id.tv_sum);

            iv_thumb.setImageURI(Uri.parse(goods.picPath));
            tv_name.setText(goods.name);
            tv_desc.setText(goods.description);
            tv_count.setText(String.valueOf(cart.count));
            tv_price.setText(String.valueOf(goods.price));
            tv_sum.setText(String.valueOf((int)(cart.count * goods.price)));

            // 给商品行添加长按事件
            view.setOnLongClickListener(new View.OnLongClickListener(){
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
                    builder.setMessage("是否从购物车中删除" + goods.name + "?");
                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 移除当前视图
                            ll_cart.removeView(v);
                            // 删除当前商品
                            deleteGoods(cart);
                        }
                    });
                    builder.setNegativeButton("否",null);
                    builder.create().show();
                    return true;
                }
            });

            // 给商品行添加点击事件，跳转到商品的详情页面
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ShoppingCartActivity.this,ShoppingDetailActivity.class);
                    intent.putExtra("goods_id",goods.id);
                    startActivity(intent);
                }
            });

            // 往购物车列表添加商品信息行
            ll_cart.addView(view);
        }
        
        // 重新计算购物车的商品总金额
        refreshTotalPrice();
    }

    private void deleteGoods(Cart cart) {
        MyApplication.getInstance().goodsCount -= cart.count;
        // 从购物车数据库中删除该商品
        shoppingDBHelper.deleteCartByGoodsId(cart.goodsId);
        // 从购物车的列表中删除商品
        Cart removeCart = null;
        for(Cart cart2 : cartList)
        {
            if(cart2.goodsId == cart.goodsId)
            {
                removeCart = cart2;
                break;
            }
        }
        cartList.remove(removeCart);

        // 显示最新的商品数量
        showCount();

        Toast.makeText(this,"已从购物车中删除" + goodsMap.get(cart.goodsId).name,Toast.LENGTH_SHORT).show();
        goodsMap.remove(cart.goodsId);

        // 刷新总金额
        refreshTotalPrice();
    }

    // 显示购物车最新的商品数量
    private void showCount() {
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));
        // 购物车中没有商品
        if(MyApplication.getInstance().goodsCount == 0)
        {
            ll_empty.setVisibility(View.VISIBLE);
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
        }
        else
        {
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    // 重新计算总金额
    private void refreshTotalPrice() {
        int totalPrice = 0;
        for(Cart cart : cartList)
        {
            Goods goods = goodsMap.get(cart.goodsId);
            totalPrice += goods.price * cart.count;
        }
        tv_total_price.setText(String.valueOf(totalPrice));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_clear:
                // 清空数据库
                shoppingDBHelper.deleteAllCart();
                MyApplication.getInstance().goodsCount = 0;
                // 显示最新的商品数量
                showCount();
                Toast.makeText(this, "购物车已清空",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_settle:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("结算商品");
                builder.setMessage("支付功能还未开通");
                builder.setPositiveButton("明白",null);
                builder.create().show();
                break;
            case R.id.btn_shopping_channel:
                Intent intent = new Intent(this,ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }
}