package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.hadippa.model.FollowingModel;
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
                viewHolder.tvName.setText(notificationsBean.getUser().getFirst_name() + " " +
                        notificationsBean.getUser().getLast_name() + " " + notificationsBean.getNotification_details().getMessage());

            //    viewHolder.tvName.setText(notificationsBean.getNotification_type());
                RequestManager requestManager = Glide.with(NotificationActivity.this);
                requestManager

                        .load(notificationsBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .error(R.drawable.place_holder)
                        .into(viewHolder.image_view);
            }

            viewHolder.tvDate.setText(
                    AppConstants.formatDate(notificationsBean.getCreated_at(), "yyyy-mm-dd hh:mm:ss", "dd MMM yy hh:mm a"));

            viewHolder.llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    if (notificationsBean.getNotification_type().equals("activity_request")) {

                        if (notificationsBean.getViewed() == 0) {
                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                            viewHolder.tvFollowUnfollow.setText("Reject");

                            acceptRequest(String.valueOf(notificationsBean.getNotification_details().getActivity_id()), String.valueOf(notificationsBean.getSender_id()), String.valueOf(notificationsBean.getId()), position);
                        } else {

                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                            viewHolder.tvFollowUnfollow.setText("Accept");
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.follow_color));

                            declineRequest(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                                    String.valueOf(notificationsBean.getSender_id())
                                    , String.valueOf(notificationsBean.getId())
                                    , position);
                        }
                    } else if (notificationsBean.getNotification_type().equals("follow_request")) {

                        if (notificationsBean.getViewed() == 0) {
                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                            viewHolder.tvFollowUnfollow.setText("Reject");

                           /* acceptRequest(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                                    String.valueOf(notificationsBean.getSender_id()),String.valueOf(notificationsBean.getId()),position);
                       */
                        } else {

                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                            viewHolder.tvFollowUnfollow.setText("Accept");
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.follow_color));

                          /*  declineRequest(String.valueOf(notificationsBean.getId()),
                                    String.valueOf(notificationsBean.getSender_id()),position);*/
                        }
                    } else if (notificationsBean.getNotification_type().equals("follow_start")) {
                        String type = "";
                        if (notificationsBean.getUser_relationship_status().equals("Connected") ||
                                notificationsBean.getUser_relationship_status().equals("Following")) {
                            viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.follow));
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                            // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                            viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
                            viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);

                            type = AppConstants.CONNECTION_UNFOLLOW;

                        } else {
                            viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.following));
                            viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                            // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                            viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_following));
                            viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);
                            viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);
                            type = AppConstants.CONNECTION_FOLLOW;
                        }

                        follow_Unfollow(notificationsBean.getUser().getId(),type,position);
                    } else if (notificationsBean.getNotification_type().equals("invite_to_join_activity")) {
                        // acceptRequest(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),String.valueOf(notificationsBean.getSender_id()),String.valueOf(notificationsBean.getId()),position);
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

                if (notificationsBean.getViewed() == 0) {
                    viewHolder.tvFollowUnfollow.setText("Accept");
                    viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.follow_color));
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));

                } else {
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));
                    viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                    viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.tvFollowUnfollow.setText("Reject");
                }
            } else if (notificationsBean.getNotification_type().equals("follow_start")) {

                if (notificationsBean.getUser_relationship_status().equals("Connected") ||
                        notificationsBean.getUser_relationship_status().equals("Following")) {
                    viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.following));
                    viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                    // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_following));
                    viewHolder.ivFollowUnfollow.setVisibility(View.VISIBLE);
                    viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);
                    viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);

                } else {
                    viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.follow));
                    viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));
                    viewHolder.ivFollowUnfollow.setVisibility(View.VISIBLE);
                    // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                    viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
                    viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);
                }

            } else if (notificationsBean.getNotification_type().equals("invite to join activity") ||
                    notificationsBean.getNotification_type().equals("invite_to_join_activity")) {

                viewHolder.llFollowUnfollow.setVisibility(View.VISIBLE);

                //Add web calls of Right and left join2

                if (notificationsBean.getViewed() == 0) {
                    viewHolder.tvFollowUnfollow.setText("Join");
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers));

                  /*  activityJoinDecline(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                            AppConstants.ACTIVITY_REQUEST_JOIN);*/
                } else {
                    viewHolder.tvFollowUnfollow.setText("Decline");
                    viewHolder.ivFollowUnfollow.setVisibility(View.GONE);
                    viewHolder.llFollowUnfollow.setBackground(getResources().getDrawable(R.drawable.rounded_followers_filled));

                 /*   activityJoinDecline(String.valueOf(notificationsBean.getNotification_details().getActivity_id()),
                            AppConstants.ACTIVITY_REQUEST_DECLINE);*/

                }

            } else if (notificationsBean.getNotification_type().equals("activity_cancel") ||
                    notificationsBean.getNotification_type().equals("activity_accept") ||
                    notificationsBean.getNotification_type().equals("activity_user_delete") ||
                    notificationsBean.getNotification_type().equals("disjoin_from_activity") ||
                    notificationsBean.getNotification_type().equals("New Message")) {
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

    private void declineRequest(String activity_id, String requester_id, String id, int position) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("activity_id", activity_id);
            requestParams.add("requester_id", requester_id);
            requestParams.add("notifications_id", id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.ACTIVITY_OTHER_REQUEST_DECLINE, requestParams,
                new DeclineRequest(position));
    }

    class DeclineRequest extends AsyncHttpResponseHandler {


        int position;

        DeclineRequest(int position1) {
            this.position = position1;
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


        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                // Toast.makeText(NotificationActivity.this,"Response  >> "+response,Toast.LENGTH_SHORT).show();
                if (jsonObject.getBoolean("success")) {


                    if (notificationModelList.get(position).getViewed() == 0) {
                        notificationModelList.get(position).setViewed(1);
                    } else {
                        notificationModelList.get(position).setViewed(0);
                    }

                    customAdapter.notifyDataSetChanged();
                    //post json stored g\here

                } else {
                    Toast.makeText(NotificationActivity.this, "Response  >> " + response, Toast.LENGTH_SHORT).show();
                    customAdapter.notifyDataSetChanged();
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

    private void acceptRequest(String activity_id, String requester_id, String id, int position) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("activity_id", activity_id);
            requestParams.add("requester_id", requester_id);
            requestParams.add("notifications_id", id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION +
                        AppConstants.ACTIVITY_OTHER_REQUEST_ACCEPT, requestParams,
                new AcceptRequest(position));
    }

    class AcceptRequest extends AsyncHttpResponseHandler {


        int position;

        AcceptRequest(int position) {
            this.position = position;
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


        }


        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");

                //   Toast.makeText(NotificationActivity.this,"Accept",Toast.LENGTH_SHORT).show();
                Log.d("async", "success" + response);
                JSONObject jsonObject = new JSONObject(response);

                if (jsonObject.getBoolean("success")) {

                    if (notificationModelList.get(position).getViewed() == 0) {
                        notificationModelList.get(position).setViewed(1);
                    } else {
                        notificationModelList.get(position).setViewed(0);
                    }

                    customAdapter.notifyDataSetChanged();

                    //post json stored g\here

                } else {

                    customAdapter.notifyDataSetChanged();
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
            Toast.makeText(NotificationActivity.this, "Accept falied", Toast.LENGTH_SHORT).show();
            customAdapter.notifyDataSetChanged();
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    private void activityJoinDecline(String activity_id, String requestFor) {
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
                new ActivityJoinDecline(requestFor));

    }

    class ActivityJoinDecline extends AsyncHttpResponseHandler {

        String requestFor;
        TextView textView;
        LinearLayout linearLayout;

        public ActivityJoinDecline(String requestFor) {
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

                if (!jsonObject.getBoolean("success")) {

                    if (requestFor.equals(AppConstants.ACTIVITY_REQUEST_DECLINE)) {


                    } else {

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            customAdapter.notifyDataSetChanged();
            //  AppConstants.showSnackBar(mainRel,"Could not register. try again");
        }

    }


    private void follow_Unfollow(int id, String type,int position) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("followed_id", String.valueOf(id));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Follow_Unfollow(position));
    }

    class Follow_Unfollow extends AsyncHttpResponseHandler {


        int position;
        String type;

        public Follow_Unfollow(int position) {
            this.position = position;
        }

        @Override
        public void onStart() {
            super.onStart();

            // AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            // AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
          /*  Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
*/
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("response>>", response);
                if (!jsonObject.getBoolean("success")) {

                    customAdapter.notifyDataSetChanged();

                } else {

                    notificationModelList.get(position).setUser_relationship_status(jsonObject.getString("status"));
                    customAdapter.notifyDataSetChanged();

                }
                Log.d("async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            customAdapter.notifyDataSetChanged();
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_invite_to_join)),intent.getExtras().getString("messageData"));
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
