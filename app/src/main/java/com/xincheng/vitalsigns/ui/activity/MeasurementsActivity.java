package com.xincheng.vitalsigns.ui.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.RecordOfSynchroAdapter;
import com.xincheng.vitalsigns.bean.PatientBean;
import com.xincheng.vitalsigns.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目测量同步页面
 */
@SuppressLint("ClickableViewAccessibility")
public class MeasurementsActivity extends BaseActivity implements View.OnClickListener{

    private TitleBar titleBar;
    private SuperTextView  measure_delete;
    private SuperTextView st_history;
    private TextView tv_startTime;
    private TextView tv_endTime;
    private Button tv_search;
    private SuperTextView st_delete;
    private RecyclerView rv_query;
    private CheckBox cv;
    private RecordOfSynchroAdapter adapter;
    private float getX;
    private float getY;
    private List<RecordBean> datas;
    private List<Integer> deletePosition;

    private BaseCircleDialog dialog;

    /**
     * 是否是多选状态
     */
    private boolean isLongClick;

    public MeasurementsActivity() {}


    @Override
    protected int getLayoutId() { return R.layout.activity_measurements_list; }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);
        measure_delete = $(R.id.measure_delete);
        st_history = $(R.id.measure_st_history);
        tv_startTime = $(R.id.tv_startTime);
        tv_endTime = $(R.id.tv_endTime);
        tv_search = $(R.id.tv_search);
        rv_query = $(R.id.rv_query);

        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) { finish(); }
            @Override
            public void onTitleClick(View v) { }
            @Override
            public void onRightClick(View v) { }
        });
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        measure_delete.setOnClickListener(this);
        st_history.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(adapter == null) {
            getData();
            adapter = new RecordOfSynchroAdapter(R.layout.item_measurements,datas);
            rv_query.setAdapter(adapter);
            //列表点击事件
            adapter.setOnItemClickListener((adapter, view, position) -> {
               /*if(position!=0){
                    //进入患者详情页面
                    startActivity(new Intent(this,PatientDetailsActivity.class));
                }

                /*Toast.makeText(this,"你好！"+getX+" "+getY,Toast.LENGTH_SHORT).show();*/
               /* RecordBean bean = datas.get(position);
                bean.setCheck(true);*/
                /*Toast.makeText(this,"你好！"+getX+" "+getY,Toast.LENGTH_SHORT).show();*/

               /* if(bean.getCheck()){
                    deletePosition.remove((Integer)position);//这里是移除这个值
                }else{
                    deletePosition.add(position);
                }*/
                /*bean.setCheck(!bean.getCheck());*/
                adapter.notifyItemChanged(position);
            });


           //列表长按事件
            /*adapter.setOnItemLongClickListener((newadapter, view, position) -> {
                isLongClick = true;
                st_delete.setVisibility(View.VISIBLE);
                st_history.setVisibility(View.VISIBLE);
                if(deletePosition == null){
                    deletePosition = new ArrayList<>();
                }
                deletePosition.clear();
                adapter.setShowCheckBox(true);
                adapter.notifyDataSetChanged();

                return false;

            });*/
           /* //列表点击事件
            adapter.setOnItemClickListener((newadapter, view, position) -> {
                System.out.println(position);
                if(position!=0){
                    Toast.makeText(QueryRoomActivity.this,"你好！",Toast.LENGTH_SHORT).show();
                }


            });*/

        }
    }
    /* //获取点击事件
     private final class ItemClickListener implements AdapterView.OnItemClickListener {

         public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             Toast.makeText(QueryRoomActivity.this,"你好！",Toast.LENGTH_SHORT).show();
         }
     }*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_startTime:
                //TODO 选择开始时间
                showDatePickDlg(R.id.tv_startTime);
                break;

            case R.id.tv_endTime:
                //TODO 选择结束时间
                showDatePickDlg(R.id.tv_endTime);
                break;

            case R.id.tv_search:
                //TODO 搜索 查询数据
                Toast.makeText(MeasurementsActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                break;

            case R.id.measure_delete://删除
                showDailog(0.3f,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.titleColor80),
                        getString(R.string.sure_delete_current_data),18,getResources().getColor(R.color.titleColor),
                        getString(R.string.will_delete_current_device_data),12,getResources().getColor(R.color.titleColor),
                        getString(R.string.cancel),16,getResources().getColor(R.color.colorRed),
                        getString(R.string.query),16,getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.measure_st_history:
                startActivity(new Intent(this, PatientInfoActivity.class));
                break;

        }
    }
    /**
     * 日期选择器显示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickDlg (int getID) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(MeasurementsActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(getID== R.id.tv_startTime){
                    MeasurementsActivity.this.tv_startTime.setText(year + "-" + month + "-" + dayOfMonth);
                }else{
                    MeasurementsActivity.this.tv_endTime.setText(year + "-" + month + "-" + dayOfMonth);
                }

            }


        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    /**
     * 获取数据
     * TODO 从数据库中读取数据
     */
    private void getData(){
        datas = new ArrayList<>();
        PatientBean bean = new PatientBean("2021-2-3 14:25:36","体温、血氧、血压、脉率、呼吸、小便、血糖、大便","慕言");
        PatientBean bean2 = new PatientBean("2021-2-4 14:25:36","体温、脉率、呼吸、小便","慕言");
        PatientBean bean3 = new PatientBean("2021-2-5 14:25:36","体温","慕言");
        PatientBean bean4 = new PatientBean("2021-2-6 14:25:36","体温、血氧","慕言");
        PatientBean bean5 = new PatientBean("2021-2-7 14:25:36","体温、血氧、血压","慕言");
        PatientBean bean6 = new PatientBean("2021-2-8 14:25:36","体温、血氧、血压、脉率","慕言");
        PatientBean bean7 = new PatientBean("2021-2-9 14:25:36","体温、血氧、血压、脉率、呼吸、小便","慕言");
        PatientBean bean8 = new PatientBean("2021-2-10 14:25:36","体温、小便、血糖、大便","慕言");
        PatientBean bean9 = new PatientBean("2021-2-11 14:25:36","体温、血氧、血糖、大便","慕言");
        PatientBean bean10 = new PatientBean("2021-2-13 14:25:36","体温、血氧、血压、血糖、大便","慕言");
        PatientBean bean11 = new PatientBean("2021-2-12 14:25:36","体温、血氧、血压、脉率、血糖、大便","慕言");
        PatientBean bean12 = new PatientBean("2021-2-14 14:25:36","体温、血氧、血压、脉率、呼吸、大便","慕言");
        PatientBean bean13 = new PatientBean("2021-2-16 14:25:36","体温、大便","慕言");
        PatientBean bean14 = new PatientBean("2021-2-15 14:25:36","体温、小便、血糖、大便","慕言");
        PatientBean bean15 = new PatientBean("2021-2-17 14:25:36","血糖、大便","慕言");
        PatientBean bean16 = new PatientBean("2021-2-18 14:25:36","体温、血糖、大便","慕言");
        PatientBean bean17 = new PatientBean("2021-2-19 14:25:36","体温、脉率、呼吸、小便、血糖、大便","慕言");
        PatientBean bean18 = new PatientBean("2021-2-20 14:25:36","体温、小便、血糖、大便","慕言");
        PatientBean bean19 = new PatientBean("2021-2-5 14:25:36","体温","慕言");
        PatientBean bean20 = new PatientBean("2021-2-6 14:25:36","体温、血氧","慕言");
        PatientBean bean21 = new PatientBean("2021-2-7 14:25:36","体温、血氧、血压","慕言");
        PatientBean bean22 = new PatientBean("2021-2-8 14:25:36","体温、血氧、血压、脉率","慕言");
        PatientBean bean23 = new PatientBean("2021-2-9 14:25:36","体温、血氧、血压、脉率、呼吸、小便","慕言");
        PatientBean bean24 = new PatientBean("2021-2-10 14:25:36","体温、小便、血糖、大便","慕言");
        PatientBean bean25 = new PatientBean("2021-2-11 14:25:36","体温、血氧、血糖、大便","慕言");
        PatientBean bean26 = new PatientBean("2021-2-13 14:25:36","体温、血氧、血压、血糖、大便","慕言");
        PatientBean bean27 = new PatientBean("2021-2-12 14:25:36","体温、血氧、血压、脉率、血糖、大便","慕言");
        PatientBean bean28 = new PatientBean("2021-2-14 14:25:36","体温、血氧、血压、脉率、呼吸、大便","慕言");
        PatientBean bean29 = new PatientBean("2021-2-16 14:25:36","体温、大便","慕言");

        datas.add(new RecordBean(bean,0 % 3,0,0));
        datas.add(new RecordBean(bean2,1 % 3,1,1));
        datas.add(new RecordBean(bean3,2 % 3,2,2));
        datas.add(new RecordBean(bean4,3 % 3,3,3));
        datas.add(new RecordBean(bean5,4 % 3,4,4));
        datas.add(new RecordBean(bean6,5 % 3,5,5));
        datas.add(new RecordBean(bean7,6 % 3,6,6));
        datas.add(new RecordBean(bean8,7 % 3,7,7));
        datas.add(new RecordBean(bean9,8 % 3,8,8));
        datas.add(new RecordBean(bean10,9 % 3,9,9));
        datas.add(new RecordBean(bean11,10 % 3,10,10));
        datas.add(new RecordBean(bean12,11 % 3,11,11));
        datas.add(new RecordBean(bean13,12 % 3,12,12));
        datas.add(new RecordBean(bean14,13 % 3,13,13));
        datas.add(new RecordBean(bean15,14 % 3,14,14));
        datas.add(new RecordBean(bean16,15 % 3,15,15));
        datas.add(new RecordBean(bean17,16 % 3,16,16));
        datas.add(new RecordBean(bean18,17 % 3,17,17));
        datas.add(new RecordBean(bean19,18 % 3,18,18));
        datas.add(new RecordBean(bean20,19 % 3,19,19));
        datas.add(new RecordBean(bean21,20 % 3,20,20));
        datas.add(new RecordBean(bean22,21 % 3,21,21));
        datas.add(new RecordBean(bean23,22 % 3,22,22));
        datas.add(new RecordBean(bean24,23 % 3,23,23));
        datas.add(new RecordBean(bean25,24 % 3,24,24));
        datas.add(new RecordBean(bean26,25 % 3,25,25));
        datas.add(new RecordBean(bean27,26 % 3,26,26));
        datas.add(new RecordBean(bean28,27 % 3,27,27));
        datas.add(new RecordBean(bean29,28 % 3,28,28));

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
                        toast("删除成功");
                        //TODO 删除
                        /*releaseDailog();
                        deleteData();
                        isLongClick = false;*/
                    })
                    .show(getSupportFragmentManager());
        }else{
            dialog.show(getSupportFragmentManager(),dialog.getTag());
        }
    }
    public void toast(CharSequence text) {
        ToastUtils.show(text);
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


    /**
     * 删除数据
     *TODO 删除数据
     */
    private void deleteData(){
        /*for(Integer i : deletePosition){
            datas.remove(i);
            adapter.notifyItemRemoved(i);
        }*/
    }


}