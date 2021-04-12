package com.xincheng.vitalsigns.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.coorchice.library.SuperTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.RecordAdapter;
import com.xincheng.vitalsigns.bean.PatientBean;
import com.xincheng.vitalsigns.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 查房页面
 * //TODO 需要添加多选的选项，多选那里的还需优化
 */
public class QueryRoomActivity extends BaseActivity implements View.OnClickListener{
    private TitleBar titleBar;
    private SuperTextView st_delete;
    private SuperTextView st_history;
    private Spinner spinner;
    private TextView tv_startTime;
    private TextView tv_endTime;
    private TextView edt_search;
    private Button tv_search;
    private RecyclerView rv_query;
    private RecordAdapter adapter;
    private String element;
    private List<RecordBean> datas;
    private List<Integer> deletePosition;

    private BaseCircleDialog dialog;



    /**
     * 是否是多选状态
     */
    private boolean isLongClick;


    @Override
    protected int getLayoutId() { return R.layout.activity_query_room; }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);
        st_delete = $(R.id.st_delete);
        st_history = $(R.id.st_history);
        spinner = $(R.id.spinner);
        tv_startTime = $(R.id.tv_startTime);
        tv_endTime = $(R.id.tv_endTime);
        tv_search = $(R.id.tv_search);
        rv_query = $(R.id.rv_query);
        edt_search = $(R.id.edt_search);
        spinner.setOnItemSelectedListener( new  OnItemSelectedListenerImpl());
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
        st_delete.setOnClickListener(this);
        st_history.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        if(adapter == null) {
            getData();
            adapter = new RecordAdapter(R.layout.item_record,datas);
            rv_query.setAdapter(adapter);
            //列表点击事件
            adapter.setOnItemClickListener((adapter, view, position) -> {
                if(position!=0){
                    startActivity(new Intent(this, MeasurementsActivity.class));
                }

                if(!isLongClick){
                    return;
                }
                RecordBean bean = datas.get(position);
                if(bean.getCheck()){
                    deletePosition.remove((Integer)position);//这里是移除这个值
                }else{
                    deletePosition.add(position);
                }
                bean.setCheck(!bean.getCheck());
                adapter.notifyItemChanged(position);
            });
            /*//列表长按事件
            adapter.setOnItemLongClickListener((newadapter, view, position) -> {
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
        }
    }

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
                //TODO 搜索
                Toast.makeText(QueryRoomActivity.this,"查询"+edt_search.getText()+","+element+","+tv_startTime.getText()+","+tv_endTime.getText()+"的信息",Toast.LENGTH_SHORT).show();
                break;

            case R.id.st_delete://删除
                showDailog(0.3f,getResources().getColor(R.color.colorWhite),getResources().getColor(R.color.titleColor80),
                        getString(R.string.sure_delete_current_data),18,getResources().getColor(R.color.titleColor),
                        getString(R.string.will_delete_current_device_data),12,getResources().getColor(R.color.titleColor),
                        getString(R.string.cancel),16,getResources().getColor(R.color.colorRed),
                        getString(R.string.query),16,getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.st_history:
                startActivity(new Intent(this, PatientInfoActivity.class));
                break;
        }
    }
    // 下拉框选择事件
    private   class  OnItemSelectedListenerImpl  implements AdapterView.OnItemSelectedListener {
        @Override
        public   void  onItemSelected(AdapterView<?> parent, View view,  int  position,  long  id) {
            element = parent.getItemAtPosition(position).toString(); // 得到spanner的值
            Toast.makeText(QueryRoomActivity.this, "选择的元素是："  + element,Toast.LENGTH_SHORT).show();
        }
        @Override
        public   void  onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    }
    /**
     * 日期选择器显示
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void showDatePickDlg (int getID) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(QueryRoomActivity.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                if(getID== R.id.tv_startTime){
                    QueryRoomActivity.this.tv_startTime.setText(year + "-" + month + "-" + dayOfMonth);
                }else{
                    QueryRoomActivity.this.tv_endTime.setText(year + "-" + month + "-" + dayOfMonth);
                }

            }


        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
    /**
     * 获取数据
     */
    private void getData(){
        datas = new ArrayList<>();
        PatientBean bean;
        for(int i= 0;i<30;i++){
            bean = new PatientBean("1-0"+i,i / 2 == 0 ? true : false,"李-"+i,i / 2 == 0 ? "男" : "女",(10+i)+"岁",i,i,"2021-2-3 14:25:36");
            bean.setAd(String.valueOf(1000+i));
            datas.add(new RecordBean(bean,i % 3,i,i+1));
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
                        System.out.println(query);
                        /*//TODO 删除
                        releaseDailog();
                        deleteData();
                        isLongClick = false;*/
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


    /**
     * 删除数据
     */
    private void deleteData(){
        for(Integer i : deletePosition){
            datas.remove(i);
            adapter.notifyItemRemoved(i);
        }
    }
}
