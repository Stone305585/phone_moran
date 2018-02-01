package com.phone.moran.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/18.
 * 分类的model只有文字和图片
 */
public class Mood extends BaseModel {

    private String mood_name;

    private int res_id;

    private int mood_id;

    private List<Picture> pictures = new ArrayList<>();


    public String getMood_name() {
        return mood_name;
    }

    public void setMood_name(String mood_name) {
        this.mood_name = mood_name;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    public int getMood_id() {
        return mood_id;
    }

    public void setMood_id(int mood_id) {
        this.mood_id = mood_id;
    }
}
