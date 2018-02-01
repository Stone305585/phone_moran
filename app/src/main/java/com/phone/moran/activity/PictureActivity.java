package com.phone.moran.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.Picture;
import com.phone.moran.presenter.implPresenter.PictureActivityImpl;
import com.phone.moran.presenter.implView.IPictureActivity;
import com.phone.moran.tools.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PictureActivity extends BaseActivity implements View.OnClickListener, IPictureActivity {

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
    @BindView(R.id.image_paint)
    ImageView imagePaint;
    @BindView(R.id.author_paint)
    TextView authorPaint;
    @BindView(R.id.time_paint)
    TextView timePaint;
    @BindView(R.id.other_paint)
    TextView otherPaint;


    private int picture_id;
    private Picture picture;
    private PictureActivityImpl pictureActivityImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        picture_id = getIntent().getIntExtra(Constant.PICTURE_ID, 9);
        pictureActivityImpl = new PictureActivityImpl(this, token, this);

        initView();
        setListener();
        initDataSource();
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
        if (connected) {
            pictureActivityImpl.getPictureDetail(picture_id);
        }
    }

    @Override
    protected void initView() {
        super.initView();
//
        rightImageBtn3.setVisibility(View.INVISIBLE);

        toolbarCommon.setBackgroundColor(getResources().getColor(R.color.transparent));

    }

    @Override
    protected void setListener() {
        super.setListener();
        backTitle.setOnClickListener(this);
    }

    @Override
    public void updatePicture(Picture picture) {
        this.picture = picture;

//        ImageLoader.displayImg(this, picture.getPicture_url(), imagePaint);
        Glide.with(this).load(picture.getPicture_url()).asBitmap()
                .into(new BitmapImageViewTarget(imagePaint) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        super.setResource(resource);

                        imagePaint.setBackground(new BitmapDrawable(resource));
                    }
                });

        if(picture.getAuthor() != null) {
            authorPaint.setText(picture.getAuthor());
        } else {
            authorPaint.setVisibility(View.GONE);
        }

        if(picture.getTime() != null) {
            timePaint.setText(picture.getTime());
        } else {
            timePaint.setVisibility(View.GONE);
        }

        if(picture.getDetail() == null) {
            otherPaint.setVisibility(View.GONE);
        } else {
            otherPaint.setText(picture.getDetail());
        }

        if(picture.getTitle() != null) {
            title.setText(picture.getTitle());
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
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_title:
                finish();
                break;

            case R.id.right_image_btn3:
                Intent intent = new Intent(this, PlayPictureActivity.class);
                intent.putExtra(Constant.PLAY_FLAG, Constant.PICTURE);
                intent.putExtra(Constant.PICTURE, picture);
                startActivity(intent);
                break;
        }
    }
}
