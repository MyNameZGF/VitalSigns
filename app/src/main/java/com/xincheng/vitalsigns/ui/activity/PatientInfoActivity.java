package com.xincheng.vitalsigns.ui.activity;

import android.util.Log;
import android.view.View;

import com.donkingliang.labels.LabelsView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose.BloodGlucoseFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.BloodOxygenFargment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.BloodPressureFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.BreatheFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.PulseRateFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.ExcrementFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.temp.TempFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;

import java.util.ArrayList;

/**
 * 患者数据详情页面
 * TODO 这里有7个Fragment 体温、血氧、血压、血糖、脉率、呼吸、大便
 * TODO 体温和血糖需要自定义控件
 * TODO 需要绘制表格
 */
public class PatientInfoActivity extends BaseActivity {
    private TitleBar titleBar;
    private LabelsView labels;
    private static int prePosition = 0;
    private static int defualtFragmentPosition;
    private ArrayList<String> menus;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_info;
    }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);
        labels = $(R.id.labels);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) { finish(); }
            @Override
            public void onTitleClick(View v) { }
            @Override
            public void onRightClick(View v) { }
        });

        showDefaultFragment();
    }

    @Override
    protected void initData() {
        menus = new ArrayList<>();
        menus.add(getString(R.string.temp));
        menus.add(getString(R.string.bloodOxygen));
        menus.add(getString(R.string.bloodPressure));
        menus.add(getString(R.string.bloodGlucose));
        menus.add(getString(R.string.pulseRate));
        menus.add(getString(R.string.breathes));
        menus.add(getString(R.string.excrements));
        labels.setLabels(menus);
        labels.setOnLabelClickListener((label, data, position) -> {
            if(prePosition != position){
                labels.setSelects(position);
                BaseFragment currentFragment = getFragment(prePosition);
                if (currentFragment != null) {
                    Log.e("bug","possition:"+position);
                    currentFragment.replaceFragment(getFragment(position), false);//TODO 这里有空指针报错
                }
                prePosition = position;
            }
        });
        labels.setSelects(defualtFragmentPosition);
    }

    /**
     * 显示默认的fragment
     */
    private void showDefaultFragment(){
        defualtFragmentPosition = (int) SP.getData(Constant.PATIENT_INFO__DEFUALT_FRAGMENT_POSITION,0);//获取默认显示的fragment
        // false:  不加入回退栈;  false: 不显示动画
        loadRootFragment(R.id.fl_content, getFragment(defualtFragmentPosition), false, false);
    }


    /**
     * 根据position获取相应的Fragment
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position){
        BaseFragment fragment;
        switch (position){
            case 0:
                fragment = findFragment(TempFragment.class);
                if(fragment == null){
                    fragment = TempFragment.newInstance();
                }
                break;

            case 1:
                fragment = findFragment(BloodOxygenFargment.class);
                if(fragment == null){
                    fragment = BloodOxygenFargment.newInstance(position);
                }
                break;

            case 2:
                fragment = findFragment(BloodPressureFragment.class);
                if(fragment == null){
                    fragment = BloodPressureFragment.newInstance(position);
                }
                break;

            case 3:
                fragment = findFragment(BloodGlucoseFragment.class);
                if(fragment == null){
                    fragment = BloodGlucoseFragment.newInstance();
                }
                break;

            case 4:
                fragment = findFragment(PulseRateFragment.class);
                if(fragment == null){
                    fragment = PulseRateFragment.newInstance(position);
                }
                break;

            case 5:
                fragment = findFragment(BreatheFragment.class);
                if(fragment == null){
                    fragment = BreatheFragment.newInstance(position);
                }
                break;

            default:
                fragment = findFragment(ExcrementFragment.class);
                if(fragment == null){
                    fragment = ExcrementFragment.newInstance(position);
                }
                break;
        }
        Log.e("bug","fragment:"+fragment);
        return fragment;
    }
}
