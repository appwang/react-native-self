package com.chemao.base.api;

import java.util.HashMap;

/**
 * User: LiYajun
 * Date: 2015-10-08
 * Describe: API接口参数配置类
 */

public abstract class BaseApiConfig {
    public abstract boolean isDebug();

    public abstract String getUserAgent();

    public abstract String getUrl();

    public abstract String getPUBKEY();

    public String getPackageKeyValue(String packageKey) {
        String[] strings = packageKey.split("\\.");
        for (int i = 0, leni = strings.length; i < leni; i++) {
            StringBuffer mapKey = new StringBuffer();
            for (int j = 0, lenj = leni - i; j < lenj; j++) {
                mapKey.append(strings[j]);
                if (j != lenj - 1) {
                    mapKey.append(".");
                }
            }
            String packageValue = getPackageKeyMap().get(mapKey.toString());
            if (packageValue != null && !packageValue.equals("")) {
                return packageValue;
            }
        }
        return null;
    }

    public abstract HashMap<String, String> getPackageKeyMap();

    public abstract HashMap<String, String> getCommonParamMap();

    public abstract HashMap<String, String> getNecessaryParamMap();

    public abstract void forceLogin();
}
