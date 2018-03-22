package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.LocalPaints;
import com.phone.moran.model.Paint;
import com.phone.moran.tools.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditPaintActivity extends BaseActivity {

    @BindView(R.id.image_paint)
    ImageView imagePaint;
    @BindView(R.id.title_paint_et)
    EditText titlePaintEt;
    @BindView(R.id.des_paint_et)
    EditText desPaintEt;
    @BindView(R.id.num_input_tv)
    TextView numInputTv;
    @BindView(R.id.save_btn)
    TextView saveBtn;

    String paintTitle;
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


    int textNum;
    String des;
    int titleIndex;
    Paint p;
    LocalPaints lp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_paint);
        ButterKnife.bind(this);

        paintTitle = getIntent().getStringExtra(Constant.PAINT_TITLE);

        lp = diskLruCacheHelper.getAsSerializable(Constant.LOCAL_MINE + userId);
        p = lp.getPaintByTitle(paintTitle);

        initView();

        setListener();
    }


    @Override
    protected void initView() {
        super.initView();

        title.setText(getResources().getString(R.string.edit_paint_msg));
        titlePaintEt.setText(paintTitle);

        textNum = Integer.valueOf(numInputTv.getText().toString());

        if(p.getPicture_info().size() != 0) {
            titleIndex = 0;
            ImageLoader.displayImg(this, p.getPicture_info().get(0).getPicture_url(), imagePaint);
        }

        desPaintEt.setText(p.getPaint_detail());
    }


    @Override
    protected void setListener() {
        super.setListener();

        desPaintEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                des = s.toString();

                int restNum = textNum - des.length();

                numInputTv.setText(String.valueOf(restNum < 0 ? 0 : restNum));

            }
        });

        imagePaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(EditPaintActivity.this, GridPicActivity.class);
                intent.putExtra(Constant.PAINT, p);
                startActivityForResult(intent, GET_PIC);

            }
        });

        backTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titlePaintEt.getText().toString();

                p.setPaint_title(title);
                p.setPaint_detail(des);

                if (titleIndex != -1)
                    p.setTitle_url(p.getPicture_info().get(titleIndex).getPicture_url());

                diskLruCacheHelper.put(Constant.LOCAL_MINE + userId, lp);

                Intent intent = new Intent();
                intent.putExtra(Constant.PAINT_TITLE, title);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private static final int GET_PIC = 120;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == GET_PIC) {
                titleIndex = data.getIntExtra(Constant.PICTURE, 0);
            }
        }
    }
}
