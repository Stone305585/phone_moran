package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 注册的back
 */
public class RegisterBack extends BaseModel{


    private String token;
    private int uin;
    private int ret;
    private String err;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUin() {
        return uin;
    }

    public void setUin(int uin) {
        this.uin = uin;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
