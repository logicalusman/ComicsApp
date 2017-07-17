package com.le.comicsapp.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.le.comicsapp.R;

/**
 * Reusable and handy UI methods.
 *
 * @author Usman
 */
public class UiUtils {

    public static AlertDialog showDialog(@NonNull Context ctx, String title, @NonNull String msg) {

        return showDialog(ctx, title, msg, false);

    }

    public static AlertDialog showDialog(@NonNull Context ctx, @Nullable String title, @NonNull String msg, boolean cancelable) {


        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setCancelable(false).setMessage(msg).
                setPositiveButton(ctx.getString(R.string.ok), null);

        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    public static AlertDialog showConnectionAlertErrDialog(@NonNull Context ctx) {
        return showDialog(ctx, ctx.getString(R.string.unable_to_connect), ctx.getString(R.string.internet_connection_alert));
    }

    public static AlertDialog showUnknownErrDialog(@NonNull Context ctx) {
        return showDialog(ctx, ctx.getString(R.string.error), ctx.getString(R.string.unknown_error));
    }

}
