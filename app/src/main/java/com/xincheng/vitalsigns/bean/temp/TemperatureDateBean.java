package com.xincheng.vitalsigns.bean.temp;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class TemperatureDateBean implements MultiItemEntity {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;

    private int type;
    private String content;
    private TemperatureBean bean;

    public TemperatureDateBean() {
    }

    public TemperatureDateBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public TemperatureDateBean(int type, TemperatureBean bean) {
        this.type = type;
        this.bean = bean;
    }

    public TemperatureDateBean(int type, TemperatureBean bean, String content) {
        this.type = type;
        this.bean = bean;
        this.content = content;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TemperatureBean getBean() {
        return bean;
    }

    public void setBean(TemperatureBean bean) {
        this.bean = bean;
    }

    @Override
    public int getItemType() { return type; }
}
