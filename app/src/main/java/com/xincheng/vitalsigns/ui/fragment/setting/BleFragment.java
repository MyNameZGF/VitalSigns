package com.xincheng.vitalsigns.ui.fragment.setting;


import android.bluetooth.BluetoothAdapter;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Switch;

import com.chad.library.adapter.base.BaseViewHolder;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.MultiAdapter;
import com.xincheng.vitalsigns.bean.BleBean;
import com.xincheng.vitalsigns.bean.BleBean2;
import com.xincheng.vitalsigns.bean.MultiBean;
import com.xincheng.vitalsigns.bean.WifiBean;
import com.xincheng.vitalsigns.event.ScanEvent;
import com.xincheng.vitalsigns.ui.activity.MainActivity;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 蓝牙连接页面
 * TODO 蓝牙换成跳到系统里面去。
 */
public class BleFragment extends BaseFragment {
    private RecyclerView rv_ble;
    private MultiAdapter adapter;
    private List<MultiBean<BleBean2>> datas;


    public static BaseFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_MENU, menu);
        //BleFragment fragment = new BleFragment();
        //fragment.setArguments(args);
        return new BleFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ble;
    }

    @Override
    protected void initView() { rv_ble = $(R.id.rv_ble); }

    @Override
    protected void initData() {
        datas = getData();
        if(adapter == null) {
            adapter = new MultiAdapter<MultiBean<BleBean2>>(datas) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, MultiBean<BleBean2> item) {
                    switch (item.getItemType()) {
                        case BleBean2.TYPE_HEADER:
                            helper.setText(R.id.tv_header, item.getHearder());
                            break;

                        case BleBean2.TYPE_OPEN_BLE:
                            helper.setText(R.id.tv_label, getString(R.string.ble))
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            Switch sw = helper.getView(R.id.swith);
                            sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                openBle(isChecked);
                            });
                            break;

                        case BleBean2.TYPE_BLE_INFO:
                            helper.setText(R.id.tv_bleName,item.getData().getBleName())
                                    .setText(R.id.tv_status,item.getData().getStatus() == 0 ? "连接" : (item.getData().getStatus() == 1 ? "断开" : "配对中"))
                                   // .setVisible(R.id.iv_connected,item.getData().isConnected() ? true : false)//TODO 这里提示空指针异常,没有这个属性
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            break;
                    }
                }

                @Override
                protected void addItemTypes() {
                    addItemType(BleBean2.TYPE_HEADER, R.layout.multipe_screen_header);
                    addItemType(BleBean2.TYPE_OPEN_BLE, R.layout.multipe_screen_light_mode);
                    addItemType(BleBean2.TYPE_BLE_INFO, R.layout.multipe_ble_connect);
                }
            };
            rv_ble.setAdapter(adapter);
            /*adapter.setOnItemClickListener((adapter, view, position) -> {
                WifiBean bean = datas.get(position).getData();
                if (!bean.isConnected()) {
                    if(bean.isLocked()){//需要密码
                        //TODO 弹出一个弹框输入密码
                    }else{
                        //TODO 直接连接
                        //TODO 连接上了，需要更新ui
                        //preTimePosition = position;
                        hideBean = datas.get(position);
                        hideBean.getData().setConnected(true);
                        datas.remove(position);
                        adapter.notifyItemChanged(position);
                        datas.add(2,hideBean);
                        adapter.notifyItemInserted(2);
                    }
                }
            });*/
        }
    }

    private List<MultiBean<BleBean2>> getData() {
        List<MultiBean<BleBean2>> data= new ArrayList<>();
        //datas = new ArrayList<>();
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            toast("该设备不支持蓝牙");
            return null;
        }
        //datas.add(new MultiBean<>(BleBean2.TYPE_HEADER,getActivity().getString(R.string.ble_setting)));
        data.add(new MultiBean<>(BleBean2.TYPE_HEADER,getActivity().getString(R.string.ble_setting)));
        data.add(new MultiBean<>(new BleBean2(getActivity().getString(R.string.ble_setting),false,bluetoothAdapter.isEnabled()),BleBean2.TYPE_OPEN_BLE));
        data.add(new MultiBean<>(new BleBean2(getActivity().getString(R.string.ble_setting),true,3,false),BleBean2.TYPE_BLE_INFO));
        return data;
    }

    /**
     * 打开或者关闭 ble
     * @param isChecked
     */
    private void openBle(boolean isChecked) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            toast("该设备不支持蓝牙");
            return;
        }
        if(isChecked){
            bluetoothAdapter.enable();
        }else{
            bluetoothAdapter.disable();
        }
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
                /*for(BleBean bleBean : datas){
                    if(bleBean.getDevice().getMac().equals(event.device.getMac())){
                        Log.e("ble","-------------已经存在此mac："+event.device.getMac());
                        return;
                    }
                }
                BleBean bleBean = new BleBean();
                bleBean.setDevice(event.device);
                datas.add(bleBean);
                adapter.notifyItemInserted(datas.size()-1);*/
                break;

            case 3:
                //TODO 隐藏扫描dailog
                Log.e("ble","扫描完成");
              /*  ToastUtils.show("扫描完成");
                set = SP.getSetData(Constant.SET_KEY, String.class);
                delayScan(10000L);*/
                break;

            default:
                break;
        }
    }
}
