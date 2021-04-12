package com.xincheng.vitalsigns.ui.fragment.setting;

import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.widget.Switch;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.MultiAdapter;
import com.xincheng.vitalsigns.bean.MultiBean;
import com.xincheng.vitalsigns.bean.WifiBean;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * wifi fragment
 *  TODO wifi连接监听
 *  TODO wifi跳到系统设置里面去
 */
public class WifiFragment extends BaseFragment {
    private MultiAdapter adapter;
    private RecyclerView rv_wifi;
    private List<MultiBean<WifiBean>> datas;
    private static int preTimePosition;
    private static MultiBean<WifiBean> hideBean;

    public static BaseFragment newInstance(){
        //Bundle args = new Bundle();
        //args.putString(ARG_MENU, menu);
        //BleFragment fragment = new BleFragment();
        //fragment.setArguments(args);
        return new WifiFragment();
    }


    @Override
    protected int getLayoutId() {
        Intent intent =  new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
//        return R.layout.fragment_wifi;
        return 0;
    }

    @Override
    protected void initView() {
//        rv_wifi = $(R.id.rv_wifi);
    }

    @Override
    protected void initData() {
        datas = getData();
        if(adapter == null) {
            adapter = new MultiAdapter<MultiBean<WifiBean>>(datas) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, MultiBean<WifiBean> item) {
                    switch (item.getItemType()) {
                        case WifiBean.TYPE_HEADER:
                            helper.setText(R.id.tv_header, item.getHearder());
                            break;

                        case WifiBean.TYPE_OPEN_WIFI:
                            helper.setText(R.id.tv_label, getString(R.string.wlan))
                            .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            Switch sw = helper.getView(R.id.swith);
                            sw.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                openWlan(isChecked);
                            });
                            break;

                        case WifiBean.TYPE_CONNECT_WIFI:
                            helper.setText(R.id.tv_wifiName,item.getData().getWifiName())
                                    .setVisible(R.id.iv_connected,item.getData().isConnected() ? true : false)
                                    .setGone(R.id.v_line,item.getData().isLastItem() ? false : true);
                            helper.getView(R.id.iv_info).setOnClickListener((v)-> {
                                //TODO 弹出一个弹框提示

                            });
                            break;
                    }
                }

                @Override
                protected void addItemTypes() {
                    addItemType(WifiBean.TYPE_HEADER, R.layout.multipe_screen_header);
                    addItemType(WifiBean.TYPE_OPEN_WIFI, R.layout.multipe_screen_light_mode);
                    addItemType(WifiBean.TYPE_CONNECT_WIFI, R.layout.multipe_wifi_connect);
                }
            };
            rv_wifi.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter, view, position) -> {
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
            });
        }
    }

    /**
     * 开启 or 关闭wifi
     * @param isChecked
     */
    private void openWlan(boolean isChecked) {
    }

    /**
     * 获取数据
     * @return
     */
    private List<MultiBean<WifiBean>> getData() {
        List<MultiBean<WifiBean>> datas = new ArrayList<>();
        datas.add(new MultiBean<>(WifiBean.TYPE_HEADER,getActivity().getString(R.string.wlan_setting)));
        //TODO 这里需要检测一下WiFI是否打开
        datas.add(new MultiBean<>(new WifiBean(false,true),WifiBean.TYPE_OPEN_WIFI));//TODO 记得有wifi列表的时候需要把这个位置的 item.getData().setLastItem(false)
        datas.add(new MultiBean<>(WifiBean.TYPE_HEADER,getActivity().getString(R.string.connet_wlan)));
        return datas;
    }
}
