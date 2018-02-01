package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.tools.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputInfoActivity extends BaseActivity {

    public static final String INFOS = "infos";

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
    @BindView(R.id.msg_et)
    EditText msgEt;
    @BindView(R.id.submit_btn)
    Button submitBtn;

    private String des = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_info);
        ButterKnife.bind(this);

        initView();
        setListener();
        initDataSource();
    }

    @Override
    protected void initView() {
        super.initView();

        title.setText("简介");
        msgEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                des = s.toString();
            }
        });
    }

    @Override
    protected void setListener() {
        super.setListener();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(des)) {

                    Intent data = new Intent();
                    data.putExtra(INFOS, des);
                    setResult(RESULT_OK, data);
                    AppUtils.showToast(getApplicationContext(), "提交成功");
                    finish();
                } else {
                    AppUtils.showToast(getApplicationContext(), "请填写内容后提交");

                }
            }
        });

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(des)) {
                    Intent data = new Intent();
                    data.putExtra(INFOS, des);
                    setResult(RESULT_OK, data);
                }

                finish();
            }
        });
    }

    @Override
    protected void initDataSource() {
        super.initDataSource();
    }
}
