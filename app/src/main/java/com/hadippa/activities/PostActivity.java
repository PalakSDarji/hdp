package com.hadippa.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.hadippa.model.Activities;
import com.hadippa.model.DataModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.hadippa.AppConstants.ACTIVITY_ADV_SPORTS;

public class PostActivity extends AppCompatActivity implements LocationListener {

    Dialog dialog1;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    List<Activities.ActivitiesBean> activitiesBeanList = new ArrayList<>();

    GPSTracker gps;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    String latitude = "", longitude = "";
    
    void getLocation() {

        mLocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());


                Log.d("locaGPS>>", latitude + ">>>" + longitude);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    requestPermission();
                    return;
                }
                Location lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (lastKnownLocation != null) {
                    latitude = String.valueOf(lastKnownLocation.getLatitude());
                    longitude = String.valueOf(lastKnownLocation.getLongitude());


                    Log.d("locaGPS>>", latitude + ">>>" + longitude);

                } else {


                }

            }

            @Override
            public void onProviderDisabled(String provider) {
            }
        };
        mLocationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 500, 10, mLocationListener);

        if (gps == null) {
            gps = new GPSTracker(PostActivity.this);
        }

        if (gps.canGetLocation()) {
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());



        } else {

            gps.showSettingsAlert();

        }


    }
    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(PostActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(PostActivity.this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 10001);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 10001:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  Snackbar.make(rel, "Permission Granted.", Snackbar.LENGTH_LONG).show();

                    getLocation();
                } else {

                    //  Snackbar.make(rel, "Permission Denied.", Snackbar.LENGTH_LONG).show();

                }
                break;
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post2);

        sp = PreferenceManager.getDefaultSharedPreferences(PostActivity.this);
        editor = sp.edit();

        if(checkPermission()){
            getLocation();
        }else{
            requestPermission();
        }
        Gson gson = new Gson();
        Activities dataModel = gson.fromJson(sp.getString("activityType", ""), Activities.class);
        activitiesBeanList = dataModel.getActivities();

        initUi();

    }

    private void initUi() {

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.ivSports).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSportsDialog();
            }
        });

        findViewById(R.id.ivCoffee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_FROM_COFFEE);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_COFFEE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivGlass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrinksDialog();
            }
        });

        findViewById(R.id.ivHobby).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_HOBBY);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_HOBBY);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivPlusBlue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_CREATE_ACTIVITY);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        findViewById(R.id.ivTravel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTravelDialog();
               /* Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });

        findViewById(R.id.ivEntertainment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEntertainmentDialog();
               /* Intent intent = new Intent(PostActivity.this, EntertainmentActivity.class);
               // intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_FROM_POST);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });
    }


    void showTravelDialog() {

        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_travel);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivAir = (ImageView) dialog1.findViewById(R.id.ivAir);
        ivAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Log.d("activity_id>>",activitiesBeanList.get(7).getId()+"");
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_POST_AIR);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_FLIGHT);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivBus = (ImageView) dialog1.findViewById(R.id.ivBus);
        ivBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Log.d("activity_id>>",activitiesBeanList.get(9).getId()+"");
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_POST_BUS);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_BUS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivTrain = (ImageView) dialog1.findViewById(R.id.ivTrain);
        ivTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Log.d("activity_id>>",activitiesBeanList.get(8).getId()+"");
                Intent intent = new Intent(PostActivity.this, TravelActivity.class);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_TRAIN);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_TRAVEL_FROM_POST_TRAIN);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
    }

    void showEntertainmentDialog() {

        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_entertainment);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivMovie = (ImageView) dialog1.findViewById(R.id.ivMovie);
        ivMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EntertainmentActivity.class);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_MOVIE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivPlay = (ImageView) dialog1.findViewById(R.id.ivPlay);
        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_THEATER);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_THEATER);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivEvent = (ImageView) dialog1.findViewById(R.id.ivEvent);
        ivEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_EVENT);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_EVENT);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        ImageView ivFestival = (ImageView) dialog1.findViewById(R.id.ivFestival);
        ivFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_FESTIVAL);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
    }

    void showSportsDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_sports);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivAdve = (ImageView) dialog1.findViewById(R.id.ivAdve);
        ivAdve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra("activity_id",AppConstants.API_ACTIVITY_ID_AVD_SPORTS);
                intent.putExtra(AppConstants.ACTIVITY_KEY, ACTIVITY_ADV_SPORTS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivIndoor = (ImageView) dialog1.findViewById(R.id.ivIndoor);
        ivIndoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra("activity_id",AppConstants.API_ACTIVITY_ID_INDOOR);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_INDOOR_SPORTS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView outIndoor = (ImageView) dialog1.findViewById(R.id.ivOutdoor);
        outIndoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_OUTDOOR);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_OUTDOOR_SPORTS);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
    }


    void showSportsAdveDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_sports_adve);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivSport = (ImageView) dialog1.findViewById(R.id.ivSport);
        ivSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                showSportsDialog();
            }
        });

        dialog1.show();
    }

    void showDrinksDialog() {


        /*Bitmap f = fastblur(v,15);*/
        dialog1 = new Dialog(this, R.style.DialogSlideAnim);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setContentView(R.layout.dialog_drinks);
        dialog1.setCanceledOnTouchOutside(false);
        dialog1.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //style id
        WindowManager.LayoutParams params = dialog1.getWindow().getAttributes();

        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;

        dialog1.getWindow().setAttributes(params);
        //Drawable d = new BitmapDrawable(getResources(), blurred);

        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));

        ImageView ivClose = (ImageView) dialog1.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });

        ImageView ivNightClub = (ImageView) dialog1.findViewById(R.id.ivNightClub);
        ivNightClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_NIGHTCLUB);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_NIGHTCLUB);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivLounge = (ImageView) dialog1.findViewById(R.id.ivLounge);
        ivLounge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CoffeeActivity.class);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_COFFEE);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_LOUNGE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivParty = (ImageView) dialog1.findViewById(R.id.ivParty);
        ivParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, EventListActivity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_EVENT_PARTY);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_PARTY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageView ivStandup = (ImageView) dialog1.findViewById(R.id.ivStandup);
        ivStandup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent = new Intent(PostActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_STANDUP_COMEDY);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("activity_id", AppConstants.API_ACTIVITY_ID_STAND_UP_COMEDY);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        dialog1.show();
    }


}
