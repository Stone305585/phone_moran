package com.phone.moran.presenter;

import com.phone.moran.model.User;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface IUserActivityPresenter {

    void getUserInfo();

    void uploadUser(User user);

}
