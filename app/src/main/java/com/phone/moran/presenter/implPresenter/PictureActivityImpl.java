package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.phone.moran.activity.PictureActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.PictureBack;
import com.phone.moran.presenter.IPictureActivityPresenter;
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
public class PictureActivityImpl extends BasePresenterImpl implements IPictureActivityPresenter{

    private PictureActivity pictureActivity;
    private String token;

    public PictureActivityImpl(Context context, String token, PictureActivity pictureActivity) {
        super(context);

        this.pictureActivity = pictureActivity;
        this.token = token;
    }

    @Override
    public void getPictureDetail(int picture_id) {
        final Subscription subscription = RetrofitUtils.api()
                .getPictureDetail(picture_id)
                .map(new Func1<PictureBack, PictureBack>() {
                    @Override
                    public PictureBack call(PictureBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PictureBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        pictureActivity.hidProgressDialog();
                        pictureActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(PictureBack allJourneysBack) {

                        pictureActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                pictureActivity.updatePicture(allJourneysBack.getPicture_detail());

//                                diskLruCacheHelper.put(ApiHelper.GETMAINDATA, JSON.toJSONString(allJourneysBack));
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
//                                allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.GETMAINDATA), PictureBack.class);
                            }

                        } else {
                            pictureActivity.showError(allJourneysBack.getErr());
//                            allJourneysBack = JSONObject.parseObject(diskLruCacheHelper.getAsString(ApiHelper.GETMAINDATA), PictureBack.class);
                        }
//                        pictureActivity.updatePicture(allJourneysBack.getPicture_detail());

                    }
                });

        addSubscription(subscription);
    }

}
