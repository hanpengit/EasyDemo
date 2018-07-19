package com.hunder.easydemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hunder.easylib.dialog.CustomMultiItemDialog;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private CustomMultiItemDialog customMultiItemDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        customMultiItemDialog = new CustomMultiItemDialog(this);
        customMultiItemDialog.setOnClickListener(new CustomMultiItemDialog.OnClickListener() {
            @Override
            public void appOpen() {
                ToastUtils.showMessage("appOpen");
            }

            @Override
            public void localOpen() {
                ToastUtils.showMessage("localOpen");
            }
        });
    }

    @OnClick({R.id.tv, R.id.tv_jump})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv:
                break;
            case R.id.tv_jump:
                customMultiItemDialog.show();
                break;
        }
    }

}
