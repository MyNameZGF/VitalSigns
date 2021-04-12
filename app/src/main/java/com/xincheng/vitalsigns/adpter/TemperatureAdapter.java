package com.xincheng.vitalsigns.adpter;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xincheng.vitalsigns.R;
import com.xincheng.vitalsigns.bean.temp.TemperatureDateBean;
import com.xincheng.vitalsigns.ui.view.TemperatureChartView;

import java.util.List;

public class TemperatureAdapter extends BaseMultiItemQuickAdapter<TemperatureDateBean, BaseViewHolder> {
    public static final int TYPE_1 = 1;
    public static final int TYPE_2 = 2;

    public TemperatureAdapter(List<TemperatureDateBean> data) {
        super(data);
        addItemType(TYPE_1, R.layout.multiple_temperature_chart);
        addItemType(TYPE_2, R.layout.multiple_footer_doctor_and_nurse_signatrue);
    }

    @Override
    protected void convert(BaseViewHolder helper, TemperatureDateBean item) {
        switch (helper.getItemViewType()) {
            case TYPE_1:
                //TODO 替换成json格式的数据
                TemperatureChartView temperatureChartView = helper.getView(R.id.temperatureChartView);
                temperatureChartView.setData(item.getBean());
                break;

            case TYPE_2:
                //TODO 替换成json格式的数据
                if (item.getContent().contains(",")) {
                    String[] names = item.getContent().split(",");
                    if (names != null && names.length == 4) {
                        helper.setText(R.id.tv_nurse_signature, names[0])
                                .setText(R.id.tv_nurse_name, names[1])
                                .setText(R.id.tv_doctor_signature, names[2])
                                .setText(R.id.tv_doctor_name, names[3]);
                    }
                }
                break;
        }
    }
}