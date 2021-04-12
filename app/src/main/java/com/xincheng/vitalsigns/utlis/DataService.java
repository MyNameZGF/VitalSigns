package com.xincheng.vitalsigns.utlis;

import com.xincheng.vitalsigns.bean.temp.TemperatureBean;
import com.xincheng.vitalsigns.bean.temp.TemperatureDateBean;
import com.xincheng.vitalsigns.bean.temp.TimeBean;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    public static final String NULL = " ";
    public static final String COLON = ": ";


    /**
     * 获取最近7天的日期
     *
     * @return
     */
    public static String[] get7nearbyDate(String date ,int position) {
        return DateUtils.get7Day(date,position);
    }

    /**
     * 获取最近7天的日期
     *
     * @return
     */
    public static String[] get7nearbyDate() {
        return DateUtils.get7Day(DateUtils.getCurrenDate());
    }




    /**
     * 获取体温单页面数据
     * @return
     * @param selectedPosition
     */
    public static List<TemperatureDateBean> getTemperatureDatas(int selectedPosition){
        List<TemperatureDateBean> datas = new ArrayList<>();
        TemperatureBean bean = new TemperatureBean();
        //String[] weeks = DateUtils.getCurrentDayPreWeek(DateUtils.getCurrenDate());
        String[] weeks = DateUtils.get7Day(DateUtils.getCurrenDate());
        String[][] titleDatas = new String[][]{
                {"日期", weeks[0], weeks[1], weeks[2], weeks[3], weeks[4], weeks[5], weeks[6]},
                {"术后日期", "3", "4", "5", "6", "7", "8", "9"},
                {"住院天数", "3", "4", "5", "6", "7", "8", "9"}
        };
        bean.setTitleDatas(titleDatas);

        TimeBean timeBean = new TimeBean();
        timeBean.setTitle("时间");
        timeBean.setAm("上午");
        timeBean.setAn("下午");

        List<String> amHours = new ArrayList<>();
        amHours.add("2");
        amHours.add("6");
        amHours.add("10");
        timeBean.setAmTitleDatas(amHours);

        List<String> anHours = new ArrayList<>();
        anHours.add("14");
        anHours.add("18");
        anHours.add("22");
        timeBean.setAnTitleDatas(anHours);

        List<List<Integer>> pluseDatas = new ArrayList<>();
        for(int j = 0;j<7;j++){
            List<Integer> dayDatas = new ArrayList<>();
            for(int i = 0;i<6;i++){
                dayDatas.add(170 - i * 5);
            }
            pluseDatas.add(dayDatas);
        }
        timeBean.setPluseValueDatas(pluseDatas);


        List<List<Float>> temeratureDatas = new ArrayList<>();
        for(int j = 0;j<7;j++){
            List<Float> dayDatas = new ArrayList<>();
            for(int i = 0;i<6;i++){
                dayDatas.add(37.0f - i * 0.2f);
            }
            temeratureDatas.add(dayDatas);
        }
        timeBean.setTemperatureValueDatas(temeratureDatas);

        bean.setTimeBean(timeBean);

        List<String> bottomTitles = new ArrayList<>();
        bottomTitles.add("呼吸(次/分)");
        bottomTitles.add("血压(mmHg)");
        bottomTitles.add("大便次数");
        bottomTitles.add("体重(kg)");
        bottomTitles.add("尿量(ml)");
        bottomTitles.add("输入液量(ml)");
        bottomTitles.add("排出液量(ml)");
        bottomTitles.add("术后天数");
        bean.setBottomTitles(bottomTitles);
        bean.setPosition(selectedPosition);
        datas.add(new TemperatureDateBean(TemperatureDateBean.TYPE_1,bean));
        datas.add(new TemperatureDateBean(TemperatureDateBean.TYPE_2,"护士签名:, 李小丽,护士长签名:, 高红"));
        return datas;
    }
}























































































