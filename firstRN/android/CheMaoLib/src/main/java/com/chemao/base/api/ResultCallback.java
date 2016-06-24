package com.chemao.base.api;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * User: LiYajun
 * Date: 2015-09-30
 * Describe: 请求返回回调函数
 */

public abstract class ResultCallback<T> {
    Type mType;

    public ResultCallback() {
        mType = getSuperclassTypeParameter(getClass());
    }

    Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    public abstract void onError(Request request, Exception e);

    public abstract void onResult(T response);

    public abstract void onStatus(String result, String code, String msg);
}
