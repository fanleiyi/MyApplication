package employee.karen.tarena.com.imusicmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import employee.karen.tarena.com.imusicmanager.adapter.MusicAdapter;
import employee.karen.tarena.com.imusicmanager.entity.Music;
import employee.karen.tarena.com.imusicmanager.iconstant.IURL;
import employee.karen.tarena.com.imusicmanager.manager.HttpMusicManager;
import employee.karen.tarena.com.imusicmanager.service.MusicService;

public  class MusicListActivity
        extends BaseActivity implements HttpMusicManager.LoadMuscsListener {
    List<Music> musics=null;
    MusicAdapter adapter=null;
    ListView listView_Music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        initialUI();
        HttpMusicManager.asyncLoadMusic(this);

    }

    public void initialUI() {
        actionbar= (LinearLayout) findViewById(R.id.actionbar_MusicList);

        initialActionbar(-1,"音乐列表",-1);


        listView_Music= (ListView)
                findViewById(R.id.listView_Music);
        adapter=new MusicAdapter(this);
        listView_Music.setAdapter(adapter);

        listView_Music.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(MusicListActivity.this,PlayActivity.class);
                //设置当前要播放的音乐对象在整个音乐列表中的位置
                intent.putExtra("position",i);
                intent.putExtra("musics",(ArrayList)musics);
                startActivity(intent);
            }
        });

    }

    //音乐从网络上加载完毕回被调用
    @Override
    public void onMusicsLoadEnd(
            List<Music> musics) {
      this.musics=musics;
        //把加载完成的音乐信息添加到适配器集合中
        adapter.addMusics(musics);

        //启动音乐播放的服务
        Intent intent=new Intent(this,
                MusicService.class);
        startService(intent);

        //发一个广播给小组件
        //把加载完成音乐的数据发送给小组件
        //小组件需要订阅该广播，获得加载的音乐数据

        Intent widgetIntent=new Intent();
        widgetIntent.setAction(IURL.MUSICSLOAD_ACTION);
        widgetIntent.putExtra("musics",(ArrayList<Music>)musics);
        sendBroadcast(widgetIntent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,MusicService.class));
    }
}
