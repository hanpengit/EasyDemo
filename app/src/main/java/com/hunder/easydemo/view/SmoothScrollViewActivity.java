package com.hunder.easydemo.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.ToastUtils;
import com.hunder.easylib.widget.SmoothScrollView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试自定义view(弹性滑动)
 */
public class SmoothScrollViewActivity extends BaseActivity {

    @BindView(R.id.view)
    View mView;
    @BindView(R.id.smooth_scroll_view)
    SmoothScrollView mSmoothScrollView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smooth_scroll_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view:
                ToastUtils.showMessage("onlick view");
                //mSmoothScrollView.scrollTo(-500,0); //比较生硬的滑动
                mSmoothScrollView.smoothScrollTo(-500, -200);
                break;
        }
    }

}
