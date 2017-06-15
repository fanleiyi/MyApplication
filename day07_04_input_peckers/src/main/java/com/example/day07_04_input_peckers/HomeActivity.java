package com.example.day07_04_input_peckers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


    }

    public void onClick(View v) {
        //使用此方法启动的activity，在activity关闭时可以获得回传数据
        startActivityForResult(new Intent(this,MainActivity.class),100);
    }

    /**
     * 通过此方法接收回传数据
     * @param requestCode 表示用户的一个请求的唯一标识
     * @param resultCode 表示响应的唯一标识
     * @param data 回传的意图对象（此对象封装了数据）
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&&resultCode==200) {
            //从intent中获得我们需要的数据
            String result = data.getStringExtra("dateTextKey");
            //将此数据更新到button上
            Button btn = (Button) findViewById(R.id.Btn);
            btn.setText(result);
        }
    }
}
