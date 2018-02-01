package com.phone.moran.presenter.implView;

import com.phone.moran.model.DeviceInfo;
import com.phone.moran.model.User;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface IUserBandActivity extends IBaseFragment{

    void remove();

    void approve();

    void updateMain(List<User> list);

    void updateMainDevice(List<DeviceInfo> list);

}
