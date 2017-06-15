package com.example.day03_01;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private TextInputEditText phoneText;
    private TextInputEditText pwdText;
    private TextInputLayout pwdLayout;
    private TextInputLayout phoneLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获得TextInputLayout对象（此对象内部只能有一个EditText）
        phoneLayout = (TextInputLayout) findViewById(R.id.phoneLayoutId);
        pwdLayout = (TextInputLayout) findViewById(R.id.pwdLayoutId);
        // 获得TextInputEditText对象（此对象继承EditText）
        phoneText = (TextInputEditText) findViewById(R.id.phoneTextId);
        pwdText = (TextInputEditText) findViewById(R.id.pwdTextId);
    }
    public void onClick(View v) {
        String phone = phoneText.getText().toString();
        String pwd = pwdText.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            // 显示错误
            phoneLayout.setErrorEnabled(true);
            // 设置错误内容
            phoneLayout.setError("phone can not be null");

        }else {
            // 不显示错误
            phoneLayout.setErrorEnabled(false);
        }
        if (TextUtils.isEmpty(pwd)) {
            pwdLayout.setErrorEnabled(true);
            pwdLayout.setError("password can not be null");

        }else {
            pwdLayout.setErrorEnabled(false);
        }

    }

}
