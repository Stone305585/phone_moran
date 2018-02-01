package com.phone.moran.model;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 首页推荐的集合类
 **/
public class PioneerHomeArray extends BaseModel {

    private List<ClassicQuote> classic_quote;

    private MasterQuote master_quote;

    private List<Paint> banner;

    public List<Paint> getBanner() {
        return banner;
    }

    public void setBanner(List<Paint> banner) {
        this.banner = banner;
    }


    public List<ClassicQuote> getClassic_quote() {
        return classic_quote;
    }

    public void setClassic_quote(List<ClassicQuote> classic_quote) {
        this.classic_quote = classic_quote;
    }


    public MasterQuote getMaster_quote() {
        return master_quote;
    }

    public void setMaster_quote(MasterQuote master_quote) {
        this.master_quote = master_quote;
    }
}
