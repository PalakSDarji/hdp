package com.hadippa.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookRequestError;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.activities.filter.CoffeeActivityFilter;
import com.hadippa.activities.filter.EventListActivityFilter;
import com.hadippa.activities.filter.TravelActivityFitler;
import com.hadippa.model.FilterModel;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener
        /*, View.OnLongClickListener*/ {

    TextView tvMovie, tvTheatrePlay, tvEvent, tvFestival, tvNightClub,
            tvLongue, tvParty, tvStandUpComedy, tvCoffee, tvAirplane, tvTrain, tvBus, tvAdventure, tvIndoor, tvOutdoor, tvHobby, tvOtherActivity;
    RecyclerView dateList;
    LayoutInflater inflater;

    boolean hasTravel = false;
    String traveFrom = "", travelTo = "";

    ImageView imageBack;

    CustomTextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        inflater = getLayoutInflater();
        dateList = (RecyclerView) findViewById(R.id.dateList);
        //flow_layout = (FlowLayout) findViewById(R.id.flow_layout);

        tvNext = (CustomTextView) findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FilterActivity.this, FilterChooserActivity.class);

                Log.d("activity_id>>??;;",activity_id.toString());
                if (hasTravel) {
                    intent.putExtra("hasTravel", true);

                    intent.putExtra("activity_name", traveFrom + " To " + travelTo);
                    intent.putExtra("activity_to", travelTo);
                    intent.putExtra("activity_from", traveFrom);
                } else {
                    intent.putExtra("hasTravel", false);
                }

                intent.putExtra("activity_type", activityType);
                intent.putExtra("activity_id", activity_id);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
            }
        });
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        tvMovie = (TextView) findViewById(R.id.tvMovie);
        tvMovie.setOnClickListener(this);
        //tvMovie.setOnLongClickListener(this);
        tvTheatrePlay = (TextView) findViewById(R.id.tvTheatrePlay);
        tvTheatrePlay.setOnClickListener(this);
        tvEvent = (TextView) findViewById(R.id.tvEvent);
        tvEvent.setOnClickListener(this);
        tvFestival = (TextView) findViewById(R.id.tvFestival);
        tvFestival.setOnClickListener(this);
        tvNightClub = (TextView) findViewById(R.id.tvNightClub);
        tvNightClub.setOnClickListener(this);
        tvLongue = (TextView) findViewById(R.id.tvLongue);
        tvLongue.setOnClickListener(this);
        tvParty = (TextView) findViewById(R.id.tvParty);
        tvParty.setOnClickListener(this);
        tvStandUpComedy = (TextView) findViewById(R.id.tvStandUpComedy);
        tvStandUpComedy.setOnClickListener(this);
        tvCoffee = (TextView) findViewById(R.id.tvCoffee);
        tvCoffee.setOnClickListener(this);
        tvAirplane = (TextView) findViewById(R.id.tvAirplane);
        tvAirplane.setOnClickListener(this);
        //tvAirplane.setOnLongClickListener(this);
        /*tvCar = (TextView) findViewById(R.id.tvCar);
        tvCar.setOnClickListener(this);
        tvCar.setOnLongClickListener(this);*/
        tvTrain = (TextView) findViewById(R.id.tvTrain);
        tvTrain.setOnClickListener(this);
        //tvTrain.setOnLongClickListener(this);
        tvBus = (TextView) findViewById(R.id.tvBus);
        tvBus.setOnClickListener(this);
        //tvBus.setOnLongClickListener(this);
        tvAdventure = (TextView) findViewById(R.id.tvAdventure);
        tvAdventure.setOnClickListener(this);
        tvIndoor = (TextView) findViewById(R.id.tvIndoor);
        tvIndoor.setOnClickListener(this);
        tvOutdoor = (TextView) findViewById(R.id.tvOutdoor);
        tvOutdoor.setOnClickListener(this);
        tvHobby = (TextView) findViewById(R.id.tvHobby);
        tvHobby.setOnClickListener(this);
        tvOtherActivity = (TextView) findViewById(R.id.tvOtherActivity);
        tvOtherActivity.setOnClickListener(this);


        // TODO This code is to make activity list dynamic. Not needed now, but in case in future.
        /*filters.add(new FilterModel("Movie",R.color.pink,false));
        filters.add(new FilterModel("Theatre Play",R.color.pink,false));
        filters.add(new FilterModel("Event",R.color.pink,false));
        filters.add(new FilterModel("Festival",R.color.pink,false));
        filters.add(new FilterModel("Nightclub", R.color.pink, false));
        filters.add(new FilterModel("Lounge",R.color.pink,false));
        filters.add(new FilterModel("Party",R.color.pink,false));
        filters.add(new FilterModel("Standup Comedy",R.color.pink,false));
        filters.add(new FilterModel("Coffee",R.color.pink,false));
        filters.add(new FilterModel("Airplane",R.color.green,false));
        filters.add(new FilterModel("Car",R.color.green,false));
        filters.add(new FilterModel("Train",R.color.green,false));
        filters.add(new FilterModel("Bus",R.color.green,false));
        filters.add(new FilterModel("Adventure",R.color.dark_green,false));
        filters.add(new FilterModel("Indoor Sports",R.color.dark_green,false));
        filters.add(new FilterModel("Outdoor Sports",R.color.dark_green,false));
        filters.add(new FilterModel("Hobby Class",R.color.blue,false));
        filters.add(new FilterModel("Add Activity",R.color.purple,false));

        try{
            if(filters != null){
                flow_layout.removeAllViews();
                for (final FilterModel filter : filters) {

                    if(filter != null){
                        final LinearLayout ll = (LinearLayout) inflater.inflate(R.layout.filter_tag, null);
                        final TextView textView = ((TextView) ll.findViewById(R.id.tvFilter));
                        textView.setText(filter.getFilterName());

                        textView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                FilterModel filterModel = (FilterModel) v.getTag();
                                filterModel.setChecked(!filterModel.isChecked());

                                textView.setSelected(filterModel.isChecked());
                            }
                        });

                        textView.setTag(filter);
                        flow_layout.addView(ll);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }*/

    }


    @Override
    public void onClick(View v) {

        Intent intent;

        switch (v.getId()) {

            case R.id.tvMovie:

               // changeEntertainment(tvMovie);

                intent = new Intent(FilterActivity.this, EntertainmentActivity.class);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_MOVIE);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvTheatrePlay:

//                changeEntertainment(tvTheatrePlay);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_THEATER);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_THEATER);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvEvent:

            //    changeEntertainment(tvEvent);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_EVENT);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_EVENT);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvFestival:

                //changeEntertainment(tvFestival);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_FESTIVAL);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_FESTIVAL);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvNightClub:

                // changeHotels(tvNightClub);

                intent = new Intent(FilterActivity.this, CoffeeActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_NIGHTCLUB);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_NIGHTCLUB);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent, 110);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvLongue:

                //changeHotels(tvLongue);
                intent = new Intent(FilterActivity.this, CoffeeActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_LOUNGE);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_LOUNGE);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent, 110);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;
            case R.id.tvCoffee:

                // changeCoffee(tvCoffee);
                intent = new Intent(FilterActivity.this, CoffeeActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_FROM_COFFEE);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_COFFEE);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent, 110);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvAirplane:

                intent = new Intent(FilterActivity.this, TravelActivityFitler.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_FLIGHT);

                startActivityForResult(intent, AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvTrain:

                intent = new Intent(FilterActivity.this, TravelActivityFitler.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_TRAIN);

                startActivityForResult(intent, AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvBus:

                intent = new Intent(FilterActivity.this, TravelActivityFitler.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_BUS);

                startActivityForResult(intent, AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;

            case R.id.tvParty:

             //   changeHotels(tvParty);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_PARTY);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_PARTY);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.tvStandUpComedy:

               // changeHotels(tvStandUpComedy);

                break;

            case R.id.tvAdventure:

               // changeSports(tvAdventure);

                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_ADV_SPORTS);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_AVD_SPORTS);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvIndoor:

              //  changeSports(tvIndoor);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_INDOOR_SPORTS);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_INDOOR);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvOutdoor:

              //  changeSports(tvOutdoor);
                intent = new Intent(FilterActivity.this, EventListActivityFilter.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_OUTDOOR_SPORTS);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_OUTDOOR);
                intent.putExtra("selectedList", activity_id);
                startActivityForResult(intent,166);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvHobby:

                changeHobby(tvHobby);

                break;

            case R.id.tvOtherActivity:

                changeOtherActivity(tvOtherActivity);

                break;

        }
    }


    /*@Override
    public boolean onLongClick(View v) {

        switch (v.getId()) {
            case R.id.tvMovie:

                Intent intent = new Intent(this, CategoryTab.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvAirplane:

                Intent intent1 = new Intent(this, TravelActivity.class);
                intent1.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvBus:

                intent = new Intent(this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
*//*

            case R.id.tvCar:

                Intent intent3 = new Intent(this, TravelActivity.class);
                intent3.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                startActivity(intent3);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
*//*

            case R.id.tvTrain:

                Intent intent4 = new Intent(this, TravelActivityFitler.class);
                intent4.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_FILTER);
                startActivity(intent4);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvTheatrePlay:

                // changeEntertainment(tvTheatrePlay);

                break;
        }
        return true;
    }
*/

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeEntertainment(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeHotels(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_hotels_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_hotel));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeCoffee(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_coffee_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_coffee));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeTransport(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_transport_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_transport));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeSports(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_sports_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_sports));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }
    }

    //TODO for sahil.. no need to manually set color on selection.. just call setSelected(). xml selector will take care of it
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeHobby(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_hobby_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_hotel));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void changeOtherActivity(TextView textView) {

        if (textView.getCurrentTextColor() == getResources().getColor(R.color.filter_text)) {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_other_selected));
            textView.setTextColor(getResources().getColor(R.color.white));
        } else {
            textView.setBackground(getResources().getDrawable(R.drawable.rounded_other));
            textView.setTextColor(getResources().getColor(R.color.filter_text));
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AppConstants.ACTIVITY_TRAVEL_SCHEDULE) {

                if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_FLIGHT) {
                    changeTransport(tvAirplane);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_BUS) {
                    changeTransport(tvBus);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_TRAIN) {
                    changeTransport(tvTrain);
                }

                hasTravel = true;
                traveFrom = data.getExtras().getString(AppConstants.TRAVEL_FROM_KEY);
                travelTo = data.getExtras().getString(AppConstants.TRAVEL_TO_KEY);




                if (!activityType.contains(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE))) {
                    activityType.add(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE));
                }

                if (activityType.size() > 0) {
                    tvNext.setVisibility(View.VISIBLE);
                }

                Log.d("activtiy_id>>?", activityType.toString());
                Log.d("activtiy_id>>?", activity_id.toString());
               /* Intent intent = new Intent(FilterActivity.this,FilterChooserActivity.class);
                intent.putExtra("activity_type",data.getExtras().getInt(AppConstants.ACTIVITY_TYPE));
                intent.putExtra("activity_name",data.getExtras().getString(AppConstants.TRAVEL_FROM_KEY)+" To "+data.getExtras().getString(AppConstants.TRAVEL_TO_KEY));
                intent.putExtra("activity_to",data.getExtras().getString(AppConstants.TRAVEL_TO_KEY));
                intent.putExtra("activity_from",data.getExtras().getString(AppConstants.TRAVEL_FROM_KEY));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);*/
            } else if (requestCode == 166) {

                if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_PARTY) {
                    changeHotels(tvParty);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_EVENT) {
                    changeEntertainment(tvEvent);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_THEATER) {
                    changeEntertainment(tvTheatrePlay);
                }else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_INDOOR) {
                    changeSports(tvIndoor);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_OUTDOOR) {
                    changeSports(tvOutdoor);
                }else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_AVD_SPORTS) {
                    changeSports(tvAdventure);
                }
                if (!activityType.contains(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE))) {
                    activityType.add(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE));
                }

                if (!activityType.contains(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE))) {
                    activityType.add(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE));
                }
                activity_id.addAll(data.getStringArrayListExtra("selectedList"));

                //clear duplicates
                Set<String> hs = new HashSet<>();
                hs.addAll(activity_id);
                activity_id.clear();
                activity_id.addAll(hs);

                if (activity_id.size() > 0) {
                    tvNext.setVisibility(View.VISIBLE);
                }
                Log.d("activtiy_id>>?", activityType.toString());
                Log.d("activtiy_id>>?", activity_id.toString());
            }
            else if (requestCode == 110) {

                Log.d("activtiy_id>>?", "110");

                if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_COFFEE) {
                    changeCoffee(tvCoffee);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_NIGHTCLUB) {
                    changeHotels(tvNightClub);
                } else if (data.getExtras().getInt(AppConstants.ACTIVITY_TYPE) == AppConstants.API_ACTIVITY_ID_LOUNGE) {
                    changeHotels(tvLongue);
                }

                if (!activityType.contains(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE))) {
                    activityType.add(data.getExtras().getInt(AppConstants.ACTIVITY_TYPE));
                }

                activity_id.addAll(data.getStringArrayListExtra("selectedList"));

                //clear duplicates
                Set<String> hs = new HashSet<>();
                hs.addAll(activity_id);
                activity_id.clear();
                activity_id.addAll(hs);

                if (activity_id.size() > 0) {
                    tvNext.setVisibility(View.VISIBLE);
                }

                Log.d("activtiy_id>>?", activityType.toString());
                Log.d("activtiy_id>>?", activity_id.toString());
            }
        } else {

            if(getIntent().getStringArrayListExtra("selectedList") == null || getIntent().getStringArrayListExtra("selectedList").size() == 0) {
               // activity_id.clear();
                tvNext.setVisibility(View.GONE);
            }}
            // Toast.makeText(FilterActivity.this,"Fail",Toast.LENGTH_SHORT).show();

    }

    ArrayList<Integer> activityType = new ArrayList<>();
    ArrayList<String> activity_id = new ArrayList<>();
}
