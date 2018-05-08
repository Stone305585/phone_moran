package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.MainActivity;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.event.LogoutEvent;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.FileUtils;
import com.phone.moran.tools.MyActivityManager;
import com.phone.moran.tools.PreferencesUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

import static com.phone.moran.config.Constant.CHINESE;
import static com.phone.moran.config.Constant.ENGLISH;

public class SettingActivity extends BaseActivity implements View.OnClickListener {

    private static final int uintM = 1024 * 1024;

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
    @BindView(R.id.cache_size)
    TextView cacheSize;
    @BindView(R.id.version_text)
    TextView verText;

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

        title.setText(getResources().getString(R.string.mine_setting));

        File cacheDir = getExternalCacheDir();
        long size = FileUtils.getFolderSize(cacheDir);
        cacheSize.setText(String.valueOf(size/uintM) + "M");

        verText.setText(AppUtils.getAppVersionName(getApplication()));
    }

    @Override
    protected void setListener() {
        super.setListener();

        backTitle.setOnClickListener(this);
        quitBtn.setOnClickListener(this);
        aboutLL.setOnClickListener(this);
        clearLL.setOnClickListener(this);
        suggestLL.setOnClickListener(this);
        languageLL.setOnClickListener(this);
        securityLL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.quit_btn:
                EventBus.getDefault().post(new LogoutEvent());

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
                finish();
                break;

            case R.id.back_title:
                finish();
                break;

            case R.id.suggest_LL:
                startActivity(new Intent(this, SuggestActivity.class));
                break;

            case R.id.language_LL:
                showMyDialog(getResources().getString(R.string.choose_your_language));

                getYesMsg().setText("中文");
                getNoMsg().setText("English");

                setAlterListener(new AlterDialogInterface() {
                    @Override
                    public void positiveGo() {

                        ad.dismiss();

                        if(PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE) != null && PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE).equals(CHINESE)) {
                            AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.change_language_msg_c));
                            return;
                        }
                        setLanguage(false);
                        PreferencesUtils.putString(getApplicationContext(), Constant.LANGUAGE, CHINESE);

                        MyActivityManager.getInstance().finishAllActivity();
                        startActivity(new Intent(SettingActivity.this, MainActivity.class));

                    }

                    @Override
                    public void negativeGo() {

                        ad.dismiss();

                        if(PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE) != null && PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE).equals(Constant.ENGLISH)) {
                            AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.change_language_msg_e));
                            return;
                        }
                        setLanguage(true);
                        PreferencesUtils.putString(getApplicationContext(), Constant.LANGUAGE, ENGLISH);

                        MyActivityManager.getInstance().finishAllActivity();
                        startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    }
                });
                break;

            case R.id.clear_LL:
                showMyDialog(getResources().getString(R.string.delete_all_cache));

                getYesMsg().setText(getResources().getString(R.string.yes));
                getNoMsg().setText(getResources().getString(R.string.no));

                setAlterListener(new AlterDialogInterface() {
                    @Override
                    public void positiveGo() {

                        dialog.show();
                        FileUtils.cleanExternalCache(getApplicationContext());
                        dialog.dismiss();

                        File cacheDir = getExternalCacheDir();
                        long size = FileUtils.getFolderSize(cacheDir);
                        cacheSize.setText(String.valueOf(size/uintM) + "M");

                        ad.dismiss();

                    }

                    @Override
                    public void negativeGo() {

                        ad.dismiss();
                    }
                });
                break;

            case R.id.about_LL:
                startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
                break;

            case R.id.security_LL:
                startActivity(new Intent(SettingActivity.this, AccountSecurityActivity.class));
                break;

        }
    }

}
