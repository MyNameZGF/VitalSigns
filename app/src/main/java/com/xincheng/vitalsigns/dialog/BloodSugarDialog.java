package com.xincheng.vitalsigns.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.BloodSugarAdapter;
import com.xincheng.vitalsigns.bean.BloodTypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BloodSugarDialog extends Dialog {

    private static final String TAG = "BreatheDialog";
    private Context context;
    private boolean mCancelable;
    private DialogCallBack dialogCallBack;
    private RecyclerView recyclerView;
    private BloodSugarAdapter bloodSugarAdapter;
    private List<BloodTypeBean> timeSlotBeans;
    int previousPosition = 0;
    int currentPosition = 0;
    private TextView bloodCancel,bloodConfirm;

    public BloodSugarDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public interface DialogCallBack {
        void confirm(BloodTypeBean timeSlotBean);// 回调确定过后的数据
    }

    public void setDialogCallBack(DialogCallBack callBack) {
        dialogCallBack = callBack;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initView();
    }


    private void initView() {
        setContentView(R.layout.dialog_blood_sugar_type);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) ((int) screenWidth / 2.8);
        attributes.height = (int) ((int) screenWidth / 3);
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);
        bloodCancel = findViewById(R.id.tv_blood_sugar_cancel);
        bloodConfirm = findViewById(R.id.tv_blood_sugar_confirm);
        recyclerView = findViewById(R.id.rv_blood_sugar_list);

        bloodCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bloodConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallBack.confirm(timeSlotBeans.get(currentPosition));
                dismiss();
            }
        });


        if (bloodSugarAdapter == null) {
            getData();
            bloodSugarAdapter = new BloodSugarAdapter(R.layout.item_blood_sugar_type, timeSlotBeans);
            recyclerView.setAdapter(bloodSugarAdapter);
            bloodSugarAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Log.d(TAG, "onItemClick: " + position);
                    currentPosition = position;
                    if (previousPosition != position) {
                        timeSlotBeans.get(currentPosition).setIsSelected(1);
                        timeSlotBeans.get(previousPosition).setIsSelected(0);
                        bloodSugarAdapter.notifyDataSetChanged();
                        previousPosition = currentPosition;
                    } else {
                        timeSlotBeans.get(currentPosition).setIsSelected(timeSlotBeans.get(currentPosition).getIsSelected() == 0 ? 1 : 0);
                        bloodSugarAdapter.notifyDataSetChanged();
                    }
                }
            });

        }
    }

    private void getData() {
        List<String> bloodSugarType = new ArrayList<>(Arrays.asList(context.getResources().getStringArray(R.array.blood_sugar_type)));
        timeSlotBeans = new ArrayList<>();
        BloodTypeBean timeSlotBean;
        for (int i = 0; i < bloodSugarType.size(); i++) {
            timeSlotBean = new BloodTypeBean(i,i==0?1:0,bloodSugarType.get(i),i);
            timeSlotBeans.add(timeSlotBean);
        }
        Log.d(TAG, "getData: " + timeSlotBeans.size());
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 屏蔽返回键
            return mCancelable;
        }
        return super.onKeyDown(keyCode, event);
    }


}
