package com.hadippa.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonclasses.location.GPSTracker;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.R;

public class TravelActivity extends AppCompatActivity implements LocationListener {

    private ImageView imageBack;
    private HorizontalScrollView horScrollView;
    private RelativeLayout rlSubHeader;
    private TextView tvNext;
    private int activityKey;
    private TextView customTextView2;
    private LinearLayout llSelectFlight;
    private TextView tvSelectType;
    CustomEditText etFrom,etTo,etSelectFlight;
    GPSTracker gps;
    String latitude = "", longitude = "";
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

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
                if (ActivityCompat.checkSelfPermission(TravelActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TravelActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            gps = new GPSTracker(TravelActivity.this);
        }

        if (gps.canGetLocation()) {

            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

        } else {

            gps.showSettingsAlert();

        }


    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(TravelActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(TravelActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(TravelActivity.this, new String[]{
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
        setContentView(R.layout.activity_travel);

        if(checkPermission()){
            getLocation();
        }else{
            requestPermission();
        }
        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        horScrollView = (HorizontalScrollView) findViewById(R.id.horScrollView);
        rlSubHeader = (RelativeLayout) findViewById(R.id.rlSubHeader);
        tvNext = (TextView) findViewById(R.id.tvNext);
        customTextView2 = (TextView) findViewById(R.id.customTextView2);
        llSelectFlight = (LinearLayout) findViewById(R.id.llSelectFlight);
        tvSelectType  = (TextView) findViewById(R.id.tvSelectType);

        etFrom = (CustomEditText)findViewById(R.id.etFrom);
        etTo = (CustomEditText)findViewById(R.id.etTo);
        etSelectFlight = (CustomEditText)findViewById(R.id.etSelectFlight);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        horScrollView.setHorizontalScrollBarEnabled(false);

        if(activityKey == AppConstants.ACTIVITY_TRAVEL_FROM_POST_AIR){

            horScrollView.setVisibility(View.GONE);
            rlSubHeader.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            llSelectFlight.setVisibility(View.VISIBLE);
            tvSelectType.setText(getString(R.string.select_fight));
            customTextView2.setText(getResources().getString(R.string.write_plane_route));

            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TravelActivity.this, CreateActivityActvity.class);
                    intent.putExtra(AppConstants.TRAVEL_FROM_KEY,etFrom.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_TO_KEY,etTo.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_BY_KEY,etSelectFlight.getText().toString().trim());
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                    intent.putExtra("activity_id",getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
        else if(activityKey == AppConstants.ACTIVITY_TRAVEL_FROM_POST_BUS){

            horScrollView.setVisibility(View.GONE);
            rlSubHeader.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            llSelectFlight.setVisibility(View.VISIBLE);
            tvSelectType.setText(getString(R.string.select_bus));
            customTextView2.setText(getResources().getString(R.string.write_bus_route));

            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TravelActivity.this, CreateActivityActvity.class);
                    intent.putExtra(AppConstants.TRAVEL_FROM_KEY,etFrom.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_TO_KEY,etTo.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_BY_KEY,etSelectFlight.getText().toString().trim());
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                    intent.putExtra("activity_id",getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
        }
        else if(activityKey == AppConstants.ACTIVITY_TRAVEL_FROM_POST_TRAIN){

            horScrollView.setVisibility(View.GONE);
            rlSubHeader.setVisibility(View.GONE);
            tvNext.setVisibility(View.VISIBLE);
            llSelectFlight.setVisibility(View.VISIBLE);
            tvSelectType.setText(getString(R.string.select_train));
            customTextView2.setText(getResources().getString(R.string.write_train_route));

            tvNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TravelActivity.this, CreateActivityActvity.class);
                    intent.putExtra(AppConstants.TRAVEL_FROM_KEY,etFrom.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_TO_KEY,etTo.getText().toString().trim());
                    intent.putExtra(AppConstants.TRAVEL_BY_KEY,etSelectFlight.getText().toString().trim());
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_TRAVEL_SCHEDULE);
                    intent.putExtra("activity_id",getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("latitude",latitude);
                    intent.putExtra("longitude",longitude);
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
