package com.xincheng.vitalsigns.bean;

public class BloodTypeBean {
    //血糖类型id
    private int id;
    //是否被选中
    private int isSelected;
    //类型名称
    private String typeName;
    //类型
    private int type;

    public BloodTypeBean(){

    }

    public BloodTypeBean(int id, int isSelected, String typeName, int type) {
        this.id = id;
        this.isSelected = isSelected;
        this.typeName = typeName;
        this.type = type;
    }

    @Override
    public String toString() {
        return "BloodTypeBean{" +
                "id=" + id +
                ", isSelected=" + isSelected +
                ", typeName='" + typeName + '\'' +
                ", type=" + type +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
