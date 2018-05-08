package com.phone.moran.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.phone.moran.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoNetActivity extends BaseActivity {

    @BindView(R.id.no_net_btn)
    ImageView noNetBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_net);
        ButterKnife.bind(this);

        initView();

        setListener();
    }


    @Override
    protected void initView() {
        super.initView();
    }


    @Override
    protected void setListener() {
        super.setListener();

        noNetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
