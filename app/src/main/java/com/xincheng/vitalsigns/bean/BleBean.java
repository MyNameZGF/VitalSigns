package com.xincheng.vitalsigns.bean;

import com.clj.fastble.data.BleDevice;

public class BleBean {
    private boolean isConnect;
    private BleDevice device;

    public BleBean() {
    }

    public BleBean(BleDevice device, boolean isConnect) {
        this.device = device;
        this.isConnect = isConnect;
    }

    public boolean isConnect() {
        return isConnect;
    }

    public void setConnect(boolean connect) {
        isConnect = connect;
    }

    public BleDevice getDevice() {
        return device;
    }

    public void setDevice(BleDevice device) {
        this.device = device;
    }
}
