package com.hunder.easydemo.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 测试动画方式实现弹性滑动
 */
public class SmoothScrollViewActivity1 extends BaseActivity {

    @BindView(R.id.tv)
    TextView mTv;
    @BindView(R.id.btn)
    Button mBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_smooth_scroll_view1;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        // 方式一（移动的是view本身）：
        ObjectAnimator.ofFloat(mTv, "translationX", 0, 200).setDuration(1000).start();
        ObjectAnimator.ofFloat(mTv, "translationY", 0, 100).setDuration(1000).start();

        // 方式二（移动的是view内容）：
        //startMove();
    }

    private void startMove() {
        final int startX = 0;
        final int deltaX = -200;
        final int startY = 0;
        final int deltaY = -100;
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mTv.scrollTo(startX + (int) (deltaX * fraction), startY + (int) (deltaY * fraction));
            }
        });
        valueAnimator.start();
    }

}
