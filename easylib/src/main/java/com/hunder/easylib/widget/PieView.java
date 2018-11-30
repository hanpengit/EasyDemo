package com.hunder.easylib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.hunder.easylib.entity.PieData;

import java.util.List;

/**
 * 自定义饼状图View
 * Created by hp on 2018/11/28.
 */

public class PieView extends View {

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFFFF8C69, 0xFF808080, 0xFFE6B800, 0xFF7CFC00};
    // 画笔
    private Paint mPaint;
    // 宽高
    private int mWidth, mHeight;
    // 饼状图初始绘制角度
    private float mStartAngle = 0;
    // 数据
    private List<PieData> mData;

    public PieView(Context context) {
        this(context, null);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null) {
            return;
        }

        float currentStartAngle = mStartAngle;                    // 当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2);                // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);  // 饼状图半径
        RectF rect = new RectF(-r, -r, r, r);                     // 饼状图绘制区域

        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.color);
            canvas.drawArc(rect, currentStartAngle, pie.angle, true, mPaint);
            currentStartAngle += pie.angle;
        }
    }

    // 设置起始角度
    public void setStartAngle(int startAngle) {
        mStartAngle = startAngle;
        invalidate();  //刷新
    }

    // 设置数据
    public void setData(List<PieData> data) {
        mData = data;
        initData();
        invalidate();
    }

    // 初始化数据
    private void initData() {
        if (mData == null || mData.isEmpty()) {
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            sumValue += pie.value;  //计算数值和

            int j = i % mColors.length;  //设置颜色
            pie.color = mColors[j];
        }

        float sumAngle = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);

            float percentage = pie.value / sumValue;   // 百分比
            float angle = percentage * 360;            // 对应的角度

            pie.percentage = percentage;               // 记录百分比
            pie.angle = angle;                         // 记录角度大小
            sumAngle += angle;
        }
    }

}
