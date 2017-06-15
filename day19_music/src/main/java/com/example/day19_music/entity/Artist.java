package com.example.day19_music.entity;

/**
 * Created by tarena on 2017/5/12.
 */

public class Artist {

    /**歌手名称*/
    private String name;

    /**此歌手的歌曲数量*/
    private int count;


    public Artist(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
