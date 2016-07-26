package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
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
import com.hadippa.AppConstants;
import com.hadippa.R;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {


    EditText edtUsername,edtPassword;
    LinearLayout linearFacebook;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorAccent));
        }

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

                    new LoginFb("facebook","","","").execute();

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

                    new LoginFb("password",edtUsername.getText().toString().trim(),edtPassword.getText().toString().trim(),"").execute();

                }

            }
        });


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

                jsonStr = libHttp.login(grant_type, username, password, fbAccessToken);

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

}
