package com.firstrn;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;


import java.util.HashMap;

/**
 * User: LiYajun
 * Date: 2015-11-02
 * Describe: 新接口api配置类
 */

public class CheShangApiConfig extends BaseApiConfig {


    private Context mContext;

    public CheShangApiConfig(Context context) {
        mContext = context;
    }

    @Override
    public boolean isDebug() {

            return true;

    }

    @Override
    public String getUserAgent() {
        return "cheshang/";
    }

    @Override
    public String getUrl() {


            return "http://testfapis.365eche.net/";


    }

    @Override
    public String getPUBKEY() {

            return "c8b697c75a0050c4d8513bf1b73be184";

    }

    /**
     * 接口密钥
     */
    @Override
    public HashMap<String, String> getPackageKeyMap() {
        HashMap<String, String> pkgKeyMap = new HashMap<>();

            pkgKeyMap.put("app_client.cheshang", "3bd679036267a732750921fe1904e003");
            pkgKeyMap.put("trade_system", "d654c277ed1f325243dcec6e655364a6");
            return pkgKeyMap;

    }

    /**
     * 公共非必需参数
     * @return
     */
    @Override
    public HashMap<String, String> getCommonParamMap() {
        HashMap<String, String> commParaMap = new HashMap<>();

            commParaMap.put("identity", "1");
            return commParaMap;


    }

    /**
     * 公共必需参数
     * @return
     */
    @Override
    public HashMap<String, String> getNecessaryParamMap() {
        HashMap<String, String> commParaMap = new HashMap<>();
        String token = CommCacheUtil.getCacheFapisLoginUser(mContext);
        if (token.equals("0")) {
            commParaMap.put("token", token);
            return commParaMap;
        }
        return null;
    }

    /**
     * token失效，强制登录
     */
    @Override
    public void forceLogin() {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(mContext);
        Intent intent = new Intent();
        intent.setAction("com.chemao.chehsang.tokenrunout");
        lbm.sendBroadcast(intent);
    }
}
