package com.xincheng.vitalsigns.ui.activity;

import android.view.Gravity;
import android.view.View;

import com.coorchice.library.SuperTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.RecordOfSynchroAdapter;
import com.xincheng.vitalsigns.bean.RecordBean;

import java.util.List;

/**
 * 患者详情信息显示页面
 */
public class PatientDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TitleBar titleBar;//头部对象

    private BaseCircleDialog dialog;//弹窗对象

    private List<Integer> deletePosition;//删除的id集合

    private RecordOfSynchroAdapter adapter;

    private List<RecordBean> datas;//数据源

    private SuperTextView measure_st_history;//同步按钮

    private SuperTextView measure_delete;//删除按钮

    /**
     * 是否是多选状态
     */
    private boolean isLongClick;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_patient_details;//绑定布局
    }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);//获取头部组件
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {//头部按钮监听事件
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
        measure_st_history = $(R.id.measure_st_history);
        measure_st_history.setOnClickListener(this);
        measure_delete = $(R.id.measure_delete);
        measure_delete.setOnClickListener(this);
    }

    @Override
    protected void initData() {//绑定数据

    }

    @Override
    public void onClick(View v) {//点击事件
        switch (v.getId()){
            case R.id.measure_st_history://同步按钮
                showDailog(0.3f,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.titleColor80),
                        getString(R.string.sure_sy_current_data),18,getResources().getColor(R.color.titleColor),
                        getString(R.string.will_uploads_current_device_data),12,getResources().getColor(R.color.titleColor),
                        getString(R.string.cancel),16,getResources().getColor(R.color.colorRed),
                        getString(R.string.cogradient_button),16,getResources().getColor(R.color.colorPrimary));

                break;


            case R.id.measure_delete://删除按钮
                showDailog(0.3f,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.titleColor80),
                        getString(R.string.sure_delete_current_data),18,getResources().getColor(R.color.titleColor),
                        getString(R.string.will_delete_current_device_data),12,getResources().getColor(R.color.titleColor),
                        getString(R.string.cancel),16,getResources().getColor(R.color.colorRed),
                        getString(R.string.query),16,getResources().getColor(R.color.colorPrimary));
                break;
        }
    }

    /**
     * 显示 dailog
     * @param width 宽度
     * @param bgColor   背景色
     * @param pressBgColor  按下时的背景色
     * @param title 标题
     * @param titleSize 标题文本大小
     * @param titleColor    标题文本颜色
     * @param content   内容
     * @param contentSize   内容文本大小
     * @param contentColor  内容文本颜色
     * @param cancel    取消文本
     * @param cancelSize    取消文本大小
     * @param cancelColor   取消文本颜色
     * @param query 确认文本
     * @param querySize 确认文本大小
     * @param queryColor    确认文本颜色
     */
    private void showDailog(float width,int bgColor,int pressBgColor,
                            String title,int titleSize,int titleColor,
                            String content,int contentSize,int contentColor,
                            String cancel,int cancelSize,int cancelColor,
                            String query,int querySize,int queryColor){
        if(dialog == null){
            dialog =  new CircleDialog.Builder()
                    .setWidth(0.3f)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .configDialog(params -> {
                        params.backgroundColor = bgColor;
                        params.backgroundColorPress = pressBgColor;
                    })
                    .setTitle(title)
                    .configTitle(params -> {
                        params.textColor = titleColor;
                        params.gravity = Gravity.CENTER;
                        params.textSize = titleSize;
                    })
                    .setText(content)
                    .configText(params -> {
                        params.textColor = contentColor;
                        params.gravity = Gravity.CENTER;
                        params.textSize = contentSize;
                    })
                    .configNegative(params -> {
                        params.textColor = cancelColor;
                        params.textSize = cancelSize;
                    })
                    .setNegative(cancel, v ->{
                        releaseDailog();
                    })
                    .configPositive(params -> {
                        params.textColor = queryColor;
                        params.textSize = querySize;
                    })
                    .setPositive(query, v ->{
                        toast(query);
                        releaseDailog();
                    })
                    .show(getSupportFragmentManager());
        }else{
            dialog.show(getSupportFragmentManager(),dialog.getTag());
        }
    }
    /**
     * 释放dailog资源
     */
    private void releaseDailog(){
        if(dialog != null){
            if(dialog.isVisible()){
                dialog.dismiss();
            }
            dialog = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseDailog();
    }

    public void toast(CharSequence text) {
        ToastUtils.show(text);
    }

    /**
     * 删除数据
     *TODO 删除数据
     */
    private void deleteData(){
        for(Integer i : deletePosition){
            datas.remove(i);
            adapter.notifyItemRemoved(i);
        }
    }

}