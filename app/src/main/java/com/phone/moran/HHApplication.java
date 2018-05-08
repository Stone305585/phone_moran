package com.phone.moran;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.igexin.sdk.PushManager;
import com.phone.moran.config.Constant;
import com.phone.moran.tools.AppTypeface;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.SLogger;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * application设置全局
 * Created by stone on 2016/3/9.
 *
 * 开启调试模式 修改的地方
 *
 * 1：Slloger
 * 2：停掉了登陆页面进入前的动画   修改成进入页面后直接跳转到注册
 */
public class HHApplication extends Application {

    public static boolean loginFlag = false;
    public static boolean HxLoginFlag = false;
    private static Context mContext;

    private String token;
    private String username;
    private String userId;

    public static IWXAPI api;

    public IWXAPI getApi() {
        return api;
    }

    public void setApi(IWXAPI api) {
        this.api = api;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        if (TextUtils.isEmpty(PreferencesUtils.getString(this, Constant.USER_ID)))
            PreferencesUtils.putString(this, Constant.USER_ID, "100000");
//        PreferencesUtils.putString(this, Constant.USER_ID, "4");

        SLogger.d("<<", "user_id-->>>" + TextUtils.isEmpty(PreferencesUtils.getString(this, Constant.USER_ID)));

        PreferencesUtils.putString(this, Constant.FIRST, null);


        //替换字体
        AppTypeface.init(this);

        AppUtils.initUtils(this);
        //环信
        //Easemob.init(this);
        //个推
        PushManager.getInstance().initialize(this);
        //校验登陆状态
        checkLogin();

        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID);
        // 将该app注册到微信
        api.registerApp(Constant.WX_APPID);

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        if (PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE) != null)
            if (PreferencesUtils.getString(getApplicationContext(), Constant.LANGUAGE).equals(Constant.ENGLISH)) {
                set(true);
            } else {
                set(false);
            }


        //UMENG
//        Config.DEBUG = true;
//        QueuedWork.isUseThreadPool = true;
        UMShareAPI.get(this);

    }

    public void set(boolean isEnglish) {

        Configuration configuration = getResources().getConfiguration();
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        if (isEnglish) {
            //设置英文  这里一定要设置成LOCAL.US  LOCAL.ENGLISH不可以的
            configuration.locale = Locale.US;
        } else {
            //设置中文
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        }
        //更新配置
        getResources().updateConfiguration(configuration, displayMetrics);
    }

    {
        PlatformConfig.setWeixin("wx4f0a156d8bb04f93", "ba2641ae564cc965e0f435d475fcaf54");
        //微信 appid appsecret
        PlatformConfig.setSinaWeibo("1810058338", "2e1f6b926c73b8a8ea3f465069730e2b", "https://api.weibo.com/oauth2/default.html");
        //新浪微博 appkey appsecret
        PlatformConfig.setTwitter("1105329593", "J8K7Mh1bxoVOnnGB");
        // QQ和Qzone appid appkey
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class<?> clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Exception e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


    /**
     * 设置用户登陆标志
     *
     * @return
     */
    public static void checkLogin() {
        String token = PreferencesUtils.getString(mContext, Constant.ACCESS_TOKEN);
        if (TextUtils.isEmpty(token)) {
            loginFlag = false;

        } else
            loginFlag = true;
    }


}
