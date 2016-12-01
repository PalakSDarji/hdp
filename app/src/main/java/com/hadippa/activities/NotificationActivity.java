package com.hadippa.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.FollowersModel;
import com.hadippa.model.NotificationModel;
import com.hadippa.model.PeopleModel;
import com.hadippa.model.SearchModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NotificationActivity extends AppCompatActivity {

    private List<String> notifications;
    private ImageView imageBack;
    private RecyclerView rcItems;
    private CustomAdapter customAdapter;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sp = PreferenceManager.getDefaultSharedPreferences(NotificationActivity.this);
        editor = sp.edit();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.pink_dark));
        }


        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent resultIntent = new Intent();

                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        rcItems = (RecyclerView) findViewById(R.id.myRecycler);

        final LinearLayoutManager mLayoutManagerHorizontal = new LinearLayoutManager(this);
        mLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);
        rcItems.setLayoutManager(mLayoutManagerHorizontal);

        fetchNotification();
    }

    List<NotificationModel.NotificationsBean> notificationModelList = new ArrayList<>();

    private void fetchNotification() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

           // requestParams.add("access_token", sp.getString("access_token", ""));

            requestParams.add("access_token", "f7ee25834e1ce61d64985221975aa543ea9ce2d4");
            Log.d("request>>","f7ee25834e1ce61d64985221975aa543ea9ce2d4"+ requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.NOTIFICATION, requestParams,
                new FetchNotifications());
    }

    class FetchNotifications extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(NotificationActivity.this, "Please Wait");

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
                JSONObject jsonObject = new JSONObject(response);

                NotificationModel notificationModel;
                Type listType = new TypeToken<NotificationModel>() {
                }.getType();
                GsonBuilder gsonBuilder = new GsonBuilder();

                Gson gson = gsonBuilder.create();

                notificationModel = (gson.fromJson(jsonObject.toString(), listType));

                if(notificationModel.isSuccess()){
                    if(notificationModel.getNotifications().size()>0){

                        notificationModelList = notificationModel.getNotifications();
                        customAdapter = new CustomAdapter(notificationModelList);

                        rcItems.setAdapter(customAdapter);

                    }else{
                        Toast.makeText(NotificationActivity.this,"No notification found",Toast.LENGTH_LONG).show();
                    }
                }

                Log.d("activityType", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }

    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<NotificationModel.NotificationsBean> data = null;
        private LayoutInflater mInflater;

        CustomAdapter(List<NotificationModel.NotificationsBean> data){
            this.data = data ;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final NotificationModel.NotificationsBean notificationsBean = data.get(position);

            if(notificationsBean.getUser()!=null) {
                viewHolder.tvName.setText(notificationsBean.getUser().getFirst_name() + " " +
                        notificationsBean.getUser().getLast_name() + " " + notificationsBean.getNotification_details().getMessage());

                RequestManager requestManager = Glide.with(NotificationActivity.this);
                requestManager

                        .load(notificationsBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .into(viewHolder.rbButton);
            }
            viewHolder.tvDate.setText(notificationsBean.getCreated_at());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            if(notificationsBean.getNotification_type().equals("follow_request")){

            }
        }

        public NotificationModel.NotificationsBean getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {

            return data.size();
            //return data.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image_view;
        CustomTextView name;
        RelativeLayout rlContainer;
        ImageView rbButton;
        TextView tvName;
        CustomTextView tvDate;

        LinearLayout llFollowUnfollow;
        ImageView ivFollowUnfollow;
        TextView tvFollowUnfollow;

        public ViewHolder(final View v) {
            super(v);

            name = (CustomTextView) v.findViewById(R.id.text_view);
            image_view = (RoundedImageView) v.findViewById(R.id.image_view);
            tvName = (TextView) v.findViewById(R.id.tvName);
            tvDate = (CustomTextView) v.findViewById(R.id.tvDate);
            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
            rbButton = (ImageView) v.findViewById(R.id.rbButton);

            llFollowUnfollow = (LinearLayout)v.findViewById(R.id.llFollowUnfollow);
            ivFollowUnfollow = (ImageView) v.findViewById(R.id.ivFollowUnfollow);
            tvFollowUnfollow = (TextView)v.findViewById(R.id.tvFollowUnfollow);
        }
    }
}
