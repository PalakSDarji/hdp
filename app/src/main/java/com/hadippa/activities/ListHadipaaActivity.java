package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListHadipaaActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageBack;

    @BindView(R.id.tvStandUpCom)
    CustomTextView tvStandUpCom;

    @BindView(R.id.tvParty)
    CustomTextView tvParty;

    @BindView(R.id.tvLiveMusic)
    CustomTextView tvLiveMusic;

    @BindView(R.id.tvAdventureSports)
    CustomTextView tvAdventureSports;

    @BindView(R.id.tvTheatrePlay)
    CustomTextView tvTheatrePla;

    @BindView(R.id.tvEvent)
    CustomTextView tvEvent;

    @BindView(R.id.tvHobbyClasses)
    CustomTextView tvHobbyClasses;

    @BindView(R.id.tvFestival)
    CustomTextView tvFestival;

    @BindView(R.id.tvIndoor)
    CustomTextView tvIndoor;

    @BindView(R.id.tvOutdoor)
    CustomTextView tvOutdoor;

    ArrayList<CustomTextView> customTextViews = new ArrayList<>();

    String cateGory = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hadipaa);
        ButterKnife.bind(this);

        fillarrayList();
        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(cateGory.equals("")){
                    Toast.makeText(ListHadipaaActivity.this,"Select any one.",Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intentPreference = new Intent(ListHadipaaActivity.this, ListHadipaaDetailActivity.class);
                intentPreference.putExtra("category",cateGory);
                startActivity(intentPreference);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        tvAdventureSports.setOnClickListener(this);
        tvEvent.setOnClickListener(this);
        tvFestival.setOnClickListener(this);
        tvHobbyClasses.setOnClickListener(this);
        tvIndoor.setOnClickListener(this);
        tvOutdoor.setOnClickListener(this);
        tvLiveMusic.setOnClickListener(this);
        tvStandUpCom.setOnClickListener(this);
        tvParty.setOnClickListener(this);
        tvTheatrePla.setOnClickListener(this);
    }

    void textSelect(View view){

    }

    void selectText(int id){

        for(int i =0; i < customTextViews.size();i++){

            if(customTextViews.get(i).getId() == id){

                customTextViews.get(i).setSelected(true);
                customTextViews.get(i).setTextColor(getResources().getColor(R.color.white));
                cateGory = customTextViews.get(i).getText().toString().trim();
            }else{
                customTextViews.get(i).setSelected(false);
                customTextViews.get(i).setTextColor(getResources().getColor(R.color.c_f55158));
            }
        }

    }

    void fillarrayList(){

        customTextViews.add(tvAdventureSports);
        customTextViews.add(tvEvent);
        customTextViews.add(tvFestival);
        customTextViews.add(tvHobbyClasses);
        customTextViews.add(tvIndoor);
        customTextViews.add(tvOutdoor);
        customTextViews.add(tvLiveMusic);
        customTextViews.add(tvStandUpCom);
        customTextViews.add(tvParty);
        customTextViews.add(tvTheatrePla);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
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

    @Override
    public void onClick(View view) {

        Toast.makeText(ListHadipaaActivity.this,((CustomTextView)findViewById(view.getId())).getText().toString().trim(),Toast.LENGTH_SHORT).show();
        selectText(view.getId());
    }
}
