package com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose.child;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.TempBloodSugarAdapter;
import com.xincheng.vitalsigns.bean.TempDetailsBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 *
 * 血糖详情fragment
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
public class BloodGlucoseDetailsFragment extends BaseFragment {
    private static final String FRAGMENT_FLAG = "flag";
    private static final String PATIENT_INFO = "patientInfo";
    private RecyclerView rv_tempDetails;
    private List<TempDetailsBean> datas;
    private TempBloodSugarAdapter adapter;
    private static int fragmentFlag;

     public static BaseFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt(FRAGMENT_FLAG, type);
        BloodGlucoseDetailsFragment fragment = new BloodGlucoseDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static BaseFragment newInstance() {
        return new BloodGlucoseDetailsFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initData() {
        datas = getDatas();
        if(adapter == null){
            adapter = new TempBloodSugarAdapter(R.layout.item_temp_details,datas,fragmentFlag);
            GridLayoutManager manager = new GridLayoutManager(getActivity(), 5);
            rv_tempDetails.setLayoutManager(manager);
            rv_tempDetails.addItemDecoration(new GridSpacingItemDecoration(5, 2, true)); //这里的间距在布局里面做了处理了。
            rv_tempDetails.setAdapter(adapter);
        }
    }

    /**
     * 获取数据
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private List<TempDetailsBean> getDatas() {
        List<TempDetailsBean> data = new ArrayList<>();
        TempDetailsBean bean;
        Calendar calendar = Calendar.getInstance();
        //获取系统的日期
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH)+1;
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String date_day = year+"."+month+"."+day;
        //获取系统时间
        //小时
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //分钟
        int minute = calendar.get(Calendar.MINUTE);
        //秒
        int second = calendar.get(Calendar.SECOND);
        String date_time= null;
        if(hour<10){
             date_time = "0"+hour+":"+minute+":"+second;
        }else{
             date_time = hour+":"+minute+":"+second;
        }
        for (int i = 0;i<40;i++){
            if(i>=5&&i<10){
                bean = new TempDetailsBean(date_day, 0,null,null,-1,i,fragmentFlag);
            }else {
                bean = new TempDetailsBean(date_time, 6.0f+0.5f, "早餐", "王晓敏", i % 3, i, fragmentFlag);

            }
            data.add(bean);
        }

        return data;
    }
}
