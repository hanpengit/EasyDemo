package com.hunder.easylib.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

/**
 * 自定义view：弹性滑动
 *
 * 这个用不成，还是要继承自ViewGroup，这样才能让内容移动。
 */
public class SmoothScrollView1 extends View {

    private Scroller mScroller;

    public SmoothScrollView1(Context context) {
        super(context);
        init(context);
    }

    public SmoothScrollView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SmoothScrollView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);
    }

    public void smoothScrollTo(int destX, int destY) {
        // 获取滑动起点坐标
        int scrollX = getScrollX();
        int scrollY = getScrollY();

        int deltaX = destX - scrollX;
        int deltaY = destY - scrollY;

        // 设置滑动参数
        // 1000ms内滑向指定位置，效果就是慢慢滑动
        mScroller.startScroll(scrollX, scrollY, deltaX, deltaY, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        // 递归调用
        // 递归终止条件:滑动结束
        if (mScroller.computeScrollOffset()) {
            // mScroller.getCurrX(),mScroller.getCurrY()记录的是此刻要滑动达到的目标坐标
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }

}
