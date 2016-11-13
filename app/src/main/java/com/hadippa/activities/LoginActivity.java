package com.hadippa.activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commonclasses.connection.ConnectionDetector;
import com.commonclasses.connection.LibHttp;
import com.commonclasses.notification.ServerUtilities;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hadippa.AppConstants;
import com.hadippa.Hadippa;
import com.hadippa.R;
import com.hadippa.model.DataModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    EditText edtUsername,edtPassword;
    LinearLayout linearFacebook;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    protected GoogleCloudMessaging gcm;
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    protected String regId;
    public static List<DataModel> posts;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RelativeLayout mainRel;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(LoginActivity.this);
        setContentView(R.layout.activity_login);

        LoginActivityPermissionsDispatcher.showAllPermissionWithCheck(this);
        sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        editor = sp.edit();

        AppConstants.generateSHAKey(LoginActivity.this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

        mainRel = (RelativeLayout)findViewById(R.id.mainRel);
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

        TextView login = (TextView)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();*/

                if(edtUsername.getText().toString().trim().equals("") ||
                edtPassword.getText().toString().trim().equals("")){

                    if(edtUsername.getText().toString().trim().equals("")){
                        edtUsername.setError("Cannot be empty");
                        return;
                    }

                    if(edtPassword.getText().toString().trim().equals("")){
                        edtPassword.setError("Cannot be empty");
                        return;
                    }


                }else {
                    if(AppConstants.isValidEmail(edtUsername.getText().toString().trim())){
                    if (ConnectionDetector.isConnectedToInternet(LoginActivity.this)) {

                        login("password", edtUsername.getText().toString().trim(),
                                edtPassword.getText().toString().trim(), "");
                    }
                }else{
                        edtUsername.setError("Invalid Email.");
                        return;
                    }
                }
            }
        });

        loginButton = (LoginButton) findViewById(R.id.fb_login_button);
        loginButton.setReadPermissions("public_profile, email, user_birthday, user_friends");

        callbackManager = CallbackManager.Factory.create();


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

              /*  GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {

                                Log.v("LoginActivity", response.toString());
                                try {

                                    JSONObject fbResponse = new JSONObject(String.valueOf(response.getJSONObject()));
                                    Log.v("LoginActivity", fbResponse.toString());

                                    login("facebook","","",fbResponse.getString("id"));



                                } catch (JSONException e) {

                                    Log.v("LoginActivity", e.toString());
                                    e.printStackTrace();

                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,link");
                request.setParameters(parameters);
                request.executeAsync();*/

                login("facebook","","",loginResult.getAccessToken().getToken());

              //  LoginManager.getInstance().logOut();
                Log.d("accesstoken>>",loginResult.getAccessToken().getToken());
             //   new LoginFb("facebook","","",loginResult.getAccessToken().getToken()).execute();

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {

            }
        });

        activityType();
        if(sp.getBoolean("loginStatus",false)){

            if(sp.getString("grant_type","password").equals("password")){
                login("password", sp.getString("username",""),
                            sp.getString("password",""), "");
            }else{
                login("facebook","","", sp.getString("code",""));
            }
        }

    }

    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showAllPermission() {
        Log.d(TAG, " showAll permission ");
        Hadippa.getApplicationCreds(this);
    }

    @OnShowRationale({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showRationaleForAllPermission(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage(R.string.permission_rationale)
                .setPositiveButton(R.string.button_allow, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton(R.string.button_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showDeniedForAllPermission() {
        Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showNeverAskForAllPermission() {
        Toast.makeText(this, R.string.permission_neverask, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
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

    String grant = "",code = "",username = "",password_ = "";
    private void login(String grant_type, String email ,
                       String password, String accessTokenFb)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        grant = grant_type;
        code = accessTokenFb;
        username = email;
        password_ = password;
        try
        {

            requestParams.add( "client_id", AppConstants.CLIENT_ID);

            requestParams.add("client_secret", AppConstants.CLIENT_SECRET);

            requestParams.add("device_id", Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID));

            requestParams.add("device_os_version", String.valueOf(Build.VERSION.SDK_INT));

            requestParams.add("grant_type",grant_type);

            requestParams.add( "device_type","android");

            requestParams.add("device_token",sp.getString("gcmId",""));

            if(grant_type.equals("facebook")){

                requestParams.add("code", accessTokenFb);

                editor.putString("code",accessTokenFb);
                editor.commit();

            }else {
                requestParams.add( "username", email);

                requestParams.add( "password", password);
            }



            Log.d("request>>",requestParams.toString());
            Log.d("request>>",requestParams.toString());
            Log.d("request>>",AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.LOGIN);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.LOGIN, requestParams,
                new LoginAsync());
    }

    class LoginAsync extends AsyncHttpResponseHandler
    {

        @Override
        public void onStart()
        {
            super.onStart();

            AppConstants.showProgressDialog(LoginActivity.this, "Please Wait");

        }


        @Override
        public void onFinish()
        {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async>>",response);
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("access_token")) {

                    Log.d("async`>>",response);
                    //post json stored g\here
                    editor.putBoolean("loginStatus",true);
                    editor.putString("grant_type",grant);
                    editor.putString("userData",jsonObject.getJSONObject("user").toString());
                    editor.putInt("following",jsonObject.getInt("following"));
                    editor.putInt("follower",jsonObject.getInt("follower"));
                    if(grant.equals("facebook")){
                        editor.putString("code",code);
                    }else {
                        editor.putString("username",username);
                        editor.putString("password",password_);
                    }
                    editor.commit();

                    if(jsonObject.has("registration_incomplete")){
                        editor.putInt("user",Integer.parseInt(jsonObject.getString("user")));
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this, SignUp.class);
                        intent.putExtra("type","incomplete");
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }else {
                        editor.putString("access_token", jsonObject.getString("access_token"));
                        editor.putString("posts", jsonObject.getString("posts"));
                        editor.commit();

                        ServerUtilities.register(LoginActivity.this, "", "", sp.getString("gcmId", ""), AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.LOGIN);
                        Intent intent = new Intent(LoginActivity.this, HomeScreen.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }
                }else{

                    AppConstants.showSnackBar(mainRel,"Invalid username or password");

                }
                    Log.d("async>>","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async>>","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.d("async>>","success exc  >>"+ error.toString());
            AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    private void activityType() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.ACTIVITY_TYPE, requestParams,
                new FetchActivityType());
    }

    class FetchActivityType extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(LoginActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                editor.putString("activityType",jsonObject.toString());
                editor.commit();
                Log.d("activityType", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }

    }

}
