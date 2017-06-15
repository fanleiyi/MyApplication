package client.karen.tarena.com.mynetprogram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import client.karen.tarena.com.mynetprogram.manager.HttpManager;

public class MainActivity extends AppCompatActivity {

    EditText edit_Name=null;
    EditText edit_Password=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUI();

    }

    private void initialUI() {
        edit_Name= (EditText) findViewById(R.id.editText_UName);
        edit_Password= (EditText) findViewById(R.id.editText_PWD);
    }

    public void onClick(View v){
        startActivity(new Intent(MainActivity.this,RegistActivity.class));
    }



    public void login(View view){
        final String username=edit_Name.getText().toString();
        final String password=edit_Password.getText().toString();
        //发送登录的请求
        new Thread(new Runnable() {
            @Override
            public void run() {
               final boolean flag= HttpManager.loginHttpPost(username,password);
                //从工作线程中回到UI主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(flag){
                            Toast.makeText(MainActivity.this,
                                    "登录成功",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(MainActivity.this,
                                    "登录失败!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }
}
