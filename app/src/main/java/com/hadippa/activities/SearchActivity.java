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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.SlidingTab.SlidingTabLayout;
import com.SlidingTab.SlidingTabStrip;
import com.hadippa.R;
import com.hadippa.fragments.following.Followers;
import com.hadippa.fragments.following.Following;
import com.hadippa.fragments.search.SearchCity;
import com.hadippa.fragments.search.SearchPeople;
import com.hadippa.fragments.search.SearchTag;
import com.klinker.android.peekview.PeekViewActivity;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SearchActivity extends PeekViewActivity implements View.OnClickListener {

    String [] tabTitle = {"CITY","PEOPLE","TAG"};
    SlidingTabLayout tabs;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    ImageView imageBack;

    //Fragment na object banaya.. and trying to using them
    SearchPeople searchPeople = new SearchPeople();
    SearchTag searchTag = new SearchTag();
    SearchCity searchCity = new SearchCity();
    public static EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
        pager = (ViewPager) findViewById(R.id.pager);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sp.edit();

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);

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


        edtSearch = (EditText)findViewById(R.id.edtSearch);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                //HERE.. but
                if(s.length()>=2){

                    Log.v("searchCity","searchCity"+searchCity);

                    if(pager.getCurrentItem()==2){
                        searchTag.fetchByTags(String.valueOf(s));
                        searchPeople.fetchPeople(String.valueOf(s));
                    }else{
                        searchCity.fetchCities(String.valueOf(s));
                        searchPeople.fetchPeople(String.valueOf(s));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

        }
    }


    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }
        public Fragment getItem(int num) {

            Fragment fragment = null;
            switch (num){

                case 0:

                    fragment = searchCity;

                    break;

                case 1 :

                    fragment = searchPeople;

                    break;

                case 2 :

                    fragment = searchTag;

                    break;


            }
            return fragment;
        }
        @Override
        public int getCount() {
            return 3;
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
}
