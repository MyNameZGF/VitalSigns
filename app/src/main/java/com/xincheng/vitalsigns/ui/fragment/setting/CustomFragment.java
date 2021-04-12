package com.xincheng.vitalsigns.ui.fragment.setting;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.DragAdapter;
import com.xincheng.vitalsigns.bean.CustomBean;
import com.xincheng.vitalsigns.event.CustomChangEvent;
import com.xincheng.vitalsigns.ui.fragment.BaseFragment;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;
import com.xincheng.vitalsigns.utlis.SP;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * 自定义Fragment
 */
public class CustomFragment extends BaseFragment {
    private static final String TAG = CustomFragment.class.getSimpleName();
    private RecyclerView rv_delete;
    private RecyclerView rv_add;
    private DragAdapter deleteAdapter;
    private DragAdapter addAdpter;
    private List<CustomBean> deleteDatas;
    private List<CustomBean> addDatas;
    private ItemTouchHelper mItemTouchHelper;
    private ItemDragAndSwipeCallback deleteItemDragAndSwipeCallback;

    private int startPosition;
    private int changePosition;

    public static BaseFragment newInstance() {
        //Bundle args = new Bundle();
        //args.putString(ARG_MENU, menu);
        //BleFragment fragment = new BleFragment();
        //fragment.setArguments(args);
        return new CustomFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_custom;
    }

    @Override
    protected void initView() {
        rv_delete = $(R.id.rv_delete);
        rv_add = $(R.id.rv_add);
    }

