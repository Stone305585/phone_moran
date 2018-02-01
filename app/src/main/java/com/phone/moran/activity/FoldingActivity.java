package com.phone.moran.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.tools.AppUtils;
import com.phone.moran.view.FoldingLayout1;
import com.phone.moran.view.TouchFoldingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoldingActivity extends BaseActivity {

    @BindView(R.id.week_daka)
    TextView weekDaka;
    @BindView(R.id.date_daka)
    TextView dateDaka;
    @BindView(R.id.content_daka)
    TextView contentDaka;
    @BindView(R.id.thumb_btn)
    LinearLayout thumbBtn;
    @BindView(R.id.share_btn)
    LinearLayout shareBtn;
    @BindView(R.id.foldingView)
    FoldingLayout1 foldingView;
    @BindView(R.id.main_LL)
    TouchFoldingLayout mainLL;
    @BindView(R.id.main_text)
    TextView mainText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        mainLL.setFoldingLayout1(foldingView);
        foldingView.setFoldListener(new FoldingLayout1.OnFoldListener() {
            @Override
            public void onStartFold() {
            }

            @Override
            public void onEndFold() {
            }

            @Override
            public void onGoneFold() {

            }

            @Override
            public void currentPixel(float pix) {
//                SLogger.d("<<", "top --->> " + foldingView.getTop() + "-->>measureH-->" + foldingView.getMeasuredHeight() + "transY--->>>>" + (mainText.getTop() + mainText.getTranslationY()));

                if (foldingView.getState() == FoldingLayout1.STATE.FOLDING && foldingView.getTop() < (mainText.getTop() + mainText.getTranslationY())) {
//                    mainText.setTranslationY(mainText.getTranslationY() - pix);
                    mainText.setTranslationY(-pix);
                }
//                else if (foldingView.getTop() >= (mainText.getTop() + mainText.getTranslationY())){
//                    foldingView.setState(FoldingLayout1.STATE.GONE);
//                } else if (mainText.getTranslationY() > 0) {
//                    foldingView.setState(FoldingLayout1.STATE.NORMAL);
//                }

            }
        });

        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showToast(getApplicationContext(), "TEXT!!!!!");
            }
        });

        foldingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUtils.showToast(getApplicationContext(), "FOLDING!!!!!");
            }
        });
    }


    private int mTranslation = -1;

}
