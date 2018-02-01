package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.MoodPicsActivity;
import com.phone.moran.activity.PaintActivity;
import com.phone.moran.activity.XinqingActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.model.PaintBack;
import com.phone.moran.presenter.IPaintActivityPresenter;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.RetrofitUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by ASUS on 2017/11/8.
 */
public class PaintActivityImpl extends BasePresenterImpl implements IPaintActivityPresenter {
    private PaintActivity paintActivity;
    private XinqingActivity xinqingActivity;
    private MoodPicsActivity moodActivity;
    private String token;

    public PaintActivityImpl(Context context, String token, PaintActivity paintActivity) {
        super(context);

        this.paintActivity = paintActivity;
        this.token = token;
    }

    public PaintActivityImpl(Context context, String token, XinqingActivity xinqingActivity) {
        super(context);

        this.xinqingActivity = xinqingActivity;
        this.token = token;
    }

    public PaintActivityImpl(Context context, String token, MoodPicsActivity moodActivity) {
        super(context);

        this.moodActivity = moodActivity;
        this.token = token;
    }

    @Override
    public void getMoodPaintDetail(int paint_id, int last_id) {

        Map<String, Integer> map = new HashMap<>();
        map.put("last_id", last_id);

        final Subscription subscription = RetrofitUtils.api()
                .getPaintDetail(paint_id, getBody(map))
                .map(new Func1<PaintBack, PaintBack>() {
                    @Override
                    public PaintBack call(PaintBack paintBack) {
                        return paintBack;
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
                        xinqingActivity.hidProgressDialog();
                        xinqingActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintBack allJourneysBack) {

                        xinqingActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "->>>>>>>>>>>>>" + JSON.toJSONString(allJourneysBack));

                            try {
                                allJourneysBack.getPaint_detail().setLast_id(allJourneysBack.getLast_id());
                                xinqingActivity.updatePaint(allJourneysBack.getPaint_detail());

                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            xinqingActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }


    @Override
    public void getMoodDetail(int paint_id, int last_id) {

        Map<String, Integer> map = new HashMap<>();
        map.put("last_id", last_id);

        final Subscription subscription = RetrofitUtils.api()
                .getPaintDetail(paint_id, getBody(map))
                .map(new Func1<PaintBack, PaintBack>() {
                    @Override
                    public PaintBack call(PaintBack paintBack) {
                        return paintBack;
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
                        moodActivity.hidProgressDialog();
                        moodActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintBack allJourneysBack) {

                        moodActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "->>>>>>>>>>>>>" + JSON.toJSONString(allJourneysBack));

                            try {
                                allJourneysBack.getPaint_detail().setLast_id(allJourneysBack.getLast_id());
                                moodActivity.updatePaint(allJourneysBack.getPaint_detail());

                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            moodActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }


    @Override
    public void getPaintDetail(int paint_id, int last_id) {

        Map<String, Integer> map = new HashMap<>();
        map.put("last_id", last_id);

        final Subscription subscription = RetrofitUtils.api()
                .getPaintDetail(paint_id, getBody(map))
                .map(new Func1<PaintBack, PaintBack>() {
                    @Override
                    public PaintBack call(PaintBack paintBack) {
                        return paintBack;
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
                        paintActivity.hidProgressDialog();
                        paintActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PaintBack allJourneysBack) {

                        paintActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "------画单详情-->>" + JSON.toJSONString(allJourneysBack));

                            try {
                                allJourneysBack.getPaint_detail().setLast_id(allJourneysBack.getLast_id());
                                paintActivity.updatePaint(allJourneysBack.getPaint_detail());

                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            paintActivity.showError(allJourneysBack.getErr());
                        }

                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void collect(int paintId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("paint_id", paintId);
        final Subscription subscription = RetrofitUtils.api()
                .collectPaint(getBody(map))
                .map(new Func1<Back, Back>() {
                    @Override
                    public Back call(Back back) {
                        return back;
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
                        paintActivity.hidProgressDialog();
                        paintActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        paintActivity.hidProgressDialog();

                        SLogger.d("<<", "---->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                paintActivity.collectSuccess();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }

                        } else {
                            paintActivity.showError(allJourneysBack.getErr());
                        }
                    }
                });

        addSubscription(subscription);
    }


    @Override
    public void upload(List<Integer> paintId) {
        Map<String, String> map = new HashMap<>();
        map.put("picture_ids", JSON.toJSONString(paintId));
        final Subscription subscription = RetrofitUtils.api()
                .play(getBody(map))
                .map(new Func1<Back, Back>() {
                    @Override
                    public Back call(Back back) {
                        return back;
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
                        paintActivity.hidProgressDialog();
                        paintActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        paintActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                paintActivity.collectSuccess();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }

                        } else {
                            paintActivity.showError(allJourneysBack.getErr());
                        }
                    }
                });

        addSubscription(subscription);
    }
}
