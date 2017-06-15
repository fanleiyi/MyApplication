package com.example.day12_02_msw;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> list=new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private Handler workerHandler;
    private WorkerThread workerThread;
    private View footerView;
    private ListView lsv;

    /**onCreate方法运行在主线程*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        footerView = View.inflate(this, R.layout.load_view_01,null);

        lsv = (ListView) findViewById(R.id.lsv);
        lsv.addFooterView(footerView);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                list);
        lsv.setAdapter(adapter);



        //第一种方法(以后每次刷新都可能要创建新的线程)
        //loadObjectsAsync();

        //第二种方法
        workerThread = new WorkerThread();
        workerThread.start();// 主线程启动的线程
        //构建handler通过handler给工作线程发消息(让工作线程帮我执行耗时操作)
        workerHandler=new Handler(workerThread.getLooper()){
            //重写此方法的目的是处理消息
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1) {
                    loadObjects();
                }
            }
        };
        //消息要发送给谁，由handler关联的looper决定
        workerHandler.sendEmptyMessage(1);

    }
   /* *//**启动工作线程访问网络获得数据*//*
    private void loadObjectsAsync(){
             new Thread(){public void run() {
                //1.模拟正在加载数据的动作
                try {Thread.sleep(5000);
                }catch(Exception e){e.printStackTrace();};
                //2.假如从网络中获得一个如下字符串
                String str="A/B/C/D/E/F/G";
                //3.解析此字符串
                String[] array=str.split("/");//{A,B,C,D,E,F}
                //4.将此数据更新到页面上
                //4.1更新listview关联数据集
                list.addAll(Arrays.asList(array));
                //4.2刷新页面(应在主线程)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });//给主线程发消息(主线程收到消息会执行Runnable对象的run方法)
            }}.start();
    }
*/


    private void loadObjects(){
        //1.模拟正在加载数据的动作
        try {Thread.sleep(5000);
        }catch(Exception e){e.printStackTrace();};
        //2.假如从网络中获得一个如下字符串
        String str="A/B/C/D/E/F/G";
        //3.解析此字符串
        String[] array=str.split("/");//{A,B,C,D,E,F}
        //4.将此数据更新到页面上
        //4.1更新listview关联数据集
        list.addAll(Arrays.asList(array));
        //4.2刷新页面(应在主线程)
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
                lsv.removeFooterView(footerView);
            }
        });//给主线程发消息(主线程收到消息会执行Runnable对象的run方法)
    }
    /**此方法用于创建选项菜单*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item=menu.add(Menu.NONE,101,201,"刷新");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }
    /**此方法用于监听item的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==101){
            //刷新页面(从网络获取数据，通过此数据更新页面)
            //loadObjectsAsync();(每次创建线程对象不好)

            //方法2
            workerHandler.sendEmptyMessage(1);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        workerThread.quit();
    }
}
