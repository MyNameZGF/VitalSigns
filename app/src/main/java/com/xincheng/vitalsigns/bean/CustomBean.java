package com.xincheng.vitalsigns.bean;

import android.content.Context;

public class CustomBean {
    private String content;
    private int pic;
    private int type;//0 固定；1 可删除；2 可增加
    private int mode;//0-体温；1-血氧；2-血压；3-血糖；4-脉率；5-呼吸；6-小便 7-大便；8-血氧波        主页根据mode来自定义绘制控件。

    public CustomBean(Context context, int wenduji1, int mode, int type) {
    }

    public CustomBean(String content, int pic, int mode,int type) {
        this.content = content;
        this.pic = pic;
        this.mode = mode;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "CustomBean{" +
                "content='" + content + '\'' +
                ", pic=" + pic +
                ", type=" + type +
                ", mode=" + mode +
                '}';
    }
}
