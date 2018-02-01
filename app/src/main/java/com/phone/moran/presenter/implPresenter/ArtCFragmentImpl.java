package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ArtCFragment;
import com.phone.moran.model.CategoryArtBack;
import com.phone.moran.presenter.IArtCFragmentPresenter;
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
public class ArtCFragmentImpl extends BasePresenterImpl implements IArtCFragmentPresenter{
    private ArtCFragment artCFragment;
    private String token;

    public ArtCFragmentImpl(Context context, String token, ArtCFragment artCFragment) {
        super(context);

        this.artCFragment = artCFragment;
        this.token = token;
    }


    @Override
    public void getMainData() {

        final Subscription subscription = RetrofitUtils.api()
                .getArtHome()
                .map(new Func1<CategoryArtBack, CategoryArtBack>() {
                    @Override
                    public CategoryArtBack call(CategoryArtBack allJourneysBack) {
                        return allJourneysBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CategoryArtBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        artCFragment.hidProgressDialog();
                        artCFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(CategoryArtBack allJourneysBack) {

                        artCFragment.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));
                            try {
                                artCFragment.updateMain(allJourneysBack);

                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            artCFragment.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
