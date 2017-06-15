package com.tarena.karen.ann;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.editText_UserName)
    EditText editText_UserName;
    @BindView(R.id.editText_Password)
    EditText editText_Password;
    @BindView(R.id.button_Login)
    Button button_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_Login)
    public void login(){
        //获得用户名
        String userName=editText_UserName.getText().toString();
        String password=editText_Password.getText().toString();

        Toast.makeText(this,
                "用户名:"+userName+"密码:"+password,
                Toast.LENGTH_LONG).show();
    }

}
