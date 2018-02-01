package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ScanCodeFragment;
import com.phone.moran.model.Back;
import com.phone.moran.presenter.IScanCodeActivityPresenter;
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
public class ScanCodeActivityImpl extends BasePresenterImpl implements IScanCodeActivityPresenter{
    private ScanCodeFragment scanCodeFragment;
    private String token;

    public ScanCodeActivityImpl(Context context, String token, ScanCodeFragment scanCodeFragment) {
        super(context);

        this.scanCodeFragment = scanCodeFragment;
        this.token = token;
    }


    /**
     * int32   uin    = 1;          //申请人的uin
     int32   status = 2;          //处理结果：1同意，2拒绝
     * @param deviceId
     */
    @Override
    public void bind(String deviceId) {

        scanCodeFragment.showProgressDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("device_id", deviceId);
        final Subscription subscription = RetrofitUtils.api()
                .bind(getBody(map))
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
                        scanCodeFragment.hidProgressDialog();
                        scanCodeFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        scanCodeFragment.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                scanCodeFragment.bandFinish();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            scanCodeFragment.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

}
