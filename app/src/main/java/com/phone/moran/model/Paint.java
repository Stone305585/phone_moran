package com.phone.moran.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 首页的画作
 */
public class Paint extends BaseModel {

    private int paint_id;
    private int picture_num;
    private int read_num;
    private int collect_num;
    private String paint_title;
    private String title_url;
    private String paint_detail;
    private int flag;
    private String paint_name;
    private String img_url;
    private String title_detail_url;

    private int last_id;

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
    }

    //用于标记进入新建画单
    private boolean myDefault;

    public boolean isMyDefault() {
        return myDefault;
    }

    public void setMyDefault(boolean myDefault) {
        this.myDefault = myDefault;
    }

    public String getTitle_detail_url() {
        return title_detail_url;
    }

    public void setTitle_detail_url(String title_detail_url) {
        this.title_detail_url = title_detail_url;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getPaint_name() {
        return paint_name;
    }

    public void setPaint_name(String paint_name) {
        this.paint_name = paint_name;
    }

    private boolean selected;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    private List<Picture>  picture_info = new ArrayList<>();

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public int getPaint_id() {
        return paint_id;
    }

    public void setPaint_id(int paint_id) {
        this.paint_id = paint_id;
    }

    public String getPaint_title() {
        return paint_title;
    }

    public void setPaint_title(String paint_title) {
        this.paint_title = paint_title;
    }

    public int getPicture_num() {
        return picture_num;
    }

    public void setPicture_num(int picture_num) {
        this.picture_num = picture_num;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public String getTitle_url() {
        return title_url;
    }

    public void setTitle_url(String title_url) {
        this.title_url = title_url;
    }

    public String getPaint_detail() {
        return paint_detail;
    }

    public void setPaint_detail(String paint_detail) {
        this.paint_detail = paint_detail;
    }

    public List<Picture> getPicture_info() {
        return picture_info;
    }

    public void setPicture_info(List<Picture> picture_info) {
        this.picture_info = picture_info;
    }
}
