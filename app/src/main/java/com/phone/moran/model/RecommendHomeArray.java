package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 首页推荐的集合类
 **/
public class RecommendHomeArray extends BaseModel {

    private List<Paint> new_arry;

    private List<Paint> banner;

    private List<Paint> hot_arry;

    public List<Paint> getBanner() {
        return banner;
    }

    public void setBanner(List<Paint> banner) {
        this.banner = banner;
    }

    public List<Paint> getHot_arry() {
        return hot_arry;
    }

    public void setHot_arry(List<Paint> hot_arry) {
        this.hot_arry = hot_arry;
    }

    public List<Paint> getNew_arry() {
        return new_arry;
    }

    public void setNew_arry(List<Paint> new_arry) {
        this.new_arry = new_arry;
    }
}
