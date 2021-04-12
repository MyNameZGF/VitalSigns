package com.xincheng.vitalsigns.bean;

public class ConnectBean {
    private String mac;
    private boolean connect;

    public ConnectBean() {
    }

    public ConnectBean(String mac, boolean connect) {
        this.mac = mac;
        this.connect = connect;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }
}
