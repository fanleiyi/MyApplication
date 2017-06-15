package com.tarena.karen.youlu03;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tarena.karen.youlu03.adapter.SMSAdapter;
import com.tarena.karen.youlu03.constant.IConstant;
import com.tarena.karen.youlu03.entity.SMS;
import com.tarena.karen.youlu03.manager.SMSManager;

import java.util.List;

public class ChatActivity extends Activity {
    static ListView listView_Chat;
    static SMSAdapter adapter=null;

    String name;//联系人的姓名
    static String address;//联系人的电话
    static int threadId;//会话的编号
    EditText editText_Message=null;
    ImageView imageView_Left;
    TextView  textView_Title;
    ImageView imageView_Right;

    static Context context=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        context=getApplicationContext();
        initialData();
        initialUI();
    }

    private void initialData() {
        //获得上一个Fragment传过来的数据
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        address=intent.getStringExtra("address");
        threadId=intent.getIntExtra("threadId",0);
    }

    private void initialUI() {
       imageView_Left= (ImageView) findViewById(
               R.id.imageView_ActionBar_Left);
        textView_Title= (TextView) findViewById(
                R.id.textView_ActionBar_Title);
        imageView_Right= (ImageView) findViewById(
                R.id.imageView_ActionBar_Right);
        imageView_Right.setVisibility(View.INVISIBLE);

        imageView_Left.setImageResource(
                R.drawable.ic_back);

        if(TextUtils.isEmpty(name)){
            textView_Title.setText(address);
        }else{
            textView_Title.setText(name);
        }

         imageView_Left.setOnClickListener(new View.OnClickListener() {
             @Override
        public void onClick(View view) {
            finish();
        }
    });
         editText_Message= (EditText) findViewById(R.id.editText_Chat_SMS);

        listView_Chat= (ListView) findViewById(R.id.listView_Chat);
        adapter=new SMSAdapter(this);
        listView_Chat.setAdapter(adapter);
        refreshSMS();


    }

    private static void refreshSMS() {
        //查询指定会话的短信
        List<SMS> smses= SMSManager.
                getAllSMSes(context,threadId);
        adapter.addDatas(smses,true);
        //每次数据适配完成后显示最后一项
        listView_Chat.setSelection(
                adapter.getCount()-1);
    }

    public static  class SMSReceiver
            extends BroadcastReceiver{
        @Override
        public void onReceive(
                Context context,
                Intent intent) {
            String action=intent.getAction();
            if(IConstant.RECEIVED_SMS.equals(action)){
                //说明收到的广播的类型是发来短信的广播
                Log.i("TAG:",action);
                //拦截到了短信
                //进行短信的解析
                SMS sms=SMSManager.onReceiveSMS(context,intent);
                Log.i("TAG:sms",sms.toString());
                if(sms.getAddress().equals(address)){
                   //把收到的短信存入到收件箱
                    SMSManager.saveReceicedSMS(context,sms,threadId);
                    //刷新UI
                    refreshSMS();
                }
            }else if(IConstant.SEND_SMS.
                    equals(action)){
                //有短信发出去了。
                Log.i("TAG:",action);


                String body=
                        intent.getStringExtra("body");
                String address=
                        intent.getStringExtra("address");
                //把发出的短信存入到发件箱中
                SMSManager.
                        saveSendSMS(context,body,address);
                //UI刷新
                refreshSMS();
            }

        }
    }

    public void send(View view){
        //获得发送的短信的内容
        String message=editText_Message.
                getText().toString();
        //发送短信
        SMSManager.sendSMS(this,message,address);
        //清空一个文本框
        editText_Message.setText("");
    }
}
