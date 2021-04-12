package com.xincheng.vitalsigns.bean;

public class ScreenBean {
    public static final int TYPE_HEADER = 1;//标题
    public static final int TYPE_LIGHT = 2;//亮度
    public static final int TYPE_LIGHT_MODE = 3;//自动、护眼
    public static final int TYPE_SLEEP_TIME = 4;//休眠时间

    private int light;
    private int lightMode;//0 自动，1护眼
    private boolean autoLight;//自动
    private boolean greenLight;//护眼
    private int sleepItem;// 0->5分钟，1->15分钟，2->30分钟，4->永不
    private String sleepTime;
    private boolean sleepItemSelected;
    private boolean isLastItem;



    public ScreenBean(int light) {
        this.light = light;
    }

    public ScreenBean(boolean autoOrScreen,int lightMode) {
        this.lightMode = lightMode;
        switch (lightMode){
            case 0:
                this.autoLight = autoOrScreen;
                break;
            default:
                this.greenLight = autoOrScreen;
                break;
        }
    }

    public ScreenBean(boolean autoOrScreen,int lightMode,boolean isLastItem) {
        this.lightMode = lightMode;
        this.isLastItem = isLastItem;
        switch (lightMode){
            case 0:
                this.autoLight = autoOrScreen;
                break;
            default:
                this.greenLight = autoOrScreen;
                break;
        }
    }

    public ScreenBean(int sleepItem,String sleepTime, boolean sleepItemSelected) {
        this.sleepItem = sleepItem;
        this.sleepItemSelected = sleepItemSelected;
        this.sleepTime = sleepTime;
    }

    public ScreenBean(int sleepItem,String sleepTime, boolean sleepItemSelected,boolean isLastItem) {
        this.sleepItem = sleepItem;
        this.sleepItemSelected = sleepItemSelected;
        this.sleepTime = sleepTime;
        this.isLastItem = isLastItem;
    }

    public ScreenBean(int sleepItem, boolean sleepItemSelected,boolean isLastItem) {
        this.sleepItem = sleepItem;
        this.sleepItemSelected = sleepItemSelected;
        this.isLastItem = isLastItem;
    }

    public ScreenBean(int light, boolean autoLight, boolean greenLight, int sleepItem, boolean sleepItemSelected) {
        this.light = light;
        this.autoLight = autoLight;
        this.greenLight = greenLight;
        this.sleepItem = sleepItem;
        this.sleepItemSelected = sleepItemSelected;
    }

    public String getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(String sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getLightMode() {
        return lightMode;
    }

    public void setLightMode(int lightMode) {
        this.lightMode = lightMode;
    }

    public boolean isAutoLight() {
        return autoLight;
    }

    public void setAutoLight(boolean autoLight) {
        this.autoLight = autoLight;
    }

    public boolean isGreenLight() {
        return greenLight;
    }

    public void setGreenLight(boolean greenLight) {
        this.greenLight = greenLight;
    }

    public int getSleepItem() {
        return sleepItem;
    }

    public void setSleepItem(int sleepItem) {
        this.sleepItem = sleepItem;
    }

    public boolean isSleepItemSelected() {
        return sleepItemSelected;
    }

    public void setSleepItemSelected(boolean sleepItemSelected) {
        this.sleepItemSelected = sleepItemSelected;
    }

    public boolean isLastItem() {
        return isLastItem;
    }

    public void setLastItem(boolean lastItem) {
        isLastItem = lastItem;
    }
}
