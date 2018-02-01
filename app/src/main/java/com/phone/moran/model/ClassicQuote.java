package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 读精彩
 */
public class ClassicQuote extends BaseModel{

    private int cq_id;
    private String cq_title;
    private String cq_content;
    private String cq_time;
    private String cq_img_url;
    private String cq_h5_url;

    public String getCq_content() {
        return cq_content;
    }

    public void setCq_content(String cq_content) {
        this.cq_content = cq_content;
    }

    public String getCq_h5_url() {
        return cq_h5_url;
    }

    public void setCq_h5_url(String cq_h5_url) {
        this.cq_h5_url = cq_h5_url;
    }

    public int getCq_id() {
        return cq_id;
    }

    public void setCq_id(int cq_id) {
        this.cq_id = cq_id;
    }

    public String getCq_img_url() {
        return cq_img_url;
    }

    public void setCq_img_url(String cq_img_url) {
        this.cq_img_url = cq_img_url;
    }

    public String getCq_time() {
        return cq_time;
    }

    public void setCq_time(String cq_time) {
        this.cq_time = cq_time;
    }

    public String getCq_title() {
        return cq_title;
    }

    public void setCq_title(String cq_title) {
        this.cq_title = cq_title;
    }
}
