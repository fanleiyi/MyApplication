package com.example.day03_02_cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageId);
        textView = (TextView) findViewById(R.id.textId);

        imageView.setOnClickListener(this);
        textView.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String text = textView.getText().toString();
        switch (v.getId()) {
            case R.id.imageId:
                Toast.makeText(MainActivity.this,text,Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
