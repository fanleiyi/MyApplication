package com.example.day01_layout_mplayer;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogonActivity extends AppCompatActivity {

    private EditText phoneEt;
    private ImageView phoneEndImgView;// 如何快速提取局部变量为实例变量?(快捷键?)
    private EditText pwdEt;//ctrl+alt+f
    private CheckBox pwdImgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logon);

        setPhoneViews();
        setPwdViews();
        setLogonBtnView();
    }

    private void setPwdViews() {
        pwdEt = (EditText) findViewById(R.id.pwdTextId);
        pwdImgView = (CheckBox) findViewById(R.id.pwdImgEndId);
        pwdImgView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                pwdEt.setInputType(isChecked ? 0x91 : 0x81);
            }
        });
        // 初始化edittext上的软键盘监听事件
        pwdEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    logon();
                }
                return true;// 事件已执行
            }
        });
    }

    private void setPhoneViews() {
        phoneEt = (EditText) findViewById(R.id.phoneTextId);
        phoneEndImgView = (ImageView) findViewById(R.id.phoneImgEndId);
        phoneEt.addTextChangedListener(new BaseTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()>0) {
                    phoneEndImgView.setVisibility(View.VISIBLE);
                }else {
                    phoneEndImgView.setVisibility(View.GONE);
                }
            }
        });
        phoneEndImgView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                phoneEt.setText("");
            }
        });
        SharedPreferences sp = getSharedPreferences("user",Context.MODE_PRIVATE);
        String phone = sp.getString("phoneKey","");
        if(!TextUtils.isEmpty(phone)) {
            phoneEt.setText(phone);
        }
    }
    /**click logon button */
    public void setLogonBtnView() {
        Button viewById = (Button) findViewById(R.id.logonId);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logon();
            }
        });
    }
    public void logon() {
        //1.获取手机号密码
        String phone=phoneEt.getText().toString();
        String pwd=pwdEt.getText().toString();
        //2.验证数据的有效性验证
        if(TextUtils.isEmpty(phone)){
            phoneEt.setError("phone can not be null");
            return;
        }
        if(!TextUtils.isDigitsOnly(phone)){
            phoneEt.setError("phone must be number");
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            pwdEt.setError("phone can not be null");
            return;
        }
        //3对数据进行业务验证(例如此用户是否存在，密码是否正确)，暂且省略

        //4.根据选择记住手机号
        CheckBox cBox= (CheckBox) findViewById(R.id.checkId);

        if(cBox.isChecked()){
            //记住手机号(偏好设置)
            SharedPreferences sp=getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor et=sp.edit();
            et.putString("phoneKey",phone);
            et.commit();
        }

        //提示登录OK或进入下一个页面(startActivity)
        Toast.makeText(LogonActivity.this, "logon ok", Toast.LENGTH_SHORT).show();

    }
}
