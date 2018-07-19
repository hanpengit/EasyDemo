package com.hunder.easylib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.hunder.easylib.R;
import com.hunder.easylib.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by hp on 2018/5/21.
 */
public class CustomMultiItemDialog extends Dialog {

    private OnClickListener listener;

    public CustomMultiItemDialog(Context context) {
        super(context, R.style.Dialog);
        View view = View.inflate(context, R.layout.dialog_custom_multi_item, null);
        ButterKnife.bind(this, view);

        setCancelable(true);
        setCanceledOnTouchOutside(true);
        setContentView(view);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }

    @OnClick({R2.id.app_open, R2.id.local_open})
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.app_open) {
            dismiss();
            listener.appOpen();
        } else if (i == R.id.local_open) {
            dismiss();
            listener.localOpen();
        }
    }


    /**
     * 点击监听
     */
    public interface OnClickListener {
        void appOpen();

        void localOpen();
    }

}
