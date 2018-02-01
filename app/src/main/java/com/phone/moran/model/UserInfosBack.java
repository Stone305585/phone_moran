package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 */
public class UserInfosBack extends BaseModel{

    private int ret;
    private String err;
    private List<User> user_infos;

    public List<User> getUser_infos() {
        return user_infos;
    }

    public void setUser_infos(List<User> user_infos) {
        this.user_infos = user_infos;
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
