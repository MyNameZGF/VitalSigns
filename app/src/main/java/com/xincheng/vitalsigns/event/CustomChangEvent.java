package com.xincheng.vitalsigns.event;

public class CustomChangEvent {
    public int startPosition;
    public int changePositin;
    public int deleteOrAddPosition;
    public int type;//0->位置变更；1->删除；2->添加
    public boolean isAdd;//false-删除，true-添加

    public CustomChangEvent(int type,int startPosition, int changePositin) {
        this.type = type;
        this.startPosition = startPosition;
        this.changePositin = changePositin;
    }

    public CustomChangEvent(int type,int deleteOrAddPosition,boolean isAdd) {
        this.type = type;
        this.deleteOrAddPosition = deleteOrAddPosition;
        this.isAdd = isAdd;
    }

    @Override
    public String toString() {
        return "CustomChangEvent{" +
                "startPosition=" + startPosition +
                ", changePositin=" + changePositin +
                ", deleteOrAddPosition=" + deleteOrAddPosition +
                ", type=" + type +
                ", isAdd=" + isAdd +
                '}';
    }
}
