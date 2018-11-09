package com.hunder.easylib.utils;

import android.content.Context;

/**
 * 屏幕px,dp(dip),sp转换工具
 * Created by hp on 2018/11/8.
 */

public class DensityUtils {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param context 上下文
     * @param dpValue dp单位值
     * @return 转换后的px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     *
     * @param context 上下文
     * @param pxValue px单位值
     * @return 转换后的dp值
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getApplicationContext().getResources()
                .getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp 转成为 px
     *
     * @param context 上下文
     * @param sp      sp值
     * @return 转换后的px值
     */
    public static int sp2px(Context context, int sp) {
        // metric 度量
        // density 密度
        float density = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * density + 0.5f);
    }

    public static int px2sp(Context context, float px) {
        float var2 = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / var2 + 0.5F);
    }

}
