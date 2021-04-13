package com.xincheng.vitalsigns.bean;

public class WifiBean {
    public static final int TYPE_HEADER = 1;//标题
    public static final int TYPE_OPEN_WIFI = 2;//打开wifi
    public static final int TYPE_CONNECT_WIFI = 3;//可连接wifi

    private boolean connected;
    private String wifiName;
    private boolean locked;//是否需要密码
    private int signal;//信号
    private String password;
    private boolean lastItem;
    private int a;

    private boolean openWifi;

    public WifiBean(boolean openWifi) {
        this.openWifi = openWifi;
    }

    public WifiBean(boolean openWifi,boolean lastItem) {
        this.lastItem = lastItem;
        this.openWifi = openWifi;
    }

    public WifiBean(boolean connected, String wifiName, boolean locked, int signal, String password) {
        this.connected = connected;
        this.wifiName = wifiName;
        this.locked = locked;
        this.signal = signal;
        this.password = password;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLastItem() {
        return lastItem;
    }

    public void setLastItem(boolean lastItem) {
        this.lastItem = lastItem;
    }

    public boolean isOpenWifi() {
        return openWifi;
    }

    public void setOpenWifi(boolean openWifi) {
        this.openWifi = openWifi;
    }
}
