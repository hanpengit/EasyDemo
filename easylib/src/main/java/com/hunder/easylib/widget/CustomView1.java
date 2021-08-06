package com.hunder.easylib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hunder.easylib.utils.LogUtils;

/**
 * 自定义view：跟随手指移动
 */
public class CustomView1 extends View {

    private int mLastX;
    private int mLastY;
    private boolean mMove = false; //手指是否移动标记

    public CustomView1(Context context) {
        super(context);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mMove = false;
                break;

            case MotionEvent.ACTION_MOVE:
                int deltaX = x - mLastX;
                int deltaY = y - mLastY;

                LogUtils.d("getX():" + getX() + ";getY():" + getY());
                LogUtils.d("event.getX():" + event.getX() + ";event.getY():" + event.getY());
                LogUtils.d("event.getRawX():" + x + ";event.getRawY():" + y);
                LogUtils.d("deltaX:" + deltaX + ";deltaY:" + deltaY);
                LogUtils.d("mLastX:" + mLastX + ";mLastY:" + mLastY);
                LogUtils.d("getTranslationX():" + getTranslationX() + ";getTranslationY():" + getTranslationY());

                int translationX = (int) getTranslationX() + deltaX;
                int translationY = (int) getTranslationY() + deltaY;

                setTranslationX(translationX);
                setTranslationY(translationY);

                if (deltaX != 0 || deltaY != 0) {
                    mMove = true;
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        mLastX = x;
        mLastY = y;

        // 兼容onClick事件
        if (mMove) {
            return true;
        }
        return super.onTouchEvent(event);
    }

}
