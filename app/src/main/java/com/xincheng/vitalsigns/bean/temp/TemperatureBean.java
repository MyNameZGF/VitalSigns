package com.xincheng.vitalsigns.bean.temp;

import java.util.List;

public class TemperatureBean {
    private String[][] titleDatas;//表头数据
    private TimeBean timeBean;//时间那一行的数据
    private List<String> bottomTitles;//底部区域左侧标题
    private int position;//当前日期是哪个

    public TemperatureBean() {}

    public TemperatureBean(String[][] titleDatas, TimeBean timeBean, List<String> bottomTitles, int position) {
        this.titleDatas = titleDatas;
        this.timeBean = timeBean;
        this.bottomTitles = bottomTitles;
        this.position = position;
    }

    public String[][] getTitleDatas() {
        return titleDatas;
    }

    public void setTitleDatas(String[][] titleDatas) {
        this.titleDatas = titleDatas;
    }

    public TimeBean getTimeBean() {
        return timeBean;
    }

    public void setTimeBean(TimeBean timeBean) {
        this.timeBean = timeBean;
    }

    public List<String> getBottomTitles() {
        return bottomTitles;
    }

    public void setBottomTitles(List<String> bottomTitles) {
        this.bottomTitles = bottomTitles;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
