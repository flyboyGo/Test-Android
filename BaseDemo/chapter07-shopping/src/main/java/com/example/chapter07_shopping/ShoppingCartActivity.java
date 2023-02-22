package com.example.chapter07_shopping;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_shopping.adapter.CartAdapter;
import com.example.chapter07_shopping.database.ShoppingDBHelper;
import com.example.chapter07_shopping.enity.Cart;
import com.example.chapter07_shopping.enity.Goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCartActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private TextView tv_count;
    // 商品列表根视图
    private ListView lv_cart;
    // 数据库帮助器
    private ShoppingDBHelper shoppingDBHelper;
    //  声明一个购物车商品信息
    private List<Cart> cartList;
    // 声明一个根据商品编号查找商品信息的映射，把商品信息缓存起来，这样不用每一次都去查询数据库
    private Map<Integer, Goods> goodsMap = new HashMap<>();
    // 总金额
    private TextView tv_total_price;
    // 空视图
    private LinearLayout ll_empty;
    private LinearLayout ll_content;
    // 适配器
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText("购物车");

        tv_count = findViewById(R.id.tv_count);
        tv_count.setText(String.valueOf(MyApplication.getInstance().goodsCount));


        // 获取商品列表区的布局，以便添加子视图(ListView)
        lv_cart = findViewById(R.id.lv_cart);

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
    private void showCart()
    {
        // 查询购物车数据库表中所有的商品信息
        cartList = shoppingDBHelper.queryAllCartGoods();
        if(cartList.size() == 0)
        {
            return;
        }
        // 遍历
        for(Cart cart : cartList) {
            // 根据商品的编号，查询商品的详细信息
            Goods goods = shoppingDBHelper.queryGoodsById(cart.goodsId);
            goodsMap.put(goods.id, goods);
            cart.goods = goods;
        }
        // 实例化适配器
        cartAdapter = new CartAdapter(this, cartList);
        lv_cart.setAdapter(cartAdapter);
        // 给商品行添加点击事件，点击商品跳到商品的详情页
        lv_cart.setOnItemClickListener(this);
        // 给商品行添加长按事件，长按商品行删除该商品
        lv_cart.setOnItemLongClickListener(this);

        // 重新计算购物车中的商品总数量
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
            // 刷新该适配器,更新视图
            cartAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(this, ShoppingChannelActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
    }

    // 点击，跳转到商品详情
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(ShoppingCartActivity.this,ShoppingDetailActivity.class);
        intent.putExtra("goods_id",cartList.get(position).goodsId);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Cart cart = cartList.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCartActivity.this);
        builder.setMessage("是否从购物车中删除" + cart.goods.name + "?");
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 从List中删除该商品的信息
                cartList.remove(position);
                // 刷新该适配器,更新视图
                cartAdapter.notifyDataSetChanged();
                // 删除当前商品
                deleteGoods(cart);
            }
        });
        builder.setNegativeButton("否",null);
        builder.create().show();
        return true;
    }
}