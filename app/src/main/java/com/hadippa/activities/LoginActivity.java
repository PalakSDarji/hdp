package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonclasses.connection.ConnectionDetector;
import com.commonclasses.connection.LibHttp;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.hadippa.AppConstants;
import com.hadippa.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {


    EditText edtUsername,edtPassword;
    LinearLayout linearFacebook;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    protected GoogleCloudMessaging gcm;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected String regId;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_login);

        sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = sp.edit();

        AppConstants.generateSHAKey(LoginActivity.this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        startRegistration();
        edtPassword = (EditText)findViewById(R.id.edtPass);
        edtUsername = (EditText)findViewById(R.id.edtUsername);
        linearFacebook = (LinearLayout)findViewById(R.id.linearFacebook);


        TextView signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        linearFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionDetector.isConnectedToInternet(LoginActivity.this)) {

                    loginButton.callOnClick();
                }
            }
        });

        Button login = (Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

                if (ConnectionDetector.isConnectedToInternet(LoginActivity.this)) {

                    new LoginFb("password",edtUsername.getText().toString().trim(),
                            edtPassword.getText().toString().trim(),"").execute();

                }

            }
        });

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("public_profile, email, user_birthday, user_friends");

        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                new LoginFb("facebook","","",loginResult.getAccessToken().getToken()).execute();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


            callbackManager.onActivityResult(requestCode, resultCode, data);

    }


    protected class LoginFb extends AsyncTask<String, Void, String> {

        String grant_type;
        String username;
        String password;
        String fbAccessToken;

        LoginFb(String grant_type,  String username, String password, String fbAccessToken){
            this.grant_type = grant_type;
            this.username = username;
            this.password = password;
            this.fbAccessToken = fbAccessToken;
        }

        String jsonStr;
        ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

           AppConstants.showProgressDialog(LoginActivity.this,getResources().getString(R.string.message_user_auth));
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                LibHttp libHttp = new LibHttp();

                jsonStr = libHttp.login(LoginActivity.this,grant_type, username, password, fbAccessToken,sp.getString("gcmId",""));

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

            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                if ((jsonObject.has("error")) || (jsonObject.has("error_description"))){

                    Toast.makeText(LoginActivity.this,jsonObject.has("error")+" :" +
                            " "+jsonObject.has("error_description"),Toast.LENGTH_LONG).show();

                }else{
                    Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            AppConstants.dismissDialog();

        }
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

    /**
     * Registers the application with GCM servers asynchronously. Stores the
     * registration ID and app versionCode in the application's shared
     * preferences.
     */
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


                        editor.putString("gcmId",regId);
                        editor.commit();



                    } else {

                    }

                }
            };
        }.execute(null, null, null);
    }



}
