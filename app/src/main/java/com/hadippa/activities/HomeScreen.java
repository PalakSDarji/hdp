package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.hadippa.fragments.search.SearchCity;
import com.hadippa.model.DataModel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener
        ,ShowCardsNew.OnFragmentInteractionListener {

    public static final int REQUEST_CODE_MY_PLAN = 50;
    public static final int REQUEST_CODE_RECENT_PLAN = 51;

    public static ArrayList<DataModel> rollBackIds = new ArrayList<>();

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
    public static EditText edtSearch;
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



    }


    @Override
    public void onBackPressed() {

        if(fragment != null && ((ShowCardsNew)fragment).checkPanelState()){
            return;
        }
         else {
            super.onBackPressed();
           // overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
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
        edtSearch.setText(sp.getString("cityName","Search"));

        try {
            jsonObject = new JSONObject(sp.getString("userData",""));
            tvUserName.setText(jsonObject.getString("first_name")+" "+jsonObject.getString("last_name"));

            tvFollowersCount.setText(sp.getInt("myFollowersCount",0)+"");
            tvFollowingCount.setText(sp.getInt("myFollowingCount",0)+"");

                Glide.with(HomeScreen.this)
                        .load(jsonObject.getString("profile_photo"))
                        .into(profileImage);

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
                intent.putExtra("tab",0);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.rlFollowing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeScreen.this,FollowingMain.class);
                intent.putExtra("tab",1);
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

        findViewById(R.id.linearSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomeScreen.this, SettingActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                startActivityForResult(intent,REQUEST_CODE_MY_PLAN);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.linearMyBooking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HomeScreen.this, MyBookingActivity.class);
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

        findViewById(R.id.linearLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.clear();
                editor.commit();

                Intent intent =  new Intent(HomeScreen.this,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.linearFeedback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);

/* Fill it with Data */
                emailIntent.setType("plain/text");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"support@gohadipaa.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");

/* Send it off to the Activity-Chooser */
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
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


    void setData(){

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == REQUEST_CODE_MY_PLAN){
                Intent intent = new Intent(HomeScreen.this, HistoryPlan.class);
                startActivityForResult(intent,REQUEST_CODE_RECENT_PLAN);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else if(requestCode == REQUEST_CODE_RECENT_PLAN){
                Intent intent = new Intent(HomeScreen.this, MyPlan.class);
                startActivityForResult(intent,REQUEST_CODE_MY_PLAN);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }
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

    private void fetchPosts() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {


            requestParams.add("city", sp.getString("cityName",""));
            requestParams.add("access_token", sp.getString("access_token",""));

            Log.d("prepareMeraEvents", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.FETCH_POST, requestParams,
                new FetchPosts());
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
    class FetchPosts extends AsyncHttpResponseHandler {

        ProgressDialog progressDialog;

        @Override
        public void onStart() {
            super.onStart();

            progressDialog = new ProgressDialog(HomeScreen.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            //    showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
             dismissDialog();

        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {


                String response = new String(responseBody, "UTF-8");
                Log.d("restaurantsBeanList", "Size >> " + response);
                JSONObject obj = new JSONObject(response);

                if(obj.getBoolean("success")) {
                    editor.putString("posts", obj.getJSONArray("posts").toString());
                    editor.commit();

                }

                fm = getSupportFragmentManager();
                fragment = new ShowCardsNew();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "home_fragment");
                fragmentTransaction.commit();

                progressDialog.dismiss();
                Log.d("restaurantsBeanList", "Size >> " + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }




    BroadcastReceiver updateActivity = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

                fm = getSupportFragmentManager();
                fragment = new ShowCardsNew();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "home_fragment");
                fragmentTransaction.commit();



        }
    };

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(drawerLayout,intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("broadCast?","yes");

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
        registerReceiver(updateActivity, new IntentFilter("update_activity"));
    }

    @Override
    protected void onResume() {
        super.onResume();

        sp = PreferenceManager.getDefaultSharedPreferences(HomeScreen.this);
        editor = sp.edit();


        initUI();


        if(SearchCity.updatePost){
            SearchCity.updatePost = false;
            fetchPosts();

            HomeScreen.edtSearch.setText(sp.getString("cityName","Search"));


        }

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("broadCast?","no");
        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(updateActivity);
    }

}
