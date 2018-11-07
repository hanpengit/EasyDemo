package com.hunder.easylib.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hunder.easylib.R;
import com.hunder.easylib.dialog.CustomBottomDialog;
import com.hunder.easylib.dialog.CustomMultiItemDialog;
import com.hunder.easylib.dialog.CustomNormalDialog;

/**
 * Created by hp on 2018/10/17.
 */

public class DialogUtils {

    /**
     * NormalDialog
     */
    public static Dialog showNormalDialog(Context context, String title, String cancelText, String confirmText, CustomNormalDialog.OnClickListener listener) {
        CustomNormalDialog normalDialog = new CustomNormalDialog(context);
        normalDialog.setTitle(title);
        if (!TextUtils.isEmpty(cancelText) && !TextUtils.isEmpty(confirmText)) {
            normalDialog.setMessage(cancelText, confirmText);
        }
        normalDialog.setOnClickListener(listener);

        normalDialog.show();
        return normalDialog;
    }

    /**
     * NormalDialog
     */
    public static Dialog showNormalDialog(Context context, String title, CustomNormalDialog.OnClickListener listener) {
        return showNormalDialog(context, title, "", "", listener);
    }

    /**
     * MultiItemDialog
     */
    public static Dialog showMultiItemDialog(Context context, CustomMultiItemDialog.OnClickListener listener) {
        CustomMultiItemDialog multiItemDialog = new CustomMultiItemDialog(context);
        multiItemDialog.setOnClickListener(listener);
        multiItemDialog.show();
        return multiItemDialog;
    }

    /**
     * MultiItemDialog 2
     */
    public static Dialog showMultiItemDialog2(Context context, final CustomMultiItemDialog.OnClickListener listener) {
        final Dialog dialog = new Dialog(context, R.style.Dialog);

        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.dialog_custom_multi_item, null);
        view.findViewById(R.id.app_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.appOpen();
            }
        });
        view.findViewById(R.id.local_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                listener.localOpen();
            }
        });

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        //ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view);
        dialog.show();

        return dialog;
    }

    /**
     * TipMessageDialog
     */
    public static AlertDialog showTipMessageDialog(Activity context,
                                                   String msg,
                                                   String negativeText,
                                                   String positiveText,
                                                   DialogInterface.OnClickListener negativeBtnClickListener,
                                                   DialogInterface.OnClickListener positiveBtnClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage(msg)
                .setNegativeButton(negativeText, negativeBtnClickListener)
                .setPositiveButton(positiveText, positiveBtnClickListener)
                .show();

        //修改普通对话框的位置、大小、透明度
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = alertDialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //设置高度和宽度
        //p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
        //p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.65

        //设置位置
        //p.gravity = Gravity.BOTTOM;

        //设置对话框透明度
        //p.alpha = 0.5f;
        //dialogWindow.setAttributes(p);

        //设置对话框背景透明度
        dialogWindow.setDimAmount(0.5f);

        return alertDialog;
    }

    /**
     * TipMessageDialog 2
     */
    public static AlertDialog showTipMessageDialog2(Activity context,
                                                   String msg,
                                                   String negativeText,
                                                   String positiveText,
                                                   DialogInterface.OnClickListener negativeBtnClickListener,
                                                   DialogInterface.OnClickListener positiveBtnClickListener) {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setTitle("")
                .setMessage(msg)
                .setNegativeButton(negativeText, negativeBtnClickListener)
                .setPositiveButton(positiveText, positiveBtnClickListener)
                .show();

        //修改普通对话框的位置、大小、透明度
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = alertDialog.getWindow();
        WindowManager m = context.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        //设置高度和宽度
        //p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.7

        //设置位置
        p.gravity = Gravity.BOTTOM;

        //设置对话框透明度
        p.alpha = 0.6f;
        dialogWindow.setAttributes(p);

        //设置对话框背景透明度
        dialogWindow.setDimAmount(0.3f);

        return alertDialog;
    }

    /**
     * TipMessageDialog
     */
    public static AlertDialog showTipMessageDialog(Activity context, String msg) {
        return showTipMessageDialog(context, msg, "", "确定", null, null);
    }

    /**
     * LoadingDialog
     */
    public static Dialog showLoadingDialog(Context context, String msg) {
        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.dialog_loading, null);
        TextView msgView = view.findViewById(R.id.loading_msg);
        msgView.setText(msg);

        Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view, layoutParams);
        dialog.show();

        return dialog;
    }

    /**
     * LoadingDialog 2
     */
    public static Dialog showLoadingDialog2(Context context, String msg) {
        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.dialog_loading2, null);
        TextView msgView = view.findViewById(R.id.loading_msg);
        msgView.setText(msg);

        Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view, layoutParams);
        dialog.show();

        return dialog;
    }

    /**
     * LoadingDialog 3
     */
    public static Dialog showLoadingDialog3(Context context) {
        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.dialog_loading2, null);
        TextView msgView = view.findViewById(R.id.loading_msg);
        msgView.setVisibility(View.GONE);

        Dialog dialog = new Dialog(context, R.style.LoadingDialog);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.setContentView(view, layoutParams);
        dialog.show();

        return dialog;
    }

    /**
     * BottomDialog
     */
    public static Dialog showBottomDialog(Context context) {
        final Dialog dialog = new Dialog(context, R.style.BottomDialog);

        LayoutInflater from = LayoutInflater.from(context);
        View view = from.inflate(R.layout.dialog_bottom, null);
        view.findViewById(R.id.tv_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToastUtils.showMessage("确定");
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                ToastUtils.showMessage("取消");
            }
        });

        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);

        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        dialogWindow.setAttributes(p);
        //p.width = d.getWidth();
        p.gravity = Gravity.FILL_HORIZONTAL | Gravity.BOTTOM;
        dialogWindow.setAttributes(p);

        dialog.show();
        return dialog;
    }

    /**
     * BottomDialog 2
     * 该方法和showBottomDialog()效果一样,只是在该方法中将代码封装到自定义Dialog中了
     */
    public static Dialog showBottomDialog2(Context context) {
        CustomBottomDialog bottomDialog = new CustomBottomDialog(context);
        bottomDialog.show();
        return bottomDialog;
    }

}
