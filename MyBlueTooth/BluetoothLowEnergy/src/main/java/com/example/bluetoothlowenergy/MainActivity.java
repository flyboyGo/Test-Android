package com.example.bluetoothlowenergy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bluetoothdemo.R;
import com.example.bluetoothlowenergy.adapter.BluetoothLowEnergyAdapter;
import com.example.bluetoothlowenergy.enity.BluetoothDevice;
import com.example.bluetoothlowenergy.util.PermissionUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private BluetoothLowEnergy bLueToothLowEnergy;
    private BluetoothLowEnergyAdapter bluetoothLowEnergyAdapter;
    private ListView listView;
    private List<BluetoothDevice> deviceList;
    private Boolean flag = false;
    private Map<String, android.bluetooth.BluetoothDevice> systemDeviceMap;
    private MyScan myScan;
    private MyGattCallBack myGattCallBack;
    private final int mut = 33;
    // 汇承
    public static UUID uuid_service = UUID.fromString("0000fff0-0000-1000-8000-00805f9b34fb");
    public static UUID uuid_notify = UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb");
    public static UUID uuid_write = UUID.fromString("0000fff2-0000-1000-8000-00805f9b34fb");
    public static UUID uuid_read = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");


    BluetoothGatt bluetoothGatt;
    BluetoothGattService bluetoothGattService = null;
    BluetoothGattCharacteristic writeCharacteristic = null;
    BluetoothGattCharacteristic notifyCharacteristic = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化蓝牙管理类
        bLueToothLowEnergy = new BluetoothLowEnergy(MainActivity.this);

        // 初始化控件
        initWidget();

        // 初始化蓝牙数据列表
        initData();
    }

    private void initData() {
        deviceList = new ArrayList<>();
        systemDeviceMap = new HashMap<>();
        bluetoothLowEnergyAdapter = new BluetoothLowEnergyAdapter(this,deviceList);
        listView.setAdapter(bluetoothLowEnergyAdapter);
    }

    private void initWidget() {
        listView = findViewById(R.id.list_view_bluetooth);
        listView.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_is_support_bluetooth:
                boolean flagSupportBleTooth = bLueToothLowEnergy.isSupportBleTooth();
                if(flagSupportBleTooth)
                {
                    Toast.makeText(this,"该设备支持低功耗蓝牙功能",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this,"该设备不支持低功耗蓝牙功能",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_is_open_bluetooth:
                isOpenBlueTooth();
                break;
            case R.id.menu_open_bluetooth:
                openBlueTooth();
                break;
            case R.id.menu_dynamic_right_bluetooth:
                dynamicRight();
                break;
            case R.id.menu_discovered_bluetooth:
                discoveredBleuTooth();
                break;
            case R.id.menu_close_bluetooth:
                closeBlueTooth();
                break;
            case R.id.menu_scan_bluetooth:
                scanBlueTooth();
                break;
            case R.id.menu_start_byte:
                sendStartData();
                break;
            case R.id.menu_stop_byte:
                sendStopData();
                break;
            case R.id.menu_stop_scan_bluetooth:
                stopScanBlueTooth();
                break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 发送启动字节数据
    private void sendStartData() {
        bLueToothLowEnergy.sendStartData(bluetoothGatt,writeCharacteristic);
    }

    // 发送关闭字节数据
    private void sendStopData() {
        bLueToothLowEnergy.sendStopData(bluetoothGatt,writeCharacteristic);
    }

    // 点击蓝夜设备条目项,连接对应的低功耗蓝牙,获取对应硬件蓝牙类的实例化对象
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // 连接数据时，停止扫描(与开始扫描的回调函数保持一致)
        bLueToothLowEnergy.stopScanBlueTooth(myScan);

        // 获取被点击蓝牙条目,对应的蓝牙设备
        BluetoothDevice device = deviceList.get(position);
        android.bluetooth.BluetoothDevice systemDevice = systemDeviceMap.get(device.getAddress());

        // 实例化自定义回调函数类
        myGattCallBack = new MyGattCallBack(device);
        // 蓝牙蓝牙设备,获取gatt(代表硬件蓝牙)
        bluetoothGatt = bLueToothLowEnergy.connectionBlueTooth(systemDevice, false, myGattCallBack);
    }

    private void stopScanBlueTooth() {
        bLueToothLowEnergy.stopScanBlueTooth(myScan);
    }

    private void scanBlueTooth() {
        // 实例化自定义的回调函数类
        myScan = new MyScan();
        bLueToothLowEnergy.scanBlueTooth(myScan);
    }

    private void addBluetoothDevice(BluetoothDevice device, int state, android.bluetooth.BluetoothDevice systemDevice) {
        int i;
        for(i = 0; i < deviceList.size(); i++)
        {
            BluetoothDevice item = deviceList.get(i);
            // 刷新的蓝牙设备在列表当中,切换其状态
            if(item.getAddress().equals(device.getAddress()))
            {
                if(item.getState() == BluetoothLowEnergyAdapter.CONNECTED && state == 12 )
                {
                    deviceList.remove(i);
                    deviceList.add(i,new BluetoothDevice(device.getName(),device.getAddress(),BluetoothLowEnergyAdapter.CONNECTED));
                    break;
                }
                else
                {
                    deviceList.remove(i);
                    deviceList.add(i,new BluetoothDevice(device.getName(),device.getAddress(),state));
                    break;
                }
            }
        }

        // 刷新的蓝牙设备不在设备列表当中,添加新的蓝牙设备到列表当中
        if(i >= deviceList.size())
        {
            deviceList.add(new BluetoothDevice(device.getName(),device.getAddress(),state));
            systemDeviceMap.put(systemDevice.getAddress(),systemDevice);
        }

        // 更新蓝牙列表数据适配器
        bluetoothLowEnergyAdapter.notifyDataSetChanged();
    }

    private void dynamicRight() {
        bLueToothLowEnergy.dynamicRight();
    }

    private void closeBlueTooth()
    {
        flag = false;
        bluetoothGatt.disconnect();
        bLueToothLowEnergy.closeBlueTooth();
        deviceList.clear();
        systemDeviceMap.clear();
        bluetoothLowEnergyAdapter.notifyDataSetChanged();
    }

    private void discoveredBleuTooth()
    {
        bLueToothLowEnergy.DiscoveredBlueTooth();
    }

    private void openBlueTooth() {
        flag = true;
        bLueToothLowEnergy.openBlueTooth();
    }

    private void isOpenBlueTooth() {
        boolean result = bLueToothLowEnergy.isOpenBlueTooth();
        if(result)
        {
            Toast.makeText(this,"蓝牙已打开",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"蓝牙未打开",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case BluetoothLowEnergy.REQUEST_CODE_ENABLE_BLUETOOTH:
                if(resultCode == Activity.RESULT_OK)
                {
                    Toast.makeText(this,"蓝牙打开成功",Toast.LENGTH_SHORT).show();
                    // 蓝牙功能打开成功,回调函数触发时,获取蓝牙扫描器,避免空指针异常
                    bLueToothLowEnergy.getBluetoothLeScanner();
                }
                else
                {
                    Toast.makeText(this,"蓝牙打开失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case BluetoothLowEnergy.REQUEST_CODE_DISCOVERED_BLUETOOTH:
                if(resultCode == Activity.RESULT_OK)
                {
                    Toast.makeText(this,"蓝牙可见性打开成功",Toast.LENGTH_SHORT).show();
                }
                else if(resultCode == Activity.RESULT_CANCELED)
                {
                    Toast.makeText(this,"蓝牙可见性打开失败",Toast.LENGTH_SHORT).show();
                    Log.d("test","resultCode = " + resultCode);
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case BluetoothLowEnergy.REQUEST_CODE_DYNAMIC_BLUETOOTH:
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

    private void jumpToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public class MyScan extends ScanCallback
    {
        public MyScan() {
            super();
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            // 方式一、获取扫描到的蓝牙设备
            android.bluetooth.BluetoothDevice systemDevice = result.getDevice();
            // 方式二、调用蓝牙适配器(BluetoothAdapter)的getRemoteDevice()方法,根据对方的MAC地址,得到对应的蓝牙设备
            // 获取蓝牙设备的详细信息
            String name = systemDevice.getName();
            String address = systemDevice.getAddress();
            int state = systemDevice.getBondState();
            // 将蓝牙信息添加到蓝牙集合中(进行过滤)
            BluetoothDevice blueToothDevice = new BluetoothDevice(name, address, state);
            addBluetoothDevice(blueToothDevice,state,systemDevice);
        }

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
        }
    }

    public class MyGattCallBack extends BluetoothGattCallback
    {
        private BluetoothDevice device;

        public MyGattCallBack(BluetoothDevice device) {
            this.device = device;
        }


        // 连接状态发生变更时回调
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);

            if(newState == BluetoothProfile.STATE_CONNECTED)
            {

                Log.d("test","onConnectionStateChange ----> CONNECTED");

                // 设置字节数
                bluetoothGatt.requestMtu(mut);

                // 更新蓝牙设备状态(主线程中)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        addBluetoothDevice(device, bluetoothLowEnergyAdapter.CONNECTED,systemDeviceMap.get(device.getAddress()));
                    }
                });

                // 设备连接成功,开始查找BLE服务
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bluetoothGatt.discoverServices();
                    }
                },1000);

                // 停止扫描
                bLueToothLowEnergy.stopScanBlueTooth(myScan);
            }
            else if(newState == BluetoothProfile.STATE_DISCONNECTED)
            {
                Log.d("test","onConnectionStateChange ----> DISCONNECTED");

                // 更新蓝牙设备状态(主线程中)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(flag)
                        {
                            addBluetoothDevice(device, device.getState(),systemDeviceMap.get(device.getAddress()));
                        }
                    }
                });
            }
        }

        // 发现服务器端的服务列表及其特征值时回调
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            // gatt与外部函数调用返回的bluetoothGatt是一致的,表示的是蓝牙硬件
            if(status == BluetoothGatt.GATT_SUCCESS)
            {
                // 获取服务
                bluetoothGattService = bluetoothGatt.getService(uuid_service);
                // 获取写入特征值
                writeCharacteristic = bluetoothGattService.getCharacteristic(uuid_write);
                // 获取监听特征值
                notifyCharacteristic = bluetoothGattService.getCharacteristic(uuid_notify);

                // 开启监听，即建立与设备的通信的首发数据通道，BLE开发中只有当客户端成功开启监听后才能与服务端收发数据
                // 开启监听(通知BlueGatt硬件蓝牙,通过此特征值)
                bluetoothGatt.setCharacteristicNotification(notifyCharacteristic,true);

                // 接收数据
                BluetoothGattDescriptor clientConfigDescriptor = notifyCharacteristic.getDescriptor(uuid_read);
                if(clientConfigDescriptor != null)
                {
                    clientConfigDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    // 通知蓝牙硬件通过这个特征值进行通讯(现阶段可以进行读操作,接收数据)
                    bluetoothGatt.writeDescriptor(clientConfigDescriptor);
                }
            }
            else if(status == BluetoothGatt.GATT_FAILURE)
            {
                Log.d("test","onServicesDiscovered --->failure");
            }
        }

        // 接收数据(收到服务器端的数据变更时回调)
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);

            byte[] buffer = new byte[30 * 1000];
            int i = 0, count = 1;
            while(true)
            {
                byte[] value = characteristic.getValue();
                System.arraycopy(value,0,buffer,i,30);
                i = i + 30;
                count++;
                if(count == 1000)
                {
                    // 写入文件
                    // 清空计数、下标
                    i = 0;
                    count = 0;
                }
            }


