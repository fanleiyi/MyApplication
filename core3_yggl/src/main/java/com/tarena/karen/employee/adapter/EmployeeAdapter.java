package com.tarena.karen.employee.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.tarena.karen.employee.R;
import com.tarena.karen.employee.entity.Employee;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pjy on 2017/6/2.
 */

public  class EmployeeAdapter
        extends BaseAdapter {

    private List<Employee> employeeList=
            new ArrayList<Employee>();
    Context context;
    public EmployeeAdapter(Context context){
        this.context=context;
    }

    public void addEmployees(List<Employee> emps){
        if(emps!=null){
            employeeList.addAll(emps);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return employeeList.size();
    }

    @Override
    public Employee getItem(int i) {
        return employeeList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder holder=null;
        if(view==null){
            view=View.inflate(context, R.layout.inflate_employee_item_layout,null);
            holder=new ViewHolder();
            holder.textView_Id= (TextView) view.findViewById(R.id.textView_Employee_Id);
            holder.textView_Name= (TextView) view.findViewById(R.id.textView_Employee_Name);
            holder.textView_Age= (TextView) view.findViewById(R.id.textView_Employee_Age);
            holder.textView_Gender= (TextView) view.findViewById(R.id.textView_Employee_Gender);
            holder.textView_Salary= (TextView) view.findViewById(R.id.textView_Employee_Salary);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }

       Employee employee=getItem(i);
       holder.textView_Name.setText(employee.getName());
        holder.textView_Id.setText(String.valueOf(employee.getId()));
        holder.textView_Salary.setText(String.valueOf(employee.getSalary()));
        holder.textView_Age.setText(String.valueOf(employee.getAge()));
        String gender="";
        if(employee.getGender().equals("m")){
            gender="男";
        }else{
            gender="女";
        }
        holder.textView_Gender.setText(gender);
        return view;
    }

    private class ViewHolder{
        TextView textView_Id;
        TextView textView_Name;
        TextView textView_Age;
        TextView textView_Gender;
        TextView textView_Salary;
    }
}
