package com.hunder.easydemo;

import android.os.Bundle;
import android.view.View;

import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easydemo.dialog.DialogActivity;
import com.hunder.easydemo.emoji.EmojiTestActivity;
import com.hunder.easydemo.network.NetWorkSpeedTestActivity;
import com.hunder.easydemo.network.NetworkListeningActivity;
import com.hunder.easydemo.view.ViewActivity;
import com.hunder.easylib.network_listening.NetworkManager;
import com.hunder.easylib.network_listening.annotation.Network;
import com.hunder.easylib.network_listening.type.NetType;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkManager.getDefault().registerObserver(this);
    }

    @OnClick({R.id.tv, R.id.dialog, R.id.view, R.id.network_speed, R.id.emoji, R.id.network_listening})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                ToastUtils.showMessage("Easy Demo");
                break;

            case R.id.dialog:
                DialogActivity.startActivity(this);
                break;

            case R.id.view:
                ViewActivity.startActivity(this);
                break;

            case R.id.network_speed:
                NetWorkSpeedTestActivity.startActivity(this);
                break;

            case R.id.emoji:
                EmojiTestActivity.startActivity(this);
                break;

            case R.id.network_listening:
                NetworkListeningActivity.startActivity(this);
                break;
        }
    }

    @Network
    public void network(NetType netType) {
        LogUtils.d("netType: " + netType);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getDefault().unRegisterObserver(this);
    }

}
