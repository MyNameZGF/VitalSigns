package com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose.child;
import android.widget.TextView;

import com.coorchice.library.SuperTextView;
import com.xincheng.linechart.SuitLines;
import com.xincheng.linechart.Unit;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 血糖统计fragment
 */
public class BloodGlucoseStatisticalFragment extends BaseFragment {
    private SuperTextView st_previous;
    private SuperTextView st_after;
    private SuitLines suitlines;
    private TextView tv_max_value;
    private TextView tv_max_time;
    private TextView tv_average_value;
    private TextView tv_average_time;
    private TextView tv_min_value;
    private TextView tv_min_time;
    private float textSize = 8;
    public static BaseFragment newInstance() {
        return new BloodGlucoseStatisticalFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bloodglucose_statistical;
    }

    @Override
    protected void initView() {
        st_previous = $(R.id.st_previous);
        st_after = $(R.id.st_after);
        suitlines = $(R.id.suitlines);
        tv_max_value = $(R.id.tv_max_value);
        tv_max_time = $(R.id.tv_max_time);
        tv_average_value = $(R.id.tv_average_value);
        tv_average_time = $(R.id.tv_average_time);
        tv_min_value = $(R.id.tv_min_value);
        tv_min_time = $(R.id.tv_min_time);
    }

    @Override
    protected void initData() {
        suitlines.setXySize(textSize = 14);
        suitlines.setLineSize(DisplayUtils.dp2px(getActivity(),3));
        SuitLines.LineBuilder builder = new SuitLines.LineBuilder();
        for (int j = 0; j < 2; j++) {//循环给两条线赋值
            List<Unit> datas = new ArrayList<>();
            for (int i = 0; i < 7; i++) {//显示七天的数据
                datas.add(new Unit(j == 0 ? i : i+2, (i) % 1 == 0 ? "10月"+i+"日" : ""));
            }
            builder.add(datas, j == 0 ? new int[]{getActivity().getResources().getColor(R.color.colorPrimary)} : new int[]{getActivity().getResources().getColor(R.color.colorGreen)});
        }
        builder.build(suitlines, true);
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        initData();
    }

    /**
     * 从数据库里获取数据
     */
    private void getDataForDb() {
    }

}
