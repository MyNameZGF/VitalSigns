package com.xincheng.vitalsigns.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.coorchice.library.SuperTextView;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.mylhyl.circledialog.BaseCircleDialog;
import com.mylhyl.circledialog.CircleDialog;
import com.xincheng.linechart.SuitLines;
import com.xincheng.linechart.Unit;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.adpter.MultiAdapter;
import com.xincheng.vitalsigns.bean.CustomBean;
import com.xincheng.vitalsigns.bean.MeasureBean;
import com.xincheng.vitalsigns.bean.MultiBean;
import com.xincheng.vitalsigns.bean.PatientBean;
import com.xincheng.vitalsigns.bean.BloodTypeBean;
import com.xincheng.vitalsigns.bean.PatientMeasure;
import com.xincheng.vitalsigns.bean.PatientsBean;
import com.xincheng.vitalsigns.dialog.BloodSugarDialog;
import com.xincheng.vitalsigns.dialog.BreatheDialog;
import com.xincheng.vitalsigns.dialog.FecesDialog;
import com.xincheng.vitalsigns.dialog.LoadingDialog;
import com.xincheng.vitalsigns.dialog.UrinateDialog;
import com.xincheng.vitalsigns.event.CustomChangEvent;
import com.xincheng.vitalsigns.utlis.Constant;
import com.xincheng.vitalsigns.utlis.GridSpacingItemDecoration;
import com.xincheng.vitalsigns.utlis.SP;

import org.greenrobot.eventbus.Subscribe;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.OnClick;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;

/**
 * TODO 添加数据库建立一个表，保存数据
 * 测量页面
 */
