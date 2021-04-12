package com.xincheng.vitalsigns;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.clj.fastble.BleManager;
import com.hjq.language.MultiLanguages;
import com.hjq.language.OnLanguageListener;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.utlis.SP;

import java.util.Locale;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);//初始化toast库
        BleManager.getInstance().init(this);//初始化fastBle库
        SP.getInstance(this,"app_sp");// 初始化多语种框架（自动适配第三方库中 Activity 语种）
        MultiLanguages.init(this);
        // 设置语种变化监听器
        MultiLanguages.setOnLanguageListener(new OnLanguageListener() {
            @Override
            public void onAppLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到应用切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale);
            }
            @Override
            public void onSystemLocaleChange(Locale oldLocale, Locale newLocale) {
                Log.d("MultiLanguages", "监听到系统切换了语种，旧语种：" + oldLocale + "，新语种：" + newLocale);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }
}
