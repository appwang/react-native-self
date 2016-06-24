package com.chemao.base.api;

import org.json.JSONObject;

/**
 * User: LiYajun
 * Date: 2015-10-08
 * Describe:请求基类
 */

public abstract class BaseRequest {
    public void doRequest(JSONObject jsonObject, ResultCallback callback, boolean hasCommParam) {
        OkHttpClientManager.postAsyn(getPkgName(), getClsName(), jsonObject, callback, hasCommParam);
    }

    public void doRequest(JSONObject jsonObject, ResultCallback callback) {
        OkHttpClientManager.postAsyn(getPkgName(), getClsName(), jsonObject, callback, true);
    }

    public abstract String getPkgName();

    public abstract String getClsName();

}
