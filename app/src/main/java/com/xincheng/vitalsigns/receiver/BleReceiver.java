package com.xincheng.vitalsigns.receiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hjq.toast.ToastUtils;

public class BleReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) { // 蓝牙开关状态变化
            int blueNewState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);  //获取蓝牙广播中的蓝牙新状态
            //int blueOldState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, 0); //获取蓝牙广播中的蓝牙旧状态
            switch (blueNewState) {
                case BluetoothAdapter.STATE_TURNING_ON: { //正在打开蓝牙
                    ToastUtils.show("正在打开蓝牙");
                    break;
                }
                case BluetoothAdapter.STATE_ON: { //蓝牙已打开
                    ToastUtils.show("蓝牙已经打开");
                    break;
                }
                case BluetoothAdapter.STATE_TURNING_OFF: { //正在关闭蓝牙
                    ToastUtils.show("正在关闭蓝牙");
                    break;
                }
                case BluetoothAdapter.STATE_OFF: { //蓝牙已关闭
                    ToastUtils.show("蓝牙已经关闭");
                    break;
                }
            }
        }
        /*
         * 本机的蓝牙连接状态发生变化
         *
         * 特指“无任何连接”→“连接任意远程设备”，以及“连接任一或多个远程设备”→“无任何连接”的状态变化，
         * 即“连接第一个远程设备”与“断开最后一个远程设备”时才会触发该Action
         */
      /*  else if (action.equals(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)) {
            //获取蓝牙广播中的蓝牙连接新状态
            int newConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, 0);
            //获取蓝牙广播中的蓝牙连接旧状态
            int oldConnState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_CONNECTION_STATE, 0);
            // 当前远程蓝牙设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //HashMap<String, Object> map = parseBtDevice2Map(device);
            switch (newConnState) {
                //蓝牙连接中
                case BluetoothAdapter.STATE_CONNECTING: {
                    Log.d(TAG, "STATE_CONNECTING, " + map.get("name"));
                    Toast.makeText(context, "STATE_CONNECTING", Toast.LENGTH_SHORT).show();
                    break;
                }
                //蓝牙已连接
                case BluetoothAdapter.STATE_CONNECTED: {
                    Log.d(TAG, "STATE_CONNECTED, " + map.get("name"));
                    Toast.makeText(context, "STATE_CONNECTED", Toast.LENGTH_SHORT).show();
                    break;
                }
                //蓝牙断开连接中
                case BluetoothAdapter.STATE_DISCONNECTING: {
                    Log.d(TAG, "STATE_DISCONNECTING, " + map.get("name"));
                    Toast.makeText(context, "STATE_DISCONNECTING", Toast.LENGTH_SHORT).show();
                    break;
                }
                //蓝牙已断开连接
                case BluetoothAdapter.STATE_DISCONNECTED: {
                    Log.d(TAG, "STATE_DISCONNECTED, " + map.get("name"));
                    Toast.makeText(context, "STATE_DISCONNECTED", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
        // 有远程设备成功连接至本机
        else if (action.equals(BluetoothDevice.ACTION_ACL_CONNECTED)) {
            // 当前远程蓝牙设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            HashMap<String, Object> map = parseBtDevice2Map(device);
            Log.d(TAG, "ACTION_ACL_CONNECTED, " + map.get("name"));
            Toast.makeText(context, "ACTION_ACL_CONNECTED", Toast.LENGTH_SHORT).show();
        }
        // 有远程设备断开连接
        else if (action.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)) {
            // 当前远程蓝牙设备
            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            HashMap<String, Object> map = parseBtDevice2Map(device);
            Log.d(TAG, "ACTION_ACL_DISCONNECTED, " + map.get("name"));
            Toast.makeText(context, "ACTION_ACL_DISCONNECTED", Toast.LENGTH_SHORT).show();
        }*/
    }
}
