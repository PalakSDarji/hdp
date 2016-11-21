package com.hadippa.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.Zodiac;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.hadippa.model.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.llRadio0)
    LinearLayout llRadio0;

    @BindView(R.id.llRadio1)
    LinearLayout llRadio1;

    @BindView(R.id.radio0)
    ImageView radio0;

    @BindView(R.id.radio1)
    ImageView radio1;

    @BindView(R.id.vSepZodiac)
    View vSepZodiac;

    private List<Zodiac> zodiacList;


    @BindView(R.id.etName)
    CustomEditText etName;

    @BindView(R.id.etOccupation)
    CustomEditText etOccupation;

    @BindView(R.id.etCompany)
    CustomEditText etCompany;

    @BindView(R.id.etLiveIn)
    CustomEditText etLiveIn;

    @BindView(R.id.tvZodiac)
    CustomTextView tvZodiac;

    @BindView(R.id.etLang) TextView etLang;
    @BindView(R.id.etEmail) CustomEditText etEmail;
    @BindView(R.id.etPhone) CustomEditText etPhone;
    @BindView(R.id.etGender) TextView etGender;
    @BindView(R.id.etDateOfBirth) TextView etDateOfBirth;

    @BindView(R.id.switchPrivateProfile)
    Switch switchPrivateProfile;

    @BindView(R.id.ivSave)
    ImageView ivSave;
    UserProfile.UserBean userBean;

    private DatePickerDialog datePickerDialog;

    String gender = "male";

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

        setDateTimePicker();
        etName.setText(userBean.getFirst_name()+" "+userBean.getLast_name());
        etCompany.setText(userBean.getCompany());
        etLiveIn.setText(userBean.getCity());
        tvZodiac.setText(userBean.getZodiac());
        etLang.setText(userBean.getLanuage_known());
        etEmail.setText(userBean.getEmail());
        etPhone.setText(String.valueOf(userBean.getMobile()));
        etGender.setText(userBean.getGender());
        etDateOfBirth.setText(userBean.getDob()+"");
        etOccupation.setText(userBean.getOccupation());

        etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        etDateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    datePickerDialog.show();
                }
            }
        });

        ivSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        llRadio0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setSelected(true);
                radio1.setSelected(false);
                tvZodiac.setVisibility(View.GONE);
                vSepZodiac.setVisibility(View.GONE);

            }
        });

        llRadio1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radio0.setSelected(false);
                radio1.setSelected(true);
                tvZodiac.setVisibility(View.VISIBLE);
                vSepZodiac.setVisibility(View.VISIBLE);
                try {
                    findZodiac();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        zodiacList = new ArrayList<>();
        zodiacList.add(new Zodiac("Aries","March 21","April 19"));
        zodiacList.add(new Zodiac("Taurus","April 20","May 20"));
        zodiacList.add(new Zodiac("Gemini","May 21","June 20"));
        zodiacList.add(new Zodiac("Cancer","June 21","July 22"));
        zodiacList.add(new Zodiac("Leo","July 23","August 22"));
        zodiacList.add(new Zodiac("Virgo","August 23","September 22"));
        zodiacList.add(new Zodiac("Libra","September 23","October 22"));
        zodiacList.add(new Zodiac("Scorpio","October 23","November 21"));
        zodiacList.add(new Zodiac("Sagittarius","November 22","December 21"));
        zodiacList.add(new Zodiac("Capricorn","December 22","January 19"));
        zodiacList.add(new Zodiac("Aquarius","January 20","February 18"));
        zodiacList.add(new Zodiac("Pisces","February 19","March 20"));

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
            requestParams.add("zodiac", tvZodiac.getText().toString());
            requestParams.add("lanuage_known", etLang.getText().toString());
            requestParams.add("mobile", etPhone.getText().toString());
            requestParams.add("gender", gender);
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


                JSONObject jsonObject = new JSONObject(response);

                if(jsonObject.getBoolean("success")){

                  /*  Gson gson = new Gson();
                    UserProfile userProfile = gson.fromJson(response,UserProfile.class);
*/
                   /* Intent resultIntent = new Intent();
                    resultIntent.putExtra("data", userProfile.getUser());
                    setResult(Activity.RESULT_OK, resultIntent);*/
                    finish();
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

                }else{
                    Toast.makeText(EditProfileActivity.this,"Failed Update",Toast.LENGTH_SHORT).show();
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

    private void setDateTimePicker() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                int day = newDate.get(Calendar.DAY_OF_MONTH);


                String ddd = "";
                String newday = String.valueOf(day), newmonth = String.valueOf(monthOfYear + 1);

                if (String.valueOf(monthOfYear).length() == 1) {
                    newmonth = "0" + newmonth;
                }

                if (String.valueOf(day).length() == 1) {
                    newday = "0" + day;
                }

                Log.d("date>>", year + "-" + newmonth + "-" + newday);
                etDateOfBirth.setText(year + "-" + newmonth + "-" + newday);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }





    private void findZodiac() throws ParseException {

        //TODO : this birthdate is for xml demo purpose only as of now. Fill it with user's birthdate later while integrating APIs in the same format or change the format of date formater below.
        String birthDate = "12/05/1992";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");

        Date date = null;
        try {
            date = simpleDateFormat.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }//asd

        if(date == null){
            //TODO print some msg about failure of the process. otherwise continue
            Toast.makeText(getApplicationContext(),"Something went wrong!", Toast.LENGTH_LONG).show();
            return;
        }

        for(Zodiac zodiac : zodiacList){
            Log.v("Zodiac1"," Zodiac1 : " + zodiac);

            Date startDate = sdf.parse(zodiac.getStartDate());
            Date endDate = sdf.parse(zodiac.getEndDate());

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(startDate);
            calendarStart.set(Calendar.YEAR,0);

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(endDate);
            calendarEnd.set(Calendar.YEAR,0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR,0);

            Log.v("Zodiac1"," date : " + calendarStart.getTime()+ ", "+ calendarEnd.getTime() + ", "+ calendar.getTime());
            if(calendar.getTime().after(calendarStart.getTime()) && calendar.getTime().before(calendarEnd.getTime())){

                Log.v("Zodiac1","enter");
                tvZodiac.setText(""+zodiac.getZodiacName());
                break;
            }
        }

    }
}
