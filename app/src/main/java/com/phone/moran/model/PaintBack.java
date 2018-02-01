package com.phone.moran.model;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 画单详情back
 */
public class PaintBack extends BaseModel{


    private Paint paint_detail;

    private int ret;
    private String err;

    private int last_id;

    public int getLast_id() {
        return last_id;
    }

    public void setLast_id(int last_id) {
        this.last_id = last_id;
    }

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public Paint getPaint_detail() {
        return paint_detail;
    }

    public void setPaint_detail(Paint paint_detail) {
        this.paint_detail = paint_detail;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
