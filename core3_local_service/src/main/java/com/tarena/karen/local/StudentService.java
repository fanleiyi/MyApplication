package com.tarena.karen.local;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class StudentService extends Service {
    String[] names=new String[]{
            "kitty",
            "jerry",
            "mary",
            "tom",
            "lily"};
    private String query(int no){
        if(no>0&&no<6){
           return names[no-1];
        }
        return null;
    }

    public StudentService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG:","服务已经启动");
    }
    public  MyBinder binder=new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {

        return binder;
    }

    public  class MyBinder extends Binder implements IStudent{
        @Override
        public String queryStudent(int no) {
            return query(no);
        }
    }

}
