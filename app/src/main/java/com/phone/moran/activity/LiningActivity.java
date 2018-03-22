package com.phone.moran.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.fragment.FrameColorFragment;
import com.phone.moran.fragment.FrameSizeFragment;
import com.phone.moran.presenter.implPresenter.LiningActivityImpl;
import com.phone.moran.presenter.implView.ILiningActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiningActivity extends BaseActivity implements View.OnClickListener, ILiningActivity, FrameColorFragment.OnFragmentColorListener, FrameSizeFragment.OnFragmentSizeListener {


    @BindView(R.id.frame_color_iv)
    ImageView frameColorIv;
    @BindView(R.id.frame_FL)
    FrameLayout frameFL;
    @BindView(R.id.frame_content)
    ViewPager frameContent;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.back_title)
    ImageView backTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.right_text_btn)
    TextView rightTextBtn;
    @BindView(R.id.right_image_btn1)
    ImageView rightImageBtn1;
    @BindView(R.id.right_image_btn2)
    ImageView rightImageBtn2;
    @BindView(R.id.right_image_btn3)
    ImageView rightImageBtn3;
    @BindView(R.id.rl_title)
    LinearLayout rlTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.frame_picture)
    ImageView framePic;

    @BindView(R.id.tip_tip)
    ImageView tipTip;
    @BindView(R.id.tip_tip_bg)
    FrameLayout tipBgFl;
    @BindView(R.id.click_gone_iv)
    ImageView clickGone;

    private int frame_color = 1;
    private int frame_size = 1;
    private LiningActivityImpl liningActivityImpl;
    private FragmentManager fm;
    private FrameColorFragment frameColorFragment;
    private FrameSizeFragment frameSizeFragment;
    private List<Fragment> fragmentList = new ArrayList<>();

    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lining);
        ButterKnife.bind(this);

        imageUrl = getIntent().getStringExtra(Constant.IMAGE);

        liningActivityImpl = new LiningActivityImpl(this, token, this);
        fm = getSupportFragmentManager();

        initView();
        setListener();

    }

    @Override
    protected void initView() {
        super.initView();
        title.setText("内衬");

        rightImageBtn3.setVisibility(View.VISIBLE);

        ImageLoader.displayImg(this, imageUrl, framePic);

        frameColorFragment = FrameColorFragment.newInstance("", "");
        frameSizeFragment = FrameSizeFragment.newInstance("", "");
        fragmentList.add(frameColorFragment);
        fragmentList.add(frameSizeFragment);
        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);
        frameContent.setAdapter(mainPagerAdapter);

        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.texture)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.size)));
        frameContent.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        setFrame();
    }

    @Override
    protected void setListener() {
        super.setListener();

        rightImageBtn3.setOnClickListener(this);
        backTitle.setOnClickListener(this);
        //设置setOnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //切换到制定的item
                frameContent.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //TODO 直接不用setListener 应该也行，在fragment中 的onattach直接就设置了listener
        frameSizeFragment.setmListener(this);
        frameColorFragment.setmListener(this);

        tipTip.setOnClickListener(this);
        clickGone.setOnClickListener(this);
//        frameSizeFragment.onAttach(LiningActivity.this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.right_image_btn3:
                liningActivityImpl.uploadLining(frame_color, frame_size);
                break;

            case R.id.back_title:
                finish();
                break;

            case R.id.tip_tip:
                tipBgFl.setVisibility(View.VISIBLE);
                break;
            case R.id.click_gone_iv:
                tipBgFl.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hidProgressDialog() {
        dialog.hide();
    }

    @Override
    public void showError(String error) {
        dialog.hide();
        AppUtils.showToast(getApplicationContext(), error);
    }

    @Override
    public void uploadLining() {
        AppUtils.showToast(this, getResources().getString(R.string.push_success));
    }

    @Override
    public void onFragmentColorInteraction(int color) {
        frame_color = color;
        setFrame();

    }

    /**
     * frame size 正好比  硬件的 小1；上传的时候加1
     * @param size
     */
    @Override
    public void onFragmentSizeInteraction(int size) {
        frame_size = size;
        setFrame();
    }

    private void setFrame() {
        frameColorIv.setVisibility(View.VISIBLE);
        switch (frame_color) {
            case 1:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_10));
                        break;

                }
                break;
            case 2:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame5_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame5_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame5_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame5_10));
                        break;

                }
                break;
            case 3:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame2_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame2_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame2_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame2_10));
                        break;

                }
                break;
            case 4:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame4_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame4_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame4_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame4_10));
                        break;

                }
                break;

            case 5:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame3_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame3_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame3_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame3_10));
                        break;

                }
                break;
            default:
                switch (frame_size) {
                    case 0:
                        frameColorIv.setVisibility(View.GONE);
                        break;
                    case 1:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_2));
                        break;
                    case 2:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_5));
                        break;
                    case 3:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_7));
                        break;
                    case 4:
                        frameColorIv.setImageDrawable(getResources().getDrawable(R.mipmap.frame1_10));
                        break;

                }
                break;

        }

    }
}
