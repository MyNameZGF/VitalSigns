package com.xincheng.vitalsigns.ui.fragment.setting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.SeekBar;
import android.widget.Switch;

import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.MultiAdapter;
import com.xincheng.vitalsigns.bean.MultiBean;
import com.xincheng.vitalsigns.bean.ScreenBean;
import com.xincheng.vitalsigns.bean.VoiceBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;
import com.xincheng.vitalsigns.utlis.SettingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 声音fragment
 */
public class VoiceFragment extends BaseFragment {
    private RecyclerView rv_voice;
    private MultiAdapter adapter;
    private List<MultiBean<VoiceBean>> datas;

    private boolean voiceTip;//声音提醒
    private boolean mute;//静音
    private int itemGonePosition = -1;
    private MultiBean<VoiceBean> itemGone;
    private static int minVoice;
    private static int maxVoice;

    /**
     * 当前音量，静音暂时没找到实现的方式，用这种方式代替
     */
    private int currentVoice;

    /**
     * 声音类型
     */
    private static int voiceType;


    public static BaseFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_MENU, menu);
        //BleFragment fragment = new BleFragment();
        //fragment.setArguments(args);
        return new VoiceFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_voice;
    }

    @Override
    protected void initView() { rv_voice = $(R.id.rv_voice); }

    @Override
    protected void initData() {
        minVoice = SettingUtils.getMinVolume(getActivity().getApplication());
        maxVoice = SettingUtils.getMaxVolume(getActivity().getApplication());
        datas = getdatas();
        if(adapter == null){
            adapter = new MultiAdapter<MultiBean<VoiceBean>>(datas) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, MultiBean<VoiceBean> item) {
                    switch (item.getItemType()){
                        case VoiceBean.TYPE_HEADER:
                            helper.setText(R.id.tv_header,item.getHearder());
                            break;

                        case VoiceBean.TYPE_VOICE:
                            helper.setText(R.id.tv_label,R.string.voice);
                            SeekBar seekBar = helper.getView(R.id.seekBar);
                            seekBar.setMax(maxVoice);
                            seekBar.setProgress(item.getData().getVoice());
                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    item.getData().setVoice(progress);

                                    //TODO 设置系统亮度
                                }
                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) { }
                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {
                                    setVoice(seekBar.getProgress());
                                }

                            });
                            break;

                        case VoiceBean.TYPE_VOICE_TYPE:
                            helper.setText(R.id.tv_label,item.getData().getVoiceMode() == 0 ? getString(R.string.voiceTip) : getString(R.string.mute))
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            Switch sw = helper.getView(R.id.swith);
                            switch (item.getData().getVoiceMode()){
                                case 0:
                                    sw.setChecked(item.getData().isVoiceTip());
                                    break;

                                default:
                                    sw.setChecked(item.getData().isMute());
                                    break;
                            }
                            sw.setOnCheckedChangeListener((buttonView,isChecked)-> {
                                openVoiceTipOrMute(item.getData().getVoiceMode(),isChecked);
                            });
                            break;

                    }
                }

                @Override
                protected void addItemTypes() {
                    addItemType(VoiceBean.TYPE_HEADER, R.layout.multipe_screen_header);
                    addItemType(VoiceBean.TYPE_VOICE, R.layout.multipe_screen_light);
                    addItemType(VoiceBean.TYPE_VOICE_TYPE, R.layout.multipe_screen_light_mode);
                }
            };
            rv_voice.setAdapter(adapter);
        }
    }

    private List<MultiBean<VoiceBean>> getdatas() {
        List<MultiBean<VoiceBean>> datas = new ArrayList<>();
        voiceTip = (boolean) SP.getData(Constant.DEFUALT_VOICE_TIP_KEY,true);//默认是开启
        mute = (boolean) SP.getData(Constant.DEFUALT_MUTE_KEY,false);//默认关闭
        int i = 0;
        datas.add(new MultiBean<>(VoiceBean.TYPE_HEADER,getActivity().getString(R.string.voice_setting)));
        i++;

        itemGonePosition = i;//这里是为了了确定需要隐藏的item的position
        currentVoice = getVoice();
        itemGone = new MultiBean<>(new VoiceBean(currentVoice),VoiceBean.TYPE_VOICE);//先保存这个item
        if(!mute){
            datas.add(itemGone);//如果不需要隐藏，则添加显示
        }else{
            i--;//如果需要隐藏，则上面已经i++了，所以需要减去1.不然位置会错乱。
        }
        i++;
        datas.add(new MultiBean<>(new VoiceBean(0,voiceTip),VoiceBean.TYPE_VOICE_TYPE));
        //设置是否开启语音提醒，因为之前已经保存了  voiceTip = (boolean) SP.getData(Constant.DEFUALT_VOICE_TIP_KEY,true);//默认是开启，所以不需要设置了。

        i++;
        datas.add(new MultiBean<>(new VoiceBean(1,mute,true),ScreenBean.TYPE_LIGHT_MODE));
        return datas;
    }

    /**
     * 设置是否开启语音提醒
     * @param voiceTip
     */
    private void setVoiceTip(boolean voiceTip) {
        SP.putData(Constant.DEFUALT_VOICE_TIP_KEY,voiceTip);//保存设置
    }

    /**
     * 获取系统音量
     * @return
     */
    private int getVoice() {
        return SettingUtils.getVolume(getActivity().getApplication());
    }

    /**
     * 设置音量
     * @param progress
     */
    private void setVoice(int progress) {
        SettingUtils.setVolume(progress,getActivity().getApplication());
    }

    /**
     * 开启 or 静音
     * @param isMute
     */
    private void openVoice(boolean isMute){
        if(isMute){
            currentVoice = getVoice();
            setVoice(0);
            datas.remove(itemGonePosition);
            adapter.notifyItemRemoved(itemGonePosition);
        }else{
            setVoice(currentVoice);
            datas.add(itemGonePosition,itemGone);
            adapter.notifyItemInserted(itemGonePosition);
        }
    }

    /**
     * 设置是否开启语音提醒or静音
     * @param mode 0->自动模式；1->护眼模式
     * @param voiceTypeOrMute 是否开启语音提醒 or 静音
     */
    private void openVoiceTipOrMute(int mode,boolean voiceTypeOrMute){
        switch (mode){
            case 0:
                voiceTip = voiceTypeOrMute;
                setVoiceTip(voiceTypeOrMute);
                SP.putData(Constant.DEFUALT_VOICE_TIP_KEY,voiceTypeOrMute);
                break;

            default:
                mute = voiceTypeOrMute;
                SP.putData(Constant.DEFUALT_MUTE_KEY,mute);
                openVoice(mute);
                break;
        }
    }
}
