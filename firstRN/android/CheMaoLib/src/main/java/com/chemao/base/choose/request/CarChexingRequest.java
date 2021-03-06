package com.chemao.base.choose.request;

import com.chemao.base.api.BaseRequest;
import com.chemao.base.api.ResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Describe: 获取汽车的品牌列表
 * @Author: zhangxb
 * @Date: 2015/10/10
 */
public class CarChexingRequest extends BaseRequest {
    public CarChexingRequest(String model_id, ResultCallback callback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("model_id",model_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        super.doRequest(jsonObject, callback);
    }


    @Override
    public String getPkgName() {
        return "pub_lib.car_attribute";
    }

    @Override
    public String getClsName() {
        return "CAR_CATEGORY_CHEXING_LIST";
    }
}
