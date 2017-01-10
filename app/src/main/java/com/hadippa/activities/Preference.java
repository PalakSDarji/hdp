package com.hadippa.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.mime.content.StringBody;

/**
 * Created by HP on 11-07-2016.
 */


public class Preference extends Activity implements View.OnClickListener {

    SeekBar discreteBarkms;
    TextView tvAge, tvDistance,tvDone;
    SwitchCompat switchMale, switchFemale;
    ImageView imageBack;

    RangeSeekBar rangeSeekBar;

    String interested_in = "both",age_from = "18",age_to = "99",radius = "3";
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preference);

        sp = PreferenceManager.getDefaultSharedPreferences(Preference.this);
        editor = sp.edit();

        switchMale = (SwitchCompat) findViewById(R.id.switchMale);
        switchFemale = (SwitchCompat) findViewById(R.id.switchFemale);
        discreteBarkms = (SeekBar) findViewById(R.id.discreteBarkms);
        imageBack = (ImageView)findViewById(R.id.imageBack);
        tvDone = (TextView) findViewById(R.id.tvDone);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        rangeSeekBar = (RangeSeekBar) findViewById(R.id.ageRange) ;

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                tvAge.setText(rangeSeekBar.getSelectedMinValue() + " - " + rangeSeekBar.getSelectedMaxValue());
                age_from = String.valueOf(rangeSeekBar.getSelectedMinValue());
                age_to = String.valueOf(rangeSeekBar.getSelectedMaxValue());
            }
        });

        rangeSeekBar.isNotifyWhileDragging();

        discreteBarkms.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvDistance.setText(String.valueOf(progress) + " km");
                radius = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        imageBack.setOnClickListener(this);
        tvDone.setOnClickListener(this);

        setdata();

    }

    void setdata() {

        try {
            if (!sp.getString("user_preference", "").equals("")) {


                JSONObject user_preference = new JSONObject(sp.getString("user_preference", ""));

                if (user_preference.getString("interested_in").equals("male")) {
                    switchMale.setChecked(true);
                    switchFemale.setChecked(false);
                    interested_in = "male";
                } else if (user_preference.getString("interested_in").equals("female")) {
                    switchMale.setChecked(false);
                    switchFemale.setChecked(true);
                    interested_in = "female";
                } else if (user_preference.getString("interested_in").equals("both")) {
                    switchMale.setChecked(true);
                    switchFemale.setChecked(true);
                    interested_in = "both";
                }

                discreteBarkms.setProgress(Integer.parseInt(user_preference.getString("radius")));
                tvDistance.setText(user_preference.getString("radius") + " km");
                radius = user_preference.getString("radius");

                rangeSeekBar.setSelectedMaxValue(Integer.parseInt(user_preference.getString("age_range_to")));
                rangeSeekBar.setSelectedMinValue(Integer.parseInt(user_preference.getString("age_range_from")));
                tvAge.setText(rangeSeekBar.getSelectedMinValue() + " - " + rangeSeekBar.getSelectedMaxValue());
                age_from = String.valueOf(rangeSeekBar.getSelectedMinValue());
                age_to = String.valueOf(rangeSeekBar.getSelectedMaxValue());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    //EDIT PREFERENCS
    private void editPreferences(String interested_in,String age_from,String age_to,String radius)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));
            requestParams.add("interested_in",interested_in);
            requestParams.add("age_range_from",age_from);
            requestParams.add("age_range_to",age_to);
            requestParams.add("radius",radius);


            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.PREFERENCES_EDIT, requestParams,
                new EditPreferences());
    }

    class EditPreferences extends AsyncHttpResponseHandler
    {

        @Override
        public void onStart()
        {
            super.onStart();

            //  AppConstants.showProgressDialog(Preference.this, "Please Wait");

        }


        @Override
        public void onFinish()
        {
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
                JSONObject jsonObject = new JSONObject(response);

                if(jsonObject.getBoolean("success")){
                    finish();
                    overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                }
                Log.d("async","success"+response);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("async","success exc  >>"+ e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.imageBack:

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
                break;

            case R.id.tvDone:

                if(switchFemale.isChecked() && switchMale.isChecked()){
                    interested_in = "both";
                }else if(switchMale.isChecked()){
                    interested_in = "male";
                }else if(switchFemale.isChecked()){
                    interested_in = "female";
                }else{
                    interested_in = "female";
                }
                editPreferences(interested_in,age_from,age_to,radius);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
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
