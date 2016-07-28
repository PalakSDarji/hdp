package com.hadippa.fragments.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.HomeScreen;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step4 extends Fragment implements View.OnClickListener {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ImageButton imageBoy, imageGirl;
    ImageView imageGirlTick, imageBoyTick;
    TextView tvImBoy, tvImGirl;
    LinearLayout linearBoy, linearGirl;

    String gender = "";

    public static SignUp_Step4 newInstance(int page, String title) {
        SignUp_Step4 fragmentFirst = new SignUp_Step4();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up_4, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        tvImBoy = (TextView) view.findViewById(R.id.tvImBoy);
        tvImGirl = (TextView) view.findViewById(R.id.tvImGirl);

        imageGirlTick = (ImageView) view.findViewById(R.id.imageGirlTick);
        imageBoyTick = (ImageView) view.findViewById(R.id.imageBoyTick);

        imageBoy = (ImageButton) view.findViewById(R.id.imageBoy);
        imageGirl = (ImageButton) view.findViewById(R.id.imageGirl);

        linearBoy = (LinearLayout) view.findViewById(R.id.linearBoy);
        linearGirl = (LinearLayout) view.findViewById(R.id.linearGirl);

        linearBoy.setOnClickListener(this);
        linearGirl.setOnClickListener(this);

        TextView tvNext = (TextView) view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gender.equals("")) {


                } else {
                    signUpStep_3();

                }
               /* Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_out, R.anim.slide_left_in);*/
            }
        });


        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearBoy:

                gender = "male";
                tvImBoy.setTextColor(getResources().getColor(R.color.pink_text));
                tvImGirl.setTextColor(Color.parseColor("#6f6d6e"));
                imageBoyTick.setVisibility(View.VISIBLE);
                imageGirlTick.setVisibility(View.INVISIBLE);

                imageBoy.setImageResource(R.drawable.signup4_boyicon_selected);
                imageGirl.setImageResource(R.drawable.signup4_girlicon);

                imageBoy.setBackgroundResource(R.drawable.rounded_profile_gender);
                imageGirl.setBackgroundResource(R.drawable.rounded_profile_gender_deselect);

                break;

            case R.id.linearGirl:

                gender = "female";
                tvImGirl.setTextColor(getResources().getColor(R.color.pink_text));
                tvImBoy.setTextColor(Color.parseColor("#6f6d6e"));
                imageBoyTick.setVisibility(View.INVISIBLE);
                imageGirlTick.setVisibility(View.VISIBLE);

                imageBoy.setImageResource(R.drawable.signup4_boyicon);
                imageGirl.setImageResource(R.drawable.signup4_girlicon_selected);

                imageGirl.setBackgroundResource(R.drawable.rounded_profile_gender);
                imageBoy.setBackgroundResource(R.drawable.rounded_profile_gender_deselect);

                break;


        }
    }

    private void signUpStep_3() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("client_id", AppConstants.CLIENT_ID);

            requestParams.add("client_secret", AppConstants.CLIENT_SECRET);

            requestParams.add("device_id", Settings.Secure.getString(getActivity().getContentResolver(),
                    Settings.Secure.ANDROID_ID));

            requestParams.add("device_os_version", String.valueOf(Build.VERSION.SDK_INT));

            requestParams.add("grant_type", "password");

            requestParams.add("device_type", "android");

            requestParams.add("device_token", sp.getString("gcmId", ""));

            requestParams.add("id", String.valueOf(sp.getInt("user", -1)));

            requestParams.add("gender", gender);

            requestParams.add("dob", SignUp_Step3.date.getText().toString().trim());

            requestParams.add("username", SignUp_Step1.edtEmail.getText().toString().trim());

            requestParams.add("password", SignUp_Step1.edtPassword.getText().toString().trim());


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

            asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SIGN_UP_STEP_3, requestParams,
                    new Update_Gender_DOB());

    }

    class Update_Gender_DOB extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(getActivity(), "Please Wait");

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

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("async_step_2", "success" + response);
                if(jsonObject.has("access_token")){

                    Log.d("async_step_2>>>",jsonObject.getJSONArray("posts").toString());
                   /* editor.putInt("user",jsonObject.getInt("user"));
                    editor.commit();*/

                    Intent intent = new Intent(getActivity(), HomeScreen.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {


            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("async", "failure" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "failure" + e.toString());
            }
        }

    }

}



