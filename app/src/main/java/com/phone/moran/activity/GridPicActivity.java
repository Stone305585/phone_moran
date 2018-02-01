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
import com.phone.moran.model.Paint;
import com.phone.moran.model.Picture;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GridPicActivity extends BaseActivity{


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
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private ImageGridRecyclerAdapter picAdapter;
    private List<Picture> pictureList = new ArrayList<>();
//    private CategoryDetailActivityImpl caImpl;
    private Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_pic);
        ButterKnife.bind(this);

        paint = (Paint)getIntent().getSerializableExtra(Constant.PAINT);

//        caImpl= new CategoryDetailActivityImpl(this, token, this);

        initView();
        setListener();
//        initDataSource();
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
        if(connected) {
//            caImpl.updateMain(paint.getPaint_id());
        }
    }

    @Override
    protected void initView() {
        super.initView();

        pictureList.addAll(paint.getPicture_info());

        picAdapter = new ImageGridRecyclerAdapter(this, pictureList);

        title.setText(paint.getPaint_title());

        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setLayoutManager(new GridLayoutManager(this, 3));
        recycler.setAdapter(picAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        picAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
//                String url = ((Picture)model).getPicture_url();
                Intent intent = new Intent();
                intent.putExtra(Constant.PICTURE, position);
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//
//    @Override
//    public void updateMain(List<Picture> paints) {
//
//        pictureList.clear();
//        pictureList.addAll(paints);
//        picAdapter.notifyDataSetChanged();
//    }

//    @Override
//    public void updateMain(List<Picture> pics, int last_id) {
//
//    }
//
//    @Override
//    public void updateFilter(List<Paint> paints) {
//    }
//
//    @Override
//    public void showProgressDialog() {
//        dialog.show();
//    }
//
//    @Override
//    public void hidProgressDialog() {
//        dialog.hide();
//    }
//
//    @Override
//    public void showError(String error) {
//        dialog.hide();
//        AppUtils.showToast(getApplicationContext(), error);
//    }
}
