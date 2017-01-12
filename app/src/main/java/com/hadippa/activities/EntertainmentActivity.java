package com.hadippa.activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.hadippa.model.CinemaModel;
import com.hadippa.model.MeraEventPartyModel;
import com.hadippa.utils.OnOkClickListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.channels.spi.AbstractSelectionKey;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class EntertainmentActivity extends BaseActionsActivity implements LocationListener {

    private RecyclerView listPlay;
    private TextView tvHeader;
    CustomAdapter customAdapter = new CustomAdapter();
    private ArrayList<String> alCities;

    ArrayList<CinemaModel.ResponseBean.MoviesBean> cinemasBeen = new ArrayList<>();


    @BindView(R.id.tvTabMovie)
    TextView tvTabMovie;
    private AlertDialog alertDialog;
    @BindView(R.id.llInvite)
    RelativeLayout llInvite;
    @BindView(R.id.edtSearch)
    EditText edtSearch;
    @BindView(R.id.rlSearch)
    RelativeLayout rlSearch;

    int pageNumber = 20;

    GPSTracker gps;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;
    String latitude = "", longitude = "";
    private boolean loading = true;

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
                if (ActivityCompat.checkSelfPermission(EntertainmentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(EntertainmentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
            gps = new GPSTracker(EntertainmentActivity.this);
        }

        if (gps.canGetLocation()) {
            latitude = String.valueOf(gps.getLatitude());
            longitude = String.valueOf(gps.getLongitude());

            prepareThings(pageNumber);

        } else {

            gps.showSettingsAlert();

        }


    }

    public void prepareThings(int pageNumber) {

        JSONObject user_preference = null;
        int dist = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(EntertainmentActivity.this);
        try {
            user_preference = new JSONObject(sharedPreferences.getString("user_preference", ""));
            dist = Integer.parseInt(user_preference.getString("radius")) * 1000;

                prepareMeraEvents(AppConstants.MOVIE_LIST, latitude, longitude, String.valueOf(dist)
                        , String.valueOf(pageNumber));


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);
        ButterKnife.bind(this);

        setCurrentActionView(this,R.id.tvTabMovie);

        alCities = new ArrayList<>();
        alCities.add("Mumbai");
        alCities.add("Vadodara");
        alCities.add("Ahmedabad");
        alCities.add("Surat");
        alCities.add("Banglore");
        alCities.add("Jaipur");
        alCities.add("Chennai");
        alCities.add("Pune");

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.v("click", "onFocusChange");
                if(hasFocus){
                    Log.v("click", "onFocusChange true");
                    rlSearch.setPressed(true);
                }
                else{
                    Log.v("click", "onFocusChange false");
                    rlSearch.setPressed(false);
                }
            }
        });


        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        listPlay = (RecyclerView) findViewById(R.id.listPlay);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listPlay.setLayoutManager(mLayoutManager);
        //listPlay.setAdapter(customAdapter);

        listPlay.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

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

        llInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupList(EntertainmentActivity.this, alCities, getString(R.string.choose_city), new OnOkClickListener() {
                    @Override
                    public void onOkClick(String dataSelected) {

                        //TODO change_city web call here
                    }
                });
            }
        });

        if (checkPermission()) {
            getLocation();
        } else {
            requestPermission();
        }
    }


    private boolean checkPermission() {

        int CAMERA = ContextCompat.checkSelfPermission(EntertainmentActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int read_external = ContextCompat.checkSelfPermission(EntertainmentActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);


        if (CAMERA == PackageManager.PERMISSION_GRANTED && read_external == PackageManager.PERMISSION_GRANTED
                ) {

            return true;


        } else {


            return false;

        }

    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(EntertainmentActivity.this, new String[]{
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (view != null && view instanceof EditText) {
                Rect r = new Rect();
                view.getGlobalVisibleRect(r);
                int rawX = (int)ev.getRawX();
                int rawY = (int)ev.getRawY();
                if (!r.contains(rawX, rawY)) {
                    view.clearFocus();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /*@Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }*/

    private void showPopupList(Context context, final List<String> list, String title, final OnOkClickListener onOkClickListener) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View view = LayoutInflater.from(context).inflate(R.layout.popup_listview, null);
        ListView listView = (ListView) view.findViewById(R.id.lvItems);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context,R.layout.item_city, R.id.txtName, list);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(alertDialog != null) alertDialog.cancel();
                onOkClickListener.onOkClick(list.get(position));
            }
        });

        builder.setTitle(title);
        builder.setView(view);

        alertDialog = builder.show();
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movies, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            CinemaModel.ResponseBean.MoviesBean cinemasBean = cinemasBeen.get(position);

            viewHolder.name.setText(cinemasBean.getTitle());

            RequestManager requestManager = Glide.with(EntertainmentActivity.this);
            requestManager.load(cinemasBean.getPoster_image_thumbnail()).into(viewHolder.profileImage);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EntertainmentActivity.this, EventDetailsActivity.class);
                    //intent.putExtra("activity_id", getIntent().getExtras().getInt("activity_id"));
                    //intent.putExtra("data", postBeanList.get(position));
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_MOVIE);
                    //intent.putExtra("latitude", latitude);
                    //intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            });
        }

        @Override
        public int getItemCount() {
            return cinemasBeen.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout rlData;

        CustomTextView name,movieType,cast,rating,duration;
        RoundedImageView profileImage;
        public ViewHolder(final View v) {
            super(v);
            rlData = (RelativeLayout)v.findViewById(R.id.rlData);
            name = (CustomTextView)v.findViewById(R.id.name);
            movieType = (CustomTextView)v.findViewById(R.id.movieType);
            cast = (CustomTextView)v.findViewById(R.id.cast);
            rating = (CustomTextView)v.findViewById(R.id.rating);
            duration = (CustomTextView)v.findViewById(R.id.duration);

            profileImage = (RoundedImageView)v.findViewById(R.id.profileImage);
            rlData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(EntertainmentActivity.this, EventDetailsActivity.class);
                    //intent.putExtra("activity_id", getIntent().getExtras().getInt("activity_id"));
                    //intent.putExtra("data", postBeanList.get(position));
                    intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_MOVIE);
                    //intent.putExtra("latitude", latitude);
                    //intent.putExtra("longitude",longitude);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    /*Intent intent = new Intent(EntertainmentActivity.this, PlayDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    *//**//*ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }*//**//**/
                }
            });
        }

        public RelativeLayout getRlData() {
            return rlData;
        }


    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_entertainment)),intent.getExtras().getString("messageData"));
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


    SharedPreferences sp;
    private void prepareMeraEvents(String searchFor, String lat, String lon, String radius, String start) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();
        sp = PreferenceManager.getDefaultSharedPreferences(EntertainmentActivity.this);
        try {

          /*  requestParams.add("lat", lat);
            requestParams.add("lon", lon);*/
            requestParams.add("countries", "IN");
            requestParams.add("limit", start);
            requestParams.add("access_token", sp.getString("access_token",""));
            Log.d("prepareMeraEvents", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + searchFor, requestParams,
                new CallMeraEvents());
    }

    class CallMeraEvents extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(EntertainmentActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
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

                CinemaModel cinemaModel = gson.fromJson(obj.toString(), CinemaModel.class);
                if (cinemaModel.isSuccess()) {
                    Log.d("prepareMeraEvents", "Size >> " + response);

                    if(cinemaModel.getResponse().getMovies().size() > 0){

                        cinemasBeen.addAll(cinemaModel.getResponse().getMovies());
                        if (pageNumber == 20) {



                            customAdapter = new CustomAdapter();
                            listPlay.setAdapter(customAdapter);


                        } else {
                            customAdapter.notifyDataSetChanged();
                            loading = true;

                        }

                        pageNumber = pageNumber + 20;
                    }else{

                        Toast.makeText(EntertainmentActivity.this,"No data",Toast.LENGTH_SHORT).show();
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
}
