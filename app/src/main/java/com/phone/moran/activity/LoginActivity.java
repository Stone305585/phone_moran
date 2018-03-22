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
import android.widget.Toast;

import com.phone.moran.HHApplication;
import com.phone.moran.MainActivity;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalMoods;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Mood;
import com.phone.moran.model.Paint;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.ThirdLoginInfo;
import com.phone.moran.model.User;
import com.phone.moran.presenter.implPresenter.LoginActivityImpl;
import com.phone.moran.presenter.implView.ILoginActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.SLogger;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.ArrayList;
import java.util.Map;

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
    Button loginBtn;
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

    //-----------------友盟 第三方登录-----------------
    private UMShareAPI mShareAPI;
    private String deviceId;
    private int thirdPartyType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);


        loginActivityImpl = new LoginActivityImpl(this, token, this);

        mShareAPI = UMShareAPI.get(this);
        initView();
        setListener();

        initUmeng(savedInstanceState);
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
                        AppUtils.showToast(this, getResources().getString(R.string.user_name_email));
                    } else {
                        AppUtils.showToast(this, getResources().getString(R.string.input_password));
                    }
                }

                break;
            case R.id.register_btn:

                startActivity(new Intent(this, RegisterKnowActivity.class));
                break;
            case R.id.later_btn:
                finish();
                break;
            case R.id.wechat_btn:

                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_demo";
                HHApplication.api.sendReq(req);
