package com.firstrn;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

/**
 * Created by cousin on 16/6/17.
 */
public class CommCacheUtil {

    public static void cacheFapisLoginUser(String token, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("configuration", 0);
        SharedPreferences.Editor  editor  =  sharedPreferences.edit();
        editor.putString("token",token);
        editor.commit();
    }

    public static String getCacheFapisLoginUser(Context context) {
        SharedPreferences  sharedPreferences = context.getSharedPreferences("configuration", 0);
        String token = sharedPreferences.getString("token","0");
        return token;
    }

}
