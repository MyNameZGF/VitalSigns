package com.xincheng.vitalsigns.adpter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.PatientBean;
import com.xincheng.vitalsigns.bean.PatientsBean;
import com.xincheng.vitalsigns.utlis.DateUtils;

import java.util.List;

public class PatientAdapter extends BaseQuickAdapter<PatientsBean, BaseViewHolder> {
    private static final String STR = "/";
    public PatientAdapter(int layoutResId, @Nullable List<PatientsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, PatientsBean item) {
        helper.setText(R.id.tv_bedNo,item.getBed_number())
                .setText(R.id.tv_isMeasured,item.getCheck_status()==1? mContext.getString(R.string.measured) : mContext.getString(R.string.unMeasure))
                .setTextColor(R.id.tv_isMeasured,item.getCheck_status()==1 ? mContext.getResources().getColor(R.color.colorPrimary) : mContext.getResources().getColor(R.color.colorRed))
                .setText(R.id.tv_name,item.getUser_name())
                .setText(R.id.tv_sex,item.getPatient_sex()==0?"女":"男")
                .setText(R.id.tv_age,item.getPatient_age().toString())
                .setText(R.id.tv_measureCount,item.getCheck_count()+STR+item.getNeed_check_count())
                .setText(R.id.tv_measureTime,item.getCheck_time()!=null? item.getCheck_time():"");
    }
}
