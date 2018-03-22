package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.SuggestActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.presenter.ISuggestActivityPresenter;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.RetrofitUtils;

import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/11/8.
 */
public class SuggestActivityImpl extends BasePresenterImpl implements ISuggestActivityPresenter {

    private SuggestActivity suggestActivity;
    private String token;

    public SuggestActivityImpl(Context context, String token, SuggestActivity suggestActivity) {
        super(context);

        this.suggestActivity = suggestActivity;
        this.token = token;
    }


    @Override
    public void postSuggest(String des) {
        Map<String, String> map = new HashMap<>();
        map.put("content", des);
        final Subscription subscription = RetrofitUtils.api()
                .suggest(getBody(map))
                .map(new Func1<Back, Back>() {
                    @Override
                    public Back call(Back RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Back>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        suggestActivity.hidProgressDialog();
                        suggestActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        suggestActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                suggestActivity.success();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            suggestActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
