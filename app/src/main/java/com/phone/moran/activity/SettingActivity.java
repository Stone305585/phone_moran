package com.phone.moran.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.about_LL)
    LinearLayout aboutLL;
    @BindView(R.id.suggest_LL)
    LinearLayout suggestLL;
    @BindView(R.id.version_LL)
    LinearLayout versionLL;
    @BindView(R.id.language_LL)
    LinearLayout languageLL;
    @BindView(R.id.clear_LL)
    LinearLayout clearLL;
    @BindView(R.id.security_LL)
    LinearLayout securityLL;

    @BindView(R.id.quit_btn)
    TextView quitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        initView();
        setListener();
    }


    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();

        backTitle.setOnClickListener(this);
        quitBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_btn:
                PreferencesUtils.putString(this, Constant.ACCESS_TOKEN, "");
                PreferencesUtils.putString(this, Constant.AVATAR, "");
                PreferencesUtils.putString(this, Constant.USER_NAME, "");
                PreferencesUtils.putString(this, Constant.MINE_BG, "");
                PreferencesUtils.putString(this, Constant.USER_ID, "100000");

            /*LocalPaints l = new LocalPaints();
            //获取本地的 local collect  为空则放入默认的画单  我的收藏
            ArrayList<Paint> paints = new ArrayList<>();
            Paint p = new Paint();
            p.setPaint_id(-1);
            p.setPaint_title("我的收藏");
            paints.add(p);
            l.setPaints(paints);
            diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, l);*/

                HHApplication.checkLogin();

                AppUtils.showToast(getApplicationContext(), "已退出");
                break;

            case R.id.back_title:
                finish();
                break;
        }
    }
}
