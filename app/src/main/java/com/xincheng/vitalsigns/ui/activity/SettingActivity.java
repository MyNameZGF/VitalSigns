package com.xincheng.vitalsigns.ui.activity;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.SettingMenuAdapter;
import com.xincheng.vitalsigns.bean.SettingMenuBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.BleFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.CustomFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.LanguageFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.ScreenFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.VoiceFragment;
import com.xincheng.vitalsigns.ui.fragment.setting.WifiFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 设置页面的主界面用 recyclerView + Faragment 实现。
 */
public class SettingActivity extends BaseActivity {
    private TitleBar titleBar;
    private RecyclerView rv_mune;
    private List<SettingMenuBean> datas;
    private SettingMenuAdapter adapter;
    private static int prePosition = 0;
    private static int defualtFragmentPosition;
    private boolean isPermission;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);
        rv_mune = $(R.id.rv_mune);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {
            }
        });
        showDefaultFragment();
    }

    /**
     * 显示默认的fragment
     */
    private void showDefaultFragment() {
        defualtFragmentPosition = (int) SP.getData(Constant.SETTING_DEFUALT_FRAGMENT_POSITION, 0);//获取默认显示的fragment
        // false:  不加入回退栈;  false: 不显示动画
        loadRootFragment(R.id.fl_content, getFragment(defualtFragmentPosition), false, false);
    }

    @Override
    protected void initData() {
        getSettingPermission();
    }

    private void initAdapter() {
        if (null == adapter) {
            getDatas();
            adapter = new SettingMenuAdapter(R.layout.item_setting_menu, datas);
            rv_mune.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter, view, position) -> {
                datas.get(prePosition).setSelect(false);
                adapter.notifyItemChanged(prePosition);
                datas.get(position).setSelect(true);
                adapter.notifyItemChanged(position);
                Log.d("TAG", "initAdapter: " + position);
                if (position == 0) {
                    BaseFragment currentFragment = getFragment(prePosition);
                    if (currentFragment != null) {
                        currentFragment.replaceFragment(getFragment(position), false);
                    }
                } else {
                    sysSetting(position);
                }
                prePosition = position;
            });
        }
    }

    /**
     * 申请系统设置权限
     */
    private void getSettingPermission() {
        if (isPermission) {
            return;
        }
        XXPermissions.with(this)
                // 申请安装包权限
                //.permission(Permission.REQUEST_INSTALL_PACKAGES)
                // 申请悬浮窗权限
                //.permission(Permission.SYSTEM_ALERT_WINDOW)
                // 申请通知栏权限
                //.permission(Permission.NOTIFICATION_SERVICE)
                // 申请系统设置权限
                //.permission(Permission.WRITE_SETTINGS)
                // 申请单个权限
                .permission(Permission.WRITE_SETTINGS)
                // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            ToastUtils.show("获取系统设置权限成功");
                            isPermission = true;
                            initAdapter();
                        } else {
                            ToastUtils.show("获取系统设置权限失败");
                        }
                    }

                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            ToastUtils.show("被永久拒绝授权，请手动授予系统设置权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(SettingActivity.this, permissions);
                        } else {
                            ToastUtils.show("获取录系统设置权限失败");
                        }
                    }
                });
    }


    private void sysSetting(int position) {
        Intent intent;
        switch (position) {
            case 1:
                intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
                break;
            case 2:
                //设置声音
                intent =  new Intent(Settings.ACTION_SOUND_SETTINGS);
                startActivity(intent);
                break;
            case 3:
                //无线连接
                intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                startActivity(intent);
                break;
            case 4:
                //系统语言
                intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case 5:
                //系统显示
                intent = new Intent(Settings.ACTION_DISPLAY_SETTINGS);
                startActivity(intent);
                break;
        }
    }


    /**
     * 根据position获取相应的Fragment
     *
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {
        BaseFragment fragment = findFragment(CustomFragment.class);//自定义模块
        if (fragment == null) {
            fragment = CustomFragment.newInstance();
        }
        return fragment;
    }

    /**
     * 获取数据
     */
    private void getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        ArrayList<String> menus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_menu)));
        Integer[] icons = {R.drawable.tongzhi, R.drawable.lanya, R.drawable.lingsheng, R.drawable.wuxianjuyuwang, R.drawable.zitidaxiao, R.drawable.tishi, R.drawable.tishi};
        for (int i = 0; i < menus.size(); i++) {
            SettingMenuBean bean = new SettingMenuBean(icons[i], menus.get(i));
            if (i == defualtFragmentPosition) {
                bean.setSelect(true);
            }
            datas.add(bean);
        }
    }


}
