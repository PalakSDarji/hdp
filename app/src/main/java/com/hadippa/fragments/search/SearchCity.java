package com.hadippa.fragments.search;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.hadippa.activities.FilterChooserActivity;
import com.hadippa.activities.HomeScreen;
import com.hadippa.activities.SearchActivity;
import com.hadippa.model.CityModel;
import com.hadippa.model.DataModel;
import com.hadippa.model.PeopleModel;
import com.hadippa.model.SearchModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
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

//Frag,ent
public class SearchCity extends Fragment {

    private static final int CITY_HEADER = 0;
    private static final int CITY_DATA = 1;

    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    public RecyclerView mRecyclerView;

    ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean> recentSearchData = new ArrayList<>();
//no staic :D
    // I was trying this today..
    //buttokay nai thatu ?
    //rv or adapter null reh 6e
    // .in my case rv but rehva de na thatu hoy to... mebiju solution kari j didhu che em on
    //ok np but let me check
    //all yours
    //:D

    public Snackbar snackbar = null;

    public RelativeLayout relMain;
    public ProgressBar progressBar;


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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //return inflater.inflate(R.layout.follownew, container, false);


        View view = inflater.inflate(R.layout.follow, container, false);

        Log.v("view", "" + view);

        Log.d("initialized", ">>City<<");
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        Gson gson = new Gson();

