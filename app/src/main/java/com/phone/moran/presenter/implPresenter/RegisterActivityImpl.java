package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.phone.moran.activity.VerifyActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.presenter.IRegisterActivityPresenter;
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
public class RegisterActivityImpl extends BasePresenterImpl implements IRegisterActivityPresenter{
    private VerifyActivity loginActivity;
    private String token;

    public RegisterActivityImpl(Context context, String token, VerifyActivity loginActivity) {
        super(context);

        this.loginActivity = loginActivity;
        this.token = token;
    }



    @Override
    public void register(String register_id, String password, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("email", register_id);
        map.put("password", password);
        map.put("verify_code", code);
        final Subscription subscription = RetrofitUtils.api()
                .register(getBody(map))
                .map(new Func1<RegisterBack, RegisterBack>() {
                    @Override
                    public RegisterBack call(RegisterBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBack allJourneysBack) {

                        loginActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.registerSuccess(allJourneysBack);
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            loginActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }


    @Override
    public void registerMobile(String phone, String password, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("email", phone);
        map.put("password", password);
        map.put("verify_code", code);
        final Subscription subscription = RetrofitUtils.api()
                .registerMobile(getBody(map))
                .map(new Func1<RegisterBack, RegisterBack>() {
                    @Override
                    public RegisterBack call(RegisterBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegisterBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBack allJourneysBack) {

                        loginActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.registerSuccess(allJourneysBack);
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            loginActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
