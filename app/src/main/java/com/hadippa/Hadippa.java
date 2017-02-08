package com.hadippa;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.hadippa.activities.LoginActivity;
import com.hadippa.utils.DeviceUniqueId;
import com.hadippa.utils.PreferencesHelper;
import com.hadippa.utils.Utils;

import java.util.ArrayList;
import java.util.Random;

import io.appsfly.sdk.models.AppsFlyClientConfig;
import io.appsfly.sdk.providers.AppsFlyProvider;

public class Hadippa extends Application {

    private static final String TAG = Hadippa.class.getSimpleName();
    private static Hadippa sInstance;
    public static String mDeviceId, mUniqueId, mAndroidId;
    private static PreferencesHelper prefs;

    private static final String DEVICE_ID_FORMAT = "imei_%s"; // Note: There is a 23 character limit you will get An NPE if you go over that limit


    @Override
    public void onCreate() {
        super.onCreate();

        String repoUrl = "http://s3-ap-southeast-1.amazonaws.com/repo.appsfly.io"; // Current repo url is 'http://s3-ap-southeast-1.amazonaws.com/repo.appsfly.io'
        String projectId = "589afc9285cbc2000fa1c782"; // Project under which MicroApp is created
        String microAppId = "589afcb985cbc2000fa1c784"; // MicroApp Id
        String associationKey = "4174f005-0b08-465a-ab76-ae118f086854"; // Association key created on the dashboard
        ArrayList<AppsFlyClientConfig> appsFlyClientConfigs = new ArrayList<AppsFlyClientConfig>();
        AppsFlyClientConfig appsflyConfig = new AppsFlyClientConfig(projectId, microAppId,  associationKey, repoUrl);
        appsFlyClientConfigs.add(appsflyConfig);
        AppsFlyProvider.getInstance().initialize(appsFlyClientConfigs, this);
        sInstance = this;
        FontsOverride.setDefaultFont(this, "DEFAULT", getResources().getString(R.string.proxima_reg));

        prefs = PreferencesHelper.getInstance(this);

        if (checkIsPermissionAvailable(this, android.Manifest.permission.READ_PHONE_STATE)) {

            getApplicationCreds(this);
        }

    }

    public static synchronized Hadippa getInstance() {
        return sInstance;
    }


    /**
     * This function is used to get imei number from phone
     *
     * @param context
     */
    public static void getApplicationCreds(Context context) {

        generateUniqueID(context);

        if (mUniqueId == null) {
            mUniqueId = prefs.getString(PreferencesHelper.DEVICE_ID);
        }
        if (mDeviceId == null) {
            mDeviceId = prefs.getString(PreferencesHelper.FORMATTED_DEVICE_ID);
        }

        if (mDeviceId.length() > 23) {
            mDeviceId = mDeviceId.substring(0, 23);
        }
    }

    /**
     * This function is used to generate and save imei and android id.
     *
     * @param applicationContext
     */
    public static void generateUniqueID(Context applicationContext) {
        //device id for api call
        mUniqueId = getIMEI(applicationContext);

        //formatted device id for mqtt
        mDeviceId = String.format(DEVICE_ID_FORMAT, mUniqueId);
        // Get the Unique Android ID for login api call
        mAndroidId = getDeviceUniqueID(applicationContext);

        //store id in preference
        prefs.putString(PreferencesHelper.ANDROID_ID, mAndroidId);
        prefs.putString(PreferencesHelper.DEVICE_ID, mUniqueId);
        prefs.putString(PreferencesHelper.FORMATTED_DEVICE_ID, String.format(DEVICE_ID_FORMAT, mUniqueId));

        Log.v(TAG,"mAndroidId: " + mAndroidId + ", mUniqueId : "+mUniqueId + ", FORMATTED_DEVICE_ID : "+ prefs.getString(PreferencesHelper.FORMATTED_DEVICE_ID));
    }

    /**
     * This function is used to get imei number
     *
     * @param activity activity
     * @return imei
     */
    public static String getIMEI(Context activity) {
        TelephonyManager telephonyManager = (TelephonyManager) activity
                .getSystemService(Context.TELEPHONY_SERVICE);

        String deviceId = telephonyManager.getDeviceId();

        // FAIL OVER 1 - RANDOM ID
        Random tmpRand = new Random();
        Long tmpRandId = tmpRand.nextLong();
        String randomId = String.valueOf(tmpRandId < 0 ? tmpRandId * -1 : tmpRandId);
        randomId = randomId.length() > 13 ? "98" + randomId.substring(0, 13) : randomId;

        if (deviceId != null && !deviceId.equals("000000000000000")) {
            return deviceId;
        } else {
            return randomId;
        }
    }

    /**
     * This function is used to generate android id.
     *
     * @param activity
     * @return
     */
    public static String getDeviceUniqueID(Context activity) {
        return Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    /**
     * Function that checks if specific permission is granted or not
     *
     * @param permission Manifest permission to be checked
     * @return true if permission is granted
     */
    public boolean checkIsPermissionAvailable(Context context, String permission) {

        int hasPermission = ContextCompat.checkSelfPermission(context, permission);
        if (hasPermission == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


}
