package com.tarena.karen.youlu03.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tarena.karen.youlu03.ChatActivity;
import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.adapter.ConversationAdapter;
import com.tarena.karen.youlu03.entity.Conversation;
import com.tarena.karen.youlu03.entity.SMS;
import com.tarena.karen.youlu03.manager.DialogManager;
import com.tarena.karen.youlu03.manager.SMSManager;

import java.util.List;

/**
 * Created by pjy on 2017/5/16.
 */

public  class SMSFragment extends BaseFragment {
    ListView listView_Con;
    ConversationAdapter adapter;
    boolean permissionFlag;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.fragment_sms_layout,container,false);
        initialUI();
        checkPermission();
        return contentView;
    }

    private void checkPermission() {
        if(ContextCompat.
                checkSelfPermission(
                getActivity(),
                Manifest.permission.
                RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED){
         requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},103);
        }else{
            refershConversation();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode){
            case 103:
                if(grantResults.length>0){
                    if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(getActivity(),
                                "获得短信访问权限才可以使用",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    permissionFlag=true;
                    refershConversation();
                }else{
                    Toast.makeText(getActivity(),
                            "没有进行相关权限的处理",
                            Toast.LENGTH_LONG).show();
                }
                break;
            default:break;
        }

    }

    @Override
    public void initialUI() {
        actionbar= (LinearLayout) contentView.findViewById(R.id.actionbar_Conversation);
        initialActionbar(-1,"短消息",-1);
        listView_Con= (ListView) contentView.
                findViewById(R.id.listView_Conversation);
        adapter=new ConversationAdapter(getActivity());
        listView_Con.setAdapter(adapter);
        listView_Con.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Conversation conversation=adapter.getItem(i);

                int threadId=conversation.getThread_id();
                String name=conversation.getName();
                String address=conversation.getAddress();

                int read=conversation.getRead();
                if(read==0){
                    //更新会话的状态为已读
                    SMSManager.updateConversation(
                    getActivity(),threadId);
                }
                //以下做intent的跳转

                Intent intent=new Intent(
                        getActivity(),
                        ChatActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("address",address);
                intent.putExtra("threadId",threadId);

                startActivity(intent);
            }
        });
        listView_Con.setOnItemLongClickListener(
                new MyOnItemLongClickListener());
    }
    public  class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener{

        @Override
        public boolean onItemLongClick(
                AdapterView<?> adapterView,
                View view, int i, long l) {
            Conversation con=adapter.getItem(i);
            DialogManager.showDeleteConversationDialog(
                    getActivity(),con,adapter);
            return true;
        }
    }

    private void refershConversation() {
        //获得所有的会话数据
        List<Conversation> cons= SMSManager.
                getAllConversations(getActivity());
        //把数据添加到适配器集合中
        adapter.addDatas(cons,true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(permissionFlag){
            refershConversation();
        }else{
            permissionFlag=true;
        }
    }
}
