package com.chemao.base.choose.bean;

import java.util.List;

/**
 * @Describe: okhttp 车型bean
 * @Author: zhangxb
 * @Date: 2015/10/10
 */
public class CarChexingInfo {

    /**
     * chexings : [{"chexing_id":"车型id [require | int]","name":"车系名称 [require | string]","effluent":"排放标准 [require | int | list:1-国1, 2-国2, 3-国3, 4-国4, 5-国5, 6-国3带OBD, 7-国4京5]","fuel":"燃料类型 [require | int | list:1-汽油, 2-柴油, 3-天然气, 4-电动, 5-混合]","displa":"车型排量 [require | float]","gearbox":"车型变速箱类型 [require | string]","newcarprice":"指导价格(单位：万元) [require | double]","cartype":"车辆类型 [require | string]","year":"年款 [require | int]","seat":"座位数 [require | int]"},{"chexing_id":"车型id [require | int]","name":"车系名称 [require | string]","effluent":"排放标准 [require | int | list:1-国1, 2-国2, 3-国3, 4-国4, 5-国5, 6-国3带OBD, 7-国4京5]","fuel":"燃料类型 [require | int | list:1-汽油, 2-柴油, 3-天然气, 4-电动, 5-混合]","displa":"车型排量 [require | float]","gearbox":"车型变速箱类型 [require | string]","newcarprice":"指导价格(单位：万元) [require | double]","cartype":"车辆类型 [require | string]","year":"年款 [require | int]","seat":"座位数 [require | int]"}]
     */

    private List<ChexingsEntity> chexings;

    public void setChexings(List<ChexingsEntity> chexings) {
        this.chexings = chexings;
    }

    public List<ChexingsEntity> getChexings() {
        return chexings;
    }

    public static class ChexingsEntity {
        /**
         * chexing_id : 车型id [require | int]
         * name : 车系名称 [require | string]
         * effluent : 排放标准 [require | int | list:1-国1, 2-国2, 3-国3, 4-国4, 5-国5, 6-国3带OBD, 7-国4京5]
         * fuel : 燃料类型 [require | int | list:1-汽油, 2-柴油, 3-天然气, 4-电动, 5-混合]
         * displa : 车型排量 [require | float]
         * gearbox : 车型变速箱类型 [require | string]
         * newcarprice : 指导价格(单位：万元) [require | double]
         * cartype : 车辆类型 [require | string]
         * year : 年款 [require | int]
         * seat : 座位数 [require | int]
         */

        private String chexing_id;
        private String name;
        private String effluent;
        private String fuel;
        private String displa;
        private String gearbox;
        private String newcarprice;
        private String cartype;
        private String year;
        private String seat;

        public void setChexing_id(String chexing_id) {
            this.chexing_id = chexing_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setEffluent(String effluent) {
            this.effluent = effluent;
        }

        public void setFuel(String fuel) {
            this.fuel = fuel;
        }

        public void setDispla(String displa) {
            this.displa = displa;
        }

        public void setGearbox(String gearbox) {
            this.gearbox = gearbox;
        }

        public void setNewcarprice(String newcarprice) {
            this.newcarprice = newcarprice;
        }

        public void setCartype(String cartype) {
            this.cartype = cartype;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public void setSeat(String seat) {
            this.seat = seat;
        }

        public String getChexing_id() {
            return chexing_id;
        }

        public String getName() {
            return name;
        }

        public String getEffluent() {
            return effluent;
        }

        public String getFuel() {
            return fuel;
        }

        public String getDispla() {
            return displa;
        }

        public String getGearbox() {
            return gearbox;
        }

        public String getNewcarprice() {
            return newcarprice;
        }

        public String getCartype() {
            return cartype;
        }

        public String getYear() {
            return year;
        }

        public String getSeat() {
            return seat;
        }
    }
}
