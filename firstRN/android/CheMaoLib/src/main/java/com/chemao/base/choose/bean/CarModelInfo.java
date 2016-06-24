package com.chemao.base.choose.bean;

import java.util.List;

/**
 * @Describe: okhttp 车系bean
 * @Author: zhangxb
 * @Date: 2015/10/10
 */
public class CarModelInfo {

    /**
     * models : [{"model_id":"车系id [require | int]","name":"车系名称 [require | string]","alias":"车系别名 [require | string]","code":"车系别名拼音首字母 [require | string]","ptype":"生产类型 [require | int | list:1-国产,2-进口]"},{"model_id":"车系id [require | int]","name":"车系名称 [require | string]","alias":"车系别名 [require | string]","code":"车系别名拼音首字母 [require | string]","ptype":"生产类型 [require | int | list:1-国产,2-进口]"}]
     */

    private List<ModelsEntity> models;

    public void setModels(List<ModelsEntity> models) {
        this.models = models;
    }

    public List<ModelsEntity> getModels() {
        return models;
    }

    public static class ModelsEntity {
        /**
         * model_id : 车系id [require | int]
         * name : 车系名称 [require | string]
         * alias : 车系别名 [require | string]
         * code : 车系别名拼音首字母 [require | string]
         * ptype : 生产类型 [require | int | list:1-国产,2-进口]
         */

        private String model_id;
        private String name;
        private String alias;
        private String code;
        private String ptype;

        public void setModel_id(String model_id) {
            this.model_id = model_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getModel_id() {
            return model_id;
        }

        public String getName() {
            return name;
        }

        public String getAlias() {
            return alias;
        }

        public String getCode() {
            return code;
        }

        public String getPtype() {
            return ptype;
        }
    }
}
