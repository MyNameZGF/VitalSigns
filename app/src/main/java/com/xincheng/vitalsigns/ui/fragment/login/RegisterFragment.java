package com.xincheng.vitalsigns.ui.fragment.login;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.event.LoginChangEvent;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.et_register_account_number)
    EditText username;
    @BindView(R.id.et_register_password)
    EditText password;

    public static BaseFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_register_submit)
    public void submit(View view){
        Log.d(TAG, "submit: ");
    }

    @OnClick(R.id.tv_register_do_login)
    public void doLogin(View view){
        Log.d(TAG, "register: ");
        EventBus.getDefault().post(new LoginChangEvent(0));
    }

    @OnClick(R.id.tv_register_forget_password)
    public void forgetPassword(View view){
        Log.d(TAG, "forgetPassword: ");
        EventBus.getDefault().post(new LoginChangEvent(2));
    }
}
