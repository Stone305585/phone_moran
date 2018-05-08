package com.phone.moran.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by zhaohe on 2017/11/22.
 */

public class MoveImageView extends android.support.v7.widget.AppCompatImageView {

    int lastX;
    int lastY;
    int offsetX;
    int offsetY;
    private EditText tipTv;
    private TextView resetTv;
    private Context context;
    private int rawW;
    private int rawH;

    private boolean open;//当前可以移动


    public MoveImageView(Context context) {
        this(context, null);
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MoveImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        rawH = getMeasuredHeight();
        rawW = getMeasuredWidth();
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!open) {
            return true;
        }
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(tipTv != null) {
                    tipTv.setVisibility(GONE);
                    resetTv.setVisibility(GONE);
                }
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:


                offsetX = x - lastX;
                offsetY = y - lastY;

                layout(getLeft()+offsetX,
                        getTop()+offsetY,
                        getRight()+offsetX,
                        getBottom()+offsetY);

                break;
            case MotionEvent.ACTION_UP:

                if(tipTv != null) {
                    tipTv.setVisibility(VISIBLE);
                    resetTv.setVisibility(VISIBLE);
                }


                if(locationListener != null) {
                    locationListener.location();
                }

                /*int x1 = getLeft();
                int y1 = getTop();
                FrameLayout.LayoutParams ll = new FrameLayout.LayoutParams(rawW, rawH);
                ll.leftMargin = x1;
                ll.topMargin = y1;
                setLayoutParams(ll);*/
                break;
        }
        return true;
    }

    public int getLastX() {
        return lastX;
    }

    public void setLastX(int lastX) {
        this.lastX = lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setLastY(int lastY) {
        this.lastY = lastY;
    }


    public EditText getTipTv() {
        return tipTv;
    }

    public void setTipTv(EditText tipTv) {
        this.tipTv = tipTv;
    }

    public TextView getResetTv() {
        return resetTv;
    }

    public void setResetTv(TextView resetTv) {
        this.resetTv = resetTv;
    }

    public interface LocationListener{
        void location();
    }

    private LocationListener locationListener;

    public LocationListener getLocationListener() {
        return locationListener;
    }

    public void setLocationListener(LocationListener locationListener) {
        this.locationListener = locationListener;
    }
}
