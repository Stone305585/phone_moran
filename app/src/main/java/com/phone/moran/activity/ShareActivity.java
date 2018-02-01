package com.phone.moran.activity;

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

import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ShareMaterialFragment;
import com.phone.moran.fragment.ShareShareFragment;
import com.phone.moran.model.MasterQuote;
import com.phone.moran.tools.DateUtils;
import com.phone.moran.tools.SLogger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        ButterKnife.bind(this);


        masterQuote = (MasterQuote) getIntent().getSerializableExtra(Constant.MASTER);

        fm = getSupportFragmentManager();

        initView();

        initWechatPopWin();

        setListener();

        setShareListener();
    }


    public void setShareListener(){

        wxShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(new UMImage(ShareActivity.this, getCacheBitmapFromView(shareBgFl)))
                        .share();
            }
        });

        circleShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ShareActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withMedia(new UMImage(ShareActivity.this, getCacheBitmapFromView(shareBgFl)))
                        .share();
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

        tabLayout.addTab(tabLayout.newTab().setText("材质"));
        tabLayout.addTab(tabLayout.newTab().setText("分享"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


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
                popupWindowWechat.showAtLocation(dateDate, Gravity.BOTTOM, 0, 0);
                // 设置popWindow弹出窗体可点击
                popupWindowWechat.setFocusable(true);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 0.7f;
                getWindow().setAttributes(params);
                break;
                //微博
            case 1:
                new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                        .share();
                break;
                //ins
            case 2:
                new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                        .share();
                break;
                //twitter
            case 3:
                new ShareAction(this).setPlatform(SHARE_MEDIA.TWITTER).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                        .share();
                break;
                //facebook
            case 4:
                new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFl)))
                        .share();
                break;
        }

    }
}
