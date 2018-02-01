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
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.AppUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.phone.moran.activity.InputInfoActivity.INFOS;

public class NewPaintActivity extends BaseActivity implements View.OnClickListener{

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
    @BindView(R.id.close_btn)
    ImageView closeBtn;


    private String des = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_paint);
        ButterKnife.bind(this);

        initView();
        setListener();
    }


    @Override
    protected void initView() {
        super.initView();

        title.setText("新建画单");
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

        submitBtn.setOnClickListener(this);
        closeBtn.setOnClickListener(this);

        backTitle.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.submit_btn:

                if(TextUtils.isEmpty(des)) {
                    AppUtils.showToast(getApplicationContext(), "请输入文字后再提交");
                    return;
                }

                LocalPaints lp = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId);
                ArrayList<Paint> paints = lp.getPaints();

                for( int i = 0; i < paints.size(); i++) {
                    if(paints.get(i).getPaint_title().equals(des)) {
                        AppUtils.showToast(getApplicationContext(), "已存在改画单");
                        return;
                    }
                }

                Intent data = new Intent();
                data.putExtra(INFOS, des);
                setResult(RESULT_OK, data);
                AppUtils.showToast(getApplicationContext(), "提交成功");
                finish();
                break;
            case R.id.close_btn:
                msgEt.setText("");
                des = "";
                break;

            case R.id.back_title:
                finish();
                break;

        }
    }
}
