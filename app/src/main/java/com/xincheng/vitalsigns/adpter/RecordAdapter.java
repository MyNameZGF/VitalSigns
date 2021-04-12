package com.xincheng.vitalsigns.adpter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.CheckBox;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.RecordBean;

import java.util.List;

public class RecordAdapter extends BaseQuickAdapter<RecordBean, BaseViewHolder> {
    private boolean showCheckBox;
    public RecordAdapter(int layoutResId, @Nullable List<RecordBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, RecordBean item) {
        helper.itemView.setBackgroundColor(item.getOrder() % 2 == 1 ? mContext.getResources().getColor(R.color.item_bg) : mContext.getResources().getColor(R.color.titleColor80));
        CheckBox box = helper.getView(R.id.cv);
        box.setChecked(item.getCheck());
        helper.setGone(R.id.cv,showCheckBox ? true : false)
                .setText(R.id.tv_date,item.getBean().getMeasureTime())
                .setText(R.id.tv_bedNo,item.getBean().getBedNo())
                .setText(R.id.tv_ad,item.getBean().getAd())
                .setText(R.id.tv_name,item.getBean().getName())
                .setText(R.id.tv_recoudCount,"共"+item.getHistoryCount()+"条")
                .setText(R.id.tv_status,item.getScnyType() == 0 ? mContext.getResources().getString(R.string.sy) : (item.getScnyType() == 1 ? mContext.getResources().getString(R.string.dis_sy) : mContext.getResources().getString(R.string.sy_fial)))
                .setTextColor(R.id.tv_status,item.getScnyType() == 2 ? mContext.getResources().getColor(R.color.colorRed) : mContext.getResources().getColor(R.color.titleColor80));
    }

    public void setShowCheckBox(boolean showCheckBox) {
        this.showCheckBox = showCheckBox;
    }
}
