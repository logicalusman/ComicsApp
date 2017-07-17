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

    public static void showDialog(@NonNull Context ctx, String title, @NonNull String msg) {

        showDialog(ctx, title, msg, false);

    }

    public static void showDialog(@NonNull Context ctx, @Nullable String title, @NonNull String msg, boolean cancelable) {


        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setCancelable(false).setMessage(msg).
                setPositiveButton(ctx.getString(R.string.ok), null);

        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title);
        }

        builder.create().show();
    }

    public static void showConnectionAlertErrDialog(@NonNull Context ctx) {
        showDialog(ctx, ctx.getString(R.string.unable_to_connect), ctx.getString(R.string.internet_connection_alert));
    }

    public static void showUnknownErrDialog(@NonNull Context ctx) {
        showDialog(ctx, ctx.getString(R.string.error), ctx.getString(R.string.unknown_error));
    }

}
