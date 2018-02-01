package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/8.
 * 首页推荐的返回类
 */
public class PioneerHomeBack extends BaseModel {

    private int ret;

    private String err;

    private PioneerHomeArray pioneer_home;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public PioneerHomeArray getPioneer_home() {
        return pioneer_home;
    }

    public void setPioneer_home(PioneerHomeArray pioneer_home) {
        this.pioneer_home = pioneer_home;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
