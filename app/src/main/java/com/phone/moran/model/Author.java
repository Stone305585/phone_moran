package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/11.
 * 作者信息
 * "authro_name": "张大仙",
 "img_url": "https://s3.cn-north-1.amazonaws.com.cn/zhangxj/33.jpg",
 "paint_id": 2,
 "authro_id": 1
 */

public class Author extends BaseModel{

    private int authro_id;
    private int paint_id;
    private String img_url;
    private String authro_name;


    public int getAuthro_id() {
        return authro_id;
    }

    public void setAuthro_id(int authro_id) {
        this.authro_id = authro_id;
    }

    public String getAuthro_name() {
        return authro_name;
    }

    public void setAuthro_name(String authro_name) {
        this.authro_name = authro_name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public int getPaint_id() {
        return paint_id;
    }

    public void setPaint_id(int paint_id) {
        this.paint_id = paint_id;
    }
}
