package com.hunder.easylib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class VolumeView extends View {
    private int color;
    private int mBottom;
    private Paint mPaint;
    private int mPrecess = 1;
    private int mRectHeight = 100;
    private int mRectWidth = 5;
    private int mTop = 0;
    private int mTotal = 4;
    private RectF rectF;
    private int space = 6;

    public VolumeView(Context paramContext) {
        super(paramContext);
        init();
    }

    public VolumeView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        init();
    }

    public VolumeView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
        init();
    }

    private void drawRect(int paramInt, Canvas paramCanvas) {
        if (paramInt <= this.mPrecess) ;
        for (int i = Color.parseColor("#FFDC54"); ; i = Color.parseColor("#ffffff")) {
            this.color = i;
            this.mPaint.setColor(this.color);
            this.rectF.left = getLeft(paramInt);
            this.rectF.top = this.mTop;
            this.rectF.right = (this.rectF.left + this.mRectWidth);
            this.rectF.bottom = this.mBottom;
            paramCanvas.drawRoundRect(this.rectF, 2.0F, 2.0F, this.mPaint);
            return;
        }
    }

    private int getLeft(int paramInt) {
        return (paramInt - 1) * (this.mRectWidth + this.space);
    }

    private void init() {
        this.mPaint = new Paint(1);
        Paint localPaint = this.mPaint;
        localPaint.setStyle(Paint.Style.FILL);
        this.rectF = new RectF();
    }

    protected void onDraw(Canvas paramCanvas) {
        super.onDraw(paramCanvas);
        for (int i = 1; i <= this.mTotal; i++)
            drawRect(i, paramCanvas);
    }

    protected void onMeasure(int paramInt1, int paramInt2) {
        super.onMeasure(paramInt1, paramInt2);
        int i = getMeasuredWidth() / 8;
        this.space = i;
        this.mRectWidth = i;
        this.mRectHeight = (int) (3.5D * this.space);
        this.mTop = ((getMeasuredHeight() - this.mRectHeight) / 2);
        this.mBottom = (this.mTop + this.mRectHeight);
    }

    public void setIndex(int paramInt) {
        if (this.mPrecess == paramInt)
            return;
        this.mPrecess = paramInt;
        invalidate();
    }
}
