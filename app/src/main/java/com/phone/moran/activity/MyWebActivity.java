package com.phone.moran.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phone.moran.R;
import com.phone.moran.config.Constant;
import com.phone.moran.model.ClassicQuote;
import com.phone.moran.tools.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWebActivity extends BaseActivity implements View.OnClickListener{


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
    @BindView(R.id.webview)
    WebView webView;
    @BindView(R.id.swipeRefresh_webview)
    SwipeRefreshLayout srl;
    @BindView(R.id.image_cq)
    ImageView cqImage;

    private ClassicQuote cq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web);
        ButterKnife.bind(this);

        dialog.show();
        Intent intent = getIntent();
        cq = (ClassicQuote)intent.getSerializableExtra(Constant.CQ_ID);

        initWebView();
        setListener();
    }

    private void initWebView() {
        title.setText(cq.getCq_title());
        ImageLoader.displayImg(this, cq.getCq_h5_url(), cqImage);

        dialog.dismiss();

        /*WebSettings webSettings = webView.getSettings();

        srl.setColorSchemeColors(ContextCompat.getColor(this, R.color.purple),
                ContextCompat.getColor(this, R.color.yellow),
                ContextCompat.getColor(this, R.color.blue),
                ContextCompat.getColor(this, R.color.bg_grey2));
        srl.setProgressViewOffset(false, 0, DensityUtils.dip2px(30));
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webView.loadUrl(cq.getCq_h5_url());

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    srl.setRefreshing(false);
                    dialog.dismiss();
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });*/

        /*webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });*/

//        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }

    @Override
    public void initView() {


    }

    @Override
    public void setListener() {
        backTitle.setOnClickListener(this);
//        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                webView.reload();
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_title:
                finish();
                break;
        }
    }


}

