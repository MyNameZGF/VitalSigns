package com.xincheng.vitalsigns.adpter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.TempDetailsBean;

import java.util.List;

public class TempDetailsAdapter extends BaseQuickAdapter<TempDetailsBean, BaseViewHolder> {
    /**
     * 0 ->温度
     * 1 ->血氧
     * 2 ->血压
     * 3 ->血糖
     * 4 ->脉率
     * 5 ->呼吸
     * 6 ->大便
     */
    private int type;
    public TempDetailsAdapter(int layoutResId, @Nullable List<TempDetailsBean> data,int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TempDetailsBean item) {
        TextView tv = helper.getView(R.id.tv_content);
        TextPaint tp = tv .getPaint();
        if(item.getPosition() < 4){//设置粗体
            tp.setFakeBoldText(true);
        }else{
            tp.setFakeBoldText(false);
        }
        helper.setTextColor(R.id.tv_content,item.getPosition()<4 ? mContext.getResources().getColor(R.color.titleColor) : mContext.getResources().getColor(R.color.titleColor80));

        String content = null;
        if(item.getPosition()<4){
            switch (item.getPosition()){
                case 0:
                    content = mContext.getString(R.string.measureTimes);
                    break;

                case 1:
                    switch (type){
                        case 0:
                            content = mContext.getString(R.string.temp);
                            break;

                        case 1:
                            content = mContext.getString(R.string.bloodOxygen);
                            break;

                        case 2:
                            content = mContext.getString(R.string.bloodPressure);
                            break;

                        case 4:
                            content = mContext.getString(R.string.pulseRate);
                            break;

                        case 5:
                            content = mContext.getString(R.string.breathes);
                            break;

                        default:
                            content = mContext.getString(R.string.excrements);
                            break;
                    }

                    break;

                case 2:
                    content = mContext.getString(R.string.measurer);
                    break;

                default:
                    content = mContext.getString(R.string.syncStatus);
                    break;
            }
        }else{
            switch (item.getPosition() % 4){
                case 0:
                    content = item.getMearSureTime();
                    break;

                case 1:
                    switch (type){
                        case 0:
                            content = item.getTemp()+"°";
                            break;

                        case 1:
                            content = item.getBlood_oxygen()+"%";
                            break;

                        case 2:
                            content = item.getCurrentValue()+"/130mmHg";
                            break;

                        case 3:

                            break;

                        case 4:
                            content = ((int)item.getPulse_rate())+"次/min";
                            break;

                        case 5:
                            content = ((int)item.getBreathe())+"次/min";
                            break;

                        default:
                            content = ((int)item.getExcrement())+"次/天";
                            break;
                    }
                    break;

                case 2:
                    content = item.getMeasurer();
                    break;

                default:
                    content = (item.getType() == 0 ? "已同步" : (item.getType() == 1 ? "未同步" : "同步失败"));
                    break;
            }
        }
        helper.setText(R.id.tv_content,content);


        if((item.getPosition()+1) % 4 ==2 && item.getPosition() > 3){
            switch (type){
                case 0:
                    helper.setTextColor(R.id.tv_content,item.getTemp()>=37.5 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;

                case 1:
                    helper.setTextColor(R.id.tv_content,item.getBlood_oxygen()<88 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;

                case 2:
                    helper.setTextColor(R.id.tv_content,item.getCurrentValue()>125 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;

                case 3:
                    break;

                case 4:
                    helper.setTextColor(R.id.tv_content,item.getPulse_rate()>100 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;

                case 5:
                    helper.setTextColor(R.id.tv_content,item.getBreathe()<=15 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;

                default:
                    helper.setTextColor(R.id.tv_content,item.getExcrement()>2 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
                    break;
            }
        }
        if((item.getPosition()+1) % 4 ==0 && item.getPosition() > 3){
            helper.setTextColor(R.id.tv_content,item.getType()==2 ? mContext.getResources().getColor(R.color.colorRed) : (item.getType() == 0 ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.titleColor80)));
        }
    }
}
