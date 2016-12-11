package com.hadippa.fragments.search;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import com.hadippa.SquareImageView;
import com.hadippa.activities.ProfileActivity;
import com.hadippa.activities.SearchActivity;
import com.hadippa.model.PeopleModel;
import com.hadippa.model.SearchModel;
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

//Fragment
public class SearchPeople extends Fragment {

    public AlertDialog alertDialog;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    public RecyclerView mRecyclerView;

    
    public Snackbar snackbar = null;
    public ProgressBar progressBar;

    public  RelativeLayout relMain;

    public Context context;
   // public ArrayList<PeopleModel> peopleModelArrayList = new ArrayList<>();

    public CustomAdapter customAdapter;
    private LinearLayout llFollowUnfollow;
    private TextView tvFollowUnfollow;
    private ImageView ivFollowUnfollow;

    public SearchPeople newInstance(int page, String title) {
        SearchPeople fragmentFirst = new SearchPeople();
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

        context = getActivity();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressBar.setVisibility(View.GONE);

        setPreviousData();
      /*  if(SearchActivity.edtSearch.getText().toString().length()>=2) {
            searchTag.fetchByTags(SearchActivity.edtSearch.getText().toString());
        }
*/
        return view;

    }

    void setPreviousData() {


        usersBeen.clear();
        if (sp.getString("previous_search", "").equals("")) {


        } else {
            Type listType = new TypeToken<SearchModel>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();

            SearchModel searchModel = (gson.fromJson(String.valueOf(sp.getString("previous_search", "")), listType));

            setAdapter(searchModel.getUsers());

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

    public List<SearchModel.UsersBean> usersBeen = new ArrayList<>();

    public void setAdapter(List<SearchModel.UsersBean> usersBeen1){

     //   onCreateView(getActivity().getLayoutInflater(),null,null);
        usersBeen = usersBeen1 ;
        customAdapter = new CustomAdapter();
       /* View view = getActivity().getLayoutInflater().inflate(R.layout.follow, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        context = getActivity();

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressBar.setVisibility(View.GONE);
*/
        mRecyclerView.setAdapter(customAdapter);


    }


   public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.search_user_list_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");


            final SearchModel.UsersBean peopleModel = usersBeen.get(position);

           // viewHolder.getId().setText(peopleModel.getId()+"");
            viewHolder.getName().setText(peopleModel.getFirst_name()+" "+peopleModel.getLast_name());

            if(peopleModel.getProfile_photo().equals("")){
                viewHolder.getProfileImage().setImageResource(R.drawable.profile_bg);
            }else {

                Glide.with(context)
                        .load(peopleModel.getProfile_photo())
                        .into(viewHolder.getProfileImage());
            }

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    showPopupDialog(peopleModel);
                }
            });



        }

        @Override
        public int getItemCount() {

            return usersBeen.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView id;
        private final ImageView profileImage;
        private final TextView name;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });


            id = (TextView) v.findViewById(R.id.tvId);
            name = (TextView) v.findViewById(R.id.name);
            profileImage = (ImageView) v.findViewById(R.id.profileImage);
        }
            public TextView getName() {
                return name;
            }

        public TextView getId() {
            return id;
        }

        public ImageView getProfileImage() {
            return profileImage;
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


    public void fetchPeople(String query) {

        usersBeen.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("query", query);




            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SEARCH_PEOPLE, requestParams,
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
                Log.d("async_step_2", "success" + response);
                if (jsonObject.getBoolean("success")) {


                    if(jsonObject.getJSONArray("users").length()==0){
                     //   AppConstants.showSnackBar(relMain, "No followers yet.");
                    }else {
                        Type listType = new TypeToken<ArrayList<SearchModel.UsersBean>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        usersBeen = new ArrayList<>();

                        usersBeen = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("users")), listType));


                        editor.putString("people_users",jsonObject.getJSONArray("users").toString());
                        editor.commit();
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


   /* @Override
    public void onDetach() {
        super.onDetach();

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(peopleModelArrayList, new TypeToken<List<PeopleModel>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString("people_users", jsonArray.toString());
        editor.commit();
    }
*/

    public void showPopupDialog(final SearchModel.UsersBean peopleModel) {

        final View view = getActivity().getLayoutInflater().inflate(R.layout.peek_view, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setView(view);
        builder.setMessage(null);

        llFollowUnfollow = (LinearLayout) view.findViewById(R.id.llFollowUnfollow);
        ivFollowUnfollow = (ImageView) view.findViewById(R.id.ivFollowUnfollow);


        tvFollowUnfollow = (TextView) view.findViewById(R.id.tvFollowUnfollow);
        ((TextView) view.findViewById(R.id.tvName_Age)).setText(peopleModel.getFirst_name() + " " + peopleModel.getLast_name());
        ((TextView) view.findViewById(R.id.tvName_Age)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alertDialog != null && alertDialog.isShowing()) alertDialog.dismiss();
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra(AppConstants.PROFILE_KEY, AppConstants.OTHERS_PROFILE);
                intent.putExtra(AppConstants.FETCH_USER_KEY,String.valueOf(peopleModel.getId()));
                //TODO pass id
                startActivity(intent);

            }
        });

        tvFollowUnfollow.setText(peopleModel.getUser_relationship_status());

        if (peopleModel.getUser_relationship_status() != null && (peopleModel.getUser_relationship_status().equals("Following") ||
                peopleModel.getUser_relationship_status().equals("Connected"))) {

            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
            llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
            ivFollowUnfollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_following));
        } else {

            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
            llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
            ivFollowUnfollow.setImageDrawable(getResources().getDrawable(R.drawable.ic_user_follow));}

      /*  if (peopleModel.getUser_relationship_status() != null && peopleModel.getUser_relationship_status().equals("Following")) {

            setFollowButtonView(true);
        }
        else{
            setFollowButtonView(false);
        }
*/
        llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancelThisDialog();

                if(tvFollowUnfollow.getText().toString().equals(getResources().getString(R.string.follow))) {
                    setFollowButtonView(true);
                    follow_Unfollow(peopleModel,AppConstants.CONNECTION_FOLLOW, String.valueOf(peopleModel.getId()));
                }
                else{
                    setFollowButtonView(false);
                    follow_Unfollow(peopleModel,AppConstants.CONNECTION_UNFOLLOW, String.valueOf(peopleModel.getId()));
                }
            }
        });

        Glide.with(context)
                .load(peopleModel.getProfile_photo())
                .into((SquareImageView) view.findViewById(R.id.image));

        alertDialog = builder.create();
        alertDialog.show();

    }

    private void setFollowButtonView(boolean isFollowing){

        if(isFollowing){
            tvFollowUnfollow.setText(getResources().getString(R.string.following));
            ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);
            llFollowUnfollow.setSelected(true);
            tvFollowUnfollow.setSelected(true);
        }
        else{
            tvFollowUnfollow.setText(getResources().getString(R.string.follow));
            ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
            tvFollowUnfollow.setSelected(false);
            llFollowUnfollow.setSelected(false);
        }
    }
    public void follow_Unfollow(SearchModel.UsersBean peopleModel,String type, String id) {
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

        SearchModel.UsersBean peopleModel;
        String type;

        Follow_Unfollow(SearchModel.UsersBean peopleModel,String type){
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

                    //alertDialog.dismiss();

                    peopleModel.setUser_relationship_status(jsonObject.getString("status"));
                    alertDialog.dismiss();
                  /*  if(type.equals(AppConstants.CONNECTION_FOLLOW)){

                     //   peopleModel.setUser_relationship_status("Following");

                    }else{

                    //    peopleModel.setUser_relationship_status(null);

                    }
*/
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

            //TODO for sahil.. check for the failure response, and change follow/following of button again by calling
            // setFollowButtonView to change the state back to original.
        }

    }

}



