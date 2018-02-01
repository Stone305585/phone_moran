package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface ILoginActivityPresenter {

    void login(String register_id, String password);

    //获取验证码
    void getCode(String register_id);

    void getUserInfo();
}
