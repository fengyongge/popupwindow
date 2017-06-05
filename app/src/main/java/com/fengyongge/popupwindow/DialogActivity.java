package com.fengyongge.popupwindow;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        createWindowsBar(DialogActivity.this);
    }


    public static Dialog createWindowsBar(Activity ctx) {
        Dialog dialog = new Dialog(ctx, R.style.DialogStyle);
        dialog.setContentView(R.layout.view_dialog);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
