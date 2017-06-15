package com.tarena.karen.employee.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pjy on 2017/6/1.
 */
public class StreamUtil {

    public static String createStr(
            InputStream is) throws IOException {
        BufferedReader reader=null;
        String jsonStr="";
        try{
            reader=new BufferedReader(
                    new InputStreamReader(
                    is,"utf-8"));
            String line="";
            StringBuilder builder=new StringBuilder();
            while((line=reader.readLine())!=null){
                builder.append(line);
            }
            jsonStr=builder.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if(reader!=null){
                reader.close();
            }
        }
        return jsonStr;
    }
}
