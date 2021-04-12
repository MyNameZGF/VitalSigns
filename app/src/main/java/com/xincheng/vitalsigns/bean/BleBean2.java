package com.xincheng.vitalsigns.bean;

public class BleBean2 {
    public static final int TYPE_HEADER = 1;//标题
    public static final int TYPE_OPEN_BLE = 2;//打开ble
    public static final int TYPE_BLE_INFO = 3;//wifi信息

    private boolean connected;
    private String bleName;
    private boolean lastItem;
    private boolean openBle;
    private int status;//0->未连接，1->已经连接，2->正在配对,3->设备名称
    private boolean itemEnableClick;//item是否可点击

    public BleBean2(boolean openBle) {
        this.openBle = openBle;
    }

    public BleBean2(String bleName, boolean lastItem, boolean openBle) {
        this.bleName = bleName;
        this.lastItem = lastItem;
        this.openBle = openBle;
    }

    public BleBean2(String bleName, boolean lastItem, int status,boolean itemEnableClick) {
        this.bleName = bleName;
        this.lastItem = lastItem;
        this.status = status;
        this.itemEnableClick = itemEnableClick;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getBleName() {
        return bleName;
    }

    public void setBleName(String bleName) {
        this.bleName = bleName;
    }

    public boolean isLastItem() {
        return lastItem;
    }

    public void setLastItem(boolean lastItem) {
        this.lastItem = lastItem;
    }

    public boolean isOpenBle() {
        return openBle;
    }

    public void setOpenBle(boolean openBle) {
        this.openBle = openBle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isItemEnableClick() {
        return itemEnableClick;
    }

    public void setItemEnableClick(boolean itemEnableClick) {
        this.itemEnableClick = itemEnableClick;
    }
}
