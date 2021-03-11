package com.hunder.easylib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


public class ColorSelectorView extends View {
    private static final String TAG = "ColorSelectorView";
    private OnClickColorListener colorSelectResultListen;
    public int defaultIndex = -1;
    private String mBgColor = "#00000000";
    private String mBorderColor = "#4DFFFFFF";
    private float mBorderWidth = 1.0F;
    private RectF mColorSelectorRect;
    private Context mContext;
    private float mCornersRadius = 2.0F;
    private int mItemHeight = 19;
    private int mItemWidth = 8;
    private float mPadding = 5.0F;
    private Paint mPaint;
    private Path mPath;
    private int mPopLeft = 0;
    private int mPopTop = 0;
    private int mPopWidth = 16;
    private RectF mRect;
    private String mSelectBorderColor = "#FFFFFF";
    private float mSelectBorderWidth = 2.0F;
    public int mSelectIndex = 0;
    private int mViewHeight;
    private int mViewWidth;
    private boolean showPop;

    public ColorSelectorView(Context paramContext) {
        this(paramContext, null);
    }

    public ColorSelectorView(Context paramContext, @Nullable AttributeSet paramAttributeSet) {
        this(paramContext, paramAttributeSet, 0);
    }

    public ColorSelectorView(Context paramContext, @Nullable AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        this.mContext = paramContext;
        init();
    }

    private float getItemBottom(int paramInt) {
        return this.mItemHeight + this.mBorderWidth + this.mPopTop;
    }

    private float getItemLeft(int paramInt) {
        return paramInt * this.mItemWidth + this.mBorderWidth + this.mItemWidth + this.mItemHeight + this.mPadding + this.mPopLeft;
    }

    private float getItemRight(int paramInt) {
        return (paramInt + 1) * this.mItemWidth + this.mBorderWidth + this.mItemWidth + this.mItemHeight + this.mPadding + this.mPopLeft;
    }

    private float getItemTop(int paramInt) {
        return this.mBorderWidth + this.mPopTop;
    }

    private void init() {
        setBackgroundColor(Color.parseColor(this.mBgColor));
        this.mPaint = new Paint();
        this.mPaint.setAntiAlias(true);
        this.mPath = new Path();
        this.mColorSelectorRect = new RectF();
        this.mRect = new RectF();
    }

    public void changeColorSelect() {
        String str="";
        if (this.defaultIndex < 0) {
            /*RoomUser localRoomUser = TKRoomManager.getInstance().getMySelf();
            if ((localRoomUser != null) && (localRoomUser.properties.containsKey("primaryColor")))
                str = (String) localRoomUser.properties.get("primaryColor");*/
        }
        for (int i = 0; ; i++) {
            if (i < Config.mColor.length) {
                if (!str.equals(Config.mColor[i]))
                    continue;
                this.defaultIndex = i;
                this.mSelectIndex = i;
            }
            this.colorSelectResultListen.setColor(Color.parseColor(Config.mColor[this.mSelectIndex]));
            //TKRoomManager.getInstance().changeUserProperty(TKRoomManager.getInstance().getMySelf().peerId, "__all", "primaryColor", Config.mColor[this.mSelectIndex]);
            return;
        }
    }