//            byte[] value = characteristic.getValue();
//            Log.d("test","onCharacteristicChanged,客户端接收到服务器端发送的数据了,数据大小为 == " + value.length + "字节");
//            byte first = value[0];
//            byte second = value[1];
//            byte third = value[2];
//            Log.d("test","第1字节数据, 高四位 == " + ByteUtil.getHeight4(first) + ", 低四位 == " + ByteUtil.getLow4(first));
//            Log.d("test","第2字节数据, 高四位 == " + ByteUtil.getHeight4(second) + ", 低四位 == " + ByteUtil.getLow4(second));
//            Log.d("test","第3字节数据, 高四位 == " + ByteUtil.getHeight4(third) + ", 低四位 == " + ByteUtil.getLow4(third));
        }

        // 服务器端接收客户端数据时，发生回调(如果执行成功,就表示服务器端已经接收到消息)
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.d("test","onCharacteristicWrite,数据发送成功!!!!!!!!");
        }

        // 字节设置回调函数
        @Override
        public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
            super.onMtuChanged(gatt, mtu, status);
            if(BluetoothGatt.GATT_SUCCESS == status)
            {
                Log.d("test","onMtuChanged success MTU == " + mtu);
            }
            else
            {
                Log.d("test","onMtuChanged fail");
            }
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
        }

        @Override
        public void onDescriptorRead(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorRead(gatt, descriptor, status);
        }


    }

}