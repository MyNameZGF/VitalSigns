package com.xincheng.vitalsigns.bean;


import java.io.Serializable;
import java.util.Date;

//患者数据，模拟假数据
public class PatientsBean implements Serializable {

    public PatientsBean() {

    }
    private long id;
    private String id_number;//住院号
    private String user_name;//患者名称
    private String dept_code;//患者所在科室code
    private String check_time;//测量时间
    private String bed_number;//病床
    private Integer status;//状态0=未同步，1=已同步，2=同步失败
    private Integer patient_age;//患者年龄
    private Integer patient_sex;//患者性别 0=女，1=男
    private String go_hospital_time;//入院时间
    private Integer check_count;//当前监测次数
    private Integer need_check_count;//需要监测次数
    private Integer check_status;//监测状态0=未监测，1=今日以监测
    private String guardian;//检测人
    private String check_guardian_id;//检测人id


    public PatientsBean(long id, String id_number, String user_name, String dept_code, String check_time, String bed_number, Integer status, Integer patient_age, Integer patient_sex, String go_hospital_time, Integer check_count, Integer need_check_count, Integer check_status, String guardian, String check_guardian_id) {
        this.id = id;
        this.id_number = id_number;
        this.user_name = user_name;
        this.dept_code = dept_code;
        this.check_time = check_time;
        this.bed_number = bed_number;
        this.status = status;
        this.patient_age = patient_age;
        this.patient_sex = patient_sex;
        this.go_hospital_time = go_hospital_time;
        this.check_count = check_count;
        this.need_check_count = need_check_count;
        this.check_status = check_status;
        this.guardian = guardian;
        this.check_guardian_id = check_guardian_id;
    }


    @Override
    public String toString() {
        return "PatientsBean{" +
                "id=" + id +
                ", id_number='" + id_number + '\'' +
                ", user_name='" + user_name + '\'' +
                ", dept_code='" + dept_code + '\'' +
                ", check_time='" + check_time + '\'' +
                ", bed_number='" + bed_number + '\'' +
                ", status=" + status +
                ", patient_age=" + patient_age +
                ", patient_sex=" + patient_sex +
                ", go_hospital_time='" + go_hospital_time + '\'' +
                ", check_count=" + check_count +
                ", need_check_count=" + need_check_count +
                ", check_status=" + check_status +
                ", guardian='" + guardian + '\'' +
                ", check_guardian_id='" + check_guardian_id + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDept_code() {
        return dept_code;
    }

    public void setDept_code(String dept_code) {
        this.dept_code = dept_code;
    }

    public String getCheck_time() {
        return check_time;
    }

    public void setCheck_time(String check_time) {
        this.check_time = check_time;
    }

    public String getBed_number() {
        return bed_number;
    }

    public void setBed_number(String bed_number) {
        this.bed_number = bed_number;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPatient_age() {
        return patient_age;
    }

    public void setPatient_age(Integer patient_age) {
        this.patient_age = patient_age;
    }

    public Integer getPatient_sex() {
        return patient_sex;
    }

    public void setPatient_sex(Integer patient_sex) {
        this.patient_sex = patient_sex;
    }

    public String getGo_hospital_time() {
        return go_hospital_time;
    }

    public void setGo_hospital_time(String go_hospital_time) {
        this.go_hospital_time = go_hospital_time;
    }

    public Integer getCheck_count() {
        return check_count;
    }

    public void setCheck_count(Integer check_count) {
        this.check_count = check_count;
    }

    public Integer getNeed_check_count() {
        return need_check_count;
    }

    public void setNeed_check_count(Integer need_check_count) {
        this.need_check_count = need_check_count;
    }

    public Integer getCheck_status() {
        return check_status;
    }

    public void setCheck_status(Integer check_status) {
        this.check_status = check_status;
    }

    public String getGuardian() {
        return guardian;
    }

    public void setGuardian(String guardian) {
        this.guardian = guardian;
    }

    public String getCheck_guardian_id() {
        return check_guardian_id;
    }

    public void setCheck_guardian_id(String check_guardian_id) {
        this.check_guardian_id = check_guardian_id;
    }
}
