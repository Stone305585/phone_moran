package com.phone.moran.model;

/**
 * Created by Stone on 2017/12/2.
 * uint32 uin         = 1;      // 用户ID
 string head_url    = 2;      // 头像
 string background  = 3;      // 背景页面
 string nick_name   = 4;      // 昵称
 int32  gender      = 5;      // 性别 (1男, 2女, 3其他)
 uint32 birth_year  = 6;      // 出生年
 uint32 birth_month = 7;      // 出生月
 uint32 birth_day   = 8;      // 出生日
 string region      = 9;      // 地区
 string personal_profile =10; //个人简介
 string email_verified =11;   //是否通过 0，未通过， 1 通过
 string client_id   = 12;     //只用于用户设置信息：上传client_id
 */

public class User extends BaseModel {

    private int uin;
    private String head_url;
    private String background;
    private String nick_name;
    private int gender;
    private int birth_year;
    private int birth_month;
    private int birth_day;
    private String region;
    private String personal_profile;
    private String email_verified;
    private String client_id;

    public int getUin()
    {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    public String getHead_url() {
        return head_url;
    }

    public void setHead_url(String head_url) {
        this.head_url = head_url;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(int birth_year) {
        this.birth_year = birth_year;
    }

    public int getBirth_month() {
        return birth_month;
    }

    public void setBirth_month(int birth_month) {
        this.birth_month = birth_month;
    }

    public int getBirth_day() {
        return birth_day;
    }

    public void setBirth_day(int birth_day) {
        this.birth_day = birth_day;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPersonal_profile() {
        return personal_profile;
    }

    public void setPersonal_profile(String personal_profile) {
        this.personal_profile = personal_profile;
    }

    public String getEmail_verified() {
        return email_verified;
    }

    public void setEmail_verified(String email_verified) {
        this.email_verified = email_verified;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }
}
