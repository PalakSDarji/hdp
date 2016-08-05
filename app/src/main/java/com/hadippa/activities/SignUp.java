package com.hadippa.activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.commonclasses.ViewPager.CustomViewPager;
import com.hadippa.R;
import com.hadippa.fragments.signup.SignUp_Step1;
import com.hadippa.fragments.signup.SignUp_Step2;
import com.hadippa.fragments.signup.SignUp_Step3;
import com.hadippa.fragments.signup.SignUp_Step4;

public class SignUp extends AppCompatActivity {


    public static CustomViewPager customViewPager;
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_main);

        sp = PreferenceManager.getDefaultSharedPreferences(SignUp.this);
        editor = sp.edit();


        customViewPager = (CustomViewPager) findViewById(R.id.viewPager);
        customViewPager.setPagingEnabled(false);

        customViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        if(getIntent().getExtras()!=null){
            if(getIntent().getExtras().getString("type").equals("incomplete")){
                customViewPager.setCurrentItem(1);
                editor.putString("grantType","facebook");
                editor.commit();

            }
        }else{
            editor.putString("grantType","password");
            editor.commit();
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

                    fragment = new SignUp_Step1();

                    break;

                case 1:

                    fragment = new SignUp_Step2();

                    break;

                case 2:

                    fragment = new SignUp_Step3();

                    break;

                case 3:

                    fragment = new SignUp_Step4();

                    break;


            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

  /*  @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        for (Fragment requestingFragment : SignUp_Step2.instantiate()) {
            requestingFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if(SignUp_Step2.newInstance(0,"")!=null)

    }*/
}
