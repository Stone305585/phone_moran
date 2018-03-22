package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface IRegisterActivityPresenter {

    void register(String register_id, String password, String code);

    void verifyCode(String register_id, String code);

    void registerMobile(String phone, String password, String code);

    public void resetPassword(String register_id, String password);

    public void getCode(String register_id);

}
