package com.phone.moran.presenter.implPresenter;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.phone.moran.activity.UserBandActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.DevicesFragment;
import com.phone.moran.model.Back;
import com.phone.moran.model.DeviceInfoBack;
import com.phone.moran.model.UserInfosBack;
import com.phone.moran.presenter.IUserBandActivityPresenter;
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
public class UserBandActivityImpl extends BasePresenterImpl implements IUserBandActivityPresenter{
    private UserBandActivity userBandActivity;
    private DevicesFragment devicesFragment;
    private String token;

    public UserBandActivityImpl(Context context, String token, UserBandActivity userBandActivity) {
        super(context);

        this.userBandActivity = userBandActivity;
        this.token = token;
    }

    public UserBandActivityImpl(Context context, DevicesFragment devicesFragment) {
        super(context);

        this.devicesFragment = devicesFragment;
    }


    /**
     * int32   uin    = 1;          //申请人的uin
     int32   status = 2;          //处理结果：1同意，2拒绝
     * @param deviceId
     * @param status
     * @param uin
     */
    @Override
    public void process(String deviceId, final int status, int uin) {

        Map<String, Object> map = new HashMap<>();
        map.put("device_id", deviceId);
        map.put("status", status);
        map.put("uin", uin);
        final Subscription subscription = RetrofitUtils.api()
                .processDeviceBind(getBody(map))
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
                        userBandActivity.hidProgressDialog();
                        userBandActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        userBandActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                userBandActivity.approve();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            userBandActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void remove(String deviceId) {

        Map<String, Object> map = new HashMap<>();
        map.put("device_id", deviceId);
        final Subscription subscription = RetrofitUtils.api()
                .removeUser(getBody(map))
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
                        devicesFragment.hidProgressDialog();
                        devicesFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(Back allJourneysBack) {

                        devicesFragment.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                devicesFragment.remove();
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            devicesFragment.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }

    @Override
    public void getUserInfos(String deviceId) {
        final Subscription subscription = RetrofitUtils.api()
                .getUserInfoByDevice(deviceId)
                .map(new Func1<UserInfosBack, UserInfosBack>() {
                    @Override
                    public UserInfosBack call(UserInfosBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserInfosBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        userBandActivity.hidProgressDialog();
                        userBandActivity.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(UserInfosBack allJourneysBack) {

                        userBandActivity.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                userBandActivity.updateMain(allJourneysBack.getUser_infos());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            userBandActivity.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }


    @Override
    public void getDeviceList() {
        final Subscription subscription = RetrofitUtils.api()
                .getUserDeviceInfo()
                .map(new Func1<DeviceInfoBack, DeviceInfoBack>() {
                    @Override
                    public DeviceInfoBack call(DeviceInfoBack RegisterBack) {
                        return RegisterBack;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeviceInfoBack>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        devicesFragment.hidProgressDialog();
                        devicesFragment.showError(e.getMessage());
                    }

                    @Override
                    public void onNext(DeviceInfoBack allJourneysBack) {

                        devicesFragment.hidProgressDialog();
                        SLogger.d("<<", "-->" + JSON.toJSONString(allJourneysBack));

                        if (allJourneysBack.getRet() == Constant.SUCCESSRESPONSE) {

                            try {
                                devicesFragment.updateMainDevice(allJourneysBack.getDevice_infos());
                            } catch (ClassCastException e) {
                                SLogger.d("<<", "异常");
                                e.printStackTrace();
                            }

                        } else {
                            devicesFragment.showError(allJourneysBack.getErr());
                        }


                    }
                });

        addSubscription(subscription);
    }
}
