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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.DataModel;
import com.hadippa.model.Followers_Following;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alm-android on 01-12-2015.
 */

public class Followers extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static RecyclerView mRecyclerView;

    ArrayList<Followers_Following>  followersFollowings = new ArrayList<>();
    
    public static Snackbar snackbar = null;

    public static  RelativeLayout relMain;
    ProgressBar progressBar;
    
    CustomAdapter customAdapter;
    public static Followers newInstance(int page, String title) {
        Followers fragmentFirst = new Followers();
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

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new CustomAdapter());

        fetchFollowers();

        return view;

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

            final Followers_Following followers_following = followersFollowings.get(position);

            Log.d(TAG, "Element " + position + " set." + followers_following);
            Log.d(TAG, "Element " + position + " set." + followers_following);
            if(followers_following != null && followers_following.getFollowed() != null){
                viewHolder.name.setText(followers_following.getFollowed().getFirst_name()+" "+
                        followers_following.getFollowed().getLast_name());

                Glide.with(getActivity())
                        .load(followers_following.getFollowed().getProfile_photo_thumbnail())
                        .into(viewHolder.foodImage);
            }

            viewHolder.tvFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    followers_following.setFollowing(!followers_following.isFollowing());
                    notifyDataSetChanged();
                }
            });

            if(followers_following.isFollowing()){
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.followling_caps));
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers_filled);
                viewHolder.tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_following),null,null,null);
            }
            else{
                viewHolder.tvFollowUnfollow.setText(getResources().getString(R.string.followers));
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_follow),null,null,null);
            }

        }

        @Override
        public int getItemCount() {

            return followersFollowings.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView foodImage;
        private final TextView name,title2;


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

            /*tvFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    *//*if(tvFollowUnfollow.getText().toString().trim().equals(getResources().getString(R.string.followers))){
                        tvFollowUnfollow.setText(getResources().getString(R.string.followling_caps));
                        tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                        tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers_filled);
                        tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_following),null,null,null);
                    }else{
                        tvFollowUnfollow.setText(getResources().getString(R.string.followers));
                        tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                        tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                        tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_follow),null,null,null);


                    }*//*
                }
            });
*/
        }

    }


    private void fetchFollowers() {
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

    class  GetFollowers extends AsyncHttpResponseHandler {

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

                    if(jsonObject.getJSONArray("followers").length()==0){
                        AppConstants.showSnackBar(relMain, "No followers yet.");
                    }else {
                        Type listType = new TypeToken<ArrayList<Followers_Following>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("followers")), listType));

                    }

                    customAdapter = new CustomAdapter();
                    mRecyclerView.setAdapter(customAdapter);
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
   

}



