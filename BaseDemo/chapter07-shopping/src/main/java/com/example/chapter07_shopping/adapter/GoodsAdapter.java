package com.example.chapter07_shopping.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter07_shopping.R;
import com.example.chapter07_shopping.ShoppingChannelActivity;
import com.example.chapter07_shopping.ShoppingDetailActivity;
import com.example.chapter07_shopping.enity.Goods;

import java.util.List;

public class GoodsAdapter extends BaseAdapter {

    private Context context;
    private List<Goods> goodsList;

    public GoodsAdapter(Context context, List<Goods> goodsList,AddCartListener  addCartListener) {
        this.context = context;
        this.goodsList = goodsList;
        this.addCartListener = addCartListener;
    }

    // 声明一个加入购物车的监听器对象
    private AddCartListener addCartListener;

    //定义一个加入购物车的监听接口
    public interface AddCartListener{
        void addToCart(int goodsId, String goodsName);
    }

    @Override
    public int getCount() {
        return goodsList.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Goods goods = goodsList.get(position);
        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.btn_add = convertView.findViewById(R.id.btn_add);
            convertView.setTag(holder);
        }
        else
        {
            holder =(ViewHolder) convertView.getTag();
        }

        holder.iv_thumb.setImageURI(Uri.parse(goods.picPath));
        holder.tv_name.setText(goods.name);
        holder.tv_price.setText(String.valueOf((int) goods.price));


        // 给加入购物车添加点击事件
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 方式一
//            ShoppingChannelActivity activity =  (ShoppingChannelActivity)context;
//            activity.addToCart(goods.id, goods.name);

                // 方式二
                addCartListener.addToCart(goods.id,goods.name);
            }
        });

        // 点击商品图片，跳转到商品详情页面
        holder.iv_thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShoppingDetailActivity.class);
                intent.putExtra("goods_id",goods.id);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

    public final  class ViewHolder{
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_price;
        public Button btn_add;
    }
}
