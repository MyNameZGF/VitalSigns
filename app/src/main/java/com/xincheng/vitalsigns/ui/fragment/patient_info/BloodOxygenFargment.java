package com.xincheng.vitalsigns.ui.fragment.patient_info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.TempDetailsAdapter;
import com.xincheng.vitalsigns.bean.TempDetailsBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.DateUtils;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * 血氧
 */
public class BloodOxygenFargment extends BaseFragment {
    private static final String FRAGMENT_FLAG = "flag";
    private RecyclerView rv;
    private TextView tv_title;
    private List<TempDetailsBean> datas;
    private TempDetailsAdapter adapter;
    private static int fragmentFlag;

    public static BaseFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_FLAG, type);
        BloodOxygenFargment fragment = new BloodOxygenFargment();
        fragment.setArguments(args);
        return fragment;
    }

      @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragmentFlag =bundle.getInt(FRAGMENT_FLAG);
        }
    }

    @Override
    protected int getLayoutId() { return R.layout.fragment_patient_normal; }

    @Override
    protected void initView() {
        rv = $(R.id.rv);
        tv_title = $(R.id.tv_title);
        tv_title.setText(getString(R.string.dayOf7BloodOxygen));
    }

    @Override
    protected void initData() {
        datas = getDatas();
        if(adapter == null){
            adapter = new TempDetailsAdapter(R.layout.item_temp_details,datas,fragmentFlag);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
            rv.setLayoutManager(manager);
            rv.addItemDecoration(new GridSpacingItemDecoration(4, 2, true)); //这里的间距在布局里面做了处理了。
            rv.setAdapter(adapter);
        }
    }

    /**
     * 获取数据
     * @return
     */
    private List<TempDetailsBean> getDatas() {
        List<TempDetailsBean> data = new ArrayList<>();
        TempDetailsBean bean;
        for(int i = 0;i<32;i++){
            bean = new TempDetailsBean(DateUtils.long2String(System.currentTimeMillis()),99-i,"李宏"+i,i % 3,i,fragmentFlag);
            data.add(bean);
        }
        return data;
    }
}
