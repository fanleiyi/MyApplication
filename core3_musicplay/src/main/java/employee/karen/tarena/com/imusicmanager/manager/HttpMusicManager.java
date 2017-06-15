package employee.karen.tarena.com.imusicmanager.manager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import employee.karen.tarena.com.imusicmanager.entity.Music;
import employee.karen.tarena.com.imusicmanager.iconstant.IURL;
import employee.karen.tarena.com.imusicmanager.util.StreamUtil;

/**
 * Created by pjy on 2017/6/2.
 */

public class HttpMusicManager {
    private static List<Music> loadMusics(){

        List<Music> musics=
                new ArrayList<Music>();
        try{
            URL url=new URL(IURL.MUSICLIST_URL);
            HttpURLConnection connection=
                    (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setDoInput(true);

            connection.connect();
            int statusCode=connection.getResponseCode();
            if(statusCode==200){
                InputStream is=connection.getInputStream();
                //将输入转换成一个json字符串
                String jsonStr= StreamUtil.
                        createStr(is);
                //将json字符串包装成json对象
                JSONObject jsonObject=
                        new JSONObject(jsonStr);
                String result=jsonObject.getString("result");
                if("ok".equals(result)){
                    JSONArray array=jsonObject.getJSONArray("data");
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonMusic=array.
                                  getJSONObject(i);
                        int id=jsonMusic.getInt("id");
                        String album=jsonMusic.getString("album");
                        String albumpic=jsonMusic.getString("albumpic");
                        String author=jsonMusic.getString("author");
                        String composer=jsonMusic.getString("composer");
                        String downcount=jsonMusic.getString("downcount");
                        String durationtime=jsonMusic.getString("durationtime");
                        String favcount=jsonMusic.getString("favcount");
                        String musicpath=jsonMusic.getString("musicpath");
                        String name=jsonMusic.getString("name");
                        String singer=jsonMusic.getString("singer");
                        Music music=new Music(
                                id,
                                album,
                                albumpic,
                                author,
                                composer,
                                downcount,
                                durationtime,
                                favcount,
                                musicpath,
                                name,singer);
                        //添加到集合中
                        musics.add(music);
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return musics;
    }

    public static LoadMuscsListener listener;
    public static void asyncLoadMusic(
            LoadMuscsListener musicListener){
        if(listener==null){
            listener=musicListener;
        }
        //启动异步任务的方法
        MyAsyncTask task=new MyAsyncTask();
        task.execute();
    }

    public  static class MyAsyncTask extends
            AsyncTask<Void,Void,List<Music>>{

        @Override
        protected List<Music>
            doInBackground(Void... voids) {
            //把网络访问的方法在这里调用
            List<Music> musics=loadMusics();
            return musics;
        }

        @Override
        protected void onPostExecute(List<Music> musics) {
            //把拿到网络加载数据通知给Activity
            listener.onMusicsLoadEnd(musics);
        }
    }

    public interface LoadMuscsListener{
        public void onMusicsLoadEnd(List<Music> musics);
    }
}
