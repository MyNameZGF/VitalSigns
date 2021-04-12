package com.xincheng.vitalsigns.ui.fragment.setting;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.Switch;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.MultiAdapter;
import com.xincheng.vitalsigns.bean.MultiBean;
import com.xincheng.vitalsigns.bean.ScreenBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;
import com.xincheng.vitalsigns.utlis.SettingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * 屏幕模式
 */
public class ScreenFragment extends BaseFragment {
    private RecyclerView rv_screen;
    private MultiAdapter adapter;
    private List<MultiBean<ScreenBean>> datas;
    private static int preTimePosition;
    private boolean auto;//自动调节
    private boolean eyeshield;//护眼模式
    private int itemGonePosition = -1;

    private MultiBean<ScreenBean> itemGone;


    public static BaseFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_MENU, menu);
        //BleFragment fragment = new BleFragment();
        //fragment.setArguments(args);
        return new ScreenFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_screen;
    }

    @Override
    protected void initView() {
        rv_screen = $(R.id.rv_screen);
    }

    @Override
    protected void initData() {
        datas = getdatas();
        if(adapter == null){
            adapter = new MultiAdapter<MultiBean<ScreenBean>>(datas) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, MultiBean<ScreenBean> item) {
                    switch (item.getItemType()){
                        case ScreenBean.TYPE_HEADER:
                            helper.setText(R.id.tv_header,item.getHearder());
                            break;

                        case ScreenBean.TYPE_LIGHT:
                            SeekBar seekBar = helper.getView(R.id.seekBar);
                            seekBar.setProgress(item.getData().getLight());
                            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                @Override
                                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                    item.getData().setLight(progress);

                                    //TODO 设置系统亮度
                                }
                                @Override
                                public void onStartTrackingTouch(SeekBar seekBar) { }
                                @Override
                                public void onStopTrackingTouch(SeekBar seekBar) {
                                    setScreenLight(seekBar.getProgress());
                                }
                            });
                            break;

                        case ScreenBean.TYPE_LIGHT_MODE:
                            helper.setText(R.id.tv_label,item.getData().getLightMode() == 0 ? getString(R.string.autoAjust) : getString(R.string.eyeshieldMode))
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            Switch sw = helper.getView(R.id.swith);
                            switch (item.getData().getLightMode()){
                                case 0:
                                    sw.setChecked(item.getData().isAutoLight());
                                    break;

                                default:
                                    sw.setChecked(item.getData().isGreenLight());
                                    break;
                            }
                            sw.setOnCheckedChangeListener((buttonView,isChecked)-> {
                                openAutoOrEyeshield(item.getData().getLightMode(),isChecked);
                            });
                            break;

                        case ScreenBean.TYPE_SLEEP_TIME:
                            helper.setText(R.id.tv_time,item.getData().getSleepTime())
                                    .setGone(R.id.iv,item.getData().isSleepItemSelected() ? true : false)
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            break;
                    }
                }

                @Override
                protected void addItemTypes() {
                    addItemType(ScreenBean.TYPE_HEADER, R.layout.multipe_screen_header);
                    addItemType(ScreenBean.TYPE_LIGHT, R.layout.multipe_screen_light);
                    addItemType(ScreenBean.TYPE_LIGHT_MODE, R.layout.multipe_screen_light_mode);
                    addItemType(ScreenBean.TYPE_SLEEP_TIME, R.layout.multipe_screen_sleep_time);
                }
            };
            rv_screen.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter,view,position)-> {
                    if(datas.get(position).getItemType() == ScreenBean.TYPE_SLEEP_TIME){
                        Log.e("postition","position:"+position);
                        if(preTimePosition != position){
                            datas.get(preTimePosition).getData().setSleepItemSelected(false);
                            adapter.notifyItemChanged(preTimePosition);
                            SP.putData(Constant.DEFUALT_SCREEN_PROTECT_TIME_MODE_KEY,datas.get(position).getData().getSleepItem());//保存默认设置
                            datas.get(position).getData().setSleepItemSelected(true);
                            adapter.notifyItemChanged(position);
                            preTimePosition = position;
                            setSysScreenTime(datas.get(position).getData().getSleepItem());
                        }
                    }
            });
        }
    }

    /**
     * 设置系统屏幕保护
     * @param sleepItem
     */
    private void setSysScreenTime(int sleepItem) {
        long time;
        switch (sleepItem){
            case 0: //5分钟
                time = 5*60*1000L;
                break;

            case 1: //15分钟
                time = 15*60*1000L;
                break;

            case 2: //30分钟
                time = 30*60*1000L;
                break;

            default://永不休眠
                time = 7*24*60*60*1000L;//待机10天
                break;
        }
        setScreenTime(time);
    }

    /**
     * 设置是否开启自动模式 or 护眼模式
     * @param mode 0->自动模式；1->护眼模式
     * @param setOrDefualt 是否开启自动 or 护眼模式
     */
    private void openAutoOrEyeshield(int mode, boolean setOrDefualt){
        switch (mode){
            case 0:
                auto = setOrDefualt;
                openOrCloseAuto(setOrDefualt);
                SP.putData(Constant.DEFUALT_SCREEN_AUTO_KEY,setOrDefualt);
                if(setOrDefualt){
                    datas.remove(itemGonePosition);
                    adapter.notifyItemRemoved(itemGonePosition);
                }else{
                    datas.add(itemGonePosition,itemGone);
                    adapter.notifyItemInserted(itemGonePosition);
                }
                break;

            default:
                eyeshield = setOrDefualt;
                SP.putData(Constant.DEFUALT_SCREEN_EYESHIELD_KEY,setOrDefualt);
                break;
        }


    }


    private List<MultiBean<ScreenBean>> getdatas() {
        List<MultiBean<ScreenBean>> datas = new ArrayList<>();
        auto = (boolean) SP.getData(Constant.DEFUALT_SCREEN_AUTO_KEY,true);//默认是开启
        int i = 0;
        datas.add(new MultiBean<>(ScreenBean.TYPE_HEADER,getActivity().getString(R.string.show_setting)));
        i++;

        itemGonePosition = i;//这里是为了了确定需要隐藏的item的position
        itemGone = new MultiBean<>(new ScreenBean(getScreenLight()),ScreenBean.TYPE_LIGHT);//先保存这个item
        if(!auto){
            datas.add(itemGone);//如果不需要隐藏，则添加显示
        }else{
            i--;//如果需要隐藏，则上面已经i++了，所以需要减去1.不然位置会错乱。
        }
        i++;

        datas.add(new MultiBean<>(new ScreenBean(auto,0),ScreenBean.TYPE_LIGHT_MODE));
        openOrCloseAuto(auto);//设置默认的自己调节

        i++;
        eyeshield = (boolean) SP.getData(Constant.DEFUALT_SCREEN_EYESHIELD_KEY,false);//默认关闭
        datas.add(new MultiBean<>(new ScreenBean(eyeshield,1,true),ScreenBean.TYPE_LIGHT_MODE));
        i++;
        datas.add(new MultiBean<>(ScreenBean.TYPE_HEADER,getActivity().getString(R.string.sleep_setting)));

        ArrayList<String> menus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_screen_time)));
        int defualt_screen_protect_mode = (int) SP.getData(Constant.DEFUALT_SCREEN_PROTECT_TIME_MODE_KEY,0);//默认的屏幕保护时间
        boolean isDefualt;
        for(int j = 0;j<menus.size();j++){
            if(j == defualt_screen_protect_mode){
                preTimePosition = i+j+1;
                isDefualt = true;
            }else{
                isDefualt = false;
            }
            if(j == menus.size()-1){
                datas.add(new MultiBean<>(new ScreenBean(j,menus.get(j),isDefualt,true),ScreenBean.TYPE_SLEEP_TIME));//最后面的那个item下划线应该隐藏
            }else{
                datas.add(new MultiBean<>(new ScreenBean(j,menus.get(j),isDefualt),ScreenBean.TYPE_SLEEP_TIME));
            }

        }
        setSysScreenTime(defualt_screen_protect_mode);
        return datas;
    }

    /**
     * 获取屏幕亮度
     * @return
     */
    private int getScreenLight(){
        return SettingUtils.getBrightness(getActivity().getApplication());
    }

    /**
     * 设置屏幕亮度
     * @param progess
     */
    private void setScreenLight(int progess){
        boolean isOk =SettingUtils.setBrightness(progess,getActivity().getApplication());
        Log.e("isOk","屏幕亮度是否设置成功："+isOk);
    }

    /**
     * 开关自动调节
     * @param isOpen
     */
    private void openOrCloseAuto(boolean isOpen){
        SettingUtils.setAutoBrightnessEnabled(isOpen,getActivity().getApplication());
    }

    /**
     * 设置屏幕保护
     * @param time
     */
    private void setScreenTime(long time){
        boolean setOK = SettingUtils.setSleepDuration(getActivity().getApplication(),time);
        Log.e("isOk","屏幕保护是否设置成功："+setOK);
    }
}
