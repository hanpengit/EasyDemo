package com.hunder.easylib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hunder.easylib.R;
import com.hunder.easylib.R2;
import com.hunder.easylib.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/11/7.
 */

public class CustomBottomDialog extends Dialog {

    @BindView(R2.id.tv_confirm)
    TextView mTvConfirm;
    @BindView(R2.id.tv_cancel)
    TextView mTvCancel;

    public CustomBottomDialog(Context context) {
        super(context, R.style.BottomDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(getContext(), R.layout.dialog_bottom, null);
        ButterKnife.bind(this, view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(view);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        dialogWindow.setAttributes(p);
        //p.width = d.getWidth();
        p.gravity = Gravity.FILL_HORIZONTAL | Gravity.BOTTOM;
        dialogWindow.setAttributes(p);
    }

    @OnClick({R2.id.tv_confirm, R2.id.tv_cancel})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_confirm) {
            dismiss();
            ToastUtils.showMessage("确定");
        } else if (i == R.id.tv_cancel) {
            dismiss();
            ToastUtils.showMessage("取消");
        }
    }

}
