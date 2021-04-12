package com.xincheng.vitalsigns.ui.fragment.patient_info.temp.child;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.TemperatureAdapter;
import com.xincheng.vitalsigns.bean.temp.TemperatureDateBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.DataService;
import com.xincheng.vitalsigns.utlis.ViewUtils;

import java.util.List;

/**
 * 温度统计表fragment
 */
public class TempStatisticalFragment extends BaseFragment {
    private static final String ARG_DATE = "date";
    private static final String ARG_POSITION = "position";
    private RecyclerView recycler;
    private TemperatureAdapter adapter;
    private String date;//日期，作用根据日期去获取数据
    private int position;//选中了哪个日期
    private List<TemperatureDateBean> datas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            date = bundle.getString(ARG_DATE);
            position = bundle.getInt(ARG_POSITION);
        }
    }

    public static TempStatisticalFragment newInstance(String date, int position) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        args.putInt(ARG_POSITION,position);
        TempStatisticalFragment fragment = new TempStatisticalFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_only_rv;
    }

    @Override
    protected void initView() { recycler = $(R.id.recycler); }

    @Override
    protected void initData() {
        datas = DataService.getTemperatureDatas(position);
        initAdapter();
    }

    private void initAdapter() {
        if(null == adapter){
            adapter = new TemperatureAdapter(datas);
            adapter.openLoadAnimation();
            recycler.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
        if(null == datas ||datas.size() <= 0){
            adapter.setEmptyView(ViewUtils.getEmptyView(getLayoutInflater(),recycler, R.mipmap.no_data,getString(R.string.status_no_date)));
        }
    }

}
