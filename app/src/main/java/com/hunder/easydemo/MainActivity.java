package com.hunder.easydemo;

import android.os.Bundle;
import android.view.View;

import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easydemo.dialog.DialogActivity;
import com.hunder.easydemo.emoji.EmojiTestActivity;
import com.hunder.easydemo.mvp.presenter.MainPresenter;
import com.hunder.easydemo.network.NetWorkSpeedTestActivity;
import com.hunder.easydemo.network.NetworkListeningActivity;
import com.hunder.easydemo.mvp.contract.MainContract;
import com.hunder.easydemo.view.ViewActivity;
import com.hunder.easylib.entity.Home;
import com.hunder.easylib.network_listening.NetworkManager;
import com.hunder.easylib.network_listening.annotation.Network;
import com.hunder.easylib.network_listening.type.NetType;
import com.hunder.easylib.utils.LogUtils;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View {

    private MainPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NetworkManager.getDefault().registerObserver(this);

        mPresenter = new MainPresenter(this);
        mPresenter.loadData();
    }

    @OnClick({R.id.tv, R.id.dialog, R.id.view, R.id.network_speed, R.id.emoji, R.id.network_listening, R.id.kefu})
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

            case R.id.kefu:
                CommonWebActivity.startActivity(this, "http://mad.miduoke.net/Web/im.aspx?_=t&accountid=115168");
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
        mPresenter.detachView();
    }


    @Override
    public void showData(Home home) {
        LogUtils.d("showData:" + home.boutiqueList.get(0).name);
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showError(String errorMsg) {
        LogUtils.d("showError:" + errorMsg);
    }

}
