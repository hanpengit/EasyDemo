package com.hunder.easydemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.DragViewUtil;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/11/8.
 */

public class DragViewActivity extends BaseActivity {

    @BindView(R.id.iv_float)
    ImageView mIvFloat;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DragViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drag_view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DragViewUtil.drag(mIvFloat);
    }

    /**
     * 必须给拖动的控件设置点击事件才可以拖动
     */
    @OnClick(R.id.iv_float)
    public void onClick() {
        if (DragViewUtil.mClickable) {
            ToastUtils.showMessage("onClick");
        }
    }

}
