package com.company.mplayer.entity;

/**
 * Created by pjy on 2017/5/12.
 */

public class Artist {//alt+insert 生成相关set/get,....
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

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", count=" + count +
                '}';
    }
}
