package com.chemao.base.choose.bean;

import java.io.Serializable;
import java.util.List;

public class CityInfo implements Serializable {
    private static final long serialVersionUID = -8697544995935716529L;
    private List<CitysEntity> citys;

    public List<CitysEntity> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysEntity> citys) {
        this.citys = citys;
    }

    public static class CitysEntity {
        private String id;
        private String name;

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
    }
}
