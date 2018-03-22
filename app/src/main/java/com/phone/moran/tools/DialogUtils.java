package com.phone.moran.tools;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.phone.moran.R;


/**
 * Created by zhaohZhaoHee on 15/10/23.
 */
public class DialogUtils {

    public static ProgressDialog progressDialog;

    public static void msgDialog(Context context, String msg) {
        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setMessage(msg)
                .setPositiveButton(context.getResources().getString(R.string.yes), null)
                .setNegativeButton(context.getResources().getString(R.string.no), null)
                .create()
                .show();
    }

    public static Dialog getProgressDialog(Context context) {
//        Dialog mDialog = new AnimProgressDialog(context);
        Dialog mDialog = new ProgressDialog(context);
//        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return mDialog;
    }
}

