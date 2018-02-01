package com.phone.moran.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.phone.moran.R;
import com.phone.moran.view.MyRotateYAnim;

/**
 * Created by ASUS on 2016/7/20.
 */
public class AnimProgressDialog extends Dialog {

    private View animView;
    MyRotateYAnim rotateYAnim = new MyRotateYAnim();
    private Context context;

    public AnimProgressDialog(Context context) {
        this(context, R.style.NoBgDialog);
    }

    public AnimProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    public void show() {
        super.show();
//        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.loading_anim);;
        rotateYAnim.setRepeatCount(Animation.INFINITE);
        animView.startAnimation(rotateYAnim);

    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (animView != null) {
            rotateYAnim.cancel();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View dialogView = View.inflate(context, R.layout.loading_dialog, null);
        animView = dialogView;

        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llp.setMargins(50,50,50,50);
        setContentView(dialogView, llp);
    }
}
