package com.xincheng.vitalsigns.ui.fragment.patient_info.temp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.temp.child.TempDetailsFragment;
import com.xincheng.vitalsigns.ui.fragment.patient_info.temp.child.TempStatisticalFragment;
import com.xincheng.vitalsigns.utlis.DateUtils;

/**
 * 温度 main fragment
 */
public class TempFragment extends BaseFragment implements View.OnClickListener {
    private static String menu;
    private TextView tv_title;
    private Button view;
    private Button view2;
    private static int defualtFragmentPosition = 0;
    private BaseFragment[] mFragments = new BaseFragment[2];
    public static BaseFragment newInstance() {
        return new TempFragment();
    }

    /**
     * 显示默认fragment
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BaseFragment tmep1 = findChildFragment(TempDetailsFragment.class);
        if (tmep1 == null) {
            mFragments[0] = getFragment(0);
            mFragments[1] = getFragment(1);
            loadMultipleRootFragment(R.id.fl_content, 0,mFragments);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[0] = tmep1;
            mFragments[1] = findChildFragment(TempStatisticalFragment.class);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_temp_main;
    }

    @Override
    protected void initView() {
        tv_title = $(R.id.tv_title);
        view = $(R.id.view);
        view2 = $(R.id.view2);
        view.setBackgroundColor(Color.parseColor("#FF1D75FF"));
        view.setTextColor(Color.parseColor("#ffffff"));
        view2.setBackgroundColor(Color.parseColor("#FAFAFA"));
        view2.setTextColor(Color.parseColor("#1D75FF"));
        view.setOnClickListener(this);
        view2.setOnClickListener(this);
        tv_title.setText(getString(R.string.dayOf7Temp));
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
        BaseFragment fragment = null;
        switch (position) {
            case 0:
                fragment = findFragment(TempDetailsFragment.class);
                if (fragment == null) {
                    fragment = TempDetailsFragment.newInstance(0);
                }
                break;

            case 1:
                fragment = findFragment(TempStatisticalFragment.class);
                if (fragment == null) {
                    fragment = TempStatisticalFragment.newInstance(DateUtils.getCurrenDate(),0);
                }
                break;
        }
        return fragment;
    }

    /**
     * 点击替换fragment
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view:
                view.setBackgroundColor(Color.parseColor("#FF1D75FF"));
                view.setTextColor(Color.parseColor("#FFFFFF"));
                view2.setBackgroundColor(Color.parseColor("#FAFAFA"));
                view2.setTextColor(Color.parseColor("#1D75FF"));
                tv_title.setText(getString(R.string.dayOf7Temp));

                int prePosition = defualtFragmentPosition;
                if (defualtFragmentPosition == 1) {
                    defualtFragmentPosition--;
                }
                System.out.println("统计表"+defualtFragmentPosition);
                showHideFragment(mFragments[defualtFragmentPosition],mFragments[1]);
                break;
            case R.id.view2:
                view.setBackgroundColor(Color.parseColor("#FAFAFA"));
                view.setTextColor(Color.parseColor("#1D75FF"));
                view2.setBackgroundColor(Color.parseColor("#FF1D75FF"));
                view2.setTextColor(Color.parseColor("#FFFFFF"));
                tv_title.setText(getString(R.string.dayOfthreeTemp));
                if (defualtFragmentPosition == 0) {
                    defualtFragmentPosition++;
                }
                System.out.println("体温表"+defualtFragmentPosition);
                showHideFragment(mFragments[defualtFragmentPosition],mFragments[0]);
                break;
        }
    }
}
