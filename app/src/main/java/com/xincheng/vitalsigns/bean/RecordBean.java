package com.xincheng.vitalsigns.bean;

public class RecordBean {
    private PatientBean bean;
    private boolean isCheck;
    private int scnyType;//0 ->同步；1->未同步；2->同步失败
    private int historyCount;
    private int order;

    public RecordBean() {
    }

    public RecordBean(PatientBean bean) {
        this.bean = bean;
    }

    public RecordBean(PatientBean bean, int scnyType, int historyCount) {
        this.bean = bean;
        this.scnyType = scnyType;
        this.historyCount = historyCount;
    }

    public RecordBean(PatientBean bean, int scnyType, int historyCount, int order) {
        this.bean = bean;
        this.scnyType = scnyType;
        this.historyCount = historyCount;
        this.order = order;
    }

    public PatientBean getBean() {
        return bean;
    }

    public void setBean(PatientBean bean) {
        this.bean = bean;
    }

    public boolean getCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public int getScnyType() {
        return scnyType;
    }

    public void setScnyType(int scnyType) {
        this.scnyType = scnyType;
    }

    public int getHistoryCount() {
        return historyCount;
    }

    public void setHistoryCount(int historyCount) {
        this.historyCount = historyCount;
    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
