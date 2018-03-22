package com.phone.moran.presenter;

/**
 * Created by ASUS on 2017/11/8.
 *
 * 获取一幅画集详情
 */
public interface IPlayPictureActivityPresenter {

    void addPlayMode(int playMode);


    void addPlayTime(int playTime);

    void addPlayLight(int playLight);

    void playPicture(int pictureId);

    public void getPaintDetail(int paint_id, int last_id);
}
