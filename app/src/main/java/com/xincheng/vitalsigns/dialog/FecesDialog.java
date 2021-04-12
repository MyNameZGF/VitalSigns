package com.xincheng.vitalsigns.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.PatientMeasure;

import java.lang.reflect.Method;

public class FecesDialog extends Dialog {

    private static final String TAG = "BreatheDialog";
    private Context context;
    private boolean mCancelable;
    private DialogCallBack dialogCallBack;
    private FecesNumKeyView customNumKeyView;
    private EditText enemaAfterCount, enemaBeforeCount, enemaCount, fecesCount;
    private CheckBox enemaRadioButton;
    private TextView fecesCancel, fecesConfirm;
    private TabLayout tabLayout;
    private LinearLayout noEnema, haveEnema,dialogFeces;
    private int index = 0;

    public FecesDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public interface DialogCallBack {
        void confirm(PatientMeasure bean);// 回调确定过后的数据
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

    public void closeKeyBoard(EditText... view) {
        for (int i = 0; i < view.length; i++) {
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                view[i].setInputType(InputType.TYPE_NULL);
            } else {
                this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                try {
                    Class<EditText> cls = EditText.class;
                    Method setSoftInputShownOnFocus;
                    setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                    setSoftInputShownOnFocus.setAccessible(true);
                    setSoftInputShownOnFocus.invoke(view[i], false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void initView() {
        setContentView(R.layout.dialog_feces);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) ((int) screenWidth / 2.3);
        attributes.height = (int) ((int) screenWidth / 3.8);
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);

        dialogFeces = findViewById(R.id.ll_dialog_feces);
        fecesCancel = findViewById(R.id.tv_feces_cancel);
        fecesConfirm = findViewById(R.id.tv_feces_confirm);
        enemaAfterCount = findViewById(R.id.et_enema_after);
        enemaBeforeCount = findViewById(R.id.et_enema_before);
        enemaCount = findViewById(R.id.et_enema_count);
        fecesCount = findViewById(R.id.et_feces_count);
        enemaRadioButton = findViewById(R.id.et_enema_radio_button);
        tabLayout = findViewById(R.id.tl_feces);
        noEnema = findViewById(R.id.ll_no_enema);
        haveEnema = findViewById(R.id.ll_have_enema);
        fecesCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        fecesConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientMeasure patientMeasureDataBean;
                if (index == 0) {
                    String count = fecesCount.getText().toString().trim();
                    if (count.length() == 0) {
                        ToastUtils.show("信息不完整");
                    } else {
                        patientMeasureDataBean = new PatientMeasure();
                        patientMeasureDataBean.setShit_type(1); //无灌肠
                        patientMeasureDataBean.setShit_count(Integer.parseInt(count));//无灌肠大便次数
                        dialogCallBack.confirm(patientMeasureDataBean);
                        dismiss();
                    }
                } else {
                    patientMeasureDataBean = new PatientMeasure();
                    String count = enemaCount.getText().toString().trim();
                    String afterCount = enemaAfterCount.getText().toString().trim();
                    String beforeCount = enemaBeforeCount.getText().toString().trim();
                    if (count.length() == 0 || afterCount.length() == 0 || beforeCount.length() == 0) {
                        ToastUtils.show("信息不完整");
                    } else {
                        patientMeasureDataBean.setShit_type(2);
                        patientMeasureDataBean.setShit_clyster_after(Integer.parseInt(afterCount));
                        patientMeasureDataBean.setShit_clyster_before(Integer.parseInt(beforeCount));
                        patientMeasureDataBean.setClyster_count(Integer.parseInt(count));
                        dialogCallBack.confirm(patientMeasureDataBean);
                        dismiss();
                    }
                }
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("\t\t\t无灌肠\t\t\t"));
        tabLayout.addTab(tabLayout.newTab().setText("\t\t\t有灌肠\t\t\t"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                if (tab.getPosition() == 0) {
                    noEnema.setVisibility(View.VISIBLE);
                    haveEnema.setVisibility(View.GONE);
                    index = 0;
                } else {
                    noEnema.setVisibility(View.GONE);
                    haveEnema.setVisibility(View.VISIBLE);
                    index = 1;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void dismiss() {
        super.dismiss();
        enemaAfterCount.setText("");
        enemaBeforeCount.setText("");
        enemaCount.setText("");
        fecesCount.setText("");
        enemaRadioButton.setChecked(false);
        dialogFeces.requestFocus();
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
