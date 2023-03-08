package com.example.mybluetooth.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bluetoothdemo.R;
import com.example.mybluetooth.enity.BlueToothDevice;

import java.util.List;
import java.util.Map;

public class BlueListAdapter extends BaseAdapter {

    private Context context;
    private List<BlueToothDevice> deviceList;
    private String[] deviceStateArray = { "未连接","连接中","已绑定","已连接"};
    public static int CONNECTED = 13;

    public BlueListAdapter(Context context,List<BlueToothDevice> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
    }

    @Override
    public int getCount() {
        return deviceList.size();
    }

    @Override
    public Object getItem(int position) {
        return deviceList.get(position);
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bluetooth,null);
            holder.tv_blue_name = convertView.findViewById(R.id.tv_blue_name);
            holder.tv_blue_address = convertView.findViewById(R.id.tv_blue_address);
            holder.tv_blue_state = convertView.findViewById(R.id.tv_blue_state);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        BlueToothDevice device = deviceList.get(position);
        holder.tv_blue_name.setText(device.getName());
        holder.tv_blue_address.setText(device.getAddress());
        if(device.getState() == BluetoothDevice.BOND_NONE)
        {
            // 未连接
            holder.tv_blue_state.setText(deviceStateArray[0]);
        }
        else if(device.getState() == BluetoothDevice.BOND_BONDING)
        {
            // 连接中
            holder.tv_blue_state.setText(deviceStateArray[1]);
        }
        else if(device.getState() == BluetoothDevice.BOND_BONDED)
        {
            // 已绑定
            holder.tv_blue_state.setText(deviceStateArray[2]);
        }
        else if(device.getState() == CONNECTED)
        {
            // 已连接
            holder.tv_blue_state.setText(deviceStateArray[3]);
        }
        return convertView;
    }

    public final class ViewHolder
    {
        public TextView tv_blue_name;
        public TextView tv_blue_address;
        public TextView tv_blue_state;
    }
}
