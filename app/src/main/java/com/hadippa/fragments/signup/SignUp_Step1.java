package com.hadippa.fragments.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
public class SignUp_Step1 extends Fragment  {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1;
    private static final int INTENT_REQUEST_GET_N_IMAGES = 14;

    private Context mContext;

    Uri imageUri = null;
    public static ImageView foodImage;
    android.support.v4.app.FragmentManager fragmentManager = null;



    public static final int REQUEST_CODE_UPDATE_PIC = 0x1;

    public static SignUp_Step1 newInstance(int page, String title) {
        SignUp_Step1 fragmentFirst = new SignUp_Step1();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public static EditText edtFullname,edtPassword,edtConfirmPassword,edtEmail;
    RelativeLayout mainRel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up, null, false);

        mContext = getActivity();

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        mainRel = (RelativeLayout)view.findViewById(R.id.mainRel);
        edtFullname = (EditText)view.findViewById(R.id.edtFullName);
        edtPassword = (EditText)view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText)view.findViewById(R.id.edtConfirmPassword);
        edtEmail = (EditText)view.findViewById(R.id.edtEmail);

        TextView tvNext = (TextView)view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);
                if(validate()){

                    signUpStep_1();
                  //  SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);

                }


            }
        });

        return view;

    }

    boolean validate(){

        if(edtFullname.getText().toString().trim().length()==0){

            edtFullname.setError("Cannot be empty.");
            return false;

        }

        if(!edtFullname.getText().toString().trim().contains(" ")){

            edtFullname.setError("Enter fullname.");
            return false;

        }
        if(edtEmail.getText().toString().trim().length()==0){

            edtEmail.setError("Cannot be empty.");
            return false;

        }

        if(edtPassword.getText().toString().trim().length()==0){

            edtPassword.setError("Cannot be empty.");
            return false;

        }

        if(edtConfirmPassword.getText().toString().trim().length()==0){

            edtConfirmPassword.setError("Cannot be empty.");
            return false;

        }

        if(!edtPassword.getText().toString().trim().equals(edtConfirmPassword.getText().toString().trim())){

            edtConfirmPassword.setError("Password Mismatch");
            return false;

        }

        if(!AppConstants.isValidEmail(edtEmail.getText().toString().trim())){

            edtEmail.setError("Invalid email.");
            return false;
        }

        return  true;

    }


    private void signUpStep_1()
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

            String seperated[] = edtFullname.getText().toString().trim().split(" ");
            requestParams.add("first_name",seperated[0]);

            requestParams.add("last_name",seperated[1]);

            requestParams.add("password",edtPassword.getText().toString().trim());

            requestParams.add("confirm_password",edtConfirmPassword.getText().toString().trim());

            requestParams.add("email",edtEmail.getText().toString().trim());


            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_1, requestParams,
                new SignUpStep_1());
    }

    class SignUpStep_1 extends AsyncHttpResponseHandler
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

                    editor.putInt("user",jsonObject.getInt("user"));
                    editor.commit();

                      SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);

                }else {
                    AppConstants.showSnackBar(mainRel,"Could not submit details");
                }
                Log.d("async","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            AppConstants.showSnackBar(mainRel,"Could not submit details");

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async","failure"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","failure"+e.toString());
            }
        }

    }

}



