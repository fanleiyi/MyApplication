package com.tarena.karen.youlu03.manager;

import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tarena.karen.youlu03.constant.IConstant;
import com.tarena.karen.youlu03.entity.Conversation;
import com.tarena.karen.youlu03.entity.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by pjy on 2017/5/23.
 * 对会话以及短信进行管理的业务类
 */

public class SMSManager {
    // 短信会话Uri
    public static final Uri CONVERSATION_URI = Uri
            .parse("content://mms-sms/conversations");
    // 短信Uri 对应的ContentProvider会协调处理短信的收件箱和发件箱
    public static final Uri SMS_URI = Uri.parse("content://sms");
    // 短信发件箱:
    public static final Uri SMS_SEND_URI = Uri.parse("content://sms/sent");
    // 短信收件箱:
    public static final Uri SMS_INBOX_URI = Uri.parse("content://sms/inbox");

    public static void
        getConversationColumns(Context context){
        ContentResolver resolver=context.getContentResolver();
        Cursor cursor=resolver.query(CONVERSATION_URI,null,null,null,null);
        if(cursor.moveToNext()){
           int count=cursor.getColumnCount();
            for(int i=0;i<count;i++){
                Log.i("TAG:",cursor.getColumnName(i)+"-->"+cursor.getString(i));
            }
        }
        cursor.close();
    }
    public static void getSMSColumns(
            Context context){
        ContentResolver resolver=context.getContentResolver();
        Cursor cursor=resolver.query(
                SMS_URI,null,null,null,null);
        if(cursor.moveToNext()){
           int count=cursor.getColumnCount();
            for(int i=0;i<count;i++){
                Log.i("TAG:",cursor.getColumnName(i)+"--"+cursor.getString(i));
            }
        }
        cursor.close();
    }

    /**
     * 查询所有的会话
     * @param context
     * @return
     */
    public static List<Conversation>
            getAllConversations(
            Context context){
     List<Conversation> conversations=
             new ArrayList<Conversation>();
        ContentResolver resolver=context.getContentResolver();
        String[] projection=
                new String[]{
                        "thread_id",
                        "address",
                        "date",
                        "body",
                        "read"
                };
        String order="date desc";
        Cursor cursor=resolver.query(
                CONVERSATION_URI,
                projection,
                null,null,order);

        while(cursor.moveToNext()){
           Conversation conversation=new Conversation();
            int thread_id=cursor.getInt(0);
            String address=cursor.getString(1);
            long date=cursor.getLong(2);
            String body=cursor.getString(3);
            int read=cursor.getInt(4);

            conversation.setThread_id(thread_id);
            conversation.setAddress(address);
            conversation.setDate(date);
            conversation.setBody(body);
            conversation.setRead(read);
            //根据电话号码查询姓名并设置
            conversation.setName(ContactsManager.
                    getNameByNumber(
                    context,address));
            //根据电话号码查询头像编号并设置
            conversation.setPhotoId(
                    ContactsManager.
                    getPhotoIdByNumber(context,address));
            conversation.setDateStr(
                    ContactsManager.
                    formatDate(date));
            //把会话对象加到集合里
            conversations.add(conversation);
        }
        cursor.close();
        return  conversations;
    }

    /**
     * 查询某一个会话下的所有的短信
     * @param context
     * @param threadId 短信所属的会话的编号
     * @return
     */
    public static List<SMS> getAllSMSes(
            Context context,
            int threadId){
        List<SMS> smses=
                new ArrayList<SMS>();
        ContentResolver resolver=
                context.getContentResolver();
        String[] project=new String[]{
                "_id",
                "body",
                "address",
                "date",
                "type"};
        String where="thread_id=?";
        String order="date asc";
        Cursor cursor=resolver.query(
                SMS_URI,
                project,
                where,
                new String[]{String.valueOf(threadId)},
                order);
        while(cursor.moveToNext()){
            SMS sms=new SMS();
            int _id=cursor.getInt(0);
            String body=cursor.getString(1);
            String address=cursor.getString(2);
            long date=cursor.getLong(3);
            int type=cursor.getInt(4);

            sms.set_id(_id);
            sms.setBody(body);
            sms.setAddress(address);
            sms.setDate(date);
            sms.setType(type);
            sms.setPhotoId(ContactsManager.
                    getPhotoIdByNumber(
                    context,address));
            sms.setDateStr(smsDateFormat(date));
            //把短信对象添加到集合中
            smses.add(sms);
        }
        cursor.close();
        return smses;
    }
    /**
     * 对聊天的时间进行格式化处理
     * @param stamp
     * @return
     */
    public static String smsDateFormat(long stamp){
        int diff=ContactsManager.daydiff(stamp);
        String dateStr="";
        if(diff==0){
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("HH:mm:ss");
            dateStr=dateFormat.format(new Date(stamp));
        }else if(diff==1){
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("昨天 HH:mm:ss");
            dateStr=dateFormat.format(new Date(stamp));
        }else{
            SimpleDateFormat dateFormat=
                    new SimpleDateFormat("yyyy-MM-dd");
            dateStr=dateFormat.format(new Date(stamp));
        }
        return dateStr;
    }

