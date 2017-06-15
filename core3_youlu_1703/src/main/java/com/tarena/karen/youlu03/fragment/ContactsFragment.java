package com.tarena.karen.youlu03.fragment;

import android.Manifest;
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
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.adapter.ContactsAdatper;
import com.tarena.karen.youlu03.entity.Contact;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.DialogManager;

import java.util.List;

/**
 * Created by pjy on 2017/5/16.
 */

public  class ContactsFragment extends BaseFragment{
    GridView gridView_Contact;
    ContactsAdatper adatper;
    //是否做过授权标识
    boolean permissionFlag=false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        contentView=inflater.inflate(R.layout.fragment_contact_layout,container,false);
        initialUI();
        //实现运行时权限的授权的确认
        requestMyPermission();
        return contentView;
    }

    private void requestMyPermission() {
        //判断有没有对联系人访问的权限进行授权确认
        //没有的话就发起对该权限进行授权确认的请求
        if(ContextCompat.
                checkSelfPermission(
                getActivity(),
                Manifest.permission.WRITE_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            //如果没有对该权限进行授权处理
            //向系统发起授权的请求
            requestPermissions(
                    new String[]{Manifest.
                    permission.WRITE_CONTACTS},
                    100);
        }else{
            refreshContact();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 100:
              if(grantResults.length>0){
                  //如果授权结果不等于同意
                  if(grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                      Toast.makeText(getActivity(),"必须通过联系人访问授权才能访问",Toast.LENGTH_LONG).show();
                      return;
                  }
                  permissionFlag=true;
                  refreshContact();
              }else{
                  Toast.makeText(getActivity(),"没有执行授权处理",Toast.LENGTH_LONG).show();
              }
              break;
            default:
              break;
        }
    }

    @Override
    public void initialUI() {
        actionbar= (LinearLayout) contentView.findViewById(R.id.actionBar_Contact);
        initialActionbar(-1,"联系人",R.drawable.ic_search);

        gridView_Contact= (GridView)
                contentView.findViewById(
                R.id.gridView_Contact);
        adatper=new ContactsAdatper(getActivity());
        //将gridview和适配器进关联
        gridView_Contact.setAdapter(adatper);

        //注册gridview的项点击事件的监听
        gridView_Contact.setOnItemClickListener(
                new MyOnItemClickListener());
        //注册长按事件监听
        gridView_Contact.setOnItemLongClickListener(
                new MyOnItemLongClickListener());
    }

    private void refreshContact() {
        //获联系人的信息
        List<Contact> contacts= ContactsManager.
                getAllContacts(getActivity());
        Contact contact=new Contact(
                0,"添加联系人",
                null,null,null,0);
        //将自定义的联系人插入到集合中作为集合的第一个元素
        contacts.add(0,contact);
        //将集合数据添加到适配器当中
        adatper.addDatas(contacts,true);
    }

    public  class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(
                AdapterView<?> adapterView,
                View view, int i, long l) {
            if(i==0){
               //说明我点击了添加联系人的项
                //弹出添加联系人对话框
                DialogManager.
                showAddContactDialog(getActivity());
            }else{
                //获得被选中的联系人
                Contact contact=adatper.getItem(i);
                DialogManager.showEditContactDialog(
                        getActivity(),contact);
            }
        }
    }

    public  class MyOnItemLongClickListener
            implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(
                AdapterView<?>
                adapterView,
                View view, int i, long l) {
          //获得被删除的联系人对象
            Contact contact=adatper.getItem(i);
            DialogManager.showDeleteContactDialog(
                    getActivity(),
                    contact,
                    adatper);
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(permissionFlag) {
            refreshContact();
        }
    }
}
