package com.example.day07_02_mylistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<Character> mObjects=new ArrayList<>();
        for(char c='A';c<='Z';c++){
            mObjects.add(c);
        }
        ListView lsv= (ListView) findViewById(R.id.listId);
        ArrayAdapter<Character> adapter=
                new ArrayAdapter<Character>(this,android.R.layout.simple_list_item_1,mObjects);
        lsv.setAdapter(adapter);

        ScrollView scrollView= (ScrollView) findViewById(R.id.scrollViewId);

        //平滑滚动到0,0点
        scrollView.smoothScrollTo(0,0);

    }
}
