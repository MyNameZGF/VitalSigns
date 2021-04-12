package com.xincheng.vitalsigns.adpter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.BloodTypeBean;

import java.util.List;

public class BloodSugarAdapter extends BaseQuickAdapter<BloodTypeBean, BaseViewHolder> {

    public BloodSugarAdapter(int layoutResId, @Nullable List<BloodTypeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BloodTypeBean item) {
        helper.setText(R.id.item_blood_sugar_type_name, item.getTypeName());
        helper.setGone(R.id.item_blood_sugar_type_image, item.getIsSelected() == 0 ? false : true);
        helper.getView(R.id.rl_item_blood_sugar).setBackgroundColor(item.getIsSelected() == 0 ? Color.parseColor("#FFFFFF") : Color.parseColor("#F2F4F7"));
    }
}
