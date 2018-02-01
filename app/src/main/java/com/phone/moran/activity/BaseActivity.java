package com.phone.moran.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.presenter.implPresenter.BasePresenterImpl;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.MyActivityManager;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.diskCache.DiskLruCacheHelper;
import com.phone.moran.tools.net.NetWorkUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.io.IOException;

import butterknife.Unbinder;

/**
 * Created by stone on 2016/3/9.
 */
public class BaseActivity extends AppCompatActivity {

    public CollapsingToolbarLayoutState state;
    private BasePresenterImpl basePresenter;


    public enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }


    protected String token="";
    protected ProgressDialog dialog;
    protected Intent intentGet;
    protected String userId;
    private ViewStub viewStub;

    //popwindow view
    protected View popView;
    protected PopupWindow popupWindow;
    protected LinearLayout wxShare;
    protected LinearLayout circleShare;
    protected LinearLayout sinaShare;
    protected TextView cancel;

    //popwindow view
    protected View popViewWechat;
    protected PopupWindow popupWindowWechat;
    protected TextView wxShareWechat;
    protected TextView circleShareWechat;
    protected TextView cancelWechat;

    //网络状态
    private ConnectivityManager.NetworkCallback connectivityCallback;
    public boolean connected = true;
    private boolean monitoringConnectivity = false;
    private ConnectivityManager connectivityManager;
    private Unbinder unbinder;
    protected DiskLruCacheHelper diskLruCacheHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        MyActivityManager.getInstance().addActivity(this);

        //屏幕方向只准竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        connected = NetWorkUtil.isNetworkConnected(this);

        if (!connected){
            AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.net_dissconncted));
        }

        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        String a = new String("adsafg");
        if (!connected) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                connectivityCallback = new ConnectivityManager.NetworkCallback() {
                    @Override
                    public void onAvailable(Network network) {

                        connected = true;

                        initDataSource();
                    }

                    @Override
                    public void onLost(Network network) {
                        connected = false;
                    }
                };
                //监听网络状态
                connectivityManager.registerNetworkCallback(
                        new NetworkRequest.Builder()
                                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                                .build(),
                        connectivityCallback);

                monitoringConnectivity = true;

            }
        }

        token = PreferencesUtils.getString(this, Constant.ACCESS_TOKEN);
        userId = PreferencesUtils.getString(this, Constant.USER_ID);
        dialog = new ProgressDialog(this, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
        dialog.setMessage("     请稍后...");

        try {
            diskLruCacheHelper = new DiskLruCacheHelper(getApplicationContext());

        } catch (IOException e) {
            e.printStackTrace();
        }

//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(getResources().getColor(R.color.bg_gray_blue));
//        }

    }


    //------------------------生命周期-----------------------
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        //取消监听网络状态变化

        if (monitoringConnectivity) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                connectivityManager.unregisterNetworkCallback(connectivityCallback);
            }
            monitoringConnectivity = false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(basePresenter != null) {
            basePresenter.unsubcrible();
        }

        MyActivityManager.getInstance().finishActivity(this);
    }

    /**
     * 验证是否已经登陆
     * @return
     */
    protected boolean goLogin(){
        if(!HHApplication.loginFlag){
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }else
            return false;
    }
    //-----友盟分享--------------
    public void initPopWin(){
        popView = LayoutInflater.from(this).inflate(R.layout.pop_share, null);
        popupWindow = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setContentView(popView);
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.text_red));
        popupWindow.setBackgroundDrawable(dw);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });

        wxShare = (LinearLayout) popView.findViewById(R.id.wx_share);
        circleShare = (LinearLayout) popView.findViewById(R.id.circle_share);
        sinaShare = (LinearLayout) popView.findViewById(R.id.sina_share);
        cancel = (TextView) popView.findViewById(R.id.cancel_share);
    }

    //-----友盟分享--------------
    public void initWechatPopWin(){
        popViewWechat = LayoutInflater.from(this).inflate(R.layout.pop_select_win, null);
        popupWindowWechat = new PopupWindow(popViewWechat, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindowWechat.setContentView(popViewWechat);
        popupWindowWechat.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(getResources().getColor(R.color.transparent));
        popupWindowWechat.setBackgroundDrawable(dw);

        popupWindowWechat.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha=1f;
                getWindow().setAttributes(params);
            }
        });

        wxShareWechat = (TextView) popViewWechat.findViewById(R.id.friend_wechat);
        circleShareWechat = (TextView) popViewWechat.findViewById(R.id.circle_wechat);
        cancelWechat = (TextView) popViewWechat.findViewById(R.id.cancel_wechat);
    }



    protected void initDataSource() {

    }

    protected void initView() {

    }

    protected void setListener() {

        if (viewStub != null && !connected) {

            View v = viewStub.inflate();

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connected = true;

                    v.setVisibility(View.GONE);

                    initDataSource();
                }
            });
        }
    }



    UMShareListener shareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调，可以用来处理等待框，或相关的文字提示
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
        }
    };


    class PopShareListener implements View.OnClickListener{

        private SHARE_MEDIA platform;
        private String title;
        private String text;
        private String imgUrl;
        private String url;
        public PopShareListener(SHARE_MEDIA platform, final String title, final String text, final String imgUrl, final String url){
            this.platform = platform;
            this.title = title;
            this.text = text;
            this.imgUrl = imgUrl;
            this.url = url;
        }

        @Override
        public void onClick(View v) {
            new ShareAction(BaseActivity.this).setPlatform(platform).setCallback(umShareListener)
                    .withText(text)
                    .withMedia(new UMImage(BaseActivity.this, imgUrl))
                    .share();
        }
    }


    protected UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(getApplicationContext(),platform + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getApplicationContext(),platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getApplicationContext(),platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 获取一个 View 的缓存视图
     *
     * @param view
     * @return
     */
    protected Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

}
