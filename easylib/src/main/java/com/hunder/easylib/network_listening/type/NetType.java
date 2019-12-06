package com.hunder.easylib.network_listening.type;

/**
 * 网络类型
 * Created by hp on 2019/12/3.
 */

public enum NetType {

    // 获取当前网络状态：未连接，移动网络，WIFI网络
    TYPE_DISCONNECTED,
    TYPE_MOBILE,
    TYPE_WIFI,

    // 用于注解中：未连接，已连接，全部网络状态
    TYPE_CONNECTED,
    TYPE_ALL
}
