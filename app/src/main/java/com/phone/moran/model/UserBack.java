package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 */
public class UserBack extends BaseModel{

    private int ret;
    private String err;
    private User user_info;

    public User getUser_info() {
        return user_info;
    }

    public void setUser_info(User user_info) {
        this.user_info = user_info;
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