        if (!sp.getString("recentSearch", "").equals("")) {
            recentSearchData = gson.fromJson(sp.getString("recentSearch", ""),
                    new TypeToken<List<SearchModel.CitiesBean.LocationSuggestionsBean>>() {
                    }.getType());
        }
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) view.findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //crashed

        //setPreviousData();

        if (recentSearchData != null) {

            removeExtra();
            setAdapter(recentSearchData,"");
        } else {
            recentSearchData = new ArrayList<>();
            setAdapter(recentSearchData,"");
        }
        return view;
    }

    public static boolean updatePost = false;


    void removeExtra() {

        if (recentSearchData.size() > 5) {
            recentSearchData.subList(5, recentSearchData.size()).clear();

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = sp.edit();

        //TODO commented by palak
        /*((TextView)getView().findViewById(R.id.tvSample)).setText("OK");*/

        progressBar = (ProgressBar) getView().findViewById(R.id.progressBar);


        mRecyclerView = (RecyclerView) getView().findViewById(R.id.recyclerView);
        relMain = (RelativeLayout) getView().findViewById(R.id.relMain);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        progressBar.setVisibility(View.GONE);

    }

    public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final String TAG = "CustomAdapter";
        private List<SearchModel.CitiesBean.LocationSuggestionsBean> listItems, filterList;

        public CustomAdapter(List<SearchModel.CitiesBean.LocationSuggestionsBean> listItems) {
            this.listItems = listItems;

            this.filterList = new ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean>();
            // we copy the original list to the filter list and use it for setting row values
            this.filterList.addAll(this.listItems);
        }

        @Override
        public int getItemViewType(int position) {

            if (locationSuggestionsBeen == null) {
                return -1;
            }
            if (locationSuggestionsBeen.get(position) == null) {
                return -1;
            }

            if (locationSuggestionsBeen.get(position).isHeader()) {
                return CITY_HEADER;
            } else {
                return CITY_DATA;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            Log.v(TAG, "OnCreateViewHolder called  : " + viewType);
            switch (viewType) {
                case CITY_HEADER:
                    View v1 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_header, viewGroup, false);
                    return new HeaderViewHolder(v1);

                case CITY_DATA:
                    View v2 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_list_item, viewGroup, false);
                    return new CityViewHolder(v2);

                default:
                    View v3 = LayoutInflater.from(viewGroup.getContext())
                            .inflate(R.layout.search_city_header, viewGroup, false);
                    return new HeaderViewHolder(v3);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            switch (viewHolder.getItemViewType()) {
                case CITY_HEADER:
                    HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;
                    SearchModel.CitiesBean.LocationSuggestionsBean cityHeader = locationSuggestionsBeen.get(position);

                    headerViewHolder.tvSearchCityHeader.setText(cityHeader.getName());
                    break;

                case CITY_DATA:

                    final CityViewHolder cityViewHolder = (CityViewHolder) viewHolder;
                    SearchModel.CitiesBean.LocationSuggestionsBean cityModel = filterList.get(position);

                    if (cityModel.isFromCityList()) {
                        cityViewHolder.getIvLocation().setVisibility(View.GONE);
                    } else {
                        cityViewHolder.getIvLocation().setVisibility(View.VISIBLE);
                    }

                    if (cityModel.getCountry_name() != null) {
                        cityViewHolder.getCity().setText(cityModel.getName() + ", " + cityModel.getCountry_name());
                    } else {
                        cityViewHolder.getCity().setText(cityModel.getName());
                    }

                    cityViewHolder.getId().setText(String.valueOf(cityModel.getId()));

                    if (locationSuggestionsBeen.get(position).isCurrentLocation()) {
                        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //TODO define click listener to get location. (Sahil)
                            }
                        });
                        cityViewHolder.getIvRightLogo().setVisibility(View.GONE);
                    } else {
                        cityViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                if (recentSearchData != null) {

                                    for (int i = 0; i < recentSearchData.size(); i++) {
                                        if (recentSearchData.get(i).getId() == (locationSuggestionsBeen.get(position).getId())) {
                                            recentSearchData.remove(i);
                                        }
                                    }
                                    recentSearchData.add(0, locationSuggestionsBeen.get(position));

                                    removeExtra();

                                    editor.putString("recentSearch", new Gson().toJson(recentSearchData));
                                    editor.commit();
                                } else {
                                    recentSearchData = new ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean>();

                                    recentSearchData.add(0, locationSuggestionsBeen.get(position));

                                    removeExtra();

                                    editor.putString("recentSearch", new Gson().toJson(recentSearchData));
                                    editor.commit();
                                }

                                editor.putString("cityName",locationSuggestionsBeen.get(position).getName());
                                editor.commit();
                                updatePost = true;

                                //  setAdapter(recentSearchData);
                             /*   Intent intent = new Intent(getActivity(), HomeScreen.class);
                                startActivity(intent);
                                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                                getActivity().finish();
                            }
                        });
                        cityViewHolder.getIvRightLogo().setVisibility(View.VISIBLE);
                    }

                    break;
            }


        }

        @Override
        public int getItemCount() {

            return (null != filterList ? filterList.size() : 0);

        }

        // Do Search...
        public void filter(final String text) {

            // Searching could be complex..so we will dispatch it to a different thread...
            new Thread(new Runnable() {
                @Override
                public void run() {

                    // Clear the filter list
                    filterList.clear();

                    // If there is no search value, then add all original list items to filter list
                    if (TextUtils.isEmpty(text)) {

                        filterList.addAll(listItems);

                    } else {
                        // Iterate in the original List and add it to filter list...
                        for (SearchModel.CitiesBean.LocationSuggestionsBean item : listItems) {
                            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                                // Adding Matched items
                                filterList.add(item);
                            }
                        }
                    }

                    // Set on UI Thread
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Notify the List that the DataSet has changed...
                            notifyDataSetChanged();
                        }
                    });

                }
            }).start();

        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        TextView tvSearchCityHeader;

        public HeaderViewHolder(final View v) {
            super(v);

            tvSearchCityHeader = (TextView) v.findViewById(R.id.tvSearchCityHeader);
        }
    }

    public class CityViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final ImageView ivLocation;
        private final ImageView ivRightLogo;
        private final TextView city;

        public CityViewHolder(final View v) {
            super(v);


            id = (TextView) v.findViewById(R.id.tvId);
            city = (TextView) v.findViewById(R.id.city);
            ivLocation = (ImageView) v.findViewById(R.id.ivLocation);
            ivRightLogo = (ImageView) v.findViewById(R.id.ivRightLogo);
        }

        public TextView getCity() {
            return city;
        }

        public TextView getId() {
            return id;
        }

        public ImageView getIvRightLogo() {
            return ivRightLogo;
        }

        public ImageView getIvLocation() {
            return ivLocation;
        }
    }


    public List<SearchModel.CitiesBean.LocationSuggestionsBean> locationSuggestionsBeen = new ArrayList<>();

    public void setAdapter(List<SearchModel.CitiesBean.LocationSuggestionsBean> locationSuggestionsBeen1,String query) {

        locationSuggestionsBeen.clear();

        if(query.equals("")){
        try {

            if (locationSuggestionsBeen1.size() > 0) {
                SearchModel.CitiesBean.LocationSuggestionsBean locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
                locationSuggestionsBean.setName("Quick Search");
                locationSuggestionsBean.setHeader(true);
                locationSuggestionsBeen.add(0, locationSuggestionsBean);

                locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
                locationSuggestionsBean.setName("Current Location");
                locationSuggestionsBean.setHeader(false);
                locationSuggestionsBean.setCurrentLocation(true);
                locationSuggestionsBeen.addAll(locationSuggestionsBeen1);

            }

            String s = sp.getString("cities", "");

            JSONObject jsonObject = new JSONObject(s);


            JSONArray jsonArray = jsonObject.getJSONArray("city_list");

            SearchModel.CitiesBean.LocationSuggestionsBean locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
            locationSuggestionsBean.setName("Select City");
            locationSuggestionsBean.setHeader(true);
            locationSuggestionsBeen.add(locationSuggestionsBean);
            Log.d("cityList>", s);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                SearchModel.CitiesBean.LocationSuggestionsBean bean = new SearchModel.CitiesBean.LocationSuggestionsBean();

                bean.setName(jsonObject1.getString("name"));
                bean.setId(jsonObject1.getInt("id"));
                bean.setState_name(jsonObject1.getString("state"));
                bean.setHeader(false);
                locationSuggestionsBeen.add(bean);
            }

            if(customAdapter!=null){
                customAdapter.notifyDataSetChanged();
            }else {
                customAdapter = new CustomAdapter(locationSuggestionsBeen);
                mRecyclerView.setAdapter(customAdapter);
            }
        } catch (Exception adapter) {

        }
    }else{

            try {

                if (locationSuggestionsBeen1.size() > 0) {
                    SearchModel.CitiesBean.LocationSuggestionsBean locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
                    locationSuggestionsBean.setName("Quick Search");
                    locationSuggestionsBean.setHeader(true);
                    locationSuggestionsBeen.add(0, locationSuggestionsBean);

                    locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
                    locationSuggestionsBean.setName("Current Location");
                    locationSuggestionsBean.setHeader(false);
                    locationSuggestionsBean.setCurrentLocation(true);

                    for(int i = 0 ; i < locationSuggestionsBeen1.size();i++){
                        if(locationSuggestionsBeen1.get(i).getName().toLowerCase().contains(query.toLowerCase())){
                            locationSuggestionsBeen.add(locationSuggestionsBeen1.get(i));
                        }
                    }

                }

                String s = sp.getString("cities", "");

                JSONObject jsonObject = new JSONObject(s);


                JSONArray jsonArray = jsonObject.getJSONArray("city_list");

                SearchModel.CitiesBean.LocationSuggestionsBean locationSuggestionsBean = new SearchModel.CitiesBean.LocationSuggestionsBean();
                locationSuggestionsBean.setName("Select City");
                locationSuggestionsBean.setHeader(true);
                locationSuggestionsBeen.add(locationSuggestionsBean);
                Log.d("cityList>", s);

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    SearchModel.CitiesBean.LocationSuggestionsBean bean = new SearchModel.CitiesBean.LocationSuggestionsBean();

                    bean.setName(jsonObject1.getString("name"));
                    bean.setId(jsonObject1.getInt("id"));
                    bean.setState_id(jsonObject1.getInt("state_id"));
                    bean.setHeader(false);
                    if(jsonObject1.getString("name").toLowerCase().contains(query.toLowerCase())){
                        locationSuggestionsBeen.add(bean);
                    }

                }


                if(customAdapter != null){
                    customAdapter.notifyDataSetChanged();
                }else {
                    customAdapter = new CustomAdapter(locationSuggestionsBeen);
                    mRecyclerView.setAdapter(customAdapter);
                }
            } catch (Exception adapter) {

            }


        }
    }

}



