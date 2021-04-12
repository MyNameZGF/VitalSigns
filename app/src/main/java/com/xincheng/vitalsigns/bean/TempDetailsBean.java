package com.xincheng.vitalsigns.bean;

import java.io.Serializable;

public class TempDetailsBean implements Serializable {
    private String mearSureTime;//测量时间
    private float temp;//温度
    private String measurer;//测量人
    private int type;//同步状态   0->已同步；1->未同步；2->同步失败
    private int position;
    private String measurerType;//测量类型
    /**
     * 0 ->温度
     * 1 ->血氧
     * 2 ->血压
     * 3 ->血糖
     * 4 ->脉率
     * 5 ->呼吸
     * 6 ->大便
     */
    private int mode;
    private float blood_oxygen;//血氧
    private float currentValue;//当前血压值
    private float blood_glucose;//血糖
    private float pulse_rate;//脉率
    private float breathe;//呼吸
    private float excrement;//大便

    public TempDetailsBean(String mearSureTime) {
        this.mearSureTime = mearSureTime;
    }

    public TempDetailsBean(String mearSureTime, float content, String measurer, int type, int position, int mode) {
        this.mode = mode;
        this.mearSureTime = mearSureTime;
        this.measurer = measurer;
        this.type = type;
        this.position = position;
        switch (mode){
            case 0:
                this.temp = content;//温度
                break;

            case 1:
                this.blood_oxygen = content;
                break;

            case 2:
                this.currentValue = content;
                break;

            case 3:
                this.blood_glucose = content;
                break;

            case 4:
                this.pulse_rate = content;
                break;

            case 5:
                this.breathe = content;
                break;

            default:
                this.excrement = content;
                break;
        }
    }
    public TempDetailsBean(String mearSureTime, float content, String measurerType,String measurer, int type, int position,int mode) {
        this.mode = mode;
        this.mearSureTime = mearSureTime;
        this.measurer = measurer;
        this.type = type;
        this.position = position;
        this.measurerType = measurerType;
        this.blood_glucose = content;
    }


    public String getMearSureTime() {
        return mearSureTime;
    }

    public void setMearSureTime(String mearSureTime) {
        this.mearSureTime = mearSureTime;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public String getMeasurer() {
        return measurer;
    }

    public void setMeasurer(String measurer) {
        this.measurer = measurer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public float getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(float blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(float currentValue) {
        this.currentValue = currentValue;
    }

    public float getBlood_glucose() {
        return blood_glucose;
    }

    public void setBlood_glucose(float blood_glucose) {
        this.blood_glucose = blood_glucose;
    }

    public float getPulse_rate() {
        return pulse_rate;
    }

    public void setPulse_rate(float pulse_rate) {
        this.pulse_rate = pulse_rate;
    }

    public float getBreathe() {
        return breathe;
    }

    public void setBreathe(float breathe) {
        this.breathe = breathe;
    }

    public float getExcrement() {
        return excrement;
    }

    public void setExcrement(float excrement) {
        this.excrement = excrement;
    }

    public String getMeasurerType() {
        return measurerType;
    }

    public void setMeasurerType(String measurerType) {
        this.measurerType = measurerType;
    }
}
