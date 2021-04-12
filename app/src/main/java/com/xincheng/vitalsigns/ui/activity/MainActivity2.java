package com.xincheng.vitalsigns.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.xincheng.vitalsigns.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity2 extends BaseActivity {



    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @OnClick({R.id.tv_main_exit,R.id.iv_main_exit})
    public void exit(){
        startActivity(new Intent(MainActivity2.this,LoginActivity.class));
        finish();
    }


    @OnClick(R.id.ll_input_information)
    public void inputInformation(){
        startActivity(new Intent(MainActivity2.this,MeasureActivity.class));
    }

    @OnClick(R.id.ll_check_record)
    public void checkRecord(){
        startActivity(new Intent(this, QueryRoomActivity.class));
    }

    @OnClick(R.id.system_settings)
    public void systemSettings(){
        startActivity(new Intent(this, SettingActivity.class));
    }


}