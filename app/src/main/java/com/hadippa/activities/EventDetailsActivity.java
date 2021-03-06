package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.Browser;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MeraEventPartyModel;
import com.hadippa.model.NotificationModel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import io.appsfly.sdk.providers.AppsFlyProvider;

public class EventDetailsActivity extends AppCompatActivity {


    static String[] suffixes =
            //    0     1     2     3     4     5     6     7     8     9
            { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    10    11    12    13    14    15    16    17    18    19
                    "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                    //    20    21    22    23    24    25    26    27    28    29
                    "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    30    31
                    "th", "st" };
    String hide_from = "", notification = "1";
/*
    @BindView(R.id.toggle2)
    ToggleButton toggle2;*/

    @BindView(R.id.switchCompat)
    SwitchCompat switchCompat;
    @BindView(R.id.llPublicAndFollowing) LinearLayout llPublicAndFollowing;
    @BindView(R.id.llFollowing) LinearLayout llFollowing;
    @BindView(R.id.llPublic) LinearLayout llPublic;
    @BindView(R.id.llCustom) LinearLayout llCustom;
    @BindView(R.id.scrollView) NestedScrollView scrollView;

    @BindView(R.id.radio0)
    ImageView radio0;
    @BindView(R.id.radio1) ImageView radio1;
    @BindView(R.id.radio2) ImageView radio2;
    @BindView(R.id.radio3) ImageView radio3;

    @BindView(R.id.tvVisitingDate) TextView tvVisitingDate;
    @BindView(R.id.tvVisitingTime) TextView tvVisitingTime;
    @BindView(R.id.tvPrice) TextView tvPrice;
    @BindView(R.id.tvEventName) TextView tvEventName;
    @BindView(R.id.tvDate) TextView tvDate;
    @BindView(R.id.tvTime) TextView tvTime;
    @BindView(R.id.tvKm) TextView tvKm;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvKnowMore) TextView tvKnowMore;
    @BindView(R.id.ivMoreDetail) ImageView ivMoreDetail;
    @BindView(R.id.vSep1) View vSep1;
    @BindView(R.id.tvDescriptionVal) TextView tvDescriptionVal;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.vSepCast) View vSepCast;
    @BindView(R.id.tvCastVal) TextView tvCastVal;
    @BindView(R.id.tvCast) TextView tvCast;

    @BindView(R.id.rlPrice) RelativeLayout rlPrice;
    @BindView(R.id.tvDetails) TextView tvDetails;
    @BindView(R.id.rcvPrice) RecyclerView rcvPrice;
    @BindView(R.id.vSep5) View vSep5;
    @BindView(R.id.tvTotalPrice) CustomTextView tvTotalPrice;
    @BindView(R.id.inviteNumber) TextView inviteNumber;
    @BindView(R.id.rlSelectTheater) RelativeLayout rlSelectTheater;
    @BindView(R.id.flMovie) FrameLayout flMovie;
    @BindView(R.id.llEventDetails) LinearLayout llEventDetails;

    private PriceAdapter priceAdapter;

    @BindView(R.id.tvAvailableTill)
    TextView tvAvailableTill;/*

    @BindView(R.id.profileImage)
    RoundedImageView profileImage;*/
    
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog, timePickerDialog1;

    private SimpleDateFormat dateFormatter;
    private int activityKey;

    ArrayList<String> selectedList = new ArrayList<>();
    ArrayList<String> customList = new ArrayList<>();
    MeraEventPartyModel.DataBean dataBean;

    @BindView(R.id.totalInclude)
    RelativeLayout totalInclude;
    int count =0;

    @BindView(R.id.rlCal) RelativeLayout rlCal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);
        ButterKnife.bind(this);

        ivMoreDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vSep1.getVisibility() == View.VISIBLE){
                    vSep1.setVisibility(View.GONE);
                    tvDescriptionVal.setVisibility(View.GONE);
                    tvDescription.setVisibility(View.GONE);
                    tvKnowMore.setText(R.string.know_more);
                    tvCast.setVisibility(View.GONE);
                    tvCastVal.setVisibility(View.GONE);
                    vSepCast.setVisibility(View.GONE);
                    ivMoreDetail.setImageResource(R.drawable.group_chat_more);
                }
                else{
                    vSep1.setVisibility(View.VISIBLE);
                    tvDescriptionVal.setVisibility(View.VISIBLE);
                    tvDescription.setVisibility(View.VISIBLE);
                    tvKnowMore.setText(R.string.know_less);
                    ivMoreDetail.setImageResource(R.drawable.group_chat_less);

                    if(activityKey == AppConstants.ACTIVITY_MOVIE){
                        tvCast.setVisibility(View.VISIBLE);
                        tvCastVal.setVisibility(View.VISIBLE);
                        vSepCast.setVisibility(View.VISIBLE);
                        tvDescription.setText(getString(R.string.synopsis));
                    }
                }
            }
        });

        dateFormatter = new SimpleDateFormat("MMM", Locale.US);

        rlCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setType("vnd.android.cursor.item/event");
                    intent.putExtra(CalendarContract.Events.TITLE, dataBean.getTitle());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, dataBean.getVenueName()+", "+dataBean.getCityName()+"-"+dataBean.getPincode());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, Html.fromHtml(dataBean.getDescription()));

