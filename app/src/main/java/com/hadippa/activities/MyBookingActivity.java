package com.hadippa.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.SlidingTab.SlidingTabLayout;
import com.hadippa.R;
import com.hadippa.fragments.BookingTicketsFragment;

public class MyBookingActivity extends AppCompatActivity {

    String[] tabTitle = {"Recent Tickets", "Past Tickets"};
    SlidingTabLayout tabs;

    public static ViewPager pager;
    private ViewPagerAdapter pagerAdapter;
    ImageView imageBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);

        pager = (ViewPager) findViewById(R.id.pager);
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



    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {
        public ViewPagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {

            Fragment fragment = null;
            switch (num) {

                case 0:
                    fragment = new BookingTicketsFragment();
                    break;

                case 1:
                    fragment = new BookingTicketsFragment();
                    break;
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
}
