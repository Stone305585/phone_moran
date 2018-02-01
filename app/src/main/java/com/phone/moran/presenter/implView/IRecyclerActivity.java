package com.phone.moran.presenter.implView;

import com.phone.moran.model.ClassicQuote;
import com.phone.moran.model.Paint;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 */
public interface IRecyclerActivity extends IBaseFragment{

    void updatePaintRecycler(List<Paint> paintList);

    void updateCq(List<ClassicQuote> cqList);

}
