package com.tarena.karen.ann;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public  class MainActivity extends BaseActivity {

    @ViewInject(R.id.testView_Test)
    TextView textView_Test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView_Test.setText("Hello Hello!");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }
}
