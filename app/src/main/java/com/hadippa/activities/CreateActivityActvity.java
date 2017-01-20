package com.hadippa.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.APIClass;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
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
import java.util.List;
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

    public ArrayList<String> selectedList = new ArrayList<>();
    public ArrayList<String> customList = new ArrayList<>();

    private int activityKey = 0;


    String hide_from = "public", notification = "1";
/*
    @BindView(R.id.toggle2)
    ToggleButton toggle2;*/

    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;

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
    @BindView(R.id.llCustom)
    LinearLayout llCustom;


    @BindView(R.id.radio0)
    ImageView radio0;
    @BindView(R.id.radio1)
    ImageView radio1;
    @BindView(R.id.radio2)
    ImageView radio2;
    @BindView(R.id.radio3)
    ImageView radio3;

    @BindView(R.id.spnAvail)
    Spinner spnAvail;
    @BindView(R.id.tvVisitingDate)
    TextView tvVisitingDate;
    @BindView(R.id.tvVisitingTime)
    TextView tvVisitingTime;
    @BindView(R.id.tvAvailableTill)
    TextView tvAvailableTill;


    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog,
            timePickerDialog1;
    private SimpleDateFormat dateFormatter;

    @BindView(R.id.tvFrom)
    CustomTextView tvFrom;

    @BindView(R.id.tvTo)
    CustomTextView tvTo;

    @BindView(R.id.tvBy)
    CustomTextView tvBy;

    @BindView(R.id.vSepAddress)
    View vSepAddress;
    @BindView(R.id.ivArrow)
    ImageView ivArrow;
    @BindView(R.id.ivLocation)
    ImageView ivLocation;

    NightCLubModel.ResponseBean.RestaurantsBean restaurantsBean;
    PlaceAutocompleteFragment autocompleteFragment;

    String selectedDate,dateVisTime,dateAvaTill;
    private int selectedRadio = -1;
    @BindView(R.id.inviteNumber) TextView inviteNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actvity);
        ButterKnife.bind(this);

        selectedList.clear();


        dateFormatter = new SimpleDateFormat("MMM", Locale.US);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0);


        //  Log.d(
        // "getIntent().",getIntent().getExtras().getString("data"));


        if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE ||
                activityKey == AppConstants.ACTIVITY_LOUNGE ||
                activityKey == AppConstants.ACTIVITY_NIGHTCLUB) {


            restaurantsBean =
                    (NightCLubModel.ResponseBean.RestaurantsBean) getIntent().getExtras().getSerializable("data");

            address.setText(restaurantsBean.getRestaurant().getLocation().getAddress());
            name.setText(restaurantsBean.getRestaurant().getName());
            rating.setText(restaurantsBean.getRestaurant().getUser_rating().getAggregate_rating());

            //((CustomTextView) (findViewById(R.id.time))).setText();

            ((CustomTextView) (findViewById(R.id.distance))).setText(AppConstants.distanceMeasure(Double.parseDouble(getIntent().getExtras().getString("latitude")),
                    Double.parseDouble(getIntent().getExtras().getString("longitude")),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLatitude()),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLongitude())) + " kms");

            if (restaurantsBean.getRestaurant().getThumb().isEmpty() || restaurantsBean.getRestaurant().getThumb().equals("")) {
                profileImage.setImageResource(R.drawable.place_holder);
            } else {
                Glide.with(CreateActivityActvity.this)
                        .load(restaurantsBean.getRestaurant().getThumb())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(profileImage);
            }
            address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent
                            = new Intent(CreateActivityActvity.this,MapsActivity.class);
                    intent.putExtra("latitide",Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLatitude()));
                    intent.putExtra("longitude",Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLongitude()));
                    intent.putExtra("location", restaurantsBean.getRestaurant().getLocation().getAddress());
                    startActivity(intent);
                }
            });
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
            tvAvaTill.setVisibility(View.GONE);
            ivArrow.setVisibility(View.VISIBLE);
            loadSpinner();

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
            //tvNotify.setText(getResources().getString(R.string.on_button_to_get_notify));
            tvNotify.setText(getResources().getString(R.string.on_button_new_text));
            ivArrow.setVisibility(View.GONE);
            tvAvaTill.setVisibility(View.GONE);
            loadSpinner();
        } else if (activityKey == AppConstants.ACTIVITY_TRAVEL_SCHEDULE) {

            tvHeader.setText(getResources().getString(R.string.travelling_date_and_time));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.GONE);
            llActivityBrief.setVisibility(View.VISIBLE);

            tvVisDate.setText(getResources().getString(R.string.select_date));
            tvVisTime.setText(getResources().getString(R.string.select_time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            /*tvNotify.setText(getResources().getString(R.string.want_to_receive_notification_for_same_activity));*/
            tvNotify.setText(getResources().getString(R.string.on_button_new_text));

            tvFrom.setText(getIntent().getExtras().getString(AppConstants.TRAVEL_FROM_KEY));
            tvTo.setText(getIntent().getExtras().getString(AppConstants.TRAVEL_TO_KEY));
            tvBy.setText(getIntent().getExtras().getString(AppConstants.TRAVEL_BY_KEY));

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
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            //HERE

            etAddress.setVisibility(View.GONE);
            autocompleteFragment = (PlaceAutocompleteFragment)
                    getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);


            ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(15);
            ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setPadding(15, 10, 0, 10);
            ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTypeface(Typeface.createFromAsset(getAssets(), "proxima_regular.OTF"));
            ((ImageButton) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)).setImageDrawable(null);
            (autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);

            autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                @Override
                public void onPlaceSelected(Place place) {
                    // TODO: Get info about the selected place.


                    autocompleteFragment.setText(place.getAttributions());

                }

                @Override
                public void onError(Status status) {
                    // TODO: Handle the error.
                    Log.i("Place?>>", "An error occurred: " + status);
                }
            });


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
                intent.putExtra("selectedId", selectedList);
                startActivityForResult(intent, 551);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.tvPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tvVisitingDate.getText().toString().equals("")){

                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_create_actvity)),"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(tvVisitingTime.getText().toString().equals("")){

                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_create_actvity)),"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(tvAvailableTill.getText().toString().equals("")){

                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_create_actvity)) ,"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }

                post();
            }
        });

        llPublicAndFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customList.clear();

                if(radio0.isSelected()){
                    hide_from="";
                    radio0.setSelected(false);

                    return;
                }

                hide_from = "public and following";
              //  if(selectedRadio == 0){
                    radio0.setSelected(true);
              /*  }
                else{
                    radio0.setSelected(false);
                }*/
                radio1.setSelected(false);
                radio2.setSelected(false);
                radio3.setSelected(false);
             //   selectedRadio = 0;
              //  setRadios(0);
            }
        });

        llFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customList.clear();   if(radio1.isSelected()){
                    hide_from="";
                    radio1.setSelected(false);

                    return;
                }
                hide_from = "following";
                radio0.setSelected(false);
              //  if(selectedRadio == 1){
                    radio1.setSelected(true);
              /*  }
                else{
                    radio1.setSelected(false);
                }*/
                radio2.setSelected(false);
                radio3.setSelected(false);
              //  selectedRadio = 1;
               // setRadios(1);
            }
        });

        llPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customList.clear();
                if(radio2.isSelected()){
                    hide_from="";
                    radio2.setSelected(false);

                    return;
                }

                hide_from = "public";
                radio0.setSelected(false);
                radio1.setSelected(false);
               // if(selectedRadio == 2){
                    radio2.setSelected(true);
              /*  }
                else{
                    radio2.setSelected(false);
                }
               */ radio3.setSelected(false);
              //  selectedRadio = 2;

               // setRadios(2);
            }
        });

        llCustom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO For sahil, define hide_from for this.
                //hide_from = "custom";
                if(radio3.isSelected()){
                    hide_from="";
                    radio3.setSelected(false);
                    customList.clear();
                    return;
                }
                radio0.setSelected(false);
                radio1.setSelected(false);
                radio2.setSelected(false);
                    radio3.setSelected(true);


                //setRadios(3);
                Intent intent = new Intent(CreateActivityActvity.this, CustomSelectPeople.class);
                intent.putExtra("selectedId",selectedList);
                startActivityForResult(intent,666);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    notification = "1";
                } else {
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

    private void loadSpinner() {

        spnAvail.setVisibility(View.VISIBLE);
        final List<String> avalSpnList = new ArrayList<>();
        avalSpnList.add(getResources().getString(R.string.available_till));
        avalSpnList.add(getResources().getString(R.string.cut_off_time_to_join_you));

        final ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.item_spinner, avalSpnList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnAvail.setAdapter(adapter);
        spnAvail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {

                //Log.v(TAG,"Position :" + position);
                //tvAvaTill.setText(avalSpnList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setRadios(int pos){

        radio0.setSelected(false);
        radio1.setSelected(false);
        radio2.setSelected(false);
        radio3.setSelected(false);

        if(pos == 0){
            if(selectedRadio == pos){
                radio0.setSelected(false);
            }
            else{
                radio0.setSelected(true);
            }
        }

        if(pos == 1){
            if(selectedRadio == pos){
                radio1.setSelected(false);
            }
            else{
                radio1.setSelected(true);
            }
        }

        if(pos == 2){
            if(selectedRadio == pos){
                radio2.setSelected(false);
            }
            else{
                radio2.setSelected(true);
            }
        }

        if(pos == 3){
            if(selectedRadio == pos){
                radio3.setSelected(false);
            }
            else{
                radio3.setSelected(true);
            }
        }
    }
    ArrayList<String> getSelectedList = new ArrayList<>();
    boolean aBoolean = false;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 551) {

            selectedList = data.getStringArrayListExtra("selectedId");
            inviteNumber.setText(""+selectedList.size());

            Log.d("selectedId >> 0*", selectedList.toString());

        } else  if (requestCode == 666){
            customList = data.getStringArrayListExtra("selectedId");
            ((TextView)findViewById(R.id.tvCustomInviteNumber)).setText(""+customList.size());
        }else {
            Log.d("selectedId >> 0*", "req != 555");

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
                String newday = String.valueOf(day), newmonth = String.valueOf(monthOfYear + 1);

                if (String.valueOf(monthOfYear).length() == 1) {
                    newmonth = "0" + newmonth;
                }

                if (String.valueOf(day).length() == 1) {
                    newday = "0" + day;
                }

                selectedDate = year + "-" + newmonth + "-" + newday;
                Log.d("date>>", year + "-" + newmonth + "-" + newday);
                tvVisitingDate.setText(AppConstants.formatDate(newday + "-" + newmonth + "-" + year,"dd-mm-yyyy","dd MMM"));
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

                /*if(strHrsToShow.length()==1){
                    strHrsToShow = "0"+strHrsToShow;
                }
*/
                String min = String.valueOf(selectedMinute);
  /*              if(String.valueOf(selectedMinute).length() == 1){
                    min = "0"+String.valueOf(selectedMinute);
                }
*/

                tvVisitingTime.setText(strHrsToShow+":"+min+" "+am_pm);

            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), false);

        timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {

                String am_pm = "";

                Calendar datetime = Calendar.getInstance();
                datetime.set(Calendar.HOUR_OF_DAY, selectedHour);
                datetime.set(Calendar.MINUTE, selectedMinute);

                if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                    am_pm = "AM";
                else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                    am_pm = "PM";

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime.get(Calendar.HOUR) + "";

                /*if(strHrsToShow.length()==1){
                    strHrsToShow = "0"+strHrsToShow;
                }*/
                String min = String.valueOf(selectedMinute);
               /* if(String.valueOf(selectedMinute).length() == 1){
                    min = "0"+String.valueOf(selectedMinute);
                }
*/

                tvAvailableTill.setText(strHrsToShow+ ":"+min+" "+am_pm);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), false);
    }


    private void post() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CreateActivityActvity.this);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sharedPreferences.getString("access_token", ""));
            requestParams.add("activity_type", String.valueOf(getIntent().getExtras().getInt("activity_id")));
            requestParams.add("activity_name", name.getText().toString());
            requestParams.add("activity_date",selectedDate + " " + convertTime12TO24(tvVisitingTime.getText().toString().trim()));
            //requestParams.add("activity_date", );
            requestParams.add("activity_location", address.getText().toString());
            if (activityKey == AppConstants.ACTIVITY_TRAVEL_SCHEDULE) {

                requestParams.add("from", tvFrom.getText().toString());
                requestParams.add("to", tvTo.getText().toString());
            }



            requestParams.add("activity_location_lat", restaurantsBean.getRestaurant().getLocation().getLatitude());
            requestParams.add("activity_location_lon", restaurantsBean.getRestaurant().getLocation().getLongitude());
            if (getIntent().getExtras().getInt("activity_id") == 1 || getIntent().getExtras().getInt("activity_id") == 2) {
                requestParams.add("cut_off_time", selectedDate + " " + convertTime12TO24(tvAvailableTill.getText().toString().trim()));
            } else if (getIntent().getExtras().getInt("activity_id") == 3 ||
                    getIntent().getExtras().getInt("activity_id") == 4 ||
                    getIntent().getExtras().getInt("activity_id") == 5 ||
                    getIntent().getExtras().getInt("activity_id") == 6 ||
                    getIntent().getExtras().getInt("activity_id") == 7) {
                requestParams.add("available_till", selectedDate + " " + convertTime12TO24(tvAvailableTill.getText().toString().trim()));
            }

            if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE ||
                    activityKey == AppConstants.ACTIVITY_LOUNGE ||
                    activityKey == AppConstants.ACTIVITY_NIGHTCLUB) {
                requestParams.add("activity_id", String.valueOf(restaurantsBean.getRestaurant().getId()));

            } else {
                requestParams.add("activity_id", "0");
            }
            requestParams.add("notification", notification);
            if(customList.size()>0){
                requestParams.add("hide_from",customList.toString().replace("[", "").replace("]", ""));
            }else {
                requestParams.add("hide_from", hide_from);
            }
            requestParams.add("invite_list", selectedList.toString().replace("[", "").replace("]", ""));
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
                AppConstants.dismissDialog();

                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("success")) {
                    AppConstants.dismissDialog();
                  /*  if(jsonObject.getJSONArray("similar_posts").length()==0){
                    Toast.makeText(getApplicationContext(), "Post created", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    }else{
                  */      Intent intent = new Intent(getApplicationContext(), PeopleJoinActivity.class);
                        intent.putExtra("similar_posts",jsonObject.getJSONArray("similar_posts").toString());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                 //   }
                } else {
                    Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
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

    String convertTime12TO24(String timeNow){

        try {


            Log.d("dateIssue>>",timeNow);
            SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = outFormat.format(inFormat.parse(timeNow));
            Log.d("dateIssue>>",time24);
            return time24+":00";
        } catch (Exception e){
            Log.d("dateIssue>>",e.toString());

            Toast.makeText(CreateActivityActvity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            return  "";
        }
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_create_actvity)),intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));

    }


    @Override
    protected void onResume() {
        super.onResume();

        if (aBoolean == true) {
            selectedList = getSelectedList;
            Log.d("selectedId >> 1*", selectedList.toString());
        }
    }




    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

}
