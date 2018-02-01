package com.phone.moran.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.adapter.UserBandAdapter;
import com.phone.moran.config.Constant;
import com.phone.moran.model.DeviceInfo;
import com.phone.moran.model.User;
import com.phone.moran.presenter.implPresenter.UserBandActivityImpl;
import com.phone.moran.presenter.implView.IUserBandActivity;
import com.phone.moran.tools.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserBandActivity extends BaseActivity implements View.OnClickListener, IUserBandActivity {


    @BindView(R.id.close_btn)
    TextView closeBtn;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.recycler_bander)
    RecyclerView recyclerBander;

    private List<User> list = new ArrayList<>();
    private UserBandAdapter userBandAdapter;
    private UserBandActivityImpl bandImpl;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_band);
        ButterKnife.bind(this);

        userBandAdapter = new UserBandAdapter(this, list);
        bandImpl = new UserBandActivityImpl(this, token, this);
        deviceId = getIntent().getStringExtra(Constant.DEVICE_ID);

        initView();
        setListener();
        initDataSource();
    }

    @Override
    protected void initView() {
        super.initView();

        recyclerBander.setItemAnimator(new DefaultItemAnimator());
        recyclerBander.setLayoutManager(new LinearLayoutManager(this));
        recyclerBander.setAdapter(userBandAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();

        userBandAdapter.setmBandListener(new UserBandAdapter.BandListener() {
            @Override
            public void remove(int pos, View v, User user) {
                bandImpl.process(deviceId, 3, user.getUin());
            }

            @Override
            public void approve(int pos, View v, User user) {
                bandImpl.process(deviceId, 1, user.getUin());

            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();

        if(connected) {
            bandImpl.getUserInfos(deviceId);
        }
    }

    @Override
    public void onClick(View v) {

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
    public void remove() {

    }

    @Override
    public void approve() {

    }

    @Override
    public void updateMain(List<User> list1) {

        if(list1.size() != 0) {
            list.clear();
            list.addAll(list1);
            userBandAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void updateMainDevice(List<DeviceInfo> list) {

    }
}
