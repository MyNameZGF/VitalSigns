package com.xincheng.vitalsigns.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.MyService;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.BlelistAdapter;
import com.xincheng.vitalsigns.bean.BleBean;
import com.xincheng.vitalsigns.event.ConnectEvent;
import com.xincheng.vitalsigns.event.ScanEvent;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 蓝牙扫描页面
 *      第一次的时候需要连接五台设备
 *      连接完毕之后，这个页面将不会显示出来
 *      如果设备管理页面删除了某台设备，则这个页面将显示
 *
 *      做一件事情如果需要60分钟，我会花费55分钟思考它，然后在花5分钟去寻找解决方案——爱因斯坦
 *
 *      循环通过mac同时连接多个设备
 *          一开始就循环
 *              然后判断
 *                  确定这个mac地址的连接状态是否已经连接
 *
 *           打开程序->判断本地是否保存有mac地址——>有->循环连接，有几个连接几个——>连接上了就取消循环->断开连接的时候在调用开启循环。
 *                                            ——>无——>不操作
 *
 *           根据设备断开连接的这个消息去发送连接这个操作
 *
 *
 *      TODO 保存连接的mac在本地
 *      TODO 根据mac地址来连接
 *      TODO 根据本地保存的mac地址的数量来决定要不要开启循环连接。
 *      TODO 循环根据mac地址来连接
 */
