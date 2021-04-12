package com.xincheng.vitalsigns.ui.fragment.patient_info.temp.child;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.TempDetailsAdapter;
import com.xincheng.vitalsigns.bean.TempDetailsBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.DateUtils;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * 温度详情fragment
 * fragmentFlag
 * 0 ->温度
 * 1 ->血氧
 * 2 ->血压
 * 3 ->血糖
 * 4 ->脉率
 * 5 ->呼吸
 * 6 ->大便
 *
 * TODO 数据传递方式弄错了，数据从父fragment传递过来；TempDetailsBean应该只存储三个值，content，position,mode:哪个fragment
 */
public class TempDetailsFragment extends BaseFragment {
    private static final String FRAGMENT_FLAG = "flag";
    private static final String PATIENT_INFO = "patientInfo";
    private RecyclerView rv_tempDetails;
    private List<TempDetailsBean> datas;
    private TempDetailsAdapter adapter;
    private static int fragmentFlag;

    public static BaseFragment newInstance(int type, List<TempDetailsBean> datas) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_FLAG, type);
        args.putSerializable(PATIENT_INFO, (Serializable) datas);
        TempDetailsFragment fragment = new TempDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

     public static BaseFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_FLAG, type);
        TempDetailsFragment fragment = new TempDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseFragment newInstance() {
        return new TempDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            fragmentFlag =bundle.getInt(FRAGMENT_FLAG);
            //datas = (List<TempDetailsBean>) bundle.getSerializable(PATIENT_INFO);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_temp_details;
    }

    @Override
    protected void initView() {
        rv_tempDetails = $(R.id.rv_tempDetails);
    }

    @Override
    protected void initData() {
        datas = getDatas();
        if(adapter == null){
            adapter = new TempDetailsAdapter(R.layout.item_temp_details,datas,fragmentFlag);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
            rv_tempDetails.setLayoutManager(manager);
            rv_tempDetails.addItemDecoration(new GridSpacingItemDecoration(4, 2, true)); //这里的间距在布局里面做了处理了。
            rv_tempDetails.setAdapter(adapter);
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
            bean = new TempDetailsBean(DateUtils.long2String(System.currentTimeMillis()),36.0f+0.1f*(i+1),"李宏"+i,i % 3,i,fragmentFlag);
            data.add(bean);
        }
        return data;
    }
}
