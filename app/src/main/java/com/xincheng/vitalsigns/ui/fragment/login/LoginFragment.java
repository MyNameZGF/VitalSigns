package com.xincheng.vitalsigns.ui.fragment.login;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.event.LoginChangEvent;
import com.xincheng.vitalsigns.ui.activity.MainActivity2;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.CustomFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

public  class LoginFragment extends BaseFragment {

    @BindView(R.id.et_account_number)
    EditText username;
    @BindView(R.id.et_login_password)
    EditText password;

    public static BaseFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initView() {}

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_login_submit)
    public void submit(View view){
        Log.d(TAG, "submit: ");
        startActivity(new Intent(getActivity(), MainActivity2.class));
    }

    @OnClick(R.id.tv_login_register_btn)
    public void register(View view){
        Log.d(TAG, "register: ");
        EventBus.getDefault().post(new LoginChangEvent(1));
    }

    @OnClick(R.id.tv_login_forget_password_btn)
    public void forgetPassword(View view){
        Log.d(TAG, "forgetPassword: ");
        EventBus.getDefault().post(new LoginChangEvent(2));
    }

}
