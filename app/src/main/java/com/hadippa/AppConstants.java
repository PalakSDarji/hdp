package com.hadippa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by HP on 11-07-2016.
 */
public class AppConstants {


    public static final boolean DEBUG = true;
    public static final String DEBUG_TAG = "Hadippa";
    public static final int SOCKET_TIMEOUT = 60;
    public static final int CONNECTION_TIMEOUT = 60;
    public static final String BASE_URL = "http://www.hadipaa.folives.com/api/android/";
    //LOGIN PAGE
    public static final String LOGIN = "/oauth-user/login";
    public static final String FORGOT_PASSWORD = "/user/forgot_password";
    public static final String VERIFY_CHANGE_PASSWORD_CODE = "/user/forgot_password/verify_change_password_code";
    public static final String CHANGE_PASSWORD = "/user/forgot_password/change_password";
    public static final String RESET_PASSWORD = "/reset_password_new";

    //CARD
    public static final String ACTIVITY_REQUEST_JOIN = "/activity/request/join";
    public static final String ACTIVITY_REQUEST_DECLINE = "/activity/decline";

    //SIGN UP
    public static final String SIGN_UP_STEP_1 = "/register/step_1";
    public static final String SIGN_UP_STEP_2 = "/register/step_2";
    public static final String SIGN_UP_STEP_2_RESEND_OTP = "/register/step_2/resend_mobile_otp";
    public static final String SIGN_UP_STEP_2_VERIFY_OTP = "/register/step_2/verify_mobile_otp";
    public static final String SIGN_UP_STEP_3 = "/register/step_3";

    //CONNECTIONS
    public static final String CONNECTION_FOLLOWERS = "/followers";
    public static final String CONNECTION_FOLLOWING = "/following";
    public static final String CONNECTION_FOLLOW = "/follow";
    public static final String CONNECTION_UNFOLLOW = "/unfollow";
    public static final String CONNECTION_BLOCK = "/block";
    public static final String CONNECTION_UNBLOCK = "/unblock";
    public static final String CONNECTION_BLOCKLIST = "/blocked_list";

    //SEARCH
    public static final String SEARCH_CITY = "/search/city";
    public static final String SEARCH_PEOPLE = "/search/people";
    public static final String SEARCH_TAGS = "/search/people/tags";

    //PREFERENCES
    public static final String PREFERENCES = "/user/preference";
    public static final String PREFERENCES_EDIT = "/user/preference/edit";

    //PROFILE
    public static final String MY_PROFILE = "/profile/my_profile";
    public static final String OTHERS_PROFILE = "/profile/other_profile";



    public static final String CLIENT_ID= "HADIPAA_USER";
    public static final String CLIENT_SECRET="HADIPAA@2016";
    public static final String API_VERSION = "v1";
    public static final String LOGIN_STATUS_EMAIL="1";
    public static final String LOGIN_STATUS_FB="2";
    public static final String LOGIN_STATUS_NA="0";

    public static final String API_KEY = "AIzaSyC3u3fzAnZ4qkHgSt7BNPifoXFHzUPKV1U";
    public static final String SENDER_ID = "1091219618361";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

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

    public static void showSnackBar(RelativeLayout relativeLayout, String message){

        Snackbar.make(relativeLayout,message , Snackbar.LENGTH_LONG)

                .show();

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
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void generateSHAKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.hadippa", PackageManager.GET_SIGNATURES); //package name here
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }




}
