package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.CategoryDetailActivity;
import com.phone.moran.activity.GridPicActivity;
import com.phone.moran.activity.RegisterActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.PaintBack;
import com.phone.moran.model.PaintListBack;
import com.phone.moran.presenter.ICategoryDetailActivityPresenter;
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
public class CategoryDetailActivityImpl extends BasePresenterImpl implements ICategoryDetailActivityPresenter{

    private CategoryDetailActivity categoryDetailActivity;
    private GridPicActivity gridPicActivity;
    RegisterActivity registerActivity;
    private String token;

    public CategoryDetailActivityImpl(Context context, String token, CategoryDetailActivity categoryDetailActivity) {
        super(context);

        this.categoryDetailActivity = categoryDetailActivity;
        this.token = token;
    }

    public CategoryDetailActivityImpl(Context context, String token, GridPicActivity gridPicActivity) {
        super(context);

        this.gridPicActivity = gridPicActivity;
        this.token = token;
    }

    @Override
    public void updateFilter(int typeId) {
        final Subscription subscription = RetrofitUtils.api()
                .getClassifyList(typeId)
                .map(new Func1<PaintListBack, PaintListBack>() {
                    @Override
                    public PaintListBack call(PaintListBack RegisterBack) {
                        return RegisterBack;
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
                        categoryDetailActivity.hidProgressDialog();
                        categoryDetailActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintListBack allJourneysBack) {

                        categoryDetailActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                categoryDetailActivity.updateFilter(allJourneysBack.getPaints());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            categoryDetailActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void updateMain(int paint_id, int last_id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("last_id", last_id);

        final Subscription subscription = RetrofitUtils.api()
                .getPaintDetail(paint_id, getBody(map))
                .map(new Func1<PaintBack, PaintBack>() {
                    @Override
                    public PaintBack call(PaintBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaintBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryDetailActivity.hidProgressDialog();
                        categoryDetailActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintBack allJourneysBack) {

                        categoryDetailActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                allJourneysBack.getPaint_detail().setLast_id(allJourneysBack.getLast_id());
                                categoryDetailActivity.updateMain(allJourneysBack.getPaint_detail(), allJourneysBack.getPaint_detail().getPicture_info(), allJourneysBack.getLast_id());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            categoryDetailActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void updateMainCommon(int paint_id, int last_id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("last_id", last_id);

        final Subscription subscription = RetrofitUtils.api()
                .getPaintDetail(paint_id, getBody(map))
                .map(new Func1<PaintBack, PaintBack>() {
                    @Override
                    public PaintBack call(PaintBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PaintBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        categoryDetailActivity.hidProgressDialog();
                        categoryDetailActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintBack allJourneysBack) {

                        categoryDetailActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                allJourneysBack.getPaint_detail().setLast_id(allJourneysBack.getLast_id());
                                categoryDetailActivity.updateMain(allJourneysBack.getPaint_detail(), allJourneysBack.getPaint_detail().getPicture_info(), allJourneysBack.getLast_id());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            categoryDetailActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
