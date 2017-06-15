package com.example.day06_03_zhihu.entity;

/**
 * Created by tarena on 2017/5/25.
 */

public class XCF {

    // 图片
    private int print;
    // 标题
    private String title;

    public XCF() {
    }

    public XCF(int print, String title) {
        this.print = print;
        this.title = title;
    }

    public void setPrint(int print) {
        this.print = print;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrint() {
        return print;
    }

    public String getTitle() {
        return title;
    }
}
