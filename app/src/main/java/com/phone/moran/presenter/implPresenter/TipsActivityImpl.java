package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.RegisterActivity;
import com.phone.moran.activity.TipActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.presenter.ITipsActivityPresenter;
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
public class TipsActivityImpl extends BasePresenterImpl implements ITipsActivityPresenter{
    private TipActivity tipActivity;
    RegisterActivity registerActivity;
    private String token;

    public TipsActivityImpl(Context context, String token, TipActivity tipActivity) {
        super(context);

        this.tipActivity = tipActivity;
        this.token = token;
    }


    @Override
    public void uploadTips(String tipContent, int texture, int pos, int pushFlag) {

        switch (pos) {
            case 1:
                pos = 1;
                break;
            case 2:
                pos = 2;
                break;
            case 4:
                pos = 3;
                break;
            case 5:
                pos = 4;
                break;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("tips_content", tipContent);
        map.put("tips_texture", texture);
        map.put("tips_location", pos);
        map.put("flag", pushFlag);
        final Subscription subscription = RetrofitUtils.api()
                .addTips(getBody(map))
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
                        tipActivity.hidProgressDialog();
                        tipActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        tipActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                tipActivity.uploadTips();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            tipActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
