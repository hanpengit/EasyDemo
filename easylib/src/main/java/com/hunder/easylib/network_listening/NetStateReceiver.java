package com.hunder.easylib.network_listening;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hunder.easylib.EasyConstants;
import com.hunder.easylib.network_listening.annotation.Network;
import com.hunder.easylib.network_listening.entity.MethodManager;
import com.hunder.easylib.network_listening.type.NetType;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.NetworkUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by hp on 2019/12/3.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private NetType mNetType = NetType.TYPE_DISCONNECTED;
    private Map<Object, List<MethodManager>> mNetworkList = new HashMap<>();

    public NetStateReceiver() {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            LogUtils.d("异常...");
        }

        // 处理广播事件
        if (intent.getAction().equalsIgnoreCase(EasyConstants.NET_CHANGE_ACTION)) {
            LogUtils.d("网络发送改变");
            mNetType = NetworkUtils.getNetType(context);
            if (NetworkUtils.isNetConnected(context)) {
                LogUtils.d("网络连接成功");
            } else {
                LogUtils.d("网络连接失败");
            }

            // 总线：全局通知
            post(mNetType);
        }
    }

    // 同时分发过程
    private void post(NetType netType) {
        Set<Object> set = mNetworkList.keySet();
        // 获取activity/fragment对象
        for (Object register : set) {
            // 所有注解方法
            List<MethodManager> methodList = mNetworkList.get(register);
            if (methodList != null) {
                // 循环每个方法
                for (MethodManager methodManager : methodList) {
                    // 两者参数比较
                    if (methodManager.getType().isAssignableFrom(netType.getClass())) {
                        switch (methodManager.getNetType()) {
                            case TYPE_DISCONNECTED:
                                if (netType == NetType.TYPE_DISCONNECTED) {
                                    invoke(methodManager, register, netType);
                                }
                                break;

                            case TYPE_CONNECTED:
                                if (netType == NetType.TYPE_MOBILE || netType == NetType.TYPE_WIFI) {
                                    invoke(methodManager, register, netType);
                                }
                                break;

                            case TYPE_ALL:
                            default:
                                invoke(methodManager, register, netType);
                                break;
                        }
                    }
                }
            }
        }
    }

    private void invoke(MethodManager methodManager, Object register, NetType netType) {
        try {
            methodManager.getMethod().invoke(register, netType);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将应用中所有Activity/Fragment注册了网络监听的方法，添加到集合
     * key:MainActivity  value:MainActivity注解的方法
     *
     * @param register
     */
    public void registerObserver(Object register) {
        // MainActivity所有网络监听注解方法
        List<MethodManager> methodList = mNetworkList.get(register);
        if (methodList == null) {
            // 开始添加方法
            methodList = findAnnotationMethod(register);
            mNetworkList.put(register, methodList);

            LogUtils.d(register.getClass().getName() + "注册成功");
        }
    }

    /**
     * 通过反射技术，从注解中找到方法，添加到集合
     *
     * @param register
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object register) {
        List<MethodManager> methodList = new ArrayList<>();

        Class<?> clazz = register.getClass();
        // 获取所有方法
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            // 获取方法的注解
            Network network = method.getAnnotation(Network.class);
            if (network == null) {
                continue;
            }

            // 方法的参数校验 (校验并不完整...!!!)
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (parameterTypes.length != 1) {
                throw new RuntimeException(method.getName() + "必须有且只有一个参数");
            }

            // 过滤方法完成，添加到集合
            MethodManager methodManager = new MethodManager(parameterTypes[0], network.netType(), method);
            methodList.add(methodManager);
        }

        return methodList;
    }

    public void unRegisterObserver(Object register) {
        if (!mNetworkList.isEmpty()) {
            mNetworkList.remove(register);

            LogUtils.d(register.getClass().getName() + "注销成功");
        }
    }

    /**
     * 应用退出时，注销全部
     */
    public void unRegisterAll() {
        if (!mNetworkList.isEmpty()) {
            mNetworkList.clear();
        }

        NetworkManager.getDefault().getApplication().unregisterReceiver(this);
        mNetworkList = null;

        LogUtils.d("注销全部成功");
    }

}
