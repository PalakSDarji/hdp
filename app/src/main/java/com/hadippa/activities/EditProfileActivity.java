package com.hadippa.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.R;
import com.hadippa.model.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.etName)
    CustomEditText etName;

    @BindView(R.id.etOccupation)
    CustomEditText etOccupation;

    @BindView(R.id.etCompany)
    CustomEditText etCompany;

    @BindView(R.id.etLiveIn)
    CustomEditText etLiveIn;

    @BindView(R.id.etZodiac) CustomEditText etZodiac;

    @BindView(R.id.etLang) CustomEditText etLang;
    @BindView(R.id.etEmail) CustomEditText etEmail;
    @BindView(R.id.etPhone) CustomEditText etPhone;
    @BindView(R.id.etGender) CustomEditText etGender;
    @BindView(R.id.etDateOfBirth) CustomEditText etDateOfBirth;

    @BindView(R.id.switchPrivateProfile)
    Switch switchPrivateProfile;
    UserProfile.UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        userBean = (UserProfile.UserBean) getIntent().getSerializableExtra("data");
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etName.setText(userBean.getFirst_name()+" "+userBean.getLast_name());
        etCompany.setText(userBean.getCompany());
        etLiveIn.setText(userBean.getCity());
        etZodiac.setText(userBean.getZodiac());
        etLang.setText(userBean.getLanuage_known());
        etEmail.setText(userBean.getEmail());
        etPhone.setText(String.valueOf(userBean.getMobile()));
        etGender.setText(userBean.getGender());
        etDateOfBirth.setText(userBean.getDob()+"");
        etOccupation.setText(userBean.getOccupation());
    }

    //MY Profile
    private void editProfile() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
            requestParams.add("access_token", sharedPreferences.getString("access_token", ""));
            requestParams.add("id", String.valueOf(userBean.getId()));
            requestParams.add("occupation", etOccupation.getText().toString());
            requestParams.add("company", etCompany.getText().toString());
            requestParams.add("city", etLiveIn.getText().toString());
            requestParams.add("zodiac", etZodiac.getText().toString());
            requestParams.add("lanuage_known", etLang.getText().toString());
            requestParams.add("mobile", etPhone.getText().toString());
            requestParams.add("gender", etGender.getText().toString());
            requestParams.add("dob", etDateOfBirth.getText().toString());

            Log.d("fetchProfile>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.EDIT_PROFILE, requestParams,
                new EditProfile());
    }

    class EditProfile extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(EditProfileActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
          /*  Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
*/
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Gson gson = new Gson();
                UserProfile userProfile = gson.fromJson(response,UserProfile.class);

                if(userProfile.isSuccess()){


                }

                Log.d("fetchProfile>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }


    }


}
