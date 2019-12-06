package com.hunder.easydemo.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.network_listening.NetworkManager;
import com.hunder.easylib.network_listening.annotation.Network;
import com.hunder.easylib.network_listening.type.NetType;
import com.hunder.easylib.utils.LogUtils;

/**
 * Created by hp on 2019/12/4.
 */

public class NetworkListeningActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NetworkListeningActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_listening;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkManager.getDefault().registerObserver(this);
    }

    @Network
    public void network(NetType netType) {
        if (netType == NetType.TYPE_DISCONNECTED) {
            LogUtils.d("网络未连接...");
        } else {
            LogUtils.d("网络已连接...");
        }
        LogUtils.d("network | netType >>> " + netType.name());

        switch (netType) {
            case TYPE_DISCONNECTED:

                break;

            case TYPE_MOBILE:

                break;

            case TYPE_WIFI:

                break;
        }
    }

    @Network(netType = NetType.TYPE_DISCONNECTED)
    public void networkDisconnected(NetType netType) {
        LogUtils.d("networkDisconnected | netType >>> " + netType);
    }

    @Network(netType = NetType.TYPE_CONNECTED)
    public void networkConnected(NetType netType) {
        LogUtils.d("networkConnected | netType >>> " + netType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
    }

}
