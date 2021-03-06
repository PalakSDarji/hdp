package com.hadippa.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SlidingTab.SlidingTabLayout;
import com.SlidingTab.SlidingTabStrip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.following.Followers;
import com.hadippa.fragments.following.Following;
import com.hadippa.fragments.search.SearchCity;
import com.hadippa.fragments.search.SearchPeople;
import com.hadippa.fragments.search.SearchTag;
import com.hadippa.model.CityModel;
import com.hadippa.model.PeopleModel;
import com.hadippa.model.SearchModel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.klinker.android.peekview.PeekViewActivity;
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

import static com.hadippa.fragments.following.Followers.relMain;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SearchActivity extends PeekViewActivity implements View.OnClickListener {

    String[] tabTitle = {"CITY", "PEOPLE"};
    SlidingTabLayout tabs;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    ImageView imageBack;

    public static SearchPeople searchPeople = new SearchPeople();
    public static SearchCity searchCity = new SearchCity();

    public static AutoCompleteTextView edtSearch;

    ArrayList<String> previousSearchList = new ArrayList<String>();
    ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean> recentSearchData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        pager = (ViewPager) findViewById(R.id.pager);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sp.edit();
        edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setOffscreenPageLimit(2);

        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.follow_color);
            }

        });
        tabs.setViewPager(pager);

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //performSearch();
                    String s = v.getText().toString();
                    if (v.getText().length() >= 2) {

                        Log.v("searchCity", "searchCity" + searchCity);

                        performSearch(String.valueOf(s));
                    }
                    return true;
                }
                return false;
            }
        });



        Gson gson = new Gson();

        if (!sp.getString("recentSearch", "").equals("")) {
            recentSearchData = gson.fromJson(sp.getString("recentSearch", ""),
                    new TypeToken<List<SearchModel.CitiesBean.LocationSuggestionsBean>>() {
                    }.getType());
        }
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(recentSearchData != null){
                    Log.d("cityList>","Old Found");
                    searchCity.setAdapter(recentSearchData,edtSearch.getText().toString().trim());
                }else{
                    Log.d("cityList>","Old not Found");
                    recentSearchData = new ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean>();
                    searchCity.setAdapter(recentSearchData,edtSearch.getText().toString().trim());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {

            Fragment fragment = null;
            switch (num) {

                case 0:

                    fragment = searchCity;

                    break;

                case 1:

                    fragment = searchPeople;

                    break;

              /*  case 2 :

                    fragment = searchTag;

                    break;

*/
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitle[position];
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    public void performSearch(String query) {

       /* searchPeople.peopleModelArrayList.clear();
        searchCity.cityModelArrayList.clear();*/
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("query", query);


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.SEARCH, requestParams,
                new GetCity());

    }

    public KProgressHUD hud;

    public void showProgressDialog(Context context, String message) {
        // PROGRESS_DIALOG.show();

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(context.getResources().getColor(R.color.back_progress))

                .setLabel(message)
                .setDimAmount(0.5f)
                .setCancellable(true)
                .setAnimationSpeed(2);

        if(hud!=null){
            hud.show();
        }
    }

    public void dismissDialog() {

        if (hud != null) hud.dismiss();


    }
    public class GetCity extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

          //  searchCity.progressBar.setVisibility(View.VISIBLE);

            if(searchPeople.swipeRefreshLayout.isRefreshing()){

            }else {
                searchPeople.progressBar.setVisibility(View.VISIBLE);
            }// showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            //   dismissDialog();
            if(searchPeople.swipeRefreshLayout.isRefreshing()){
                searchPeople.swipeRefreshLayout.setRefreshing(false);
            }
            if(searchCity.swipeRefreshLayout.isRefreshing()){
                searchCity.swipeRefreshLayout.setRefreshing(false);
            }
            searchCity.progressBar.setVisibility(View.GONE);
            searchPeople.progressBar.setVisibility(View.GONE);
        }


        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("async_step_2", "success" + response);
                SearchModel searchModel;
                Type listType = new TypeToken<SearchModel>() {
                }.getType();
                GsonBuilder gsonBuilder = new GsonBuilder();

                Gson gson = gsonBuilder.create();

                searchModel = (gson.fromJson(jsonObject.toString(), listType));

                if (searchModel.isSuccess()) {

                    Log.d("async", "success exc  >> Locaion" + searchModel.getUsers());
                    if(recentSearchData != null){
                        Log.d("cityList>","Old Found");
                        searchCity.setAdapter(recentSearchData,edtSearch.getText().toString().trim());
                    }else{
                        Log.d("cityList>","Old not Found");
                        recentSearchData = new ArrayList<SearchModel.CitiesBean.LocationSuggestionsBean>();
                        searchCity.setAdapter(recentSearchData,edtSearch.getText().toString().trim());
                    }

                    searchPeople.setAdapter(searchModel.getUsers());

                    editor.putString("previous_search", response);
                    editor.commit();


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

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.linearMain)),intent.getExtras().getString("messageData"));
        }
    };

    BroadcastReceiver swipeData = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            performSearch(edtSearch.getText().toString().trim());

         //   AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.linearMain)),intent.getExtras().getString("messageData"));
        }
    };


    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));

        registerReceiver(swipeData,new IntentFilter("SWIPE_REFRESH"));
    }


    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(swipeData);
    }
}
