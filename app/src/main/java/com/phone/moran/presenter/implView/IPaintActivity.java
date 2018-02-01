package com.phone.moran.presenter.implView;

import com.phone.moran.model.Paint;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface IPaintActivity extends IBaseFragment{

    void updatePaint(Paint paint);

    void collectSuccess();

    void uploadSuccess();

}
