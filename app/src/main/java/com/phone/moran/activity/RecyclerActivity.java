package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.BaseRecyclerAdapter;
import com.phone.moran.adapter.PaintRecyclerAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.ClassicQuote;
import com.phone.moran.model.Paint;
import com.phone.moran.presenter.implPresenter.RecyclerActivityImpl;
import com.phone.moran.presenter.implView.IRecyclerActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.SLogger;
import com.phone.moran.view.RefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 画单列表 activity，
 * 多用版
 */
public class RecyclerActivity extends BaseActivity implements IRecyclerActivity, RefreshLayout.OnRefreshListener {

    public static final String UPLOAD_TYPE = "upload_type";
    public static final int UPLOAD_NEW = 1;
    public static final int UPLOAD_HOT = 2;


    RecyclerView recyclerView;
    RefreshLayout srl;
    PaintRecyclerAdapter paintRecyclerAdapter;
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
    private List<Paint> paints = new ArrayList<>();
    private RecyclerActivityImpl recyclerActivityImpl;

    private View doingfooterView;
    private int pageNum = 1;
    private boolean isNeedClear = true;
    private int uploadType = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ButterKnife.bind(this);

        recyclerActivityImpl = new RecyclerActivityImpl(this, token, this);
        uploadType = getIntent().getIntExtra(UPLOAD_TYPE, 1);

        initView();
        setListener();
        onRefresh();
    }

    @Override
    protected void initView() {
        super.initView();

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        srl = (RefreshLayout) findViewById(R.id.srl);
        title.setText("最新上传");
        srl.setColorSchemeColors(ContextCompat.getColor(this, R.color.purple),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.bg_grey2));
        srl.setProgressViewOffset(false, 0, DensityUtils.dip2px(30));

        paintRecyclerAdapter = new PaintRecyclerAdapter(this, paints);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        setFooter(recyclerView);
        paintRecyclerAdapter.setmFooterView(doingfooterView);

        recyclerView.setAdapter(paintRecyclerAdapter);
        srl.setChildView(recyclerView);
        AppUtils.hideFooter(doingfooterView);
    }

    @Override
    protected void setListener() {
        super.setListener();

        paintRecyclerAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, Object model) {
                Paint paint = (Paint) model;
                Intent intent = new Intent(RecyclerActivity.this, PaintActivity.class);
                intent.putExtra(Constant.PAINT_ID, paint.getPaint_id());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, Object model) {

            }
        });

        srl.setOnRefreshListener(this);
        srl.setOnLoadListener(new RefreshLayout.OnLoadListener() {
                                  @Override
                                  public void onLoad() {
                                      AppUtils.showFooter(doingfooterView);
                                      pageNum++;
                                      initDataSource();
                                  }

                              }
        );

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        if (connected) {

            recyclerActivityImpl.getPaintList(uploadType);
        }
    }

    @Override
    public void updatePaintRecycler(List<Paint> paintList) {

        if (isNeedClear) {
            paints.clear();
            isNeedClear = false;
        }

        paints.addAll(paintList);
        paintRecyclerAdapter.notifyDataSetChanged();

    }

    @Override
    public void updateCq(List<ClassicQuote> cqList) {

    }

    @Override
    public void showProgressDialog() {
        dialog.show();
    }

    @Override
    public void hidProgressDialog() {
        dialog.hide();
        srl.setRefreshing(false);
        AppUtils.hideFooter(doingfooterView);
    }

    @Override
    public void showError(String error) {
        hidProgressDialog();
        srl.setRefreshing(false);
        AppUtils.showToast(getApplicationContext(), error);
    }

    /**
     * 给recycler设置footer
     *
     * @param view
     */
    private void setFooter(RecyclerView view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        doingfooterView = inflater.inflate(R.layout.footer_recycler, view, false);
    }


    @Override
    public void onRefresh() {
        isNeedClear = true;
        srl.setRefreshing(true);
        pageNum = 1;

        initDataSource();

    }


}
