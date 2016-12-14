package com.hadippa.activities.filter;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.activities.BaseActionsActivity;
import com.hadippa.activities.CoffeeActivity;
import com.hadippa.activities.CreateActivityActvity;
import com.hadippa.activities.FilterActivity;
import com.hadippa.activities.FilterChooserActivity;
import com.hadippa.model.FilterModel;
import com.hadippa.model.FilterSelection;
import com.hadippa.model.MeraEventPartyModel;
import com.hadippa.model.NightCLubModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class CoffeeActivityFilter extends BaseActionsActivity implements LocationListener, View.OnClickListener {

    private RecyclerView listShops;
    private ImageView imageBack;
    private TextView tvHeader1;
    private int activityKey;
    @BindView(R.id.ivActivityIcon)
    ImageView ivActivityIcon;
    CustomAdapter customAdapter = new CustomAdapter();
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @BindView(R.id.llActivitiesContainer)
    LinearLayout llActivitiesContainer;

    @BindView(R.id.tvNext)
    CustomTextView tvNext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    int pageNumber = 0;
    List<NightCLubModel.ResponseBean.RestaurantsBean> restaurantsBeanList = new ArrayList<>();
    public static EventAdapter adapter;

    List<MeraEventPartyModel.DataBean> postBeanList = new ArrayList<>();

    GPSTracker gps;
    String latitude = "", longitude = "";
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

    int activity_id_;
    ArrayList<String> activity_id = new ArrayList<>();
    ArrayList<FilterSelection> filterModels = new ArrayList<>();

    boolean callZomato = true;

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

    @BindView(R.id.tvTabMovie) CustomTextView tvTabMovie;
    @BindView(R.id.tvTabTheatrePlay) CustomTextView tvTabTheatrePlay;
    @BindView(R.id.tvTabEvent) CustomTextView tvTabEvent;
    @BindView(R.id.tvTabFestival) CustomTextView tvTabFestival;
    @BindView(R.id.tvTabNightclub) CustomTextView tvTabNightclub;
    @BindView(R.id.tvTabLounge) CustomTextView tvTabLounge;
    @BindView(R.id.tvTabParty) CustomTextView tvTabParty;
    @BindView(R.id.tvTabStandupComedy) CustomTextView tvTabStandupComedy;
    @BindView(R.id.tvTabCoffee) CustomTextView tvTabCoffee;
    @BindView(R.id.tvTabIndoorSports) CustomTextView tvTabIndoorSports;
    @BindView(R.id.tvTabOutdoorSports) CustomTextView tvTabOutdoorSports;
    @BindView(R.id.tvTabHobbyClass) CustomTextView tvTabHobbyClass;
    @BindView(R.id.tvTabAdventure) CustomTextView tvTabAdventure;

    @Override
    public void onClick(View v) {



        switch (v.getId()){


            case R.id.tvTabMovie:

                if(tvTabMovie.isSelected()){

                }else{
                    pageNumber = 0;
                    setCurrentActionView(this,v.getId());
                   /* activityKey = AppConstants.ACTIVITY_;
                    prepareThings(0);*/
                }
                break;

            case R.id.tvTabTheatrePlay:

                if(tvTabTheatrePlay.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_EVENT_THEATER;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabEvent:

                if(tvTabEvent.isSelected()){


                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_EVENT_EVENT;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabFestival:

                if(tvTabFestival.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_EVENT_FESTIVAL;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabNightclub:

                if(tvTabNightclub.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_NIGHTCLUB;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabLounge:

                if(tvTabLounge.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_LOUNGE;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabParty:

                if(tvTabParty.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_EVENT_PARTY;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabStandupComedy:

                if(tvTabStandupComedy.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_STANDUP_COMEDY;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabCoffee:

                if(tvTabCoffee.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_FROM_COFFEE;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabIndoorSports:

                if(tvTabIndoorSports.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_INDOOR_SPORTS;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabOutdoorSports:

                if(tvTabOutdoorSports.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_OUTDOOR_SPORTS;
                    prepareThings(0);
                }
                break;
            case R.id.tvTabHobbyClass:

                if(tvTabHobbyClass.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                }
                break;
            case R.id.tvTabAdventure:

                if(tvTabAdventure.isSelected()){

                }else{
                    setCurrentActionView(this,v.getId());
                    pageNumber = 0;
                    activityKey = AppConstants.ACTIVITY_ADV_SPORTS;
                    prepareThings(0);
                }
                break;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_coffee);
        ButterKnife.bind(this);

        tvTabMovie.setOnClickListener(this);
        tvTabTheatrePlay.setOnClickListener(this);
        tvTabEvent.setOnClickListener(this);
        tvTabFestival.setOnClickListener(this);
        tvTabNightclub.setOnClickListener(this);
        tvTabLounge.setOnClickListener(this);
        tvTabParty.setOnClickListener(this);
        tvTabStandupComedy.setOnClickListener(this);
        tvTabCoffee.setOnClickListener(this);
        tvTabIndoorSports.setOnClickListener(this);
        tvTabOutdoorSports.setOnClickListener(this);
        tvTabHobbyClass.setOnClickListener(this);
        tvTabAdventure.setOnClickListener(this);

        llActivitiesContainer.setVisibility(View.VISIBLE);
        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0);

        if(getIntent().getStringArrayListExtra("selectedList")!=null){
            activity_id = getIntent().getStringArrayListExtra("selectedList");
        }

        if(activityKey == AppConstants.ACTIVITY_NIGHTCLUB){

            setCurrentActionView(this,R.id.tvTabNightclub);
        }
        else if(activityKey == AppConstants.ACTIVITY_LOUNGE){

            setCurrentActionView(this,R.id.tvTabLounge);
        }
        else if(activityKey == AppConstants.ACTIVITY_FROM_COFFEE){

            setCurrentActionView(this,R.id.tvTabCoffee);
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_THEATER){

            setCurrentActionView(this,R.id.tvTabTheatrePlay);
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_EVENT){

            setCurrentActionView(this,R.id.tvTabEvent);
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_FESTIVAL){

            setCurrentActionView(this,R.id.tvTabFestival);
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_PARTY){

            setCurrentActionView(this,R.id.tvTabParty);
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_STANDUP){

            setCurrentActionView(this,R.id.tvTabStandupComedy);
        }
        else if(activityKey == AppConstants.ACTIVITY_ADV_SPORTS){

            setCurrentActionView(this,R.id.tvTabAdventure);
        }
        else if(activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS){

            setCurrentActionView(this,R.id.tvTabIndoorSports);
        }
        else if(activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS){

            setCurrentActionView(this,R.id.tvTabOutdoorSports);
        }

        tvNext.setVisibility(View.VISIBLE);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_id.size() > 0) {
                    Intent intent = new Intent(CoffeeActivityFilter.this, FilterChooserActivity.class);


                    ArrayList<Integer> activityType = new ArrayList<Integer>();

                    activityType.addAll(getIntent().getIntegerArrayListExtra("activity_type"));
                    for(int i = 0; i < filterModels.size();i++) {

                        FilterSelection filterSelection = filterModels.get(i);

                        activityType.add(filterSelection.activityType);

                    }
                    if(getIntent().getExtras().getBoolean("hasTravel")){
                        intent.putExtra("hasTravel", true);

                        intent.putExtra("activity_name", getIntent().getExtras().getString("activity_name"));
                        intent.putExtra("activity_to", getIntent().getExtras().getString("activity_to"));
                        intent.putExtra("activity_from", getIntent().getExtras().getString("activity_from"));
                    }
                    Set<Integer> hs = new HashSet<>();
                    hs.addAll(activityType);
                    activityType.clear();
                    activityType.addAll(hs);
                    intent.putExtra("activity_type", activityType);
                    intent.putExtra("selectedList", activity_id);
                    intent.putExtra("filterSelection", filterModels);
                    startActivity(intent);
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
                intent.putExtra(AppConstants.ACTIVITY_TYPE, activity_id_);
                intent.putExtra("selectedList",activity_id);
                intent.putExtra("filterSelection", filterModels);
                setResult(RESULT_OK, intent);
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

        if(pageNumber == 0){
            restaurantsBeanList.clear();
            postBeanList.clear();
        }
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
            else if(activityKey == AppConstants.ACTIVITY_EVENT_PARTY) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_PARTY;
                tvHeader1.setText(getString(R.string.party));
                prepareMeraEvents(AppConstants.MERAEVENTS_PARTY, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }
            else if(activityKey == AppConstants.ACTIVITY_EVENT_THEATER) {

                activity_id_ = AppConstants.API_ACTIVITY_ID_THEATER;
                tvHeader1.setText(getString(R.string.theater_play));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_THEATER, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));

            }else if(activityKey == AppConstants.ACTIVITY_EVENT_EVENT) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_EVENT;
                tvHeader1.setText(getString(R.string.event));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_EVENT, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_EVENT_FESTIVAL) {

                activity_id_ = AppConstants.API_ACTIVITY_ID_FESTIVAL;
                tvHeader1.setText(getString(R.string.festival));
                prepareMeraEvents(AppConstants.MERAEVENTS_FESTIVAL, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_INDOOR;
                tvHeader1.setText(getString(R.string.indoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_INDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_OUTDOOR;
                tvHeader1.setText(getString(R.string.outdoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_OUTDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_ADV_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_AVD_SPORTS;
                tvHeader1.setText(getString(R.string.adventure_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_ADV, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(AppConstants.ACTIVITY_TYPE, activity_id_);
        intent.putExtra("selectedList",activity_id);
        intent.putExtra("filterSelection", filterModels);
        setResult(RESULT_OK, intent);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
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


                    FilterSelection filterSelection = new FilterSelection(activity_id_,restaurantsBean.getRestaurant().getId());
                    if (activity_id.contains(restaurantsBean.getRestaurant().getId())) {
                        activity_id.remove(restaurantsBean.getRestaurant().getId());
                        filterModels.remove(filterSelection);
                    } else {
                        activity_id.add(restaurantsBean.getRestaurant().getId());
                        filterModels.add(filterSelection);
                    }

                    Log.d("add activity_id??",activity_id.toString());
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

    private void prepareMeraEvents(String searchFor, String lat, String lon, String radius, String start) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        try {

            requestParams.add("lat", lat);
            requestParams.add("lon", lon);
            requestParams.add("radius", radius);
            requestParams.add("start", start);
            requestParams.add("access_token", sp.getString("access_token",""));
            Log.d("prepareMeraEvents", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.MERAEVENTS + searchFor, requestParams,
                new CallMeraEvents());
    }

    class CallMeraEvents extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(CoffeeActivityFilter.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");

                JSONObject obj = new JSONObject(response);
                Gson gson = new Gson();
                MeraEventPartyModel meraEventPartyModel = gson.fromJson(obj.toString(), MeraEventPartyModel.class);
                if (meraEventPartyModel.isSuccess()) {
                    Log.d("prepareMeraEvents", "Size >> " + response);

                    if(meraEventPartyModel.getData().size() > 0){
                        postBeanList.addAll(meraEventPartyModel.getData());
                        if (pageNumber == 0) {

                            adapter = new EventAdapter(CoffeeActivityFilter.this,postBeanList);
                            listShops.setAdapter(adapter);


                        } else {
                            adapter.notifyDataSetChanged();
                            loading = true;

                        }

                        pageNumber = pageNumber + 20;
                    }else{

                        Toast.makeText(CoffeeActivityFilter.this,"No data",Toast.LENGTH_SHORT).show();
                    }
                }
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

    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

        private List<MeraEventPartyModel.DataBean> list;

        private Context mContext;

        RequestManager requestManager = Glide.with(CoffeeActivityFilter.this);
        public EventAdapter(Context context, List<MeraEventPartyModel.DataBean> events) {
            this.list = events;
            mContext = context;
        }

        @Override
        public EventAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

            return new EventAdapter.EventHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventAdapter.EventHolder holder, final int position) {


            holder.tvPrice.setText(postBeanList.get(position).getTicket_currencyCode()+" "+postBeanList.get(position).getTicket_price());
            holder.tvEventName.setText(postBeanList.get(position).getTitle());
            holder.tvAddress.setText(postBeanList.get(position).getAddress1());
            holder.timings.setText(postBeanList.get(position).getStartDate()+" - "+postBeanList.get(position).getEndDate());
            holder.tvDistance.setText(AppConstants.distanceMeasure(Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    (postBeanList.get(position).getLatitude()),
                    (postBeanList.get(position).getLongitude())) + " kms");

            if(postBeanList.get(position).getBannerPath().isEmpty() || postBeanList.get(position).getBannerPath().equals("")){
                holder.profileImage.setImageResource(R.drawable.bg_item_above);
            }else {
                requestManager
                        .load(postBeanList.get(position).getBannerPath())
                        .error(R.drawable.bg_item_above)
                        .placeholder(R.drawable.bg_item_above)
                        .into(holder.profileImage);
            }

            if (activity_id != null) {
                if (activity_id.contains(String.valueOf(postBeanList.get(position).getId()))) {
                    holder.rlContainer.setSelected(true);
                } else {
                    holder.rlContainer.setSelected(false);
                }

            }

            holder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FilterSelection filterSelection = new FilterSelection(activity_id_,String.valueOf(postBeanList.get(position).getId()));

                    if (activity_id.contains(String.valueOf(postBeanList.get(position).getId()))) {
                        activity_id.remove(String.valueOf(postBeanList.get(position).getId()));
                        filterModels.remove(filterSelection);
                    } else {
                        activity_id.add(String.valueOf(postBeanList.get(position).getId()));
                        filterModels.add(filterSelection);
                    }

                    Log.d("add activity_id??",activity_id.toString());

                    notifyDataSetChanged();
                }
            });
        }

        public MeraEventPartyModel.DataBean getItem(int position){
            return list.get(position);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void setData(List<MeraEventPartyModel.DataBean> data) {
            list = data;
            notifyDataSetChanged();
        }

        public class EventHolder extends RecyclerView.ViewHolder {

            CustomTextView tvEventName,tvAddress,tvOnwards,timings,tvDistance,tvPrice;
            RelativeLayout rlContainer;
            RoundedImageView profileImage;

            public EventHolder(View view) {
                super(view);
                rlContainer = (RelativeLayout) view.findViewById(R.id.rlContainer);
                tvEventName = (CustomTextView) view.findViewById(R.id.tvEventName);
                tvAddress = (CustomTextView) view.findViewById(R.id.tvAddress);
                tvOnwards = (CustomTextView) view.findViewById(R.id.tvOnwards);
                timings = (CustomTextView) view.findViewById(R.id.timings);
                tvDistance = (CustomTextView)view.findViewById(R.id.tvDistance);
                tvPrice = (CustomTextView) view.findViewById(R.id.tvPrice);
                profileImage = (RoundedImageView)view.findViewById(R.id.profileImage);
            }
        }

    }


}
