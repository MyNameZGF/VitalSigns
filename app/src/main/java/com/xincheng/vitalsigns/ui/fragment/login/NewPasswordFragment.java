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

public class NewPasswordFragment extends BaseFragment {

    @BindView(R.id.et_new_password)
    EditText newPassword;

    public static BaseFragment newInstance() {
        return new NewPasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_new_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_reset_password_login)
    public void submit(View view){
        Log.d(TAG, "submit: ");
        EventBus.getDefault().post(new LoginChangEvent(0));
    }
}
