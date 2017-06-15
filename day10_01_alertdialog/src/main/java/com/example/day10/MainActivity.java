package com.example.day10;

import android.app.DatePickerDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    CharSequence[] items = {"站票","硬座","硬卧","软卧",};
    private DatePickerDialog dp;
    private Button button;
    private NotificationManager nManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }
    public void onClick(final View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle("请选择您的座位")
                .setSingleChoiceItems(items,0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CharSequence item = items[which];
                        TextView textView = (TextView) findViewById(R.id.et_1);
                        textView.setText(item);
                        dialog.cancel();
                    }
                });
        builder.show();
    }
    public void onClick02(View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int week = c.get(Calendar.DAY_OF_WEEK);
        dp = new DatePickerDialog(MainActivity.this,this, year, month, day);
        dp.show();

    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String[] weeks = {"星期天","星期一","星期二","星期三","星期四","星期五","星期六",};
        TextView textView = (TextView) findViewById(R.id.et_2);
        textView.setText(year+"年"+(1+month)+"月"+dayOfMonth+"日");
    }


    public void onClick03(View v) {
        // 1 构建通知
        Notification ntf = new Notification.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)// 小图标
                .setContentTitle("tarena")// 标题
                .setContentText("helloworld")// 内容
                .build();
        // 2 发送通知
        nManager.notify(1,// id，通知对象唯一标识
                ntf);
    }
}