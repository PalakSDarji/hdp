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
    public static final String BASE_URL = "http://hadipaa.dev.tnxlabs.com/";
    public static final String API_VERSION = "api/android/v1";

    //LOGIN PAGE
    public static final String LOGIN = "/oauth-user/login";
    public static final String FORGOT_PASSWORD = "/user/forgot_password";
    public static final String VERIFY_CHANGE_PASSWORD_CODE = "/user/forgot_password/verify_change_password_code";
    public static final String CHANGE_PASSWORD = "/user/forgot_password/change_password";
    public static final String RESET_PASSWORD = "/reset_password_new";

    //ACTIVITY
    public static final String ACTIVITY_TYPE = "/activities";
    public static final String CREATE_POST = "/post/create";
    public static final String POST_FILTERS = "/posts/filters";
    public static final String FETCH_POST = "/posts";
    public static final String ACTIVITY_REQUEST_JOIN = "/activity/request/join";
    public static final String ACTIVITY_REQUEST_DECLINE = "/activity/decline";
    public static final String ACTIVITY_OTHER_REQUEST_DECLINE = "/activity/request/decline";
    public static final String ACTIVITY_OTHER_REQUEST_ACCEPT = "/activity/request/accept";
    public static final String ACTIVITY_ROLLBACK = "/activity/rollback";

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
    public static final String EDIT_PROFILE = "/profile/edit";

    //MY PLANS
    public static final String MY_PLANS = "/my_plans";
    public static final String MY_PLANS_HISTORY = "/my_plans/history";
    public static final String MY_PLANS_EDIT = "/my_plans/edit";
    public static final String MY_PLANS_DELETE_MY_ACTIVITY = "/my_plans/delete_my_activity";
    public static final String MY_PLANS_DELETE_FROM_OTHER_ACTIVITY = "/my_plans/delete_from_other_activity";

    //ZOMATO
    public static final String ZOMATO = "zomato/restaurant/search/";
    public static final String CAFES = "cafes";
    public static final String NIGHTCLUB = "nightclub";
    public static final String LOUNGE = "lounge";

    //MERAEVENTS
    public static final String MERAEVENTS = "meraevents/";
    public static final String MERAEVENTS_PARTY  = "event/party";
    public static final String MERAEVENTS_SPORTS_INDOOR = "sports/indoor";
    public static final String MERAEVENTS_SPORTS_OUTDOOR = "sports/outdoor";
    public static final String MERAEVENTS_SPORTS_ADV = "sports/adventure";
    public static final String MERAEVENTS_ENTERTAINMENT_EVENT = "entertainment/event";
    public static final String MERAEVENTS_ENTERTAINMENT_THEATER = "entertainment/theater";
    public static final String MERAEVENTS_FESTIVAL = "event/fastival";


    public static final String CLIENT_ID= "HADIPAA_USER";
    public static final String CLIENT_SECRET="HADIPAA@2016";
    public static final String LOGIN_STATUS_EMAIL="1";
    public static final String LOGIN_STATUS_FB="2";
    public static final String LOGIN_STATUS_NA="0";

    public static final String API_KEY = "AIzaSyC3u3fzAnZ4qkHgSt7BNPifoXFHzUPKV1U";
    public static final String SENDER_ID = "1091219618361";

    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final String DEVICE_TYPE="2"; //For Android
    public static final String SEND_PASSWORD = "";//TODO add value
    public static final String LOGIN_BY_THIRD_PARTY = "";//TODO add value

    public static ProgressDialog PROGRESS_DIALOG = null;

    public static final String ACTIVITY_KEY = "activity_key";
    public static final String TRAVEL_FROM_KEY = "travel_from";
    public static final String TRAVEL_TO_KEY = "travel_to";
    public static final String TRAVEL_BY_KEY = "travel_by";
    public static final String PROFILE_KEY = "PROFILE_KEY";
    public static final String FETCH_USER_KEY = "id";

    public static final int ACTIVITY_CREATE_ACTIVITY = 1;
    public static final int ACTIVITY_FROM_COFFEE = 2;
    public static final int ACTIVITY_HOBBY = 5;
    public static final int ACTIVITY_TRAVEL_SCHEDULE = 6;
    public static final int ACTIVITY_PARTY =10;
    public static final int ACTIVITY_STANDUP_COMEDY =11;
    public static final int ACTIVITY_INDOOR_SPORTS =12;
    public static final int ACTIVITY_ENTERTAINMENT =13;
    public static final int ACTIVITY_OUTDOOR_SPORTS =14;
    public static final int ACTIVITY_ADV_SPORTS =88;

    public static final int ACTIVITY_TRAVEL_FROM_FILTER = 4;
    public static final int ACTIVITY_TRAVEL_FROM_POST_AIR = 15;
    public static final int ACTIVITY_TRAVEL_FROM_POST_TRAIN =16;
    public static final int ACTIVITY_TRAVEL_FROM_POST_BUS = 17;

    public static final int ACTIVITY_COFFEE = 7;
    public static final int ACTIVITY_NIGHTCLUB = 8;
    public static final int ACTIVITY_LOUNGE = 9;

    public static final int ACTIVITY_EVENT_PARTY = 18;
    public static final int ACTIVITY_EVENT_THEATER = 19;
    public static final int ACTIVITY_EVENT_EVENT = 20;
    public static final int ACTIVITY_EVENT_FESTIVAL = 21;

    public static final int API_ACTIVITY_ID_MOVIE = 1;
    public static final int API_ACTIVITY_ID_THEATER = 2;
    public static final int API_ACTIVITY_ID_EVENT = 3;
    public static final int API_ACTIVITY_ID_NIGHTCLUB = 4;
    public static final int API_ACTIVITY_ID_LOUNGE = 5;
    public static final int API_ACTIVITY_ID_PARTY = 6;
    public static final int API_ACTIVITY_ID_STAND_UP_COMEDY = 7;
    public static final int API_ACTIVITY_ID_FLIGHT = 8;
    public static final int API_ACTIVITY_ID_TRAIN = 9;
    public static final int API_ACTIVITY_ID_BUS = 10;
    public static final int API_ACTIVITY_ID_COFFEE = 12;
    public static final int API_ACTIVITY_ID_INDOOR = 13;
    public static final int API_ACTIVITY_ID_OUTDOOR = 14;
    public static final int API_ACTIVITY_ID_AVD_SPORTS = 15;
    public static final int API_ACTIVITY_ID_HOBBY = 20;
    public static final int API_ACTIVITY_ID_FESTIVAL = 9;


    //GOOGLE PLACES
    public static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    public static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    public static final String OUT_JSON = "/json";
    public static final String GOOGLE_API_KEY = "AIzaSyD5DyFre7np6MJ6MlZ-rEegKPxycXKBB8c";



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

        if(PROGRESS_DIALOG != null) PROGRESS_DIALOG.dismiss();


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


    public static String distanceMeasure(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return String.valueOf(Math.round(dist));
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }



}
