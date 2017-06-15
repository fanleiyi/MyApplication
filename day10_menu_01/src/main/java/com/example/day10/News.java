package com.example.day10;

/**
 * Created by pjy on 2017/4/21.
 */

public class News {
    private int logo;
    private String title;
    //....
    public News(int logo,String title){
        this.logo=logo;
        this.title=title;
    }
    public int getLogo() {
        return logo;
    }

    public String getTitle() {
        return title;
    }
}
