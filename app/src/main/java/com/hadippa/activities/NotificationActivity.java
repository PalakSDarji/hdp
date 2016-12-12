package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
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

import com.APIClass;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.pink_dark));
        }


        imageBack = (ImageView) findViewById(R.id.imageBack);
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

            requestParams.add("access_token", sp.getString("access_token", ""));

            //    requestParams.add("access_token", "f7ee25834e1ce61d64985221975aa543ea9ce2d4");
            Log.d("request>>", "f7ee25834e1ce61d64985221975aa543ea9ce2d4" + requestParams.toString());
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

                if (notificationModel.isSuccess()) {
                    if (notificationModel.getNotifications().size() > 0) {

                        notificationModelList = notificationModel.getNotifications();
                        customAdapter = new CustomAdapter(notificationModelList);

                        rcItems.setAdapter(customAdapter);

                    } else {
                        Toast.makeText(NotificationActivity.this, "No notification found", Toast.LENGTH_LONG).show();
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

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<NotificationModel.NotificationsBean> data = null;
        private LayoutInflater mInflater;

        CustomAdapter(List<NotificationModel.NotificationsBean> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

            return new ViewHolder(v);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final NotificationModel.NotificationsBean notificationsBean = data.get(position);

            if (notificationsBean.getUser() != null) {
              /*  viewHolder.tvName.setText(notificationsBean.getUser().getFirst_name() + " " +
                        notificationsBean.getUser().getLast_name() + " " + notificationsBean.getNotification_details().getMessage());
*/

                viewHolder.tvName.setText(notificationsBean.getNotification_type());
                RequestManager requestManager = Glide.with(NotificationActivity.this);
                requestManager

                        .load(notificationsBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .into(viewHolder.image_view);
            }

            viewHolder.tvDate.setText(
                    AppConstants.formatDate(notificationsBean.getCreated_at(),"yyyy-mm-dd hh:mm:ss","dd MMM yy hh:mm a"));

            viewHolder.llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    if (notificationsBean.getNotification_type().equals("activity_request")) {

                        if(viewHolder.tvFollowUnfollow.equals("Accept")) {
                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));
                            viewHolder.tvFollowUnfollow.setText(getResources().getColor(R.color.white));
                            viewHolder.tvFollowUnfollow.setText("Reject");
                            acceptRequest(String.valueOf(notificationsBean.getId()), String.valueOf(notificationsBean.getSender_id()), viewHolder.tvFollowUnfollow
                                    ,viewHolder.llFollowUnfollow);
                        }else{

                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                            viewHolder.tvFollowUnfollow.setText("Accept");
                            viewHolder.tvFollowUnfollow.setText(getResources().getColor(R.color.follow_color));
                            declineRequest(String.valueOf(notificationsBean.getId()),
                                    String.valueOf(notificationsBean.getSender_id()),viewHolder.tvFollowUnfollow,viewHolder.llFollowUnfollow);
                        }
                    } else if (notificationsBean.getNotification_type().equals("follow_request")) {

                    } else if (notificationsBean.getNotification_type().equals("follow_start")) {

                    } else if (notificationsBean.getNotification_type().equals("invite_to_join_activity")) {
                      //  acceptRequest(String.valueOf(notificationsBean.getId()),String.valueOf(notificationsBean.getSender_id()),viewHolder.tvFollowUnfollow);
                    } else if (notificationsBean.getNotification_type().equals("activity_cancel")) {

                    } else if (notificationsBean.getNotification_type().equals("activity_user_delete")) {

                    } else if (notificationsBean.getNotification_type().equals("disjoin_from_activity")) {



                    }
                }
            });

            viewHolder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(NotificationActivity.this, ProfileActivity.class);
                    intent.putExtra(AppConstants.PROFILE_KEY, AppConstants.OTHERS_PROFILE);
                    intent.putExtra(AppConstants.FETCH_USER_KEY,String.valueOf(notificationsBean.getUser().getId()));
                    //TODO pass id
                    startActivity(intent);*/
                }
            });


            if (notificationsBean.getNotification_type().equals("activity_request")
                    || notificationsBean.getNotification_type().equals("follow_request")) {
                viewHolder.tvFollowUnfollow.setText("Accept");
                viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);

            } else if (notificationsBean.getNotification_type().equals("follow_start")) {

                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.follow));
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
                viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);
            } else if (notificationsBean.getNotification_type().equals("invite to join activity") ||
                    notificationsBean.getNotification_type().equals("invite_to_join_activity")) {

                viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);

                //Add web calls of Right and left join2

                if(viewHolder.tvFollowUnfollow.getText().toString().equals("Decline")){
                    viewHolder.tvFollowUnfollow.setText("Join");
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));

                    activityJoinDecline(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                            AppConstants.ACTIVITY_REQUEST_JOIN      ,viewHolder.tvFollowUnfollow,viewHolder.llFollowUnfollow);
                }else{
                    viewHolder.tvFollowUnfollow.setText("Decline");
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));

                    activityJoinDecline(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                            AppConstants.ACTIVITY_REQUEST_DECLINE,viewHolder.tvFollowUnfollow,viewHolder.llFollowUnfollow);

                }

            } else if (notificationsBean.getNotification_type().equals("activity_cancel") ||
                    notificationsBean.getNotification_type().equals("activity_accept") ||
                    notificationsBean.getNotification_type().equals("activity_user_delete") ||
                    notificationsBean.getNotification_type().equals("disjoin_from_activity")||
                    notificationsBean.getNotification_type().equals("New Message"))
            {
                viewHolder.llFollowUnfollow.setVisibility(View.GONE);
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

            llFollowUnfollow = (LinearLayout) v.findViewById(R.id.llFollowUnfollow);
            ivFollowUnfollow = (ImageView) v.findViewById(R.id.ivFollowUnfollow);
            tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);
        }
    }

    private void declineRequest(String activity_id, String requester_id,TextView textView,LinearLayout linearLayout) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("activity_id", activity_id);
            requestParams.add("requester_id", requester_id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.ACTIVITY_OTHER_REQUEST_DECLINE, requestParams,
                new DeclineRequest(textView,linearLayout));
    }

    class DeclineRequest extends AsyncHttpResponseHandler {

        TextView textView;
        LinearLayout linearLayout;
        DeclineRequest(TextView textView1,LinearLayout linearLayout1){
            textView = textView1;

            linearLayout = linearLayout1;
        }
        @Override
        public void onStart() {
            super.onStart();

            //  AppConstants.showProgressDialog(Preference.this, "Please Wait");

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


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(NotificationActivity.this,"Response  >> "+response,Toast.LENGTH_SHORT).show();
                if (!jsonObject.getBoolean("success")) {

                    textView.setText("Reject");
                    textView.setTextColor(getResources().getColor(R.color.white));
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));
                    Log.d("response>>", response);
                    //post json stored g\here

                } else {


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

                }
                Log.d("async", "success" + response);
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

    private void acceptRequest(String activity_id, String requester_id,TextView textView,LinearLayout linearLayout) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("activity_id", activity_id);
            requestParams.add("requester_id", requester_id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION +
                AppConstants.ACTIVITY_OTHER_REQUEST_ACCEPT, requestParams,
                new AcceptRequest(textView,linearLayout));
    }

    class AcceptRequest extends AsyncHttpResponseHandler {

        TextView textView;
        LinearLayout linearLayout;
        AcceptRequest(TextView textView1,LinearLayout linearLayout1){
            textView = textView1;
            linearLayout = linearLayout1;
        }
        @Override
        public void onStart() {
            super.onStart();

            //  AppConstants.showProgressDialog(Preference.this, "Please Wait");

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


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                Toast.makeText(NotificationActivity.this,"Response  >> "+response,Toast.LENGTH_SHORT).show();
                if (!jsonObject.getBoolean("success")) {

                    Toast.makeText(NotificationActivity.this,"Sucess > false",Toast.LENGTH_SHORT).show();
                    textView.setText("Accept");
                    textView.setTextColor(getResources().getColor(R.color.follow_color));
                    linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                    //post json stored g\here

                } else {


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

                }
                Log.d("async", "success" + response);
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

    private void activityJoinDecline(String activity_id,String requestFor,TextView textView,LinearLayout linearLayout) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("activity_id", activity_id);

            requestParams.add("access_token", (sp.getString("access_token", "")));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + requestFor, requestParams,
                new ActivityJoinDecline(requestFor,textView,linearLayout));

    }

    class ActivityJoinDecline extends AsyncHttpResponseHandler {

        String requestFor;
        TextView textView;
        LinearLayout linearLayout;

        public ActivityJoinDecline(String requestFor, TextView textView, LinearLayout linearLayout) {
            this.requestFor = requestFor;
            this.textView = textView;
            this.linearLayout = linearLayout;
        }

        @Override
        public void onStart() {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            //   AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            //  AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);

                if(!jsonObject.getBoolean("success")){

                    if(requestFor.equals(AppConstants.ACTIVITY_REQUEST_DECLINE)){
                        textView.setText("Decline");
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));

                    }else{
                        textView.setText("Decline");
                        linearLayout.setBackground(getResources().getDrawable(R.drawable.rounded_followers));

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Could not register. try again");
        }

    }

}
