package com.phone.moran.presenter.implView;

import com.phone.moran.model.Paint;
import com.phone.moran.model.Picture;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface ICategoryDetailActivity extends IBaseFragment{

    void updateMain(List<Picture> pics, int last_id);

    void updateFilter(List<Paint> paints);

}
