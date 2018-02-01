package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 一副画作
 * int32  picture_id   = 1;       //作品id
 string picture_url  = 3;       //url地址
 string author       = 4;       //作者
 string time         = 5;       //创作年代
 string size         = 6;       //尺寸
 string detail       = 7；      //简介
 string title;
 */
public class Picture extends BaseModel{

    public static final int COLLECT = 1;
    public static final int UNCOLLECT = 0;


    private int picture_id;
    private int picture_type;
    private String picture_url;
    private String author;
    private String time;
    private String detail;
    private String title;
    private String detail_url;
    private int flag;  //1:收藏，0：未收藏

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getDetail_url() {
        return detail_url;
    }

    public void setDetail_url(String detail_url) {
        this.detail_url = detail_url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(int picture_id) {
        this.picture_id = picture_id;
    }

    public int getPicture_type() {
        return picture_type;
    }

    public void setPicture_type(int picture_type) {
        this.picture_type = picture_type;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }
}
