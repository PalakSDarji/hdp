package com.hadippa.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.APIClass;
import com.bumptech.glide.Glide;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.NightCLubModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CreateActivityActvity extends AppCompatActivity {

    static String[] suffixes =
            //    0     1     2     3     4     5     6     7     8     9
            {"th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    10    11    12    13    14    15    16    17    18    19
                    "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                    //    20    21    22    23    24    25    26    27    28    29
                    "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    30    31
                    "th", "st"};

    public static ArrayList<String> selectedList = new ArrayList<>();

    private int activityKey = 0;



    String hide_from = "public", notification = "1";

    @BindView(R.id.toggle2)
    ToggleButton toggle2;

    @BindView(R.id.profileImage)
    RoundedImageView profileImage;

    @BindView(R.id.address)
    CustomTextView address;

    @BindView(R.id.name)
    CustomTextView name;

    @BindView(R.id.rating)
    CustomTextView rating;

    /*@BindView(R.id.distance)
    CustomTextView distance;*/

    @BindView(R.id.time)
    CustomTextView time;

    @BindView(R.id.cvVisitingCard)
    CardView cvVisitingCard;
    @BindView(R.id.llNameAddress)
    LinearLayout llNameAddress;
    @BindView(R.id.llActivityBrief)
    LinearLayout llActivityBrief;
    @BindView(R.id.tvHeader)
    TextView tvHeader;

    @BindView(R.id.tvActivityName)
    TextView tvActivityName;
    @BindView(R.id.tvAddress)
    TextView tvAddress;
    @BindView(R.id.tvVisDate)
    TextView tvVisDate;
    @BindView(R.id.tvVisTime)
    TextView tvVisTime;
    @BindView(R.id.tvAvaTill)
    TextView tvAvaTill;
    @BindView(R.id.tvNotify)
    TextView tvNotify;

    @BindView(R.id.etActivityName) EditText etActivityName;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etActivityName)
    EditText etActivityName;
    @BindView(R.id.etAddress)
    EditText etAddress;

    @BindView(R.id.llPublicAndFollowing)
    LinearLayout llPublicAndFollowing;
    @BindView(R.id.llFollowing)
    LinearLayout llFollowing;
    @BindView(R.id.llPublic)
    LinearLayout llPublic;

    @BindView(R.id.radio0)
    RadioButton radio0;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;

    @BindView(R.id.tvVisitingDate)
    TextView tvVisitingDate;
    @BindView(R.id.tvVisitingTime)
    TextView tvVisitingTime;
    @BindView(R.id.tvAvailableTill)
    TextView tvAvailableTill;


    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog, timePickerDialog1;
    private SimpleDateFormat dateFormatter;

    @BindView(R.id.vSepAddress)
    View vSepAddress;
    @BindView(R.id.ivArrow)
    ImageView ivArrow;
    @BindView(R.id.ivLocation) ImageView ivLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actvity);
        ButterKnife.bind(this);

        selectedList.clear();
        dateFormatter = new SimpleDateFormat("MMM", Locale.US);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0);

        //  Log.d("getIntent().",getIntent().getExtras().getString("data"));
        Log.d("getIntent().", activityKey + "");
        Log.d("getIntent().", AppConstants.ACTIVITY_FROM_COFFEE + "");
        if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE) {


            NightCLubModel.ResponseBean.RestaurantsBean restaurantsBean =
                    (NightCLubModel.ResponseBean.RestaurantsBean) getIntent().getExtras().getSerializable("data");

            address.setText(restaurantsBean.getRestaurant().getLocation().getAddress());
            name.setText(restaurantsBean.getRestaurant().getName());
            rating.setText(restaurantsBean.getRestaurant().getUser_rating().getAggregate_rating());
            ((CustomTextView) (findViewById(R.id.distance))).setText(AppConstants.distanceMeasure(Double.parseDouble(getIntent().getExtras().getString("latitude")),
                    Double.parseDouble(getIntent().getExtras().getString("longitude")),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLatitude()),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLongitude())) + " kms");

            Glide.with(CreateActivityActvity.this)
                    .load(restaurantsBean.getRestaurant().getFeatured_image())
                    .into(profileImage);

            tvHeader.setText(getResources().getString(R.string.visiting_date_and_time));
            cvVisitingCard.setVisibility(View.VISIBLE);
            llNameAddress.setVisibility(View.GONE);
            llActivityBrief.setVisibility(View.GONE);

            tvVisDate.setText(getResources().getString(R.string.select_date));
            tvVisTime.setText(getResources().getString(R.string.select_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_CREATE_ACTIVITY) {

            tvHeader.setText(getResources().getString(R.string.create_activity));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.name_of_the_activity));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.VISIBLE);

        } else if (activityKey == AppConstants.ACTIVITY_HOBBY) {

            tvHeader.setText(getResources().getString(R.string.hobby));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.name_of_hobby));
            tvAddress.setText(getResources().getString(R.string.where));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            tvNotify.setText(getResources().getString(R.string.on_button_to_get_notify));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_TRAVEL_SCHEDULE) {

            tvHeader.setText(getResources().getString(R.string.travelling_date_and_time));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.GONE);
            llActivityBrief.setVisibility(View.VISIBLE);

            tvVisDate.setText(getResources().getString(R.string.select_date));
            tvVisTime.setText(getResources().getString(R.string.select_time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            tvNotify.setText(getResources().getString(R.string.want_to_receive_notification_for_same_activity));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_PARTY) {

            tvHeader.setText(getResources().getString(R.string.party));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.party_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_STANDUP_COMEDY) {

            tvHeader.setText(getResources().getString(R.string.standup_comedy));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.standup_comedy_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {

            tvHeader.setText(getResources().getString(R.string.indoor_game));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.game_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {

            tvHeader.setText(getResources().getString(R.string.outdoor_game));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.game_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
            ivArrow.setVisibility(View.GONE);
        } else if (activityKey == AppConstants.ACTIVITY_ENTERTAINMENT) {

            tvHeader.setText("Sultan (U/A)");
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
            etAddress.setVisibility(View.GONE);
            vSepAddress.setVisibility(View.GONE);
            ivLocation.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.select_theater));
            tvVisDate.setText(getResources().getString(R.string.date));
            tvVisTime.setText(getResources().getString(R.string.time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            ivArrow.setVisibility(View.GONE);
        }

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.llInvite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivityActvity.this, InviteToJoinActivity.class);
                intent.putExtra("selectedId",selectedList);
                startActivityForResult(intent,555);
            }
        });

        findViewById(R.id.tvPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               post();
            }
        });

        llPublicAndFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                hide_from = "public and following";
                radio0.setChecked(true);
                radio1.setChecked(false);
                radio2.setChecked(false);
            }
        });

        llFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hide_from = "following";
                radio0.setChecked(false);
                radio1.setChecked(true);
                radio2.setChecked(false);
            }
        });

        llPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hide_from = "public";
                radio0.setChecked(false);
                radio1.setChecked(false);
                radio2.setChecked(true);
            }
        });

        toggle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    notification = "1";
                }else{
                    notification = "0";
                }
            }
        });

        setDateTimePicker();

        tvVisitingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        tvVisitingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        tvAvailableTill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog1.show();
            }
        });

    }

    ArrayList<String> getSelectedList = new ArrayList<>();
    boolean aBoolean = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 555){

            selectedList = data.getStringArrayListExtra("selectedId");


            Log.d("selectedId >> 0*",selectedList.toString());

        }else{
            Log.d("selectedId >> 0*","req != 555");

        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if(aBoolean== true){
            selectedList =  getSelectedList;
            Log.d("selectedId >> 1*",selectedList.toString());
        }
    }

    private void setDateTimePicker() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                int day = newDate.get(Calendar.DAY_OF_MONTH);
                String dayStr = day + suffixes[day];

                String ddd = "";
                String newday = String.valueOf(day),newmonth = String.valueOf(monthOfYear+1);

                if(String.valueOf(monthOfYear).length() == 1){
                    newmonth = "0"+newmonth;
                }

                if(String.valueOf(day).length() == 1){
                    newday = "0"+day;
                }

                Log.d("date>>",year+"-"+newmonth+"-"+newday);
                tvVisitingDate.setText(year+"-"+newmonth+"-"+newday);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                String newHour = String.valueOf(selectedHour);
                String newMin = String.valueOf(selectedMinute);
                if(String.valueOf(newHour).length() == 1){
                    newHour = "0"+newHour;
                }

                if(String.valueOf(selectedMinute).length() == 1){
                    newMin = "0"+newMin;
                }

                tvVisitingTime.setText(newHour + ":" + newMin+":"+"00");

            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), true);

        timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

               /* String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";*/

                String newHour = String.valueOf(selectedHour);
                String newMin = String.valueOf(selectedMinute);
                if(String.valueOf(newHour).length() == 1){
                    newHour = "0"+newHour;
                }

                if(String.valueOf(selectedMinute).length() == 1){
                    newMin = "0"+newMin;
                }

                tvAvailableTill.setText(newHour + ":" + newMin+":"+"00");
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), true);
    }



    private void post() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CreateActivityActvity.this);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sharedPreferences.getString("access_token", ""));
            requestParams.add("activity_type", String.valueOf(getIntent().getExtras().getInt("activity_id")));
            requestParams.add("activity_name", name.getText().toString());
            requestParams.add("activity_date", tvVisitingDate.getText().toString() + " " + tvVisitingTime.getText().toString());
            requestParams.add("activity_location", address.getText().toString());
            requestParams.add("activity_location_lat", getIntent().getExtras().getString("latitude"));
            requestParams.add("activity_location_lon", getIntent().getExtras().getString("longitude"));
            if (getIntent().getExtras().getInt("activity_id")==1 || getIntent().getExtras().getInt("activity_id") == 2) {
                requestParams.add("cut_off_time", name.getText().toString());
            } else if (getIntent().getExtras().getInt("activity_id") == 3 ||
                    getIntent().getExtras().getInt("activity_id") == 4 ||
                    getIntent().getExtras().getInt("activity_id") == 5 ||
                    getIntent().getExtras().getInt("activity_id") == 6 ||
                    getIntent().getExtras().getInt("activity_id") == 7) {
                requestParams.add("available_till", tvAvailableTill.getText().toString());
            }

            requestParams.add("notification", notification);
            requestParams.add("hide_from", hide_from);
            Log.d("date>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CREATE_POST, requestParams,
                new Post());
    }

    class Post extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

              AppConstants.showProgressDialog(CreateActivityActvity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
           /* Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
*/
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                if(jsonObject.getBoolean("success")) {
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                }
                Log.d("date>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("date>>", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

}
