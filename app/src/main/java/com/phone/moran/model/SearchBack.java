package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 搜索返回内容
 */
public class SearchBack extends BaseModel{

    private int ret;
    private String err;
    private List<Paint> paint_info;
    private List<Author> authro_info;
    private List<Picture> picture_info;

    public List<Author> getAuthro_info() {
        return authro_info;
    }

    public void setAuthro_info(List<Author> authro_info) {
        this.authro_info = authro_info;
    }

    public List<Paint> getPaint_info() {
        return paint_info;
    }

    public void setPaint_info(List<Paint> paint_info) {
        this.paint_info = paint_info;
    }

    public List<Picture> getPicture_info() {
        return picture_info;
    }

    public void setPicture_info(List<Picture> picture_info) {
        this.picture_info = picture_info;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
