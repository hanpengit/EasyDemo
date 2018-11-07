package com.hunder.easydemo.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hunder.easydemo.R;
import com.hunder.easydemo.base.BaseActivity;
import com.hunder.easylib.dialog.CustomMultiItemDialog;
import com.hunder.easylib.dialog.CustomNormalDialog;
import com.hunder.easylib.utils.DialogUtils;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.OnClick;

/**
 * Created by hp on 2018/7/25.
 */

public class DialogActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DialogActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @OnClick({R.id.dialog1, R.id.dialog2, R.id.dialog2_1, R.id.dialog3, R.id.dialog4, R.id.dialog4_1, R.id.dialog5,
            R.id.dialog6, R.id.dialog7, R.id.dialog8, R.id.dialog9})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog1:
                DialogUtils.showNormalDialog(this, "是否删除文件?", new CustomNormalDialog.OnClickListener() {
                    @Override
                    public void onConfirm() {
                        ToastUtils.showMessage("已删除");
                    }
                });
                break;

            case R.id.dialog2:
                DialogUtils.showMultiItemDialog(this, new CustomMultiItemDialog.OnClickListener() {
                    @Override
                    public void appOpen() {
                        ToastUtils.showMessage("appOpen");
                    }

                    @Override
                    public void localOpen() {
                        ToastUtils.showMessage("localOpen");
                    }
                });
                break;

            case R.id.dialog2_1:
                DialogUtils.showMultiItemDialog2(this, new CustomMultiItemDialog.OnClickListener() {
                    @Override
                    public void appOpen() {
                        ToastUtils.showMessage("appOpen2");
                    }

                    @Override
                    public void localOpen() {
                        ToastUtils.showMessage("localOpen2");
                    }
                });
                break;

            case R.id.dialog3:
                DialogUtils.showTipMessageDialog(this, "直播还未开始");
                break;

            case R.id.dialog4:
                DialogUtils.showTipMessageDialog(this,
                        "退出账号?",
                        "取消啊",
                        "确定啊",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showMessage("取消。。。");
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showMessage("确定。。。");
                            }
                        });
                break;

            case R.id.dialog4_1:
                DialogUtils.showTipMessageDialog2(this,
                        "退出账号吗?",
                        "取消",
                        "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showMessage("取消...");
                            }
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ToastUtils.showMessage("确定...");
                            }
                        });
                break;

            case R.id.dialog5:
                DialogUtils.showLoadingDialog(this, "正在加载...");
                break;

            case R.id.dialog6:
                DialogUtils.showLoadingDialog2(this, "正在加载...");
                break;

            case R.id.dialog7:
                DialogUtils.showLoadingDialog3(this);
                break;

            case R.id.dialog8:
                DialogUtils.showBottomDialog(this);
                break;

            case R.id.dialog9:
                DialogUtils.showBottomDialog2(this);
                break;

        }
    }

}
