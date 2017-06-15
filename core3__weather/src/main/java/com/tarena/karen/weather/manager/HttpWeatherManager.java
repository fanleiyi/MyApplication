package com.tarena.karen.weather.manager;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tarena.karen.weather.constant.IURL;
import com.tarena.karen.weather.entity.MyWeather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by pjy on 2017/6/12.
 */

public class HttpWeatherManager {
    public static RequestQueue queue=null;

    /**
     * 使用Volley框架+Gson网络天气数据的加载
     * @param context
     * @param cityName 城市名称
     * @param weatherListener 通知天气加载完成的接口
     */
    public static void loadWeather(
            Context context,
            String cityName,
            final WeatherLoadListener weatherListener){
        try {
            cityName= URLEncoder.encode(cityName,"utf8");
            String url= IURL.ROOT+"cityname="+cityName+"&key="+IURL.APPKEY;
            String url2=IURL.ROOT2;
            if(queue==null){
                queue= Volley.newRequestQueue(context);
            }
            StringRequest request=
                    new StringRequest(url2, new Response.Listener<String>() {
                        @Override
                        public void onResponse(
                                String response) {
                            //创建Gson对象
                            Gson gson=new Gson();
                            MyWeather myWeather=gson.fromJson(response,MyWeather.class);
                            //使用回调接口机制把数据通知给activity
                            weatherListener.onWeatherLoadEnd(myWeather);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("TAG:","volley error");
                        }
                    });
            //把请求对象加载对列中
            queue.add(request);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public interface WeatherLoadListener{
        public void onWeatherLoadEnd(MyWeather myWeather);
    }

}
