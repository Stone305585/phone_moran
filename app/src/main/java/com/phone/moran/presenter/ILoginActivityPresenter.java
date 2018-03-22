package com.phone.moran.presenter;

import com.phone.moran.model.ThirdLoginInfo;

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

    void thirdBind(ThirdLoginInfo t);
}
