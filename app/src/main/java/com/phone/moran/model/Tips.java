package com.phone.moran.model;

/**
 * Created by Stone on 2017/11/29.
 */

public class Tips extends BaseModel {

    private String tips_content;
    private int tips_texture;
    private int tips_location;


    public String getTips_content() {
        return tips_content;
    }

    public void setTips_content(String tips_content) {
        this.tips_content = tips_content;
    }

    public int getTips_texture() {
        return tips_texture;
    }

    public void setTips_texture(int tips_texture) {
        this.tips_texture = tips_texture;
    }

    public int getTips_location() {
        return tips_location;
    }

    public void setTips_location(int tips_location) {
        this.tips_location = tips_location;
    }
}
