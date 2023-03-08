package com.example.mybluetooth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BlendMode;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bluetoothdemo.R;
import com.example.mybluetooth.adapter.BlueListAdapter;
import com.example.mybluetooth.enity.BlueToothDevice;
import com.example.mybluetooth.task.BlueAcceptTask;
import com.example.mybluetooth.task.BlueConnectTask;
import com.example.mybluetooth.util.BluetoothUtil;
import com.example.mybluetooth.util.PermissionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    // 蓝牙开启按钮
    private CheckBox ck_bluetooth;
    // 声明一个文本视图对象
    private TextView tv_discovery;
    // 设备列表
    private ListView listView;
    // 设备列表的数据适配器
    private BlueListAdapter blueListAdapter;
    // 蓝牙设备地址的map映射
    private Map<String, Integer> deviceMap = new HashMap<>();
    // 设备列表
    private List<BlueToothDevice> deviceList = new ArrayList<>();

    // 主线程
    private final Handler mainHandler = new Handler(Looper.getMainLooper());
    // 声明一个蓝牙适配器对象
    private BluetoothAdapter bluetoothAdapter;
    // 声明一个蓝牙设备的套接字对象
    private BluetoothSocket blueSocket;

    // 是否允许设备可见性请求码
    private  static  final  int REQUEST_CODE_DISCOVER = 1;
    // 蓝牙动态权限请求码
    private  static final int REQUEST_CODE_BLUETOOTH = 2;
    // 蓝牙定位权限集合
    private static final String[]PERMISSIONS_BLUETOOTH = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化各项控件
        initWidget();
        //初始化系统的蓝牙适配器
        initBlueToothAdapter();
        // 初始化蓝牙设备列表
        initBlueDeviceList();
        // 蓝牙已打开,切换按钮的状态
        if(BluetoothUtil.getBlueToothStatus())
        {
            ck_bluetooth.setChecked(true);
        }
    }

    // 初始化各项控件
    private void initWidget() {
        ck_bluetooth = findViewById(R.id.ck_bluetooth);
        ck_bluetooth.setOnCheckedChangeListener(this);
        tv_discovery = findViewById(R.id.tv_discovery);
        listView = findViewById(R.id.lv_bluetooth);
        listView.setOnItemClickListener(this);
    }

    // 初始化系统的蓝牙适配器
    private void initBlueToothAdapter() {
        // 获取系统默认的蓝牙适配器
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "当前设备未找到蓝牙功能", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    // 初始化蓝牙设备列表
    private void initBlueDeviceList() {
        // 清空蓝牙设备列表
        deviceList.clear();
        // 清空蓝夜设备地址映射
        deviceMap.clear();
        // 获取已经配对过的的蓝牙设备集合
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        // 遍历蓝牙设备
        for (BluetoothDevice device : bondedDevices) {
            // 添加到蓝牙设备列表中
            deviceList.add(new BlueToothDevice(device.getName(),device.getAddress(),device.getBondState()));
            // 添加到蓝牙地址映射中
            deviceMap.put(device.getAddress(),device.getBondState());
        }
        // 创建蓝牙设备列表的数据适配器
        blueListAdapter = new BlueListAdapter(this,deviceList);
        // 蓝牙设备列表设置数据适配器
        listView.setAdapter(blueListAdapter);
    }

    // 蓝牙按钮的监听事件
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(buttonView.getId() == R.id.ck_bluetooth)
        {
            if(isChecked)
            {
                // 开启蓝牙功能
                ck_bluetooth.setText("蓝牙已开");
                // 设置设备的蓝牙状态为开启的状态
                if (!BluetoothUtil.getBlueToothStatus()) {
                    BluetoothUtil.setBlueToothStatus(true);
                }
                // 动态检查蓝牙的各项权限
                PermissionUtil.checkPermission(this,PERMISSIONS_BLUETOOTH,REQUEST_CODE_BLUETOOTH);
                // 设置设备的可见性(内部开启蓝牙设备扫描)
                mainHandler.post(Discoverable);
                // 开启监听服务(不可已放在主线程中)
                // mainHandler.postDelayed(acceptBlueTooth,500);
            }
            else
            {
                // 取消蓝牙的搜索
                cancelDiscovery();
                // 关闭蓝牙功能
                BluetoothUtil.setBlueToothStatus(false);
                ck_bluetooth.setText("蓝牙已关");
                tv_discovery.setText("蓝牙搜索已关闭");
                // 清空数据
                deviceList.clear();
                deviceMap.clear();
                blueListAdapter.notifyDataSetChanged();
            }
        }
    }


    // 弹出是否允许扫描蓝牙设备的选择对话框
    // 启用设备可见性
    private Runnable Discoverable = new Runnable()
    {
        public void run() {
            // Android8.0要在已打开蓝牙功能时才会弹出下面的选择窗
            if (BluetoothUtil.getBlueToothStatus()) { // 已经打开蓝牙
                // 弹出是否允许扫描蓝牙设备的选择对话框
                // 启用设备可见性
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                // 设置可见性的时间，300秒
                intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,300);
                startActivityForResult(intent, REQUEST_CODE_DISCOVER);

            } else {
                mainHandler.postDelayed(this, 500);
            }
        }
    };

    // 定义一个搜索任务
    private Runnable searchBlueToothDevice  = new Runnable()
    {
        @Override
        public void run() {
            // 开始扫描周围的蓝牙设备
            beginDiscovery();
            // 延迟30秒后再次启动蓝牙设备的刷新任务
            // mainHandler.postDelayed(this, 30 * 1000);
        }
    };

    // 开始扫描周围的蓝牙设备
    private void beginDiscovery()
    {
        tv_discovery.setText("正在搜索蓝牙设备");
        // 如果当前不是正在搜索，则开始新的搜索任务
        if (!bluetoothAdapter.isDiscovering()) {
            Log.d("test","蓝牙搜索功能");
            bluetoothAdapter.startDiscovery();
        }
    }

    // 取消蓝牙设备的搜索
    private void cancelDiscovery()
    {
        tv_discovery.setText("取消搜索蓝牙设备");
        // 当前正在搜索，则取消搜索任务
        if (bluetoothAdapter.isDiscovering()) {
            Log.d("test","已关闭蓝牙功能");
            bluetoothAdapter.cancelDiscovery();
        }
    }

    // 定义一个连接侦听任务
    private Runnable acceptBlueTooth = new Runnable()
    {
        @Override
        public void run() {
            // 已连接
            if (bluetoothAdapter.getState() == BluetoothAdapter.STATE_ON) {
                // 创建一个蓝牙设备侦听线程，成功侦听就启动蓝牙消息的接收任务
                BlueAcceptTask acceptTask = new BlueAcceptTask(
                        MainActivity.this, true, new BlueAcceptTask.BlueAcceptListener() {
                    @Override
                    public void onBlueAccept(BluetoothSocket socket,BluetoothDevice device) {
                        // 接收到蓝夜设备的请求连接,回调方法返回其套接字,及相关信息!!!!!!!!!!!!!!!!!!
                        blueSocket = socket;
                        Log.d("test","接收到连接的请求");
                        // 更新蓝牙设备数据适配器
                        refreshBlueToothDevice(device,13);
                    }
                });

                // 启动子线程监听程序
                acceptTask.start();

            } else { // 未连接
                // 延迟1秒后重新启动连接侦听任务
                mainHandler.postDelayed(this, 1000);
            }
        }
    };

    // 列表条目项的点击事件
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // 获取用户点击的蓝牙设备信息
        BlueToothDevice item = deviceList.get(position);
        // 根据设备地址获得远端的蓝牙设备对象
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(item.getAddress());

        // 蓝牙设备未绑定
        if(device.getBondState() == BluetoothDevice.BOND_NONE)
        {
            // 开始绑定蓝牙设备
            BluetoothUtil.createBond(device);
            tv_discovery.setText("开始绑定蓝牙设备");
        }
        // 蓝牙设备已绑定，但未建立链接
        else if(device.getBondState() == BluetoothDevice.BOND_BONDED && item.getState() != BlueListAdapter.CONNECTED)
        {
            tv_discovery.setText("开始连接蓝牙设备");
            // 开启蓝牙的连接线程
            BlueConnectTask connectTask = new BlueConnectTask(this, device, new BlueConnectTask.BlueConnectListener() {
                @Override
                public void onBlueConnect(BluetoothSocket socket) {
                    // 接收到蓝夜设备的请求连接,回调方法返回其套接字,及相关信息!!!!!!!!!!!!!!!!!!
                    blueSocket = socket;
                    Log.d("test","已连接到设备");
                    // 更新蓝牙设备数据适配器
                    refreshBlueToothDevice(device,13);
                }
            });

            connectTask.start();
        }
        // 蓝牙设备已绑定,且建立链接
        else if(device.getBondState() == BluetoothDevice.BOND_BONDED && item.getState() == BlueListAdapter.CONNECTED)
        {
            // 可以进行数据的传输
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //如果用户提前打开蓝牙功能,直接搜索蓝牙设备

        // 需要过滤多个动作，则调用IntentFilter对象的addAction添加新动作
        IntentFilter discoveryFilter = new IntentFilter();
        discoveryFilter.addAction(BluetoothDevice.ACTION_FOUND);
        discoveryFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        discoveryFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        // 注册蓝牙设备搜索的广播接收器
        registerReceiver(blueToothDeviceReceiver, discoveryFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 取消蓝牙设备的搜索
           cancelDiscovery();
        // 注销蓝牙设备搜索的广播接收器
        unregisterReceiver(blueToothDeviceReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 关闭蓝牙设备的套接字
        if (blueSocket != null) {
            try {
                blueSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 刷新蓝牙设备列表
    private void refreshBlueToothDevice(BluetoothDevice device, int state)
    {
        int i;
        for(i = 0; i < deviceList.size(); i++)
        {
            BlueToothDevice item = deviceList.get(i);
            // 刷新的蓝牙设备在列表当中,切换其状态
            if(item.getAddress().equals(device.getAddress()))
            {
                if(item.getState() == BlueListAdapter.CONNECTED && state == 12 )
                {
                    deviceList.remove(i);
                    deviceList.add(i,new BlueToothDevice(device.getName(),device.getAddress(),BlueListAdapter.CONNECTED));
                    break;
                }
                else
                {
                    deviceList.remove(i);
                    deviceList.add(i,new BlueToothDevice(device.getName(),device.getAddress(),state));
                    break;
                }
            }
        }

        // 刷新的蓝牙设备不在设备列表当中,添加新的蓝牙设备到列表当中
        if(i >= deviceList.size())
        {
            deviceList.add(new BlueToothDevice(device.getName(),device.getAddress(),state));
        }

        // 刷新蓝牙数据适配器
        blueListAdapter.notifyDataSetChanged();
    }

    // 蓝牙设备的搜索结果通过广播返回,创建蓝牙结果的接收广播
    private BroadcastReceiver blueToothDeviceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 获取意图中的携带的广播action
            String action = intent.getAction();
            Log.d("test","action = " + action.toString());

            // 发现新的蓝牙设备
            if(action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                refreshBlueToothDevice(device,device.getBondState());
            }
            // 搜索完毕
            else if(action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED))
            {
                tv_discovery.setText("蓝牙设备搜索完成");
                // 继续搜索蓝牙设备
                mainHandler.post(searchBlueToothDevice);
            }
            // 配对状态变更
            else if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                // 设备绑定中
                if(device.getBondState() == BluetoothDevice.BOND_BONDING)
                {
                    refreshBlueToothDevice(device,device.getBondState());
                }
                // 设备已绑定
                else if(device.getBondState() == BluetoothDevice.BOND_BONDED)
                {
                    refreshBlueToothDevice(device,device.getBondState());
                }
                // 设备未绑定
                else if(device.getBondState() == BluetoothDevice.BOND_NONE)
                {
                    refreshBlueToothDevice(device,device.getBondState());
                }
            }
        }
    };

    // 获取设备可见性所需权限的返回结果
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_CODE_DISCOVER) {

            // 启动蓝牙设备的扫描任务
            mainHandler.postDelayed(searchBlueToothDevice,50);

            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "允许本地蓝牙被附近的其他蓝牙设备发现",
                        Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "不允许蓝牙被附近的其他蓝牙设备发现",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 动态获取蓝牙所需要的权限的返回结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_CODE_BLUETOOTH:
                if(PermissionUtil.checkGrant(grantResults)){
                    Toast.makeText(this,"蓝牙相关权限开启成功",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"蓝牙相关权限开启失败",Toast.LENGTH_SHORT).show();
                    jumpToSettings();
                }
                break;
        }
    }

    // 跳转到应用权限设置界面
    private void jumpToSettings()
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}