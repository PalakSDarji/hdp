/*
package com.hadippa.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hadippa.AppConstants;

import java.io.IOException;

public class BaseActivity extends FragmentActivity {

    protected String deviceId;

    protected GoogleCloudMessaging gcm;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected String regId;


    protected void initData() {

        deviceId = Settings.Secure.getString(this.getContentResolver(),
                Settings.Secure.ANDROID_ID);

    }
    protected void initUi() {
        //setDefaultFont();
    }


    protected void startRegistration() {

        if (checkPlayServices()) {
            // If this check succeeds, proceed with normal processing.
            // Otherwise, prompt user to get valid Play Services APK.
            if(AppConstants.DEBUG) Log.i(AppConstants.DEBUG_TAG, "Google Play Services OK");
            gcm = GoogleCloudMessaging.getInstance(getApplicationContext());

            if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "regId : "+regId);
            registerInBackground();
        }else{
            if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "No valid Google Play Services APK found.");

        }

    }

    protected boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getApplicationContext());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "No Google Play Services...Get it from the store.");
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        AppConstants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    */
/**
     * Registers the application with GCM servers asynchronously. Stores the
     * registration ID and app versionCode in the application's shared
     * preferences.
     *//*

    protected void registerInBackground() {

        new AsyncTask<Void, Void, String>() {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();


            }

            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
                    }
                    regId = gcm.register(AppConstants.SENDER_ID);
                    msg = "Device registered, registration ID=" + regId;

                } catch (IOException ex) {
                    msg = ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "onPostExecute : " + msg);



                if (!msg.equalsIgnoreCase("SERVICE_NOT_AVAILABLE")) {

                    Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("server_response", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);

                } else {



                }
            }

            // Define the Handler that receives messages from the thread and
            // update the progress
            private final Handler handler = new Handler() {

                public void handleMessage(Message msg) {

                    String aResponse = msg.getData().getString(
                            "server_response");

                    if ((null != aResponse)) {

                        if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, " sendRegistrationIdToBackend();");

                        libFile.setGcmId(regId);

                        onGcmRegistered();

                    } else {
                        showCustomMessage(getString(R.string.msg_something_failed));
                    }

                }
            };
        }.execute(null, null, null);
    }

    protected void onGcmRegistered(){

    }

    protected class LoginEmail extends AsyncTask<String, Void, String> {

        String email;
        String password;
        String deviceToken;

        LoginEmail(String email, String password, String deviceToken){
            this.email = email;
            this.password = password;
            this.deviceToken = deviceToken;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog(getString(R.string.msg_logging_in));

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                LibHttp libHttp = new LibHttp();

                String jsonStr = libHttp.loginEmail(email, password, deviceId, deviceToken);

                if (AppConstants.DEBUG) Log.d(AppConstants.DEBUG_TAG, "Response: > " + jsonStr);

                return jsonStr;
            } catch (Exception e) {
                e.printStackTrace();
                return "not-executed";
            }

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            cancelProgressDialog();

            if (!result.equalsIgnoreCase("not-executed")) {

                user = new Gson().fromJson(result, User.class);

                if(user.getStatusCode()==1){

                    //User user = new Gson().fromJson(result, User.class);

                    PrefUtils.setCurrentUser(user, getApplicationContext());

                    libFile.setUserName(email);

                    libFile.setUserPassword(password);

                    if(AppConstants.DEBUG)Log.i(AppConstants.DEBUG_TAG, "token:"+user.getUserToken());

                    Toast.makeText(BaseActivity.this, "Welcome " + user.getName(), Toast.LENGTH_LONG).show();

                    goToMainActivity();

                }else if(user.getStatusCode()==0){
                    showCustomMessage(getString(R.string.msg_something_failed));
                }else if(user.getStatusCode()==9 || user.getStatusCode()==10){
                    showCustomMessage(getString(R.string.msg_invalid_email_or_pwd));
                }

            }
        }
    }

}
*/
