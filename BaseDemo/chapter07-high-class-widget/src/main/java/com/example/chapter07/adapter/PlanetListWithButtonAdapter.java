package com.example.chapter07.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.chapter07.R;
import com.example.chapter07.bean.Planet;
import com.example.chapter07.util.ToastUtil;

import java.util.List;

public class PlanetListWithButtonAdapter extends BaseAdapter {

    private Context context;
    private List<Planet> planetList;

    public PlanetListWithButtonAdapter(Context context, List<Planet> planetList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_with_button, null);
            // 实例化视图持有者
            holder = new ViewHolder();
            // 设置列表项布局中的各个控件
            // 获取 视图对象中的布局控件
            holder.linearLayout  = convertView.findViewById(R.id.ll_item);
            // 其他普通控件
            holder.iv_icon = convertView.findViewById(R.id.iv_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            holder.tv_desc = convertView.findViewById(R.id.tv_desc);
            holder.btn_operator = convertView.findViewById(R.id.btn_operator);
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
        // 代码中setDescendantFocusability()函数可阻止下级控件获得焦点,避免阻塞列表视图的点击事件
        holder.linearLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        // 设置视图拥有者的其他事件
        holder.iv_icon.setImageResource(planet.image);
        holder.tv_name.setText(planet.name);
        holder.tv_desc.setText(planet.desc);
        holder.btn_operator.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ToastUtil.show(context,"按钮被点击了," + planet.name);
            }
        });

        return convertView;
    }

    public final class ViewHolder
    {
        public LinearLayout linearLayout;
        public ImageView iv_icon;
        public TextView tv_name;
        public TextView tv_desc;
        public Button btn_operator;
    }
}
