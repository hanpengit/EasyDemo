package com.hunder.easydemo;

import android.app.Application;
import android.content.Context;

import com.hunder.easylib.utils.CommonUtils;

/**
 * Created by hp on 2018/7/18.
 */

public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtils.context = mContext = getApplicationContext();
    }

    public Context getContext() {
        return mContext;
    }

}
