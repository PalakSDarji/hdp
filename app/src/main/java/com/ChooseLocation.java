/*
package com;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.getitdone.Constants;
import com.getitdone.R;
import com.getitdone.databinding.ActivityChooseLocationBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

public class ChooseLocation extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, LocationListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    SupportMapFragment mapFragment;
    ActivityChooseLocationBinding locationBinding;

    public Location lastLocation = null;

    protected GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;

    Marker marker;
    MarkerOptions markerOptions;
    PlaceAutocompleteFragment autocompleteFragment;

    Constants constants;

    String task_location = "", task_latitude = "", task_longitude = "";
    JSONObject data = new JSONObject();

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        buildGoogleApiClient();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(getResources().getColor(R.color.status_bar));
        View view = window.getDecorView();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);

        }

        locationBinding = DataBindingUtil.setContentView(ChooseLocation.this, R.layout.activity_choose_location);

        constants = new Constants(ChooseLocation.this);

        getPreviousFormData();

        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextSize(15);
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setPadding(15, 10, 0, 10);
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTextColor(getResources().getColor(R.color.white));
        ((EditText) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_input)).setTypeface(Typeface.createFromAsset(getAssets(), getResources().getString(R.string.light)));
        ((ImageButton) autocompleteFragment.getView().findViewById(R.id.place_autocomplete_clear_button)).setImageDrawable(null);
        (autocompleteFragment.getView().findViewById(R.id.place_autocomplete_search_button)).setVisibility(View.GONE);
        locationBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    101);
        } else {

        }

     */
/*   locationBinding.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ChooseLocation.this, PlaceAutoComplete.class);
                startActivityForResult(intent,101);
            }
        });*//*


        locationBinding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseLocation.this, SelectBudget.class);
                intent.putExtra(constants.DATA, collectData());
                startActivity(intent);
            }
        });

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.

                MarkerOptions markerOptions1 = new MarkerOptions();

                // Setting the position for the marker
                markerOptions1.position(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude));

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions1.title("Current Location").draggable(true);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(place.getLatLng().latitude, place.getLatLng().longitude)));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions1);

                task_latitude = String.valueOf(place.getLatLng().latitude);
                task_longitude = String.valueOf(place.getLatLng().longitude);
                task_location = place.getAddress() + "";
                autocompleteFragment.setText(task_location);


            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Place?>>", "An error occurred: " + status);
            }
        });


    }

    */
/**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     *//*

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Current Location").draggable(true);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);

                task_latitude = String.valueOf(latLng.latitude);
                task_longitude = String.valueOf(latLng.longitude);
                new AsyncTaskForAddress(latLng).execute();


            }
        });

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(marker.getPosition());

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title("Current Location").draggable(true);

                // Clears the previously touched position
                mMap.clear();

                // Animating to the touched position
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                // Placing a marker on the touched position
                mMap.addMarker(markerOptions);
                task_latitude = String.valueOf(marker.getPosition().latitude);
                task_longitude = String.valueOf(marker.getPosition().longitude);

                new AsyncTaskForAddress(marker.getPosition()).execute();

            }
        });


        // Add a marker in Sydney and move the camera
     */
/*   LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*//*

    }


    String collectData() {

        try {
            data.put(constants.task_latitude, task_latitude);
            data.put(constants.task_longitude, task_longitude);
            data.put(constants.task_location, task_location);
        } catch (Exception e) {

        }
        return data.toString();
    }


    void getPreviousFormData() {

        try {
            data = new JSONObject(getIntent().getExtras().getString(constants.DATA));
            Log.d("DATA>>", data.toString());
        } catch (Exception e) {

        }
    }

    class AsyncTaskForAddress extends AsyncTask {

        String address;
        LatLng latLng;

        public AsyncTaskForAddress(LatLng latLng) {
            this.latLng = latLng;
        }

        @Override
        protected Object doInBackground(Object[] params) {

            address = getCompleteAddressString(latLng);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            task_location = address;
            if (autocompleteFragment != null) {
                autocompleteFragment.setText(task_location);
            }
        }
    }

    public String getCompleteAddressString(LatLng latLng) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(" ");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current", "Canont get Address!");
        }
        return strAdd;
    }

    private boolean checkPermission() {

        int coarse_location = ContextCompat.checkSelfPermission(ChooseLocation.this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        int fine_location = ContextCompat.checkSelfPermission(ChooseLocation.this, android.Manifest.permission.ACCESS_FINE_LOCATION);

        if (coarse_location == PackageManager.PERMISSION_GRANTED &&
                fine_location == PackageManager.PERMISSION_GRANTED) {

            return true;

        } else {

            //   requestPermission();
            return false;

        }
    }

    boolean validations() {


        return checkPermission() && isLocationEnabled(getApplicationContext());

    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }

    private void requestPermission() {


        ActivityCompat.requestPermissions(ChooseLocation.this, new String[]{
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("requestCode>>", "req code > " + requestCode + "   res code > " + permissions.toString() + "    gr  >> " + grantResults.length);
        switch (requestCode) {
            case 101:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //  atStartUp();
                    //   Snackbar.make(rel, "Permission Granted.", Snackbar.LENGTH_LONG).show();

                    if (isLocationEnabled(getApplicationContext())) {
                        Toast.makeText(ChooseLocation.this, "Location is on. Going to start location", Toast.LENGTH_SHORT).show();
                        startLocation();


                    } else {

                        Toast.makeText(ChooseLocation.this, "Turn ON location", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(ChooseLocation.this, "Permisson cannot be denied.", Toast.LENGTH_SHORT).show();
                    //Snackbar.make(getCurrentFocus(), "Permisson cannot be denied.", Snackbar.LENGTH_LONG).show();
                    //    atStartUp();

                }
                break;
        }
    }

    protected synchronized void buildGoogleApiClient() {

        //  if(checkPermission()){
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        */
/*}else{
            requestPermission();
        }*//*



        //createLocationRequest();
    }

    void startLocation() {

        if (mGoogleApiClient == null) {
            Toast.makeText(ChooseLocation.this, "null", Toast.LENGTH_SHORT).show();
            buildGoogleApiClient();
            startLocation();

            return;
        }

        if(!mGoogleApiClient.isConnected()){
            mGoogleApiClient.connect();

            return;
        }
        if (!checkPermission()) {
            return;
        }

        if (!isLocationEnabled(ChooseLocation.this)) {

            Toast.makeText(ChooseLocation.this, "Turn ON location", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(ChooseLocation.this);
            dialog.setMessage("Hadippa requires your location. Please turn it on.");
            dialog.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 110);
                    //get gps


                }
            });

            dialog.show();
            return;
        }

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            requestPermission();
        }

        if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
            mapFragment.getMapAsync(this);

            lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            mMap.clear();
            //  mMap.getUiSettings().setScrollGesturesEnabled(false);

            LatLng sydney = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            markerOptions = new MarkerOptions().position(sydney).title("Current Location").draggable(true);
            marker = mMap.addMarker(markerOptions);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12));

            task_latitude = String.valueOf(sydney.latitude);
            task_longitude = String.valueOf(sydney.longitude);
            new AsyncTaskForAddress(sydney).execute();

        } else {
            //startLocationUpdates();
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        startLocation();

    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i("Locate>>>", "Connection suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.i("Locate>>>", "Connection failed: ConnectionResult.getErrorCode() = " + connectionResult.getErrorCode());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        //handler.removeCallbacks(UpdateLocation);

    }

    @Override
    public void onLocationChanged(Location location) {


        startLocation();


    }

    void checkLocation(final Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user

        } else {

            startLocation();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==110){
            startLocation();
        }else{

        }}

}
*/
