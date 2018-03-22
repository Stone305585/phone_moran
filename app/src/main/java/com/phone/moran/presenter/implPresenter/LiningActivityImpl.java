package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.LiningActivity;
import com.phone.moran.activity.RegisterActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.presenter.ILiningActivityPresenter;
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
public class LiningActivityImpl extends BasePresenterImpl implements ILiningActivityPresenter{
    private LiningActivity liningActivity;
    RegisterActivity registerActivity;
    private String token;

    public LiningActivityImpl(Context context, String token, LiningActivity liningActivity) {
        super(context);

        this.liningActivity = liningActivity;
        this.token = token;
    }

    public LiningActivityImpl(Context context, String token, RegisterActivity registerActivity) {
        super(context);
        this.registerActivity = registerActivity;
        this.token = token;
    }


    @Override
    public void uploadLining(int frame_colour, int frame_size) {

            Map<String, Integer> map = new HashMap<>();
            map.put("frame_colour", frame_colour);
            map.put("frame_size", ++frame_size);
            final Subscription subscription = RetrofitUtils.api()
                    .addFrame(getBody(map))
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
                            liningActivity.hidProgressDialog();
                            liningActivity.showError(e.getMessage());
                        }

                        @Override
                        public void onNext(Back allJourneysBack) {

                            liningActivity.hidProgressDialog();
                            SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                            if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                                try {
                                    liningActivity.uploadLining();
                                } catch (ClassCastException e) {
                                    SLogger.d("<<", "异常");
                                    e.printStackTrace();
                                }

                            } else {
                                liningActivity.showError(allJourneysBack.getErr());
                            }


                        }
                    });

            addSubscription(subscription);
    }
}
