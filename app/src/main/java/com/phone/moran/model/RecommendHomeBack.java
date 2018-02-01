package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/8.
 * 首页推荐的返回类
 */
public class RecommendHomeBack extends BaseModel {

    private int ret;

    private String err;

    private RecommendHomeArray recommend_home_page;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public RecommendHomeArray getRecommend_home_page() {
        return recommend_home_page;
    }

    public void setRecommend_home_page(RecommendHomeArray recommend_home_page) {
        this.recommend_home_page = recommend_home_page;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
