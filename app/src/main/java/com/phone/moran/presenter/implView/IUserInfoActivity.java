package com.phone.moran.presenter.implView;

import com.phone.moran.model.User;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface IUserInfoActivity extends IBaseFragment{

    void updateUserInfo(User user);
    void save(User user);


//    void uploadUser();

//    void collectSuccess();

}
