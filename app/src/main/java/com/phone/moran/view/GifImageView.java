package com.phone.moran.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.phone.moran.R;

/**
 * Created by ASUS on 2016/7/19.
 */
public class GifImageView extends ImageView {
    private Context mContext;

    public GifImageView(Context context) {
        super(context);
        mContext = context;
        initView();

    }

    public GifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();

    }

    public GifImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        Glide.with(mContext).load(R.drawable.loading_more).asGif().into(this);
    }

}
