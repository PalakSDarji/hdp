package com.hadippa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

/**
 * Created by HP on 11-07-2016.
 */
public class AppConstants {


    public static final boolean DEBUG = true;
    public static final String DEBUG_TAG = "Hadippa";
    public static final int SOCKET_TIMEOUT = 60;
    public static final int CONNECTION_TIMEOUT = 60;
    public static final String BASE_URL = "http://www.hadipaa.folives.com/api/android/";
    public static final String SIGN_UP_STEP_1 = "/register/step_1";
    public static final String SIGN_UP_STEP_2 = "/register/step_2";
    public static final String SIGN_UP_STEP_3 = "/register/step_3";
    public static final String SIGN_UP_STEP_4 = "/register/step_4";
    public static final String LOGIN = "/oauth-user/login";
    public static final String CHANGE_PASSWORD = "/change_password";
    public static final String SEND_PASSWORD = "/forgot_password_new";
    public static final String RESET_PASSWORD = "/reset_password_new";
    public static final String LOGIN_BY_THIRD_PARTY = "/login_by_thirdparty";
    public static final String CLIENT_ID= "HADIPAA_USER";
    public static final String CLIENT_SECRET="HADIPAA@2016";
    public static final String API_VERSION = "v1";
    public static final String LOGIN_STATUS_EMAIL="1";
    public static final String LOGIN_STATUS_FB="2";
    public static final String LOGIN_STATUS_NA="0";

    public static final String DEVICE_TYPE="2"; //For Android

    public static ProgressDialog PROGRESS_DIALOG = null;

    public static String USER = "user";
    public static Bitmap takeScreenShot(Activity activity)
    {
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height  - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    public static void showProgressDialog(Context context,String message){

        PROGRESS_DIALOG = new ProgressDialog(context);
        PROGRESS_DIALOG.setMessage(message);
        PROGRESS_DIALOG.setCanceledOnTouchOutside(false);
        PROGRESS_DIALOG.setCancelable(false);
        PROGRESS_DIALOG.show();

    }

    public static void dismissDialog(){

        PROGRESS_DIALOG.dismiss();

    }
}
