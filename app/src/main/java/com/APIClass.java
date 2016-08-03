package com;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import com.commonclasses.notification.ServerUtilities;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.HomeScreen;
import com.hadippa.activities.Preference;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by HP on 03-08-2016.
 */
public class APIClass {


    //GET PREFERENCS
    private void getPreferences()
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));


            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.PREFERENCES, requestParams,
                new GetPreferences());
    }

    class GetPreferences extends AsyncHttpResponseHandler
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
                if(jsonObject.has("access_token")) {

                    Log.d("response>>",response);
                    //post json stored g\here

                }else{


                  //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

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

    //EDIT PREFERENCS
    private void editPreferences(String interested_in,String age_from,String age_to,String radius)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));
            requestParams.add("interested_in",interested_in);
            requestParams.add("age_from",age_from);
            requestParams.add("age_to",age_to);
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
                if(jsonObject.has("access_token")) {

                    Log.d("response>>",response);
                    //post json stored g\here

                }else{


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

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

    //MY Profile
    private void myProfile()
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));

            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.MY_PROFILE, requestParams,
                new MyProfile());
    }

    class MyProfile extends AsyncHttpResponseHandler
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
                if(jsonObject.has("access_token")) {

                    Log.d("response>>",response);
                    //post json stored g\here

                }else{


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

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

    //Others Profile
    private void othersProfile(String id)
    {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try
        {

            requestParams.add("access_token",sp.getString("access_token",""));
            requestParams.add("id",id);

            Log.d("request>>",requestParams.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL+AppConstants.API_VERSION + AppConstants.OTHERS_PROFILE, requestParams,
                new OthersProfile());
    }

    class OthersProfile extends AsyncHttpResponseHandler
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
                if(jsonObject.has("access_token")) {

                    Log.d("response>>",response);
                    //post json stored g\here

                }else{


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

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
}
