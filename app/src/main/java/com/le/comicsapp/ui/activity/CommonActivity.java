package com.le.comicsapp.ui.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

public class CommonActivity extends AppCompatActivity {


    private String TAG = "CommonActivity";
    private ProgressDialog mProcessingDialog;

    public void showProcessingDialog(@NonNull String message) {

        mProcessingDialog = new ProgressDialog(this);
        mProcessingDialog.setMessage(message);
        mProcessingDialog.setCancelable(true);
        mProcessingDialog.show();
    }

    public void dismissProcessingDialog() {
        if (mProcessingDialog != null && mProcessingDialog.isShowing()) {
            try {
                mProcessingDialog.dismiss();
                mProcessingDialog = null;
            } catch (Exception ignored) {
            }
        }
    }

}
