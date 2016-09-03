package com.hadippa.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.fragments.main_screen.ShowCards;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener
        ,ShowCardsNew.OnFragmentInteractionListener {


    android.support.v4.app.Fragment fragment;
    android.support.v4.app.FragmentManager fm;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    DrawerLayout drawerLayout;
    ImageView drawerOpen,imageFilter;
    LinearLayout leftDrawer;
    TextView tvUserName;
    ImageView profileImage;
    LinearLayout linearNotification, linearPreference, linearHome, linearFeedback;
    EditText edtSearch;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_light));
        }

        initUI();



    }


    @Override
    public void onBackPressed() {

        if(fragment != null && ((ShowCardsNew)fragment).checkPanelState()){
            return;
        }
         else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

    void initUI(){
//ajksdbnkjasbdn
        drawerOpen = (ImageView) findViewById(R.id.drawerOpen);
        drawerOpen.setOnClickListener(this);
        imageFilter = (ImageView) findViewById(R.id.imageFilter);
        imageFilter.setOnClickListener(this);

        edtSearch = (EditText)findViewById(R.id.edtSearch);
        edtSearch.setOnClickListener(this);

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){

                    Intent intent1 = new Intent(HomeScreen.this, SearchActivity.class);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
        });

        findViewById(R.id.profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.profileImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.tvUserName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.llPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,PostActivity.class);
                startActivity(intent);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawer = (LinearLayout) findViewById(R.id.leftDrawer);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        profileImage = (ImageView)findViewById(R.id.profileImage);

        linearHome = (LinearLayout) findViewById(R.id.linearHome);
        linearHome.setOnClickListener(this);
        linearNotification = (LinearLayout) findViewById(R.id.linearNotification);
        linearNotification.setOnClickListener(this);
        linearPreference = (LinearLayout) findViewById(R.id.linearPreference);
        linearPreference.setOnClickListener(this);
        /*linearFollowling = (LinearLayout) findViewById(R.id.linearFollowling);
        linearFollowling.setOnClickListener(this);*/
        linearFeedback = (LinearLayout) findViewById(R.id.linearFeedback);
        linearFeedback.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fragment = new ShowCardsNew();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "home_fragment");
        fragmentTransaction.commit();

        findViewById(R.id.linearListWithHadipaa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeScreen.this, ListHadipaaActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.linearPreference).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentPreference = new Intent(HomeScreen.this, Preference.class);
                startActivity(intentPreference);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch ((v.getId())){

            case R.id.edtSearch:

                Intent intent1_ = new Intent(HomeScreen.this, SearchActivity.class);
                startActivity(intent1_);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;
            case R.id.drawerOpen:

                drawerLayout.openDrawer(Gravity.LEFT);

                break;

            case R.id.imageFilter:

                Intent intent1 = new Intent(HomeScreen.this, FilterActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
