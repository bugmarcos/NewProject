package com.foodapp.foodnearme.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Harsh on 21-Feb-15.
 */
public class UserDataUtil {
    private static SharedPreferences pref;
    public static final String USER_ID = "userid";
    public static final String TOKEN = "access_token";
    public static final String DISP_NAME = "dname";
    public static final String PHONE_NUM = "phone";
    private static SharedPreferences getPreference(Context cont)
    {
        if(pref==null)
        {
            pref = cont.getSharedPreferences("UserData", Context.MODE_PRIVATE);

        }
        return pref;
    }



    public static void addToPreference(Context cont,Map<String,String> map)
    {
        SharedPreferences.Editor edit = getPreference(cont).edit();
        for(String key : map.keySet())
        {
            edit.putString(key,map.get(key));
        }
        edit.commit();

    }

    public static Map<String, ?> getAllData(Context cont)
    {
        return getPreference(cont).getAll();
    }
    public static void with(Context context)
    {
        getPreference(context);
    }
}
