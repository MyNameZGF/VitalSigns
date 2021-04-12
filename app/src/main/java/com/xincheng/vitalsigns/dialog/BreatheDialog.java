package com.xincheng.vitalsigns.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.lang.reflect.Method;

public class BreatheDialog extends Dialog {

    private static final String TAG = "BreatheDialog";
    private Context context;
    private boolean mCancelable;
    private DialogCallBack dialogCallBack;

    private BreatheNumKeyView customNumKeyView;
    private EditText breatheCount;
    private TextView breatheCancel, breatheConfirm;
    private LinearLayout dialogBreathe;

    public BreatheDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public interface DialogCallBack {
        void confirm(Integer breathe);// 回调确定过后的数据
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

    public void closeKeyBoard(EditText view) {
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            view.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setSoftInputShownOnFocus;
                setSoftInputShownOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                setSoftInputShownOnFocus.setAccessible(true);
                setSoftInputShownOnFocus.invoke(view, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private void initView() {
        setContentView(R.layout.dialog_breathe);
        // 设置窗口大小
        WindowManager windowManager = getWindow().getWindowManager();
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.width = (int) ((int) screenWidth / 2.6);
        attributes.height = (int) ((int) screenWidth / 6.5);
        getWindow().setAttributes(attributes);
        setCancelable(mCancelable);

        dialogBreathe = findViewById(R.id.dialog_breathe);
        breatheCancel = findViewById(R.id.tv_breathe_cancel);
        breatheConfirm = findViewById(R.id.tv_breathe_confirm);
        breatheCount = findViewById(R.id.et_breathe_count);
//        customNumKeyView = findViewById(R.id.ll_breathe_num_key);
//        customNumKeyView.setOnCallBack(new BreatheNumKeyView.CallBack() {
//            @Override
//            public void clickNum(String num) {
//                breatheCount.append(num);
//            }
//
//            @Override
//            public void deleteNum() {
//                String breathe = breatheCount.getText().toString();
//                if (breathe.length() == 0 || breathe == null)
//                    return;
//                breatheCount.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
//            }
//        });

        breatheCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        breatheConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String count = breatheCount.getText().toString().trim();
                if (count.length() == 0) {
                    ToastUtils.show("信息不完整");
                } else {
                    dialogCallBack.confirm(Integer.parseInt(breatheCount.getText().toString()));
                    dismiss();
                }
            }
        });
    }

    @Override
    public void dismiss() {
        super.dismiss();
        breatheCount.setText("");
        dialogBreathe.requestFocus();
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
