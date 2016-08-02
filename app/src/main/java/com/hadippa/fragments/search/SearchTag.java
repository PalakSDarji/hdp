package com.hadippa.fragments.search;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.SearchActivity;
import com.hadippa.model.PeopleModel;
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

public class SearchTag extends Fragment {

    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;

    public static RecyclerView mRecyclerView;
    public static ProgressBar progressBar;


    public static Snackbar snackbar = null;

    public static RelativeLayout relMain;

    public static CustomAdapter customAdapter;
    public static Context context;

    public static ArrayList<PeopleModel> tagsModelArrayList = new ArrayList<>();

    public static SearchTag newInstance(int page, String title) {
        SearchTag fragmentFirst = new SearchTag();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    public static int width;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.follow, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();
        context = getActivity();
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 3;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(SearchActivity.edtSearch.getText().toString().length()>=2) {
            SearchTag.fetchByTags(SearchActivity.edtSearch.getText().toString());
        }


        return view;

    }


    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = space;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = space;
            } else {
                outRect.top = 0;
            }
        }
    }

    void checkConnection(final int page) {

        if (ConnectionDetector.isConnectedToInternet(getActivity())) {

        } else {
            snackbar = Snackbar
                    .make(relMain, "No Internet Connection.", Snackbar.LENGTH_INDEFINITE)
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


   static class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_grid_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            PeopleModel peopleModel = tagsModelArrayList.get(position);
            viewHolder.getId().setText(peopleModel.getId());

            if(peopleModel.getProfile_photo_thumbnail().equals("")){
                viewHolder.getImage_view().setImageResource(R.drawable.ic_user_avatar_default);
            }else {

                Glide.with(context)
                        .load(peopleModel.getProfile_photo_thumbnail())
                        .into(viewHolder.getImage_view());
            }



        }

        @Override
        public int getItemCount() {


            return tagsModelArrayList.size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
         private final TextView id;
       /*   private final ImageView foodImage;
          private final TextView tvDonarName;
          private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
          private final View typeView;*/
        private final ImageView image_view;

        //   TextView tvFollowUnfollow;
        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            //  tvFollowUnfollow = (TextView) v.findViewById(R.id.tvFollowUnfollow);


            image_view = (ImageView) v.findViewById(R.id.image_view);
            image_view.setLayoutParams(new LinearLayout.LayoutParams(width, width));

            id = (TextView) v.findViewById(R.id.text_view);

/*
            id = (TextView) v.findViewById(R.id.tvId);

            foodImage = (ImageView) v.findViewById(R.id.profileImage);*/
           /* tvDonarName = (TextView) v.findViewById(R.id.tvDonarName);
            tvDonarPh = (TextView) v.findViewById(R.id.tvDonarPh);
            tvAddress = (TextView) v.findViewById(R.id.tvAddress);
            tvFoodfor = (TextView) v.findViewById(R.id.tvFoodfor);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            typeView = (View) v.findViewById(R.id.typeView);*/


        }

        public ImageView getImage_view() {
            return image_view;
        }
        
        public TextView getId() {
            return id;
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


    public static void fetchByTags(String query) {

        tagsModelArrayList.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("query", query);




            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SEARCH_TAGS, requestParams,
                new GetCity());

    }

    static class GetCity extends AsyncHttpResponseHandler {

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
                Log.d("async_step_2tags", "success" + response);
                if (jsonObject.getBoolean("success")) {


                    if(jsonObject.getJSONArray("users").length()==0){
                       // AppConstants.showSnackBar(relMain, "No followers yet.");
                    }else {
                        Type listType = new TypeToken<ArrayList<PeopleModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        tagsModelArrayList = new ArrayList<>();
                        tagsModelArrayList = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("users")), listType));

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



