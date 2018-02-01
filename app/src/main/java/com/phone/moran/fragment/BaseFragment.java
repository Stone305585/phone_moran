package com.phone.moran.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.phone.moran.HHApplication;
import com.phone.moran.R;
import com.phone.moran.activity.LoginActivity;
import com.phone.moran.config.Constant;
import com.phone.moran.presenter.implPresenter.BasePresenterImpl;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.DialogUtils;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.diskCache.DiskLruCacheHelper;
import com.phone.moran.tools.net.NetWorkUtil;

import java.io.IOException;

import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    private ConnectivityManager.NetworkCallback connectivityCallback;
    public boolean connected = false;
    private boolean monitoringConnectivity = false;
    private ConnectivityManager connectivityManager;
    private ViewStub viewStub;

    protected Dialog dialog;
    protected String token="";
    protected String userId;

    protected Unbinder unbinder;

    private BasePresenterImpl basePresenter;
    protected DiskLruCacheHelper diskLruCacheHelper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connected = NetWorkUtil.isNetworkConnected(getActivity());

        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        //屏幕方向只准竖屏
        if (!connected){
            AppUtils.showToast(getActivity().getApplicationContext(), getResources().getString(R.string.net_dissconncted));
        }

        token = PreferencesUtils.getString(getActivity(), Constant.ACCESS_TOKEN);
        userId = PreferencesUtils.getString(getActivity(), Constant.USER_ID);
        dialog = DialogUtils.getProgressDialog(getActivity());

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

        try {
            diskLruCacheHelper = new DiskLruCacheHelper(getActivity().getApplicationContext());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //------------------------生命周期-----------------------
    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
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
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if(basePresenter != null) {
            basePresenter.unsubcrible();
        }
    }

    /**
     * 验证是否已经登陆
     * @return
     */
    public boolean goLogin(){
        if(!HHApplication.loginFlag){
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return true;
        }else
            return false;
    }

    protected void initDataSource() {

    }

    protected void initView(View view) {

    }

    protected void setListener() {

        if(viewStub != null && !connected) {

            final View view = viewStub.inflate();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    connected = NetWorkUtil.isNetworkConnected(getActivity());

                    if (connected) {

                        view.setVisibility(View.GONE);

                        connected = true;

                        initDataSource();
                    }
                }
            });
        }
    }


    /**
     * 自定义dialog
     * @param message
     * @param disable  0：两个按钮都显示 1：只显示 确认
     * @return
     */
    protected AlertDialog showMyDialog(String message, int disable) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.my_dialog, null);
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity()).setView(v);
        TextView pt = (TextView) v.findViewById(R.id.ok_btn);
        TextView msgTv = (TextView) v.findViewById(R.id.message_dialog);
        TextView dt = (TextView) v.findViewById(R.id.default_dialog);
        TextView nt = (TextView) v.findViewById(R.id.cancel_btn);


        msgTv.setText(message);
        pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alterListener != null) {
                    alterListener.positiveGo();
                }
            }
        });

        nt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alterListener != null) {
                    alterListener.negativeGo();
                }
            }
        });
        if(disable == 1) {
            nt.setVisibility(View.GONE);
            dt.setVisibility(View.GONE);
        }
        ad = ab.create();
        ad.show();
        Button btnPositive =
                ad.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        Button btnNegative =
                ad.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setVisibility(View.GONE);
        btnPositive.setVisibility(View.GONE);

        return ad;
    }

    protected AlterDialogInterface alterListener;
    protected AlertDialog ad;

    public AlterDialogInterface getAlterListener() {
        return alterListener;
    }

    public void setAlterListener(AlterDialogInterface alterListener) {
        this.alterListener = alterListener;
    }

    interface AlterDialogInterface{

        void positiveGo();
        void negativeGo();
    }



    //popwindow view
    public View popView;
    public PopupWindow popupWindow;
    private LinearLayout wxShare;
    private LinearLayout circleShare;
    private LinearLayout sinaShare;
    private TextView cancel;

}
