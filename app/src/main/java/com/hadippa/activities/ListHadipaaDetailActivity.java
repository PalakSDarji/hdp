package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ListHadipaaDetailActivity extends AppCompatActivity {

    private ImageView imageBack;
    CustomEditText edtCompanyWeb, edtCompany, edtNumber, edtEmail, edtName;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hadipaa_detail);

        sp = PreferenceManager.getDefaultSharedPreferences(ListHadipaaDetailActivity.this);
        editor = sp.edit();

        edtName = (CustomEditText) findViewById(R.id.edtName);
        edtEmail = (CustomEditText) findViewById(R.id.edtEmail);
        edtNumber = (CustomEditText) findViewById(R.id.edtNumber);
        edtCompany = (CustomEditText) findViewById(R.id.edtCompany);
        edtCompanyWeb = (CustomEditText) findViewById(R.id.edtCompanyWeb);

        ((CustomTextView) findViewById(R.id.title)).setText(getIntent().getExtras().getString("category"));
        imageBack = (ImageView) findViewById(R.id.imageBack);
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

                if (edtName.getText().toString().trim().equals("")) {
                    Snackbar.make(((RelativeLayout) findViewById(R.id.activity_list_hadipaa_detail)), "Name is mandatory", Snackbar.LENGTH_LONG).show();
                    return;

                }

                if (edtEmail.getText().toString().trim().equals("")) {
                    Snackbar.make(((RelativeLayout) findViewById(R.id.activity_list_hadipaa_detail)), "Email is mandatory", Snackbar.LENGTH_LONG).show();
                    return;

                }

                if (edtNumber.getText().toString().trim().equals("")) {
                    Snackbar.make(((RelativeLayout) findViewById(R.id.activity_list_hadipaa_detail)), "Mobile number is mandatory", Snackbar.LENGTH_LONG).show();
                    return;

                }

                if (edtNumber.getText().toString().trim().length() != 10) {
                    Snackbar.make(((RelativeLayout) findViewById(R.id.activity_list_hadipaa_detail)), "Mobile number must be 10 digits.", Snackbar.LENGTH_LONG).show();
                    return;

                }

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
            requestParams.add("company_name", edtCompany.getText().toString().trim());
            requestParams.add("company_website", edtCompanyWeb.getText().toString().trim());
            requestParams.add("mobile", edtNumber.getText().toString().trim());
            requestParams.add("activity_category", getIntent().getExtras().getString("category"));

            Log.d("myplan>> req", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.LIST_WITH_HADIPAA, requestParams,
                new PostListWithHadipaa());
    }

    public KProgressHUD hud;

    public void showProgressDialog(Context context, String message) {
        // PROGRESS_DIALOG.show();

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(context.getResources().getColor(R.color.back_progress))

                .setLabel(message)
                .setDimAmount(0.5f)
                .setCancellable(true)
                .setAnimationSpeed(2);

        if(hud!=null){
            hud.show();
        }
    }

    public void dismissDialog() {

        if (hud != null) hud.dismiss();
    }
    
    class PostListWithHadipaa extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            showProgressDialog(ListHadipaaDetailActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
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
                if (jsonObject.getBoolean("success")) {
                    Toast.makeText(ListHadipaaDetailActivity.this, "Thank you for listing with Hadipaa :)", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(ListHadipaaDetailActivity.this, HomeScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ListHadipaaDetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(), intent.getExtras().getString("messageData"));
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
