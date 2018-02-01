package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/18.
 * 分类的model只有文字和图片
 */
public class Category extends BaseModel {

    private String type_name;

    private String type_id;

    private List<Paint> paints;

    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }

    public Category() {

    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }
}
