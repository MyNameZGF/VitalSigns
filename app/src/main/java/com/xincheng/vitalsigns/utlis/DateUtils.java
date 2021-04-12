package com.xincheng.vitalsigns.utlis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    /**
     * 获取到秒的当前时间
     * @return
     */
    public static String getCurrenTime() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * 获取到当前日期
     * @return
     */
    public static String getCurrenDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }



    /**
     * long 转 String
     * @param date
     * @return
     */
    public static String long2String(long date){
        Date d = new Date(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        return dateFormat.format(d);
    }

    /**
     * long 转 String
     * @param date
     * @return
     */
    public static String dDong2String(long date){
        Date d = new Date(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(d);
    }

    /**
     * long 转 String
     * @param date
     * @return
     */
    public static String mLong2String(long date){
        Date d = new Date(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        return dateFormat.format(d);
    }



    /**
     * 这里暂时有问题，小时那里无缘无故多了
     * long 转 String
     * @param date
     * @return
     */
    public static String hourLong2String(long date){
        Date d = new Date(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(d);
    }




    /**
     * String 转long
     * @param date
     * @return
     */
    public static long DateString2Long(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm:ss");
        try {
            Date d = dateFormat.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * String 转long
     * @param date
     * @return
     */
    public static long DateMinuteString2Long(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm");
        try {
            Date d = dateFormat.parse(date);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }



    /**
     * 获取到分钟的当前时间
     * @return
     */
    public static String getCurrenTimeForMinute() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd : HH:mm");
        return dateFormat.format(date);
    }

    /**
     * 获取到分钟的当前时间
     * @return
     */
    public static String getCurrenTimeForhm() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    /**
     * 日期转星期
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天.
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     *
     * @param selectedDate 选中的日期
     * @param postition    选中日期的位置
     * @return
     */
    public static String[] get7Day(String selectedDate,int postition){
        String[] week = new String[7];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return week;
        }
        Date itemDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for(int i = 0;i<7;i++){//TODO 这里要根据position来获取数据
            if(i == 0){
                calendar.add(Calendar.DATE, -postition);
            }else{
                calendar.add(Calendar.DATE, postition == 0 ? -1 : 1);
            }
            itemDate = calendar.getTime();
            week[i] = dateFormat.format(itemDate);
        }
        return week;
    }


    /**
     *
     * @param selectedDate 选中的日期
     * @return
     */
    public static String[] get7Day(String selectedDate){
        String[] week = new String[7];
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateFormat.parse(selectedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return week;
        }
        Date itemDate;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        for(int i = 0;i<7;i++){
            if(i == 0){
                calendar.add(Calendar.DATE, 0);
            }else{
                calendar.add(Calendar.DATE, -1);
            }
            itemDate = calendar.getTime();
            week[i] = dateFormat.format(itemDate);
        }
        return week;
    }


    /**
     * 获取当前日期的前6天，包括当前日期
     * @param mydata
     * @return
     */
    public static String[] getCurrentDayPreWeek(String mydata) {
        String[] date = new String[7];
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            Calendar cal = Calendar.getInstance();
            Date time = sdf.parse(mydata);
            cal.setTime(time);

            //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            //获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, (cal.getFirstDayOfWeek() - day + dayWeek -7 - (7- dayWeek) -1));
            date[6] = sdf.format(cal.getTime());
            for (int i = 5; i >= 0; i--) {
                cal.add(Calendar.DATE, 1);
                date[i] = sdf.format(cal.getTime());
            }

        } catch (Exception e) {
            e.printStackTrace();

            throw new RuntimeException(e);
        }

        return date;
    }


    /**
     * 获取当前周的所有日期
     * @param n -1代表上一周 +1代表下一周
     * @param mydata
     * @return
     */
    public static String[] getweek(int n, String mydata) {

        String[] date = new String[7];
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setFirstDayOfWeek(Calendar.MONDAY);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            Calendar cal = Calendar.getInstance();
            Date time = sdf.parse(mydata);
            cal.setTime(time);

            //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
            int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
            if (1 == dayWeek) {
                cal.add(Calendar.DAY_OF_MONTH, -1);
            }
            //设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
            cal.setFirstDayOfWeek(Calendar.MONDAY);
            //获得当前日期是一个星期的第几天
            int day = cal.get(Calendar.DAY_OF_WEEK);
            //根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
            cal.add(Calendar.DATE, (cal.getFirstDayOfWeek() - day + 7 * n + dayWeek));
            date[0] = sdf.format(cal.getTime());
            for (int i = 1; i < 7; i++) {
                cal.add(Calendar.DATE, 1);
                date[i] = sdf.format(cal.getTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return date;
    }
}
