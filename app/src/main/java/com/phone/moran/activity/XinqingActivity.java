package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.FilterRecyclerAdapter;
import com.phone.moran.adapter.ImageGridRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.Mood;
import com.phone.moran.model.Paint;
import com.phone.moran.model.Picture;
import com.phone.moran.presenter.implPresenter.PaintActivityImpl;
import com.phone.moran.presenter.implView.IPaintActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.SLogger;
import com.phone.moran.view.recycler.AdjustGLManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class XinqingActivity extends BaseActivity implements IPaintActivity, View.OnClickListener {


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
    @BindView(R.id.xinqing_cover_image)
    ImageView xinqingCoverImage;
    @BindView(R.id.xinqing_cover_text)
    TextView xinqingCoverText;
    @BindView(R.id.custom_btn)
    LinearLayout customBtn;
    @BindView(R.id.custom_recycler)
    RecyclerView customRecycler;
    @BindView(R.id.init_btn)
    LinearLayout initBtn;
    @BindView(R.id.init_recycler)
    RecyclerView initRecycler;
    @BindView(R.id.category_tv)
    TextView categoryTv;
    @BindView(R.id.filter_btn)
    LinearLayout filterBtn;
    private ImageGridRecyclerAdapter customAdapter;

    private ImageGridRecyclerAdapter defaultAdapter;

    private List<Picture> customPics = new ArrayList<>();
    private List<Picture> defaultPics = new ArrayList<>();

    LocalMoods localMoods;
    Mood myMood;

    private int moodId;

    private PaintActivityImpl moodImpl;
    private int last_id = 0;


    //--------------pop Window-------------
    private View popView;
    private RecyclerView popRecycler;
    private FilterRecyclerAdapter filterRecyclerAdapter;
    private List<Paint> filterPaint = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinqing);
        ButterKnife.bind(this);

        moodId = getIntent().getIntExtra(Constant.PAINT_ID, 17000036);

        SLogger.d("<<", "mood id is -->>>" + moodId);

        moodImpl = new PaintActivityImpl(this, token, this);

        initView();

        initPopWin();

        setListener();

        moodImpl.getMoodPaintDetail(moodId, last_id);
    }

    public void initPopWin() {
        popView = LayoutInflater.from(this).inflate(R.layout.filter_window, null);
        popupWindow = new PopupWindow(popView, DensityUtils.dip2px(230), DensityUtils.dip2px(130), true);
        popupWindow.setContentView(popView);
        popupWindow.setAnimationStyle(R.style.filterpopwindow_anim_style);

        // 实例化一个ColorDrawable颜色为半透明
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.corner_bg_white_pop));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });

        localMoods = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        for (int i = 0; i < localMoods.getMoods().size(); i++) {
            Paint paint = new Paint();
            paint.setPaint_id(localMoods.getMoods().get(i).getMood_id());
            paint.setPaint_name(localMoods.getMoods().get(i).getMood_name());
            filterPaint.add(paint);
        }

        filterRecyclerAdapter = new FilterRecyclerAdapter(this, filterPaint, false);

        popRecycler = (RecyclerView) (popView.findViewById(R.id.filter_recycler));
        popRecycler.setItemAnimator(new DefaultItemAnimator());
        popRecycler.setLayoutManager(new LinearLayoutManager(this));
        popRecycler.setAdapter(filterRecyclerAdapter);

    }

    @Override
    protected void initView() {
        super.initView();

        localMoods = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        myMood = localMoods.getMoodById(moodId);

//        title.setText(myMood.getMood_name());
        title.setText("心情");


        List<Picture> list = myMood.getPictures();
        if (list.size() > 12) {
            for (int i = 0; i < 12; i++) {
                customPics.add(list.get(i));
            }
        } else
            customPics.addAll(myMood.getPictures());

        xinqingCoverImage.setImageDrawable(getResources().getDrawable(myMood.getRes_id()));
        xinqingCoverText.setText(myMood.getMood_name());

        customAdapter = new ImageGridRecyclerAdapter(this, customPics);
        customRecycler.setItemAnimator(new DefaultItemAnimator());
        customRecycler.setLayoutManager(new AdjustGLManager(this, 3));
        customRecycler.setAdapter(customAdapter);

        if (customPics.size() == 0) {
            customBtn.setVisibility(View.GONE);
        } else {
            customBtn.setVisibility(View.VISIBLE);
        }

        defaultAdapter = new ImageGridRecyclerAdapter(this, defaultPics);
        initRecycler.setItemAnimator(new DefaultItemAnimator());
        initRecycler.setLayoutManager(new AdjustGLManager(this, 3));
        initRecycler.setAdapter(defaultAdapter);
    }


    @Override
    protected void setListener() {
        super.setListener();

        backTitle.setOnClickListener(this);
        initBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);
        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAtLocation(filterBtn, Gravity.TOP, 0, DensityUtils.dip2px(116));
                // 设置popWindow弹出窗体可点击
                popupWindow.setFocusable(true);
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 0.7f;
                getWindow().setAttributes(params);
            }
        });

        filterRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                for (Paint item : filterPaint) {
                    item.setSelected(false);
                }
                Paint paint = (Paint) model;
                paint.setSelected(true);

                last_id = 0;

                filterRecyclerAdapter.notifyDataSetChanged();

                moodImpl.getMoodPaintDetail(paint.getPaint_id(), last_id);

                categoryTv.setText(paint.getPaint_name());

                popupWindow.dismiss();
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        defaultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Picture picture = (Picture) model;

                Paint paint = new Paint();
                paint.setPicture_info(defaultPics);
                paint.setPaint_title(myMood.getMood_name());

                Intent intent = new Intent(XinqingActivity.this, PlayPictureActivity.class);
                intent.putExtra(Constant.PLAY_FLAG, PlayPictureActivity.PAINT);
                intent.putExtra(Constant.PAINT, paint);
                intent.putExtra(Constant.PLAY_INDEX, position);
                startActivity(intent);

                /*Intent intent = new Intent(XinqingActivity.this, PictureActivity.class);
                intent.putExtra(Constant.PICTURE_ID, picture.getPicture_id());
                intent.putExtra(Constant.TITLE, 1);
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        customAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {

                Paint paint = new Paint();
                paint.setPicture_info(customPics);
                paint.setPaint_title(myMood.getMood_name());

                Intent intent = new Intent(XinqingActivity.this, PlayPictureActivity.class);
                intent.putExtra(Constant.PLAY_FLAG, PlayPictureActivity.PAINT);
                intent.putExtra(Constant.PAINT, paint);
                intent.putExtra(Constant.PLAY_INDEX, position);
                startActivity(intent);

                /*Picture picture = (Picture) model;

                Intent intent = new Intent(XinqingActivity.this, PictureActivity.class);
                intent.putExtra(Constant.PICTURE_ID, picture.getPicture_id());
                intent.putExtra(Constant.TITLE, 0);
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

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
    public void updatePaint(Paint paint) {

        defaultPics.clear();

        List<Picture> pictureList = paint.getPicture_info();
        if (pictureList.size() > 12) {
            for (int i = 0; i < 12; i++) {
                defaultPics.add(pictureList.get(i));
            }
        } else {
            defaultPics.addAll(paint.getPicture_info());
        }

        //设置本地的心情收藏图
        myMood = localMoods.getMoodById(paint.getPaint_id());
        customPics.clear();

        List<Picture> list = myMood.getPictures();
        if (list.size() > 12) {
            for (int i = 0; i < 12; i++) {
                customPics.add(list.get(i));
            }
        } else
            customPics.addAll(myMood.getPictures());

        customAdapter.notifyDataSetChanged();

        xinqingCoverImage.setImageDrawable(getResources().getDrawable(myMood.getRes_id()));
        xinqingCoverText.setText(myMood.getMood_name());

        defaultAdapter.notifyDataSetChanged();
    }

    @Override
    public void collectSuccess() {

    }

    @Override
    public void uploadSuccess() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_btn:

                myMood.setPictures(customPics);

                Intent intent = new Intent(this, MoodPicsActivity.class);
                intent.putExtra(Constant.TITLE, 0);
                intent.putExtra(Constant.PAINT, myMood);
                startActivity(intent);
                break;

            case R.id.init_btn:

                myMood.setPictures(defaultPics);

                Intent intent1 = new Intent(this, MoodPicsActivity.class);
                intent1.putExtra(Constant.TITLE, 1);
                intent1.putExtra(Constant.PAINT, myMood);
                startActivity(intent1);

                break;
            case R.id.back_title:
                finish();
                break;

        }
    }
}
