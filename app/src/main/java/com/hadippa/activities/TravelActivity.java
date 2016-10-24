package com.hadippa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;

public class TravelActivity extends AppCompatActivity {

    private ImageView imageBack;
    private HorizontalScrollView horScrollView;
    private RelativeLayout rlSubHeader;
    private TextView tvNext;
    private int activityKey;
    private TextView customTextView2;
    private LinearLayout llSelectFlight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        horScrollView = (HorizontalScrollView) findViewById(R.id.horScrollView);
        rlSubHeader = (RelativeLayout) findViewById(R.id.rlSubHeader);
        tvNext = (TextView) findViewById(R.id.tvNext);
        customTextView2 = (TextView) findViewById(R.id.customTextView2);
        llSelectFlight = (LinearLayout) findViewById(R.id.llSelectFlight);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        horScrollView.setHorizontalScrollBarEnabled(false);

        if(activityKey == AppConstants.ACTIVITY_TRAVEL_FROM_POST){

            horScrollView.setVisibility(View.GONE);
            rlSubHeader.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            llSelectFlight.setVisibility(View.VISIBLE);
            customTextView2.setText(getResources().getString(R.string.write_plane_route));

            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TravelActivity.this, CreateActivityActvity.class);
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
        else if(activityKey == AppConstants.ACTIVITY_TRAVEL_FROM_FILTER){

            horScrollView.setVisibility(View.VISIBLE);
            rlSubHeader.setVisibility(View.VISIBLE);
            tvNext.setVisibility(View.VISIBLE);
            llSelectFlight.setVisibility(View.GONE);
            customTextView2.setText(getResources().getString(R.string.select_activity));
        }
    }
}
