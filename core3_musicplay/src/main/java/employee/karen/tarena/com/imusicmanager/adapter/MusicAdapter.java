package employee.karen.tarena.com.imusicmanager.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import employee.karen.tarena.com.imusicmanager.R;
import employee.karen.tarena.com.imusicmanager.entity.Music;
import employee.karen.tarena.com.imusicmanager.iconstant.IURL;
import employee.karen.tarena.com.imusicmanager.manager.MyImageLoader;
import employee.karen.tarena.com.imusicmanager.view.CircleImageView;

/**
 * Created by pjy on 2017/6/5.
 */

public class MusicAdapter extends
        BaseAdapter {
    public Context context;

    public MusicAdapter(Context context) {
        this.context = context;
    }

    private List<Music> musics = new ArrayList<Music>();

    public void addMusics(List<Music> musicList) {
        if (musicList != null) {
            this.musics.addAll(musicList);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Music getItem(int i) {
        return musics.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
       ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.inflate_music_item_layout,null);
            viewHolder.circleImageView_Album=
                    (CircleImageView)
                    view.findViewById(
                    R.id.imageView_MusicList_Album);
            viewHolder.textView_Author=
                    (TextView)
                    view.findViewById(
                    R.id.textView_MusicList_Author);
            viewHolder.textView_Composer=
                    (TextView) view.findViewById(
                    R.id.textView_MusicList_Composer);
            viewHolder.textView_SongName=
                    (TextView) view.findViewById(
                    R.id.textView_MusicList_SongName);
            viewHolder.textView_Singer=
                    (TextView) view.findViewById(
                    R.id.textView_MusicList_Singer);
            viewHolder.textView_Duration=
                    (TextView) view.findViewById(
                    R.id.textView_MusicList_Duration);
            view.setTag(viewHolder);

        }else{
            viewHolder= (ViewHolder) view.getTag();
        }
        Music music=getItem(i);
        String imageUrl= IURL.ROOT+music.getAlbumpic();
        Log.i("TAG:imageUrl",imageUrl);
        MyImageLoader.setBitmapFromCache(
                context,
                viewHolder.circleImageView_Album,
                imageUrl);
        //在音乐对象中封装了当前要适配的这个音乐对象它的专辑图片的路径
        //要想获得专辑图片需要在此发出网络请求，加载该对象的专辑图片

        viewHolder.textView_SongName.setText(music.getName());
        viewHolder.textView_Singer.setText(music.getSinger());
        viewHolder.textView_Author.setText(music.getAuthor());
        viewHolder.textView_Composer.setText(music.getComposer());
        viewHolder.textView_Duration.setText(music.getDurationtime());

        return view;
    }

    private class ViewHolder{
        CircleImageView circleImageView_Album;
        TextView textView_SongName;
        TextView textView_Singer;
        TextView textView_Author;
        TextView textView_Composer;
        TextView textView_Duration;


    }
}
