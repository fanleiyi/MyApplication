package com.tarena.karen.local;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    EditText editText_No;
    TextView textView_Name;

    MyConnection connection=new MyConnection();

    IStudent istudent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUI();
        //绑定服务实现学生姓名的查询
        Intent intent=new Intent(this,StudentService.class);

        bindService(
                intent,
                connection,
                Service.BIND_AUTO_CREATE);
    }

    public  class MyConnection implements  ServiceConnection{

        @Override
        public void onServiceConnected(ComponentName componentName,
                              IBinder iBinder) {
            istudent=(IStudent)iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            istudent=null;
        }
    }

    private void initialUI() {
        editText_No= (EditText)
                findViewById(R.id.editText_No);
        textView_Name= (TextView)
                findViewById(R.id.textView_Name);
    }

    public void query(View view){
       String noStr=editText_No.getText().toString();
        int no=Integer.parseInt(noStr);
        String name=istudent.queryStudent(no);
        if(!TextUtils.isEmpty(name)){
            textView_Name.setText(name);
        }else{
            Toast.makeText(this,"该学号的学生不存在",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
        unbindService(connection);
    }
}
