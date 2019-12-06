package com.hunder.easylib.network_listening;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;

import com.hunder.easylib.EasyConstants;
import com.hunder.easylib.network_listening.core.NetworkCallbackImpl;

/**
 * Created by hp on 2019/12/3.
 */

public class NetworkManager {

    private static volatile NetworkManager instance;
    private NetStateReceiver mReceiver;
    private Application mApplication;

    private NetworkManager() {
        mReceiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }

        return instance;
    }

    public Application getApplication() {
        return mApplication;
    }

    public void init(Application application) {
        mApplication = application;

        // 动态广播注册
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EasyConstants.NET_CHANGE_ACTION);
        mApplication.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * 第二种实现方式，不通过广播
     */
    @SuppressLint("MissingPermission")
    public void init1(Application application) {
        mApplication = application;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ConnectivityManager.NetworkCallback networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            ConnectivityManager connMgr = (ConnectivityManager) mApplication.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connMgr != null) {
                connMgr.registerNetworkCallback(request, networkCallback);
            }
        } else {

        }
    }

    /**
     * 注册
     *
     * @param register
     */
    public void registerObserver(Object register) {
        mReceiver.registerObserver(register);
    }

    /**
     * 移除
     *
     * @param register
     */
    public void unRegisterObserver(Object register) {
        mReceiver.unRegisterObserver(register);
    }

}
