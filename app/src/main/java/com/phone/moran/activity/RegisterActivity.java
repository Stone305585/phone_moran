package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.MainPagerAdapter;
import com.phone.moran.fragment.EmailRegisterFragment;
import com.phone.moran.fragment.MobileRegisterFragment;
import com.phone.moran.model.RegisterBack;
import com.phone.moran.model.User;
import com.phone.moran.presenter.implView.ILoginActivity;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.tools.SLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.phone.moran.fragment.MobileRegisterFragment.PASSWORD;
import static com.phone.moran.fragment.MobileRegisterFragment.PHONE;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, ILoginActivity{

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
    @BindView(R.id.email_btn)
    TextView emailBtn;
    @BindView(R.id.mobile_btn)
    TextView mobileBtn;
    @BindView(R.id.viewpager_register)
    ViewPager viewpagerRegister;

    MobileRegisterFragment mobileRegisterFragment;
    EmailRegisterFragment emailRegisterFragment;
    FragmentManager fm;
    MainPagerAdapter mainPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initView();
        setListener();

    }

    @Override
    protected void initView() {
        super.initView();

        title.setText(getResources().getString(R.string.register_account));
        mobileRegisterFragment = MobileRegisterFragment.newInstance("", "");
        emailRegisterFragment = EmailRegisterFragment.newInstance("", "");
        fm = getSupportFragmentManager();
        mainPagerAdapter = new MainPagerAdapter(fm, fragmentList);
        fragmentList.add(emailRegisterFragment);
        fragmentList.add(mobileRegisterFragment);
        viewpagerRegister.setAdapter(mainPagerAdapter);

        mobileBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
        emailBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
    }

    @Override
    protected void setListener() {
        super.setListener();

        mobileBtn.setOnClickListener(this);
        emailBtn.setOnClickListener(this);
        backTitle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mobile_btn:
                viewpagerRegister.setCurrentItem(1);
                emailBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                mobileBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                break;
            case R.id.email_btn:
                viewpagerRegister.setCurrentItem(0);
                mobileBtn.setBackgroundColor(getResources().getColor(R.color.transparent));
                emailBtn.setBackground(getResources().getDrawable(R.mipmap.tab_selected_bg));
                break;
            case R.id.back_title:
                finish();
                break;
        }
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
    public void loginSuccess(RegisterBack registerBack) {

    }

    @Override
    public void code() {
        SLogger.d("<<", "=-->code 成功------------1");

        if(viewpagerRegister.getCurrentItem() == 0) {
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra(PHONE, emailRegisterFragment.getPhone());
            intent.putExtra(PASSWORD, emailRegisterFragment.getPassword());
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, VerifyActivity.class);
            intent.putExtra(PHONE, mobileRegisterFragment.getPhone());
            intent.putExtra(PASSWORD, mobileRegisterFragment.getPassword());
            startActivity(intent);
        }
    }

    @Override
    public void updateUserInfo(User user) {

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
