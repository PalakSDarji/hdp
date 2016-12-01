package com.hadippa.fragments.following;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.commonclasses.connection.ConnectionDetector;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.FollowersModel;
import com.hadippa.model.FollowingModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alm-android on 01-12-2015.
 */

public class Following extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static RecyclerView mRecyclerView;
    List <FollowingModel.FollowingBean> followersFollowings = new ArrayList<>();
    public static Snackbar snackbar = null;
    public static  RelativeLayout linearMain;
    ProgressBar progressBar;
    CustomAdapter customAdapter;

    public static Following newInstance(int page, String title) {
        Following fragmentFirst = new Following();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.follow, null, false);

       /* followersFollowings.add(new FollowingModel());
        followersFollowings.add(new FollowingModel());
        followersFollowings.add(new FollowingModel());
        followersFollowings.add(new FollowingModel());
        followersFollowings.add(new FollowingModel());
        followersFollowings.add(new FollowingModel());*/

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearMain = (RelativeLayout) view.findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        setPreviousData();
        fetchFollowing();

        return view;

    }

    void checkConnection(final int page) {

        if (ConnectionDetector.isConnectedToInternet(getActivity())) {

        } else {
            snackbar = Snackbar
                    .make(linearMain, "No Internet Connection.", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Try Again", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            snackbar.dismiss();
                            checkConnection(page);
                        }
                    });

            snackbar.show();
        }
    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.followers_list_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final FollowingModel.FollowingBean followers_following = followersFollowings.get(position);

          /*  if(followers_following.isFollowing()){
                viewHolder.ivFollowUnfollow.setBackgroundResource(R.drawable.ic_user_following);
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.following));
            }
            else{
                viewHolder.ivFollowUnfollow.setBackgroundResource(R.drawable.ic_user_follow);
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.follow));
            }*/
            viewHolder.getName().setText(followers_following.getFollowed().getFirst_name()+" "+
                    followers_following.getFollowed().getLast_name());

            Glide.with(getActivity())
                    .load(followers_following.getFollowed().getProfile_photo_thumbnail())
                    .into(viewHolder.getProfileImage());

            Log.d("followers_following??>>",followers_following.getFollow_accepted()+"");
            if(followers_following.getUser_relationship_status().equals("Following") ||
                    followers_following.getUser_relationship_status().equals("Connected") ){

                viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.following));
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                //viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers_filled);
                viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);

            }
            else{

                viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.follow));
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);


            }


            viewHolder.llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("Followers","clicked " + position);
                    if(followers_following.getUser_relationship_status().equals("Following") ||
                            followers_following.getUser_relationship_status().equals("Connected") ){
                        follow_Unfollow(followers_following,AppConstants.CONNECTION_UNFOLLOW);
                    }else{
                        follow_Unfollow(followers_following,AppConstants.CONNECTION_FOLLOW);
                    }


                }
            });

        }

        @Override
        public int getItemCount() {

            return followersFollowings.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView foodImage,ivFollowUnfollow;
        private final TextView name,title2;
        private LinearLayout llFollowUnfollow;
        TextView tvFollowUnfollow;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                }
            });

            tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);
            name = (TextView) v.findViewById(R.id.name);
            title2 = (TextView) v.findViewById(R.id.title2);
            foodImage = (ImageView) v.findViewById(R.id.profileImage);
            ivFollowUnfollow = (ImageView) v.findViewById(R.id.ivFollowUnfollow);
            llFollowUnfollow = (LinearLayout) v.findViewById(R.id.llFollowUnfollow);

          /*  llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(tvFollowUnfollow.getText().toString().trim().equals(getResources().getString(R.string.followers))){
                      *//*  tvFollowUnfollow.setText(getResources().getString(R.string.followling_caps));
                        //tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                        ivFollowUnfollow.setBackgroundResource(R.drawable.ic_user_following);*//*
                        follow_Unfollow(followers_following,AppConstants.CONNECTION_UNFOLLOW);
                        //tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_following),null,null,null);
                    }
                    else{
                       *//* tvFollowUnfollow.setText(getResources().getString(R.string.followers));
                        //tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                        ivFollowUnfollow.setBackgroundResource(R.drawable.ic_user_follow);*//*
                        //tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_follow),null,null,null);
                        follow_Unfollow(followers_following,AppConstants.CONNECTION_FOLLOW);

                    }
                }
            });*/

        }

        public TextView getTvFollowUnfollow() {
            return tvFollowUnfollow;
        }

        public TextView getTitle2() {
            return title2;
        }

        public TextView getName() {
            return name;
        }

        public ImageView getProfileImage() {
            return foodImage;
        }

    }

    private void fetchFollowing() {

        followersFollowings.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));




            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CONNECTION_FOLLOWING, requestParams,
                new GetFollowing());

    }

    class GetFollowing extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

              progressBar.setVisibility(View.VISIBLE);
           // AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
         //   AppConstants.dismissDialog();
            progressBar.setVisibility(View.GONE);
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

                    if(jsonObject.getJSONArray("following").length()==0){
                        AppConstants.showSnackBar(linearMain, "No followings yet.");
                    }else {
                        Type listType = new TypeToken<ArrayList<FollowingModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("following")), listType));

                    }

                    editor.putString("myFollowing", jsonObject.getJSONArray("following").toString());
                    editor.commit();

                    customAdapter = new CustomAdapter();
                    mRecyclerView.setAdapter(customAdapter);

                } else {
                    AppConstants.showSnackBar(linearMain, "Could not refresh feed");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            AppConstants.showSnackBar(linearMain, "Could not refresh feed");
        }

    }

    void setPreviousData(){

        followersFollowings.clear();
        if(sp.getString("myFollowing","").equals("")){



        }else{
            Type listType = new TypeToken<ArrayList<FollowingModel.FollowingBean>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            followersFollowings.addAll((ArrayList<FollowingModel.FollowingBean>) gson.fromJson(String.valueOf(sp.getString("myFollowing","")), listType));

            customAdapter = new CustomAdapter();
            mRecyclerView.setAdapter(customAdapter);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(followersFollowings, new TypeToken<List<FollowingModel>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString("myFollowers", jsonArray.toString());
        editor.commit();
    }


    private void follow_Unfollow(FollowingModel.FollowingBean followersModel,String type) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("followed_id", String.valueOf(followersModel.getFollowed_id()));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Follow_Unfollow(followersModel,type));
    }

    class Follow_Unfollow extends AsyncHttpResponseHandler {

        FollowingModel.FollowingBean followersModel;
        String type;

        public Follow_Unfollow(FollowingModel.FollowingBean followersModel,String type) {
            this.followersModel = followersModel;
            this.type = type;
        }

        @Override
        public void onStart() {
            super.onStart();

            AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
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
                if (jsonObject.getBoolean("success")) {

                    if(jsonObject.getString("status").equals("Following")){
                        followersModel.setUser_relationship_status("Following");
                    }else{
                        followersModel.setUser_relationship_status("Connected");
                    }

                    customAdapter.notifyDataSetChanged();

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


}



