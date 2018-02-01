package com.phone.moran.activity;

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

import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.ShareHuawenFragment;
import com.phone.moran.fragment.ShareMoodFragment;
import com.phone.moran.fragment.ShareShareFragment;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.ImageLoader;
import com.umeng.socialize.ShareAction;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_main);
        ButterKnife.bind(this);

        paint = (Paint) getIntent().getSerializableExtra(Constant.PAINT);

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

        tabLayout.addTab(tabLayout.newTab().setText("花纹"));
        tabLayout.addTab(tabLayout.newTab().setText("心情"));
        tabLayout.addTab(tabLayout.newTab().setText("分享"));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        ImageLoader.displayImg(this, paint.getTitle_detail_url(), picImage);

        try{
            barcode.setImageBitmap(EncodingHandler.createQRCode("www.baidu.com", DensityUtils.dip2px(120)));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public void setShareListener(){

        wxShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ShareMainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener)
                        .withMedia(new UMImage(ShareMainActivity.this, getCacheBitmapFromView(shareBgFL)))
                        .share();
            }
        });

        circleShareWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ShareMainActivity.this).setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener)
                        .withMedia(new UMImage(ShareMainActivity.this, getCacheBitmapFromView(shareBgFL)))
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
    protected void setListener() {
        super.setListener();

        //设置setOnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换到制定的item
                viewPager.setCurrentItem(tab.getPosition());

                if(tab.getPosition() == 1) {
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
            case 0:picKuang.setVisibility(View.GONE);
                break;

        }

    }

    @Override
    public void onFragmentShareI(int uri) {
        shareApp = uri;

        switch (shareApp) {
            //微信
            case 0:
                popupWindowWechat.showAtLocation(moodImage, Gravity.BOTTOM, 0, 0);
                // 设置popWindow弹出窗体可点击
                popupWindowWechat.setFocusable(true);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 0.7f;
                getWindow().setAttributes(params);
                break;
            //微博
            case 1:
                new ShareAction(this).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                        .share();
                break;
            //ins
            case 2:
                new ShareAction(this).setPlatform(SHARE_MEDIA.INSTAGRAM).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                        .share();
                break;
            //twitter
            case 3:
                new ShareAction(this).setPlatform(SHARE_MEDIA.TWITTER).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                        .share();
                break;
            //facebook
            case 4:
                new ShareAction(this).setPlatform(SHARE_MEDIA.FACEBOOK).setCallback(umShareListener)
                        .withMedia(new UMImage(this, getCacheBitmapFromView(shareBgFL)))
                        .share();
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
    }
}
