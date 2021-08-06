package com.hunder.easydemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.ToastUtils;

public class CustomView1Activity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomView1Activity.class);
        context.startActivity(intent);
    }

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
