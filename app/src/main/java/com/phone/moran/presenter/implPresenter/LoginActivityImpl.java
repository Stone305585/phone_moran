package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.LoginActivity;
import com.phone.moran.activity.RegisterActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Back;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.ThirdLoginInfo;
import com.phone.moran.model.UserBack;
import com.phone.moran.presenter.ILoginActivityPresenter;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.net.RetrofitUtils;
import com.phone.moran.wxapi.WXEntryActivity;

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
public class LoginActivityImpl extends BasePresenterImpl implements ILoginActivityPresenter{
    private LoginActivity loginActivity;
    private WXEntryActivity wxEntryActivity;
    RegisterActivity registerActivity;
    private String token;

    public LoginActivityImpl(Context context, String token, LoginActivity loginActivity) {
        super(context);

        this.loginActivity = loginActivity;
        this.token = token;
    }

    public LoginActivityImpl(Context context, String token, RegisterActivity registerActivity) {
        super(context);
        this.registerActivity = registerActivity;
        this.token = token;
    }

    public LoginActivityImpl(Context context,  WXEntryActivity wxEntryActivity) {
        super(context);

        this.wxEntryActivity = wxEntryActivity;
        this.token = token;
    }


    @Override
    public void login(String register_id, String password) {

        loginActivity.showProgressDialog();
        Map<String, String> map = new HashMap<>();
        map.put("register_id", register_id);
        map.put("password", password);
        final Subscription subscription = RetrofitUtils.api()
                .login(getBody(map))
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
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.loginSuccess(allJourneysBack);
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
                        registerActivity.hidProgressDialog();
                        registerActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        registerActivity.hidProgressDialog();

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                registerActivity.code();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            registerActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void getUserInfo() {
        final Subscription subscription = RetrofitUtils.api()
                .getUserInfo()
                .map(new Func1<UserBack, UserBack>() {
                    @Override
                    public UserBack call(UserBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserBack allJourneysBack) {

                        loginActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.updateUserInfo(allJourneysBack.getUser_info());
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
    public void thirdBind(ThirdLoginInfo t) {
        loginActivity.showProgressDialog();
        Map<String, Object> map = new HashMap<>();
        map.put("auth_token", t.getAuth_token());
        map.put("register_type", t.getRegister_type());

        final Subscription subscription = RetrofitUtils.api()
                .bindThird(getBody(map))
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
                        SLogger.d("<<", "-->" + JSON.toJSONString(e.getMessage()));

                        loginActivity.hidProgressDialog();
                        loginActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(RegisterBack allJourneysBack) {

                        loginActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                loginActivity.loginSuccess(allJourneysBack);
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
