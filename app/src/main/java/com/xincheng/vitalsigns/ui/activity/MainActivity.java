package com.xincheng.vitalsigns.ui.activity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.MyService;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.BleConnectAdapter;
import com.xincheng.vitalsigns.bean.ConnectBean;
import com.xincheng.vitalsigns.event.ConnectEvent;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;


/**
 *  1.动态申请定位权限
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final int REQUEST_ENABLE_BT = 0x00;
    private static final int REQUEST_BLE_SCAN = 0x01;
    private Button bt_permission;
    private Button bt_ble_list;
    private Button bt_measure;
    private Button bt_setting;
    private Button bt_query;
    private Button bt_patientInfo;
    private boolean isPermission;
    private Set<String> set;
    private Handler handler;
    private Runnable runnable;
    private RecyclerView rv;
    private BleConnectAdapter adapter;
    private List<ConnectBean> datas;

    private ExecutorService exec;


    public static MyService myService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//连接成功
            myService = ((MyService.MyBinder) service).getService();
            autoConnect();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//连接断开
            recycle();
        }
    };

    /**
     * 自动连接设备
     */
    private void autoConnect(){
        set = SP.getSetData(Constant.SET_KEY,String.class);
        if(null == set){
            return;
        }
        Log.e("Main","macSize:"+set.size());
        //开启线程连接设备
        if(null == handler){
            handler = new Handler();
        }
        if(null == runnable){
            runnable = () -> {
                for(String mac : set){
                    myService.connect(mac);
                }
            };
        }

        handler.postDelayed(runnable,1000L);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_permission = findViewById(R.id.bt_permission);
        bt_ble_list = findViewById(R.id.bt_ble_list);
        bt_measure = findViewById(R.id.bt_measure);
        bt_setting = findViewById(R.id.bt_setting);
        bt_query = findViewById(R.id.bt_query);
        bt_patientInfo = findViewById(R.id.bt_patientInfo);


        bt_permission.setOnClickListener(this);
        bt_ble_list.setOnClickListener(this);
        bt_measure.setOnClickListener(this);
        bt_setting.setOnClickListener(this);
        bt_query.setOnClickListener(this);
        bt_patientInfo.setOnClickListener(this);
        bt_ble_list.setEnabled(false);


       /* rv = $(R.id.rv);
        initAdapter();
        EventBus.getDefault().register(this);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        exec = Executors.newCachedThreadPool(); */

    }

    private void initAdapter() {
        if(adapter == null){
            datas = new ArrayList<>();
            set = SP.getSetData(Constant.SET_KEY,String.class);
            if(null != set){
                for(String mac : set){
                    ConnectBean bean = new ConnectBean();
                    bean.setMac(mac);
                    datas.add(bean);
                }
            }
            rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            adapter = new BleConnectAdapter(R.layout.item_connect,datas);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            rv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // bleEnable();
    }

    /**
     * 启用蓝牙
     */
    private void bleEnable(){
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            ToastUtils.show("设备不支持低功耗蓝牙功能");
            finish();
        }
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        if (!bluetoothAdapter.isEnabled()) {//跳转到蓝牙设置页面，开启蓝牙
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            return;
        }
        bt_ble_list.setEnabled(true);
        if(isPermission){
            return;
        }
        getLoactionPermission();
    }

    /**
     * 申请定位权限
     */
    private void getLoactionPermission(){
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
                .permission(Permission.ACCESS_FINE_LOCATION)
                // 申请多个权限
                //.permission(Permission.Group.CALENDAR)
                .request(new OnPermissionCallback() {
                    @Override
                    public void onGranted(List<String> permissions, boolean all) {
                        if (all) {
                            ToastUtils.show("获取定位权限成功");
                            isPermission = true;
                        } else {
                            ToastUtils.show("获取权限失败");
                        }
                    }
                    @Override
                    public void onDenied(List<String> permissions, boolean never) {
                        if (never) {
                            ToastUtils.show("被永久拒绝授权，请手动授予定位权限");
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(MainActivity.this, permissions);
                        } else {
                            ToastUtils.show("获取录定位权限失败");
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_ble_list:
                //startActivityForResult(new Intent(this, BleScanActivity.class),REQUEST_BLE_SCAN);
                startActivity(new Intent(this, BleScanActivity.class));
                break;

            case R.id.bt_permission:
                bleEnable();
                break;

            case R.id.bt_measure:
                startActivity(new Intent(this, MeasureActivity.class));
                break;

            case R.id.bt_setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;

            case R.id.bt_query:
                startActivity(new Intent(this, QueryRoomActivity.class));
                break;

            case R.id.bt_patientInfo:
                startActivity(new Intent(this, PatientInfoActivity.class));
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*EventBus.getDefault().unregister(this);
        unbindService(serviceConnection);*/
        recycle();
    }

    /**
     * 回收资源
     */
    private void recycle(){
        if(myService != null){
            myService = null;
        }
        if(runnable != null && handler != null){
            handler.removeCallbacks(runnable);
            handler = null;
            runnable = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            bleEnable();
        }
    }



    /**
     * 蓝牙连接
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ConnectEvent event) {
        //TODO 这里也需要用个多线程来接收消息，或者队列
            //TODO 这里有必要把数据存储到数据库，因为要区分是哪个设备断开了连接  ,或者用SP替代
            switch (event.type){
                case 1:
                    //TODO 显示连接dailog
                    Log.e("Main","开始连接");
                    break;

                case 2:
                    Log.e("Main","开始失败");
                    //TODO 隐藏连接dailog
                    //TODO 提示连接失败
                    //TODO 判断本地是否存储有mac 地址，如果有则，延时2秒在重新连接
                    set = SP.getSetData(Constant.SET_KEY,String.class);
                    if(null == set){//第一次连接
                        return;
                    }
                    if(set.contains(event.device.getMac())){
                        delayConnect(event.device.getMac(),2000L);
                    }
                    break;

                case 3:
                    //TODO 隐藏连接dailog
                    //TODO 提示连接成功
                    //TODO 将mac地址保存在本地
                    set = SP.getSetData(Constant.SET_KEY,String.class);
                    if(null == set || (!set.contains(event.device.getMac()) && set.size() <5)){//如果为空，或者不包括这个mac并且保存的mac数量小于5
                        set = null == set ? new HashSet<>() : set;
                        Log.e("Main","连接的mac地址:"+event.device.getMac());
                        set.add(event.device.getMac());
                        SP.putSetData(Constant.SET_KEY,set);
                        ConnectBean bean = new ConnectBean();
                        bean.setMac(event.device.getMac());
                        bean.setConnect(true);
                        datas.add(bean);
                        adapter.notifyItemInserted(datas.size()-1);
                    }else{
                        updateItem(event.device.getMac(),true);
                    }
                    break;

                case 4:
                    Log.e("Main","断开连接");
                    set =  SP.getSetData(Constant.SET_KEY, String.class);
                    if(null != set && set.contains(event.device.getMac())){
                        //TODO 这里需要根据mac地址来对比是哪个设备断开了，然后在更新ui
                        //TODO 提示连接断开
                    }
                    updateItem(event.device.getMac(),false);
                    if(set.contains(event.device.getMac())){
                        delayConnect(event.device.getMac(),2000L);
                    }
                    break;

                default:
                    break;
            }

    }

    /**
     * 更新item
     * @param mac
     * @param isConnect
     */
    private void updateItem(String mac,boolean isConnect){
        int i = 0;
        for(ConnectBean bean : datas){
            if(bean.getMac().equals(mac)){
                if(isConnect){
                    Log.e("mac","重新连接上mac:"+mac);
                }else{
                    Log.e("mac","已经断开连接mac:"+mac);
                }
                bean.setConnect(isConnect);
                adapter.notifyItemChanged(i);
            }
            i++;
        }
    }

    /**
     * 延时连接
     * @param mac
     */
    private void delayConnect(String mac,Long time){
        if(null == handler){
            handler = new Handler();
        }
        handler.postDelayed(()->{
            myService.connect(mac);
        },time);
    }

}