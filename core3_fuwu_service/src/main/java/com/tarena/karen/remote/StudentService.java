package com.tarena.karen.remote;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class StudentService extends Service {
    private String[] names=new String[]
            {"kitty","mary","jerry","tom","lily"};
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
        Log.i("TAG:","远程服务已启动!");
    }

    MyBinder binder=new MyBinder();
    @Override
    public IBinder onBind(Intent intent) {
       return binder;
    }
    public  class MyBinder extends IStudent.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {}

        @Override
        public String queryStudent(int no) throws RemoteException {
            return query(no);
        }
    }
}
