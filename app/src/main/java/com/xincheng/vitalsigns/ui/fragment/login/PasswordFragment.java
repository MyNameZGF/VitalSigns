package com.xincheng.vitalsigns.ui.fragment.login;

import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;

import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.Dept;
import com.xincheng.vitalsigns.event.LoginChangEvent;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTouch;

public class PasswordFragment extends BaseFragment {

    @BindView(R.id.et_retrieve_account_number)
    EditText username;
    @BindView(R.id.et_retrieve_job_number)
    EditText jobNumber;
    @BindView(R.id.et_retrieve_dept)
    EditText dept;
    boolean isOpen = false;
    List<Dept> depts = new ArrayList<>();
    String[] list = {};

    public static BaseFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        depts.add(new Dept(1,"内科"));
        depts.add(new Dept(2,"外科"));
        depts.add(new Dept(3,"古科"));
        depts.add(new Dept(4,"精神科"));
        depts.add(new Dept(5,"妇科科"));
        depts.add(new Dept(1,"内科"));
        depts.add(new Dept(2,"外科"));
        depts.add(new Dept(3,"古科"));
        depts.add(new Dept(4,"精神科"));
        depts.add(new Dept(5,"妇科科"));
        list = new String[depts.size()];
        for (int i=0;i<depts.size();i++){
            list[i] = depts.get(i).getName();
        }
    }

    @OnClick(R.id.btn_retrieve_submit)
    public void submit(View view){
        EventBus.getDefault().post(new LoginChangEvent(3));
        Log.d(TAG, "submit: ");
    }

    @OnClick(R.id.tv_retrieve_retrun_login)
    public void doLogin(View view){
        Log.d(TAG, "register: ");
        EventBus.getDefault().post(new LoginChangEvent(0));
    }

    @OnClick(R.id.et_retrieve_dept)
    public void selectDept(){
        Log.d(TAG, "deptTouch: ");
        showListPoPulWindow();
    }


    private void showListPoPulWindow(){
        ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
        listPopupWindow.setHeight(300);
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dept.setText(list[i]);
                Log.d(TAG, "onItemClick: :"+depts.get(i).toString());
                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.dept_lsit_item, list));
        listPopupWindow.setAnchorView(dept);
        listPopupWindow.setModal(true);
        listPopupWindow.show();
    }

}
