package com.xincheng.vitalsigns.adpter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.ConnectBean;

import java.util.List;

public class BleConnectAdapter extends BaseQuickAdapter<ConnectBean, BaseViewHolder> {
    public BleConnectAdapter(int layoutResId, @Nullable List<ConnectBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ConnectBean item) {
        helper.setText(R.id.tv_mac,item.getMac())
                .setText(R.id.tv_status,item.isConnect() ? "已连接" : "未连接")
                .setBackgroundColor(R.id.item,item.isConnect() ? Color.GREEN : Color.RED);
    }
}
