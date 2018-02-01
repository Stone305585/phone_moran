package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.ReadRecyclerActivity;
import com.phone.moran.activity.RecyclerActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.ClassicQBack;
import com.phone.moran.model.PaintListBack;
import com.phone.moran.presenter.IRecyclerActivityPresenter;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.RetrofitUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/11/8.
 */
public class RecyclerActivityImpl extends BasePresenterImpl implements IRecyclerActivityPresenter{

    private RecyclerActivity recyclerActivity;
    private String token;
    private ReadRecyclerActivity readRecyclerActivity;

    public RecyclerActivityImpl(Context context, String token, RecyclerActivity recyclerActivity) {
        super(context);

        this.recyclerActivity = recyclerActivity;
        this.token = token;
    }

    public RecyclerActivityImpl(Context context, String token, ReadRecyclerActivity readRecyclerActivity) {
        super(context);

        this.readRecyclerActivity = readRecyclerActivity;
        this.token = token;
    }

    @Override
    public void getPaintList(int type_id) {
        final Subscription subscription = RetrofitUtils.api()
                .getPaintList(type_id)
                .map(new Func1<PaintListBack, PaintListBack>() {
                    @Override
                    public PaintListBack call(PaintListBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaintListBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        recyclerActivity.hidProgressDialog();
                        recyclerActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintListBack allJourneysBack) {

                        recyclerActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "-->>" + JSON.toJSONString(allJourneysBack));

                            try {
                                recyclerActivity.updatePaintRecycler(allJourneysBack.getPaint_arry());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            recyclerActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void getCqList() {
        final Subscription subscription = RetrofitUtils.api()
                .getCqList()
                .map(new Func1<ClassicQBack, ClassicQBack>() {
                    @Override
                    public ClassicQBack call(ClassicQBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ClassicQBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        readRecyclerActivity.hidProgressDialog();
                        readRecyclerActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(ClassicQBack allJourneysBack) {

                        readRecyclerActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                readRecyclerActivity.updateCq(allJourneysBack.getClassic_quote());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            readRecyclerActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }
}
