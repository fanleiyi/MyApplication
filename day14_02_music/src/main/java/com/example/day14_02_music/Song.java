package com.example.day14_02_music;

/**
 * Created by tarena on 2017/5/4.
 */

public class Song {
    /**歌曲名称*/
    private String title;
    /**歌手名字*/
    private String artist;
    /**歌曲路径*/
    private String data;
    //....
    public Song (String title,String artist,String data) {
        this.title = title;
        this.artist = artist;
        this.data = data;

    }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getDateta() {
        return data;
    }
}
