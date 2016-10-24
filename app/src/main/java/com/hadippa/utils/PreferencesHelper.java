package com.hadippa.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * This class handles all the values which are stored/persisted.
 * @version 1.0.
 */
public class PreferencesHelper {

    private static PreferencesHelper preferencesHelper;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor prefsEditor;

    public static final String DEVICE_ID = "device_id";
    public static final String ANDROID_ID = "android_id";
    public static final String FORMATTED_DEVICE_ID = "formatted_device_id";
    // PREFS
    public static final String PREFS_NAME = "com.hadippa.utils.prefs";
    public static final String PREFS_LATITUDE = "latitude";
    public static final String PREFS_LONGITUDE = "longitude";
    public static final String PREFS_ACCURACY = "ACCURACY";
    public static final String PREF_USER_ID_KEY = "KEY_USER_ID";
    public static final String PREF_USERNAME_KEY = "KEY_USERNAME";
    public static final String PREF_SUB_USERNAME_KEY = "KEY_SUB_USERNAME";
    public static final String PREF_COUNTRY_NAME = "KEY_COUNTRY"; //EXTRA
    public static final String PREF_PASSWORD_KEY = "KEY_PASSWORD";
    public static final String PREF_DATABASE_URL_KEY = "KEY_DATABASE_URL";
    public static final String PREF_APPS_URL_KEY = "KEY_APPS_URL";
    public static final String PREF_SERVER_ID_KEY = "KEY_SERVER_ID";
    public static final String PREF_CREDENTIALS_VALIDATED_KEY = "KEY_CREDENTIALS_VALIDATED";
    public static final String PREF_BACKOFF_MS = "backoff_ms";
    public static final String CITY_MODEL_HISTORY = "city_model_history";


    public static PreferencesHelper getInstance(Context context){

        if(preferencesHelper == null){
            preferencesHelper = new PreferencesHelper(context);
        }
        return preferencesHelper;
    }

    public PreferencesHelper(Context context) {

        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    public boolean contains(String key){

        return this.sharedPreferences.contains(key);
    }

    public Long getLong(String key) {
        return sharedPreferences.getLong(key,0);
    }

    public void putLong(String key, Long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.commit();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, ""); // Get our string from prefs or return an empty string
    }

    public void putString(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    public float getFloat(String key) {
        return sharedPreferences.getFloat(key, 0F);
    }

    public void putFloat(String key, Float value) {
        prefsEditor.putFloat(key, value);
        prefsEditor.commit();
    }

    public void putBoolean(String key, Boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false); // Get our string from prefs or return an false
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public void putInt(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    public void clear() {
        prefsEditor.clear();
        prefsEditor.commit();
    }
}
