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


import com.APIClass;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.FollowersModel;
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

public class Followers extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static RecyclerView mRecyclerView;

    List<FollowersModel.FollowersBean>  followersFollowings = new ArrayList<>();
    
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

      /*  followersFollowings.add(new FollowersModel());
        followersFollowings.add(new FollowersModel());
        followersFollowings.add(new FollowersModel());
        followersFollowings.add(new FollowersModel());
        followersFollowings.add(new FollowersModel());
        followersFollowings.add(new FollowersModel());
*/
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new CustomAdapter());

        setPreviousData();
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

            final FollowersModel.FollowersBean followers_following = followersFollowings.get(position);

            if(followers_following != null && followers_following.getFollower() != null){
                viewHolder.name.setText(followers_following.getFollower().getFirst_name()+" "+
                        followers_following.getFollower().getLast_name());

                Glide.with(getActivity())
                        .load(followers_following.getFollower().getProfile_photo())
                        .into(viewHolder.foodImage);
            }

            viewHolder.llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.v("??Followers","clicked " + position+"  "+followers_following.getUser_relationship_status());
                    if(followers_following.getUser_relationship_status().equals("Connected")
                            || followers_following.getUser_relationship_status().equals("Following")){

                        viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                        viewHolder.tvFollowUnfollow.setText("Follow");
                        viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                        // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                        viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
                        follow_Unfollow(position,AppConstants.CONNECTION_UNFOLLOW);

                        Log.v("??Followers","clicked " + position);
                    }else{

                        viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
                        viewHolder.tvFollowUnfollow.setText("Following");
                        viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                        viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);

                        follow_Unfollow(position,AppConstants.CONNECTION_FOLLOW);

                        Log.v("??Followers","CONECTFOLLOW clicked " + position);
                    }


                }
            });

            Log.d("followers_following??",followers_following.getFollow_accepted()+"");

            Log.d("checkStauts>>",followers_following.getFollower_id()+ " >> "+followers_following.getUser_relationship_status());
            //TODO check this equals.. checking 1 to make it look like "Followed" is temp. I have no idea what returns from api call.. just wanted to demo the design
            if(followers_following.getUser_relationship_status().equals("Following") ||
                    followers_following.getUser_relationship_status().equals("Connected") ){

                viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
                viewHolder.tvFollowUnfollow.setText("Following");
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);

            }
            else{
                viewHolder.llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.tvFollowUnfollow.setText("Follow");
                viewHolder.tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                viewHolder.ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);



                }

        }

        @Override
        public int getItemCount() {

            return followersFollowings.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //
        private final ImageView foodImage;
        private final TextView name,title2;


        TextView tvFollowUnfollow;
        ImageView ivFollowUnfollow;
        LinearLayout llFollowUnfollow;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){

                }
            });

            ivFollowUnfollow = (ImageView) v.findViewById(R.id.ivFollowUnfollow);
            tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);
            llFollowUnfollow  = (LinearLayout) v.findViewById(R.id.llFollowUnfollow);
            name = (TextView) v.findViewById(R.id.name);
            title2 = (TextView) v.findViewById(R.id.title2);
            foodImage = (ImageView) v.findViewById(R.id.profileImage);
        }

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
                        Type listType = new TypeToken<ArrayList<FollowersModel.FollowersBean>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("followers")), listType));

                    }

                    editor.putString("myFollowers", jsonObject.getJSONArray("followers").toString());
                    editor.commit();

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

    void setPreviousData(){

        followersFollowings.clear();
        if(sp.getString("myFollowers","").equals("")){



        }else{
            Type listType = new TypeToken<ArrayList<FollowersModel.FollowersBean>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            followersFollowings.addAll((ArrayList<FollowersModel.FollowersBean>)
                    gson.fromJson(String.valueOf(sp.getString("myFollowers","")), listType));

            customAdapter = new CustomAdapter();
            mRecyclerView.setAdapter(customAdapter);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(followersFollowings, new TypeToken<List<FollowersModel>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString("myFollowers", jsonArray.toString());
        editor.commit();
    }

    private void follow_Unfollow(int position,String type) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("followed_id", String.valueOf(followersFollowings.get(position).getFollower().getId()));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Follow_Unfollow(position,type));
    }

    class Follow_Unfollow extends AsyncHttpResponseHandler {

        int position;
        String type;

        public Follow_Unfollow(int position,String type) {
            this.position = position;
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

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("success")) {


                    customAdapter.notifyDataSetChanged();


                } else {
                    followersFollowings.get(position).setUser_relationship_status(jsonObject.getString("status"));
                    customAdapter.notifyDataSetChanged();

                }
                Log.d("async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }

    }

}



