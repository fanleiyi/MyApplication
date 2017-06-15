package com.example.day13_03_memory;

import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private List<String> mObjects=new ArrayList<>();
    private SimpleRecyclerAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeRefreshLayout();
        setRecyclerView();
        loadObjectAsync();
    }

    /**初始化RecyclerView*/
    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.radioId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleRecyclerAdapter(this,mObjects);
        recyclerView.setAdapter(adapter);
    }

    /**初始化SwipeRefreshLayout对象以实现下拉刷新*/
    private void setSwipeRefreshLayout() {
        swipeRefreshLayout=(SwipeRefreshLayout) findViewById(R.id.swipeId);
        //设置此监听的目的时，在执行滚动视图的下拉操作时执行监听的onRefresh方法
        swipeRefreshLayout.setOnRefreshListener(this);
        //设置滚动条的颜色
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_green_dark
                ,android.R.color.holo_green_light,
                android.R.color.holo_blue_dark);
    }

    /**启动异步任务加载数据*/
    public void loadObjectAsync(){
        new LoadAsyncTask(swipeRefreshLayout).execute();
    }

    /**页面刷新时要启动异步任务*/
    @Override
    public void onRefresh() {
        loadObjectAsync();//刷新时要启动异步任务加载数据
    }

    static class LoadAsyncTask extends AsyncTask<String,Void,List<String>>{
     /* //强引用(直接引用)
        private RecyclerView mRecyclerView;
        public LoadAsyncTask(RecyclerView recyclerView){
            mRecyclerView=recyclerView;
        }*/
        /**弱引用WeakReference引用的对象允许被销毁?
         * 为什么对view的引用应该是弱引用呢？
         * 因为我们可能要销毁activity(view关联着activity)
         * */
        private WeakReference<SwipeRefreshLayout> weakReference;
        public LoadAsyncTask(SwipeRefreshLayout swipeRefreshLayout){
            weakReference=new WeakReference<SwipeRefreshLayout>(swipeRefreshLayout);
        }
        @Override
        protected List<String> doInBackground(String... params) {
            try{Thread.sleep(3000);}catch(Exception e){}
            List<String> objects=new ArrayList<>();
            String dataStr="A/B/C/D/E/F/G";
            objects.addAll(Arrays.asList(dataStr.split("/")));
            return objects;
        }
        /**此方法运行在主线程*/
        @Override
        protected void onPostExecute(List<String> strings) {
            SwipeRefreshLayout swipeRefreshLayout=
                    weakReference.get();
            if(swipeRefreshLayout==null)return;
            RecyclerView recyclerView=
                    (RecyclerView) swipeRefreshLayout
                            .findViewById(R.id.radioId);

            SimpleRecyclerAdapter adapter=
                    (SimpleRecyclerAdapter)
                            recyclerView.getAdapter();

            //添加数据
            adapter.addAll(0,strings);
            //刷新页面(应在主线程执行)
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);

            Log.i("TAG","onPostExecute.strings="+strings.toString());
        }
    }
}
