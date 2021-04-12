package com.xincheng.vitalsigns.ui.activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.hjq.language.MultiLanguages;
import com.hjq.toast.ToastUtils;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.ui.fragment.login.LoginFragment;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public abstract class BaseActivity extends SupportActivity {
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;
    private static BaseCircleDialog dialog;
    protected Context context;
    protected final String TAG = getClass().getSimpleName();

    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        context = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.bind(this).unbind();
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 默认竖向(和安卓5.0以上的动画相同)
        // 设置横向(和安卓4.x动画相同) return new DefaultHorizontalAnimator();
        // 设置自定义动画 return new FragmentAnimator(enter,exit,popEnter,popExit);
        return super.onCreateFragmentAnimator();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    private void showDefaultFragment() {
        // false:  不加入回退栈;  false: 不显示动画
        loadRootFragment(R.id.ll_login_context, new LoginFragment(), false, false);
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
                finish();
            } else {
                TOUCH_TIME = System.currentTimeMillis();
                toast(getString(R.string.press_again_exit));
            }
        }
    }
    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }

    /**
     * 获取布局 ID
     */
    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();
    public <V extends View> V $(@IdRes int id) {
        return findViewById(id);
    }
}
