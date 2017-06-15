package com.example.day06_03_zhihu.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.day06_03_zhihu.R;
import com.example.day06_03_zhihu.entity.User;
import com.example.day06_03_zhihu.manager.HttpManager;

public class RegistActivity extends AppCompatActivity {
    EditText editText_LoginName;
    EditText editText_Password;
    EditText editText_RealName;
    EditText editText_Email;
    EditText editText_OkPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        //初始化UI控件
        initialUI();
    }
    private void initialUI() {
        editText_LoginName=
                (EditText)
                        findViewById(R.id.editText_LoginName);

        editText_Password=
                (EditText)
                        findViewById(R.id.editText_Password);

        editText_OkPassword= (EditText)
                findViewById(R.id.editText_OkPassword);

        editText_Email=
                (EditText) findViewById(
                        R.id.editText_Email);

        editText_RealName= (EditText)
                findViewById(R.id.editText_RealName);
    }

    public void register(View view){
        String loginName=editText_LoginName.
                getText().toString();
        String password=editText_Password.
                getText().toString();
        String okPassword=editText_OkPassword.
                getText().toString();
        String realName=editText_RealName.
                getText().toString();
        String email=editText_Email.
                getText().toString();

        if(TextUtils.isEmpty(loginName)||
                TextUtils.isEmpty(password)||
                TextUtils.isEmpty(realName)||
                TextUtils.isEmpty(email)
                ){
            Toast.makeText(RegistActivity.this,
                    "请填写完整的注册信息",
                    Toast.LENGTH_LONG).show();
            return;
        }
        //如果密码和确认密码不一致则返回方法调用
        if(!password.equals(okPassword)){
            Toast.makeText(RegistActivity.this,
                    "密码和确认密码不一致",
                    Toast.LENGTH_LONG).show();
            return;
        }
        //把提交的注册用户信息注册成一个用户对象
        User user=new User(
                -1,
                loginName,
                password,
                realName,email);
        //发送注册请求
        asyncRegister(user);


    }

    private void asyncRegister(final User user) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag= HttpManager.registHttpPost(user);
                //将返回结果发送回UI主线程
                //从消息池中获得一个消息对象
                Message msg=handler.obtainMessage();
                msg.obj=flag;
                handler.sendMessage(msg);
            }
        }).start();

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Boolean flag= (Boolean) msg.obj;
            if(flag){
                Toast.makeText(RegistActivity.this,
                        "注册成功",
                        Toast.LENGTH_LONG).show();
                //清空输入框内容
                clearContent();

            }else{
                Toast.makeText(RegistActivity.this,
                        "注册失败",
                        Toast.LENGTH_LONG).show();
            }
        }
    };
    public void clearContent(){
        editText_LoginName.setText("");
        editText_Password.setText("");
        editText_OkPassword.setText("");
        editText_RealName.setText("");
        editText_Email.setText("");
    }
    public void esc(View v){
        finish();
    }
}


