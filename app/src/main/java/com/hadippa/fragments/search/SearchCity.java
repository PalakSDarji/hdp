package com.hadippa.fragments.search;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.SearchActivity;
import com.hadippa.model.CityModel;
import com.hadippa.model.DataModel;
import com.hadippa.model.PeopleModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cz.msebera.android.httpclient.Header;

/**
 * Created by alm-android on 01-12-2015.
 */

public class SearchCity extends Fragment {

    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

     public RecyclerView mRecyclerView;


    public Snackbar snackbar = null;

    public RelativeLayout relMain;
    public ProgressBar progressBar;
    public ArrayList<CityModel> cityModelArrayList = new ArrayList<>();


    public CustomAdapter customAdapter;
    public SearchCity newInstance(int page, String title) {
        SearchCity fragmentFirst = new SearchCity();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.follownew, container, false);

/*
        View view = inflater.inflate(R.layout.follownew, container, false);

        Log.v("view",""+ view);

        Log.d("initialized",">>City<<");
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

       // progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //crashed

        if(SearchActivity.edtSearch.getText().toString().length()>=2){
            fetchCities(SearchActivity.edtSearch.getText().toString());
        }

        return view;*/

        //ok textviewe aayu
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();

        ((TextView)getView().findViewById(R.id.tvSample)).setText("OK");

        progressBar = (ProgressBar)getView().findViewById(R.id.tempProgress);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) getView().findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar.setVisibility(View.GONE);
        setPreviousData();

        if(SearchActivity.edtSearch.getText().toString().length()>=2){
            fetchCities(SearchActivity.edtSearch.getText().toString());
        }

    }

    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.search_city_list_item, viewGroup, false);

            return new ViewHolder(v);
        }


        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");


            CityModel cityModel = cityModelArrayList.get(position);

            viewHolder.getCity().setText(cityModel.getName());
            viewHolder.getCountry().setText(", "+cityModel.getCountry_name());
            viewHolder.getId().setText(String.valueOf(cityModel.getId()));


        }

        @Override
        public int getItemCount() {

            return cityModelArrayList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView id;
        private final TextView city;
        private final TextView country;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                  

                }
            });


            id = (TextView) v.findViewById(R.id.tvId);
            city = (TextView) v.findViewById(R.id.city);
            country = (TextView) v.findViewById(R.id.country);

        }

        public TextView getCity() {
            return city;
        }

        public TextView getCountry() {
            return country;
        }

        public TextView getId() {
            return id;
        }

    }

    public void fetchCities(String query) {

        cityModelArrayList.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("query", query);


            //Sahil , i will see it tomorrow, meanwhile keep trying!
            //Yeah, sure. Csn i use static ? its the easiest of alll.
            // give it a try
            //already did. but palak asked to not to use staic variables so i changed it to this.
            //do palak and you work together on this proj?
            //yeah.. ok well static prevents reference n it ha its own adv n disadv..somewhere its best somewhere it isnt..// one more

            //thing, it would be better if we prefer to use TabLayout given in support lib
            //ok thanks sahil to cooperating ,
             //see u tomorrow
//yea bro, thanks for helping out.. will try this tomorrow again.. and let you know if done..
            // thanks.. Good night :) yeah gn tc
            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SEARCH_CITY, requestParams,
                new GetCity());

    }

    public class GetCity extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //NUll aave che....
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

                    JSONObject cites = jsonObject.getJSONObject("cities");
                    if(cites.getJSONArray("location_suggestions").length()==0){
                      //  AppConstants.showSnackBar(relMain, "No followers yet.");
                    }else {
                        Type listType = new TypeToken<ArrayList<CityModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        cityModelArrayList = new ArrayList<>();
                        cityModelArrayList = (gson.fromJson(String.valueOf(cites.getJSONArray("location_suggestions")), listType));

                        editor.putString("location_suggestions",cites.getJSONArray("location_suggestions").toString());
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


    void setPreviousData(){

        cityModelArrayList.clear();
        if(sp.getString("location_suggestions","").equals("")){



        }else{
            Type listType = new TypeToken<ArrayList<CityModel>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            cityModelArrayList = new ArrayList<>();
            cityModelArrayList = (gson.fromJson(String.valueOf(sp.getString("location_suggestions","")), listType));

            customAdapter = new CustomAdapter();
            mRecyclerView.setAdapter(customAdapter);
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(cityModelArrayList, new TypeToken<List<CityModel>>() {}.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        editor.putString("location_suggestions", jsonArray.toString());
        editor.commit();
    }
}



