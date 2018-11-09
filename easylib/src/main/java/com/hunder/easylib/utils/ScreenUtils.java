package com.hunder.easylib.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;


/**
 * Created by hp on 2016/11/10.
 */

public class ScreenUtils {

    /**
     * 状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity
     * @param colorResId
     */
    public static void setStatusBarColor(Activity activity, int colorResId) {

    }

    /**
     * 设置全屏且状态栏透明
     *
     * @param activity
     */
    public static void setFullscreen(Activity activity) {
        if (AppUtils.isBelowLollipop()) {
            ScreenUtils.setStatusBarColor(activity, android.R.color.transparent);
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    /**
     * 屏幕宽
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 屏幕高
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }

}
