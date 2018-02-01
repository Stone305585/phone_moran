package com.phone.moran.presenter.implView;

import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.User;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface ILoginActivity extends IBaseFragment{

    void loginSuccess(RegisterBack registerBack);

    void code();

    void updateUserInfo(User user);

}
