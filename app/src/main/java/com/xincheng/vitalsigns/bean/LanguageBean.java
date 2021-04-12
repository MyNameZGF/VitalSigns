package com.xincheng.vitalsigns.bean;

public class LanguageBean {
    private String language;
    private boolean selected;
    private boolean lastItem;

    public LanguageBean() {
    }

    public LanguageBean(String language, boolean selected, boolean lastItem) {
        this.language = language;
        this.selected = selected;
        this.lastItem = lastItem;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isLastItem() {
        return lastItem;
    }

    public void setLastItem(boolean lastItem) {
        this.lastItem = lastItem;
    }
}
