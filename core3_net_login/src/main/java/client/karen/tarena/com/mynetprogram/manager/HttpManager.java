package client.karen.tarena.com.mynetprogram.manager;

import android.icu.util.Output;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import client.karen.tarena.com.mynetprogram.entity.User;

/**
 * Created by pjy on 2017/5/31.
 */

public class HttpManager {

    private static User resultUser;

    /**
     * 实现用户登录的处理
     * @param username 登录的用户名
     * @param password 登录的密码
     * @return
     */
    public static boolean loginHttpPost(
            String username,
            String password){
        boolean flag=false;

        String path="http://192.168.199.186:8080/" +
                "myweb/LoginServlet";
        BufferedReader reader=null;
        try{
            URL url=new URL(path);
            //获得连接对象
            HttpURLConnection connection=
                    (HttpURLConnection)
                    url.openConnection();
            //设置请求的参数和请求的属性
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //拼接提交数据的字符串
            Map<String,String> params=
                    new HashMap<String,String>();
            params.put("username",username);
            params.put("password",password);

            StringBuilder builder=
                    new StringBuilder();
            //username=xxxx&password=xxxx
            for (Map.Entry<String,String> entry:
                    params.entrySet()){
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
            byte[] datas=builder.deleteCharAt(
                    builder.length()-1).
                    toString().getBytes();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(datas.length));

            //创建客户端和服务器的连接
            connection.connect();

            //获得指向服务器端的输出流
            OutputStream os=connection.getOutputStream();
            os.write(datas);
            os.flush();

            //获得服务器端响应的状态码
            int statusCode=
                    connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                reader=new BufferedReader(
                        new InputStreamReader(is,"utf-8"));
                String line="";
                StringBuilder sb=new StringBuilder();
                while((line=reader.readLine())!=null){
                    sb.append(line);
                }
                //把返回的数据包装成一个json对象
                JSONObject jsonObj=
                        new JSONObject(sb.toString());
                String result=jsonObj.getString("result");
                if("ok".equals(result)){
                    flag=true;
                }
            }else{
                Log.i("TAG:code",statusCode+"");
            }


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
        return flag;
    }

    /**
     * 实现用户注册
     * @param user
     * @return
     */
    public static User registHttpPost(
            User user){
        //注册成功{"result":"ok","user":{"username":"xxxx","password":"xxx","email":"xxxx","phone":"xxxx"}}
        //注册失败{"result":"error","msg":"xxxxxxx"}
        User resultUser=null;
        String path="http://192.168.199.186:8080/myweb/RegistServlet";
        BufferedReader reader=null;
        try{
            URL url=new URL(path);
            HttpURLConnection connection=
                    (HttpURLConnection)
                            url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            //username=xxx&password=xxx&email=xxx&phone=xxx
            Map<String,String> params=new HashMap<String,String>();
            params.put("username",user.getUsername());
            params.put("password",user.getPassword());
            params.put("email",user.getEmail());
            params.put("phone",user.getPhone());
            StringBuilder builder=new StringBuilder();
            for(Map.Entry<String,String> entry:params.entrySet()){
                builder.append(entry.getKey());
                builder.append("=");
                builder.append(entry.getValue());
                builder.append("&");
            }
            byte[] datas=builder.deleteCharAt(builder.length()-1).toString().getBytes();

            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",String.valueOf(datas.length));

            connection.connect();

            OutputStream os=connection.getOutputStream();
            os.write(datas);
            os.flush();

            int statusCode=connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                reader=new BufferedReader(new InputStreamReader(is,"utf-8"));
                String line="";
                StringBuilder sb=new StringBuilder();
                while((line=reader.readLine())!=null){
                    sb.append(line);
                }
                JSONObject jsonObj=new JSONObject(sb.toString());
                String result=jsonObj.getString("result");
                if("ok".equals(result)){

                    JSONObject jsonUser=jsonObj.getJSONObject("user");
                    String uname=jsonUser.getString("username");
                    String pwd=jsonUser.getString("password");
                    String em=jsonUser.getString("email");
                    String p=jsonUser.getString("phone");
                    //添加到对象
                    resultUser=new User(uname,pwd,em,p);
                }else{
                    String msg=jsonObj.getString("msg");
                    Log.i("TAG:",msg);
                }
            }else{
                Log.i("TAG:code",statusCode+"");
            }
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
        return  resultUser;
    }
}
