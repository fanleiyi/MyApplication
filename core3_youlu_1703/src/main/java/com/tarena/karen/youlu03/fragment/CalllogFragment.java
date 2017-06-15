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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.tarena.karen.youlu03.R;
import com.tarena.karen.youlu03.adapter.CalllogAdapter;
import com.tarena.karen.youlu03.entity.Calllog;
import com.tarena.karen.youlu03.manager.ContactsManager;
import com.tarena.karen.youlu03.manager.DialogManager;

import java.util.List;

/**
 * Created by pjy on 2017/5/16.
 */

public  class CalllogFragment
        extends BaseFragment {
    ListView listView_Calllog;
    CalllogAdapter adapter=null;
    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
         contentView=inflater.
                inflate(
                        R.layout.fragment_calllog_layout,
                        container,false);
         initialUI();
         //运行时权限的验证
         checkPerssion();
        return contentView;
    }

    private void checkPerssion() {
        if(ContextCompat.checkSelfPermission(
                getActivity(),
                Manifest.permission.WRITE_CALL_LOG)!=
                PackageManager.PERMISSION_GRANTED){
            //发起权限验证请求
            requestPermissions(new String[]{
                    Manifest.permission.
                    WRITE_CALL_LOG},101);
        }else{
            refreshCalllogs();
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:
                if(grantResults.length>0){
                    if(grantResults[0]!=
                            PackageManager.PERMISSION_GRANTED){
                        //授权未通过
                        Toast.makeText(getActivity(),
                                "没有通过相关权限的验证",
                                Toast.LENGTH_LONG).show();
                        return;
                    }
                    refreshCalllogs();
                }else{
                    Toast.makeText(
                            getActivity(),
                            "没有去执行权限验证的处理",
                            Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void initialUI() {
        //初始设置标题栏
        actionbar= (LinearLayout) contentView.findViewById(R.id.actionbar_CallLog);
        initialActionbar(-1,"通话记录",-1);
        listView_Calllog= (ListView) contentView.
                findViewById(R.id.listView_Calllog);
        adapter=new CalllogAdapter(getActivity());

        listView_Calllog.setAdapter(adapter);
        listView_Calllog.setOnItemLongClickListener(
                new MyOnItemLongClickListener());

        //refreshCalllogs();
    }

    private void refreshCalllogs() {
        List<Calllog> calllogs=
                ContactsManager.
                getAllCalllogs(getActivity());
        //将通话记录添加到适配器中
        adapter.addDatas(calllogs,true);
    }

    public  class MyOnItemLongClickListener implements AdapterView.OnItemLongClickListener{
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            //获得被删除的通话记录对象
            Calllog calllog=adapter.getItem(i);
            DialogManager.showDeleteCallLogDialog(
                    getActivity(),
                    calllog,
                    adapter);

            return true;
        }
    }

}