public class BleScanActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView rv;
    private Button bt_refresh;
    private BlelistAdapter adapter;
    private List<BleBean> datas;
    private Handler handler;
    private volatile Set<String> set;
    private Runnable delayConnectRunnable;
    private Runnable delayScanRunnable;

    private MyService myService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {//连接成功
            myService = ((MyService.MyBinder) service).getService();
            autoConnect(1000L);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//连接断开
            recycle();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble_scan);
        rv = findViewById(R.id.rv);
        bt_refresh = findViewById(R.id.bt_refresh);
        bt_refresh.setOnClickListener(this);
        initAdapter();
        EventBus.getDefault().register(this);
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_refresh:
                if(null != myService){
                    delayScan(2000L);
                }
                break;
        }
    }

    /**
     * 初始化适配器
     */
    private void initAdapter(){
        if(adapter == null){
            datas = new ArrayList<>();
            rv.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
            adapter = new BlelistAdapter(R.layout.item_ble,datas);
            adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            adapter.setOnItemChildClickListener((adapter, view, position) -> {
                switch (view.getId()){
                    case R.id.bt_connect:
                        myService.connect(datas.get(position).getDevice());
                        break;

                    case R.id.bt_enter:
                        //TODO 操作
                        ToastUtils.show("点击了操作");
                        break;
                }
            });
            rv.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unbindService(serviceConnection);
        set = SP.getSetData(Constant.SET_KEY, String.class);
        recycle();
    }


    /**
     * 蓝牙扫描
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ScanEvent event) {
        switch (event.type){
            case 1:
                //TODO 显示扫描dailog
                break;

            case 2:
                //TODO 更新蓝牙列表
                for(BleBean bleBean : datas){
                    if(bleBean.getDevice().getMac().equals(event.device.getMac())){
                        Log.e("ble","-------------已经存在此mac："+event.device.getMac());
                        return;
                    }
                }
                BleBean bleBean = new BleBean();
                bleBean.setDevice(event.device);
                datas.add(bleBean);
                adapter.notifyItemInserted(datas.size()-1);
                break;

            case 3:
                //TODO 隐藏扫描dailog
                Log.e("ble","扫描完成");
                ToastUtils.show("扫描完成");
                set = SP.getSetData(Constant.SET_KEY, String.class);
                delayScan(10000L);
                break;

            default:
                break;
        }
    }


    /**
     * 延时扫描
     */
    private void delayScan(long time){
        set = SP.getSetData(Constant.SET_KEY, String.class);
        if(null == set || set.size() < 5){
            if(null == handler){
                handler = new Handler();
            }
           /* if(null == delayScanRunnable) {*/
                delayScanRunnable = () -> {
                    if(myService != null){
                        myService.startScan();
                    }
                };
           /* }*/
            handler.postDelayed(delayScanRunnable,time);
        }

       /*  //定时器
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask(){
            @Override
            public void run() {
                if(set.size()<5){
                    myService.startScan();
                }else{
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask,0,5000L);//延时0秒执行，间隔10秒
        timer.schedule(timerTask,3000L);//延时3秒执行，只执行一次
        timer.cancel();*/
    }


    /**
     * 蓝牙连接
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ConnectEvent event) {
        //TODO 这里有必要把数据存储到数据库，因为要区分是哪个设备断开了连接  ,或者用SP替代
        switch (event.type){
            case 1:
                //TODO 显示连接dailog
                Log.e("ble","开始连接");
                ToastUtils.show("开始连接");
                break;

            case 2:
                Log.e("ble","连接失败");
                ToastUtils.show("连接失败");
                //TODO 隐藏连接dailog
                //TODO 提示连接失败
                //TODO 判断本地是否存储有mac 地址，如果有则，延时2秒在重新连接
                set = SP.getSetData(Constant.SET_KEY, String.class);
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
                Log.e("ble","连接成功mac"+event.device.getMac());
                ToastUtils.show("连接成功mac："+event.device.getMac());
                set = SP.getSetData(Constant.SET_KEY, String.class);
                if(null == set){
                    Log.e("ble","set=null");
                    set =  new HashSet<>();
                }
                if (!set.contains(event.device.getMac()) && set.size() < 5) {//如果为空，或者不包括这个mac并且保存的mac数量小于5
                    set.add(event.device.getMac());
                    SP.putData(Constant.SET_KEY, set);
                    Log.e("ble","连接的mac地址:"+event.device.getMac());
                }
                int i = 0;
                boolean isExist = false;
                for(BleBean bleBean : datas){
                    if(bleBean.getDevice().getMac().equals(event.device.getMac())){
                        datas.get(i).setConnect(true);
                        adapter.notifyItemChanged(i);
                        Log.e("ble","连接成功更新ui");
                        isExist = true;
                        break;
                    }
                    i++;
                }
                if(!isExist){//如果列表里面没有，但是已经连接过这个设备，则需要添加到列表，不添加也行。
                    set = SP.getSetData(Constant.SET_KEY, String.class);
                    if(set.contains(event.device.getMac())){
                        BleBean bean = new BleBean();
                        bean.setDevice(event.device);
                        bean.setConnect(true);
                        datas.add(0,bean);
                        //adapter.notifyItemRangeInserted(1,datas.size()-1);
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

            case 4:
                Log.e("ble","断开连接");
                ToastUtils.show("连接断开mac："+event.device.getMac());
                int k = 0;
                for(BleBean bleBean : datas){
                    if(bleBean.getDevice().getMac().equals(event.device.getMac())){
                        datas.get(k).setConnect(false);
                        adapter.notifyItemChanged(k);
                        Log.e("ble","断开连接更新ui");
                    }
                    k++;
                }
                if(set.contains(event.device.getMac())){
                    delayConnect(event.device.getMac(),2000L);
                }

               /* set = SPUtils.getInstance(this).getStringSet(SET_KEY,null);
                if(null != set && set.contains(event.device.getMac())){
                    //TODO 这里需要根据mac地址来对比是哪个设备断开了，然后在更新ui
                    //TODO 提示连接断开
                }*/
                break;

            default:
                break;
        }
    }


    /**
     * 延时连接
     * @param mac
     */
    private void delayConnect(String mac,long time){
        if(myService == null){
            return;
        }
        if(null == handler){
            handler = new Handler();
        }
        //TODO 这里匿名内部类需要换成变量，不然会出现内存泄漏，空指针异常
       /* handler.postDelayed(()->{
            Log.e("ble","延时连接mac:"+mac);
            myService.connect(mac);
            },time);*/
        ToastUtils.show("重连mac："+mac);
        delayConnectRunnable = ()->{
            Log.e("ble","延时连接mac:"+mac);
            if(myService == null || mac == null){
                return;
            }
            myService.connect(mac);
        };
        handler.postDelayed(delayConnectRunnable,time);
    }

    /**
     * 自动连接设备
     */
    private void autoConnect(long time){
        set = SP.getSetData(Constant.SET_KEY, String.class);
        if(null == set || (set.size() < 5)){
            myService.startScan();
        }
        if(null == set){
            return;
        }
        //开启线程连接设备
        if(null == handler){
            handler = new Handler();
        }
        if(delayConnectRunnable == null){
            delayConnectRunnable = () -> {
                for(String mac : set){
                    Log.e("ble","自动连接mac:"+mac);
                    myService.connect(mac);
                }
            };
        }
        handler.postDelayed(delayConnectRunnable,time);
    }


    /**
     * 回收资源
     */
    private void recycle(){
        if(myService != null){
            myService = null;
        }
        if(delayConnectRunnable != null && handler != null){
            handler.removeCallbacks(delayConnectRunnable);
            delayConnectRunnable = null;
        }
        if(delayScanRunnable != null && handler != null){
            handler.removeCallbacks(delayScanRunnable);
            delayScanRunnable = null;
        }
        if(null != handler){
            handler = null;
        }
    }
}
