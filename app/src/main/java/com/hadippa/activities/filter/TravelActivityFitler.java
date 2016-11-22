package com.hadippa.activities.filter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.commonclasses.location.GPSTracker;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.activities.CreateActivityActvity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TravelActivityFitler extends AppCompatActivity implements LocationListener {

    private ImageView imageBack;
    private HorizontalScrollView horScrollView;
    private RelativeLayout rlSubHeader;
    private TextView tvNext;
    private int activityKey;
    private TextView customTextView2;
    private LinearLayout llSelectFlight;
    private TextView tvSelectType;
    AutoCompleteTextView etFrom,etTo;
    CustomEditText etSelectFlight;
    GPSTracker gps;
    String latitude = "", longitude = "";
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private static final String LOG_TAG = "Google Places Autocomplete";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";

    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";

    private static final String OUT_JSON = "/json";


    private static final String API_KEY = "AIzaSyD5DyFre7np6MJ6MlZ-rEegKPxycXKBB8c";

    @BindView(R.id.tvAirplane)
    CustomTextView tvAirplane;

    @BindView(R.id.tvTrain)
    CustomTextView tvTrain;


    @BindView(R.id.tvBus)
    CustomTextView tvBus;

    int travet_type;
    String travel_type_;

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
                if (ActivityCompat.checkSelfPermission(TravelActivityFitler.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TravelActivityFitler.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            gps = new GPSTracker(TravelActivityFitler.this);
        }

        if (gps.canGetLocation()) {

            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

        } else {

            gps.showSettingsAlert();

        }


    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(TravelActivityFitler.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(TravelActivityFitler.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(TravelActivityFitler.this, new String[]{
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

        ButterKnife.bind(this);
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

        etFrom = (AutoCompleteTextView)findViewById(R.id.etFrom);
        etTo = (AutoCompleteTextView)findViewById(R.id.etTo);
        etFrom.setTypeface(Typeface.createFromAsset(getAssets(),"proxima_regular.OTF"));
        etTo.setTypeface(Typeface.createFromAsset(getAssets(),"proxima_regular.OTF"));
        etFrom.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        etTo.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));

        /*((ImageView)findViewById(R.id.shuffleplace)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String from =  etFrom.getText().toString().trim();
               String to = etTo.getText().toString().trim();

                etFrom.setText(to);
                etTo.setText(from);
            }
        });*/
        etSelectFlight = (CustomEditText)findViewById(R.id.etSelectFlight);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        horScrollView.setHorizontalScrollBarEnabled(false);

        tvAirplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                travel_type_ = "flight";
                travet_type = AppConstants.API_ACTIVITY_ID_FLIGHT;
                changeColor(tvAirplane,tvBus,tvTrain);
            }
        });

        tvBus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                travel_type_ = "bus";
                travet_type = AppConstants.API_ACTIVITY_ID_BUS;
                changeColor(tvBus,tvAirplane,tvTrain);
            }
        });



        tvTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                travel_type_ = "train";
                travet_type = AppConstants.API_ACTIVITY_ID_TRAIN;
                changeColor(tvTrain,tvBus,tvAirplane);
            }
        });

        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(AppConstants.ACTIVITY_TYPE,travet_type);
                intent.putExtra(AppConstants.TRAVEL_FROM_KEY,etFrom.getText().toString());
                intent.putExtra(AppConstants.TRAVEL_TO_KEY,etTo.getText().toString());

                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.slide_out_left,R.anim.slide_in_right);
            }
        });
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
                    Intent intent = new Intent(TravelActivityFitler.this, CreateActivityActvity.class);
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
                    Intent intent = new Intent(TravelActivityFitler.this, CreateActivityActvity.class);
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
                    Intent intent = new Intent(TravelActivityFitler.this, CreateActivityActvity.class);
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


    public static ArrayList autocomplete(String input) {
        ArrayList resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e("fff", "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e("fff", "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e("dddd", "C", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter implements Filterable {
        private ArrayList resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return String.valueOf(resultList.get(index));
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void changeColor(CustomTextView selected, CustomTextView unselected1, CustomTextView unselected2){

        selected.setBackground(getResources().getDrawable(R.drawable.rounded_transport_selected));
        selected.setTextColor(getResources().getColor(R.color.white));

        unselected1.setBackground(getResources().getDrawable(R.drawable.rounded_transport));
        unselected1.setTextColor(getResources().getColor(R.color.filter_text));

        unselected2.setBackground(getResources().getDrawable(R.drawable.rounded_transport));
        unselected2.setTextColor(getResources().getColor(R.color.filter_text));

    }
}
