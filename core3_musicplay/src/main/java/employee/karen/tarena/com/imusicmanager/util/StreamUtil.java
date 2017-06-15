package employee.karen.tarena.com.imusicmanager.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pjy on 2017/6/2.
 */

public class StreamUtil {
    public static String createStr(InputStream is){
        String jsonStr="";
        BufferedReader reader=null;
        try{
            reader=new BufferedReader(
                    new InputStreamReader(is,"utf-8"));
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
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return  jsonStr;
    }
    public static byte[] createBytes(InputStream is){
        ByteArrayOutputStream os=null;
        byte[] datas=null;
        try{
            //流的目标是内存数组
            os=new ByteArrayOutputStream();
            byte[] buffer=new byte[1024];
            int len=0;
            while((len=is.read(buffer))!=-1){
               os.write(buffer,0,len);
            }
            os.flush();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        datas=os.toByteArray();
        return datas;
    }
}
