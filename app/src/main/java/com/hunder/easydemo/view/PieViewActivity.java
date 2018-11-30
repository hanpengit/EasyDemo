package com.hunder.easydemo.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.entity.PieData;
import com.hunder.easylib.widget.PieView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by hp on 2018/11/28.
 */

public class PieViewActivity extends BaseActivity {

    @BindView(R.id.pieView)
    PieView mPieView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, PieViewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<PieData> datas = new ArrayList<>();
        PieData pieData = new PieData(60);
        PieData pieData2 = new PieData(30);
        PieData pieData3 = new PieData(40);
        PieData pieData4 = new PieData(20);
        PieData pieData5 = new PieData(20);
        PieData pieData6 = new PieData(30);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        datas.add(pieData6);
        mPieView.setData(datas);
    }

    @OnClick({R.id.btn, R.id.btn1, R.id.btn2, R.id.btn3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                mPieView.setStartAngle(0);
                break;
            case R.id.btn1:
                mPieView.setStartAngle(45);
                break;
            case R.id.btn2:
                mPieView.setStartAngle(90);
                break;
            case R.id.btn3:
                mPieView.setStartAngle(180);
                break;
        }
    }

}
