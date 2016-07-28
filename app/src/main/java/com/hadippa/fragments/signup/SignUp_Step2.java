package com.hadippa.fragments.signup;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.commonclasses.receivers.IncomingSmsReceiver;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.SignUp;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step2 extends Fragment  {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    EditText edtPhoneNumber,edtOtp;
    TextView resendOtp;

    public static SignUp_Step2 newInstance(int page, String title) {
        SignUp_Step2 fragmentFirst = new SignUp_Step2();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up_2, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        TextView tvNext = (TextView)view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtOtp.getText().toString().trim().equals("")){
                    edtOtp.setError("Cannot be empty");
                }else{
                   signUpStep_2("verify_otp");
                }

               /* if( edtPhoneNumber.getText().toString().length()== 14 ){
                SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);*/
          //  }
            }
        });

        resendOtp = (TextView)view.findViewById(R.id.edtPhoneNumber);
        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = edtPhoneNumber.getText().toString().trim();

                if(s1.length() == 10){
                    signUpStep_2("receive_otp_resend");
                }else{
                    edtPhoneNumber.setError(getString(R.string.phone_error));
                }

            }
        });
        edtPhoneNumber = (EditText)view.findViewById(R.id.edtPhoneNumber);

        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String s1 = edtPhoneNumber.getText().toString().trim();
                if(checkPermission() && s1.length() == 10 ){
                    receiveOtp();

                }else if(!checkPermission() && s1.length() == 10){

                        requestPermission();


                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtOtp = (EditText)view.findViewById(R.id.edtOtp);


        return view;

    }



    private void signUpStep_2(String type)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try
        {

            requestParams.add( "client_id", AppConstants.CLIENT_ID);

            requestParams.add("client_secret", AppConstants.CLIENT_SECRET);

            requestParams.add("device_id", Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));

            requestParams.add("device_os_version", String.valueOf(Build.VERSION.SDK_INT));

            requestParams.add("grant_type","password");

            requestParams.add( "device_type","android");

            requestParams.add("device_token",sp.getString("gcmId",""));

            requestParams.add("id", String.valueOf(sp.getInt("user",-1)));

            if(type.equals("receive_otp") || type.equals("receive_otp_resend")){

                requestParams.add("mobile",edtPhoneNumber.getText().toString().trim());

            }else if(type.equals("verify_otp")){

                requestParams.add("code",edtOtp.getText().toString().trim());

            }



            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(type.equals("receive_otp")) {
            asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_2, requestParams,
                    new ReceiveOTP());
        }else if(type.equals("verify_otp")){
            asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_2_VERIFY_OTP, requestParams,
                    new VerifyOTP());
        }else if(type.equals("receive_otp_resend")){
            asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_2_RESEND_OTP, requestParams,
                    new ReceiveOTP());
    }
    }

    class ReceiveOTP extends AsyncHttpResponseHandler
    {

        @Override
        public void onStart()
        {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(getActivity(), "Please Wait");

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

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {




            try {
                String response = new String(responseBody, "UTF-8");
               /* JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getBoolean("success")){

                    editor.putInt("user",jsonObject.getInt("user"));
                    editor.commit();

                    SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);

                }*/
                Log.d("async_step_2","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {



            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async","failure"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","failure"+e.toString());
            }
        }

    }

    class VerifyOTP extends AsyncHttpResponseHandler
    {

        @Override
        public void onStart()
        {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(getActivity(), "Please Wait");

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

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {




            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.getBoolean("success")){

                    SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);

                }else{
                    edtOtp.setError("Invalid OTP");
                }
                Log.d("async_step_2","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {



            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async","failure"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","failure"+e.toString());
            }
        }

    }


    private boolean checkPermission() {

        int READ_SMS = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_SMS);

        if ( READ_SMS == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            //   requestPermission();
            return false;

        }
    }

    private void requestPermission() {


        requestPermissions( new String[]{Manifest.permission.READ_SMS}, 15);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 15:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    enableBroadcastReceiver();

                } else {

                    disableBroadcastReceiver();

                }

                receiveOtp();
                Log.d("SmsReceiver", "Enabled");
               // Toast.makeText(getActivity(),""+grantResults[0],Toast.LENGTH_LONG).show();
                break;

        }
    }


    void receiveOtp(){

        String s1 = edtPhoneNumber.getText().toString().trim();


        if(s1.length() == 10){
            signUpStep_2("receive_otp");
        }else{
            edtPhoneNumber.setError(getString(R.string.phone_error));
        }


    }


    public void enableBroadcastReceiver() {

        ComponentName receiver = new ComponentName(getActivity(), IncomingSmsReceiver.class); //created SMSLog class above!
        PackageManager pm = getActivity().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        Log.d("SmsReceiver", "Enabled");
    }
    
    public void disableBroadcastReceiver() {
        ComponentName receiver = new ComponentName(getActivity(), IncomingSmsReceiver.class);
        PackageManager pm = getActivity().getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        Log.d("SmsReceiver", "Disabled");

    }


}



