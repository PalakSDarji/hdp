package com.hadippa.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hadippa.AppConstants;
import com.hadippa.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateActivityActvity extends AppCompatActivity {

    static String[] suffixes =
            //    0     1     2     3     4     5     6     7     8     9
            { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    10    11    12    13    14    15    16    17    18    19
                    "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
                    //    20    21    22    23    24    25    26    27    28    29
                    "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
                    //    30    31
                    "th", "st" };


    private int activityKey = 0;
    @BindView(R.id.cvVisitingCard) CardView cvVisitingCard;
    @BindView(R.id.llNameAddress) LinearLayout llNameAddress;
    @BindView(R.id.llActivityBrief) LinearLayout llActivityBrief;
    @BindView(R.id.tvHeader) TextView tvHeader;

    @BindView(R.id.tvActivityName) TextView tvActivityName;
    @BindView(R.id.tvAddress) TextView tvAddress;
    @BindView(R.id.tvVisDate)  TextView tvVisDate;
    @BindView(R.id.tvVisTime) TextView tvVisTime;
    @BindView(R.id.tvAvaTill) TextView tvAvaTill;
    @BindView(R.id.tvNotify) TextView tvNotify;

    @BindView(R.id.etActivityName) EditText etActivityName;
    @BindView(R.id.etAddress) EditText etAddress;
    @BindView(R.id.etAvaTill) EditText etAvaTill;

    @BindView(R.id.llPublicAndFollowing) LinearLayout llPublicAndFollowing;
    @BindView(R.id.llFollowing) LinearLayout llFollowing;
    @BindView(R.id.llPublic) LinearLayout llPublic;

    @BindView(R.id.radio0) RadioButton radio0;
    @BindView(R.id.radio1) RadioButton radio1;
    @BindView(R.id.radio2) RadioButton radio2;

    @BindView(R.id.tvVisitingDate) TextView tvVisitingDate;
    @BindView(R.id.tvVisitingTime) TextView tvVisitingTime;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private SimpleDateFormat dateFormatter;

    @BindView(R.id.vSepAddress) View vSepAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_actvity);
        ButterKnife.bind(this);

        dateFormatter = new SimpleDateFormat("MMM", Locale.US);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0);

        if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE) {

            tvHeader.setText(getResources().getString(R.string.visiting_date_and_time));
            cvVisitingCard.setVisibility(View.VISIBLE);
            llNameAddress.setVisibility(View.GONE);
            llActivityBrief.setVisibility(View.GONE);

            tvVisDate.setText(getResources().getString(R.string.select_date));
            tvVisTime.setText(getResources().getString(R.string.select_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
        }
        else if (activityKey == AppConstants.ACTIVITY_CREATE_ACTIVITY) {

            tvHeader.setText(getResources().getString(R.string.create_activity));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.name_of_the_activity));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));

        }
        else if (activityKey == AppConstants.ACTIVITY_HOBBY) {

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
        }
        else if (activityKey == AppConstants.ACTIVITY_TRAVEL_SCHEDULE) {

            tvHeader.setText(getResources().getString(R.string.travelling_date_and_time));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.GONE);
            llActivityBrief.setVisibility(View.VISIBLE);

            tvVisDate.setText(getResources().getString(R.string.select_date));
            tvVisTime.setText(getResources().getString(R.string.select_time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
            tvNotify.setText(getResources().getString(R.string.want_to_receive_notification_for_same_activity));
        }
        else if (activityKey == AppConstants.ACTIVITY_PARTY) {

            tvHeader.setText(getResources().getString(R.string.party));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.party_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
        }
        else if (activityKey == AppConstants.ACTIVITY_STANDUP_COMEDY) {

            tvHeader.setText(getResources().getString(R.string.standup_comedy));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.standup_comedy_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
        }
        else if (activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {

            tvHeader.setText(getResources().getString(R.string.indoor_game));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.game_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
        }
        else if (activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {

            tvHeader.setText(getResources().getString(R.string.outdoor_game));
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.game_name));
            tvAddress.setText(getResources().getString(R.string.address));
            tvVisDate.setText(getResources().getString(R.string.visiting_date));
            tvVisTime.setText(getResources().getString(R.string.visiting_time));
            tvAvaTill.setText(getResources().getString(R.string.available_till));
        }
        else if (activityKey == AppConstants.ACTIVITY_ENTERTAINMENT) {

            tvHeader.setText("Sultan (U/A)");
            cvVisitingCard.setVisibility(View.GONE);
            llNameAddress.setVisibility(View.VISIBLE);
            llActivityBrief.setVisibility(View.GONE);
            tvAddress.setVisibility(View.GONE);
            etAddress.setVisibility(View.GONE);
            vSepAddress.setVisibility(View.GONE);

            tvActivityName.setText(getResources().getString(R.string.select_theater));
            tvVisDate.setText(getResources().getString(R.string.date));
            tvVisTime.setText(getResources().getString(R.string.time));
            tvAvaTill.setText(getResources().getString(R.string.cut_off_time_to_join_you));
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
                startActivity(intent);
            }
        });

        findViewById(R.id.tvPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivityActvity.this, PeopleJoinActivity.class);
                startActivity(intent);
            }
        });

        llPublicAndFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radio0.setChecked(true);
                radio1.setChecked(false);
                radio2.setChecked(false);
            }
        });

        llFollowing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radio0.setChecked(false);
                radio1.setChecked(true);
                radio2.setChecked(false);
            }
        });

        llPublic.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                radio0.setChecked(false);
                radio1.setChecked(false);
                radio2.setChecked(true);
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

    }

    private void setDateTimePicker() {

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                int day = newDate.get(Calendar.DAY_OF_MONTH);
                String dayStr = day + suffixes[day];
                tvVisitingDate.setText(dayStr+" "+dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

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

                String strHrsToShow = (datetime.get(Calendar.HOUR) == 0) ?"12":datetime.get(Calendar.HOUR)+"";

                tvVisitingTime.setText( strHrsToShow+":"+datetime.get(Calendar.MINUTE)+" "+am_pm );
            }
        },newCalendar.get(Calendar.HOUR_OF_DAY),newCalendar.get(Calendar.MONTH),false);
    }

}
