package com.tarena.karen.client;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tarena.karen.remote.IStudent;

public class MainActivity extends AppCompatActivity {
    EditText editText_No;
    TextView textView_Name;
    MyConnection con = new MyConnection();
    IStudent istudent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialUI();
        //绑定方式起启服务
        Intent intent = new Intent();
        intent.setAction("com.tarena.karen.remote.QUERY_STUDENT");
        intent.setPackage("com.tarena.karen.remote");
        bindService(intent, con, Service.BIND_AUTO_CREATE);
    }

    private class MyConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("TAG:", "已连接");
            istudent = IStudent.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("TAG:", "已断开");
            istudent=null;
        }
    }
    private void initialUI() {
        editText_No = (EditText) findViewById(R.id.editText_No);
        textView_Name = (TextView) findViewById(R.id.textView_Name);
    }

    public void query(View view) {
        try {
            String strNo = editText_No.getText().toString();
            int no = Integer.parseInt(strNo);
            String name = istudent.queryStudent(no);
            if (!TextUtils.isEmpty(name)) {
                textView_Name.setText(name);
            } else {
                textView_Name.setText("学生不存在");
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(con);
    }
}
