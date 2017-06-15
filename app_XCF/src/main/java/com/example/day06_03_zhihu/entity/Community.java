package com.example.day06_03_zhihu.entity;

/**
 * Created by tarena on 2017/5/25.
 */

public class Community {

    private int photoId;

    private String name;

    private String body;

    private String date;


    public Community() {
    }

    public Community(int photoId, String name, String body, String date) {
        this.photoId = photoId;
        this.name = name;
        this.body = body;
        this.date = date;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }
}
