package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 * 获取读精彩列表
 */
public class ClassicQBack {

    private int ret;
    private String err;

    private List<ClassicQuote> classic_quote;

    public List<ClassicQuote> getClassic_quote() {
        return classic_quote;
    }

    public void setClassic_quote(List<ClassicQuote> classic_quote) {
        this.classic_quote = classic_quote;
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