// Setting dates
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    Calendar calDate = Calendar.getInstance();
                    calDate.setTime(df.parse(dataBean.getStartDate()));
                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                            calDate.getTimeInMillis());
                    calDate.setTime(df.parse(dataBean.getEndDate()));
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                            calDate.getTimeInMillis());

// make it a full day event
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

// make it a recurring Event
                    intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

// Making it private and shown as busy
                    intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
                    intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });
        if(activityKey == AppConstants.ACTIVITY_MOVIE){
            llEventDetails.setVisibility(View.GONE);
            flMovie.setVisibility(View.VISIBLE);
            rlSelectTheater.setVisibility(View.VISIBLE);
        }
        else{

            dataBean = (MeraEventPartyModel.DataBean) getIntent().getExtras().getSerializable("data");

            tvPrice.setText(dataBean.getTicket_currencyCode().trim()+" "+dataBean.getTicket_price());
            tvEventName.setText(dataBean.getTitle());
            tvAddress.setText(dataBean.getAddress1()+" "+dataBean.getAddress2()
                    +" "+dataBean.getCityName()+" "+dataBean.getStateName());


            if(AppConstants.calculateDays(dataBean.getStartDate(),dataBean.getEndDate()) == 0){

                tvTime.setText(AppConstants.formatDate(dataBean.getStartDate(),"yyyy-MM-dd hh:mm:ss","hh.mm a")
                        +
                        " - "
                        +
                        AppConstants.formatDate(dataBean.getEndDate(),"yyyy-MM-dd hh:mm:ss","hh.mm a")+"\n"
                +AppConstants.formatDate(dataBean.getStartDate(),"yyyy-MM-dd hh:mm:ss","dd MMM yy"));

            }else{

                tvTime.setText(AppConstants.formatDate(dataBean.getStartDate(),"yyyy-MM-dd hh:mm:ss","hh.mm a")
                        +
                        " - "
                        +
                        AppConstants.formatDate(dataBean.getEndDate(),"yyyy-MM-dd hh:mm:ss","hh.mm a")+"\n"
                        +AppConstants.formatDate(dataBean.getStartDate(),"yyyy-MM-dd hh:mm:ss","dd MMM yy") +" to "
                        +AppConstants.formatDate(dataBean.getEndDate(),"yyyy-MM-dd hh:mm:ss","dd MMM yy"));

            }
            tvKm.setText(AppConstants.distanceMeasure(Double.parseDouble(getIntent().getExtras().getString("latitude")),
                    Double.parseDouble(getIntent().getExtras().getString("longitude")),
                    (dataBean.getLatitude()),
                    (dataBean.getLongitude())) + " kms");
            tvDescriptionVal.setText(Html.fromHtml(dataBean.getDescription()));

            tvAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent
                            = new Intent(EventDetailsActivity.this,MapsActivity.class);
                    intent.putExtra("latitide",dataBean.getLatitude());
                    intent.putExtra("longitude",dataBean.getLongitude());
                    intent.putExtra("location", dataBean.getAddress1()+" "+dataBean.getAddress2()
                            +" "+dataBean.getCityName()+" "+dataBean.getStateName());
                    startActivity(intent);
                }
            });
            RequestManager requestManager = Glide.with(EventDetailsActivity.this);

            requestManager.load(dataBean.getBannerPath()).error(R.drawable.bg_item_above)
                    .placeholder(R.drawable.bg_item_above)
                    .into(((ImageView)findViewById(R.id.profileImage)));


            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            rcvPrice.setLayoutManager(mLayoutManager);

            if(dataBean.getTickets().getTicketList().size() > 0){
                priceAdapter = new PriceAdapter(dataBean.getTickets().getTicketList());
                rcvPrice.setAdapter(priceAdapter);
                rcvPrice.setNestedScrollingEnabled(false);

                    }
            else {
                totalInclude.setVisibility(View.GONE);
                rcvPrice.setVisibility(View.GONE);
            }

        }
        /*if(dataBean.getBannerPath().isEmpty() || dataBean.getBannerPath().equals("")){

            profileImage.setImageResource(R.drawable.place_holder);
        }else {

            Glide.with(EventDetailsActivity.this)
                    .load(dataBean.getBannerPath())
                    .error(R.drawable.place_holder)
                    .placeholder(R.drawable.place_holder)
                    .into(profileImage);
        }*/


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
                Intent intent = new Intent(EventDetailsActivity.this, InviteToJoinActivity.class);
                intent.putExtra("selectedId",selectedList);
                intent.putExtra("otherId", customList);
                startActivityForResult(intent,555);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.tvPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tvVisitingDate.getText().toString().equals("")){
                    
                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_event_details)),"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(tvVisitingTime.getText().toString().equals("")){

                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_event_details)),"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(tvAvailableTill.getText().toString().equals("")){

                    Snackbar.make(((RelativeLayout)findViewById(R.id.activity_event_details)),"Date and Times are mandatory.",Snackbar.LENGTH_LONG).show();
                    return;
                }

                if(count==0){
                    post();
                }else{

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    //Yes button clicked


                                    try {
                                        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(EventDetailsActivity.this);
                                        JSONObject jsonObject1 = new JSONObject(sp.getString("userData",""));
                                        String.valueOf(jsonObject1.getInt("id"));
                                        JSONObject jsonObject = new JSONObject();
                                        jsonObject.put("eventId",String.valueOf(dataBean.getId()));
                                        jsonObject.put("emailId",jsonObject1.getString("email"));
                                       // jsonObject.put("ticketArray["+dataBean.getTickets().getTicketList().get(0).getId()+"]", "1");
                                        AppsFlyProvider.getInstance().pushApp("589afcb985cbc2000fa1c784", "4174f005-0b08-465a-ab76-ae118f086854", "INTENT",
                                                jsonObject,
                                                EventDetailsActivity.this);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                 //   getAccessToken();

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    post();
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(EventDetailsActivity.this);
                        builder.setMessage("Do you want to book this event now ?").setPositiveButton("Now", dialogClickListener)
                            .setNegativeButton("Later", dialogClickListener).show();

                }

            }
        });


        llPublicAndFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(radio0.isSelected()){
                    hide_from="";
                    radio0.setSelected(false);

                    return;
                }
                hide_from = "public and following";
                radio0.setSelected(true);
                radio1.setSelected(false);
                radio2.setSelected(false);
                radio3.setSelected(false);
            }
        });

        llFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(radio1.isSelected()){
                    hide_from="";
                    radio1.setSelected(false);

                    return;
                }
                hide_from = "following";
                radio0.setSelected(false);
                radio1.setSelected(true);
                radio2.setSelected(false);
                radio3.setSelected(false);
            }
        });

        llPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(radio2.isSelected()){
                    hide_from="";
                    radio2.setSelected(false);

                    return;
                }

                hide_from = "public";
                radio0.setSelected(false);
                radio1.setSelected(false);
                radio2.setSelected(true);
                radio3.setSelected(false);
            }
        });

        llCustom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //TODO For sahil, define hide_from for this.
                //hide_from = "custom";

                if(radio3.isSelected()){
                    hide_from="";
                    radio3.setSelected(false);

                    return;
                }
                radio0.setSelected(false);
                radio1.setSelected(false);
                radio2.setSelected(false);
                radio3.setSelected(true);

                Intent intent = new Intent(EventDetailsActivity.this, CustomSelectPeople.class);
                intent.putExtra("selectedId",customList);intent.putExtra("otherId", selectedList);
                startActivityForResult(intent,555);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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


        rlPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vSep5.getVisibility() == View.VISIBLE){
                    vSep5.setVisibility(View.GONE);
                    rcvPrice.setVisibility(View.GONE);
                    totalInclude.setVisibility(View.GONE);
                    tvDetails.setText(getString(R.string.details));
                }
                else{
                    vSep5.setVisibility(View.VISIBLE);
                    totalInclude.setVisibility(View.VISIBLE);
                    rcvPrice.setVisibility(View.VISIBLE);
                    tvDetails.setText(getString(R.string.less));
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 555){

            selectedList = data.getStringArrayListExtra("selectedId");

            inviteNumber.setText(""+selectedList.size());

            Log.d("selectedId >> 0*",selectedList.toString());

        }else  if (requestCode == 666){
            customList = data.getStringArrayListExtra("selectedId");
            ((TextView)findViewById(R.id.tvCustomInviteNumber)).setText(""+customList.size());
        }else{
            Log.d("selectedId >> 0*","req != 555");

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

              /*  if(strHrsToShow.length()==1){
                    strHrsToShow = "0"+strHrsToShow;
                }*/
                String min = String.valueOf(selectedMinute);
                if(String.valueOf(selectedMinute).length() == 1){
                    min = "0"+String.valueOf(selectedMinute);
                }


                tvVisitingTime.setText(strHrsToShow + ":" + min+" "+am_pm);

            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), true);

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

             /*   if(strHrsToShow.length()==1){
                    strHrsToShow = "0"+strHrsToShow;
                }*/
                String min = String.valueOf(selectedMinute);
                if(String.valueOf(selectedMinute).length() == 1){
                    min = "0"+String.valueOf(selectedMinute);
                }

                tvAvailableTill.setText(strHrsToShow + ":" + min+" "+am_pm);
            }
        }, newCalendar.get(Calendar.HOUR_OF_DAY), newCalendar.get(Calendar.MONTH), true);
    }

    private void post() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EventDetailsActivity.this);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            Log.d("activity_type>>",String.valueOf(getIntent().getExtras().getInt("activity_id")));
            requestParams.add("access_token", sharedPreferences.getString("access_token", ""));
            requestParams.add("activity_type", String.valueOf(getIntent().getExtras().getInt("activity_id")));
            requestParams.add("activity_name", tvEventName.getText().toString());
            requestParams.add("activity_date", tvVisitingDate.getText().toString() + " " + convertTime12TO24(tvVisitingTime.getText().toString()));
            requestParams.add("activity_location", tvAddress.getText().toString());
            requestParams.add("activity_location_lat",String.valueOf(dataBean.getLatitude()));
            requestParams.add("activity_location_lon", String.valueOf(dataBean.getLongitude()));
            if (getIntent().getExtras().getInt("activity_id")==1 || getIntent().getExtras().getInt("activity_id") == 2) {
                requestParams.add("cut_off_time", tvEventName.getText().toString());
            } else if (getIntent().getExtras().getInt("activity_id") == 3 ||
                    getIntent().getExtras().getInt("activity_id") == 4 ||
                    getIntent().getExtras().getInt("activity_id") == 5 ||
                    getIntent().getExtras().getInt("activity_id") == 6 ||
                    getIntent().getExtras().getInt("activity_id") == 7) {
                requestParams.add("available_till", tvVisitingDate.getText().toString() + " " +convertTime12TO24(tvAvailableTill.getText().toString()));
            }

            requestParams.add("activity_id", String.valueOf(dataBean.getId()));
            requestParams.add("notification", notification);

            if(selectedList.size()>0) {
                requestParams.add("invite_list", selectedList.toString().replace("[", "").replace("]", ""));
            }else{
                requestParams.add("invite_list","");
            }
            if(customList.size()>0){
                requestParams.add("hide_from",customList.toString().replace("[", "").replace("]", ""));
            }else {
                requestParams.add("hide_from", "");
            }
            Log.d("date>>", requestParams.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CREATE_POST, requestParams,
                new Post());
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
    class Post extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            showProgressDialog(EventDetailsActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
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
                dismissDialog();
                if(jsonObject.getBoolean("success")) {
                    Toast.makeText(getApplicationContext(),"Post created",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), PeopleJoinActivity.class);
                    intent.putExtra("similar_posts",jsonObject.getJSONArray("similar_posts").toString());
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

    String convertTime12TO24(String timeNow){

        try {


            SimpleDateFormat inFormat = new SimpleDateFormat("hh:mm a");
            SimpleDateFormat outFormat = new SimpleDateFormat("HH:mm");
            String time24 = outFormat.format(inFormat.parse(timeNow));

            return time24+":00";
        } catch (Exception e){
            return  "";
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_event_details)),intent.getExtras().getString("messageData"));
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

    class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

        private List<MeraEventPartyModel.DataBean.TicketsBean.TicketListBean> data = null;
        private LayoutInflater mInflater;

        PriceAdapter(List<MeraEventPartyModel.DataBean.TicketsBean.TicketListBean> data) {
            this.data = data;
        }


        @Override
        public PriceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    View view1 = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_event_price, parent, false);
                    return new ViewHolder(view1);
            }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final PriceAdapter.ViewHolder viewHolder1, final int position) {



                final MeraEventPartyModel.DataBean.TicketsBean.TicketListBean str = data.get(position);
                viewHolder1.tvPriceName.setText(str.getName());
                viewHolder1.tvSittingDetails.setText(str.getDescription());
                viewHolder1.tvSitting.setText(""+str.getPrice());

            viewHolder1.ivPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Integer.parseInt(viewHolder1.tvTicketNo.getText().toString()) == str.getMaxOrderQuantity()){
                        Toast.makeText(EventDetailsActivity.this,"Cannot book more than "+str.getMaxOrderQuantity()+" tickets.",Toast.LENGTH_LONG).show();
                        return;
                    }

                    count = count + 1;
                    int currentTotal = Integer.parseInt(tvTotalPrice.getText().toString().trim().split(" ")[1]);
                    currentTotal = currentTotal + str.getPrice();
                    tvTotalPrice.setText("INR "+currentTotal);

                    viewHolder1.tvTicketNo.setText(""+(Integer.parseInt(viewHolder1.tvTicketNo.getText().toString())+1));
                }
            });

            viewHolder1.ivMinus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(Integer.parseInt(viewHolder1.tvTicketNo.getText().toString()) == 0){
                        //Toast.makeText(EventDetailsActivity.this,"Cannot book more than "+str.getMaxOrderQuantity()+" tickets.",Toast.LENGTH_LONG).show();
                        return;
                    }

                    count = count - 1;
                    int currentTotal = Integer.parseInt(tvTotalPrice.getText().toString().trim().split(" ")[1]);
                    currentTotal = currentTotal - str.getPrice();
                    tvTotalPrice.setText("INR "+currentTotal);
                    viewHolder1.tvTicketNo.setText(""+(Integer.parseInt(viewHolder1.tvTicketNo.getText().toString())-1));
                }
            });

        }

        public MeraEventPartyModel.DataBean.TicketsBean.TicketListBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {

            return data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public CustomTextView tvPriceName;
            public CustomTextView tvSitting;
            public CustomTextView tvSittingDetails;
            public ImageView ivPlus;
            public ImageView ivMinus;
            public TextView tvTicketNo;

            public ViewHolder(final View v) {
                super(v);

                tvPriceName = (CustomTextView) v.findViewById(R.id.tvPriceName);
                tvSitting = (CustomTextView) v.findViewById(R.id.tvSitting);
                tvSittingDetails = (CustomTextView) v.findViewById(R.id.tvSittingDetails);
                ivPlus = (ImageView) v.findViewById(R.id.ivPlus);
                ivMinus = (ImageView) v.findViewById(R.id.ivMinus);
                tvTicketNo = (CustomTextView) v.findViewById(R.id.tvTicketNo);
            }
        }


    }

    private void getAccessToken() {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EventDetailsActivity.this);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("clientId", "701828916");
            requestParams.add("eventId", String.valueOf(dataBean.getId()));

            for(int i =0 ; i < dataBean.getTickets().getTicketList().size();i++ ){
                requestParams.add("ticketArray["+dataBean.getTickets().getTicketList().get(i).getId()+"]", "1");
            }



            Log.d("date>>", requestParams.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+"meraevents/event/booking",requestParams,
                new GetAccessToken());
    }

    class GetAccessToken extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            showProgressDialog(EventDetailsActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
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

                Toast.makeText(EventDetailsActivity.this,response,Toast.LENGTH_LONG).show();

              //  Intent intent = new Intent(EventDetailsActivity.this,WebViewActivity.class);

               // dataBean.getId();
               // dataBean.getTickets().getTicketList().get(0).getId();


            /*   String s = "https://www.meraevents.com/web/api/v1/booking";

                Log.d("s>>",s);
                intent.putExtra("code",jsonObject.getJSONObject("response").getString("accessToken"));
                intent.putExtra("url",s);
                intent.putExtra("data","eventId="+dataBean.getId()+"&ticketArray="+map.toString());
                startActivity(intent);*/

                /*String url = "https://www.meraevents.com/web/api/v1/booking?eventId=\"+dataBean.getId()+\"&ticketArray=\"+map.toString()";
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
// set toolbar color and/or setting custom actions before invoking build()
// Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
                CustomTabsIntent customTabsIntent = builder.build();
// and launch the desired Url with CustomTabsIntent.launchUrl()
                Bundle header = new Bundle();
                header.putString("Authorization", "Bearer "+jsonObject.getJSONObject("response").getString("accessToken"));
                customTabsIntent.intent.putExtra(Browser.EXTRA_HEADERS, header);

                customTabsIntent.launchUrl(EventDetailsActivity.this, Uri.parse(url));*/
                dismissDialog();

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
