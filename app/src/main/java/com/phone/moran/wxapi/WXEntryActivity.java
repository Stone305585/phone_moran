package com.phone.moran.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.phone.moran.HHApplication;
import com.phone.moran.config.Constant;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.User;
import com.phone.moran.presenter.implView.ILoginActivity;
import com.phone.moran.tools.PreferencesUtils;
import com.phone.moran.tools.SLogger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler, ILoginActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //如果没回调onResp，八成是这句没有写
        HHApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        HHApplication.api.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

        SLogger.d("<<", "---------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>******" + JSON.toJSONString(baseReq));

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == BaseResp.ErrCode.ERR_OK) {
            switch (resp.getType()) {
                case ConstantsAPI.COMMAND_SENDAUTH://获取授权码
                    if (resp instanceof SendAuth.Resp) {
                        SendAuth.Resp r = (SendAuth.Resp) resp;
                        PreferencesUtils.putString(getApplicationContext(), Constant.WX_CODE, r.code);

                    }
                    break;
                case ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX:// 分享到微信
                    PreferencesUtils.putString(getApplicationContext(), Constant.WX_SHARE_CODE, "success");
                    break;
            }
        } else {
            PreferencesUtils.putString(getApplicationContext(), Constant.WX_CODE, "-1");
            PreferencesUtils.putString(getApplicationContext(), Constant.WX_SHARE_CODE, "-1");

            if (resp.errCode == BaseResp.ErrCode.ERR_AUTH_DENIED) {//用户拒绝授权
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:

                        break;
                }
            } else if (resp.errCode == BaseResp.ErrCode.ERR_USER_CANCEL) {//用户取消
                switch (resp.getType()) {
                    case ConstantsAPI.COMMAND_SENDAUTH:
                        break;
                }
            }
        }


//        if(resp.errCode == 0) {
//            SLogger.d("<<", "------登陆成功-------->"+JSON.toJSONString(resp) + "-->" + ((SendAuth.Resp) baseResp).code);
//            PreferencesUtils.putString(getApplicationContext(), Constant.WX_CODE, ((SendAuth.Resp) baseResp).code);
//        } else {
//            PreferencesUtils.putString(getApplicationContext(), Constant.WX_CODE, "-1");
//        }
        finish();
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hidProgressDialog() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void loginSuccess(RegisterBack registerBack) {

    }

    @Override
    public void code() {

    }

    @Override
    public void updateUserInfo(User user) {

    }
}
