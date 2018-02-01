package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 画单详情back
 */
public class CategoryArtBack extends BaseModel{


    private List<Category> art_home_page;

    private int ret;
    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<Category> getArt_home_page() {
        return art_home_page;
    }

    public void setArt_home_page(List<Category> art_home_page) {
        this.art_home_page = art_home_page;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
