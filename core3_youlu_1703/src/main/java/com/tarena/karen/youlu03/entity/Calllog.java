package com.tarena.karen.youlu03.entity;

/**
 * Created by pjy on 2017/5/19.
 * 用来封装通话记录的实体类
 */

public class Calllog {
    private int _id;//编号
    private String name;//姓名
    private String phone;//电话号码
    private long date;//通话日期
    private String dateStr;//格式化后的日期字符串
    private int type;//通话类型
    private int photoId;//头像的编号

    public Calllog() {
    }

    public Calllog(int _id, String name, String phone, long date, String dateStr, int type, int photoId) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.date = date;
        this.dateStr = dateStr;
        this.type = type;
        this.photoId = photoId;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "Calllog{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", date=" + date +
                ", dateStr='" + dateStr + '\'' +
                ", type=" + type +
                ", photoId=" + photoId +
                '}';
    }
}