//                mShareAPI.doOauthVerify(this, SHARE_MEDIA.WEIXIN, umAuthListener);
//                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.WEIXIN, umAuthListener);
                break;
            case R.id.facebook_btn:
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.FACEBOOK, umAuthListener);

                break;
            case R.id.twitter_btn:
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.TWITTER, umAuthListener);

                break;
            case R.id.weibo_btn:
                mShareAPI.getPlatformInfo(this, SHARE_MEDIA.SINA, umAuthListener);

                break;

            case R.id.forget_password_tv:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
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
        forgetPasswordTv.setOnClickListener(this);

        wechatBtn.setOnClickListener(this);
        weiboBtn.setOnClickListener(this);
        facebookBtn.setOnClickListener(this);
        twitterBtn.setOnClickListener(this);

    }

    private void initUmeng(Bundle savedInstanceState) {

//        UMShareAPI.get(this).auisfetchAuthResultWithBundle(this, savedInstanceState, umAuthListener);
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
            p.setPaint_title(getResources().getString(R.string.mine_collect));
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
            moodNu.setMood_name(getResources().getString(R.string.anger));
            moodNu.setRes_id(R.mipmap.mood_nu);
            moodList.add(moodNu);

            Mood moodSi = new Mood();
            moodSi.setMood_name(getResources().getString(R.string.think));
            moodNu.setMood_id(Constant.MOOD_SI);
            moodSi.setRes_id(R.mipmap.mood_si);
            moodList.add(moodSi);

            Mood moodKong = new Mood();
            moodKong.setMood_id(Constant.MOOD_KONG);
            moodKong.setMood_name(getResources().getString(R.string.fear));
            moodKong.setRes_id(R.mipmap.mood_kong);
            moodList.add(moodKong);

            Mood moodJing = new Mood();
            moodJing.setMood_id(Constant.MOOD_JING);
            moodJing.setMood_name(getResources().getString(R.string.boggle));
            moodJing.setRes_id(R.mipmap.mood_jing);
            moodList.add(moodJing);

            Mood moodYou = new Mood();
            moodYou.setMood_id(Constant.MOOD_YOU);
            moodYou.setMood_name(getResources().getString(R.string.melancholy));
            moodYou.setRes_id(R.mipmap.mood_you);
            moodList.add(moodYou);

            Mood moodXi = new Mood();
            moodXi.setMood_id(Constant.MOOD_XI);
            moodXi.setMood_name(getResources().getString(R.string.happiness));
            moodXi.setRes_id(R.mipmap.mood_xi);
            moodList.add(moodXi);

            Mood moodBei = new Mood();
            moodBei.setMood_id(Constant.MOOD_BEI);
            moodBei.setMood_name(getResources().getString(R.string.sadness));
            moodBei.setRes_id(R.mipmap.mood_bei);
            moodList.add(moodBei);

            Mood moodWu = new Mood();
            moodWu.setMood_id(Constant.MOOD_WU);
            moodWu.setMood_name(getResources().getString(R.string.emptiness));
            moodWu.setRes_id(R.mipmap.mood_wu);
            moodList.add(moodWu);

            lm.setMoods(moodList);

            diskLruCacheHelper.put(Constant.LOCAL_MOOD + userId, lm);

        }
        AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.sign_in_success));
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



    /**
     * 第三方授权
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            SocializeUtils.safeShowDialog(dialog);
        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            SLogger.d("<<", "auth data---》》" + data.toString());

            //{country=中国, unionid=oCTAm05sPl3v0YCCSeCUqr4rdKLM, gender=男, city=海淀, openid=oLw1k1GeDgO7Zm01U_4vWvelxh28, language=zh_CN, profile_image_url=http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJhn3cibsWkZJdkaCACHrcMxHgftAMibA5fJ9akv7sgdCBhvk3Obulr1KFjsuEhjgbvsb1lVJE2k4tA/132, accessToken=6_B7lVc5GmqjO6M0deO6MoXCNKqhiCnO5mUiJ7j7SefC85SvNI7uOpzZzMKVB-ON1RXLSf31UJ7dSaTxhdYgYFZS5lto1L7UbzbL7i8iTINUM, access_token=6_B7lVc5GmqjO6M0deO6MoXCNKqhiCnO5mUiJ7j7SefC85SvNI7uOpzZzMKVB-ON1RXLSf31UJ7dSaTxhdYgYFZS5lto1L7UbzbL7i8iTINUM, uid=oCTAm05sPl3v0YCCSeCUqr4rdKLM, province=北京, screen_name=stone, name=stone, iconurl=http://thirdwx.qlogo.cn/mmopen/vi_32/PiajxSqBRaEJhn3cibsWkZJdkaCACHrcMxHgftAMibA5fJ9akv7sgdCBhvk3Obulr1KFjsuEhjgbvsb1lVJE2k4tA/132, expiration=1518098031601, expires_in=1518098031601, refreshToken=6_MrwteL5mjDJw7FXqgWX5WDqebDWBmhl21P38pvKHVQ1CSsSPQcE2Qq9fYltWCOwN4of1Gb9uL-Vg6BEtr6MRm7DJXkok7p8hq7U3vBLs7cg}
//            Toast.makeText(getApplicationContext(), "onRestoreInstanceState Authorize succeed", Toast.LENGTH_SHORT).show();
            ThirdLoginInfo thirdLoginInfo = new ThirdLoginInfo();
            thirdLoginInfo.setAuth_token(data.get("unionid"));
            thirdLoginInfo.setRegister_type(ThirdLoginInfo.WECHAT);
            loginActivityImpl.thirdBind(thirdLoginInfo);


//                shareAdapter.notifyDataSetChanged();
            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SLogger.d("<<", "auth data---》》" + t.getMessage());

            SocializeUtils.safeCloseDialog(dialog);
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SLogger.d("<<", "auth data---》》cancel");

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.cancel), Toast.LENGTH_SHORT).show();
//                shareAdapter.notifyDataSetChanged();
            SocializeUtils.safeCloseDialog(dialog);
        }
    };

    /**
     * 通过第三方   获取用户信息
     */
    private UMAuthListener umGetInfoListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            if (data != null) {
                SLogger.d("<<", "info data---》》" + data.toString());
                ThirdLoginInfo thirdLoginInfo = new ThirdLoginInfo();
                switch (thirdPartyType) {
                    //WECHAT
                    case 2:
//                        thirdLoginInfo.setUid(data.get(WX_UIN));
//                        thirdLoginInfo.setDeviceId(deviceId);
//                        thirdLoginInfo.setCity(data.get("city"));
//                        thirdLoginInfo.setCountry(data.get("country"));
//                        thirdLoginInfo.setHeadImageUrl(data.get("headimgurl"));
//                        thirdLoginInfo.setProvince(data.get("province"));
//                        thirdLoginInfo.setSex(Integer.valueOf(data.get("sex")));
//                        thirdLoginInfo.setUsername(data.get("nickname"));
//                        thirdLoginInfo.setThirdPartyType(WX);
                        break;
                    //QQ
                    case 3:
//                        thirdLoginInfo.setUid(data.get(QQ_UIN));
//                        thirdLoginInfo.setDeviceId(deviceId);
//                        thirdLoginInfo.setCity(data.get("city"));
//                        thirdLoginInfo.setCountry("");
//                        thirdLoginInfo.setHeadImageUrl(data.get("profile_image_url"));
//                        thirdLoginInfo.setProvince(data.get("province"));
//                        thirdLoginInfo.setSex(data.get("gender").equals(Constant.MALE) ? 1 : 2);
//                        thirdLoginInfo.setUsername(data.get("screen_name"));
//                        thirdLoginInfo.setThirdPartyType(QQ);//18813176972
                        break;
                }
                //TODO 绑定三方登陆
//                checkThirdLogin(thirdLoginInfo);
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            SLogger.d("<<", "info-->>>>FAILED");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            SLogger.d("<<", "info-->>>>CANCELED");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        String code = PreferencesUtils.getString(getApplicationContext(), Constant.WX_CODE);

        if(code != null && code.length() != 0 && !code.equals("-1")){
            dialog.show();
            ThirdLoginInfo thirdLoginInfo = new ThirdLoginInfo();
            thirdLoginInfo.setAuth_token(code);
            thirdLoginInfo.setRegister_type(ThirdLoginInfo.WECHAT);
            loginActivityImpl.thirdBind(thirdLoginInfo);

            PreferencesUtils.putString(getApplicationContext(), Constant.WX_CODE, "-1");
        }
    }
}
