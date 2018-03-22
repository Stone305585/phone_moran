package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.RecommendFragment;
import com.phone.moran.model.RecommendHomeBack;
import com.phone.moran.presenter.IRecommendFragmentPresenter;
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
public class RecommendFragmentImpl extends BasePresenterImpl implements IRecommendFragmentPresenter{

    private RecommendFragment recommendFragment;
    private String token;

    public RecommendFragmentImpl(Context context, String token, RecommendFragment recommendFragment) {
        super(context);

        this.recommendFragment = recommendFragment;
        this.token = token;
    }


    @Override
    public void getMainData() {

        final Subscription subscription = RetrofitUtils.api()
                .getRecommendMain()
                .map(new Func1<RecommendHomeBack, RecommendHomeBack>() {
                    @Override
                    public RecommendHomeBack call(RecommendHomeBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendHomeBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recommendFragment.hidProgressDialog();
                        recommendFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RecommendHomeBack allJourneysBack) {

                        SLogger.d("<<", "--recommend-->>>" + JSON.toJSONString(allJourneysBack));

                        recommendFragment.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                diskLruCacheHelper.put(ApiHelper.GETMAINDATA, JSON.toJSONString(allJourneysBack));
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                                allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.GETMAINDATA), RecommendHomeBack.class);
                            }

                        } else {
                            recommendFragment.showError(allJourneysBack.getErr());
                            allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.GETMAINDATA), RecommendHomeBack.class);
                        }
                        recommendFragment.updateMain(allJourneysBack.getRecommend_home_page());

                    }
                });

        addSubscription(subscription);
    }
}
