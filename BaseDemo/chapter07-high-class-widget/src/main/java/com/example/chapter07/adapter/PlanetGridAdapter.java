package com.example.chapter07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chapter07.R;
import com.example.chapter07.bean.Planet;

import java.util.List;

public class PlanetGridAdapter extends BaseAdapter {

    private Context context;
    private List<Planet> planetList;

    public PlanetGridAdapter(Context context, List<Planet> planetList) {
        this.context = context;
        this.planetList = planetList;
    }

    // 获取列表项的个数
    @Override
    public int getCount() {
        return planetList.size();
    }


    // 返回指定下标的列表项
    @Override
    public Object getItem(int position) {
        return planetList.get(position);
    }

    // 返回指定列表项的id
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 返回列表中列表项需要每个视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 视图控件持有者
        ViewHolder holder;
        if(convertView == null)
        {
            // 根据布局文件item_list.xml生成转换成视图对象,布局加载器
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid, null);
            // 实例化视图持有者
            holder = new ViewHolder();
            // 设置列表项布局中的各个控件
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            // 将视图控件持有者保存到转换视图中
            convertView.setTag(holder);
        }
        else
        {
            // 获取复用视图中的视图控件持有者
            holder =(ViewHolder) convertView.getTag();
        }

        // 设置列表项布局中的各个控件的内容
        Planet planet = planetList.get(position);
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);

        return convertView;
    }

    public final class ViewHolder
    {
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
    }
}
