package com.phone.moran.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ShareMaterialFragment;
import com.phone.moran.fragment.ShareShareFragment;
import com.phone.moran.model.MasterQuote;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DateUtils;
import com.phone.moran.tools.Util;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.zxing.encoding.EncodingHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShareActivity extends BaseActivity implements View.OnClickListener, ShareMaterialFragment.OnFragmentMaterialIListener, ShareShareFragment.OnFragmentShareIListener {

    @BindView(R.id.content_daka)
    TextView contentDaka;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.week_date)
    TextView weekDate;
    @BindView(R.id.date_date)
    TextView dateDate;
    @BindView(R.id.barcode_share)
    ImageView barcodeShare;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bg_LL)
    FrameLayout bgLL;
    @BindView(R.id.share_bg_FL)
    FrameLayout shareBgFl;

    ShareShareFragment shareFragment;
    ShareMaterialFragment materialFragment;

    FragmentManager fm;

    List<Fragment> fragmentList = new ArrayList<>();

    private int shareMat;

    private int shareApp;

    private MasterQuote masterQuote;
    private UMShareAPI mShareAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);


        masterQuote = (MasterQuote) getIntent().getSerializableExtra(Constant.MASTER);

        fm = getSupportFragmentManager();

        mShareAPI = UMShareAPI.get(this);

        initView();

        initWechatPopWin();

        setListener();

        setShareListener();
    }

    private WXMediaMessage msg;
    public void setShareListener(){

        wxShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*new ShareAction(ShareMainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(new UMImage(ShareMainActivity.this, getCacheBitmapFromView(shareBgFL)))
                        .share();*/
                dialog.show();
                msg = new WXMediaMessage();
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                msg.setThumbImage(thumb);
                Bitmap mBp = getCacheBitmapFromView(shareBgFl);
                WXImageObject wxImage = new WXImageObject(mBp);
                msg.mediaObject = wxImage;

                Bitmap p = Bitmap.createScaledBitmap(mBp, 68, 68 * mBp.getHeight() / mBp.getWidth(), true);

                mBp.recycle();

                msg.thumbData = Util.bmpToByteArray(p, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                HHApplication.api.sendReq(req);
                dialog.dismiss();
            }
        });

        circleShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                msg = new WXMediaMessage();
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                msg.setThumbImage(thumb);
                Bitmap mBp = getCacheBitmapFromView(shareBgFl);
                WXImageObject wxImage = new WXImageObject(mBp);
                msg.mediaObject = wxImage;

                Bitmap p = Bitmap.createScaledBitmap(mBp, 68, 68 * mBp.getHeight() / mBp.getWidth(), true);

                mBp.recycle();

                msg.thumbData = Util.bmpToByteArray(p, true);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = String.valueOf(System.currentTimeMillis());
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                HHApplication.api.sendReq(req);

                dialog.dismiss();
            }
        });

        cancelWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindowWechat.dismiss();
            }
        });

    }

    @Override
    protected void initView() {
        super.initView();

        shareFragment = ShareShareFragment.newInstance("", "");
        materialFragment = ShareMaterialFragment.newInstance("", "");
        fragmentList.add(materialFragment);
        fragmentList.add(shareFragment);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);
        viewPager.setAdapter(mainPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.texture)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.share)));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        try {
            barcodeShare.setImageBitmap(EncodingHandler.createQRCode("www.atmoran.com", 260));

        } catch (Exception e) {
            e.printStackTrace();
        }


        contentDaka.setText(masterQuote.getMq_content());
        dateDate.setText(DateUtils.getChineseDate(System.currentTimeMillis()));
        weekDate.setText(DateUtils.getWeekOfDate(System.currentTimeMillis()));

    }


    @Override
    protected void setListener() {
        super.setListener();

        //设置setOnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换到制定的item
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        bgLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bg_LL:
                finish();
                break;
        }
    }

    @Override
    public void onFragmentMaterialI(int material) {

        shareMat = material;

        switch (shareMat) {
            case 0:
                shareBgFl.setBackground(getResources().getDrawable(R.mipmap.yishuxianfeng_bg_0));

                break;
            case 1:
                shareBgFl.setBackground(getResources().getDrawable(R.mipmap.yishuxianfeng_bg_1));

                break;
            case 2:
                shareBgFl.setBackground(getResources().getDrawable(R.mipmap.yishuxianfeng_bg_2));

                break;
        }
    }

    @Override
    public void onFragmentShareI(int uri) {

        shareApp = uri;

        switch (shareApp) {
            //微信
            case 0:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN)) {

                    popupWindowWechat.showAtLocation(dateDate, Gravity.BOTTOM, 0, 0);
                    // 设置popWindow弹出窗体可点击
                    popupWindowWechat.setFocusable(true);
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    params.alpha = 0.7f;
                    getWindow().setAttributes(params);
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_wechat));

                }
                break;
            //微博
            case 1:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.SINA)) {

                    new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_weibo));

                }
                break;
            //ins
            case 2:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.INSTAGRAM)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_instagram));
                }

                break;
            //twitter
            case 3:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.TWITTER)) {

                    new ShareAction(this).setPlatform(SHARE_MEDIA.TWITTER).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_twitter));
                }
                break;
            //facebook
            case 4:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.FACEBOOK)) {

                    new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                            .share();
                }else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_facebook));

                }
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
