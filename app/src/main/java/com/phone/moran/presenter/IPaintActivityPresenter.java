package com.phone.moran.presenter;

import java.util.List;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 获取一幅画集详情
 */
public interface IPaintActivityPresenter {

    void getPaintDetail(int paint_id, int last_id);
    void getMoodPaintDetail(int paint_id, int last_id);
    void getMoodDetail(int paint_id, int last_id);

    //collect
    void collect(int paintId);

    void upload(List<Integer> pics);
}
