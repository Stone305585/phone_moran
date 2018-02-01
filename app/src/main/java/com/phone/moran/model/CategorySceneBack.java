package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 画单详情back
 */
public class CategorySceneBack extends BaseModel{


    private List<Category> scene_home_page;

    private int ret;
    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<Category> getScene_home_page() {
        return scene_home_page;
    }

    public void setScene_home_page(List<Category> scene_home_page) {
        this.scene_home_page = scene_home_page;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
