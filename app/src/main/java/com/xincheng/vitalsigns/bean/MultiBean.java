package com.xincheng.vitalsigns.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * 多类型布局基类
 * @param <T>
 */
public class MultiBean<T> implements MultiItemEntity {
    private T data;
    private int type;
    private int headerLayoutId;
    private String hearder;

    public MultiBean() { }

    public MultiBean(int type) {
        this.type = type;
    }

    public MultiBean(int type, String hearder) {
        this.type = type;
        this.hearder = hearder;
    }

    public MultiBean(T data, int type) {
        this.data = data;
        this.type = type;
    }

    public MultiBean(int type, int headerLayoutId, String hearder) {
        this.type = type;
        this.headerLayoutId = headerLayoutId;
        this.hearder = hearder;
    }

    @Override
    public int getItemType() { return type; }

    public int getType() { return type; }

    public void setType(int type) { this.type = type; }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getHeaderLayoutId() {
        return headerLayoutId;
    }

    public void setHeaderLayoutId(int headerLayoutId) {
        this.headerLayoutId = headerLayoutId;
    }

    public String getHearder() {
        return hearder;
    }

    public void setHearder(String hearder) {
        this.hearder = hearder;
    }

    @Override
    public String toString() {
        return "MultiBean{" +
                "data=" + data +
                ", type=" + type +
                ", headerLayoutId=" + headerLayoutId +
                ", hearder='" + hearder + '\'' +
                '}';
    }
}
