package com.xincheng.vitalsigns.bean;

import java.io.Serializable;

public class SettingMenuBean implements Serializable {
    private int icon;
    private String menuName;
    private boolean select;

    public SettingMenuBean() {
    }

    public SettingMenuBean(int icon, String menuName) {
        this.icon = icon;
        this.menuName = menuName;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}
