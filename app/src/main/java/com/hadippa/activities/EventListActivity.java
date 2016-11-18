package com.hadippa.activities;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.commonclasses.location.GPSTracker;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.fragments.EventListFragment;
import com.hadippa.model.MeraEventPartyModel;
import com.hadippa.model.NightCLubModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    private boolean loading = true;
    ViewPager viewPager;
    List<Fragment> fragments=new ArrayList<>();

    public static List<MeraEventPartyModel.DataBean> postBeanList = new ArrayList<>();

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
            gps = new GPSTracker(EventListActivity.this);
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
        setContentView(R.layout.activity_event_list);

        postBeanList = new ArrayList<>();

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });



        //set tab view
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabs = new String[]{getString(R.string.lbl_today), getString(R.string.lbl_tomorrow),getString(R.string.lbl_weekend),getString(R.string.lbl_custom)};

        //add tabs to tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(tabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[2]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[3]));

        //load fragment do display in the tab


        //set view pager adapter and view pager data.

        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                changeTabsFont();
            }
        });
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
                prepareMeraEvents(AppConstants.MERAEVENTS_PARTY, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_INDOOR_SPORTS) {
                tvHeader.setText(getString(R.string.indoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_INDOOR, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));
            }else if(activityKey == AppConstants.ACTIVITY_OUTDOOR_SPORTS) {
                tvHeader.setText(getString(R.string.outdoor_sports));
                prepareMeraEvents(AppConstants.MERAEVENTS_SPORTS_OUTDOOR, latitude, longitude, String.valueOf(dist)
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

            AppConstants.showProgressDialog(EventListActivity.this, "Please Wait");

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

                    postBeanList.addAll(meraEventPartyModel.getData());
                    if (pageNumber == 0) {
                        EventListFragment eventListFragment = new EventListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("activity_id", getIntent().getExtras().getInt("activity_id"));
                        bundle.putString("latitude",latitude);
                        bundle.putString("longitude",longitude);
                        eventListFragment.setArguments(bundle);
                        fragments.add(eventListFragment);
                    /*    fragments.add(new EventListFragment());
                        fragments.add(new EventListFragment());
                        fragments.add(new EventListFragment());
*/
                        viewPagerAdapter=new PagerAdapter(EventListActivity.this,getSupportFragmentManager(),fragments,tabs);
                        viewPager.setAdapter(viewPagerAdapter);
                        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
                        viewPager.setOffscreenPageLimit(4);
                        tabLayout.setupWithViewPager(viewPager);
                        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                            @Override
                            public void onTabSelected(TabLayout.Tab tab) {
                                viewPager.setCurrentItem(tab.getPosition());
                            }

                            @Override
                            public void onTabUnselected(TabLayout.Tab tab) {

                            }

                            @Override
                            public void onTabReselected(TabLayout.Tab tab) {

                            }
                        });
                    } else {
                        EventListFragment.adapter.notifyDataSetChanged();
                        loading = true;

                    }

                    pageNumber = pageNumber + 20;
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


}
