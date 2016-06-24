package com.chemao.base.choose.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ProvinceInfo implements Serializable {
    private ArrayList<ProvincesEntity> provinces;

    public ArrayList<ProvincesEntity> getProvinces() {
        return provinces;
    }

    public void setProvinces(ArrayList<ProvincesEntity> provinces) {
        this.provinces = provinces;
    }

    public static class ProvincesEntity {
        private String id;
        private String name;
        private int addressFlag;
        private String provincePy;
        private CityInfo cityInfo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public CityInfo getCityInfo() {
            return cityInfo;
        }

        public void setCityInfo(CityInfo cityInfo) {
            this.cityInfo = cityInfo;
        }

        public int getAddressFlag() {
            return addressFlag;
        }

        public void setAddressFlag(int addressFlag) {
            this.addressFlag = addressFlag;
        }

        public String getProvincePy() {
            return provincePy;
        }

        public void setProvincePy(String provincePy) {
            this.provincePy = provincePy;
        }
    }

}
