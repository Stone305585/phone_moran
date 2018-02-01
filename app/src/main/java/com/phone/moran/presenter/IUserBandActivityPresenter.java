package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface IUserBandActivityPresenter {

    void getUserInfos(String deviceId);

    void process(String deviceId, int status, int uin);

    void remove(String deviceId);

    void getDeviceList();
}
