package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface IRegisterActivityPresenter {

    void register(String register_id, String password, String code);

    void registerMobile(String phone, String password, String code);

}
