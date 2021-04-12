package com.xincheng.vitalsigns.event;

public class LoginChangEvent {
    //0=登陆、1=注册、2=找回密码
    public int type;

    public LoginChangEvent(){

    }

    public LoginChangEvent(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LoginChangEvent{" +
                "type=" + type +
                '}';
    }
}
