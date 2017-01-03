package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ListHadipaaDetailActivity extends AppCompatActivity {

    private ImageView imageBack;
    CustomEditText edtCompanyWeb,edtCompany,edtNumber,edtEmail,edtName;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hadipaa_detail);

        sp = PreferenceManager.getDefaultSharedPreferences(ListHadipaaDetailActivity.this);
        editor = sp.edit();

        edtName = (CustomEditText)findViewById(R.id.edtName);
        edtEmail = (CustomEditText)findViewById(R.id.edtEmail);
        edtNumber = (CustomEditText)findViewById(R.id.edtNumber);
        edtCompany = (CustomEditText)findViewById(R.id.edtCompany);
        edtCompanyWeb = (CustomEditText)findViewById(R.id.edtCompanyWeb);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.tvSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               listWithHadipaa();
            }
        });
    }


    //Get My Plans
    private void listWithHadipaa() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

           /* "access_token(*)
            name:(*)
            email:(*)
            company_name:(*)
            company_website:(*)
            mobile:(*)"*/


            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("name", edtName.getText().toString().trim());
            requestParams.add("email", edtEmail.getText().toString().trim());
            requestParams.add("company_name",edtCompany.getText().toString().trim());
            requestParams.add("company_website", edtCompanyWeb.getText().toString().trim());
            requestParams.add("mobile", edtNumber.getText().toString().trim());

            Log.d("myplan>> req", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.LIST_WITH_HADIPAA, requestParams,
                new PostListWithHadipaa());
    }

    class PostListWithHadipaa extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(ListHadipaaDetailActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("<<async>>", response);
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getBoolean("success")){
                    finish();
                }else{
                    Toast.makeText(ListHadipaaDetailActivity.this,"Failed",Toast.LENGTH_SHORT).show();












                }

                Log.d("<<async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("<<async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

}
