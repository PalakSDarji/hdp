package com.hadippa.activities;

import android.app.Activity;
import android.content.Intent;
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
import com.klinker.android.peekview.PeekViewActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
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

    //Fragment na object banaya.. and trying to using them
    //kayu object?

    public static SearchPeople searchPeople = new SearchPeople();
    public static SearchCity searchCity = new SearchCity();

    public static AutoCompleteTextView edtSearch;

    ArrayList<String> previousSearchList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        pager = (ViewPager) findViewById(R.id.pager);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sp.edit();
        edtSearch = (AutoCompleteTextView) findViewById(R.id.edtSearch);

        Set<String> tasksSet = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getStringSet("searchOptions", new HashSet<String>());
        previousSearchList = new ArrayList<>(tasksSet);

        Log.d("previousSearchList", previousSearchList.toString());
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (SearchActivity.this, R.layout.list_item, previousSearchList);

        edtSearch.setThreshold(0);//will start working from first character
        edtSearch.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView

        edtSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);

                previousSearchList.remove(position);
                previousSearchList.add(0,selectedItem);
                adapter.notifyDataSetChanged();
                performSearch(selectedItem);

                edtSearch.dismissDropDown();

            }
        });
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

     //   setPreviousData();

        //This prevents fragments from being destroyed, default limit is 1, so next and pre frag will be alive if pos -1,
        // if pos = 0 then 1 will be alive, if 2 then 1 will be alive
        //by setting it 2, we make sure that view pager should atleast keep 2 fragments in a history, other than current
        //  pager.setOffscreenPageLimit(2);


        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);


        // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        // Setting Custom Color for the Scroll bar indicator of the Tab View
      /*  int col[] = {getResources().getColor(R.color.follow_color),getResources().getColor(R.color.following_color)};
        tabs.setSelectedIndicatorColors(col);*/
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

                        previousSearchList.add(0,edtSearch.getText().toString().trim());
                        adapter.notifyDataSetChanged();
                        Set<String> tasksSet = new HashSet<String>(previousSearchList);
                        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                                .edit()
                                .putStringSet("searchOptions", tasksSet)
                                .commit();

                       /* if (pager.getCurrentItem() == 2) {
                            searchTag.fetchByTags(String.valueOf(s));
                            searchPeople.fetchPeople(String.valueOf(s));
                        }else{*/
                        performSearch(String.valueOf(s));
                        // searchPeople.fetchPeople(String.valueOf(s));
                        //  }
                    }
                    return true;
                }
                return false;
            }
        });

        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.showDropDown();
            }
        });

        edtSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtSearch.showDropDown();
                }
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

    public class GetCity extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            searchCity.progressBar.setVisibility(View.VISIBLE);
            searchPeople.progressBar.setVisibility(View.VISIBLE);
            // AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            //   AppConstants.dismissDialog();
            searchCity.progressBar.setVisibility(View.GONE);
            searchPeople.progressBar.setVisibility(View.GONE);
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
                SearchModel searchModel;
                Type listType = new TypeToken<SearchModel>() {
                }.getType();
                GsonBuilder gsonBuilder = new GsonBuilder();

                Gson gson = gsonBuilder.create();

                searchModel = (gson.fromJson(jsonObject.toString(), listType));

                if (searchModel.isSuccess()) {

                    Log.d("async", "success exc  >> Locaion" + searchModel.getCities().getLocation_suggestions().size());
                    Log.d("async", "success exc  >> Locaion" + searchModel.getUsers());
                    searchCity.setAdapter(searchModel.getCities().getLocation_suggestions());
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

}
