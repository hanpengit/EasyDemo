package com.hunder.easylib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hunder.easylib.R;
import com.hunder.easylib.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by hp on 2016/8/3.
 */
public class CustomNormalDialog extends Dialog {

    @BindView(R2.id.tv_title)
    TextView tvTitle;
    @BindView(R2.id.btn_cancel)
    Button btnCancel;
    @BindView(R2.id.btn_confirm)
    Button btnConfirm;

    private OnClickListener listener;

    public CustomNormalDialog(Context context) {
        super(context, R.style.Dialog);

        //也可以将下边的代码放到onCreate()方法中
        View view = View.inflate(context, R.layout.dialog_normal, null);
        ButterKnife.bind(this, view);
        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(view);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setMessage(String cancelText, String confirmText) {
        btnCancel.setText(cancelText);
        btnConfirm.setText(confirmText);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R2.id.btn_cancel, R2.id.btn_confirm})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_cancel) {
            dismiss();
        } else if (i == R.id.btn_confirm) {
            dismiss();
            listener.onConfirm();
        }
    }


    /**
     * 确认点击监听
     */
    public interface OnClickListener {
        void onConfirm();
    }

}
