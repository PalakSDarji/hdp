package com.hadippa.activities.filter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.activities.CreateActivityActvity;
import com.hadippa.model.NightCLubModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CoffeeActivityFilter extends AppCompatActivity implements LocationListener {

    private RecyclerView listShops;
    private ImageView imageBack;
    private TextView tvHeader1;
    private int activityKey;
    @BindView(R.id.ivActivityIcon)
    ImageView ivActivityIcon;
    CustomAdapter customAdapter = new CustomAdapter();
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.tvNext)
    CustomTextView tvNext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    int pageNumber = 0;
    List<NightCLubModel.ResponseBean.RestaurantsBean> restaurantsBeanList = new ArrayList<>();


    GPSTracker gps;
    String latitude = "", longitude = "";
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    int activity_id_;
    ArrayList<String> activity_id = new ArrayList<>();

    void getLocation() {

        mLocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());

                prepareThings(pageNumber);
                Log.d("locaGPS>>", latitude + ">>>" + longitude);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(CoffeeActivityFilter.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CoffeeActivityFilter.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    prepareThings(pageNumber);

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
            gps = new GPSTracker(CoffeeActivityFilter.this);
        }

        if (gps.canGetLocation()) {
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

            prepareThings(pageNumber);

        } else {

            gps.showSettingsAlert();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_coffee);
        ButterKnife.bind(this);
        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0);

        if(getIntent().getStringArrayListExtra("selectedList")!=null){
            activity_id = getIntent().getStringArrayListExtra("selectedList");
        }


        tvNext.setVisibility(View.VISIBLE);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_id.size() > 0) {
                    Intent intent = new Intent();

                    intent.putExtra(AppConstants.ACTIVITY_TYPE, activity_id_);
                    intent.putExtra("activity_id", activity_id);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                } else {
                    Toast.makeText(CoffeeActivityFilter.this, "Please select atleast one to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.putExtra("selectedList",activity_id);
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        Log.d("getLOcation>>", latitude + " " + longitude);
        ivActivityIcon.setImageResource(R.drawable.zomato_spoon);
        tvHeader1 = (TextView) findViewById(R.id.tvHeader1);
        listShops = (RecyclerView) findViewById(R.id.listShops);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShops.setLayoutManager(mLayoutManager);


        listShops.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");

                            prepareThings(pageNumber);
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }
            }
        });


        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }


    }


    void prepareThings(int pageNumber) {

        JSONObject user_preference = null;
        int dist = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CoffeeActivityFilter.this);
        try {
            user_preference = new JSONObject(sharedPreferences.getString("user_preference", ""));
            dist = Integer.parseInt(user_preference.getString("radius")) * 1000;
            if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE) {

                activity_id_ = AppConstants.API_ACTIVITY_ID_COFFEE;
                tvHeader1.setText(getString(R.string.coffee_cafe));
                edtSearch.setHint(getString(R.string.select_cafe));
                prepareZomato(AppConstants.CAFES, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            } else if (activityKey == AppConstants.ACTIVITY_NIGHTCLUB) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_NIGHTCLUB;
                tvHeader1.setText(getString(R.string.night_club));
                edtSearch.setHint(getString(R.string.select_club));
                prepareZomato(AppConstants.NIGHTCLUB, latitude, longitude, String.valueOf(dist), String.valueOf(pageNumber));
            } else if (activityKey == AppConstants.ACTIVITY_LOUNGE) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_LOUNGE;
                tvHeader1.setText(getString(R.string.lounge));
                edtSearch.setHint(getString(R.string.select_lounge));
                prepareZomato(AppConstants.LOUNGE, latitude, longitude, String.valueOf(dist), String.valueOf(pageNumber));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(CoffeeActivityFilter.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(CoffeeActivityFilter.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(CoffeeActivityFilter.this, new String[]{
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


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_coffee_longue_night_club, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final NightCLubModel.ResponseBean.RestaurantsBean restaurantsBean = restaurantsBeanList.get(position);

            viewHolder.address.setText(restaurantsBean.getRestaurant().getLocation().getAddress());
            viewHolder.name.setText(restaurantsBean.getRestaurant().getName());
            viewHolder.rating.setText(restaurantsBean.getRestaurant().getUser_rating().getAggregate_rating());
            viewHolder.distance.setText(AppConstants.distanceMeasure(Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLatitude()),
                    Double.parseDouble(restaurantsBean.getRestaurant().getLocation().getLongitude())) + " kms");

            if (restaurantsBean.getRestaurant().getThumb().isEmpty() || restaurantsBean.getRestaurant().getThumb().equals("")) {
                viewHolder.profileImage.setImageResource(R.drawable.place_holder);
            } else {
                Glide.with(CoffeeActivityFilter.this)
                        .load(restaurantsBean.getRestaurant().getThumb())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.profileImage);
            }

            if (activity_id != null) {
                if (restaurantsBean.getRestaurant().getId() != null) {
                    if (activity_id.contains(restaurantsBean.getRestaurant().getId())) {
                        viewHolder.rlContainer.setSelected(true);
                    } else {
                        viewHolder.rlContainer.setSelected(false);
                    }
                }
            }

            Log.d("image>>>", position + " Postion Url >> " + restaurantsBean.getRestaurant().getThumb());

            viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*Intent intent = new Intent(CoffeeActivityFilter.this, CreateActivityActvity.class);
                    Log.d("getIntent().", activityKey + "");
                    intent.putExtra(AppConstants.ACTIVITY_KEY, getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0));
                    intent.putExtra("activity_id", getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("data", restaurantsBean);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

                    if (activity_id.contains(restaurantsBean.getRestaurant().getId())) {
                        activity_id.remove(restaurantsBean.getRestaurant().getId());

                    } else {
                        activity_id.add(restaurantsBean.getRestaurant().getId());
                    }

                    notifyDataSetChanged();


                }
            });

        }


        @Override
        public int getItemCount() {

            return restaurantsBeanList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlContainer;
        CustomTextView address, name, rating, type, distance, time;
        RoundedImageView profileImage;

        public ViewHolder(final View v) {
            super(v);

            address = (CustomTextView) v.findViewById(R.id.address);
            name = (CustomTextView) v.findViewById(R.id.name);
            rating = (CustomTextView) v.findViewById(R.id.rating);
            type = (CustomTextView) v.findViewById(R.id.type);
            distance = (CustomTextView) v.findViewById(R.id.distance);
            time = (CustomTextView) v.findViewById(R.id.time);

            profileImage = (RoundedImageView) v.findViewById(R.id.profileImage);

            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);

        }

        //,,
        /*public LinearLayout getLinearDate() {
            return linearDate;
        }*/


    }

    //zomato activity

    SharedPreferences sp;


    private void prepareZomato(String searchFor, String lat, String lon, String radius, String start) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        sp = PreferenceManager.getDefaultSharedPreferences(CoffeeActivityFilter.this);

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("lat", lat);
            requestParams.add("lon", lon);
            requestParams.add("radius", radius);
            requestParams.add("start", start);

            Log.d("prepareZomato>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.ZOMATO + searchFor, requestParams,
                new CallZomato());
    }

    class CallZomato extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(CoffeeActivityFilter.this, "Please Wait");
            Log.d("prepareZomato>>", "success exc  >> start");
        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
            Log.d("prepareZomato>>", "success exc  >> finish");
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
           /* Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
*/
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                AppConstants.dismissDialog();
                JSONObject obj = new JSONObject(response);
                Gson gson = new Gson();
                NightCLubModel nightCLubModel = gson.fromJson(obj.toString(), NightCLubModel.class);
                if (nightCLubModel.isSuccess()) {

                    if (pageNumber == 0) {
                        restaurantsBeanList = nightCLubModel.getResponse().getRestaurants();
                        customAdapter = new CustomAdapter();
                        listShops.setAdapter(customAdapter);
                    } else {
                        loading = true;
                        restaurantsBeanList.addAll(nightCLubModel.getResponse().getRestaurants());
                        customAdapter.notifyDataSetChanged();
                    }

                    if (nightCLubModel.getResponse().getRestaurants().size() == 0) {

                    }

                    pageNumber = pageNumber + 20;
                } else {
                    Toast.makeText(CoffeeActivityFilter.this, nightCLubModel.getErrors(), Toast.LENGTH_SHORT).show();
                }
                Log.d("prepareZomato>>", "Size >> " + response);
                Log.d("prepareZomato>>", "Size >> " + restaurantsBeanList.size());
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("prepareZomato>>", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            Log.d("prepareZomato>>", "success exc  >>" + error.toString());
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }
}
