package com.hadippa.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.hadippa.Hadippa;
import com.hadippa.R;
import com.hadippa.database.HadippaDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Palak on 10-10-2016.
 */

public class Utils {

    public static final Locale LOCALE;
    private static AtomicInteger messageSequence = new AtomicInteger();

    static {
        LOCALE = Locale.US;
    }

    public static ProgressDialog getGenericProgressDialog(Context context, int string_id) {
        ProgressDialog dialog;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            dialog = new ProgressDialog(context);
        } else {
            dialog = new ProgressDialog(context);
        }
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(context.getResources().getString(string_id));
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return dialog;
    }

    /**
     * get message id which is auto increment
     * @return
     */
    public static String nextMessageId(Context context) {
        try {
            HadippaDatabase mDB = HadippaDatabase.getInstance(context);
            if (mDB != null) {
                messageSequence.set(mDB.getMessageMaxID() + 1);
            }
        } catch (Exception e) {
            // Swallow
        }

        return String.valueOf(messageSequence.incrementAndGet());
    }

    public static Date timestampStringToDate(String inDate) {
        if (!TextUtils.isEmpty(inDate)) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIMESTAMP_FORMAT_WITH_TIMEZONE, LOCALE);
                Date outDate = simpleDateFormat.parse(inDate);
                simpleDateFormat.applyPattern(Constants.TIMESTAMP_FORMAT_WITH_TIMEZONE);
                TimeZone tz = TimeZone.getDefault();
                simpleDateFormat.setTimeZone(tz);
                return outDate;
            } catch (Exception e) {
                Log.e("utils", e.toString());
                e.printStackTrace();
            }
        }
        return new Date();
    }

    /***************************************************
     * Connection Helpers
     ***************************************************/

    public static NetworkInfo getNetworkInfo(Context context) {

        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return connectivityManager.getActiveNetworkInfo();
        }
        return null;
    }

    public static boolean hasConnection(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);

        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * get current data and time
     * @param format
     * @return
     */
    public static String getDateTimeNow(String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, LOCALE);
        TimeZone tz = TimeZone.getDefault();
        simpleDateFormat.setTimeZone(tz);
        return simpleDateFormat.format(cal.getTime());
    }


}