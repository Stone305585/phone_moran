package com.phone.moran.presenter.implPresenter;

import android.content.Context;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.UserInfoActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.model.User;
import com.phone.moran.model.UserBack;
import com.phone.moran.presenter.IUserActivityPresenter;
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
public class UserActivityImpl extends BasePresenterImpl implements IUserActivityPresenter {
    private UserInfoActivity userInfoActivity;
    private String token;

    public UserActivityImpl(Context context, String token, UserInfoActivity userInfoActivity) {
        super(context);

        this.userInfoActivity = userInfoActivity;
        this.token = token;
    }


    @Override
    public void uploadUser(User user) {

        Map<String, Object> map = new HashMap<>();
        if (!TextUtils.isEmpty(user.getNick_name()))
            map.put("nick_name", user.getNick_name());
        if (!TextUtils.isEmpty(user.getBackground()))
            map.put("background", user.getBackground());
        if (!TextUtils.isEmpty(user.getHead_url()))
            map.put("head_url", user.getHead_url());
        if (!TextUtils.isEmpty(user.getPersonal_profile()))
            map.put("personal_profile", user.getPersonal_profile());
        if (user.getBirth_day() != 0) {
            map.put("birth_day", user.getBirth_day());
            map.put("birth_month", user.getBirth_month());
            map.put("birth_year", user.getBirth_year());
        }
        if (!TextUtils.isEmpty(user.getRegion()))
            map.put("region", user.getRegion());

        if (user.getGender() != 0) {
            map.put("gender", user.getGender());
        }

        map.put("client_id", user.getClient_id());
        final Subscription subscription = RetrofitUtils.api()
                .setUser(getBody(map))
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
                        userInfoActivity.hidProgressDialog();
                        userInfoActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserBack allJourneysBack) {

                        userInfoActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {

                                userInfoActivity.save(allJourneysBack.getUser_info());
                                SLogger.d("<<", "---->>更新成功");
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            userInfoActivity.showError(allJourneysBack.getErr());
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
                        userInfoActivity.hidProgressDialog();
                        userInfoActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserBack allJourneysBack) {

                        userInfoActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                userInfoActivity.updateUserInfo(allJourneysBack.getUser_info());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            userInfoActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
