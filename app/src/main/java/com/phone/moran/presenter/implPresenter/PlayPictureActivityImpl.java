package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.phone.moran.activity.PlayPictureActivity;
import com.phone.moran.activity.XinqingActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.presenter.IPlayPictureActivityPresenter;
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
public class PlayPictureActivityImpl extends BasePresenterImpl implements IPlayPictureActivityPresenter {
    private PlayPictureActivity paintActivity;
    private XinqingActivity xinqingActivity;
    private String token;

    public PlayPictureActivityImpl(Context context, String token, PlayPictureActivity paintActivity) {
        super(context);

        this.paintActivity = paintActivity;
        this.token = token;
    }

    @Override
    public void addPlayMode(final int playMode) {
        Map<String, Integer> map = new HashMap<>();
        map.put("play_type", playMode);
        final Subscription subscription = RetrofitUtils.api()
                .modifyPlayType(getBody(map))
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
                                paintActivity.addPlayModeSuccess();
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
    public void addPlayTime(int playTime) {
        Map<String, Integer> map = new HashMap<>();
        map.put("play_type", playTime);
        final Subscription subscription = RetrofitUtils.api()
                .modifyPlayType(getBody(map))
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
                                paintActivity.uploadSuccess();
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
    public void addPlayLight(int playLight) {
        Map<String, Integer> map = new HashMap<>();
        map.put("play_type", playLight);
        final Subscription subscription = RetrofitUtils.api()
                .modifyPlayType(getBody(map))
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
                                paintActivity.uploadSuccess();
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
