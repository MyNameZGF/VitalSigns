package com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose.child.BloodGlucoseDetailsFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.blood_glucose.child.BloodGlucoseStatisticalFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 血糖 main fragment
 */
public class BloodGlucoseFragment extends BaseFragment implements View.OnClickListener{
    private TextView tv_title;
    private Button view;
    private Button view2;
    private static int defualtFragmentPosition = 0;
    private TextView tv_startTime;
    private TextView tv_endTime;
    private Button tv_search;
    private BaseFragment[] mFragments = new BaseFragment[2];

    public static BaseFragment newInstance() {
        return new BloodGlucoseFragment();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseFragment tmep1 = findChildFragment(BloodGlucoseDetailsFragment.class);
        if (tmep1 == null) {
            mFragments[0] = getFragment(0);
            mFragments[1] = getFragment(1);
            loadMultipleRootFragment(R.id.fl_content, 0,mFragments);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = tmep1;
            mFragments[1] = findChildFragment(BloodGlucoseStatisticalFragment.class);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_temp_bloodsugar;
    }

    @Override
    protected void initView() {
        tv_title = $(R.id.tv_title);
        tv_startTime = $(R.id.tv_startTime);
        tv_endTime = $(R.id.tv_endTime);
        tv_search = $(R.id.tv_search);
        view = $(R.id.view);
        view2 = $(R.id.view2);
        view.setBackgroundColor(Color.parseColor("#FF1D75FF"));
        view.setTextColor(Color.parseColor("#ffffff"));
        view2.setBackgroundColor(Color.parseColor("#FAFAFA"));
        view2.setTextColor(Color.parseColor("#1D75FF"));
        view.setOnClickListener(this);
        view2.setOnClickListener(this);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_title.setText(getString(R.string.dayOf7BloodGlucose));
    }

    @Override
    protected void initData() {


    }

    /**
     * 根据position获取相应的Fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        BaseFragment fragment;
        switch (position) {
            case 0:
                fragment = findFragment(BloodGlucoseDetailsFragment.class);
                if (fragment == null) {
                    fragment = BloodGlucoseDetailsFragment.newInstance(0);
                }
                break;

            default:
                fragment = findFragment(BloodGlucoseStatisticalFragment.class);
                if (fragment == null) {
                    fragment = BloodGlucoseStatisticalFragment.newInstance();
                }
                break;
        }
        return fragment;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view://详细数据
                view.setBackgroundColor(Color.parseColor("#FF1D75FF"));
                view.setTextColor(Color.parseColor("#FFFFFF"));
                view2.setBackgroundColor(Color.parseColor("#FAFAFA"));
                view2.setTextColor(Color.parseColor("#1D75FF"));
                tv_title.setText(getString(R.string.dayOf7Temp));

                int prePosition = defualtFragmentPosition;
                if (defualtFragmentPosition == 1) {
                    defualtFragmentPosition--;
                }
                BaseFragment currentFragment = getFragment(defualtFragmentPosition);
                if (currentFragment != null) {
                    showHideFragment(mFragments[defualtFragmentPosition],mFragments[1]);
                }
                break;
            case R.id.view2://统计表
                view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                view.setTextColor(Color.parseColor("#1D75FF"));
                view2.setBackgroundColor(Color.parseColor("#FF1D75FF"));
                view2.setTextColor(Color.parseColor("#FFFFFF"));
                tv_title.setText(getString(R.string.dayOfthreeTemp));
                if (defualtFragmentPosition == 0) {
                    defualtFragmentPosition++;
                }
                System.out.println("体温表"+defualtFragmentPosition);
                BaseFragment currentFragment2 = getFragment(defualtFragmentPosition);
                if (currentFragment2 != null) {
                    showHideFragment(mFragments[defualtFragmentPosition],mFragments[0]);
                }
                break;
            case R.id.tv_startTime:
                //TODO 选择开始时间
                showDatePickDlg(R.id.tv_startTime);
                break;
            case R.id.tv_endTime:
                //TODO 选择开始时间
                showDatePickDlg(R.id.tv_endTime);
                break;
            case R.id.tv_search://搜索按钮

                Toast.makeText(getActivity(),"查询从"+tv_startTime.getText()+"日~"+tv_endTime.getText()+"日的数据",Toast.LENGTH_SHORT).show();;
                break;
        }
    }

    /**
     * 日期选择器显示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickDlg (int getID) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String time = year + "-" + month + "-" + dayOfMonth;
                if(!passOfDate(time)){
                    Toast.makeText(getActivity(),"当前选择的时间超过六个月！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(getID== R.id.tv_startTime){
                    BloodGlucoseFragment.this.tv_startTime.setText(year + "-" + month + "-" + dayOfMonth);
                }else{
                    BloodGlucoseFragment.this.tv_endTime.setText(year + "-" + month + "-" + dayOfMonth);
                }

            }


        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }

    /**
     * 判断选择的日期是否超过六个月
     * @param date
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean passOfDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -6);
        String time = sdf.format(cal.getTime());
        try {
            if(sdf.parse(time).after(sdf.parse(date))){
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }
}


