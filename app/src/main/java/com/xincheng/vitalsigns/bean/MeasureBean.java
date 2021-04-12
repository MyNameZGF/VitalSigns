package com.xincheng.vitalsigns.bean;

import java.util.List;

public class MeasureBean {
    public static final int TYPE_COMMON = 0;//
    public static final int TYPE_BOW = 1;//血氧波
    //测量项目
    private String project;
    //测量结果
    private String result;

    //血压
    private int defaultValue;
    private int currentValue;

    private List<? extends Object> chartDatas;

    //测量项目类型 0 ->体温 ；1 ->血氧；2->血压；3->血糖；4->脉搏；5->呼吸；6->小便 7->大便
    private int inputType;

    private boolean isAfter;//是否餐后


    public MeasureBean(String project, String result, int inputType) {
        this.project = project;
        this.result = result;
        this.inputType = inputType;
    }

    public MeasureBean(List<Object> chartDatas) {
        this.chartDatas = chartDatas;
    }

    public MeasureBean(String project, int defualtValue, int currentValue) {
        this.project = project;
        this.defaultValue = defualtValue;
        this.currentValue = currentValue;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getDefualtValue() {
        return defaultValue;
    }

    public void setDefualtValue(int defualtValue) {
        this.defaultValue = defualtValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public List<? extends Object> getChartDatas() {
        return chartDatas;
    }

    public void setChartDatas(List<? extends Object> chartDatas) {
        this.chartDatas = chartDatas;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public boolean isAfter() {
        return isAfter;
    }

    public void setAfter(boolean after) {
        isAfter = after;
    }

    @Override
    public String toString() {
        return "MeasureBean{" +
                "project='" + project + '\'' +
                ", result='" + result + '\'' +
                ", defualtValue=" + defaultValue +
                ", currentValue=" + currentValue +
                ", chartDatas=" + chartDatas +
                ", inputType=" + inputType +
                ", isAfter=" + isAfter +
                '}';
    }
}
