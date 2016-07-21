package com.hadippa.fragments.following;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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


import com.commonclasses.connection.ConnectionDetector;
import com.hadippa.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alm-android on 01-12-2015.
 */

public class Followers extends Fragment {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public static RecyclerView mRecyclerView;

    
    public static Snackbar snackbar = null;

    public static  RelativeLayout linearMain;
    
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


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        linearMain = (RelativeLayout) view.findViewById(R.id.linearMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(new CustomAdapter());


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
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");




        }

        @Override
        public int getItemCount() {




            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
      /*  private final TextView id;
        private final ImageView foodImage;
        private final TextView tvDonarName;
        private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
        private final View typeView;*/

        TextView tvFollowUnfollow;
        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  

                }
            });

            tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);

/*
            id = (TextView) v.findViewById(R.id.tvId);

            foodImage = (ImageView) v.findViewById(R.id.profileImage);*/
           /* tvDonarName = (TextView) v.findViewById(R.id.tvDonarName);
            tvDonarPh = (TextView) v.findViewById(R.id.tvDonarPh);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvFoodfor = (TextView) v.findViewById(R.id.tvFoodfor);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            typeView = (View) v.findViewById(R.id.typeView);*/

            tvFollowUnfollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(tvFollowUnfollow.getText().toString().trim().equals(getResources().getString(R.string.followers))){
                        tvFollowUnfollow.setText(getResources().getString(R.string.followling_caps));
                        tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                        tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers_filled);
                        tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_following),null,null,null);
                    }else{
                        tvFollowUnfollow.setText(getResources().getString(R.string.followers));
                        tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                        tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                        tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_follow),null,null,null);


                    }
                }
            });

        }

        public TextView getTvFollowUnfollow() {
            return tvFollowUnfollow;
        }

      /*  public TextView getTvStatus() {
            return tvStatus;
        }

        public TextView getName() {
            return tvDonarName;
        }

        public TextView getId() {
            return id;
        }

        public ImageView getProfileImage() {
            return foodImage;
        }


        public TextView getTvDonarPh() {
            return tvDonarPh;
        }

        public TextView getTvAddress() {
            return tvAddress;
        }

        public TextView getTvFoodfor() {
            return tvFoodfor;
        }

        public View getTypeView() {
            return typeView;
        }
*/

    }

   

}



