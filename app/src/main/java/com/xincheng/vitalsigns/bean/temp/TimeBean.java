package com.xincheng.vitalsigns.bean.temp;

import java.util.List;

public class TimeBean {
    private String title;
    private String am;//上午
    private String an;//下午
    private List<String> amTitleDatas;
    private List<String> anTitleDatas;
    private List<List<Float>> temperatureValueDatas;
    private List<List<Integer>> pluseValueDatas;


    public TimeBean() {
    }

    public TimeBean(String title, String am, String an, List<String> amTitleDatas, List<String> anTitleDatas) {
        this.title = title;
        this.am = am;
        this.an = an;
        this.amTitleDatas = amTitleDatas;
        this.anTitleDatas = anTitleDatas;
    }

    public TimeBean(String title, String am, String an, List<String> amTitleDatas, List<String> anTitleDatas, List<List<Float>> temperatureValueDatas, List<List<Integer>> pluseValueDatas) {
        this.title = title;
        this.am = am;
        this.an = an;
        this.amTitleDatas = amTitleDatas;
        this.anTitleDatas = anTitleDatas;
        this.temperatureValueDatas = temperatureValueDatas;
        this.pluseValueDatas = pluseValueDatas;
    }

    public String getAm() {
        return am;
    }

    public void setAm(String am) {
        this.am = am;
    }

    public String getAn() {
        return an;
    }

    public void setAn(String an) {
        this.an = an;
    }

    public List<String> getAmTitleDatas() {
        return amTitleDatas;
    }

    public void setAmTitleDatas(List<String> amTitleDatas) {
        this.amTitleDatas = amTitleDatas;
    }

    public List<String> getAnTitleDatas() {
        return anTitleDatas;
    }

    public void setAnTitleDatas(List<String> anTitleDatas) {
        this.anTitleDatas = anTitleDatas;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<List<Float>> getTemperatureValueDatas() {
        return temperatureValueDatas;
    }

    public void setTemperatureValueDatas(List<List<Float>> temperatureValueDatas) {
        this.temperatureValueDatas = temperatureValueDatas;
    }



    public List<List<Integer>> getPluseValueDatas() {
        return pluseValueDatas;
    }

    public void setPluseValueDatas(List<List<Integer>> pluseValueDatas) {
        this.pluseValueDatas = pluseValueDatas;
    }

}
