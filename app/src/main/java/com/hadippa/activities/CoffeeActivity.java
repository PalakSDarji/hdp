package com.hadippa.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.search.SearchCity;
import com.hadippa.model.NightCLubModel;
import com.hadippa.model.SearchModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class CoffeeActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView listShops;
    private ImageView imageBack;
    private TextView tvHeader1;
    private int activityKey;
    @BindView(R.id.ivActivityIcon)
    ImageView ivActivityIcon;
    CustomAdapter customAdapter = new CustomAdapter();
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    int pageNumber = 0;
    List<NightCLubModel.ResponseBean.RestaurantsBean> restaurantsBeanList = new ArrayList<>();

    GPSTracker gps;
    String latitude = "", longitude = "";
    private boolean loading = true;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;

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
                if (ActivityCompat.checkSelfPermission(CoffeeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CoffeeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            gps = new GPSTracker(CoffeeActivity.this);
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

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
        sp = PreferenceManager.getDefaultSharedPreferences(CoffeeActivity.this);

        Log.d("getLOcation>>", latitude + " " + longitude);
        ivActivityIcon.setImageResource(R.drawable.zomato_spoon);
        tvHeader1 = (TextView) findViewById(R.id.tvHeader1);
        listShops = (RecyclerView) findViewById(R.id.listShops);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShops.setLayoutManager(mLayoutManager);
        ((TextView)findViewById(R.id.tvHeader2)).setText(sp.getString("cityName","Search"));


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

        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CoffeeActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY, AppConstants.ACTIVITY_FROM_COFFEE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        findViewById(R.id.tvHeader2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityDialog();
            }
        });
        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }


    }


    CustomAdapter1 adapter = null;

    void showCityDialog() {

        try {
            RelativeLayout relMain;
            ProgressBar progressBar;
            RecyclerView mRecyclerView;


            final CustomEditText customEditText;
            ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean> locationSuggestionsBeen = new ArrayList<>();

            Dialog dialog = new Dialog(CoffeeActivity.this);
            dialog.setContentView(R.layout.dialog_select_city);
            progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);

            customEditText =
                    (CustomEditText)dialog.findViewById(R.id.edtSearch);
            mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
            relMain = (RelativeLayout) dialog.findViewById(R.id.relMain);

            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(CoffeeActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            customEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    adapter.filter(customEditText.getText().toString().trim());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            String s = sp.getString("cities", "");

            JSONObject jsonObject = new JSONObject(s);


            JSONArray jsonArray = jsonObject.getJSONArray("city_list");

            SearchModel.CitiesBean.LocationSuggestionsBean locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
            locationSuggestionsBean.setName("Select City");
            locationSuggestionsBean.setHeader(true);
            locationSuggestionsBeen.add(locationSuggestionsBean);
            Log.d("cityList>", s);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SearchModel.CitiesBean.LocationSuggestionsBean bean = new SearchModel.CitiesBean.LocationSuggestionsBean();

                bean.setName(jsonObject1.getString("name"));
                bean.setId(jsonObject1.getInt("id"));
                bean.setState_name(jsonObject1.getString("state"));
                bean.setHeader(false);
                locationSuggestionsBeen.add(bean);
            }

            adapter = new CustomAdapter1(dialog, locationSuggestionsBeen);
            mRecyclerView.setAdapter(adapter);


            dialog.show();
        } catch (Exception e) {

        }
    }

    private static final int CITY_HEADER = 0;
    private static final int CITY_DATA = 1;

    public class CustomAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final String TAG = "CustomAdapter";
        private List<SearchModel.CitiesBean.LocationSuggestionsBean> listItems, filterList;
        Dialog dialog;

        public CustomAdapter1(Dialog dialog1, List<SearchModel.CitiesBean.LocationSuggestionsBean> listItems) {
            this.listItems = listItems;

            this.dialog = dialog1;
            this.filterList = new ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean>();
            // we copy the original list to the filter list and use it for setting row values
            this.filterList.addAll(this.listItems);
        }

        @Override
        public int getItemViewType(int position) {

            if (filterList == null) {
                return -1;
            }
            if (filterList.get(position) == null) {
                return -1;
            }

            if (filterList.get(position).isHeader()) {
                return CITY_HEADER;
            } else {
                return CITY_DATA;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            Log.v(TAG, "OnCreateViewHolder called  : " + viewType);
            switch (viewType) {
                case CITY_HEADER:
                    View v1 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_header, viewGroup, false);
                    return new HeaderViewHolder(v1);

                case CITY_DATA:
                    View v2 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_list_item, viewGroup, false);
                    return new CityViewHolder(v2);

                default:
                    View v3 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_header, viewGroup, false);
                    return new HeaderViewHolder(v3);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            switch (viewHolder.getItemViewType()) {
                case CITY_HEADER:
                    HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                    SearchModel.CitiesBean.LocationSuggestionsBean cityHeader = filterList.get(position);

                    headerViewHolder.tvSearchCityHeader.setText(cityHeader.getName());
                    break;

                case CITY_DATA:

                    final CityViewHolder cityViewHolder = (CityViewHolder) viewHolder;
                    SearchModel.CitiesBean.LocationSuggestionsBean cityModel = filterList.get(position);

                    if (cityModel.isFromCityList()) {
                        cityViewHolder.getIvLocation().setVisibility(View.GONE);
                    } else {
                        cityViewHolder.getIvLocation().setVisibility(View.VISIBLE);
                    }

                    if (cityModel.getCountry_name() != null) {
                        cityViewHolder.getCity().setText(cityModel.getName() + ", " + cityModel.getCountry_name());
                    } else {
                        cityViewHolder.getCity().setText(cityModel.getName());
                    }

                    cityViewHolder.getId().setText(String.valueOf(cityModel.getId()));

                    if (filterList.get(position).isCurrentLocation()) {
                        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //TODO define click listener to get location. (Sahil)
                            }
                        });
                        cityViewHolder.getIvRightLogo().setVisibility(View.GONE);
                    } else {
                        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((TextView) findViewById(R.id.tvHeader2)).setText(cityViewHolder.getCity().getText().toString().trim());

                                prepareThings(0);
                                dialog.dismiss();
                            }
                        });
                        cityViewHolder.getIvRightLogo().setVisibility(View.VISIBLE);
                    }

                    break;
            }


        }

        @Override
        public int getItemCount() {

            return (null != filterList ? filterList.size() : 0);

        }

        // Do Search...
        public void filter(final String text) {

            // Searching could be complex..so we will dispatch it to a different thread...
            new Thread(new Runnable() {
                @Override
                public void run() {

                    // Clear the filter list
                    filterList.clear();

                    // If there is no search value, then add all original list items to filter list
                    if (TextUtils.isEmpty(text)) {

                        filterList.addAll(listItems);

                    } else {
                        // Iterate in the original List and add it to filter list...
                        for (SearchModel.CitiesBean.LocationSuggestionsBean item : listItems) {
                            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                filterList.add(item);
                            }
                        }
                    }

                    // Set on UI Thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Notify the List that the DataSet has changed...
                            notifyDataSetChanged();
                        }
                    });

                }
            }).start();

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchCityHeader;

        public HeaderViewHolder(final View v) {
            super(v);

            tvSearchCityHeader = (TextView) v.findViewById(R.id.tvSearchCityHeader);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final ImageView ivLocation;
        private final ImageView ivRightLogo;
        private final TextView city;

        public CityViewHolder(final View v) {
            super(v);


            id = (TextView) v.findViewById(R.id.tvId);
            city = (TextView) v.findViewById(R.id.city);
            ivLocation = (ImageView) v.findViewById(R.id.ivLocation);
            ivRightLogo = (ImageView) v.findViewById(R.id.ivRightLogo);
        }

        public TextView getCity() {
            return city;
        }

        public TextView getId() {
            return id;
        }

        public ImageView getIvRightLogo() {
            return ivRightLogo;
        }

        public ImageView getIvLocation() {
            return ivLocation;
        }
    }


    void prepareThings(int pageNumber) {

        JSONObject user_preference = null;
        int dist = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(CoffeeActivity.this);
        try {
            user_preference = new JSONObject(sharedPreferences.getString("user_preference", ""));
            dist = Integer.parseInt(user_preference.getString("radius")) * 1000;
            if (activityKey == AppConstants.ACTIVITY_FROM_COFFEE) {

                tvHeader1.setText(getString(R.string.coffee_cafe));
                edtSearch.setHint(getString(R.string.select_cafe));
                prepareZomato(AppConstants.CAFES, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            } else if (activityKey == AppConstants.ACTIVITY_NIGHTCLUB) {

                tvHeader1.setText(getString(R.string.night_club));
                edtSearch.setHint(getString(R.string.select_club));
                prepareZomato(AppConstants.NIGHTCLUB, latitude, longitude, String.valueOf(dist), String.valueOf(pageNumber));
            } else if (activityKey == AppConstants.ACTIVITY_LOUNGE) {

                tvHeader1.setText(getString(R.string.lounge));
                edtSearch.setHint(getString(R.string.select_lounge));
                prepareZomato(AppConstants.LOUNGE, latitude, longitude, String.valueOf(dist), String.valueOf(pageNumber));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(CoffeeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(CoffeeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(CoffeeActivity.this, new String[]{
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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
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


                Glide.with(CoffeeActivity.this)
                        .load(restaurantsBean.getRestaurant().getThumb())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.profileImage);
            }


            Log.d("image>>>", position + " Postion Url >> " + restaurantsBean.getRestaurant().getThumb());

            viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CoffeeActivity.this, CreateActivityActvity.class);
                    Log.d("getIntent().", activityKey + "");
                    intent.putExtra(AppConstants.ACTIVITY_KEY, getIntent().getIntExtra(AppConstants.ACTIVITY_KEY, 0));
                    intent.putExtra("activity_id", getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("data", restaurantsBean);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude", longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        sp = PreferenceManager.getDefaultSharedPreferences(CoffeeActivity.this);

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("lat", lat);
            requestParams.add("lon", lon);
            requestParams.add("radius", radius);
            requestParams.add("start", start);
            requestParams.add("city_name",((TextView) findViewById(R.id.tvHeader2)).getText().toString().trim());

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

            restaurantsBeanList.clear();
            AppConstants.showProgressDialog(CoffeeActivity.this, "Please Wait");
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
                    Toast.makeText(CoffeeActivity.this, nightCLubModel.getErrors(), Toast.LENGTH_SHORT).show();
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

            AppConstants.dismissDialog();
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_coffee)), intent.getExtras().getString("messageData"));
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

}
