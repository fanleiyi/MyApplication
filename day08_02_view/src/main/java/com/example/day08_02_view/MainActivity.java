package com.example.day08_02_view;

import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;

public class MainActivity extends AppCompatActivity {

    private TabGroup tabGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabGroup = (TabGroup) findViewById(R.id.radioId);
        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                   int position = group.indexOfChild(group.findViewById(checkedId));
                    tabGroup.update(position);
            }
        });
    }
}
