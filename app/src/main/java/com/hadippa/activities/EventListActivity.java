package com.hadippa.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomEditText;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MeraEventPartyModel;
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

public class EventListActivity extends AppCompatActivity implements LocationListener {

    private PagerAdapter viewPagerAdapter;
    private String[] tabs;
    private TabLayout tabLayout;
    private int activityKey;

    private TextView tvHeader;
    GPSTracker gps;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    String latitude = "", longitude = "";
    public int pageNumber = 0;

    @Nullable
    @BindView(R.id.rv_event_list)
    public RecyclerView rvEventList;
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Nullable @BindView(R.id.srl_event_list)
    public SwipeRefreshLayout swipeRefreshLayout;

    public static EventAdapter adapter;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    List<MeraEventPartyModel.DataBean> postBeanList = new ArrayList<>();

    void getLocation() {

        mLocationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                latitude = String.valueOf(location.getLatitude());
                longitude = String.valueOf(location.getLongitude());

                prepareThings(pageNumber,true);
                Log.d("locaGPS>>", latitude + ">>>" + longitude);


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                if (ActivityCompat.checkSelfPermission(EventListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EventListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                    prepareThings(pageNumber,true);

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
            gps = new GPSTracker(EventListActivity.this);
        }

        if (gps.canGetLocation()) {
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

            prepareThings(pageNumber,true);

        } else {

            gps.showSettingsAlert();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);
        sp = PreferenceManager.getDefaultSharedPreferences(EventListActivity.this);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(EventListActivity.this);
        rvEventList.setLayoutManager(mLayoutManager);
        rvEventList.setItemAnimator(new DefaultItemAnimator());
        rvEventList.setAdapter(adapter);
        postBeanList = new ArrayList<>();
        ((TextView)findViewById(R.id.tvHeader2)).setText(sp.getString("cityName","Search"));
        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        tvHeader = (TextView) findViewById(R.id.tvHeader);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                prepareThings(0, false);
            }
        });


        swipeRefreshLayout.setDistanceToTriggerSync(50);


        rvEventList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = false;
                            Log.v("...", "Last Item Wow !");

                            prepareThings(pageNumber,isCurrent);
                            //Do pagination.. i.e. fetch new data
                        }
                    }
                }

            }
        });

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                adapter.filter(edtSearch.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.relH2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCityDialog();
            }
        });

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prepareThings(0,true);

            }
        });

        //load fragment do display in the tab

       /* edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                customAdapter.filter(edtSearch.getText().toString().trim());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
        //set view pager adapter and view pager data.

        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }


    }



    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTextSize(12);
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(),getString(R.string.proxima_bold)), Typeface.NORMAL);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Fragment> registeredFragments = new SparseArray<>();
        private Context mContext;
        String[] tabs;
        private List<Fragment> mFragmentList;


        //This is a constructor
        public PagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, String[] tabs) {
            super(fm);
            this.mContext=context;
            mFragmentList=fragmentList;
            this.tabs=tabs;

        }

        //get tab item
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        //get page title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        //get all tab count
        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
    boolean isCurrent = true;

   public void prepareThings(int pageNumber,boolean isCurrent) {
       this.isCurrent = isCurrent;

       this.pageNumber = pageNumber;
        JSONObject user_preference = null;
        int dist = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EventListActivity.this);
        try {
            user_preference = new JSONObject(sharedPreferences.getString("user_preference", ""));
            dist = Integer.parseInt(user_preference.getString("radius")) * 1000;
            if(activityKey == AppConstants.ACTIVITY_EVENT_PARTY) {
                tvHeader.setText(getString(R.string.party));
                prepareMeraEvents(AppConstants.MERAEVENTS_PARTY, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }
            else if(activityKey == AppConstants.ACTIVITY_EVENT_THEATER) {
                tvHeader.setText(getString(R.string.theater_play));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_THEATER, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));

            }else if(activityKey == AppConstants.ACTIVITY_EVENT_EVENT) {
                tvHeader.setText(getString(R.string.event));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_EVENT, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }            else if(activityKey == AppConstants.ACTIVITY_EVENT_FESTIVAL) {
                tvHeader.setText(getString(R.string.festival));
                prepareMeraEvents(AppConstants.MERAEVENTS_FESTIVAL, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {
                tvHeader.setText(getString(R.string.indoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_INDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {
                tvHeader.setText(getString(R.string.outdoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_OUTDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_ADV_SPORTS) {
                tvHeader.setText(getString(R.string.adventure_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_ADV, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(EventListActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(EventListActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(EventListActivity.this, new String[]{
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

    SharedPreferences sp;

    private void prepareMeraEvents(String searchFor, String lat, String lon, String radius, String start) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();
        sp = PreferenceManager.getDefaultSharedPreferences(EventListActivity.this);
        try {

            requestParams.add("lat", lat);
            requestParams.add("lon", lon);
            requestParams.add("radius", radius);
            requestParams.add("start", start);
            requestParams.add("access_token", sp.getString("access_token",""));
            if(!isCurrent) {
              requestParams.add("city_name", ((TextView) findViewById(R.id.tvHeader2)).getText().toString().trim());
            }
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

            postBeanList.clear();
            if(swipeRefreshLayout.isRefreshing()){

            }else {
                AppConstants.showProgressDialog(EventListActivity.this, "Please Wait");
            }
        }


        @Override
        public void onFinish() {

            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");

                JSONObject obj = new JSONObject(response);
                Gson gson = new Gson();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("data", response);
                clipboard.setPrimaryClip(clip);
                Log.d("prepareMeraEvents", "Size >> " + response);
                MeraEventPartyModel meraEventPartyModel = gson.fromJson(obj.toString(), MeraEventPartyModel.class);
                if (meraEventPartyModel.isSuccess()) {
                    Log.d("prepareMeraEvents", "Size >> " + response);

                    if(meraEventPartyModel.getData().size() > 0){

                    if (pageNumber == 0) {
                        postBeanList.clear();
                        postBeanList.addAll(meraEventPartyModel.getData());
                        adapter = new EventAdapter(EventListActivity.this,postBeanList);
                        rvEventList.setAdapter(adapter);


                    } else {
                        postBeanList.addAll(meraEventPartyModel.getData());
                        adapter.notifyDataSetChanged();
                        loading = true;

                    }

                    pageNumber = pageNumber + 20;
                }else{

                        Toast.makeText(EventListActivity.this,"No data",Toast.LENGTH_SHORT).show();
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
            AppConstants.dismissDialog();
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }


    public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder>{

        private List<MeraEventPartyModel.DataBean> listItems, filterList;


        private Context mContext;

        RequestManager requestManager = Glide.with(EventListActivity.this);

        public EventAdapter(Context context, List<MeraEventPartyModel.DataBean> events) {
            this.listItems = events;

            this.filterList = new ArrayList<>();
            // we copy the original list to the filter list and use it for setting row values
            this.filterList.addAll(this.listItems);
        }

        @Override
        public EventAdapter.EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event, parent, false);

            return new EventAdapter.EventHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventAdapter.EventHolder holder, final int position) {

            holder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventListActivity.this, EventDetailsActivity.class);
                    intent.putExtra("activity_id", getIntent().getExtras().getInt("activity_id"));
                    intent.putExtra("data", filterList.get(position));
                    intent.putExtra(AppConstants.ACTIVITY_KEY,activityKey);
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });
            holder.tvPrice.setText(filterList.get(position).getTicket_currencyCode()+" "+filterList.get(position).getTicket_price());
            holder.tvEventName.setText(filterList.get(position).getTitle());
            holder.tvAddress.setText(filterList.get(position).getAddress1()+" "+filterList.get(position).getCityName()+" "+filterList.get(position).getStateName());
            holder.timings.setText(AppConstants.formatDate(filterList.get(position).getStartDate(),"yyyy-mm-dd hh:mm:ss","dd MMM yy hh:mm a")
                    +
                    " - "
                    +
                    AppConstants.formatDate(filterList.get(position).getEndDate(),"yyyy-mm-dd hh:mm:ss","dd MMM yy hh:mm a"));
            holder.tvDistance.setText(AppConstants.distanceMeasure(Double.parseDouble(latitude),
                    Double.parseDouble(longitude),
                    (filterList.get(position).getLatitude()),
                    (filterList.get(position).getLongitude())) + " kms");

            if(filterList.get(position).getBannerPath().isEmpty() || filterList.get(position).getBannerPath().equals("")){
                holder.profileImage.setImageResource(R.drawable.bg_item_above);
            }else {
                requestManager
                        .load(filterList.get(position).getBannerPath())
                        .error(R.drawable.bg_item_above)
                        .placeholder(R.drawable.bg_item_above)
                        .into(holder.profileImage);
            }
        }

        public MeraEventPartyModel.DataBean getItem(int position){
            return filterList.get(position);
        }

        public void filter(final String text){
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
                        for (MeraEventPartyModel.DataBean item : listItems) {
                            if (item.getTitle().contains(text.toLowerCase()) || item.getAddress1().contains(text.toLowerCase())
                                    || item.getAddress2().contains(text.toLowerCase())) {
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

        @Override
        public int getItemCount() {
            return (null != filterList ? filterList.size() : 0);
        }

        public void setData(List<MeraEventPartyModel.DataBean> data) {
            filterList = data;
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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_event_list)),intent.getExtras().getString("messageData"));
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



    CustomAdapter1 adapter1 = null;

    void showCityDialog() {

        try {
            RelativeLayout relMain;
            ProgressBar progressBar;
            RecyclerView mRecyclerView;


            final CustomEditText customEditText;
            ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean> locationSuggestionsBeen = new ArrayList<>();

            Dialog dialog = new Dialog(EventListActivity.this);
            dialog.setContentView(R.layout.dialog_select_city);
            progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);

            customEditText =
                    (CustomEditText)dialog.findViewById(R.id.edtSearch);
            mRecyclerView = (RecyclerView) dialog.findViewById(R.id.recyclerView);
            relMain = (RelativeLayout) dialog.findViewById(R.id.relMain);

            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(EventListActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLayoutManager);

            customEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    adapter1.filter(customEditText.getText().toString().trim());
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

            locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
            locationSuggestionsBean.setName("Current Location");
            locationSuggestionsBean.setHeader(false);
            locationSuggestionsBean.setFromCityList(false);
            locationSuggestionsBean.setCurrentLocation(true);
            locationSuggestionsBeen.add(locationSuggestionsBean);

            Log.d("cityList>", s);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SearchModel.CitiesBean.LocationSuggestionsBean bean = new SearchModel.CitiesBean.LocationSuggestionsBean();

                bean.setName(jsonObject1.getString("name"));
                bean.setId(jsonObject1.getInt("id"));
              //  bean.setState_name(jsonObject1.getString("state"));
                bean.setHeader(false);
                locationSuggestionsBeen.add(bean);
            }

            adapter1 = new CustomAdapter1(dialog, locationSuggestionsBeen);
            mRecyclerView.setAdapter(adapter1);


            dialog.show();
        } catch (Exception e) {

            Log.d("dialogException",e.getMessage());
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
                                ((TextView) findViewById(R.id.tvHeader2)).setText(sp.getString("cityName", "Search"));
                                prepareThings(0, true);
                                dialog.dismiss();
                            }
                        });
                        cityViewHolder.getIvRightLogo().setVisibility(View.GONE);
                        cityViewHolder.getIvLocation().setVisibility(View.VISIBLE);
                    } else {
                        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((CustomTextView) findViewById(R.id.tvHeader2)).setText(cityViewHolder.getCity().getText().toString().trim());

                                prepareThings(0,false);
                                dialog.dismiss();
                            }
                        });
                        cityViewHolder.getIvLocation().setVisibility(View.GONE);
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


}
