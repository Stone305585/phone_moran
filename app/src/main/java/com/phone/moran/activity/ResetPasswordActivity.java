package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.presenter.implPresenter.RegisterActivityImpl;
import com.phone.moran.presenter.implView.IResetPasswordActivity;
import com.phone.moran.tools.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPasswordActivity extends BaseActivity implements IResetPasswordActivity {

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
    @BindView(R.id.tip_reset_tv)
    TextView tipResetTv;
    @BindView(R.id.password_et)
    EditText passwordEt;
    @BindView(R.id.password_repeat_et)
    EditText passwordRepeatEt;
    @BindView(R.id.send_btn)
    Button sendBtn;

    String password;
    String username;

    private RegisterActivityImpl impl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);

        impl = new RegisterActivityImpl(this, token,this);
        username = getIntent().getStringExtra(Constant.PHONE);

        initView();
        setListener();
    }

    @Override
    protected void initView() {
        super.initView();

        title.setText(getResources().getString(R.string.reset_password));

        String[] s = getResources().getString(R.string.reset_pass_hint).split("-");

        String ss = s[0] + username + s[1];

        SpannableString ssb = new SpannableString(ss);
        //设置颜色
        ssb.setSpan(new ForegroundColorSpan(0xff1840ae), ss.indexOf(username), ss.indexOf(username) + username.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        tipResetTv.setText(ssb);
    }

    @Override
    protected void setListener() {
        super.setListener();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = passwordEt.getText().toString();
                String password1 = passwordRepeatEt.getText().toString();
                if (!password.equals(password1)) {
                    AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.password_not_identify));
                } else {
                    if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(username)) {
                        impl.resetPassword(username, password1);

                    }
                }
            }
        });

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


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
    public void resetSuccess() {

        AppUtils.showToast(getApplicationContext(), getResources().getString(R.string.reset_pass_success));

        startActivity(new Intent(this, LoginActivity.class));

        finish();

    }
}
