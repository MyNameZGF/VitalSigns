package com.xincheng.vitalsigns.adpter;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.RecordBean;

import java.util.List;

public class RecordOfSynchroAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    private CheckBox box ;
    public RecordOfSynchroAdapter(int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecordBean item) {
        helper.itemView.setBackgroundColor(item.getOrder() % 2 == 1 ? mContext.getResources().getColor(R.color.colorWhite) : mContext.getResources().getColor(R.color.tableColor));
        box = helper.getView(R.id.cv);
        box.setChecked(item.getCheck());
        if( item.getOrder() == 0){
            helper.setGone(R.id.cv, true)
                    .setText(R.id.tv_date,"测量时间")
                    .setText(R.id.tv_measure,"测量项目")
                    .setText(R.id.tv_ad,"检测人")
                    .setText(R.id.tv_status,"状态")
                    .setTypeface(R.id.tv_date, Typeface.DEFAULT_BOLD)
                    .setTypeface(R.id.tv_measure,Typeface.DEFAULT_BOLD)
                    .setTypeface(R.id.tv_ad,Typeface.DEFAULT_BOLD)
                    .setTypeface(R.id.tv_status,Typeface.DEFAULT_BOLD)
                    .setTextColor(R.id.tv_date,mContext.getResources().getColor(R.color.titleColor))
                    .setTextColor(R.id.tv_measure,mContext.getResources().getColor(R.color.titleColor))
                    .setTextColor(R.id.tv_ad,mContext.getResources().getColor(R.color.titleColor))
                    .setTextColor(R.id.tv_status,mContext.getResources().getColor(R.color.titleColor));
        }else{
            helper.setGone(R.id.cv,true)
                    .setText(R.id.tv_date,item.getBean().getMeasureTime())
                    .setText(R.id.tv_measure,item.getBean().getBody_temperature())
                    .setText(R.id.tv_ad,item.getBean().getAd())
                    .setText(R.id.tv_status,item.getScnyType() == 0 ? mContext.getResources().getString(R.string.sy) : (item.getScnyType() == 1 ? mContext.getResources().getString(R.string.dis_sy) : mContext.getResources().getString(R.string.sy_fial)))
                    .setTextColor(R.id.tv_status,item.getScnyType() == 2 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.sysnColor))
                    .setTypeface(R.id.tv_date, Typeface.DEFAULT)
                    .setTypeface(R.id.tv_measure,Typeface.DEFAULT)
                    .setTypeface(R.id.tv_ad,Typeface.DEFAULT)
                    .setTypeface(R.id.tv_status,Typeface.DEFAULT)
                    .setTextColor(R.id.tv_date,mContext.getResources().getColor(R.color.sysnColor))
                    .setTextColor(R.id.tv_measure,mContext.getResources().getColor(R.color.sysnColor))
                    .setTextColor(R.id.tv_ad,mContext.getResources().getColor(R.color.sysnColor));
        }

    }


    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }

}
