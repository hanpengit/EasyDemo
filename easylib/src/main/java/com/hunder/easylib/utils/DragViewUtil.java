package com.hunder.easylib.utils;

import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * 这个工具可以使任何一个view进行拖动。
 */

public class DragViewUtil {
    private static int mScreenWidth = ScreenUtils.getScreenWidth(CommonUtils.context);
    private static int mScreenHeight = ScreenUtils.getScreenHeight(CommonUtils.context);
    private static int mBottomHeight = ScreenUtils.getStatusBarHeight(CommonUtils.context); //底部+头部+状态栏(按需选择)
    private static int mViewSize = DensityUtils.dp2px(CommonUtils.context, 80);
    public static boolean mClickable = true;

    public static void drag(View v) {
        drag(v, 0);
    }

    /**
     * 拖动View方法
     *
     * @param v     view
     * @param delay 延迟
     */
    public static void drag(View v, long delay) {
        v.setOnTouchListener(new TouchListener(delay));
    }

    private static class TouchListener implements View.OnTouchListener {
        private float downX;
        private float downY;
        private int left;
        private int top;
        private long downTime;
        private long delay;

        private TouchListener() {
        }

        private TouchListener(long delay) {
            this.delay = delay;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mClickable = true;
                    downX = event.getX();
                    downY = event.getY();
                    downTime = System.currentTimeMillis();

                    LogUtils.d("DragView:" + "ACTION_DOWN:downX:" + downX);
                    LogUtils.d("DragView:" + "ACTION_DOWN:downY:" + downY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (System.currentTimeMillis() - downTime >= delay) {
                        final float xDistance = event.getX() - downX;
                        final float yDistance = event.getY() - downY;

                        LogUtils.d("DragView:" + "ACTION_MOVE:event.getX():" + event.getX());
                        LogUtils.d("DragView:" + "ACTION_MOVE:event.getY():" + event.getY());
                        if (Math.abs(xDistance) > 5 || Math.abs(yDistance) > 5) {
                            mClickable = false;
                        }

                        if (xDistance != 0 && yDistance != 0) {
                            int l = (int) (v.getLeft() + xDistance);
                            int r = (int) (v.getRight() + xDistance);
                            int t = (int) (v.getTop() + yDistance);
                            int b = (int) (v.getBottom() + yDistance);

                            if (l < 0) {
                                l = 0;
                                r = l + v.getWidth();
                            }
                            if (r > mScreenWidth) {
                                r = mScreenWidth;
                                l = r - v.getWidth();
                            }
                            if (t < 0) {
                                t = 0;
                                b = t + v.getHeight();
                            }
                            if (b > mScreenHeight - mBottomHeight) {
                                b = mScreenHeight - mBottomHeight;
                                t = b - v.getHeight();
                            }

                            left = l;
                            top = t;

                            LogUtils.d("DragView:" + "ACTION_MOVE:l:" + l);
                            LogUtils.d("DragView:" + "ACTION_MOVE:r:" + r);
                            LogUtils.d("DragView:" + "ACTION_MOVE:t:" + t);
                            LogUtils.d("DragView:" + "ACTION_MOVE:b:" + b);

                            v.layout(l, t, r, b);
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(mViewSize, mViewSize);
                    lp.setMargins(v.getLeft(), v.getTop(), 0, 0);
                    v.setLayoutParams(lp);
                    break;
            }
            return false;
        }

    }


}