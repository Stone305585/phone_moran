package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 大咖说
 */
public class MasterQuote extends BaseModel{

    private int mq_id;
    private String mq_content;
    private int flag;           //1:已经点赞  2：未点赞
    private int mq_love_num;


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMq_content() {
        return mq_content;
    }

    public void setMq_content(String mq_content) {
        this.mq_content = mq_content;
    }

    public int getMq_id() {
        return mq_id;
    }

    public void setMq_id(int mq_id) {
        this.mq_id = mq_id;
    }

    public int getMq_love_num() {
        return mq_love_num;
    }

    public void setMq_love_num(int mq_love_num) {
        this.mq_love_num = mq_love_num;
    }
}
