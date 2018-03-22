package com.phone.moran.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.widget.ScrollView;

import com.phone.moran.tools.SLogger;

/**
 * Created by ASUS on 2017/11/5.
 */

public class TouchFoldingLayout extends ScrollView {

    private GestureDetector mScrollGestureDetector;
    private FoldingLayout1 foldingLayout1;
    private ScrollerViewPager scrollerViewPager;
    private Context context;
    private PointF curPoint = new PointF();
    private PointF downPoint = new PointF();
    private int slop;

    public TouchFoldingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        slop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    public FoldingLayout1 getFoldingLayout1() {
        return foldingLayout1;
    }

    public ScrollerViewPager getScrollerViewPager() {
        return scrollerViewPager;
    }

    public void setScrollerViewPager(ScrollerViewPager scrollerViewPager) {
        this.scrollerViewPager = scrollerViewPager;
    }

    public void setFoldingLayout1(FoldingLayout1 foldingLayout1) {
        this.foldingLayout1 = foldingLayout1;
        mScrollGestureDetector = new GestureDetector(context,
                new ScrollGestureDetector());
    }

    private float scrollY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        if (foldingLayout1.getTop() <= 0) {
//            SLogger.d("<<", "*********top-->>" + foldingLayout1.getTop() + "-->>yyy>>>>" + foldingLayout1.getY());
//
//            return super.onTouchEvent(event);
//        } else {
//            SLogger.d("<<", "top-->>" + foldingLayout1.getTop() + "-->>yyy>>>>" + foldingLayout1.getY() + "--eventY-->" + event.getY());
//
//            // 每次进行onTouch事件都记录当前的按下的坐标
//
//            curPoint.x = event.getX();
//            curPoint.y = event.getY();
//
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    // 记录按下时候的坐标
//                    downPoint.x = event.getX();
//                    downPoint.y = event.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    // if (this.getChildCount() >1) {
//
//                    float x = Math.abs(curPoint.x - downPoint.x);
//                    float y = Math.abs(curPoint.y - downPoint.y);
//
//                    if ((y > 1 || x > 1) && y / x > 1) {
//                        if (curPoint.y >= downPoint.y && foldingLayout1.getState() == FoldingLayout1.STATE.NORMAL) {
//                            SLogger.d("<<", "--.>>>11");
//                            return super.onTouchEvent(event);
//                        } else if (curPoint.y < downPoint.y && foldingLayout1.getState() == FoldingLayout1.STATE.GONE) {
//                            SLogger.d("<<", "--.>>>222");
//                            return super.onTouchEvent(event);
//                        } else {
//                            SLogger.d("<<", "--.>>>3333");
//
//                            mScrollGestureDetector.onTouchEvent(event);
//                            return true;
//                        }
//                    } else {
//                        super.onTouchEvent(event);
//                    }
//
//
//                    // }
//                    break;
//                case MotionEvent.ACTION_UP:
//                    // 再up时判断是否按下和松手的坐标为一个点
//                    float absX = Math.abs(downPoint.x - curPoint.x);
//                    float absY = Math.abs(downPoint.y - curPoint.y);
//                    if (0 <= absX && absX <= slop && 0 <= absY && absY <= slop) {
//                        return super.onTouchEvent(event);
//                    }
//                    break;
//            }
//
//            return super.onTouchEvent(event);
//
//        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            scrollY = Math.abs(curPoint.y - downPoint.y);
            SLogger.d("fffo", "scrollY---->" + scrollY);

            curY = -1;
            downPoint.y = 0;

        }

        if (foldingLayout1.getState() != FoldingLayout1.STATE.GONE) {

            return mScrollGestureDetector.onTouchEvent(event);
        } else {
            curPoint.y = event.getY();
//
//            SLogger.d("fffo", "11111111111cY-->>" + curPoint.y);
//            SLogger.d("fffo", "11111111111dY-->>" + downPoint.y);

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    downPoint.y = event.getY();

//                    return true;
                    break;

                case MotionEvent.ACTION_MOVE:
                    if (downPoint.y == 0) {
                        downPoint.y = curPoint.y;
//                        SLogger.d("fffo", "22222222cY-->>" + curPoint.y);
//                        SLogger.d("fffo", "22222222dY-->>" + downPoint.y);
                    }

                    float y = curPoint.y - downPoint.y;

//                    SLogger.d("fffo", "cY-->>" + curPoint.y);
//                    SLogger.d("fffo", "dY-->>" + downPoint.y);

//                    SLogger.d("fffo", "-------------------->>" + scrollY);

//                    if (scrollY == 0)
//                        scrollY = (downPoint.y - curPoint.y);
//                    setScrollY((int)Math.abs((scrollY - (curPoint.y - downPoint.y))));

                    //向下滑动并且scrollview没滑动x
                    if (y > 0 && getScrollY() == 0) {
//                        SLogger.d("fffo", "-->>" + y);
                        return mScrollGestureDetector.onTouchEvent(event);
                    }
//                    return true;
                    break;
//
//                case MotionEvent.ACTION_UP:
//                    downPoint.y = 0;
//                    break;
            }
            return super.onTouchEvent(event);
        }
    }

    private int mTranslation = -1;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mTranslation == -1)
            mTranslation = 0;
        super.dispatchDraw(canvas);
    }

    class ScrollGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {


            SLogger.d("fffo", "curY--->" + curY);
            SLogger.d("fffo", "mTranslation--->" + mTranslation);
            SLogger.d("fffo", "distanceY--->" + distanceY);
            if (foldingLayout1 != null) {
                if (curY == -1) {
                    distanceY = 0;
                    curY = 0;
                }
                mTranslation += distanceY;


                if (mTranslation < 0) {
                    mTranslation = 0;
                }
                if (mTranslation > foldingLayout1.getHeight()) {
                    mTranslation = foldingLayout1.getHeight();
                }

                if (foldingLayout1.mFoldListener != null) {
                    foldingLayout1.mFoldListener.currentPixel(mTranslation);
                }

                float factor = Math.abs(((float) mTranslation)
                        / ((float) foldingLayout1.getHeight()));

                foldingLayout1.setFoldFactor(factor);
            }

            return true;
        }
    }

    private float curY = -1;

}
