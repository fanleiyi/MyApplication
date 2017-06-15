package com.tarena.karen.ann;

import android.icu.text.DateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Field;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        autoInjectAllFieldId();
    }

    public abstract int getLayoutId();

    public void autoInjectAllFieldId(){
        try {
            //获得当前反射类的对象
            Class clz = this.getClass();
            //获得补反射的Activity的所有的字段
            Field[] fields = clz.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                if (fields[i].
                        isAnnotationPresent(ViewInject.class)) {
                    ViewInject inject = fields[i].getAnnotation(ViewInject.class);
                    int value = inject.value();
                    if (value > 0) {
                        fields[i].setAccessible(true);
                        fields[i].set(this, this.findViewById(value));
                    }

                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
