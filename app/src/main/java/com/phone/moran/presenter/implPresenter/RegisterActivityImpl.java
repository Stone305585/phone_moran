package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.ResetPasswordActivity;
import com.phone.moran.activity.VerifyActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
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
    ResetPasswordActivity resetPasswordActivity;
    private String token;

    public RegisterActivityImpl(Context context, String token, VerifyActivity loginActivity) {
        super(context);

        this.loginActivity = loginActivity;
        this.token = token;
    }

    public RegisterActivityImpl(Context context, String token, ResetPasswordActivity resetPasswordActivity) {
        super(context);

        this.resetPasswordActivity = resetPasswordActivity;
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
        map.put("phone", phone);
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

    @Override
    public void resetPassword(String register_id, String password) {
        Map<String, String> map = new HashMap<>();
        map.put("register_id", register_id);
        map.put("password", password);
        final Subscription subscription = RetrofitUtils.api()
                .resetPassword(getBody(map))
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
                        resetPasswordActivity.hidProgressDialog();
                        resetPasswordActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBack allJourneysBack) {

                        SLogger.d("<<", "--reset password-->" + JSON.toJSONString(allJourneysBack));
                        resetPasswordActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                resetPasswordActivity.resetSuccess();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            resetPasswordActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void verifyCode(String register_id, String code) {
        Map<String, String> map = new HashMap<>();
        map.put("register_id", register_id);
        map.put("verify_code", code);
        final Subscription subscription = RetrofitUtils.api()
                .verifyCode(getBody(map))
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
                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        SLogger.d("<<", "---Verify-->" + JSON.toJSONString(allJourneysBack));
                        loginActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.verifySuccess();
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
    public void getCode(String register_id) {
        Map<String, String> map = new HashMap<>();
        map.put("register_id", register_id);
        final Subscription subscription = RetrofitUtils.api()
                .sendCode(getBody(map))
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
                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        loginActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.code();
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
