package com.tarena.karen.employee.manager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.tarena.karen.employee.constant.IURL;
import com.tarena.karen.employee.entity.Employee;
import com.tarena.karen.employee.entity.User;
import com.tarena.karen.employee.util.ParamsUtil;
import com.tarena.karen.employee.util.StreamUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CheckedOutputStream;

/**
 * Created by pjy on 2017/6/1.
 */

public class HttpManager {
    //1.先封装一个用户实体类
    //2.定义一个实现体用户注册的方法
    static String SESSIONID="";
    public static boolean
        registHttpPost(User user){
        boolean flag=false;

        try{
            URL url=new URL(IURL.REGIST_URL);
            HttpURLConnection connection=
                    (HttpURLConnection)
                    url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            //拼接提交数据的字符串
            Map<String,String> params=
                    new HashMap<String,String>();
            params.put("loginname",user.getLoginName());
            params.put("password",user.getPassword());
            params.put("realname",user.getRealName());
            params.put("email",user.getEmail());
            //根据拼接的字符串获得提交的数据
            byte[] datas= ParamsUtil.
                    createParams(params).
                    getBytes();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(datas.length));
            connection.connect();

            //获得指向服务器的数据输出流向服务器端提交数据
            OutputStream os=connection.getOutputStream();
            os.write(datas);
            os.flush();

            //获得服务器端响应的状态码
            int statusCode=connection.
                    getResponseCode();
             if(statusCode==200){
                //获得服务器端的响应结果
                InputStream is= connection.getInputStream();
                //将输入流转换成字符串
                String jsonStr=
                        StreamUtil.createStr(is);
                 JSONObject jsonObject=
                         new JSONObject(jsonStr);
                 String result=jsonObject.getString("result");
                 if("ok".equals(result)){
                     flag=true;
                 }else{
                     String msg=jsonObject.getString("msg");
                     Log.i("TAG:msg",msg);
                 }
             }else {
                 Log.i("TAG:statusCode",""+statusCode);
             }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }

    public static Bitmap loadCodeHttpGet(){
       Bitmap codeBitmap=null;
        try{
            URL url=new URL(IURL.CODE_URL);
            HttpURLConnection connection=
                    (HttpURLConnection)
                            url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            connection.connect();
            int statusCode=connection.
                    getResponseCode();
            if(statusCode==200){
                SESSIONID=connection.getHeaderField("Set-Cookie").split(";")[0];
                InputStream is=connection.getInputStream();
                codeBitmap=BitmapFactory.decodeStream(is);
                Log.i("TAG:SESSIONID",SESSIONID);
            }else{
                Log.i("TAG:statusCode",""+statusCode);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return codeBitmap;
    }

    public static boolean loginHttpPost(
            String loginName,
            String password,
            String code){
        boolean flag=false;
        try{
            URL url=new URL(IURL.LOGIN_URL);
            HttpURLConnection connection= (HttpURLConnection)
                    url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //设置请求头信息
            connection.setRequestProperty("Cookie",SESSIONID);


            //拼接提交的参数的字符串
            Map<String,String> params=
                    new HashMap<String,String>();
            params.put("loginname",loginName);
            params.put("password",password);
            params.put("code",code);
            byte[] datas=ParamsUtil.createParams(params).getBytes();
            connection.setRequestProperty(
                    "Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty(
                    "Content-Length",
                    String.valueOf(datas.length));
            connection.connect();

            //向服务器端提交数据
            OutputStream os=connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();

            //获得响应的状态码
            int statusCode=connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                String jsonStr=StreamUtil.createStr(is);
                JSONObject jsonObject=new JSONObject(jsonStr);
                String result=jsonObject.getString("result");
                if("ok".equals(result)){
                    flag=true;
                }else{
                    String msg=jsonObject.getString("msg");
                    Log.i("TAG:msg",msg);
                }
            }else{
                Log.i("TAG:statusCode",statusCode+"");
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }

    /**
     * 实现员工添加的网络http的访问
     * @param employee
     * @return
     */
    public static boolean
        addEmployeeHttpPost(
            Employee employee){
        boolean flag=false;
        try{
            URL url=new URL(IURL.ADDEMP_URL);
            HttpURLConnection connection=
                    (HttpURLConnection)
                    url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Map<String,String> params=
                    new HashMap<String,String>();
            params.put("name",employee.getName());
            params.put("salary",String.valueOf(employee.getSalary()));
            params.put("age",String.valueOf(employee.getAge()));
            params.put("gender",employee.getGender());

            byte[] datas=ParamsUtil.createParams(params).getBytes();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length",
                    String.valueOf(datas.length));
            connection.connect();

            OutputStream os=connection.getOutputStream();
            os.write(datas);
            os.flush();
            os.close();

            int statusCode=connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                String jsonStr=StreamUtil.createStr(is);
                JSONObject jsonObject=new JSONObject(jsonStr);
                String result=jsonObject.getString("result");
                if("ok".equals(result)){
                    flag=true;
                }else{
                    String msg=jsonObject.getString("msg");
                    Log.i("TAG:msg",msg);
                }
            }else{
                Log.i("TAG:code",statusCode+"");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }
    public static List<Employee>
        queryEmployeesHttpGet(){
        List<Employee> employees=
                new ArrayList<Employee>();
        try{
            URL url=new URL(IURL.EMPLOYEELIST_URL);
            HttpURLConnection connection=
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            connection.connect();
            int statusCode=connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                String jsonStr=StreamUtil.createStr(is);

                JSONObject jsonObject=new JSONObject(jsonStr);
                String result=jsonObject.getString("result");
                if("ok".equals(result)){
                    JSONArray array=jsonObject.
                            getJSONArray("data");
                    for(int i=0;i<array.length();i++){
                        Employee employee=new Employee();
                        JSONObject jsonEmp=array.getJSONObject(i);

                        int id=jsonEmp.getInt("id");
                        String name=jsonEmp.getString("name");
                        String gender=jsonEmp.getString("gender");
                        double salary=jsonEmp.getDouble("salary");
                        int age=jsonEmp.getInt("age");

                        employee.setId(id);
                        employee.setAge(age);
                        employee.setName(name);
                        employee.setSalary(salary);
                        employee.setGender(gender);

                        employees.add(employee);

                    }
                }else{
                    String msg=jsonObject.getString("msg");
                    Log.i("TAG:msg",msg);
                }
            }else{
                Log.i("TAG:code",""+statusCode);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return employees;
    }
}
