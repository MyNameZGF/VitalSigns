package com.xincheng.vitalsigns.event;

import com.clj.fastble.data.BleDevice;

import java.util.List;

/**
 * 蓝牙扫描事件
 */
public class ScanEvent {
    public boolean isScan;
    public BleDevice device;
    public List<BleDevice> deviceList;

    /**
     * 事件的类型
     *  1：开始扫描
     *  2：发现设备
     *  3：扫描完成
     */
    public int type;
    public ScanEvent(boolean isScan,int type) {
        this.type = type;
        this.isScan = isScan;

    }

    public ScanEvent(BleDevice device,int type) {
        this.type = type;
        this.device = device;
    }

    public ScanEvent(List<BleDevice> deviceList,int type) {
        this.type = type;
        this.deviceList = deviceList;
    }
}
