package com.hadippa.fragments.search;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.SquareImageView;
import com.hadippa.activities.FilterActivity;
import com.hadippa.activities.SearchActivity;
import com.hadippa.model.CityModel;
import com.hadippa.model.PeopleModel;
import com.klinker.android.peekview.builder.Peek;
import com.klinker.android.peekview.callback.SimpleOnPeek;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.hadippa.R.id.view;

/**
 * Created by alm-android on 01-12-2015.
 */

public class SearchTag extends Fragment {

    public AlertDialog alertDialog;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    public RecyclerView mRecyclerView;
    public ProgressBar progressBar;


    public Snackbar snackbar = null;

    public RelativeLayout relMain;

    public CustomAdapter customAdapter;
    public Context context;

    public ArrayList<PeopleModel> tagsModelArrayList = new ArrayList<>();
    public LayoutInflater layoutInflater;

    public SearchTag newInstance(int page, String title) {
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
        layoutInflater = getActivity().getLayoutInflater();
        width = getActivity().getWindowManager().getDefaultDisplay().getWidth() / 3;

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        final GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(2));
        mRecyclerView.setLayoutManager(mLayoutManager);

       /* PeopleModel peopleModel = new PeopleModel();
        peopleModel.setFirst_name("Palak");
        peopleModel.setProfile_photo_thumbnail("https://dyn.web.whatsapp.com/pp?t=l&u=918490800236%40c.us&i=1470245410&ref=0%40FcUK%2BNFYTwRMqIPQN1ytCHa72OOc%2F9cFtfY%2BOuvzbK4ifd0e479xkRN8&tok=0%40jiph%2Fci23ymKJpIF8p%2FliMZqh4IUr%2BeP2YLHxGVUmbk%2FZHaNtzTgGS4Ogng0fxs15J7AF543XhzppQ%3D%3D");
        tagsModelArrayList.add(peopleModel);*/

      /*  customAdapter = new CustomAdapter((Activity)context);
        mRecyclerView.setAdapter(customAdapter);
*/



        //TODO uncomment this call once webservice starts responding.
        if (SearchActivity.edtSearch.getText().toString().length() >= 2) {
            fetchByTags(SearchActivity.edtSearch.getText().toString());
        }else{
            setPreviousData();
        }

        return view;

    }

    public void showPopupDialog(final PeopleModel peopleModel) {

        final View view = layoutInflater.inflate(R.layout.peek_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setView(view);
        builder.setMessage(null);

        final TextView tvFollowUnfollow = (TextView) view.findViewById(R.id.tvFollowUnfollow);
        ((TextView) view.findViewById(R.id.tvName_Age)).setText(peopleModel.getFirst_name() + " " + peopleModel.getLast_name());
        if (peopleModel.getUser_relationship_status() != null && peopleModel.getUser_relationship_status().equals("Following")) {
            tvFollowUnfollow.setText(getResources().getString(R.string.followling_caps));
            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
            tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
            tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_following), null, null, null);
        } else {
            tvFollowUnfollow.setText(getResources().getString(R.string.followers));
            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
            tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
            tvFollowUnfollow.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_user_follow), null, null, null);
        }

        tvFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancelThisDialog();

                if(tvFollowUnfollow.getText().toString().equals(getResources().getString(R.string.followers))) {
                    follow_Unfollow(peopleModel,AppConstants.CONNECTION_FOLLOW, peopleModel.getId());
                }else{
                    follow_Unfollow(peopleModel,AppConstants.CONNECTION_UNFOLLOW, peopleModel.getId());
                }
            }
        });

        Glide.with(context)
                .load(peopleModel.getProfile_photo())
                .into((SquareImageView) view.findViewById(R.id.image));

        alertDialog = builder.create();
        alertDialog.show();

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


    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        private Activity activity;
        private static final String TAG = "CustomAdapter";


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recycler_grid_item, viewGroup, false);

            return new ViewHolder(v);
        }



        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final PeopleModel peopleModel = tagsModelArrayList.get(position);
            viewHolder.getId().setText(peopleModel.getId());

            if (peopleModel.getProfile_photo_thumbnail().equals("")) {
                viewHolder.getImage_view().setImageResource(R.drawable.ic_user_avatar_default);
            } else {

                Glide.with(context)
                        .load(peopleModel.getProfile_photo_thumbnail())
                        .into(viewHolder.getImage_view());


/*
                Peek.into(R.layout.peek_view, new SimpleOnPeek() {
                    @Override
                    public void onInflated(View rootView) {
                        // rootView is the layout inflated from R.layout.image_peek

                        Glide.with(context)
                                .load(peopleModel.getProfile_photo_thumbnail())
                                .into((ImageView) rootView.findViewById(R.id.image));

                    }
                }).applyTo((SearchActivity)context, viewHolder.image_view);*/
            }

            viewHolder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopupDialog(tagsModelArrayList.get(position));
                }
            });

            viewHolder.image_view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    showPopupDialog(tagsModelArrayList.get(position));
                    return false;
                }
            });


        }

        @Override
        public int getItemCount() {


            return tagsModelArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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


    public void fetchByTags(String query) {

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

    public class GetCity extends AsyncHttpResponseHandler {

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


                    if (jsonObject.getJSONArray("users").length() == 0) {
                        AppConstants.showSnackBar(relMain, "No tags found.");
                    } else {
                        Type listType = new TypeToken<ArrayList<PeopleModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        tagsModelArrayList.addAll((ArrayList<PeopleModel>) gson.fromJson(String.valueOf(jsonObject.getJSONArray("users")), listType));

                        editor.putString("tags_users", jsonObject.getJSONArray("users").toString());
                        editor.commit();

                        customAdapter = new CustomAdapter();
                        mRecyclerView.setAdapter(customAdapter);
                    }


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

    //Follow/UnFollow
    public void follow_Unfollow(PeopleModel peopleModel,String type, String id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("followed_id", id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Follow_Unfollow(peopleModel,type));
    }

    public class Follow_Unfollow extends AsyncHttpResponseHandler {

        PeopleModel peopleModel;
        String type;

        Follow_Unfollow(PeopleModel peopleModel,String type){
           this.peopleModel = peopleModel;
            this.type = type;
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


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("response>>", response);
                //post json stored g\here

                if (jsonObject.getBoolean("success")) {

                    alertDialog.dismiss();

                    if(type.equals(AppConstants.CONNECTION_FOLLOW)){

                        peopleModel.setUser_relationship_status("Following");

                    }else{

                        peopleModel.setUser_relationship_status(null);

                    }

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

    void setPreviousData(){

        tagsModelArrayList.clear();
        if(sp.getString("tags_users","").equals("")){



        }else{
            Type listType = new TypeToken<ArrayList<PeopleModel>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            tagsModelArrayList.addAll((ArrayList<PeopleModel>) gson.fromJson(String.valueOf(sp.getString("tags_users","")), listType));

            customAdapter = new CustomAdapter();
            mRecyclerView.setAdapter(customAdapter);
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(tagsModelArrayList, new TypeToken<List<PeopleModel>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString("tags_users", jsonArray.toString());
        editor.commit();
    }
}



