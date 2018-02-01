package com.phone.moran;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;

import com.igexin.sdk.PushManager;
import com.phone.moran.config.Constant;
import com.phone.moran.tools.AppTypeface;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.PreferencesUtils;

import java.lang.reflect.Method;

/**
 * application设置全局
 * Created by stone on 2016/3/9.
 */
public class HHApplication extends Application {

    public static boolean loginFlag = false;
    public static boolean HxLoginFlag = false;
    private static Context mContext;

    private String token;
    private String username;
    private String userId;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;

        if (TextUtils.isEmpty(PreferencesUtils.getString(this, Constant.USER_ID)))
            PreferencesUtils.putString(this, Constant.USER_ID, "100000");
//        PreferencesUtils.putString(this, Constant.USER_ID, "4");

        //替换字体
        AppTypeface.init(this);

        AppUtils.initUtils(this);
        //环信
        //Easemob.init(this);
        //个推
        PushManager.getInstance().initialize(this);
        //校验登陆状态
        checkLogin();

        // android 7.0系统解决拍照的问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        //checkHxLogin();

        //if (EMClient.getInstance() == null) {
        //    SLogger.d("<<", "____->nulll");
        //} else {

        //loginHx("stone", "123456", "");
        //}


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
