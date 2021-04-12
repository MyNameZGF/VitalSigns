package com.xincheng.vitalsigns.adpter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.LanguageBean;

import java.util.List;

public class LanguageAdapter extends BaseQuickAdapter<LanguageBean, BaseViewHolder> {
    public LanguageAdapter(int layoutResId, @Nullable List<LanguageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, LanguageBean item) {
        helper.setText(R.id.tv_language,item.getLanguage())
                .setGone(R.id.iv,item.isSelected() ? true : false)
                .setGone(R.id.v_line,item.isLastItem() ? false : true);
    }
}
