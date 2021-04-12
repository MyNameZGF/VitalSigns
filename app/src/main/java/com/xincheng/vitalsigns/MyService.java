package com.xincheng.vitalsigns;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleIndicateCallback;
import com.clj.fastble.callback.BleNotifyCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.exception.BleException;
import com.hjq.language.MultiLanguages;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.event.ConnectEvent;
import com.xincheng.vitalsigns.event.ScanEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 1.处理蓝牙连接
 * 2.多线程循环连接设备
 *      Executors.newCachedThreadPool()
 *      可以参考GRBL里面的代码
 * 3.数据解析
 * 4.通知数据更新
 * 5.将连接和数据处理放入队列中。
 *      如果BleManager.getInstance()是单例模式，则需要用到队列，否则多线程即可。
 *      这里不需要用队列，多线程即可
 * //6.需要设置为前台运行服务，这样资源不足时，也不会kill掉服务。
 *
 * 服务运行在主线程中
 *
 * IntentService
 *      使用工作线程逐一处理所有启动请求。如果您不要求服务同时处理多个请求，此类为最佳选择。
 *      使用场景
 *          单线程
 *
 *  Service
 *      默认使用应用的主线程，这会降低应用正在运行的任何 Activity 的性能
 *      使用场景
 *          多线程
 */
public class MyService extends Service {
    private IBinder myBinder;
    private ExecutorService exec;

    public  class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(MultiLanguages.attach(newBase));
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myBinder = new MyBinder();
        exec = Executors.newCachedThreadPool();
        //TODO 初始化工作
    }

    /**
     * 绑定服务，便于组件与服务进行交互，组件取消绑定后，服务销毁。
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return myBinder; }


    /**
     * 启动服务，让组件，如activity启动服务，以无期限运行，要结束运行的话，需调用stopSelf() or  stopService()
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //TODO 回收释放资源，取消绑定
        cancelConnect();
    }

    /**
     * 开始扫描
     */
    public void startScan(){
        Log.e("myService","开始扫描");
        ToastUtils.show("开始扫描");
        BleManager.getInstance().scan(new BleScanCallback() {
            @Override
            public void onScanStarted(boolean success) {//开始扫描
                EventBus.getDefault().post(new ScanEvent(success,1));
            }
            @Override
            public void onScanning(BleDevice bleDevice) {//扫描到设备
                EventBus.getDefault().post(new ScanEvent(bleDevice,2));
            }
            @Override
            public void onScanFinished(List<BleDevice> scanResultList) {//扫描完成
                EventBus.getDefault().post(new ScanEvent(scanResultList,3));
            }
        });
    }

    /**
     * 根据设备连接
     * 第一次连接使用
     * 连接过程需要放在主线程
     * @param device
     */
    public void connect(BleDevice device){
        exec.execute(()->{
                BleManager.getInstance().connect(device, new BleGattCallback() {
                    @Override
                    public void onStartConnect() {//开始连接
                        //TODO 显示连接dailog
                        EventBus.getDefault().post(new ConnectEvent(1));
                    }

                    @Override
                    public void onConnectFail(BleDevice bleDevice, BleException exception) {//连接失败
                        //TODO 隐藏连接dailog
                        //TODO 提示连接失败
                        EventBus.getDefault().post(new ConnectEvent(bleDevice,2));
                    }

                    @Override
                    public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {//连接成功
                        //TODO 隐藏连接dailog
                        //TODO 提示连接成功
                        EventBus.getDefault().post(new ConnectEvent(bleDevice,gatt,3));
                    }

                    @Override
                    public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                        //TODO 提示连接断开
                        EventBus.getDefault().post(new ConnectEvent(bleDevice,gatt,4));
                    }
                });
        });
    }

    /**
     * 通过mac 地址连接
     * 非第一次连接使用
     * 连接过程需要放在主线程
     * @param mac
     */
    public void connect(String mac){
        //延时2秒后执行
       /* AppExceutors.getInstance().scheduledExecutor().schedule(new Runnable() {
            @Override
            public void run() {
                // do something
            }
        },2, TimeUnit.SECONDS);*/

        /*AppExceutors.getInstance().networkIO().execute(new Runnable() {
            @Override
            public void run() {
                //do something
            }
        });*/

        exec.execute(()-> {
            BleManager.getInstance().connect(mac, new BleGattCallback() {
                @Override
                public void onStartConnect() {
                    EventBus.getDefault().post(new ConnectEvent(1));
                    //TODO 显示连接dailog
                }

                @Override
                public void onConnectFail(BleDevice bleDevice, BleException exception) {
                    //TODO 隐藏连接dailog
                    //TODO 提示连接失败
                    EventBus.getDefault().post(new ConnectEvent(bleDevice, 2));
                }

                @Override
                public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    //TODO 隐藏连接dailog
                    //TODO 提示连接成功
                    EventBus.getDefault().post(new ConnectEvent(bleDevice, gatt, 3));
                }

                @Override
                public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                    //TODO 提示连接断开
                    EventBus.getDefault().post(new ConnectEvent(bleDevice, gatt, 4));
                }
            });
        });
    }

    /**
     * notify机制不一定确保消息能到达，但是传输效率高，速度快。
     * 开启通知
     * @param device
     * @param service
     * @param characteristic
     */
    private void notify(BleDevice device,String service,String characteristic){
        BleManager.getInstance().notify(device, service, characteristic, new BleNotifyCallback() {
            @Override
            public void onNotifySuccess() {//通知打开成功
                //TODO 这里设置通知开启成功的flag= true,在断开连接的时候要将其设为flag=false
            }

            @Override
            public void onNotifyFailure(BleException exception) {//通知打开失败
                //TODO 这里需要延时重试
            }

            @Override
            public void onCharacteristicChanged(byte[] data) {//设备传递过来的数据
                //TODO 这里需要数据转换
            }
        });
    }

    /**
     * 关闭notify通知
     * @param device
     * @param service
     * @param characteristic
     */
    private boolean closeNotify(BleDevice device,String service,String characteristic){
        return BleManager.getInstance().stopNotify(device,service,characteristic);
    }

    /**
     * indicate机制，确保消息能够到达，速度稍慢。
     * 开启通知
     * @param device
     * @param service
     * @param characteristic
     */
    private void indicate(BleDevice device,String service,String characteristic){
        BleManager.getInstance().indicate(device, service, characteristic, new BleIndicateCallback() {
            @Override
            public void onIndicateSuccess() {//通知打开成功
                //TODO 这里设置通知开启成功的flag= true,在断开连接的时候要将其设为flag=false
            }

            @Override
            public void onIndicateFailure(BleException exception) {//通知打开失败
                //TODO 这里需要延时重试
            }

            @Override
            public void onCharacteristicChanged(byte[] data) {//设备传递过来的数据
                //TODO 这里需要数据转换
            }
        });
    }

    /**
     * 关闭Indicate通知
     * @param device
     * @param service
     * @param characteristic
     * @return
     */
    private boolean closeIndicate(BleDevice device,String service,String characteristic){
        return BleManager.getInstance().stopIndicate(device,service,characteristic);
    }



    /**
     * 取消连接
     */
    private void cancelConnect(){
        BleManager.getInstance().disconnectAllDevice();
        Log.e("MySevice","断开所有连接");
        BleManager.getInstance().destroy();
        exec.shutdownNow();
    }


    public void disConnect(){

    }
}
