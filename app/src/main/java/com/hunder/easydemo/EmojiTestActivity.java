package com.hunder.easydemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.TextView;

import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class EmojiTestActivity extends BaseActivity {

    @BindView(R.id.et)
    EditText mEt;
    @BindView(R.id.tv)
    TextView mTv;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, EmojiTestActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_emoji_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick(R.id.btn)
    public void onClick() {
        /*for (char c : mEt.getText().toString().toCharArray()) {
            mTv.append(c+"_");
        }*/

        mTv.setText("嘿嘿\uD83D\uDE13\uD83D\uDE13");
        //mTv.setText("嘿嘿\uDE10");

        LogUtils.d("执行" + mEt.getText().toString());
    }

}
