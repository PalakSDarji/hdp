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

import com.hadippa.R;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener
        ,View.OnLongClickListener   {

    TextView tvMovie, tvTheatrePlay, tvEvent, tvFestival, tvNightClub,
            tvLongue, tvParty, tvStandUpComedy, tvCoffee, tvAirplane, tvCar, tvTrain, tvBus, tvAdventure, tvIndoor, tvOutdoor, tvHobby, tvOtherActivity;

    CustomAdapter customAdapter = new CustomAdapter();
    RecyclerView dateList;

    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        dateList = (RecyclerView) findViewById(R.id.dateList);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(FilterActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dateList.setLayoutManager(mLayoutManager);

        dateList.setAdapter(customAdapter);

        tvMovie = (TextView) findViewById(R.id.tvMovie);
        tvMovie.setOnClickListener(this);
        tvMovie.setOnLongClickListener(this);
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
        tvCar = (TextView) findViewById(R.id.tvCar);
        tvCar.setOnClickListener(this);
        tvTrain = (TextView) findViewById(R.id.tvTrain);
        tvTrain.setOnClickListener(this);
        tvBus = (TextView) findViewById(R.id.tvBus);
        tvBus.setOnClickListener(this);
        tvCar = (TextView) findViewById(R.id.tvCar);
        tvCar.setOnClickListener(this);
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

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tvMovie:

                changeEntertainment(tvMovie);

                break;

            case R.id.tvTheatrePlay:

                changeEntertainment(tvTheatrePlay);

                break;

            case R.id.tvEvent:

                changeEntertainment(tvEvent);

                break;

            case R.id.tvFestival:

                changeEntertainment(tvFestival);

                break;

            case R.id.tvNightClub:

                changeHotels(tvNightClub);

                break;

            case R.id.tvLongue:

                changeHotels(tvLongue);

                break;
            case R.id.tvParty:

                changeHotels(tvParty);

                break;
            case R.id.tvStandUpComedy:

                changeHotels(tvStandUpComedy);

                break;
            case R.id.tvCoffee:

                changeCoffee(tvCoffee);

                break;
            case R.id.tvAirplane:

                changeTransport(tvAirplane);

                break;

            case R.id.tvCar:

                changeTransport(tvCar);

                break;

            case R.id.tvTrain:

                changeTransport(tvTrain);

                break;

            case R.id.tvBus:

                changeTransport(tvBus);

                break;

            case R.id.tvAdventure:

                changeSports(tvAdventure);

                break;

            case R.id.tvIndoor:

                changeSports(tvIndoor);

                break;

            case R.id.tvOutdoor:

                changeSports(tvOutdoor);

                break;

            case R.id.tvHobby:

                changeHobby(tvHobby);

                break;

            case R.id.tvOtherActivity:

                changeOtherActivity(tvOtherActivity);

                break;

        }
    }




    @Override
    public boolean onLongClick(View v) {

        switch (v.getId())
        {
            case R.id.tvMovie:

                Intent intent = new Intent(this, CategoryTab.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case R.id.tvTheatrePlay:

                // changeEntertainment(tvTheatrePlay);

                break;
        }
        return true;
    }


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

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.choose_date_list_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

        }

        @Override
        public int getItemCount() {

            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     /*  private final TextView id;
        private final ImageView foodImage;
        private final TextView tvDonarName;
        private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
        private final View typeView;*/

        private final LinearLayout linearDate;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            linearDate = (LinearLayout)v.findViewById(R.id.linearDate);



            linearDate.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    Drawable drawable = linearDate.getBackground();
                    if(drawable.getConstantState()==(getResources().getDrawable(R.drawable.rounded_date)).getConstantState()){
                        v.setBackgroundResource(R.drawable.rounded_date_selected);
                    }else{
                        v.setBackgroundResource(R.drawable.rounded_date);
                    }
                }
            });
        }


        public LinearLayout getLinearDate() {
            return linearDate;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }
}