    public void cleanDefaultColor() {
        this.defaultIndex = -1;
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(Color.parseColor(this.mSelectBorderColor));
        this.mPaint.setStrokeWidth(this.mBorderWidth);
        this.mRect.set(this.mPadding + this.mPopLeft, this.mBorderWidth / 2.0F + this.mPopTop, this.mItemHeight + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mPadding + this.mPopLeft, this.mItemHeight + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mPopTop);
        paramCanvas.drawRoundRect(this.mRect, this.mCornersRadius, this.mCornersRadius, this.mPaint);
        if (this.showPop) {
            this.mRect.set(getItemLeft(this.mSelectIndex) - this.mPopWidth / 4, getItemTop(this.mSelectIndex) - this.mPopWidth - 10.0F, getItemRight(this.mSelectIndex) + this.mPopWidth / 4, getItemTop(this.mSelectIndex) - 10.0F);
            paramCanvas.drawRoundRect(this.mRect, this.mCornersRadius, this.mCornersRadius, this.mPaint);
        }
        this.mPaint.setColor(Color.parseColor(this.mBorderColor));
        this.mColorSelectorRect.set(this.mBorderWidth / 2.0F + this.mItemWidth + this.mItemHeight + this.mPadding + this.mPopLeft, this.mBorderWidth / 2.0F + this.mPopTop, this.mItemWidth * Config.mColor.length + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mItemWidth + this.mItemHeight + this.mPadding + this.mPopLeft, this.mItemHeight + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mPopTop);
        paramCanvas.drawRect(this.mColorSelectorRect, this.mPaint);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setColor(Color.parseColor(Config.mColor[this.mSelectIndex]));
        this.mRect.set(this.mPadding + this.mPopLeft + this.mBorderWidth / 2.0F, this.mBorderWidth / 2.0F + this.mPopTop + this.mBorderWidth / 2.0F, this.mItemHeight + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mPadding + this.mPopLeft - this.mBorderWidth / 2.0F, this.mItemHeight + 2.0F * this.mBorderWidth - this.mBorderWidth / 2.0F + this.mPopTop - this.mBorderWidth / 2.0F);
        paramCanvas.drawRoundRect(this.mRect, this.mCornersRadius, this.mCornersRadius, this.mPaint);
        if (this.showPop) {
            this.mPaint.setColor(Color.parseColor(Config.mColor[this.mSelectIndex]));
            this.mRect.set(getItemLeft(this.mSelectIndex) - this.mPopWidth / 4 + this.mBorderWidth / 2.0F, getItemTop(this.mSelectIndex) - this.mPopWidth + this.mBorderWidth / 2.0F - 10.0F, getItemRight(this.mSelectIndex) + this.mPopWidth / 4 - this.mBorderWidth / 2.0F, getItemTop(this.mSelectIndex) - this.mBorderWidth / 2.0F - 10.0F);
            paramCanvas.drawRoundRect(this.mRect, this.mCornersRadius, this.mCornersRadius, this.mPaint);
            this.mPaint.setColor(Color.parseColor(this.mSelectBorderColor));
            this.mPath.reset();
            this.mPath.moveTo(getItemLeft(this.mSelectIndex) + this.mPopWidth / 8, getItemTop(this.mSelectIndex) - 10.0F);
            this.mPath.lineTo(getItemRight(this.mSelectIndex) - this.mPopWidth / 8, getItemTop(this.mSelectIndex) - 10.0F);
            this.mPath.lineTo(getItemLeft(this.mSelectIndex) + this.mPopWidth / 4, getItemTop(this.mSelectIndex) - 10.0F + getItemLeft(4) / 32.0F);
            this.mPath.lineTo(getItemLeft(this.mSelectIndex) + this.mPopWidth / 8, getItemTop(this.mSelectIndex) - 10.0F);
            this.mPath.close();
            paramCanvas.drawPath(this.mPath, this.mPaint);
        }
        for (int i = 0; i < Config.mColor.length; i++) {
            String str = Config.mColor[i];
            float f1 = getItemLeft(i);
            float f2 = getItemTop(i);
            float f3 = getItemRight(i);
            float f4 = getItemBottom(i);
            this.mRect.set(f1, f2, f3, f4);
            this.mPaint.setColor(Color.parseColor(str));
            paramCanvas.drawRect(this.mRect, this.mPaint);
        }
        this.mPaint.setStyle(Paint.Style.STROKE);
        if (this.showPop) {
            this.mPaint.setStrokeWidth(this.mSelectBorderWidth);
            this.mPaint.setColor(Color.parseColor(this.mSelectBorderColor));
            this.mRect.set(getItemLeft(this.mSelectIndex) - this.mSelectBorderWidth / 2.0F, getItemTop(this.mSelectIndex) - this.mSelectBorderWidth / 2.0F, getItemRight(this.mSelectIndex) + this.mSelectBorderWidth / 2.0F, getItemBottom(this.mSelectIndex) + this.mSelectBorderWidth / 2.0F);
            paramCanvas.drawRect(this.mRect, this.mPaint);
        }
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        this.mViewHeight = getMeasuredHeight();
        this.mViewWidth = getMeasuredWidth();
        float f = Math.min((this.mViewWidth - 2.0F * this.mPadding - 4.0F * this.mBorderWidth) / (19 + (8 + 8 * Config.mColor.length)), (this.mViewHeight - 2.0F * this.mPadding) / 39.0F);
        this.mItemWidth = (int) (8.0F * f);
        this.mItemHeight = (int) (19.0F * f);
        this.mPopTop = ((this.mViewHeight - 2 * this.mItemHeight) / 2 + this.mItemHeight);
        this.mPopLeft = (int) ((this.mViewWidth - f * (19 + (8 + 8 * Config.mColor.length))) / 2.0F + this.mBorderWidth);
        this.mPopWidth = (int) (16.0F * f);
    }

    public boolean onTouchEvent(MotionEvent paramMotionEvent) {
        switch (paramMotionEvent.getAction()) {
            default:
                return true;
            case MotionEvent.ACTION_DOWN:
                this.showPop = true;
                float f1 = paramMotionEvent.getX();
                float f2 = paramMotionEvent.getY();
                if (this.mColorSelectorRect.contains(f1, f2)) {
                    this.mSelectIndex = (int) ((f1 - this.mColorSelectorRect.left) / this.mItemWidth);
                    if (this.mSelectIndex > -1 + Config.mColor.length)
                        this.mSelectIndex = (-1 + Config.mColor.length);
                    /*while (true) {
                        postInvalidate();
                        return true;
                        if (this.mSelectIndex >= 0)
                            continue;
                        this.mSelectIndex = 0;
                    }*/

                    if (mSelectIndex < 0) {
                        mSelectIndex = 0;
                    }
                    ///postInvalidate();
                }
                //return false;
                break;
            case MotionEvent.ACTION_MOVE:
                this.showPop = true;
                this.mSelectIndex = (int) ((paramMotionEvent.getX() - this.mColorSelectorRect.left) / this.mItemWidth);
                if (this.mSelectIndex > -1 + Config.mColor.length)
                    this.mSelectIndex = (-1 + Config.mColor.length);
                /*while (true) {
                    postInvalidate();
                    return true;
                    if (this.mSelectIndex >= 0)
                        continue;
                    this.mSelectIndex = 0;
                }*/

                if (mSelectIndex < 0) {
                    mSelectIndex = 0;
                }
                ///postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.showPop = false;
                break;
        }
        performClick();
        ///this.showPop = false;
        this.colorSelectResultListen.setColor(Color.parseColor(Config.mColor[this.mSelectIndex]));
        ///TKRoomManager.getInstance().changeUserProperty(TKRoomManager.getInstance().getMySelf().peerId, "__all", "primaryColor", Config.mColor[this.mSelectIndex]);
        postInvalidate();
        return true;
    }

    public boolean performClick() {
        return super.performClick();
    }

    public void setColorSelectResultListen(OnClickColorListener paramOnClickColorListener) {
        this.colorSelectResultListen = paramOnClickColorListener;
    }

    public void setmSelectIndex(int paramInt) {
        this.mSelectIndex = paramInt;
        postInvalidate();
    }

    public static abstract interface OnClickColorListener {
        public abstract void setColor(int paramInt);
    }
}