    /**
     * 更新会话读取状态
     * @param context
     * @param threadId 会话ID
     */
    public static void
        updateConversation(
            Context context,
            int threadId){
        ContentResolver resolver=context.
                getContentResolver();

        ContentValues values=
                new ContentValues();
        values.put("read",1);
        String where="thread_id=?";
        String args[]=new String[]{
                String.valueOf(threadId)};
        resolver.update(SMS_INBOX_URI,
                values,where,args);
    }
    public static void deleteConversation(Context context,int threadId){
        ContentResolver resolver=context.getContentResolver();
        String where="thread_id=?";
        String[] args=new String[]
                {String.valueOf(threadId)};
        resolver.delete(CONVERSATION_URI,where,args);
    }

    /**
     * 解析广播接收器拦截到的短信的内容
     * @param context
     * @param intent 封装了短信内容的意图
     * @return
     */
    public static SMS onReceiveSMS(
            Context context,
            Intent intent){
        //获得短信的内容,以bundle的形式获得
        Bundle bundle=intent.
                getExtras();
        //在bundle中保存了短信的内容
        Object[] pdus=(Object[])bundle.get("pdus");
        SmsMessage[] messages=new SmsMessage[pdus.length];
        //为了方便对短信内容的提取，需要将pdus转换成SmsMessage数组
        String format=intent.getStringExtra("format");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage=null;
            if(Build.VERSION.SDK_INT<23) {
                smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            }else{
                smsMessage=SmsMessage.createFromPdu((byte[])pdus[i],format);
            }
            messages[i]=smsMessage;
         }
        //对messages数组中的短信数据进行处理
        StringBuilder builder=
                new StringBuilder();
        String address="";
        long date=0;
        for(int i=0;i<messages.length;i++){
            if(i==0){
                address=messages[i].getOriginatingAddress();
                date=messages[i].getTimestampMillis();
            }
            builder.append( messages[i].getDisplayMessageBody());
        }
        //把解析出来的内容封装成一个sms对象
        SMS sms=new SMS();
        sms.setBody(builder.toString());
        sms.setDate(date);
        sms.setAddress(address);
        sms.setType(1);
        return  sms;
    }

    /**
     * 将收到的短信存入到收件箱中
     * @param context
     * @param sms 收到的短信
     * @param threadId 短信所属的会话Id
     */
    public static void saveReceicedSMS(
            Context context,
            SMS sms,
            int threadId){
       ContentResolver resolver=
               context.getContentResolver();
       ContentValues values=new ContentValues();

        values.put("thread_id",threadId);
        values.put("body",sms.getBody());
        values.put("date",sms.getDate());
        values.put("address",sms.getAddress());
        values.put("type",sms.getType());
        values.put("read",1);

        resolver.insert(SMS_INBOX_URI,values);
    }

    /**
     * 发出短信
     * @param context
     * @param message 发出的短信的内容
     * @param address 发出的短信接收方的电话号码
     */
    public static void sendSMS(
            Context context,
            String message,
            String address){
        SmsManager manager=SmsManager.
                getDefault();
        ArrayList<String> messages=
                manager.divideMessage(message);
        for(int i=0;i<messages.size();i++){
            String body=messages.get(i);

            Intent intent=new Intent();
            intent.putExtra("body",body);
            intent.putExtra("address",address);
            intent.setAction(IConstant.SEND_SMS);

            PendingIntent sendIntent=
                    PendingIntent.getBroadcast(
                    context,100,intent,
                    PendingIntent.
                    FLAG_UPDATE_CURRENT);
            manager.sendTextMessage(
                    address,null,body,
                    sendIntent,null);
        }
    }

    /**
     * 保存发出去的短信到发件箱中
     * @param context
     * @param body 短信的内容
     * @param address 接收者的电话号码
     */
    public static void saveSendSMS(
            Context context,
            String body,
            String address){
        ContentResolver resolver=
                context.getContentResolver();
        ContentValues values=
                new ContentValues();
        values.put("address",address);
        values.put("body",body);
        values.put("type",2);
        values.put("date",System.currentTimeMillis());
        resolver.insert(SMS_SEND_URI,values);
    }
}
