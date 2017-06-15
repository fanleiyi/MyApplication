package com.tarena.karen.employee;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.tarena.karen.employee.adapter.EmployeeAdapter;
import com.tarena.karen.employee.entity.Employee;
import com.tarena.karen.employee.manager.HttpManager;

import java.util.List;

public class EmployeeQueryActivity extends AppCompatActivity {

    ListView listView=null;
    EmployeeAdapter adapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_query);
        initialUI();
        asyncLoadEmps();
    }

    private void asyncLoadEmps() {
        new Thread(new Runnable() {
            @Override
            public void run() {
               List<Employee> emps= HttpManager.queryEmployeesHttpGet();
               Message msg= hadler.obtainMessage();
                msg.obj=emps;
                hadler.sendMessage(msg);
            }
        }).start();
    }

    private void initialUI() {
        listView= (ListView) findViewById(R.id.listView_Employees);
        adapter=new EmployeeAdapter(this);
        listView.setAdapter(adapter);
    }
    Handler hadler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<Employee> emps= (List<Employee>) msg.obj;
            if(emps!=null){
                adapter.addEmployees(emps);
            }
        }
    };
}
