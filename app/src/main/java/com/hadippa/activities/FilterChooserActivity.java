package com.hadippa.activities;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.ChooseDateModel;
import com.hadippa.model.DataModel;
import com.hadippa.model.FilterModel;
import com.hadippa.model.MeraEventPartyModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apmem.tools.layouts.FlowLayout;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class FilterChooserActivity extends AppCompatActivity {

    CustomAdapter customAdapter = new CustomAdapter();
    RecyclerView dateList;
    LayoutInflater inflater;

    ImageView imageBack;

    List<ChooseDateModel.DatesBean> datesBeen = new ArrayList<>();

    ArrayList<String> dateArray = new ArrayList<>();

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_chooser);

        sp = PreferenceManager.getDefaultSharedPreferences(FilterChooserActivity.this);
        editor =sp.edit();
        inflater = getLayoutInflater();
        dateList = (RecyclerView) findViewById(R.id.dateList);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        ((CustomTextView)findViewById(R.id.tvNext))
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fetchPosts();
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dateList.setLayoutManager(mLayoutManager);

        fetchDates();
    }

    private void fetchDates() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();
        try {

            requestParams.add("activity_type", String.valueOf(getIntent().getIntegerArrayListExtra("activity_type")).replace("[","").replace("]",""));
           // requestParams.add("activity_id", lon);
            requestParams.add("activity_id",getIntent().getStringArrayListExtra("activity_id").toString().replace("[","").replace("]",""));
            if(getIntent().getExtras().getBoolean("hasTravel")) {
                requestParams.add("activity", getIntent().getExtras().getString("activity_name"));
                requestParams.add("activity_from", getIntent().getExtras().getString("activity_from"));
                requestParams.add("activity_to", getIntent().getExtras().getString("activity_to"));
            }

          //  requestParams.add("access_token", "744768a9484e47d3e8cce4050358fae002ddafe9");
            requestParams.add("access_token", sp.getString("access_token",""));
            requestParams.add("start_date","");
            requestParams.add("end_date","");
            Log.d("prepareMeraEvents", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.POST_FILTERS, requestParams,
                new FetchDates());
    }

    class FetchDates extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(FilterChooserActivity.this, "Please Wait");

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
                Log.d("restaurantsBeanList", "Size >> " + response);
                JSONObject obj = new JSONObject(response);
                Gson gson = new Gson();

                ChooseDateModel chooseDateModel = gson.fromJson(obj.toString(), ChooseDateModel.class);
                if (chooseDateModel.isSuccess()) {
                    Log.d("prepareMeraEvents", "Size >> " + response);

                    if(chooseDateModel.getDates().size() > 0){
                        datesBeen.addAll(chooseDateModel.getDates().get(0));

                        customAdapter = new CustomAdapter();
                        dateList.setAdapter(customAdapter);
                    }else{

                        Toast.makeText(FilterChooserActivity.this,"No data",Toast.LENGTH_SHORT).show();
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



    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.choose_date_list_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final ChooseDateModel.DatesBean datesBean = datesBeen.get(position);

            viewHolder.date.setText(datesBean.getActivity_date());
            viewHolder.day.setText(datesBean.getActivity_day());
            viewHolder.month.setText(datesBean.getActivity_month());

            viewHolder.linearDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Drawable drawable = viewHolder.linearDate.getBackground();
                    if(drawable.getConstantState()==(getResources().getDrawable(R.drawable.rounded_date)).getConstantState()){
                        v.setBackgroundResource(R.drawable.rounded_date_selected);
                        dateArray.add(datesBean.getFull_date());
                    }else{
                        v.setBackgroundResource(R.drawable.rounded_date);
                        dateArray.remove(datesBean.getFull_date());
                    }

                    Log.d("dateArray>>",dateArray.toString());
                }
            });

        }

        @Override
        public int getItemCount() {

            return datesBeen.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
     /*  private final TextView id;
        private final ImageView foodImage;
        private final TextView tvDonarName;
        private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
        private final View typeView;*/

        private final LinearLayout linearDate;
        TextView date,day,month;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            linearDate = (LinearLayout)v.findViewById(R.id.linearDate);
            date = (TextView)v.findViewById(R.id.date);
            day = (TextView)v.findViewById(R.id.day);
            month = (TextView)v.findViewById(R.id.month);



        }


        public LinearLayout getLinearDate() {
            return linearDate;
        }


    }


    private void fetchPosts() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {


            requestParams.add("date", dateArray.toString().replace("[","").replace("]",""));
            requestParams.add("access_token", sp.getString("access_token",""));

            Log.d("prepareMeraEvents", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.FETCH_POST, requestParams,
                new FetchPosts());
    }

    class FetchPosts extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(FilterChooserActivity.this, "Please Wait");

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
                Log.d("restaurantsBeanList", "Size >> " + response);
                JSONObject obj = new JSONObject(response);

                if(obj.getBoolean("success")) {
                    editor.putString("posts", obj.getString("posts"));
                    editor.commit();

                    Type listType = new TypeToken<ArrayList<DataModel>>() {
                    }.getType();
                    GsonBuilder gsonBuilder = new GsonBuilder();

                    Gson gson = gsonBuilder.create();

                    ArrayList<DataModel> dataModels = gson.fromJson(sp.getString("posts", ""), listType);

                    if(dataModels.size()>0){
                        Intent intent = new Intent(FilterChooserActivity.this, HomeScreen.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }else{
                        Toast.makeText(FilterChooserActivity.this,"No post found",Toast.LENGTH_SHORT).show();
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


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_filter_chooser)),intent.getExtras().getString("messageData"));
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
