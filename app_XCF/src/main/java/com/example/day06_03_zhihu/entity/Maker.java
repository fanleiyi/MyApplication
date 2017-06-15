package com.example.day06_03_zhihu.entity;

/**
 * Created by tarena on 2017/5/25.
 */

public class Maker {

    private int pngId;

    private int photoId;

    private String title;

    private String body;

    private String date;

    private String name;

    private int type;

    public Maker() {
    }

    public Maker(int pngId, int photoId, String title, String body, String date, String name, int type) {
        this.pngId = pngId;
        this.photoId = photoId;
        this.title = title;
        this.body = body;
        this.date = date;
        this.name = name;
        this.type = type;
    }

    public void setPngId(int pngId) {
        this.pngId = pngId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPngId() {
        return pngId;
    }

    public int getPhotoId() {
        return photoId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
