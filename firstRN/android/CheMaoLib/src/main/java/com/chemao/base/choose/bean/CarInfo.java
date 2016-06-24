package com.chemao.base.choose.bean;

import java.io.Serializable;

/**
 * @Describe: 车辆信息bean
 * @Author: zhangxb
 * @Date: 2015/10/15
 */
public class CarInfo implements Serializable{
    /**
     * 品牌id
     */
    private String brand_id;
    /**
     * 品牌名称
     */
    private String brand_name;

    /**
     * 车系id
     */
    private String model_id;
    /**
     * 车系名称
     */
    private String model_name;
    /**
     * 车型id
     */
    private String chexing_id;
    /**
     * 车型名称
     */
    private String chexing_name;
    /**
     * 年款
     */
    private String year;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getModel_id() {
        return model_id;
    }

    public void setModel_id(String model_id) {
        this.model_id = model_id;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getChexing_id() {
        return chexing_id;
    }

    public void setChexing_id(String chexing_id) {
        this.chexing_id = chexing_id;
    }

    public String getChexing_name() {
        return chexing_name;
    }

    public void setChexing_name(String chexing_name) {
        this.chexing_name = chexing_name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "CarInfo{" +
                "brand_id='" + brand_id + '\'' +
                ", brand_name='" + brand_name + '\'' +
                ", model_id='" + model_id + '\'' +
                ", model_name='" + model_name + '\'' +
                ", chexing_id='" + chexing_id + '\'' +
                ", chexing_name='" + chexing_name + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
