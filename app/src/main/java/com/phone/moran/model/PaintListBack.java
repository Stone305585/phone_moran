package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/9.
 *
 * 画单列表back
 */
public class PaintListBack extends BaseModel{


    private List<Paint> paint_arry;
    private List<Paint> paints;

    private int ret;
    private String err;

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }

    public List<Paint> getPaint_arry() {
        return paint_arry;
    }

    public void setPaint_arry(List<Paint> paint_arry) {
        this.paint_arry = paint_arry;
    }

    public List<Paint> getPaints() {
        return paints;
    }

    public void setPaints(List<Paint> paints) {
        this.paints = paints;
    }

    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }
}
