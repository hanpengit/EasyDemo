package com.hunder.easylib.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.hunder.easylib.network_listening.type.NetType;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 判断网络连接类
 */
public class NetworkUtils {

    public static final int NETWORK_WIFI_CONNECTION = 1;
    public static final int NETWORK_MOBILE_CONNECTION = 2;
    public static final int NETWORK_DISCONNECTION = 0;
    private static final String TAG = "NetConnectedUtils";


    public static boolean isNetConnected(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        }
        return false;
    }

    public static NetType getNetType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    return NetType.TYPE_WIFI;
                }
                return NetType.TYPE_MOBILE;
            }
        }
        return NetType.TYPE_DISCONNECTED;
    }

    public static boolean isWifi(Context context) {
        int networkType = getNetworkType(context);
        if (networkType == ConnectivityManager.TYPE_WIFI){
            return true;
        }
        return false;
    }



    public static int getNetworkType(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return networkInfo.getType();
            }
        }
        return -1;
    }

    /**
     * 通过ping网址方法，判断我们的网络是否能用。
     *
     * @return
     */
    public static final boolean ping() {
        String result = null;
        try {
            String ip = "www.baidu.com";
            Process p = Runtime.getRuntime().exec("ping -c 1 -w 100 " + ip);// ping网址1次
            LogUtils.d(TAG, "ping网址了。");
            int status = p.waitFor();
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.d(TAG, "出现IOException异常了。");
        } catch (InterruptedException e) {
            e.printStackTrace();
            LogUtils.d(TAG, "出现InterruptedException异常了。");
        } finally {
            LogUtils.d(TAG, "result = " + result);
        }
        return false;
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    private static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

}
