package com.hadippa.activities;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.main_screen.ShowCardsNew;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener
        ,ShowCardsNew.OnFragmentInteractionListener {


    android.support.v4.app.Fragment fragment;
    android.support.v4.app.FragmentManager fm;
    android.support.v4.app.FragmentTransaction fragmentTransaction;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    DrawerLayout drawerLayout;
    ImageView drawerOpen,imageFilter;
    LinearLayout leftDrawer;

    @BindView(R.id.profileImage)
    ImageView profileImage;

    LinearLayout linearNotification, linearPreference, linearHome, linearFeedback;
    EditText edtSearch;
    private LinearLayout linearMyPlan;
    @BindView(R.id.rlSearch) RelativeLayout rlSearch;
    @BindView(R.id.ivIcon) ImageView ivIcon;

    @BindView(R.id.tvFollowersCount)
    CustomTextView tvFollowersCount;

    @BindView(R.id.tvUserName)
    CustomTextView tvUserName;

    @BindView(R.id.tvFollowingCount)
    CustomTextView tvFollowingCount;

    JSONObject jsonObject;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(getResources().getColor(R.color.purple_light));
        }

        sp = PreferenceManager.getDefaultSharedPreferences(HomeScreen.this);
        editor = sp.edit();

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

        tvFollowersCount.setText(sp.getInt("follower",0)+"");
        tvFollowingCount.setText(sp.getInt("following",0)+"");
        
        try {
            jsonObject = new JSONObject(sp.getString("userData",""));
            tvUserName.setText(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));
            if(jsonObject.getString("profile_photo").equals(null) || jsonObject.getString("profile_photo").equals("")){

                Log.v("Home","Profile pic "+ jsonObject.getString("profile_photo"));
                profileImage.setImageResource(R.drawable.ic_user_avatar_default_white);
            }
            else {
                Log.v("Home","Profile pic url "+ jsonObject.getString("profile_photo"));
                Glide.with(HomeScreen.this)
                        .load(jsonObject.getString("profile_photo"))
                        .into(profileImage);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


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

        findViewById(R.id.relChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this,ChatListActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.rlProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent intent = new Intent(HomeScreen.this,ProfileActivity.class);
                    intent.putExtra(AppConstants.PROFILE_KEY, AppConstants.MY_PROFILE);
                    intent.putExtra(AppConstants.FETCH_USER_KEY, String.valueOf(jsonObject.getInt("id")));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

     /*   findViewById(R.id.tvUserName).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,ProfileActivity.class);
                startActivity(intent);
            }
        });*/

        findViewById(R.id.rlFollowers).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,FollowingMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.rlFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,FollowingMain.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.llPost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,PostActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftDrawer = (LinearLayout) findViewById(R.id.leftDrawer);

        profileImage = (ImageView)findViewById(R.id.profileImage);

        linearHome = (LinearLayout) findViewById(R.id.linearHome);
        linearHome.setOnClickListener(this);
        linearNotification = (LinearLayout) findViewById(R.id.linearNotification);
        linearNotification.setOnClickListener(this);
        linearMyPlan = (LinearLayout) findViewById(R.id.linearMyPlan);
        linearMyPlan.setOnClickListener(this);
        linearPreference = (LinearLayout) findViewById(R.id.linearPreference);
        linearPreference.setOnClickListener(this);
        linearFeedback = (LinearLayout) findViewById(R.id.linearFeedback);
        linearFeedback.setOnClickListener(this);

        fm = getSupportFragmentManager();
        fragment = new ShowCardsNew();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "home_fragment");
        fragmentTransaction.commit();

        findViewById(R.id.linearHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.linearListWithHadipaa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeScreen.this, ListHadipaaActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.linearMyPlan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, MyPlan.class);
                startActivity(intent);
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

        rlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1_ = new Intent(HomeScreen.this, SearchActivity.class);
                startActivity(intent1_);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ivIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch ((v.getId())){

            case R.id.drawerOpen:

                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.imageFilter:

                Intent intent1 = new Intent(HomeScreen.this, FilterActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;


            case R.id.linearNotification:

                Intent intent2 = new Intent(HomeScreen.this, NotificationActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
