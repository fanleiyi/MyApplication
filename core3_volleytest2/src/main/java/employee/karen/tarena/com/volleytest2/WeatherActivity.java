package employee.karen.tarena.com.volleytest2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import employee.karen.tarena.com.volleytest2.entity.WeatherBean;
import employee.karen.tarena.com.volleytest2.request.WeatherRequest;

public class WeatherActivity extends AppCompatActivity {

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        queue= Volley.newRequestQueue(this);
    }
    public void loadWeather(View view){
        String url="";
        WeatherRequest request=new WeatherRequest(url, new Response.Listener<WeatherBean>() {
            @Override
            public void onResponse(WeatherBean weatherBean) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        queue.add(request);
    }
}
