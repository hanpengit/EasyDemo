package com.hunder.easydemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.ToastUtils;

/**
 * 测试自定义view(跟随手指移动)
 */
public class CustomView1Activity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_view1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = findViewById(R.id.custom_view1);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ToastUtils.showMessage("onClick");
            }
        });
    }

}
