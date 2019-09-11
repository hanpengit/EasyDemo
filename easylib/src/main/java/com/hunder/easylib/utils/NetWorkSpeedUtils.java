package com.hunder.easylib.utils;

import android.content.Context;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检测网速
 * Created by hp on 2019/5/14.
 */

public class NetWorkSpeedUtils {

    private Context mContext;
    private Handler mHandler;

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private final long PERIOD = 1000; //周期1秒

    public static NetWorkSpeedUtils newInstance(Context context, Handler handler) {
        return new NetWorkSpeedUtils(context, handler);
    }

    private NetWorkSpeedUtils(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            showNetSpeed();
        }
    };

    public void startShowNetSpeed() {
        lastTotalRxBytes = getTotalRxBytes();
        //lastTimeStamp = System.currentTimeMillis();
        new Timer().schedule(task, PERIOD, PERIOD); // 1s后启动任务，每隔1s执行一次
    }

    private long getTotalRxBytes() {
        return TrafficStats.getTotalRxBytes() / 1024; //转为KB
    }

    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        //long nowTimeStamp = System.currentTimeMillis();
        long speed = (nowTotalRxBytes - lastTotalRxBytes) * 1000 / PERIOD; //毫秒转换

        //lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;

        Message msg = mHandler.obtainMessage();
        msg.what = 100;
        msg.obj = (int) speed + " kb/s";
        mHandler.sendMessage(msg); //更新界面
    }

}
