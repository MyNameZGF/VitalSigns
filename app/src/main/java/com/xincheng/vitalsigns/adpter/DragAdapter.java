package com.xincheng.vitalsigns.adpter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.CustomBean;

import java.util.List;

public class DragAdapter extends BaseItemDraggableAdapter<CustomBean, BaseViewHolder> {
    public DragAdapter(int layoutResId, List<CustomBean> data) {
        super(layoutResId, data);
        Log.i(TAG, "DragAdapter: "+data.size());
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CustomBean item) {
        helper.setText(R.id.tv_content,item.getContent())
                .setImageResource(R.id.iv_content,item.getPic())
                .setGone(R.id.iv_delete,item.getType() == 1 ? true : false)
                .addOnClickListener(R.id.iv_delete);
    }
}
