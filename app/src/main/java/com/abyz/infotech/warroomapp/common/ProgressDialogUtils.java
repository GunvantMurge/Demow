package com.abyz.infotech.warroomapp.common;

import android.app.ProgressDialog;
import android.content.Context;

import com.abyz.infotech.warroomapp.R;


public class ProgressDialogUtils {

    public ProgressDialog getProgressDialog(Context context) {
        ProgressDialog mProgressDialog = new ProgressDialog(context, R.style.ProgressDialogTheme);
        mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        mProgressDialog.setCancelable(false);
        return mProgressDialog;
    }


}
