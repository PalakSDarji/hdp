package com.hadippa.activities.filter;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.hadippa.activities.EventDetailsActivity;
import com.hadippa.model.MeraEventPartyModel;
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

public class EventListActivityFilter extends AppCompatActivity implements LocationListener {

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

    @Nullable @BindView(R.id.srl_event_list)
    public SwipeRefreshLayout srlEventList;

    public static EventAdapter adapter;

    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    List<MeraEventPartyModel.DataBean> postBeanList = new ArrayList<>();
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
                if (ActivityCompat.checkSelfPermission(EventListActivityFilter.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EventListActivityFilter.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            gps = new GPSTracker(EventListActivityFilter.this);
        }

        if (gps.canGetLocation()) {
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

            prepareThings(pageNumber);

        } else {

            gps.showSettingsAlert();

        }


    }

    @BindView(R.id.tvNext)
    CustomTextView tvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        ButterKnife.bind(this);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(EventListActivityFilter.this);
        rvEventList.setLayoutManager(mLayoutManager);
        rvEventList.setItemAnimator(new DefaultItemAnimator());
        rvEventList.setAdapter(adapter);
        postBeanList = new ArrayList<>();

        if(getIntent().getStringArrayListExtra("selectedList")!=null){
            activity_id = getIntent().getStringArrayListExtra("selectedList");
        }

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        tvNext.setVisibility(View.VISIBLE);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_id.size() > 0) {
                    Intent intent = new Intent();

                    intent.putExtra(AppConstants.ACTIVITY_TYPE, activity_id_);
                    intent.putExtra("selectedList", activity_id);
                    setResult(RESULT_OK, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
                } else {
                    Toast.makeText(EventListActivityFilter.this, "Please select atleast one to continue", Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("selectedList",activity_id);
                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        //load fragment do display in the tab


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

   public void prepareThings(int pageNumber) {

        JSONObject user_preference = null;
        int dist = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EventListActivityFilter.this);
        try {
            user_preference = new JSONObject(sharedPreferences.getString("user_preference", ""));
            dist = Integer.parseInt(user_preference.getString("radius")) * 1000;
            if(activityKey == AppConstants.ACTIVITY_EVENT_PARTY) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_PARTY;
                tvHeader.setText(getString(R.string.party));
                prepareMeraEvents(AppConstants.MERAEVENTS_PARTY, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }
            else if(activityKey == AppConstants.ACTIVITY_EVENT_THEATER) {

                activity_id_ = AppConstants.API_ACTIVITY_ID_THEATER;
                tvHeader.setText(getString(R.string.theater_play));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_THEATER, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));

            }else if(activityKey == AppConstants.ACTIVITY_EVENT_EVENT) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_EVENT;
                tvHeader.setText(getString(R.string.event));
                prepareMeraEvents(AppConstants.MERAEVENTS_ENTERTAINMENT_EVENT, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }            else if(activityKey == AppConstants.ACTIVITY_EVENT_FESTIVAL) {

                activity_id_ = AppConstants.API_ACTIVITY_ID_FESTIVAL;
                tvHeader.setText(getString(R.string.festival));
                prepareMeraEvents(AppConstants.MERAEVENTS_FESTIVAL, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_INDOOR;
                tvHeader.setText(getString(R.string.indoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_INDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_OUTDOOR;
                tvHeader.setText(getString(R.string.outdoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_OUTDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_ADV_SPORTS) {
                activity_id_ = AppConstants.API_ACTIVITY_ID_AVD_SPORTS;
                tvHeader.setText(getString(R.string.adventure_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_ADV, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(EventListActivityFilter.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(EventListActivityFilter.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(EventListActivityFilter.this, new String[]{
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
        sp = PreferenceManager.getDefaultSharedPreferences(EventListActivityFilter.this);
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

            AppConstants.showProgressDialog(EventListActivityFilter.this, "Please Wait");

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
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("data", response);
                clipboard.setPrimaryClip(clip);

                MeraEventPartyModel meraEventPartyModel = gson.fromJson(obj.toString(), MeraEventPartyModel.class);
                if (meraEventPartyModel.isSuccess()) {
                    Log.d("prepareMeraEvents", "Size >> " + response);

                    if(meraEventPartyModel.getData().size() > 0){
                    postBeanList.addAll(meraEventPartyModel.getData());
                    if (pageNumber == 0) {

                        adapter = new EventAdapter(EventListActivityFilter.this,postBeanList);
                        rvEventList.setAdapter(adapter);


                    } else {
                        adapter.notifyDataSetChanged();
                        loading = true;

                    }

                    pageNumber = pageNumber + 20;
                }else{

                        Toast.makeText(EventListActivityFilter.this,"No data",Toast.LENGTH_SHORT).show();
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

        RequestManager requestManager = Glide.with(EventListActivityFilter.this);
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
                    if (activity_id.contains(postBeanList.get(position).getId())) {
                        activity_id.remove(String.valueOf(postBeanList.get(position).getId()));

                    } else {
                        activity_id.add(String.valueOf(postBeanList.get(position).getId()));
                    }

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
