package com.hadippa.fragments.signup;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.activities.SignUp;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step2 extends Fragment  {

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public static SignUp_Step2 newInstance(int page, String title) {
        SignUp_Step2 fragmentFirst = new SignUp_Step2();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up_2, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        TextView tvNext = (TextView)view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem()+1);
            }
        });

        return view;

    }



}



