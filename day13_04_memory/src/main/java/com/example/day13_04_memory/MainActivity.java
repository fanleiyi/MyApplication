package com.example.day13_04_memory;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageId);

    }
   /* public void onClick(View v){
        new Thread(){
            @Override
            public void run() {
                try{Thread.sleep(5000);}catch(Exception e){}
                //BitmapFactory.decodeFile()
                //假设如下图片来在网络
                final Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
            }
        }.start();
    }*///此写法可能有内存泄漏

    public void onClick(View v){
        new LoadImageTask(imageView,
                getApplicationContext()).execute();
    }
    static class LoadImageTask extends AsyncTask<Void,Void,Bitmap>{

        private WeakReference<ImageView> weakReference;
        private Context mContext;
        public LoadImageTask(ImageView imageView,Context ctx){
            weakReference=new WeakReference<ImageView>(imageView);
            mContext=ctx;
        }
        @Override
        protected Bitmap doInBackground(Void... params) {
            try{Thread.sleep(5000);}catch(Exception e){}
            //图片的加载将来来自网络(可能还要压缩)
            Bitmap bitmap=
                    BitmapFactory.decodeResource(
                            mContext.getResources(),
                            R.mipmap.ic_launcher);
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //从弱引用对象中获得imageview对象
            ImageView imageView=weakReference.get();
            //初始化imageview中的内容
            if(bitmap!=null&&imageView!=null) {
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
