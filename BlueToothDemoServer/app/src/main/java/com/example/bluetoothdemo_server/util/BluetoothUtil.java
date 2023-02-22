package com.example.bluetoothdemo_server.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class BluetoothUtil {
    private final static String TAG = "BluetoothUtil";

    // 获取蓝牙的开关状态
    public static boolean getBlueToothStatus() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        boolean enabled;
        switch (bluetoothAdapter.getState()) {
            case BluetoothAdapter.STATE_ON:
            case BluetoothAdapter.STATE_TURNING_ON:
                enabled = true;
                break;
            case BluetoothAdapter.STATE_OFF:
            case BluetoothAdapter.STATE_TURNING_OFF:
            default:
                enabled = false;
                break;
        }
        return enabled;
    }

    // 打开或关闭蓝牙
    public static void setBlueToothStatus(boolean enabled) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (enabled) {
            bluetoothAdapter.enable();
        } else {
            bluetoothAdapter.disable();
        }
    }

    // 建立蓝牙配对
    public static boolean createBond(BluetoothDevice device) {
        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("createBond");
            Boolean result = (Boolean) createBondMethod.invoke(device);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 取消蓝牙配对
    public static boolean removeBond(BluetoothDevice device) {
        try {
            Method createBondMethod = BluetoothDevice.class.getMethod("removeBond");
            Boolean result = (Boolean) createBondMethod.invoke(device);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 创建一个默认频道的监听
    public static BluetoothServerSocket listenServer(BluetoothAdapter adapter) {
        BluetoothServerSocket serverSocket = null;
        try {
            Method listenMethod = adapter.getClass().getMethod("listenUsingRfcommOn", int.class);
            serverSocket = (BluetoothServerSocket) listenMethod.invoke(adapter, 29);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverSocket;
    }

    // 读取对方设备的输入信息
    public static String readInputStream(InputStream is) {
        String result = null;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            byte[] data = baos.toByteArray();
            result = new String(data, "utf8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 向对方设备发送信息
    public static void writeOutputStream(BluetoothSocket socket, String message) {
        try {
            OutputStream os = socket.getOutputStream();
            os.write(message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
