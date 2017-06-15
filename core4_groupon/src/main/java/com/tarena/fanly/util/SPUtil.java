package com.tarena.fanly.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tarena.fanly.constant.Constant;

/**
 * 对偏好设置文件的操作
 *
 * 1.Context的getSharedPreferences(文件名，模式);
 * 2.Activity的getPreference(模式);获取以preference_activity名的偏好设置文件
 * 3.PreferenceManager的getDefaultSharedPreferences(Context)获取以preference_包名的偏好设置文件
 *   模式 Context.MODE_PRIVATE
 *
 * Created by tarena on 2017/6/15.
 */

public class SPUtil {

   public static SharedPreferences sp;

    // 通过构造方法重载，以不同的方式获得片偏好设置文件
    public SPUtil(Context context,String name){
        sp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }
    public SPUtil(Context context){
        sp = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static boolean isFirst(){
        return sp.getBoolean(Constant.FIRST,true);
    }

    public static void setFirst(boolean flag){
        // 存到偏好设置
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(Constant.FIRST,flag);
        editor.commit();
    }

}
