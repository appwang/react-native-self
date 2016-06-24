package com.chemao.base.api;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * User: LiYajun
 * Date: 2015-09-30
 * Describe: OkHttp接口请求函数
 */
public class OkHttpClientManager {
    private static final String TAG = "OkHttpClientManager";
    private static OkHttpClientManager mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;
    private Gson mGson;
    private PostDelegate mPostDelegate = new PostDelegate();
    private BaseApiConfig mApiConfig;
    private String curTime = "";

    private OkHttpClientManager() {
        mOkHttpClient = new OkHttpClient();
        mOkHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));
        mOkHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setWriteTimeout(60, TimeUnit.SECONDS);
        mOkHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        mDelivery = new Handler(Looper.getMainLooper());
        mGson = new Gson();
    }

    public static OkHttpClientManager getInstance() {
        if (mInstance == null) {
            synchronized (OkHttpClientManager.class) {
                if (mInstance == null) {
                    mInstance = new OkHttpClientManager();
                }
            }
        }
        return mInstance;
    }

    public void init(BaseApiConfig apiConfig) {
        mApiConfig = apiConfig;
    }

    public static void postAsyn(String pkgname, String clsname, JSONObject jsonObject, final ResultCallback callback) {
        getInstance().mPostDelegate.postAsyn(pkgname, clsname, jsonObject, callback, null, true);
    }

    public static void postAsyn(String pkgname, String clsname, JSONObject jsonObject, final ResultCallback callback, boolean hasCommParam) {
        getInstance().mPostDelegate.postAsyn(pkgname, clsname, jsonObject, callback, null, hasCommParam);
    }

    public static void postAsyn(String pkgname, String clsname, JSONObject jsonObject, final ResultCallback callback, Object tag, boolean hasCommParam) {
        getInstance().mPostDelegate.postAsyn(pkgname, clsname, jsonObject, callback, tag, hasCommParam);
    }

    private void deliveryResult(ResultCallback callback, final Request request) {
        final ResultCallback resCallBack = callback;
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                mDelivery.post(new Runnable() {
                    @Override
                    public void run() {
                        resCallBack.onError(request, e);
                    }
                });
            }

            @Override
            public void onResponse(final Response response) {
                try {
                    String string = response.body().string();
                    if (string != null || !"".equals(string)) {
                        if (mApiConfig.isDebug()) {
                            Log.d("chemaolog", "------接口返回结果 = " + string);
                        }
                        final JSONObject resObj = new JSONObject(string);
                        if (resObj.has("status")) {
                            JSONObject statueObj = resObj.getJSONObject("status");
                            String code = "";
                            String msg = "";
                            String runtime = "";
                            if (statueObj.has("code")) {
                                code = statueObj.getString("code");
                            }
                            if (statueObj.has("msg")) {
                                msg = statueObj.getString("msg");
                            }
                            if (statueObj.has("runtime")) {
                                runtime = statueObj.getString("runtime");
                            }

                            if (code.equals("00000")) {
                                if (resObj.has("result")) {
                                    final String result = resObj.getString("result");
                                    if (resCallBack.mType == String.class) {
                                        mDelivery.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                resCallBack.onResult(result);
                                            }
                                        });
                                    } else {
                                        mDelivery.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                Object obj = mGson.fromJson(result, resCallBack.mType);
                                                resCallBack.onResult(obj);
                                            }
                                        });
                                    }
                                }
                            } else {
                                final String tempCode = code;
                                final String tempMsg = msg;

                                if("951".equals(tempCode)){
                                    mApiConfig.forceLogin();
                                }

                                if (resObj.has("result")) {
                                    final String result = resObj.getString("result");
                                    mDelivery.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            resCallBack.onStatus(result, tempCode, tempMsg);
                                        }
                                    });
                                }
                            }
                        }
                    } else {

                        mDelivery.post(new Runnable() {
                            @Override
                            public void run() {
                                resCallBack.onError(response.request(), new NullPointerException("response empty."));
                            }
                        });
                    }
                } catch (final IOException e) {
                    e.printStackTrace();
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            resCallBack.onError(response.request(), e);
                        }
                    });
                } catch (final JSONException e) {
                    e.printStackTrace();
                    mDelivery.post(new Runnable() {
                        @Override
                        public void run() {
                            resCallBack.onError(response.request(), e);
                        }
                    });
                }
            }
        });
    }


    public static void cancelTag(Object tag) {
        getInstance().mOkHttpClient.cancel(tag);
    }

    public class PostDelegate {
        private final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream;charset=utf-8");
        private final String Content_Type = "application/json; charset=utf-8";

        public void postAsyn(String pkgname, String clsname, JSONObject jsonObject, final ResultCallback callback, Object tag, boolean hasCommParam) {
            curTime = String.valueOf(System.currentTimeMillis() / 1000);
            String checksum = getChecksumString(pkgname, clsname);
            if(null == jsonObject){
                jsonObject = new JSONObject();
            }
            try {
                jsonObject.put("package", pkgname);
                jsonObject.put("class", clsname);

                //add common param (not necessary)
                HashMap<String, String> commonParamMap = mApiConfig.getCommonParamMap();
                if (hasCommParam && commonParamMap != null && commonParamMap.size() != 0) {
                    for (HashMap.Entry<String, String> entry : commonParamMap.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                }

                //add common param (necessary)
                HashMap<String, String> commonNecessaryParamMap = mApiConfig.getNecessaryParamMap();
                if (commonNecessaryParamMap != null && commonNecessaryParamMap.size() != 0) {
                    for (HashMap.Entry<String, String> entry : commonNecessaryParamMap.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                }



                if (!"".equals(checksum)) {
                    jsonObject.put("checksum", checksum);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String bodyStr = jsonObject.toString();
            if (mApiConfig.isDebug()) {
                Log.d("chemaolog", "-------请求参数 = " + bodyStr);
            }
            Request request = buildPostRequest(mApiConfig.getUrl(), bodyStr, tag);
            deliveryResult(callback, request);
        }

        public String getChecksumString(String pkgName, String clsName) {
            if ("".equals(pkgName) || "".equals(pkgName)) {
                if (mApiConfig.isDebug()) {
                    Log.w("chemaolog", "------错误：未传入package或者class-----------");
                }
            } else {
                String packageKeyValue = mApiConfig.getPackageKeyValue(pkgName);
                if (packageKeyValue != null) {
                    return EncryptUtil.md5(curTime + pkgName + clsName + packageKeyValue);
                } else {
                    if (mApiConfig.isDebug()) {
                        Log.d("chemaolog", "-----未找到接口对应的package_key-----------");
                    }
                }
            }
            return "";
        }

        /**
         * post构造Request的方法
         *
         * @param url
         * @param bodyStr
         * @return
         */
        private Request buildPostRequest(String url, String bodyStr, Object tag) {
            RequestBody body = RequestBody.create(MEDIA_TYPE_STREAM, bodyStr);
            Request.Builder builder = new Request.Builder()
                    .url(url)
                    .post(body);
            if (tag != null) {
                builder.tag(tag);
            }
            String random = getRandomChar();
            builder.header("Random", random);
            builder.header("UTC-Timestemp", curTime);
            builder.header("Signature", EncryptUtil.sha1(bodyStr + curTime + random + mApiConfig.getPUBKEY()));
            builder.header("Content-Type", Content_Type);
            builder.header("User-Agent", mApiConfig.getUserAgent());
            Request request = builder.build();
            return request;
        }

        private String getRandomChar() {
            char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            Random random = new Random();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < 8; i++) {
                buffer.append(chr[random.nextInt(62)]);
            }
            return buffer.toString();
        }

    }

}

