package com.hunder.easydemo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.hunder.easydemo.manager.ActivityUtils;
import com.hunder.easylib.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * base Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        ActivityUtils.addActivity(this);
        LogUtils.d("Activity:" + this.getLocalClassName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityUtils.removeActivity(this);
    }

    protected abstract int getLayoutId();

}
