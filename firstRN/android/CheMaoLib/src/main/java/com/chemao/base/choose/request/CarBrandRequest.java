package com.chemao.base.choose.request;

import com.chemao.base.api.BaseRequest;
import com.chemao.base.api.ResultCallback;

import org.json.JSONObject;

/**
 * @Describe: 获取汽车的品牌列表
 * @Author: zhangxb
 * @Date: 2015/10/10
 */
public class CarBrandRequest extends BaseRequest {
    public CarBrandRequest( ResultCallback callback) {
        JSONObject jsonObject = new JSONObject();
        super.doRequest(jsonObject, callback);
    }


    @Override
    public String getPkgName() {
        return "pub_lib.car_attribute";
    }

    @Override
    public String getClsName() {
        return "CAR_CATEGORY_BRAND_LIST";
    }
}
