package com.example.chapter01.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chapter01.R;
import com.example.chapter01.enity.BillInfo;

import java.util.List;

public class BillListAdapter extends BaseAdapter {

    private final  Context context;
    private final List<BillInfo> billInfoList;

    public BillListAdapter(Context context, List<BillInfo> billInfoList) {
        this.context = context;
        this.billInfoList = billInfoList;
    }

    @Override
    public int getCount() {
        return billInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return billInfoList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bill,null);
            holder.tv_date = convertView.findViewById(R.id.tv_date);
            holder.tv_remark = convertView.findViewById(R.id.tv_remark);
            holder.tv_amount = convertView.findViewById(R.id.tv_amount);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        BillInfo billInfo = billInfoList.get(position);
        holder.tv_date.setText(billInfo.date);
        holder.tv_remark.setText(billInfo.remark);
        holder.tv_amount.setText(String.format("%s%d元",billInfo.type == 0 ? "+" : "-",(int)billInfo.amount));

        return convertView;
    }

    public final class ViewHolder
    {
        public TextView tv_date;
        public TextView tv_remark;
        public TextView tv_amount;
    }
}
