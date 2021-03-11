package com.hunder.easydemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.widget.ColorSelectorView;

import butterknife.BindView;

/**
 * Created by hp on 2020/6/13.
 */

public class VolumeViewActivity extends BaseActivity {

    @BindView(R.id.color_selector_view)
    ColorSelectorView mColorSelectorView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_volume_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mColorSelectorView.setColorSelectResultListen(new ColorSelectorView.OnClickColorListener() {
            @Override
            public void setColor(int paramInt) {

            }
        });
    }

}
