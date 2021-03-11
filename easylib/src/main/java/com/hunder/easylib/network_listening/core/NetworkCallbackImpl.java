package com.hunder.easylib.network_listening.core;

import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.hunder.easylib.network_listening.NetworkManager;
import com.hunder.easylib.network_listening.type.NetType;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.NetworkUtils;

/**
 * 实现网络监听除广播外的另一种方式
 * Created by hp on 2019/12/6.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NetworkCallbackImpl extends ConnectivityManager.NetworkCallback {

    @Override
    public void onAvailable(Network network) {
        super.onAvailable(network);
        LogUtils.d("NetworkCallback: onAvailable");
        NetType netType = NetworkUtils.getNetType(NetworkManager.getDefault().getApplication());
        LogUtils.d("netType: " + netType.ordinal());
    }

    @Override
    public void onLost(Network network) {
        super.onLost(network);
        LogUtils.d("NetworkCallback: onLost");
    }

    @Override
    public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities);
        if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                LogUtils.d("onCapabilitiesChanged: 网络类型为wifi");
            } else {
                LogUtils.d("onCapabilitiesChanged: 其他网络");
            }
        }
    }

}
