package com.example.day14_02_contentproviderclient;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //运行时权限检测，添加访问权限（android6.0新特性）
        int permissionCheck =
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE);
        Log.i("TAG","permissionCheck="+permissionCheck);
        //检测是否已经授予权限
        if(permissionCheck!= PackageManager.PERMISSION_GRANTED){
            //请求权限(可能会弹出一个对话框，然后进行选择)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    101);//requestCode
            return;
        }
        loadMediaImages();

    }

    private void loadMediaImages() {
        ContentResolver contentResolver=getContentResolver();
        //此uri对应媒体库中的images库
        Uri uri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        //通过uri访问对应的媒体库中的图片信息
        Cursor cursor=
                contentResolver.query(uri,null,null,null,null);

        //处理结果方式1，
        //processResultsMethod01(cursor);
        //处理结果方式2
        SimpleCursorAdapter adapter=//此adapter一般用于直接访问cursor中数据
                new SimpleCursorAdapter(this,
                        R.layout.grid_item_01,
                        cursor,
                        new String[]{MediaStore.Audio.Media.DATA},
                        new int[]{R.id.imageId},
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        GridView gView= (GridView) findViewById(R.id.gridViewId);
        gView.setAdapter(adapter);

    }

    private void processResultsMethod01(Cursor cursor) {
        //处理结果
        if(cursor==null){
            Log.i("TAG","query error");
            return;
        }
        if(!cursor.moveToFirst()){//移动指针判定是否有数据
            Log.i("TAG","no data");
            return;
        }
        Log.i("TAG","start process data");
        //?????
        do{
            Log.i("TAG",cursor.getString(
                    cursor.getColumnIndex(MediaStore.Images.Media.DATA)));
        }while(cursor.moveToNext());

        cursor.close();
    }

    /**通过此方法处理用户对权限申请的响应*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //同意
                    loadMediaImages();
                } else {
                    //拒绝
                    Toast.makeText(this,"权限被拒绝了",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
