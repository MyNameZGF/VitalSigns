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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.PatientMeasure;

import java.lang.reflect.Method;

public class UrinateDialog extends Dialog {

    private static final String TAG = "BreatheDialog";
    private Context context;
    private boolean mCancelable;
    private DialogCallBack dialogCallBack;

    private UrinateNumKeyView urinateNumKeyView;
    private EditText urinateCount, urinateQuantity, urinateQuantity2;
    private TextView urinateCancel, urinateConfirm;
    private TabLayout tabLayout;
    private LinearLayout noCatheter, haveCatheter,dialogUrinate;
    private int index = 0;

    public UrinateDialog(@NonNull Context context) {
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
//        closeKeyBoard(new EditText[]{urinateCount, urinateQuantity, urinateQuantity2});
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
        setContentView(R.layout.dialog_urinate);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) ((int) screenWidth / 2.5);
        attributes.height = (int) ((int) screenWidth / 4.5);
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);

        urinateCancel = findViewById(R.id.tv_urinate_cancel);
        urinateConfirm = findViewById(R.id.tv_urinate_confirm);
        urinateCount = findViewById(R.id.et_urinate_count);
        urinateQuantity = findViewById(R.id.et_urinate_quantity);
        urinateQuantity2 = findViewById(R.id.et_urinate_quantity2);
        tabLayout = findViewById(R.id.tl_urinate);
        noCatheter = findViewById(R.id.ll_no_catheter);
        haveCatheter = findViewById(R.id.ll_have_catheter);
        dialogUrinate = findViewById(R.id.ll_dialog_urinate);

        urinateCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        urinateConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PatientMeasure bean = new PatientMeasure();
                if (index == 0) {
                    String count = urinateCount.getText().toString();
                    String quantity = urinateQuantity.getText().toString();
                    if (count.length() == 0 || quantity.length() == 0) {
                        ToastUtils.show("信息不完整");
                    } else {
                        bean.setPee_type(1);//无导管
                        bean.setPee_no_conduit(Integer.parseInt(count));
                        bean.setPee_volume_no_conduit(Float.parseFloat(quantity));
                        dialogCallBack.confirm(bean);
                        dismiss();
                    }
                } else {
                    String quantity = urinateQuantity2.getText().toString();
                    if (quantity.length() == 0) {
                        ToastUtils.show("信息不完整");
                    } else {
                        bean.setPee_type(2);//有导管
                        bean.setPee_volume_conduit(Float.parseFloat(quantity));
                        dialogCallBack.confirm(bean);
                        dismiss();
                    }
                }
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("\t\t\t无导管\t\t\t"));
        tabLayout.addTab(tabLayout.newTab().setText("\t\t\t有导管\t\t\t"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "onTabSelected: " + tab.getPosition());
                if (tab.getPosition() == 0) {
                    noCatheter.setVisibility(View.VISIBLE);
                    haveCatheter.setVisibility(View.GONE);
                    index = 0;
                } else {
                    noCatheter.setVisibility(View.GONE);
                    haveCatheter.setVisibility(View.VISIBLE);
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
        urinateCount.setText("");
        urinateQuantity.setText("");
        urinateQuantity2.setText("");
        dialogUrinate.requestFocus();
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
