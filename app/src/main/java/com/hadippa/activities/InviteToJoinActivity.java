package com.hadippa.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.following.Followers;
import com.hadippa.model.FollowersModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class InviteToJoinActivity extends AppCompatActivity {

    RecyclerView myRecycler;
    ImageView imageBack;
    ArrayList<FollowersModel> followersFollowings = new ArrayList<>();

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RelativeLayout relMain;

    public CustomAdapter customAdapter;
    
    ArrayList<String> selectedList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_join);


        relMain = (RelativeLayout) findViewById(R.id.activity_invite_to_join);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedId", selectedList);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(InviteToJoinActivity.this);
        editor = sp.edit();

        selectedList = getIntent().getStringArrayListExtra("selectedId");
        myRecycler = (RecyclerView) findViewById(R.id.myRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(mLayoutManager);

        fetchFollowers();
//        myRecycler.setAdapter(new CustomAdapter());
    }


    @Override
    public void onBackPressed() {

        Log.d("selectedId >> 4", selectedList.toString());
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedId", selectedList);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    private void fetchFollowers() {

        followersFollowings.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CONNECTION_FOLLOWERS, requestParams,
                new GetFollowers());

    }

    class GetFollowers extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  progressBar.setVisibility(View.VISIBLE);
            AppConstants.showProgressDialog(InviteToJoinActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
            // progressBar.setVisibility(View.GONE);
        }


        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("async_step_2", "success" + response);
                if (jsonObject.getBoolean("success")) {

                    if (jsonObject.getJSONArray("followers").length() == 0) {
                        AppConstants.showSnackBar(relMain, "No followers yet.");
                    } else {
                        Type listType = new TypeToken<ArrayList<FollowersModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("followers")), listType));

                    }

                    editor.putString("myFollowers", jsonObject.getJSONArray("followers").toString());
                    editor.commit();

                    customAdapter = new CustomAdapter();
                    myRecycler.setAdapter(customAdapter);
                } else {
                    AppConstants.showSnackBar(relMain, "Could not refresh feed");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            AppConstants.showSnackBar(relMain, "Could not refresh feed");
        }

    }

    void setPreviousData() {

        followersFollowings.clear();
        if (sp.getString("myFollowers", "").equals("")) {


        } else {
            Type listType = new TypeToken<ArrayList<FollowersModel>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            followersFollowings.addAll((ArrayList<FollowersModel>) gson.fromJson(String.valueOf(sp.getString("myFollowers", "")), listType));

            customAdapter = new CustomAdapter();
            myRecycler.setAdapter(customAdapter);
        }

    }


    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_invite_people, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");
            final FollowersModel followers_following = followersFollowings.get(position);

            if (followers_following != null && followers_following.getFollower() != null) {
                viewHolder.name.setText(followers_following.getFollower().getFirst_name() + " " +
                        followers_following.getFollower().getLast_name());

                Glide.with(InviteToJoinActivity.this)
                        .load(followers_following.getFollower().getProfile_photo())
                        .into(viewHolder.image_view);

                boolean found = false;
                for (int i = 0; i < selectedList.size(); i++) {

                    if (selectedList.get(i).equals(followersFollowings.get(position).getId())) {

                        found = true;
                        viewHolder.rbButton.setChecked(true);
                    }

                }


                if(!found) {
                    viewHolder.rbButton.setChecked(false);

                }


            }

            viewHolder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    boolean found = false;
                        for (int i = 0; i < selectedList.size(); i++) {

                            if (selectedList.get(i).equals(followersFollowings.get(position).getId())) {
                                selectedList.remove(i);
                                found = true;
                                viewHolder.rbButton.setChecked(false);
                                break;
                            }

                        }


                    if(!found) {
                        viewHolder.rbButton.setChecked(true);
                        selectedList.add(followersFollowings.get(position).getId());
                    }



                }
            });


        }

        public FollowersModel getItem(int position) {
            return followersFollowings.get(position);
        }

        @Override
        public int getItemCount() {

            return followersFollowings.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image_view;
        CustomTextView name;
        RadioButton rbButton;

        public ViewHolder(final View v) {
            super(v);

            //  rbButton = (RadioButton)v.findViewById(R.id.rbButton);
            name = (CustomTextView) v.findViewById(R.id.text_view);
            image_view = (RoundedImageView) v.findViewById(R.id.image_view);
            rbButton = (RadioButton) v.findViewById(R.id.rbButton);

        }

    }

}
