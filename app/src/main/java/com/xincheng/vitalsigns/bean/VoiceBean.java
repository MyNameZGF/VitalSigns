package com.xincheng.vitalsigns.bean;

public class VoiceBean {
    public static final int TYPE_HEADER = 1;//标题
    public static final int TYPE_VOICE = 2;//音量
    public static final int TYPE_VOICE_TYPE = 3;//0->语音提醒，1->静音


    private int voice;
    private int voiceMode;//0 语音提醒，静音
    private boolean voiceTip;//语音提醒
    private boolean mute;//静音
    private boolean isLastItem;//是否是最后一个item


    public VoiceBean(int voice) {
        this.voice = voice;
    }

    public VoiceBean(int voiceMode, boolean voiceTipOrMute) {
        this.voiceMode = voiceMode;
        switch (voiceMode){
            case 0:
                this.voiceTip = voiceTipOrMute;
                break;
            default:
                this.mute = voiceTipOrMute;
                break;
        }
    }

    public VoiceBean(int voiceMode, boolean voiceTipOrMute, boolean isLastItem) {
        this.voiceMode = voiceMode;
        this.isLastItem = isLastItem;
        switch (voiceMode){
            case 0:
                this.voiceTip = voiceTipOrMute;
                break;
            default:
                this.mute = voiceTipOrMute;
                break;
        }
    }

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }

    public int getVoiceMode() {
        return voiceMode;
    }

    public void setVoiceMode(int voiceMode) {
        this.voiceMode = voiceMode;
    }

    public boolean isVoiceTip() {
        return voiceTip;
    }

    public void setVoiceTip(boolean voiceTip) {
        this.voiceTip = voiceTip;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public boolean isLastItem() {
        return isLastItem;
    }

    public void setLastItem(boolean lastItem) {
        isLastItem = lastItem;
    }
}
