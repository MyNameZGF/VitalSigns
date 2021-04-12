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

/**
 * 血糖历史记录自定义表格
 */
public class TempBloodSugarAdapter extends BaseQuickAdapter<TempDetailsBean, BaseViewHolder> {
    private int type;
    public TempBloodSugarAdapter(int layoutResId, @Nullable List<TempDetailsBean> data, int type) {
        super(layoutResId, data);
        this.type = type;
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TempDetailsBean item) {
        TextView tv = helper.getView(R.id.tv_content);
        TextPaint tp = tv .getPaint();
        if(item.getPosition() < 5){//设置粗体
            tp.setFakeBoldText(true);
        }else{
            tp.setFakeBoldText(false);
        }
        helper.setTextColor(R.id.tv_content,item.getPosition()<5 ? mContext.getResources().getColor(R.color.titleColor) : mContext.getResources().getColor(R.color.sysnColor));

        String content = null;
        if(item.getPosition()<5){//表格第一行
            switch (item.getPosition()){
                case 0:
                    content = mContext.getString(R.string.measureTimes);
                    break;
                case 1:
                    content = mContext.getString(R.string.glucose);
                    break;
                case 2:
                    content = mContext.getString(R.string.type);
                    break;
                case 3:
                    content = mContext.getString(R.string.measure_peo);
                    break;
                default:
                    content = mContext.getString(R.string.syncStatus);
                    break;
            }
        }else{
            switch (item.getPosition() % 5){
                case 0:
                    content = item.getMearSureTime();
                    System.out.println("第1格"+content+"第1格");
                    break;
                case 1:
                    content = Float.toString(item.getBlood_glucose());
                    if(item.getBlood_glucose()==0){
                        content = " ";
                    }
                    System.out.println("第2格"+item.getBlood_glucose()+"第2格");
                    break;
                case 2:
                    content = item.getMeasurerType();
                    if(content==null){
                        content=" ";
                    }
                    System.out.println("第3格"+content+"第3格");
                    break;
                case 3:
                    content = item.getMeasurer();
                    if(content==null){
                        content=" ";
                    }
                    System.out.println("第4格"+content+"第4格");
                    break;
                default:
                    content = (item.getType() == 0 ? "已同步" : (item.getType() == 1 ? "未同步" : "同步失败"));
                    if(item.getType()==-1){
                        content=" ";
                    }
                    System.out.println("第5格"+content+"第5格");
                    break;
            }
        }

        helper.setText(R.id.tv_content,content);

        if((item.getPosition()+1) % 5 ==2 && item.getPosition() > 3){
            helper.setTextColor(R.id.tv_content,item.getBlood_oxygen()>=6.5 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.colorPrimary));
        }
        if(item.getPosition() % 5 ==0 && item.getPosition() ==5){
            helper.setTextColor(R.id.tv_content, mContext.getResources().getColor(R.color.colorPrimary));
        }
        if(item.getPosition() <10 && item.getPosition() >=5){
            helper.setBackgroundColor(R.id.tv_content,mContext.getResources().getColor(R.color.black6));
        }
        if((item.getPosition()+1) % 5 ==0 && item.getPosition() > 3){
            helper.setTextColor(R.id.tv_content,item.getType()==2 ? mContext.getResources().getColor(R.color.colorRed) : (item.getType() == 0 ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.sysnColor)));
        }
    }
}
