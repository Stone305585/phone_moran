package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.phone.moran.activity.SearchActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.SearchBack;
import com.phone.moran.model.SearchHotBack;
import com.phone.moran.presenter.ISearchActivityPresenter;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.ApiHelper;
import com.phone.moran.tools.net.RetrofitUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/11/8.
 */
public class SearchActivityImpl extends BasePresenterImpl implements ISearchActivityPresenter{

    private SearchActivity searchActivity;
    private String token;

    public SearchActivityImpl(Context context, String token, SearchActivity SearchActivity) {
        super(context);

        this.searchActivity = SearchActivity;
        this.token = token;
    }

    @Override
    public void getHotSearch() {
        final Subscription subscription = RetrofitUtils.api()
                .getSearchHot()
                .map(new Func1<SearchHotBack, SearchHotBack>() {
                    @Override
                    public SearchHotBack call(SearchHotBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchHotBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        searchActivity.hidProgressDialog();
                        searchActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(SearchHotBack allJourneysBack) {

                        searchActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                diskLruCacheHelper.put(ApiHelper.SEARCHHOT, JSON.toJSONString(allJourneysBack));
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                                allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.SEARCHHOT), SearchHotBack.class);
                            }

                        } else {
                            searchActivity.showError(allJourneysBack.getErr());
                            allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.SEARCHHOT), SearchHotBack.class);
                        }
                        searchActivity.getSearchHot(allJourneysBack.getHot_words());

                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void getSearchResult(String kw) {
        final Subscription subscription = RetrofitUtils.api()
                .search(kw)
                .map(new Func1<SearchBack, SearchBack>() {
                    @Override
                    public SearchBack call(SearchBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        searchActivity.hidProgressDialog();
                        searchActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(SearchBack allJourneysBack) {

                        searchActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                searchActivity.searchSuccess(allJourneysBack);
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            searchActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }
}
