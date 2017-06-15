package com.tarena.karen.room;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView listView=null;
    List<String> chats;
    ArrayAdapter<String> adapter=null;
    EditText edNickname;
    EditText edContent;

    TextView tvEmpty;

    LinearLayout llConnect;
    LinearLayout llChat;

    Socket socket=null;
    BufferedReader br;

    PrintWriter pw;

    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialUI();
    }

    private void initialUI() {
        listView= (ListView) findViewById(R.id.lv_main_chats);
        chats=new ArrayList<String>();
        adapter=new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                chats);

        listView.setAdapter(adapter);

        edNickname= (EditText) findViewById(
                R.id.et_main_nickname);
        edContent= (EditText) findViewById(
                R.id.et_main_content);

        tvEmpty= (TextView) findViewById(R.id.tv_empty);
        listView.setEmptyView(tvEmpty);

        llChat= (LinearLayout) findViewById(R.id.ll_main_chat);
        llConnect= (LinearLayout) findViewById(R.id.ll_main_connect);
    }

    /**
     * 点击连接按钮回调方法
     * @param view
     */
    public void connect(View view){
        //网络访问要在工作线程中执行
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket=new Socket("192.168.199.186",5000);
                    //用来读取服务器端发送来的数据
                    br=new BufferedReader(new InputStreamReader(
                            socket.getInputStream(),
                            "utf-8"));
                    //向服务器端发送数据的输出流
                    pw=new PrintWriter(
                            new OutputStreamWriter(
                            socket.getOutputStream(),"utf-8"));
                    //开启一个子线程,不断的对服务器发来的数据的读取
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                                     try {
                                        while (flag) {
                                            final String resp=br.readLine();
                                            if(resp==null){
                                                throw new RuntimeException("服务器端已经关闭");
                                            }
                                            //如果不为空说明连接成功了
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    chats.add(resp);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llConnect.setVisibility(View.GONE);
                            llChat.setVisibility(View.VISIBLE);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 发送消息的按钮的回调方法
     * @param view
     */
    public void send(View view){
        final String line=edContent.getText().toString();
        if(TextUtils.isEmpty(line)){
            return;
        }
        //需要向服务器端提交数据
        new Thread(new Runnable() {
            @Override
            public void run() {
               String nickname= edNickname.getText().toString();
                if(TextUtils.isEmpty(nickname)){
                    nickname="1703学员";
                }
                pw.println(nickname+": "+line);
                pw.flush();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            socket.close();
            flag=false;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
