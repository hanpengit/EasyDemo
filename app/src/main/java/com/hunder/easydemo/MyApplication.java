package com.hunder.easydemo;

import android.app.Application;
import android.content.Context;

import com.hunder.easylib.network_listening.NetworkManager;
import com.hunder.easylib.utils.CommonUtils;

/**
 * Created by hp on 2018/7/18.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        CommonUtils.context = context = getApplicationContext();
        NetworkManager.getDefault().init1(this);
    }

    public Context getContext() {
        return context;
    }

}
