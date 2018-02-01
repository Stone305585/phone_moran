package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.SceneCFragment;
import com.phone.moran.model.CategorySceneBack;
import com.phone.moran.presenter.ISceneCFragmentPresenter;
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
public class SceneCFragmentImpl extends BasePresenterImpl implements ISceneCFragmentPresenter {
    private SceneCFragment sceneCFragment;
    private String token;

    public SceneCFragmentImpl(Context context, String token, SceneCFragment sceneCFragment) {
        super(context);

        this.sceneCFragment = sceneCFragment;
        this.token = token;
    }


    @Override
    public void getMainData() {

        final Subscription subscription = RetrofitUtils.api()
                .getSceneHome()
                .map(new Func1<CategorySceneBack, CategorySceneBack>() {
                    @Override
                    public CategorySceneBack call(CategorySceneBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategorySceneBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        sceneCFragment.hidProgressDialog();
                        sceneCFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(CategorySceneBack allJourneysBack) {

                        sceneCFragment.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));
                            try {
                                sceneCFragment.updateMain(allJourneysBack);

                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            sceneCFragment.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
