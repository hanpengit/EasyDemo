package com.hunder.easylib.network_listening.listener;

import com.hunder.easylib.network_listening.type.NetType;

/**
 * Created by hp on 2019/12/3.
 */

public interface NetChangeListener {

    void onConnect(NetType type);
    void onDisConnect();
}
