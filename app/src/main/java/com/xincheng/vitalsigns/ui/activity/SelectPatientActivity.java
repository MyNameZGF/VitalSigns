package com.xincheng.vitalsigns.ui.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.coorchice.library.SuperTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.PatientAdapter;
import com.xincheng.vitalsigns.bean.PatientBean;
import com.xincheng.vitalsigns.bean.PatientsBean;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/***
 * 选择病人页面
 */
public class SelectPatientActivity extends BaseActivity implements View.OnClickListener {
    private TitleBar titleBar;
    private SuperTextView st_out;
    private SuperTextView st_name;
    private EditText edt_search;
    private Spinner spinner;//TODO 这里应该要替换
    private SuperTextView st_measure;
    private SuperTextView st_unMeasure;
    private RecyclerView rv_select;

    private PatientAdapter adapter;
    private List<PatientsBean> datas;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_patient;
    }

    @Override
    protected void initView() {
        titleBar = $(R.id.titleBar);
        st_out = $(R.id.st_out);
        st_name = $(R.id.st_name);
        edt_search = $(R.id.edt_search);
        spinner = $(R.id.spinner);
        st_measure = $(R.id.st_measure);
        st_unMeasure = $(R.id.st_unMeasure);
        rv_select = $(R.id.rv_select);

        st_out.setOnClickListener(this);
        titleBar.setOnTitleBarListener(new OnTitleBarListener() {
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
    }

    @Override
    protected void initData() {
        getData();
        if (adapter == null) {
            adapter = new PatientAdapter(R.layout.item_patient, datas);
            GridLayoutManager manager = new GridLayoutManager(this, 4);
            rv_select.setLayoutManager(manager);
            rv_select.addItemDecoration(new GridSpacingItemDecoration(4, 40, true)); //这里的间距在布局里面做了处理了。
            adapter.openLoadAnimation();
            rv_select.setAdapter(adapter);
            adapter.setOnItemClickListener((adapter, view, position) -> {
                Intent intent = new Intent();
                intent.putExtra("bean", (Serializable) datas.get(position));
                setResult(RESULT_OK, intent);
                finish();
            });
        }


    }

    private void getData() {
        datas = new ArrayList<>();
        //TODO jsonArray 转 datas
        PatientsBean bean1 =new PatientsBean(1,"p10001","张三","d10001","2021-04-04","1-10001",null,20,0,"2021-04-04",0,5,0,"王医生","u10001");
        PatientsBean bean2 =new PatientsBean(2,"p10001","张三","d10001","2021-04-04","1-10001",null,20,0,"2021-04-04",0,5,0,"王医生","u10001");
        datas.add(bean1);
        datas.add(bean2);


        int a = 0;
        int b = 0;
        for (int i =0;i<datas.size();i++){
            if(datas.get(i).getCheck_status()==1){
                a+=datas.get(i).getCheck_count();
            }
            b+=datas.get(i).getNeed_check_count();
        }

        st_measure.setText(st_measure.getText().toString()+a);
        st_unMeasure.setText(st_unMeasure.getText().toString()+(b-a));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.st_out:
                //TODO 退出登录
                startActivity(new Intent(SelectPatientActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }
}
