package com.phone.moran.presenter.implView;

import com.phone.moran.model.SearchBack;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 搜索页面
 */
public interface ISearchActivity extends IBaseFragment{

    void getSearchHot(List<String> hotwords);

    void searchSuccess(SearchBack searchBack);

}
