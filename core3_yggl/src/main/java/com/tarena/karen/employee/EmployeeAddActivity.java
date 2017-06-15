package com.tarena.karen.employee;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tarena.karen.employee.entity.Employee;
import com.tarena.karen.employee.manager.HttpManager;

public class EmployeeAddActivity extends AppCompatActivity {
    EditText editText_Name;
    EditText editText_Salary;
    EditText editText_Age;
    RadioButton radioButton_Male;
    RadioButton radioButton_Female;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        initialUI();
    }

    private void initialUI() {
        editText_Salary= (EditText) findViewById(
                R.id.editText_Add_Salary);
        editText_Name= (EditText) findViewById(
                R.id.editText_Add_Name);
        editText_Age= (EditText) findViewById(
                R.id.editText_Add_Age);
        radioButton_Female= (RadioButton) findViewById(
                R.id.radio_Female);
        radioButton_Male= (RadioButton) findViewById(
                R.id.radio_Male);
    }

    public void add(View view){
        String name=editText_Name.getText().toString();
        int age=Integer.parseInt(
                editText_Age.getText().toString());
        double salary=Double.parseDouble(
                editText_Salary.getText().toString());
        String gender="";
        if(radioButton_Male.isChecked()){
            gender="m";
        }else if(radioButton_Female.isChecked()){
            gender="f";
        }

        if(TextUtils.isEmpty(name)){
            Toast.makeText(EmployeeAddActivity.this,
                    "员工姓名不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(age<=0||salary<=0){
            Toast.makeText(EmployeeAddActivity.this,
                    "请填写有效的年龄或薪水",
             Toast.LENGTH_LONG).show();
            return;
        }
        Employee employee=new Employee(
                -1,name,salary,age,gender);
        asyncAddEmployee(employee);

    }
    private void asyncAddEmployee(final Employee employee) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag=HttpManager.addEmployeeHttpPost(employee);
                Message msg=handler.obtainMessage();
                msg.obj=flag;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(
                Message msg) {
            Boolean flag= (Boolean) msg.obj;
            if(flag){
                Toast.makeText(EmployeeAddActivity.this,
                        "添加成功!",
                        Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(
                        EmployeeAddActivity.this,
                        "添加失败!",Toast.LENGTH_LONG).show();
            }
        }
    };
}
