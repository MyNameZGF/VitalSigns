package com.xincheng.vitalsigns.event;
import android.bluetooth.BluetoothGatt;
import com.clj.fastble.data.BleDevice;

/**
 * 蓝牙连接事件
 */
public class ConnectEvent {
    public BleDevice device;
    public BluetoothGatt gatt;

    /**
     * 事件的类型
     *  1：开始连接
     *  2：连接失败
     *  3：连接成功
     *  4：断开连接
     */
    public int type;
    public ConnectEvent(int type) {
        this.type = type;


    }

    public ConnectEvent(BleDevice device,int type) {
        this.type = type;
        this.device = device;
    }

    public ConnectEvent(BleDevice device, BluetoothGatt gatt,int type) {
        this.type = type;
        this.device = device;
        this.gatt = gatt;
    }
}
