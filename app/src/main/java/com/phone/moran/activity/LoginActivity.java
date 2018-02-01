package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.MainActivity;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Mood;
import com.phone.moran.model.Paint;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.User;
import com.phone.moran.presenter.implPresenter.LoginActivityImpl;
import com.phone.moran.presenter.implView.ILoginActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener, ILoginActivity {

    @BindView(R.id.accountEt)
    EditText accountEt;
    @BindView(R.id.passwordEt)
    EditText passwordEt;
    @BindView(R.id.forget_password_tv)
    TextView forgetPasswordTv;
    @BindView(R.id.login_btn)
    TextView loginBtn;
    @BindView(R.id.register_btn)
    Button registerBtn;
    @BindView(R.id.later_btn)
    Button laterBtn;
    @BindView(R.id.wechat_btn)
    LinearLayout wechatBtn;
    @BindView(R.id.weibo_btn)
    LinearLayout weiboBtn;
    @BindView(R.id.facebook_btn)
    LinearLayout facebookBtn;
    @BindView(R.id.twitter_btn)
    LinearLayout twitterBtn;

    LoginActivityImpl loginActivityImpl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginActivityImpl = new LoginActivityImpl(this, token, this);
        initView();
        setListener();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login_btn:
                String register_id = accountEt.getText().toString();
                String password = passwordEt.getText().toString();

                if (!TextUtils.isEmpty(register_id) && !TextUtils.isEmpty(password))
                    loginActivityImpl.login(register_id, password);
                else {
                    if (TextUtils.isEmpty(register_id)) {
                        AppUtils.showToast(this, "请输入邮箱或手机号");
                    } else {
                        AppUtils.showToast(this, "请输入密码");
                    }
                }

                break;
            case R.id.register_btn:

                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.later_btn:
                finish();
                break;
            case R.id.wechat_btn:
                break;
            case R.id.facebook_btn:
                break;
            case R.id.twitter_btn:
                break;
            case R.id.weibo_btn:
                break;
        }
    }

    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void setListener() {
        super.setListener();
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        laterBtn.setOnClickListener(this);

    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
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
    public void loginSuccess(RegisterBack back) {

        PreferencesUtils.putString(getApplicationContext(), Constant.USER_ID, String.valueOf(back.getUin()));
        PreferencesUtils.putString(getApplicationContext(), Constant.ACCESS_TOKEN, back.getToken());
        HHApplication.checkLogin();

        userId = String.valueOf(back.getUin());

        loginActivityImpl.getUserInfo();

    }

    @Override
    public void code() {

    }

    @Override
    public void updateUserInfo(User user) {

        if (user.getHead_url() != null)
            PreferencesUtils.putString(getApplicationContext(), Constant.AVATAR, user.getHead_url());
        if(user.getNick_name() != null) {
            PreferencesUtils.putString(getApplicationContext(), Constant.USER_NAME, user.getNick_name());
        }
        if(user.getBackground() != null) {
            PreferencesUtils.putString(getApplicationContext(), Constant.MINE_BG, user.getBackground());
        }

        LocalPaints l = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId);
        if(l == null)
            l = new LocalPaints();
        //获取本地的 local collect  为空则放入默认的画单  我的收藏
        if(l.getPaints() == null || l.getPaints().size() == 0) {
            ArrayList<Paint> paints = new ArrayList<>();
            Paint p = new Paint();
            p.setPaint_id(-1);
            p.setPaint_title("我的收藏");
            paints.add(p);
            l.setPaints(paints);
            diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, l);
        }

        //存放本地心情初始化
        LocalMoods lm = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MOOD + userId);

        if(lm == null) {
            lm = new LocalMoods();
            ArrayList<Mood> moodList = new ArrayList<>();

            Mood moodNu = new Mood();
            moodNu.setMood_id(Constant.MOOD_NU);
            moodNu.setMood_name("怒");
            moodNu.setRes_id(R.mipmap.mood_nu);
            moodList.add(moodNu);

            Mood moodSi = new Mood();
            moodSi.setMood_name("思");
            moodNu.setMood_id(Constant.MOOD_SI);
            moodSi.setRes_id(R.mipmap.mood_si);
            moodList.add(moodSi);

            Mood moodKong = new Mood();
            moodKong.setMood_id(Constant.MOOD_KONG);
            moodKong.setMood_name("恐");
            moodKong.setRes_id(R.mipmap.mood_kong);
            moodList.add(moodKong);

            Mood moodJing = new Mood();
            moodJing.setMood_id(Constant.MOOD_JING);
            moodJing.setMood_name("惊");
            moodJing.setRes_id(R.mipmap.mood_jing);
            moodList.add(moodJing);

            Mood moodYou = new Mood();
            moodYou.setMood_id(Constant.MOOD_YOU);
            moodYou.setMood_name("忧");
            moodYou.setRes_id(R.mipmap.mood_you);
            moodList.add(moodYou);

            Mood moodXi = new Mood();
            moodXi.setMood_id(Constant.MOOD_XI);
            moodXi.setMood_name("喜");
            moodXi.setRes_id(R.mipmap.mood_xi);
            moodList.add(moodXi);

            Mood moodBei = new Mood();
            moodBei.setMood_id(Constant.MOOD_BEI);
            moodBei.setMood_name("悲");
            moodBei.setRes_id(R.mipmap.mood_bei);
            moodList.add(moodBei);

            Mood moodWu = new Mood();
            moodWu.setMood_id(Constant.MOOD_WU);
            moodWu.setMood_name("空");
            moodWu.setRes_id(R.mipmap.mood_wu);
            moodList.add(moodWu);

            lm.setMoods(moodList);

            diskLruCacheHelper.put(Constant.LOCAL_MOOD + userId, lm);

        }

        startActivity(new Intent(this, MainActivity.class));

        finish();


    }

    /**
     * 用于点击edittext以外的位置，隐藏键盘
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (AppUtils.hidenSoftInputASimple(ev, this)) {
            case 0:
                return super.dispatchTouchEvent(ev);
            case 1:
                return true;
            case 2:
                return onTouchEvent(ev);
            default:
                return true;
        }

    }
}
