package com.group18.dormitory.Data;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.group18.dormitory.R;

public final class CustomProgressBar {

    private Dialog dialog;

    public Dialog show(Context context) {
        return show(context, null);
    }

    public Dialog show(Context context, CharSequence title) {
        return show(context, title, false);
    }

    public Dialog show(Context context, CharSequence title, boolean cancelable) {
        return show(context, title, cancelable, null);
    }

    public Dialog show(Context context, CharSequence title, boolean cancelable,
                       DialogInterface.OnCancelListener cancelListener) {
        LayoutInflater inflator = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = inflator.inflate(R.layout.progress_bar, null);

        dialog = new Dialog(context, R.style.NewDialog);
        dialog.setContentView(view);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();

        return dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }


    private static CustomProgressBar instance;
    private CustomProgressBar() {
    }
    public static CustomProgressBar getInstance() {
        if(instance == null) {
            instance = new CustomProgressBar();
        }
        return instance;
    }

}