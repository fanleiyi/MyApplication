package com.example.day06_03_zhihu.util;

import java.util.Map;

/**
 * Created by pjy on 2017/6/1.
 */

public class ParamsUtil {

    public static String createParams(Map<String,String> params){
        StringBuilder builder=new StringBuilder();
        for (Map.Entry<String,String> entry:params.entrySet()){
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append("&");
        }
        String str=builder.deleteCharAt(builder.length()-1).toString();
        return str;
    }

}
