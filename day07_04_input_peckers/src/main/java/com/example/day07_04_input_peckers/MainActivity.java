package com.example.day07_04_input_peckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得一个日历对象
        DatePicker datePicker = (DatePicker) findViewById(R.id.dataPickerId);
        //通过日历对象获得当前年月日
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        final int monthOfyear = c.get(Calendar.MONTH);
        final int dayOfmonth = c.get(Calendar.DAY_OF_MONTH);
        //初始化datepicker对象的当前日志
        datePicker.init(year,monthOfyear,dayOfmonth, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(MainActivity.this,year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日",Toast.LENGTH_SHORT).show();
                //构建intent封装响应数据
                Intent intent = new Intent();
                intent.putExtra("dateTextKey",year+"年"+(monthOfYear+1)+"月"+dayOfMonth+"日");
                // 设置响应结果
                setResult(200,intent);
                // 关闭当前页面
                finish();
            }
        });
    }

}
