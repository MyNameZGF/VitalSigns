package com.xincheng.vitalsigns.bean;

public class HitsPatientMeasure {
    private String id_number;//住院号关联病人
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
}
