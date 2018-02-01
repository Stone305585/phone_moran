package com.phone.moran.model;

import java.util.List;

/**
 * Created by Stone on 2017/11/26.
 *
 * 新增画作推送：
 * {
 "opr_type": 5,
 "picture_detail": {
 "picture_id": 1,
 "author": null,
 "picture_url": "https://s3.cn-north-1.amazonaws.com.cn/zhangxj/人物故事图10张++南华秋水.jpg",
 "detail": "人物故事：南华秋水",
 "time": "2101年",
 "size": "1700*123"
 }
 }
 */

public class PushBack extends BaseModel {

    public static final int PLAY = 1;
    public static final int MODE = 3;
    public static final int TIPS = 4;
    public static final int LINING = 5;
    public static final int PICTURE = 6;


    //亮度、播放时间、播放顺序  都放在播放模式里面

    private int opr_type;  //1:播放2： 3：播放模式  4：tips  5：内衬  6:画作
    private int frame_colour;
    private int frame_size;
    private int play_type;
    private int tips_texure;
    private int tips_location;
    private String tips_content;
    private List<Integer> pictures;
//    private TitleInfo title_info;
    private Picture picture_detail;
    private int flag;//tips 删除和显示  1:显示 2：删除

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getOpr_type() {
        return opr_type;
    }

    public void setOpr_type(int opr_type) {
        this.opr_type = opr_type;
    }

    public int getFrame_colour() {
        return frame_colour;
    }

    public void setFrame_colour(int frame_colour) {
        this.frame_colour = frame_colour;
    }

    public int getFrame_size() {
        return frame_size;
    }

    public void setFrame_size(int frame_size) {
        this.frame_size = frame_size;
    }

    public int getPlay_type() {
        return play_type;
    }

    public void setPlay_type(int play_type) {
        this.play_type = play_type;
    }

    public int getTips_texure() {
        return tips_texure;
    }

    public void setTips_texure(int tips_texure) {
        this.tips_texure = tips_texure;
    }

    public int getTips_location() {
        return tips_location;
    }

    public void setTips_location(int tips_location) {
        this.tips_location = tips_location;
    }

    public String getTips_content() {
        return tips_content;
    }

    public void setTips_content(String tips_content) {
        this.tips_content = tips_content;
    }

//    public TitleInfo getTitle_info() {
//        return title_info;
//    }
//
//    public void setTitle_info(TitleInfo title_info) {
//        this.title_info = title_info;
//    }

    public List<Integer> getPictures() {
        return pictures;
    }

    public void setPictures(List<Integer> pictures) {
        this.pictures = pictures;
    }

    public Picture getPicture_detail() {
        return picture_detail;
    }

    public void setPicture_detail(Picture picture_detail) {
        this.picture_detail = picture_detail;
    }
}
