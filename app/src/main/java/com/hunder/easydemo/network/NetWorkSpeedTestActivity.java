package com.hunder.easydemo.network;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.NetWorkSpeedUtils;

import butterknife.BindView;

/**
 * 检测网速
 * Created by hp on 2019/5/14.
 */

public class NetWorkSpeedTestActivity extends BaseActivity {

    @BindView(R.id.tv_network_speed)
    TextView mTvNetworkSpeed;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NetWorkSpeedTestActivity.class);
        context.startActivity(intent);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    mTvNetworkSpeed.setText("当前网速:" + msg.obj.toString());
                    //ToastUtils.showMessage("当前网速:" + msg.obj.toString());
                    LogUtils.d("执行", "当前网速:" + msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_network_speed_test;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetWorkSpeedUtils.newInstance(this, mHandler).startShowNetSpeed();
    }

}
