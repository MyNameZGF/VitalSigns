package com.xincheng.vitalsigns.ui.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hjq.toast.ToastUtils;
import com.xincheng.vitalsigns.bean.CustomBean;
import com.xincheng.vitalsigns.event.CustomChangEvent;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.SP;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

public abstract class BaseFragment  extends SupportFragment{

    protected static final String TAG = "BaseFragment";
    private Unbinder unbinder;
    /** 根布局 */
    private View mRootView;
    /** 当前页面是否加载过 */
    private boolean isLoading;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMessage(CustomChangEvent changEvent) {
        Log.d(TAG, "onGetMessage: "+changEvent.toString());
        switch (changEvent.type){
            case 0:
                Log.d(TAG, "onReceiveMessage: 变更了位置");
                break;
            case 1:
                Log.d(TAG, "onReceiveMessage: 删除了模块");
                break;
            case 2:
                Log.d(TAG, "onReceiveMessage: 添加了新模块");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + changEvent.type);
        }
    }

    private static <E> void swap(List<E> list,int index1,int index2) {
        //定义第三方变量
        E e=list.get(index1);
        //交换值
        list.set(index1, list.get(index2));
        list.set(index2, e);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        isLoading = false;
        if (getLayoutId() > 0) {
            mRootView =  inflater.inflate(getLayoutId(), container,false);
            unbinder = ButterKnife.bind(this,mRootView);
            return mRootView;
        }else {
            return null;
        }
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 获取布局 ID
     */
    protected abstract int getLayoutId();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    public void onResume() {
        super.onResume();
        if (!isLoading) {
            isLoading = true;
            EventBusActivityScope.getDefault(_mActivity).register(this);
            initView();
            initData();
        }
    }


    @Override
    public void onDestroyView() {
        EventBusActivityScope.getDefault(_mActivity).unregister(this);
        isLoading = false;
        mRootView = null;
        if (unbinder != null) {
            unbinder.unbind();
        }
        super.onDestroyView();
    }

    public <V extends View> V $(@IdRes int id) {
        return mRootView.findViewById(id);
    }

    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }
}
