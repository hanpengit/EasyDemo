package com.hunder.easydemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.IntentUtils;

import butterknife.OnClick;

/**
 * Created by hp on 2018/7/25.
 */

public class ViewActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.view, R.id.viewx, R.id.view1, R.id.view2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.view:
                DragViewActivity.startActivity(this);
                break;

            case R.id.view1:
                PieViewActivity.startActivity(this);
                break;

            case R.id.view2:
                IntentUtils.startActivity(this, VolumeViewActivity.class);
                break;

            case R.id.viewx:

                break;
        }
    }

}
