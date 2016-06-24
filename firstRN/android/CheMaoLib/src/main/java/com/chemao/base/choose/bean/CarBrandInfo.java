package com.chemao.base.choose.bean;

import java.util.List;

/**
 * @Describe: okhttp车辆品牌bean
 * @Author: zhangxb
 * @Date: 2015/10/10
 */
public class CarBrandInfo {

    /**
     * brands : [{"brand_id":"品牌id [require | int]","name":"品牌名称 [require | string]","brandid":"品牌id [require | int]","brand_py":"品牌拼音首字母 [require | string | fixed:1]"},{"brand_id":"品牌id [require | int]","name":"品牌名称 [require | string]","brandid":"品牌id [require | int]","brand_py":"品牌拼音首字母 [require | string | fixed:1]"}]
     */

    private List<BrandsEntity> brands;

    public void setBrands(List<BrandsEntity> brands) {
        this.brands = brands;
    }

    public List<BrandsEntity> getBrands() {
        return brands;
    }

    public static class BrandsEntity {
        /**
         * brand_id : 品牌id [require | int]
         * name : 品牌名称 [require | string]
         * brandid : 品牌id [require | int]
         * brand_py : 品牌拼音首字母 [require | string | fixed:1]
         */

        private String brand_id;
        private String name;
        private String brand_py;

        public void setBrand_id(String brand_id) {
            this.brand_id = brand_id;
        }

        public void setName(String name) {
            this.name = name;
        }


        public void setBrand_py(String brand_py) {
            this.brand_py = brand_py;
        }

        public String getBrand_id() {
            return brand_id;
        }

        public String getName() {
            return name;
        }

        public String getBrand_py() {
            return brand_py;
        }
    }

}
