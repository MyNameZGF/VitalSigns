package com.xincheng.vitalsigns.adpter;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.SettingMenuBean;

import java.util.List;

public class SettingMenuAdapter extends BaseQuickAdapter<SettingMenuBean, BaseViewHolder> {
    public SettingMenuAdapter(int layoutResId, @Nullable List<SettingMenuBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, SettingMenuBean item) {
        helper.setText(R.id.tv_menu_name,item.getMenuName())
                .setImageResource(R.id.iv,item.getIcon())
                .setBackgroundColor(R.id.item,item.isSelect() ? mContext.getResources().getColor(R.color.item_bg) : mContext.getResources().getColor(R.color.colorWhite));
    }
}
