package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * Login
 */
public interface ICategoryDetailActivityPresenter {

    void updateFilter(int typeId);

    void updateMain(int paintId, int last_id);

    void updateMainCommon(int paintId, int last_id);
}
