package com.xincheng.vitalsigns.bean;


import java.util.Date;

/**
 * 患者监测数据
 */
public class PatientMeasure {

    private long id;
    private String id_number;//住院号关联病人
    private String inspectorId;//检测人工号
    private String inspectorName;//检测人名称
    private int status;//状态 0=同步错误，1=已同步
    private Date check_time;//测量时间
    private Float temperature;//温度
    private Float blood_oxygen;//血氧
    private String pressure;//血压
    private Float glucose;//血糖
    private Integer glucose_type;//血糖类型
    private Float pulse_rate;//脉率
    private Integer breathe;//呼吸次数
    private Integer pee_type;//小便类型
    private Integer pee_no_conduit;//无导管次数
    private Float pee_volume_no_conduit;//无导管毫升
    private Float pee_volume_conduit;//有导管毫升
    private Integer shit_type;//大便类型
    private Integer shit_count;//大便次数
    private Integer shit_clyster_before;//灌肠前次数
    private Integer shit_clyster_after;//灌肠后次数
    private Integer clyster_count;//灌肠次数
    private String bowData;//血氧波


    public PatientMeasure(){

    }

    @Override
    public String toString() {
        return "PatientMeasure{" +
                "id=" + id +
                ", id_number='" + id_number + '\'' +
                ", inspectorId='" + inspectorId + '\'' +
                ", inspectorName='" + inspectorName + '\'' +
                ", status=" + status +
                ", check_time=" + check_time +
                ", temperature=" + temperature +
                ", blood_oxygen=" + blood_oxygen +
                ", pressure='" + pressure + '\'' +
                ", glucose=" + glucose +
                ", glucose_type=" + glucose_type +
                ", pulse_rate=" + pulse_rate +
                ", breathe=" + breathe +
                ", pee_type=" + pee_type +
                ", pee_no_conduit=" + pee_no_conduit +
                ", pee_volume_no_conduit=" + pee_volume_no_conduit +
                ", pee_volume_conduit=" + pee_volume_conduit +
                ", shit_type=" + shit_type +
                ", shit_count=" + shit_count +
                ", shit_clyster_before=" + shit_clyster_before +
                ", shit_clyster_after=" + shit_clyster_after +
                ", clyster_count=" + clyster_count +
                ", bowData='" + bowData + '\'' +
                '}';
    }

    public PatientMeasure(long id, String id_number, String inspectorId, String inspectorName, int status, Date check_time, Float temperature, Float blood_oxygen, String pressure, Float glucose, Integer glucose_type, Float pulse_rate, Integer breathe, Integer pee_type, Integer pee_no_conduit, Float pee_volume_no_conduit, Float pee_volume_conduit, Integer shit_type, Integer shit_count, Integer shit_clyster_before, Integer shit_clyster_after, Integer clyster_count, String bowData) {
        this.id = id;
        this.id_number = id_number;
        this.inspectorId = inspectorId;
        this.inspectorName = inspectorName;
        this.status = status;
        this.check_time = check_time;
        this.temperature = temperature;
        this.blood_oxygen = blood_oxygen;
        this.pressure = pressure;
        this.glucose = glucose;
        this.glucose_type = glucose_type;
        this.pulse_rate = pulse_rate;
        this.breathe = breathe;
        this.pee_type = pee_type;
        this.pee_no_conduit = pee_no_conduit;
        this.pee_volume_no_conduit = pee_volume_no_conduit;
        this.pee_volume_conduit = pee_volume_conduit;
        this.shit_type = shit_type;
        this.shit_count = shit_count;
        this.shit_clyster_before = shit_clyster_before;
        this.shit_clyster_after = shit_clyster_after;
        this.clyster_count = clyster_count;
        this.bowData = bowData;
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

    public String getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(String inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getInspectorName() {
        return inspectorName;
    }

    public void setInspectorName(String inspectorName) {
        this.inspectorName = inspectorName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCheck_time() {
        return check_time;
    }

    public void setCheck_time(Date check_time) {
        this.check_time = check_time;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(Float blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public Float getGlucose() {
        return glucose;
    }

    public void setGlucose(Float glucose) {
        this.glucose = glucose;
    }

    public Integer getGlucose_type() {
        return glucose_type;
    }

    public void setGlucose_type(Integer glucose_type) {
        this.glucose_type = glucose_type;
    }

    public Float getPulse_rate() {
        return pulse_rate;
    }

    public void setPulse_rate(Float pulse_rate) {
        this.pulse_rate = pulse_rate;
    }

    public Integer getBreathe() {
        return breathe;
    }

    public void setBreathe(Integer breathe) {
        this.breathe = breathe;
    }

    public Integer getPee_type() {
        return pee_type;
    }

    public void setPee_type(Integer pee_type) {
        this.pee_type = pee_type;
    }

    public Integer getPee_no_conduit() {
        return pee_no_conduit;
    }

    public void setPee_no_conduit(Integer pee_no_conduit) {
        this.pee_no_conduit = pee_no_conduit;
    }

    public Float getPee_volume_no_conduit() {
        return pee_volume_no_conduit;
    }

    public void setPee_volume_no_conduit(Float pee_volume_no_conduit) {
        this.pee_volume_no_conduit = pee_volume_no_conduit;
    }

    public Float getPee_volume_conduit() {
        return pee_volume_conduit;
    }

    public void setPee_volume_conduit(Float pee_volume_conduit) {
        this.pee_volume_conduit = pee_volume_conduit;
    }

    public Integer getShit_type() {
        return shit_type;
    }

    public void setShit_type(Integer shit_type) {
        this.shit_type = shit_type;
    }

    public Integer getShit_count() {
        return shit_count;
    }

    public void setShit_count(Integer shit_count) {
        this.shit_count = shit_count;
    }

    public Integer getShit_clyster_before() {
        return shit_clyster_before;
    }

    public void setShit_clyster_before(Integer shit_clyster_before) {
        this.shit_clyster_before = shit_clyster_before;
    }

    public Integer getShit_clyster_after() {
        return shit_clyster_after;
    }

    public void setShit_clyster_after(Integer shit_clyster_after) {
        this.shit_clyster_after = shit_clyster_after;
    }

    public Integer getClyster_count() {
        return clyster_count;
    }

    public void setClyster_count(Integer clyster_count) {
        this.clyster_count = clyster_count;
    }

    public String getBowData() {
        return bowData;
    }

    public void setBowData(String bowData) {
        this.bowData = bowData;
    }
}
