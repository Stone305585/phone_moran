package com.phone.moran.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ShareHuawenFragment;
import com.phone.moran.fragment.ShareMoodFragment;
import com.phone.moran.fragment.ShareShareFragment;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.AppTypeface;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.ImageLoader;
import com.phone.moran.tools.PreferencesUtils;
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

public class ShareMainActivity extends BaseActivity implements View.OnClickListener, ShareHuawenFragment.OnFragmentHuawenIListener, ShareShareFragment.OnFragmentShareIListener, ShareMoodFragment.OnFragmentMoodIListener {

    @BindView(R.id.pic_image)
    ImageView picImage;
    @BindView(R.id.pic_title)
    TextView picTitle;
    @BindView(R.id.mood_image)
    ImageView moodImage;
    @BindView(R.id.mood_text)
    TextView moodText;
    @BindView(R.id.barcode)
    ImageView barcode;
    @BindView(R.id.share_bg_FL)
    ScrollView shareBgFL;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.bg_LL)
    FrameLayout bgLL;

    ShareShareFragment shareFragment;
    ShareHuawenFragment huawenFragment;
    ShareMoodFragment moodFragment;

    FragmentManager fm;

    List<Fragment> fragmentList = new ArrayList<>();
    @BindView(R.id.pic_kuang)
    ImageView picKuang;

    private int shareHuawen;
    private int shareMood;
    private int shareApp;

    private Paint paint;

    private UMShareAPI mShareAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);
        ButterKnife.bind(this);

        paint = (Paint) getIntent().getSerializableExtra(Constant.PAINT);

        mShareAPI = UMShareAPI.get(this);

        fm = getSupportFragmentManager();

        initView();

        initWechatPopWin();

        setListener();

        setShareListener();
    }


    @Override
    protected void initView() {
        super.initView();

        shareFragment = ShareShareFragment.newInstance("", "");
        moodFragment = ShareMoodFragment.newInstance("", "");
        huawenFragment = ShareHuawenFragment.newInstance("", "");

        fragmentList.add(huawenFragment);
        fragmentList.add(moodFragment);
        fragmentList.add(shareFragment);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);
        viewPager.setAdapter(mainPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.pattern)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.mood)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.share)));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_xi));
        moodText.setText(getResources().getString(R.string.happiness));
        picTitle.setText(paint.getPaint_title());

        ImageLoader.displayImg(this, paint.getTitle_detail_url(), picImage);

        try {
            barcode.setImageBitmap(EncodingHandler.createQRCode("http://www.atmoran.com", DensityUtils.dip2px(120)));

        } catch (Exception e) {
            e.printStackTrace();
        }

        changeViewGroupFonts(this, (ViewGroup)tabLayout, AppTypeface.REPLACE_FONT, 15, Color.BLACK);

    }

    public final static int FRIEND = 0;
    public final static int CIRCLE = 1;
    public final static int INVITE = 2;
    public final static int MAIN = 3;

    private WXMediaMessage msg;

    public void setShareListener() {

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
                Bitmap mBp = getCacheBitmapFromView(shareBgFL);
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
                Bitmap mBp = getCacheBitmapFromView(shareBgFL);
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
    protected void onResume() {
        super.onResume();
        String code = PreferencesUtils.getString(getApplicationContext(), Constant.WX_SHARE_CODE);

        if (code != null && code.length() != 0 && !code.equals("-1")) {
            AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.share_success));
            PreferencesUtils.putString(getApplicationContext(), Constant.WX_SHARE_CODE, "-1");
        }
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

                if (tab.getPosition() == 1) {
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(260));
                    lp.gravity = Gravity.BOTTOM;
                    lp.bottomMargin = DensityUtils.dip2px(45);
                    viewPager.setLayoutParams(lp);
                } else {
                    FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtils.dip2px(60));
                    lp.gravity = Gravity.BOTTOM;
                    lp.bottomMargin = DensityUtils.dip2px(45);
                    viewPager.setLayoutParams(lp);
                }
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
    public void onFragmentHuawenI(int uri) {
        shareHuawen = uri;

        switch (uri) {
            case 1:
                picKuang.setVisibility(View.VISIBLE);
                picKuang.setImageDrawable(getResources().getDrawable(R.mipmap.huawen1kuang));
                break;
            case 2:
                picKuang.setVisibility(View.VISIBLE);
                picKuang.setImageDrawable(getResources().getDrawable(R.mipmap.huawen2kuang));
                break;
            case 3:
                picKuang.setVisibility(View.VISIBLE);
                picKuang.setImageDrawable(getResources().getDrawable(R.mipmap.huawen3kuang));
                break;
            case 0:
                picKuang.setVisibility(View.GONE);
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

                    popupWindowWechat.showAtLocation(moodImage, Gravity.BOTTOM, 0, 0);
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
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_weibo));

                }
                break;
            //ins
            case 2:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.INSTAGRAM)) {
                    new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_instagram));
                }

                break;
            //twitter
            case 3:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.TWITTER)) {

                    new ShareAction(this).setPlatform(SHARE_MEDIA.TWITTER).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                            .share();
                } else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_twitter));
                }
                break;
            //facebook
            case 4:
                if(mShareAPI.isInstall(this, SHARE_MEDIA.FACEBOOK)) {

                    new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).setCallback(umShareListener)
                            .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                            .share();
                }else {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.not_install_facebook));

                }
                break;
        }

    }

    @Override
    public void onFragmentMoodI(int uri) {
        shareMood = uri;

        switch (uri) {
            case 0:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_nu));
                moodText.setText("怒");
                break;

            case 1:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_si));
                moodText.setText("思");
                break;

            case 2:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_kong));
                moodText.setText("恐");
                break;

            case 3:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_jing));
                moodText.setText("惊");
                break;

            case 4:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_you));
                break;

            case 5:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_xi));
                moodText.setText("喜");
                break;

            case 6:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_bei));
                moodText.setText("悲");
                break;

            case 7:
                moodImage.setImageDrawable(getResources().getDrawable(R.mipmap.mood_wu));
                moodText.setText("无常");
                break;

        }

        viewPager.setCurrentItem(2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }
}
