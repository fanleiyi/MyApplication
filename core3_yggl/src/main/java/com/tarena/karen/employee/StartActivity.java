package com.tarena.karen.employee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.InputStream;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void register(View view){
        Intent intent=new Intent(this,RegistActivity.class);
        startActivity(intent);

    }
    public void login(View view){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
    }

    public void add(View view){
        Intent intent=new Intent(this,EmployeeAddActivity.class);
        startActivity(intent);
    }

    public void query(View view){
        Intent intent=new Intent(this,EmployeeQueryActivity.class);
        startActivity(intent);
    }
}
