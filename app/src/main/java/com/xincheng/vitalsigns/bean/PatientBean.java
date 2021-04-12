package com.xincheng.vitalsigns.bean;

import java.io.Serializable;
import java.util.List;

public class PatientBean implements Serializable {
    private String bedNo;
    private boolean isMeasure;//是否测量
    private String name;
    private String sex;
    private String age;
    private int measureCount;
    private int totalMeasureCount;
    private String measureTime;
    private String ad;

    private String body_temperature;//体温
    private String blood_oxygen;//血氧
    private int commonValue;//正常血压值
    private int currentValue;//当前血压值
    private String blood_glucose;//血糖
    private String isAfter;//是否是餐后
    private String pulse_rate;//脉率
    private String perfusion_index;//灌注指数
    private String breathe;//呼吸
    private String excrement;//大便
    private List<Object> bow;//血氧波




    public PatientBean() {
    }

    public PatientBean(String bedNo, boolean isMeasure, String name, String sex, String age, int measureCount, int totalMeasureCount, String measureTime) {
        this.bedNo = bedNo;
        this.isMeasure = isMeasure;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.measureCount = measureCount;
        this.totalMeasureCount = totalMeasureCount;
        this.measureTime = measureTime;
    }

    public PatientBean(String bedNo, boolean isMeasure, String name, String sex, String age, int measureCount, int totalMeasureCount, String measureTime, String ad, String body_temperature, String blood_oxygen, int commonValue, int currentValue, String blood_glucose, String isAfter, String pulse_rate, String perfusion_index, String breathe, String excrement, List<Object> bow) {
        this.bedNo = bedNo;
        this.isMeasure = isMeasure;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.measureCount = measureCount;
        this.totalMeasureCount = totalMeasureCount;
        this.measureTime = measureTime;
        this.ad = ad;
        this.body_temperature = body_temperature;
        this.blood_oxygen = blood_oxygen;
        this.commonValue = commonValue;
        this.currentValue = currentValue;
        this.blood_glucose = blood_glucose;
        this.isAfter = isAfter;
        this.pulse_rate = pulse_rate;
        this.perfusion_index = perfusion_index;
        this.breathe = breathe;
        this.excrement = excrement;
        this.bow = bow;
    }

    public PatientBean(String s, String s1, String 慕言) {
    }


    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAd() {
        return "123456";
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getBody_temperature() {
        return body_temperature;
    }

    public void setBody_temperature(String body_temperature) {
        this.body_temperature = body_temperature;
    }

    public String getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(String blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public int getCommonValue() {
        return commonValue;
    }

    public void setCommonValue(int commonValue) {
        this.commonValue = commonValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

    public String getBlood_glucose() {
        return blood_glucose;
    }

    public void setBlood_glucose(String blood_glucose) {
        this.blood_glucose = blood_glucose;
    }

    public String getIsAfter() {
        return isAfter;
    }

    public void setIsAfter(String isAfter) {
        this.isAfter = isAfter;
    }

    public String getPulse_rate() {
        return pulse_rate;
    }

    public void setPulse_rate(String pulse_rate) {
        this.pulse_rate = pulse_rate;
    }

    public String getPerfusion_index() {
        return perfusion_index;
    }

    public void setPerfusion_index(String perfusion_index) {
        this.perfusion_index = perfusion_index;
    }

    public String getBreathe() {
        return breathe;
    }

    public void setBreathe(String breathe) {
        this.breathe = breathe;
    }

    public String getExcrement() {
        return excrement;
    }

    public void setExcrement(String excrement) {
        this.excrement = excrement;
    }

    public List<Object> getBow() {
        return bow;
    }

    public void setBow(List<Object> bow) {
        this.bow = bow;
    }

    public boolean isMeasure() {
        return isMeasure;
    }

    public void setMeasure(boolean measure) {
        isMeasure = measure;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getMeasureCount() {
        return measureCount;
    }

    public void setMeasureCount(int measureCount) {
        this.measureCount = measureCount;
    }

    public int getTotalMeasureCount() {
        return totalMeasureCount;
    }

    public void setTotalMeasureCount(int totalMeasureCount) {
        this.totalMeasureCount = totalMeasureCount;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(String measureTime) {
        this.measureTime = measureTime;
    }


    @Override
    public String toString() {
        return "PatientBean{" +
                "bedNo='" + bedNo + '\'' +
                ", isMeasure=" + isMeasure +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age='" + age + '\'' +
                ", measureCount=" + measureCount +
                ", totalMeasureCount=" + totalMeasureCount +
                ", measureTime='" + measureTime + '\'' +
                ", ad='" + ad + '\'' +
                ", body_temperature='" + body_temperature + '\'' +
                ", blood_oxygen='" + blood_oxygen + '\'' +
                ", commonValue=" + commonValue +
                ", currentValue=" + currentValue +
                ", blood_glucose='" + blood_glucose + '\'' +
                ", isAfter='" + isAfter + '\'' +
                ", pulse_rate='" + pulse_rate + '\'' +
                ", perfusion_index='" + perfusion_index + '\'' +
                ", breathe='" + breathe + '\'' +
                ", excrement='" + excrement + '\'' +
                ", bow=" + bow +
                '}';
    }
}
