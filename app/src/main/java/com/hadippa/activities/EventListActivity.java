package com.hadippa.activities;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.fragments.EventListFragment;

import java.util.ArrayList;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private PagerAdapter viewPagerAdapter;
    private String[] tabs;
    private TabLayout tabLayout;
    private int activityKey;
    private TextView tvHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        if(activityKey == AppConstants.ACTIVITY_EVENT_PARTY) {
            tvHeader.setText(getString(R.string.party));
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_THEATER) {
            tvHeader.setText(getString(R.string.theater_play));
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_EVENT) {
            tvHeader.setText(getString(R.string.event));
        }
        else if(activityKey == AppConstants.ACTIVITY_EVENT_FESTIVAL) {
            tvHeader.setText(getString(R.string.festival));
        }

        //set tab view
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);

        tabs = new String[]{getString(R.string.lbl_today), getString(R.string.lbl_tomorrow),getString(R.string.lbl_weekend),getString(R.string.lbl_custom)};

        //add tabs to tabLayout
        tabLayout.addTab(tabLayout.newTab().setText(tabs[0]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[1]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[2]));
        tabLayout.addTab(tabLayout.newTab().setText(tabs[3]));

        //load fragment do display in the tab
        List<Fragment> fragments=new ArrayList<>();
        fragments.add(new EventListFragment());
        fragments.add(new EventListFragment());
        fragments.add(new EventListFragment());
        fragments.add(new EventListFragment());

        //set view pager adapter and view pager data.
        viewPagerAdapter=new PagerAdapter(this,getSupportFragmentManager(),fragments,tabs);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                changeTabsFont();
            }
        });
    }

    private void changeTabsFont() {

        ViewGroup vg = (ViewGroup) tabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildsCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildsCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTextSize(12);
                    ((TextView) tabViewChild).setTypeface(Typeface.createFromAsset(getAssets(),getString(R.string.proxima_bold)), Typeface.NORMAL);
                }
            }
        }
    }

    class PagerAdapter extends FragmentStatePagerAdapter {

        private SparseArray<Fragment> registeredFragments = new SparseArray<>();
        private Context mContext;
        String[] tabs;
        private List<Fragment> mFragmentList;


        //This is a constructor
        public PagerAdapter(Context context, FragmentManager fm, List<Fragment> fragmentList, String[] tabs) {
            super(fm);
            this.mContext=context;
            mFragmentList=fragmentList;
            this.tabs=tabs;

        }

        //get tab item
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        //get page title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }

        //get all tab count
        @Override
        public int getCount() {
            return tabs.length;
        }

        // Register the fragment when the item is instantiated
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        // Unregister when the item is inactive
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        // Returns the fragment for the position (if instantiated)
        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
