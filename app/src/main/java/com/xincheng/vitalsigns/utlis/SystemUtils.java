package com.xincheng.vitalsigns.utlis;

import android.content.Context;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;

import java.lang.reflect.Method;

public class SystemUtils {

    private Context context;

    /**
     *  
     *  禁止Edittext弹出软件盘，光标依然正常显示。 
     */
    public static void disableShowSoftInput(EditText... editTest) {
        for (int i = 0; i < editTest.length; i++) {
            if (android.os.Build.VERSION.SDK_INT <= 10) {
                editTest[i].setInputType(InputType.TYPE_NULL);
            } else {
                Class<EditText> cls = EditText.class;
                Method method;
                try {
                    method = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(editTest[i], false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    method = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
                    method.setAccessible(true);
                    method.invoke(editTest[i], false);
                } catch (Exception e) {
                    Log.e("disableShowSoftInput", "disableShowSoftInput: " + e.getMessage());
                }
            }
        }
    }

    public void closeKeyBoard(EditText view) {
        if (Build.VERSION.SDK_INT <= 10) {
            view.setInputType(InputType.TYPE_NULL);
        } else {
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
}