    @Override
    protected void initData() {
        getData();
        Log.e(TAG, "initData addDatas: " + addDatas.size());
        Log.e(TAG, "initData deleteDatas: " + deleteDatas.size());
        if (deleteAdapter == null) {//
            deleteAdapter = new DragAdapter(R.layout.item_custom, deleteDatas);
            rv_delete.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            rv_delete.addItemDecoration(new GridSpacingItemDecoration(4, 2, true)); //这里的间距在布局里面做了处理了。

            //绑定拖动事件
            OnItemDragListener listener = new OnItemDragListener() {
                @Override
                public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                    Log.d(TAG, "drag start pos:" + pos);
                    startPosition = pos;
                }

                @Override
                public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                }

                @Override
                public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                    Log.d(TAG, "drag end pos:" + pos);
                    changePosition = pos;
                    SP.putListData(Constant.CUSTOM_DELETE_DATA_KEY, deleteDatas);//存储数据
                    EventBus.getDefault().post(new CustomChangEvent(0, startPosition, changePosition));//通知位置发生了变更
                }
            };

            deleteItemDragAndSwipeCallback = new ItemDragAndSwipeCallback(deleteAdapter);
            mItemTouchHelper = new ItemTouchHelper(deleteItemDragAndSwipeCallback);
            mItemTouchHelper.attachToRecyclerView(rv_delete);
            deleteItemDragAndSwipeCallback.setSwipeMoveFlags(ItemTouchHelper.START | ItemTouchHelper.END);
            //deleteAdapter.enableSwipeItem();//滑动删除
            deleteAdapter.enableDragItem(mItemTouchHelper);
            deleteAdapter.setOnItemDragListener(listener);
            rv_delete.setAdapter(deleteAdapter);

            addAdpter = new DragAdapter(R.layout.item_custom, addDatas);
            rv_add.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            rv_add.addItemDecoration(new GridSpacingItemDecoration(4, 2, true)); //这里的间距在布局里面做了处理了。
            rv_add.setAdapter(addAdpter);

            //删除模块
            deleteAdapter.setOnItemChildClickListener((adapter, view, position) -> {
                CustomBean bean = deleteDatas.remove(position);
                deleteAdapter.notifyItemRemoved(position);
                bean.setType(2);
                if (addDatas.size() == 0) {
                    addDatas.add(bean);
                    addAdpter.notifyDataSetChanged();
                } else {
                    addDatas.add(bean);//这里和上面那里不能提出去，会报错，应该是adpter还没有子item，所以不能使用insert的方式.
                    addAdpter.notifyItemInserted(addDatas.size() - 1);
                }
                //存储数据
                SP.putListData(Constant.CUSTOM_ADD_DATA_KEY, addDatas);
                SP.putListData(Constant.CUSTOM_DELETE_DATA_KEY, deleteDatas);
                EventBusActivityScope.getDefault(getActivity()).post(new CustomChangEvent(1, position, false));//通知删除了item
            });

            //添加模块
            addAdpter.setOnItemClickListener((adapter, view, position) -> {
                CustomBean bean = addDatas.remove(position);
                addAdpter.notifyItemRemoved(position);
                bean.setType(1);
                deleteDatas.add(bean);
                deleteAdapter.notifyItemInserted(deleteDatas.size());
                //存储数据
                SP.putListData(Constant.CUSTOM_ADD_DATA_KEY, addDatas);
                SP.putListData(Constant.CUSTOM_DELETE_DATA_KEY, deleteDatas);
                showNodateView();
                EventBusActivityScope.getDefault(getActivity()).post(new CustomChangEvent(2, position, true));//通知添加了item
            });
            showNodateView();
        }
    }


    /**
     * 获取数据
     */
    private void getData() {
        SP.cleanByKey(Constant.CUSTOM_DELETE_DATA_KEY);
        SP.cleanByKey(Constant.CUSTOM_ADD_DATA_KEY);
        //零时存放
        List<CustomBean> customBeans = new ArrayList<>();
        deleteDatas = SP.getListData(Constant.CUSTOM_DELETE_DATA_KEY, CustomBean.class);
        if (deleteDatas == null || deleteDatas.size() <= 0) {//没有存储数据
            ArrayList<String> menus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_custom)));
            if (deleteDatas == null) {
                deleteDatas = new ArrayList<>();
            }
            if (addDatas == null) {
                addDatas = new ArrayList<>();
            }
            CustomBean bean = null;
            for (int i = 0; i < menus.size(); i++) {
                if (menus.get(i).equals("体温")) {
                    bean = new CustomBean(menus.get(i), R.drawable.wenduji1, 0, 0);
                } else if (menus.get(i).equals("血氧")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan, 1, 0);
                } else if (menus.get(i).equals("血压")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan1, 2, 0);
                } else if (menus.get(i).equals("血糖")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan2, 3, 0);
                } else if (menus.get(i).equals("脉率")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan4, 4, 1);
                } else if (menus.get(i).equals("呼吸")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan7, 5, 1);
                } else if (menus.get(i).equals("小便")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan5, 6, 1);
                } else if (menus.get(i).equals("大便")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan5, 7, 1);
                } else if (menus.get(i).equals("血氧波")) {
                    bean = new CustomBean(menus.get(i), R.drawable.ziyuan3, 8, 1);
                }
                customBeans.add(bean);
            }
            for (int i = 0; i < customBeans.size(); i++) {
                if (customBeans.get(i).getType() == 0 || customBeans.get(i).getType() == 1) {
                    deleteDatas.add(customBeans.get(i));
                    Log.e(TAG, "getData: getType" + customBeans.get(i).getType());
                } else if (customBeans.get(i).getType() == 2) {
                    Log.e(TAG, "getData: getType" + customBeans.get(i).getType());
                    addDatas.add(customBeans.get(i));
                }
            }
            //存储数据
            SP.putListData(Constant.CUSTOM_DELETE_DATA_KEY, deleteDatas);
            SP.putListData(Constant.CUSTOM_ADD_DATA_KEY, addDatas);
        } else {//有存储数据，获取数据
            deleteDatas = SP.getListData(Constant.CUSTOM_DELETE_DATA_KEY, CustomBean.class);
            addDatas = SP.getListData(Constant.CUSTOM_ADD_DATA_KEY, CustomBean.class);
            if (addDatas == null) {
                addDatas = new ArrayList<>();
            }
        }
    }

    /**
     * 无数据控件
     */
    private void showNodateView() {
        if (null == addDatas || addDatas.size() <= 0) {
            View view = getLayoutInflater().inflate(R.layout.entry, (ViewGroup) rv_add.getParent(), false);
            addAdpter.setEmptyView(view);
        }
    }
}