public class MeasureActivity extends BaseActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 0x00;
    private TitleBar titleBar;
    private SuperTextView st_save;
    private SuperTextView st_reset;
    private TextView tv_bedNO;
    private TextView tv_name;
    private TextView tv_ad;
    private TextView tv_select_patient;
    private TextView tv_select_patient_tip;
    private RecyclerView rv_measure;
    private List<Unit> lines;
    private BloodTypeBean bloodTypeBean = new BloodTypeBean(1, 0, "随机", 0);


    private MultiAdapter adapter;
    private List<MultiBean<MeasureBean>> datas;
    private List<CustomBean> positionDatas;
    private ArrayList<String> menus;
    private List<String> bloodMenus;
    private BaseCircleDialog dialog;
    private boolean isDefault = false;
    private BreatheDialog breatheDialog;
    private UrinateDialog urinateDialog;
    private FecesDialog fecesDialog;
    private BloodSugarDialog bloodSugarDialog;
    private LoadingDialog loadingDialog;
    private SuitLines suitlines;
    private PatientMeasure measureDataBean;
    DecimalFormat df = new DecimalFormat( "#0.#####" );


    @Override
    protected int getLayoutId() {
        return R.layout.activity_measure;
    }

    @Override
    protected void initView() {
        measureDataBean = new PatientMeasure();
        breatheDialog = new BreatheDialog(this);
        urinateDialog = new UrinateDialog(this);
        fecesDialog = new FecesDialog(this);
        bloodSugarDialog = new BloodSugarDialog(this);
        titleBar = $(R.id.titleBar);
        st_save = $(R.id.st_save);
        st_reset = $(R.id.st_reset);
        tv_bedNO = $(R.id.tv_bedNO);
        tv_name = $(R.id.tv_name);
        tv_ad = $(R.id.tv_ad);
        tv_select_patient = $(R.id.tv_select_patient);
        tv_select_patient_tip = $(R.id.tv_select_patient_tip);
        rv_measure = $(R.id.rv_measure);

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
        st_save.setOnClickListener(this);
        st_reset.setOnClickListener(this);
        tv_select_patient.setOnClickListener(this);
        EventBusActivityScope.getDefault(this).register(this);
        menus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_measure)));
        bloodMenus = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.blood_sugar_type)));
    }

    int inputType = 0;

    @OnClick(R.id.test)
    public void receiveMeasureInfo() {
        int index = findCustomBeanByMode(inputType);//找到温度在哪个位置，用于刷新模块
        MeasureBean measureBean;
        if (index != -1) {
            switch (inputType) {
                case 0://温度
                    Log.d(TAG, "test: 温度" + index);
                    measureDataBean.setTemperature((float)36.1);
                    measureBean = findMeasureBeanInputType(inputType);
                    measureBean.setResult("36C°");
                    adapter.refreshNotifyItemChanged(index);
                    inputType++;
                    break;
                case 1://血氧
                    Log.d(TAG, "test: 血氧" + index);
                    measureDataBean.setBlood_oxygen((float)96);
                    measureBean = findMeasureBeanInputType(inputType);
                    measureBean.setResult("96%");
                    adapter.refreshNotifyItemChanged(index);
                    inputType++;
                    break;
                case 2://血压
                    Log.d(TAG, "test: 血压" + index);
                    measureDataBean.setPressure("148/120");
                    measureBean = findMeasureBeanInputType(inputType);
                    measureBean.setCurrentValue(148);
                    measureBean.setDefualtValue(120);
                    adapter.refreshNotifyItemChanged(index);
                    inputType++;
                    break;
                case 3://血糖
                    Log.d(TAG, "test: 血糖" + index);
                    measureDataBean.setGlucose((float) 6.5);
                    measureDataBean.setGlucose_type(bloodTypeBean.getType());
                    measureBean = findMeasureBeanInputType(inputType);
                    measureBean.setResult("6.5");
                    adapter.refreshNotifyItemChanged(index);
                    inputType++;
                    break;
                case 4://脉率
                    Log.d(TAG, "test: 脉率" + index);
                    measureDataBean.setPulse_rate((float) 72);
                    measureBean = findMeasureBeanInputType(inputType);
                    measureBean.setResult("72");
                    adapter.refreshNotifyItemChanged(index);
                    inputType++;
                    break;
            }
        } else {
            Log.d(TAG, "test: 招不到此模块");
        }
    }

    //根据mode找到对应的模块下标
    public int findCustomBeanByMode(int mode) {
        for (int i = 0; i < positionDatas.size(); i++) {
            if (positionDatas.get(i).getMode() == mode) {
                return i;
            }
        }
        return -1;
    }

    //根据inputType找到对应的MeasureBean
    public MeasureBean findMeasureBeanInputType(int inputType) {
        MeasureBean measureBean = null;
        for (int i = 0; i < datas.size(); i++) {
            measureBean = datas.get(i).getData();
            if (measureBean.getInputType() == inputType) {
                return measureBean;
            }
        }
        return measureBean;
    }

    @Override
    protected void initData() {
        datas = getData();
        if (adapter == null) {
            adapter = new MultiAdapter<MultiBean<MeasureBean>>(datas) {
                @Override
                protected void convert(@NonNull BaseViewHolder helper, MultiBean<MeasureBean> item) {
                    switch (item.getItemType()) {
                        case MeasureBean.TYPE_COMMON://普通
                            switch (item.getData().getInputType()) {
                                case 0://体温
                                case 1://血氧
                                case 4://脉率
                                    helper.setGone(R.id.iv_over, false)
                                            .setGone(R.id.tv_over_tip, false)
                                            .setGone(R.id.tv_input, false)
                                            .setGone(R.id.tv_before, false)
                                            .setGone(R.id.tv_swith, false)
                                            .setGone(R.id.iv_switchover, false)
                                            .setText(R.id.tv_label, item.getData().getProject())
                                            .setText(R.id.tv_result, item.getData().getResult());
                                    break;
                                case 2://血压
                                    helper.setGone(R.id.iv_over, item.getData().getCurrentValue() > item.getData().getDefualtValue() ? true : false)
                                            .setGone(R.id.tv_over_tip, item.getData().getCurrentValue() > item.getData().getDefualtValue() ? true : false)
                                            .setGone(R.id.tv_input, false)
                                            .setGone(R.id.tv_before, false)
                                            .setGone(R.id.tv_swith, false)
                                            .setText(R.id.tv_label, item.getData().getProject())
                                            .setText(R.id.tv_result, item.getData().getCurrentValue() <= 0 && item.getData().getDefualtValue() <= 0 ? getString(R.string.no_data) : item.getData().getCurrentValue() + "/" + item.getData().getDefualtValue())
                                            .setTextColor(R.id.tv_result, item.getData().getCurrentValue() > item.getData().getDefualtValue() ? getResources().getColor(R.color.colorRed) : getResources().getColor(R.color.titleColor80));
                                    break;

                                case 3://血糖
                                    helper.setGone(R.id.iv_over, false)
                                            .setGone(R.id.tv_over_tip, false)
                                            .setGone(R.id.tv_input, false)
                                            .setGone(R.id.tv_before, true)
                                            .setGone(R.id.tv_swith, true)
                                            .setGone(R.id.iv_switchover, true)
                                            .setText(R.id.tv_label, item.getData().getProject())
                                            .setText(R.id.tv_result, item.getData().getResult());

                                    helper.getView(R.id.tv_before).setOnClickListener((v) -> {
                                        bloodSugarDialog.show();
                                        bloodSugarDialog.setDialogCallBack(new BloodSugarDialog.DialogCallBack() {
                                            @Override
                                            public void confirm(BloodTypeBean b) {
                                                if (b.getType() != 0) {
                                                    bloodTypeBean = b;
                                                    if (b.getType() % 2 == 0) {
                                                        item.getData().setAfter(false);
                                                    } else {
                                                        item.getData().setAfter(true);
                                                    }
                                                    helper.setText(R.id.tv_before, b.getTypeName());
                                                    helper.setGone(R.id.tv_swith, true)
                                                            .setGone(R.id.iv_switchover, true);
                                                    return;
                                                }
                                                bloodTypeBean.setType(0);
                                                item.getData().setAfter(false);
                                                helper.setText(R.id.tv_before, R.string.before);
                                            }
                                        });
                                    });

                                    helper.getView(R.id.tv_swith).setOnClickListener((v) -> {
                                        if (item.getData().isAfter()) {
                                            if (bloodTypeBean.getType() != 0) {
                                                bloodTypeBean.setType(bloodTypeBean.getType() + 1);
                                                String s = bloodMenus.get(bloodTypeBean.getType());
                                                helper.setText(R.id.tv_before, s);
                                                item.getData().setAfter(false);
                                            } else {
                                                helper.setText(R.id.tv_before, R.string.before);
                                                item.getData().setAfter(false);
                                            }
                                        } else {
                                            if (bloodTypeBean.getType() != 0) {
                                                bloodTypeBean.setType(bloodTypeBean.getType() - 1);
                                                String s = bloodMenus.get(bloodTypeBean.getType());
                                                item.getData().setAfter(true);
                                                helper.setText(R.id.tv_before, s);
                                            } else {
                                                helper.setText(R.id.tv_before, R.string.after);
                                                item.getData().setAfter(true);
                                            }

                                        }
                                    });
                                    break;

                                case 5://呼吸
                                case 6://小便
                                case 7://大便
                                    helper.setGone(R.id.iv_over, false)
                                            .setGone(R.id.tv_over_tip, false)
                                            .setGone(R.id.tv_input, true)
                                            .setGone(R.id.tv_before, false)
                                            .setGone(R.id.tv_swith, false)
                                            .setText(R.id.tv_label, item.getData().getProject())
                                            .setText(R.id.tv_result, item.getData().getResult());
                                    helper.getView(R.id.tv_input).setOnClickListener((v) -> {
                                        //TODO 弹出弹框提升用户手动输入，然后在保存数据
                                        switch (item.getData().getInputType()) {
                                            case 5:
                                                breatheDialog.show();
                                                breatheDialog.setDialogCallBack(new BreatheDialog.DialogCallBack() {
                                                    @Override
                                                    public void confirm(Integer breathe) {
                                                        Log.d(TAG, "confirm: " + breathe);
                                                        measureDataBean.setBreathe(breathe);
                                                        item.getData().setResult(String.valueOf(breathe));
                                                        int customBeanByMode = findCustomBeanByMode(item.getData().getInputType());
                                                        adapter.notifyItemChanged(customBeanByMode);
                                                    }
                                                });
                                                break;
                                            case 6:
                                                urinateDialog.show();
                                                urinateDialog.setDialogCallBack(new UrinateDialog.DialogCallBack() {
                                                    @Override
                                                    public void confirm(PatientMeasure bean) {
                                                        Log.d(TAG, "confirm: " + bean.toString());
                                                        if (bean.getPee_type() == 1) {
                                                            measureDataBean.setPee_type(1);
                                                            measureDataBean.setPee_no_conduit(bean.getPee_no_conduit());
                                                            measureDataBean.setPee_volume_no_conduit(bean.getPee_volume_no_conduit());
                                                            item.getData().setResult(bean.getPee_no_conduit() + "/" + df.format(bean.getPee_volume_no_conduit()) + "ml");
                                                        } else {
                                                            measureDataBean.setPee_type(2);
                                                            measureDataBean.setPee_volume_conduit(bean.getPee_volume_conduit());
                                                            item.getData().setResult(df.format(bean.getPee_volume_conduit()) + "ml");
                                                        }
                                                        int customBeanByMode = findCustomBeanByMode(item.getData().getInputType());
                                                        adapter.notifyItemChanged(customBeanByMode);
                                                    }
                                                });
                                                break;
                                            case 7:
                                                fecesDialog.show();
                                                fecesDialog.setDialogCallBack(new FecesDialog.DialogCallBack() {
                                                    @Override
                                                    public void confirm(PatientMeasure bean) {
                                                        Log.d(TAG, "confirm: " + bean.toString());
                                                        if (bean.getShit_type() == 1) {
                                                            measureDataBean.setShit_type(1);
                                                            measureDataBean.setShit_count(bean.getShit_count());
                                                            item.getData().setResult(String.valueOf(bean.getShit_count()));
                                                        } else {
                                                            measureDataBean.setShit_type(2);
                                                            measureDataBean.setShit_clyster_after(bean.getShit_clyster_after());
                                                            measureDataBean.setShit_clyster_after(bean.getShit_clyster_after());
                                                            item.getData().setResult(bean.getShit_clyster_after() + " " + bean.getShit_clyster_before() + "/" + bean.getClyster_count() + "E");
                                                        }
                                                        int customBeanByMode = findCustomBeanByMode(item.getData().getInputType());
                                                        adapter.notifyItemChanged(customBeanByMode);
                                                    }
                                                });
                                                break;
                                        }
                                    });
                                    break;
                            }
                            break;

                        case MeasureBean.TYPE_BOW: //血氧波
                            helper.setText(R.id.tv_label, item.getData().getProject());
                            helper.setTextColor(R.id.tv_label, getResources().getColor(R.color.black));
                            suitlines = helper.getView(R.id.suitlines);
                            suitlines.setXySize(9);
                            suitlines.setDefaultOneLineColor(new int[]{getResources().getColor(R.color.colorPrimary)});
                            suitlines.setShowYGrid(true);
                            suitlines.setLineForm(true);
                            suitlines.showX(false);
                            suitlines.showY(false);
                            List<Unit> lines1 = (List<Unit>) item.getData().getChartDatas();
                            //TODO 这里的数据后续需要从设备里面获取。
                            lines = new ArrayList<>();
                            lines.add(new Unit(10, ""));
                            lines.add(new Unit(3, ""));
                            lines.add(new Unit(3, ""));
                            lines.add(new Unit(4, ""));
                            lines.add(new Unit(10, ""));
                            lines.add(new Unit(8, ""));
                            lines.add(new Unit(5, ""));
                            lines.add(new Unit(4, ""));
                            lines.add(new Unit(3.6f, ""));
                            lines.add(new Unit(10f, ""));
                            lines.add(new Unit(4.8f, ""));
                            lines.add(new Unit(4.4f, ""));
                            lines.add(new Unit(3.2f, ""));
                            lines.add(new Unit(10f, ""));
                            suitlines.feedWithAnim(lines);
                            break;
                    }
                }

                @Override
                protected void addItemTypes() {
                    addItemType(MeasureBean.TYPE_COMMON, R.layout.multipe_measure_common);
                    addItemType(MeasureBean.TYPE_BOW, R.layout.multipe_measure_bow);
                }
            };
            final GridLayoutManager manager = new GridLayoutManager(this, 4);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return adapter.getItemViewType(position) == MeasureBean.TYPE_BOW ? manager.getSpanCount() : 1;
                }
            });
            rv_measure.setLayoutManager(manager);
            rv_measure.addItemDecoration(new GridSpacingItemDecoration(4, 4, true)); //这里的间距在布局里面做了处理了。
            rv_measure.setAdapter(adapter);
            adapter.openLoadAnimation();

            adapter.setOnItemClickListener((adapter, view, position) -> {
                Log.d(TAG, "setOnItemClickListener: " + position);
            });


        }
    }

    private List<MultiBean<MeasureBean>> getData() {
        List<MultiBean<MeasureBean>> data = new ArrayList<>();
        positionDatas = SP.getListData(Constant.CUSTOM_DELETE_DATA_KEY, CustomBean.class);//存储数据
        for (int i = 0; i < positionDatas.size(); i++) {
            Log.d(TAG, "positionDatas: " + positionDatas.get(i).getMode());
        }
        MeasureBean measureBean;
        if (positionDatas == null || positionDatas.size() <= 0) { //datas 按默认的顺序添加 数据
            //TODO datas 按默认的顺序添加 数据
            for (int i = 0; i < menus.size(); i++) {
                Log.d(TAG, "getData: " + menus.get(i));
                measureBean = new MeasureBean(menus.get(i), getString(R.string.no_data), i);
                if (i != menus.size() - 1) {
                    data.add(new MultiBean<>(measureBean, MeasureBean.TYPE_COMMON));
                } else {
                    data.add(new MultiBean<>(measureBean, MeasureBean.TYPE_BOW));//血氧波
                }
            }
        } else {//datas 根据position添加数据
            //这里是重新排序，因为血氧波，占了四个位置，如果在0-4之间的话，就把它放在第一个位置，然后他就占据第一行，
            int position = -1;
            for (int i = 0; i < positionDatas.size(); i++) {
                if (positionDatas.get(i).getMode() == 8) {
                    if (positionDatas.size() >= 3 && i <= 3) {
                        position = i;
                    }
                    if (positionDatas.size() >= 7 && (i > 3 && i <= 7)) {
                        position = i;
                    }
                    if (positionDatas.size() >= 8 && i >= 8) {
                        position = i;
                    }
                }
            }
            if (position != -1) {
                CustomBean bean;
                if (position <= 3) {
                    bean = positionDatas.remove(position);
                    positionDatas.add(0, bean);
                } else if (position > 3 && position <= 7) {
                    bean = positionDatas.remove(position);
                    positionDatas.add(4, bean);
                } else {
                    bean = positionDatas.remove(position);
                    positionDatas.add(8, bean);
                }
            }
            //这里是添加需要展示的数据
            int type;
            for (int i = 0; i < positionDatas.size(); i++) {
                type = positionDatas.get(i).getMode();
                Log.d(TAG, "getData: " + menus.get(type));
                measureBean = new MeasureBean(menus.get(type), getString(R.string.no_data), type);
                if (type != menus.size() - 1) {
                    data.add(new MultiBean<>(measureBean, MeasureBean.TYPE_COMMON));
                } else {
                    data.add(new MultiBean<>(measureBean, MeasureBean.TYPE_BOW));//血氧波
                }
            }
        }
        return data;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.st_reset:
                //TODO 重置
                showDailog(0.3f, getResources().getColor(R.color.colorWhite), getResources().getColor(R.color.titleColor80),
                        getString(R.string.sure_reset_measure_data), 18, getResources().getColor(R.color.titleColor),
                        getString(R.string.will_clear_current_measure_data), 12, getResources().getColor(R.color.titleColor),
                        getString(R.string.cancel), 16, getResources().getColor(R.color.colorRed),
                        getString(R.string.reset), 16, getResources().getColor(R.color.colorPrimary));
                break;

            case R.id.st_save:
                loadingDialog = new LoadingDialog(this, "正在保存");
                loadingDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "保存数据: "+measureDataBean.toString());

                        loadingDialog.dismiss();
                        toast(getString(R.string.save_sucess));
                    }

                }, 2000);
                break;
            case R.id.tv_select_patient:
                startActivityForResult(new Intent(MeasureActivity.this, SelectPatientActivity.class), REQUEST_CODE);
                //TODO 选择病人
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseDailog();
        EventBusActivityScope.getDefault(this).unregister(this);
    }


    /**
     * 订阅 CustomFragment 位置变更事件
     * TODO 后续需要重构整合设置自定义页面的bean和此页面的bean 为同一个bean
     *
     * @param event
     */
    @Subscribe
    public void onCustomChangEvent(CustomChangEvent event) {
        switch (event.type) {
            case 0://位置变更
                MultiBean<MeasureBean> bean = datas.remove(event.startPosition);
                adapter.notifyItemRemoved(event.startPosition);
                datas.add(event.changePositin, bean);
                adapter.notifyItemInserted(event.changePositin);
                break;

            case 1://删除了
                datas.remove(event.deleteOrAddPosition);
                adapter.notifyItemRemoved(event.startPosition);
                break;

            default://添加了
                positionDatas = SP.getListData(Constant.CUSTOM_DELETE_DATA_KEY, CustomBean.class);//获取需要显示的数据
                int type = positionDatas.get(positionDatas.size() - 1).getMode();
                MeasureBean measureBean = new MeasureBean(menus.get(type), getString(R.string.no_data), type);
                int insertPosition;
                if (type == 8) {//考虑排序问题
                    if (datas.size() < 3) {
                        insertPosition = 0;
                    } else if (datas.size() < 7 && datas.size() > 3) {
                        insertPosition = 4;
                    } else {
                        insertPosition = 8;
                    }
                    datas.add(insertPosition, new MultiBean<>(measureBean, MeasureBean.TYPE_BOW));
                    adapter.notifyItemInserted(insertPosition);
                } else {
                    datas.add(new MultiBean<>(measureBean, MeasureBean.TYPE_COMMON));
                    adapter.notifyItemInserted(datas.size() - 1);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && requestCode == REQUEST_CODE) {
            if (data == null)
                return;
            PatientsBean patientsBean = (PatientsBean) data.getSerializableExtra("bean");
            Log.d(TAG, "选择病人接收返回数据" + patientsBean.toString());
            tv_bedNO.setVisibility(View.VISIBLE);
            tv_name.setVisibility(View.VISIBLE);
            tv_ad.setVisibility(View.VISIBLE);

            tv_bedNO.setText(patientsBean.getBed_number() + "床");
            tv_name.setText(patientsBean.getUser_name() + "(成人)");
            tv_ad.setText("住院号：" + patientsBean.getId_number());

            tv_select_patient_tip.setVisibility(View.GONE);
        }
    }


    /**
     * 显示 dailog
     *
     * @param width        宽度
     * @param bgColor      背景色
     * @param pressBgColor 按下时的背景色
     * @param title        标题
     * @param titleSize    标题文本大小
     * @param titleColor   标题文本颜色
     * @param content      内容
     * @param contentSize  内容文本大小
     * @param contentColor 内容文本颜色
     * @param cancel       取消文本
     * @param cancelSize   取消文本大小
     * @param cancelColor  取消文本颜色
     * @param query        确认文本
     * @param querySize    确认文本大小
     * @param queryColor   确认文本颜色
     */
    private void showDailog(float width, int bgColor, int pressBgColor,
                            String title, int titleSize, int titleColor,
                            String content, int contentSize, int contentColor,
                            String cancel, int cancelSize, int cancelColor,
                            String query, int querySize, int queryColor) {//
        if (dialog == null) {
            dialog = new CircleDialog.Builder()
                    .setWidth(0.3f)
                    .setCanceledOnTouchOutside(false)
                    .setCancelable(false)
                    .configDialog(params -> {
                        params.backgroundColor = bgColor;
//                        params.backgroundColorPress = pressBgColor;
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
                    .setNegative(cancel, v -> {
                        releaseDailog();
                    })
                    .configPositive(params -> {
                        params.textColor = queryColor;
                        params.textSize = querySize;
                    })
                    .setPositive(query, v -> {
                        releaseDailog();
                        //TODO 重置
//                        datas.clear();
//                        adapter.notifyDataSetChanged();
                        int index = findCustomBeanByMode(2);
                        for (int i = 0; i < datas.size(); i++) {
                            if (i == index) {
                                datas.get(i).getData().setCurrentValue(0);
                                datas.get(i).getData().setDefualtValue(0);
                            } else {
                                datas.get(i).getData().setResult(getString(R.string.no_data));
                            }
                        }
                        adapter.notifyDataSetChanged();
                        toast("重置成功");
                    })
                    .show(getSupportFragmentManager());
        } else {
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }
    }

    /**
     * 释放dailog资源
     */
    private void releaseDailog() {
        if (dialog != null) {
            if (dialog.isVisible()) {
                dialog.dismiss();
            }
            dialog = null;
        }
    }


}
