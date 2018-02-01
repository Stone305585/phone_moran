package com.phone.moran.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.gc.materialdesign.views.Switch;
import com.phone.moran.R;
import com.phone.moran.presenter.implPresenter.TipsActivityImpl;
import com.phone.moran.presenter.implView.ITipsActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DensityUtils;
import com.phone.moran.tools.SLogger;
import com.phone.moran.tools.ScreenUtils;
import com.phone.moran.view.MoveImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipActivity extends BaseActivity implements View.OnClickListener, ITipsActivity {

    private static final String TAB1 = "材质";
    private static final String TAB2 = "位置";

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
    @BindView(R.id.material_background)
    ImageView materialBackground;
    @BindView(R.id.nine_position)
    ImageView ninePosition;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.position_tip)
    MoveImageView positionTip;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.material_1)
    RadioButton material1;
    @BindView(R.id.material_2)
    RadioButton material2;
    @BindView(R.id.material_3)
    RadioButton material3;
    @BindView(R.id.material_LL)
    LinearLayout materialLL;
    @BindView(R.id.pos1)
    ImageView pos1;
    @BindView(R.id.pos2)
    ImageView pos2;
    @BindView(R.id.pos4)
    ImageView pos4;
    @BindView(R.id.pos5)
    ImageView pos5;

    @BindView(R.id.tip_switch)
    Switch tipSwitch;
    @BindView(R.id.tip_tip)
    ImageView tipTip;
    @BindView(R.id.tip_tip_bg)
    FrameLayout tipBgFl;
    @BindView(R.id.click_gone_iv)
    ImageView clickGone;

    private String flag = TAB1;//当前页面标志
    private int position = 1;
    private int texture = 1;//材质index
    private int pushFlag = 1;//1:添加   2：删除
    private TipsActivityImpl tipsActivityImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        ButterKnife.bind(this);

        tipsActivityImpl = new TipsActivityImpl(this, token, this);

        initView();
        setListener();
        initDataSource();
    }

    @Override
    protected void initView() {
        super.initView();

        title.setText("便签");
        rightImageBtn3.setVisibility(View.VISIBLE);
        tabLayout.addTab(tabLayout.newTab().setText(TAB1));
        tabLayout.addTab(tabLayout.newTab().setText(TAB2));
        ninePosition.setVisibility(View.GONE);
        material1.setChecked(true);
        positionTip.setTipTv(tipTv);

        tipSwitch.setChecked(true);
    }

    @Override
    protected void setListener() {
        super.setListener();

        material1.setOnClickListener(this);
        material2.setOnClickListener(this);
        material3.setOnClickListener(this);

        backTitle.setOnClickListener(this);

        tipTip.setOnClickListener(this);

        rightImageBtn3.setOnClickListener(this);
        clickGone.setOnClickListener(this);

        tipSwitch.setOncheckListener(new Switch.OnCheckListener() {
            @Override
            public void onCheck(Switch aSwitch, boolean b) {
                if (!b) {
                    pushFlag = 2;
                    tipsActivityImpl.uploadTips("", 1, 1, pushFlag);
                } else {
                    pushFlag = 1;
                }
            }
        });
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getText() == TAB1) {
                    materialLL.setVisibility(View.VISIBLE);
                    tipTip.setVisibility(View.VISIBLE);
                    ninePosition.setVisibility(View.GONE);
                    pos1.setVisibility(View.INVISIBLE);
                    pos2.setVisibility(View.INVISIBLE);
                    pos4.setVisibility(View.INVISIBLE);
                    pos5.setVisibility(View.INVISIBLE);
                    flag = TAB1;
                } else {
                    tipTip.setVisibility(View.GONE);
                    materialLL.setVisibility(View.GONE);
                    ninePosition.setVisibility(View.VISIBLE);
                    flag = TAB2;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        positionTip.setLocationListener(new MoveImageView.LocationListener() {
            @Override
            public void location() {
                position = getPosition();
                SLogger.d("<<", "-->>" + position);
                switch (position) {
                    case 1:
                        pos1.setVisibility(View.VISIBLE);
                        pos2.setVisibility(View.INVISIBLE);
                        pos4.setVisibility(View.INVISIBLE);
                        pos5.setVisibility(View.INVISIBLE);
                        break;
                    case 2:
                        pos2.setVisibility(View.VISIBLE);
                        pos1.setVisibility(View.INVISIBLE);
                        pos4.setVisibility(View.INVISIBLE);
                        pos5.setVisibility(View.INVISIBLE);
                        break;
                    case 4:
                        pos4.setVisibility(View.VISIBLE);
                        pos2.setVisibility(View.INVISIBLE);
                        pos1.setVisibility(View.INVISIBLE);
                        pos5.setVisibility(View.INVISIBLE);
                        break;
                    case 5:
                        pos5.setVisibility(View.VISIBLE);
                        pos2.setVisibility(View.INVISIBLE);
                        pos4.setVisibility(View.INVISIBLE);
                        pos1.setVisibility(View.INVISIBLE);
                        break;

                }
            }
        });

    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.material_1:
                materialBackground.setImageResource(R.mipmap.material_tip1);
                material1.setChecked(true);
                material2.setChecked(false);
                material3.setChecked(false);
                texture = 1;
                break;
            case R.id.material_2:
                materialBackground.setImageResource(R.mipmap.material_tip2);
                material1.setChecked(false);
                material2.setChecked(true);
                material3.setChecked(false);
                texture = 2;
                break;
            case R.id.material_3:
                materialBackground.setImageResource(R.mipmap.material_tip3);
                material1.setChecked(false);
                material2.setChecked(false);
                material3.setChecked(true);
                texture = 3;
                break;

            case R.id.right_image_btn3:

                upload();
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

    private void upload() {
        if (pushFlag == 1) {
            dialog.show();

            tipsActivityImpl.uploadTips(tipTv.getText().toString(), texture, getPosition(), pushFlag);

        }

    }

    /**
     * 获取当前tip的位置
     *
     * @return
     */
    private int getPosition() {
        int width = ScreenUtils.getScreenWidth(this);
        int height = ScreenUtils.getScrrenHeight(this);

        int x1 = width / 2;
        int y1 = height / 2;

        int curX = positionTip.getLeft();
        int curY = positionTip.getTop() + DensityUtils.dip2px(74);

        if (curX < x1) {
            if (curY < y1) {
                position = 1;
            } else {
                position = 2;
            }
        } else {
            if (curY < y1) {
                position = 3;
            } else {
                position = 4;
            }
        }

        return position;
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

        tipSwitch.setChecked(true);
        pushFlag = 1;
    }

    @Override
    public void uploadTips() {
        AppUtils.showToast(getApplicationContext(), "推送成功");
    }
}
