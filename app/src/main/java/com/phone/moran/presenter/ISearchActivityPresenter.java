package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 画作详情页
 */
public interface ISearchActivityPresenter {

    /**
     * 获取热词
     */
    void getHotSearch();

    /**
     * 获取搜索结果
     */
    void getSearchResult(String kw);
}
