package com.tarena.karen.youlu03.entity;

/**
 * Created by pjy on 2017/5/17.
 * 用户来封装联系人基本信息的实体类
 */

public class Contact {
    private int _id;
    private String name;//联系的姓名
    private String phone;//电话号码
    private String address;//地址
    private String email;//电子邮箱
    private int photoId;//头像编号

    public Contact() {
    }

    public Contact(int _id, String name, String phone, String address, String email, int photoId) {
        this._id = _id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", photoId=" + photoId +
                '}';
    }
}
