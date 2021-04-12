package com.xincheng.vitalsigns.adpter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.BleBean;

import java.util.List;

public class BlelistAdapter extends BaseQuickAdapter<BleBean, BaseViewHolder> {
    public BlelistAdapter(int layoutResId, @Nullable List<BleBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, BleBean item) {
        helper.setText(R.id.tv_name,item.getDevice().getName())
                .setBackgroundColor(R.id.item,item.isConnect() ? Color.GREEN : Color.BLUE)
                .setText(R.id.tv_mac,item.getDevice().getMac())
                .setGone(R.id.bt_enter,item.isConnect() ? true : false)
                .setText(R.id.bt_connect,item.isConnect() ? "断开连接" : "连接")
                .addOnClickListener(R.id.bt_connect,R.id.bt_connect);
    }
}
