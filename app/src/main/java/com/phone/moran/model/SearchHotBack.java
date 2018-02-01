package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 读精彩
 */
public class SearchHotBack extends BaseModel{

    private int ret;
    private String err;
    private List<String> hot_words;

    public List<String> getHot_words() {
        return hot_words;
    }

    public void setHot_words(List<String> hot_words) {
        this.hot_words = hot_words;
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
