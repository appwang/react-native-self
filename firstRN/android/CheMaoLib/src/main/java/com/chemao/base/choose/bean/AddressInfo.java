package com.chemao.base.choose.bean;

import java.io.Serializable;

public class AddressInfo implements Serializable {
    /**
     * 城市ID
     */
    private String cityId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 省份ID
     */
    private String ProvinceId;
    /**
     * 省份名称
     */
    private String ProvinceName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getProvinceName() {
        return ProvinceName;
    }

    public void setProvinceName(String provinceName) {
        ProvinceName = provinceName;
    }

    //为防止调用报空指针，默认赋值
    public AddressInfo() {
        cityId = "";
        cityName = "";
        ProvinceId = "";
        ProvinceName = "";
    }

    @Override
    public String toString() {
        return "AddressInfo{" +
                "cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", ProvinceId='" + ProvinceId + '\'' +
                ", ProvinceName='" + ProvinceName + '\'' +
                '}';
    }
}
