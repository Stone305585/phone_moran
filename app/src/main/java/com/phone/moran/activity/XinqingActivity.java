package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.ImageGridRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.Mood;
import com.phone.moran.model.Paint;
import com.phone.moran.model.Picture;
import com.phone.moran.presenter.implPresenter.PaintActivityImpl;
import com.phone.moran.presenter.implView.IPaintActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.SLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
    CircleImageView xinqingCoverImage;
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
    private ImageGridRecyclerAdapter customAdapter;

    private ImageGridRecyclerAdapter defaultAdapter;

    private List<Picture> customPics = new ArrayList<>();
    private List<Picture> defaultPics = new ArrayList<>();

    LocalMoods localMoods;
    Mood myMood;

    private int moodId;

    private PaintActivityImpl moodImpl;
    private int last_id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xinqing);
        ButterKnife.bind(this);

        moodId = getIntent().getIntExtra(Constant.PAINT_ID, 17000036);

        SLogger.d("<<", "mood id is -->>>" + moodId);

        moodImpl = new PaintActivityImpl(this, token, this);

        initView();

        setListener();

        moodImpl.getMoodPaintDetail(moodId, last_id);
    }

    @Override
    protected void initView() {
        super.initView();

        localMoods = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        myMood = localMoods.getMoodById(moodId);

        List<Picture> list = myMood.getPictures();
        if (list.size() > 12) {
            for (int i = 0; i < 12; i++) {
                customPics.add(list.get(i));
            }
        } else
            customPics.addAll(myMood.getPictures());

        customAdapter = new ImageGridRecyclerAdapter(this, customPics);
        customRecycler.setItemAnimator(new DefaultItemAnimator());
        customRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        customRecycler.setAdapter(customAdapter);

        defaultAdapter = new ImageGridRecyclerAdapter(this, defaultPics);
        initRecycler.setItemAnimator(new DefaultItemAnimator());
        initRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        initRecycler.setAdapter(defaultAdapter);
    }


    @Override
    protected void setListener() {
        super.setListener();

        backTitle.setOnClickListener(this);
        initBtn.setOnClickListener(this);
        customBtn.setOnClickListener(this);

        defaultAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Picture picture = (Picture) model;

                Intent intent = new Intent(XinqingActivity.this, PictureActivity.class);
                intent.putExtra(Constant.PICTURE_ID, picture.getPicture_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        customAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Picture picture = (Picture) model;

                Intent intent = new Intent(XinqingActivity.this, PictureActivity.class);
                intent.putExtra(Constant.PICTURE_ID, picture.getPicture_id());
                startActivity(intent);
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
