package com.example.bluetoothlowenergy;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import com.example.bluetoothlowenergy.util.PermissionUtil;

public class BluetoothLowEnergy {
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothLeScanner bluetoothLeScanner;
    private Activity activity;
    public static final int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    public static final int REQUEST_CODE_DISCOVERED_BLUETOOTH = 1;
    public static final int REQUEST_CODE_DYNAMIC_BLUETOOTH = 2;

    // 蓝牙定位权限集合
    private static final String[]PERMISSIONS_BLUETOOTH = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };

    public BluetoothLowEnergy(Activity activity)
    {
        // 活动
        this.activity = activity;
        // 方式二: 获取系统默认的蓝牙适配器
        BluetoothManager bluetoothManager = (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        this.bluetoothAdapter = bluetoothManager.getAdapter();
        // 获取蓝牙扫描器(此时蓝牙未打开,获取到的是NULL)
        this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
    }

    // 是否支持低功耗蓝牙功能
    public boolean isSupportBleTooth()
    {
        if(!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    // 蓝牙功能是否打开
    public boolean isOpenBlueTooth()
    {
        return bluetoothAdapter.isEnabled();
    }

    // 打开蓝牙操作
    public void openBlueTooth()
    {
        // 隐式打开蓝牙,不推荐
        // bluetoothAdapter.enable();
        // 通过意图添加动作打开蓝牙
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(intent,REQUEST_CODE_ENABLE_BLUETOOTH);
    }

    public void getBluetoothLeScanner()
    {
        this.bluetoothLeScanner = this.bluetoothAdapter.getBluetoothLeScanner();
    }

    // 关闭蓝牙操作
    public void closeBlueTooth()
    {
        bluetoothAdapter.disable();
    }

    // 蓝牙动态权限申请
    public void dynamicRight()
    {
        PermissionUtil.checkPermission(activity,PERMISSIONS_BLUETOOTH,REQUEST_CODE_DYNAMIC_BLUETOOTH);
    }

    // 设置设备可见性
    public void DiscoveredBlueTooth()
    {
        Intent intent = new Intent();
        intent.setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        activity.startActivityForResult(intent,REQUEST_CODE_DISCOVERED_BLUETOOTH);
    }

    // 扫描蓝牙设备,触发回调函数
    public void scanBlueTooth(ScanCallback scanCallback)
    {
        // 可以使用startScan重载函数进行过滤
        if(bluetoothLeScanner != null)
        {
            Log.d("test","开始扫描");
            bluetoothLeScanner.startScan(scanCallback);
        }
        else
        {
            Log.d("test","bluetoothLeScanner is null");
        }
    }

    // 停止扫描蓝牙设备,触发回调函数
    public void stopScanBlueTooth(ScanCallback scanCallback)
    {
        bluetoothLeScanner.stopScan(scanCallback);
    }

    // 连接蓝牙设备,触发回调函数
    public BluetoothGatt connectionBlueTooth(BluetoothDevice bluetoothDevice, Boolean autoConnect, BluetoothGattCallback gattCallBack)
    {
        // Activity的父类就是Context, autoConnect是否自动连接蓝牙设备
        BluetoothGatt bluetoothGatt = bluetoothDevice.connectGatt(this.activity, autoConnect, gattCallBack);
        // 代表硬件蓝牙
        return bluetoothGatt;
    }

    // 发送启动字节数据
    public void sendStartData(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic)
    {
        // 发送启动蓝牙设备的字节数据
        // 0xFE 0xFD 0xFC 0xFB 0x0B
        byte first = (byte)Integer.parseInt("FE", 16);
        byte second = (byte)Integer.parseInt("FD", 16);
        byte third = (byte)Integer.parseInt("FC", 16);
        byte fourth = (byte)Integer.parseInt("FB", 16);
        byte five = (byte)Integer.parseInt("0B", 16);
        byte[] start = new byte[5];
        start[0] = first;
        start[1] = second;
        start[2] = third;
        start[3] = fourth;
        start[4] = five;

        // 设置发送的类型
        bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);

        // 设置发送的字节数据
        bluetoothGattCharacteristic.setValue(start);

        // 蓝牙硬件发送数据
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        Log.d("test","启动字节数据已发送");
    }

    // 发送停止字节数据
    public void sendStopData(BluetoothGatt bluetoothGatt, BluetoothGattCharacteristic bluetoothGattCharacteristic)
    {
        // 发送启动蓝牙设备的字节数据
        // 0xFE 0xFD 0xFC 0xFB 0x0B
        byte first = (byte)Integer.parseInt("FE", 16);
        byte second = (byte)Integer.parseInt("FD", 16);
        byte third = (byte)Integer.parseInt("FC", 16);
        byte fourth = (byte)Integer.parseInt("FB", 16);
        byte five = (byte)Integer.parseInt("0C", 16);
        byte[] start = new byte[5];
        start[0] = first;
        start[1] = second;
        start[2] = third;
        start[3] = fourth;
        start[4] = five;

        // 设置发送的类型
        bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);

        // 设置发送的字节数据
        bluetoothGattCharacteristic.setValue(start);
        // 设置发送文本数据
        // bluetoothGattCharacteristic.setValue("");

        // 蓝牙硬件发送数据
        bluetoothGatt.writeCharacteristic(bluetoothGattCharacteristic);
        Log.d("test","启动字节数据已发送");
    }
}
