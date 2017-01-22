package com.hadippa.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.Zodiac;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.hadippa.model.UserProfile;
import com.hadippa.utils.OnOkClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EditProfileActivity extends AppCompatActivity {

    @BindView(R.id.tvZodiac)
    TextView tvZodiac;

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


    private AlertDialog alertDialog;

    private List<Zodiac> zodiacList;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;


    @BindView(R.id.etName)
    CustomEditText etName;

    @BindView(R.id.etOccupation)
    CustomEditText etOccupation;

    @BindView(R.id.etCompany)
    CustomEditText etCompany;

    @BindView(R.id.etLiveIn)
    TextView etLiveIn;

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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        ButterKnife.bind(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);
        editor = sharedPreferences.edit();

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
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
        tvZodiac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> zodiacListStr = new ArrayList<String>();
              //  zodiacListStr.add(getString(R.string.str_dont_believe));
                zodiacListStr.add(getString(R.string.str_dont_know));
                for(Zodiac zodiac : zodiacList){
                    zodiacListStr.add(zodiac.getZodiacName());
                }
                showPopupList(EditProfileActivity.this, zodiacListStr, "Zodiac", new OnOkClickListener() {
                    @Override
                    public void onOkClick(String dataSelected) {

                        if(dataSelected.equalsIgnoreCase(getString(R.string.str_dont_believe))){
                            tvZodiac.setText(getString(R.string.str_dont_believe));
                        }
                        else if(dataSelected.equalsIgnoreCase(getString(R.string.str_dont_know))){
                            try {
                                findZodiac();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        else{
                            tvZodiac.setText(dataSelected);
                        }

                    }
                });
            }
        });
        etLang.setText(userBean.getLanuage_known());
        etEmail.setText(userBean.getEmail());
        etPhone.setText(String.valueOf(userBean.getMobile()));
        etGender.setText(userBean.getGender());
        etDateOfBirth.setText(AppConstants.formatDate(userBean.getDob(),"yyyy-MM-dd","dd-MM-yyyy"));
        etOccupation.setText(userBean.getOccupation());

        if(userBean.getPrivate_account() == 0){
            switchPrivateProfile.setChecked(false);
        }else{
            switchPrivateProfile.setChecked(true);
        }
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
/*

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
*/

        etLang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> dummyLangs = new ArrayList<String>();
                dummyLangs.add("Hindi");
                dummyLangs.add("Gujarati");
                dummyLangs.add("English");
                dummyLangs.add("Bengali");
                dummyLangs.add("Assamese");
                dummyLangs.add("Kannada");
                dummyLangs.add("Kashmiri");
                dummyLangs.add("Konkani");
                dummyLangs.add("Malayalam");
                dummyLangs.add("Manipuri");
                dummyLangs.add("Marathi");
                dummyLangs.add("Nepali");
                dummyLangs.add("Oriya");
                dummyLangs.add("Punjabi");
                dummyLangs.add("Sanskrit");
                dummyLangs.add("Sindhi");
                dummyLangs.add("Tamil");
                dummyLangs.add("Telugu");
                dummyLangs.add("Urdu");
                dummyLangs.add("Bodo");
                dummyLangs.add("Santhali");
                dummyLangs.add("Maithili");
                dummyLangs.add("Dogri");

                showPopupList(EditProfileActivity.this, dummyLangs, getString(R.string.language), new OnOkClickListener() {
                    @Override
                    public void onOkClick(String dataSelected) {
                        etLang.setText(""+ dataSelected);
                    }
                });
            }
        });

        etLiveIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    List<String> dummyData = new ArrayList<String>();

                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(EditProfileActivity.this);

                    String s = sp.getString("cities", "");

                    JSONObject jsonObject = new JSONObject(s);


                    JSONArray jsonArray = jsonObject.getJSONArray("city_list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                        dummyData.add(jsonObject1.getString("name"));

                    }

                    showPopupList(EditProfileActivity.this, dummyData, getString(R.string.country), new OnOkClickListener() {
                        @Override
                        public void onOkClick(String dataSelected) {
                            etLiveIn.setText("" + dataSelected);
                        }
                    });
                }catch (Exception e){

                }
            }
        });

        etGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> dummyData = new ArrayList<String>();
                dummyData.add("Male");
                dummyData.add("Female");
                showPopupList(EditProfileActivity.this, dummyData, getString(R.string.gender), new OnOkClickListener() {
                    @Override
                    public void onOkClick(String dataSelected) {
                        etGender.setText(""+ dataSelected);
                    }
                });
            }
        });

     /*   etDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });*/


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

        Calendar calendar = Calendar.getInstance();
        showDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showDate(int year, int month, int day) {

        //etDateOfBirth.setText(new StringBuilder().append(day).append("/").append(month).append("/").append(year));
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDateOfBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    //MY Profile
    private void editProfile() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            String firstnaem = "";String lastname = "";

            if(etName.getText().toString().contains(" ")){
                firstnaem = etName.getText().toString().split(" ")[0];
                lastname = etName.getText().toString().split(" ")[1];
            }else{
                firstnaem = etName.getText().toString();
                lastname = "";
            }


            requestParams.add("access_token", sharedPreferences.getString("access_token", ""));
            requestParams.add("id", String.valueOf(userBean.getId()));
            requestParams.add("first_name",firstnaem);
            requestParams.add("last_name",lastname);
            requestParams.add("occupation", etOccupation.getText().toString());
            requestParams.add("company", etCompany.getText().toString());
            requestParams.add("city", etLiveIn.getText().toString());
            requestParams.add("zodiac", tvZodiac.getText().toString());

            requestParams.add("lanuage_known", etLang.getText().toString());
            requestParams.add("mobile", etPhone.getText().toString());
            requestParams.add("gender", gender);
            requestParams.add("dob", AppConstants.formatDate(etDateOfBirth.getText().toString(),"dd-MM-yyyy","yyyy-MM-dd"));

            if(switchPrivateProfile.isChecked()){
                requestParams.add("profile_type", "1");
            }else{
                requestParams.add("profile_type", "0");
            }

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

                    editor.putString("user",jsonObject.getJSONObject("user").toString());
                    editor.commit();

                    Gson gson = new Gson();
                    UserProfile userProfile = gson.fromJson(response,UserProfile.class);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("data", userProfile.getUser());
                    setResult(Activity.RESULT_OK, resultIntent);
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
                etDateOfBirth.setText(newday + "-" + newmonth + "-" + year);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


    private void findZodiac() throws ParseException {

        //TODO : this birthdate is for xml demo purpose only as of now. Fill it with user's birthdate later while integrating APIs in the same format or change the format of date formater below.
        String birthDate = userBean.getDob();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

    private void showPopupList(Context context, final List<String> list, String title, final OnOkClickListener onOkClickListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.lvItems);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.item_city, R.id.txtName, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(alertDialog != null) alertDialog.cancel();
                onOkClickListener.onOkClick(list.get(position));
            }
        });

        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setView(view);

        alertDialog = builder.show();
    }
/*
    private void showPopupList(Context context, final List<Zodiac> list, String title, final OnOkClickListener onOkClickListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.lvItems);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.item_city, R.id.txtName, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(alertDialog != null) alertDialog.cancel();
                onOkClickListener.onOkClick(list.get(position));
            }
        });

        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setView(view);

        alertDialog = builder.show();
    }*/


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_edit_profile)),intent.getExtras().getString("messageData"));
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
