package com.example.chapter07_shopping.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter07_shopping.R;
import com.example.chapter07_shopping.enity.Cart;

import java.util.List;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @Override
    public int getCount() {
        return cartList.size();
    }

    @Override
    public Object getItem(int position) {
        return cartList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null)
        {
            holder = new ViewHolder();

            // 获取布局文件item_cart.xml的根视图
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, null);
            holder.iv_thumb = convertView.findViewById(R.id.iv_thumb);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.tv_count = convertView.findViewById(R.id.tv_count);
            holder.tv_price = convertView.findViewById(R.id.tv_price);
            holder.tv_sum = convertView.findViewById(R.id.tv_sum);

            convertView.setTag(holder);
        }
        else
        {
            holder =(ViewHolder) convertView.getTag();
        }
        Cart cart = cartList.get(position);
        holder.iv_thumb.setImageURI(Uri.parse(cart.goods.picPath));
        holder.tv_name.setText(cart.goods.name);
        holder.tv_desc.setText(cart.goods.description);
        holder.tv_count.setText(String.valueOf(cart.count)) ;
        holder.tv_price.setText(String.valueOf(cart.goods.price)) ;
        // 设置商品总价
        holder.tv_sum.setText(String.valueOf((int)(cart.count * cart.goods.price)));

        return convertView;
    }

    public final class ViewHolder
    {
        public ImageView iv_thumb;
        public TextView tv_name;
        public TextView tv_desc;
        public TextView tv_count;
        public TextView tv_price;
        public TextView tv_sum ;
    }
}
