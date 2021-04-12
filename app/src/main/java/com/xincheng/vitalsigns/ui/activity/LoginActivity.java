package com.xincheng.vitalsigns.ui.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.event.LoginChangEvent;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.login.LoginFragment;
import com.xincheng.vitalsigns.ui.fragment.login.NewPasswordFragment;
import com.xincheng.vitalsigns.ui.fragment.login.PasswordFragment;
import com.xincheng.vitalsigns.ui.fragment.login.RegisterFragment;
import com.xincheng.vitalsigns.utlis.DisplayUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

public class LoginActivity extends BaseActivity {

    //存储Fragment
    BaseFragment[] baseFragments;

    @BindView(R.id.ll_login_context_parent)
    LinearLayout linearLayout;
    private LinearLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
//        layoutParams = new LinearLayout.LayoutParams(600,560);
//        linearLayout.setLayoutParams(layoutParams);
        //默认添加所有Fragment到容器
        baseFragments = new BaseFragment[4];
        baseFragments[0] = getFragment(0);
        baseFragments[1] = getFragment(1);
        baseFragments[2] = getFragment(2);
        baseFragments[3] = getFragment(3);
        //默认显示第一个Fragment
        loadMultipleRootFragment(R.id.ll_login_context, 0, baseFragments);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    //事件接收
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receiveEvent(LoginChangEvent loginChangEvent) {
        Log.d("receiveEvent:", "a: loginChangEvent" + loginChangEvent.type);
        settingHeight(loginChangEvent.type);
        //通过事件接受显示不同的Fragment
        BaseFragment currentFragment = baseFragments[loginChangEvent.type];
        if (currentFragment != null) {
            showHideFragment(currentFragment);
        }
    }

    public void settingHeight(int position) {
        if (position == 0 || position == 1 || position == 3) {
            layoutParams = new LinearLayout.LayoutParams(DisplayUtils.dp2px(this,380), DisplayUtils.dp2px(this,360));
            linearLayout.setLayoutParams(layoutParams);
        } else if (position == 2) {
            layoutParams = new LinearLayout.LayoutParams(DisplayUtils.dp2px(this,380), DisplayUtils.dp2px(this,430));
            linearLayout.setLayoutParams(layoutParams);
        }
    }

    private int getPixelsFromDp(int i){
        DisplayMetrics metrics =new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return(i * metrics.densityDpi) / DisplayMetrics.DENSITY_DEFAULT;
    }

    public BaseFragment getFragment(int position) {
        BaseFragment fragment;
        switch (position) {
            case 0:
                fragment = findFragment(LoginFragment.class);//登陆模块
                if (fragment == null) {
                    fragment = LoginFragment.newInstance();
                }
                break;
            case 1:
                fragment = findFragment(RegisterFragment.class);//注册模块
                if (fragment == null) {
                    fragment = RegisterFragment.newInstance();
                }
                break;
            case 2:
                fragment = findFragment(PasswordFragment.class);//找回密码模块
                if (fragment == null) {
                    fragment = PasswordFragment.newInstance();
                }
                break;
            case 3:
                fragment = findFragment(NewPasswordFragment.class);//找回密码模块
                if (fragment == null) {
                    fragment = NewPasswordFragment.newInstance();
                }
                break;
            default:
                fragment = findFragment(LoginFragment.class);
                if (fragment == null) {
                    fragment = LoginFragment.newInstance();
                }
                break;
        }
        return fragment;
    }
}