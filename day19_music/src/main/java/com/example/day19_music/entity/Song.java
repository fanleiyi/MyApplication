package com.example.day19_music.entity;

/**
 * Created by tarena on 2017/5/4.
 */

public class Song {

    private  int id;
    /**歌曲名称*/
    private String title;
    /**歌手名字*/
    private String artist;
    /**歌曲路径*/
    private String data;

    public Song() {
    }

    //....
    public Song(int id,String title, String artist, String data) {
        this.id =id;
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
    public String getData() {
        return data;
    }
    public long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", data='" + data + '\'' +
                '}';
    }


}
