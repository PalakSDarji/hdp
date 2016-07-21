package com.hadippa.fragments.signup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.activities.LoginActivity;
import com.hadippa.activities.SignUp;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step4 extends Fragment implements View.OnClickListener {

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    ImageButton imageBoy,imageGirl;
    ImageView imageGirlTick,imageBoyTick;
    TextView tvImBoy,tvImGirl;
    LinearLayout linearBoy,linearGirl;

    String gender = "";
    public static SignUp_Step4 newInstance(int page, String title) {
        SignUp_Step4 fragmentFirst = new SignUp_Step4();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up_4, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        tvImBoy = (TextView)view.findViewById(R.id.tvImBoy);
        tvImGirl = (TextView)view.findViewById(R.id.tvImGirl);

        imageGirlTick = (ImageView)view.findViewById(R.id.imageGirlTick);
        imageBoyTick  = (ImageView)view.findViewById(R.id.imageBoyTick);

        imageBoy = (ImageButton)view.findViewById(R.id.imageBoy);
        imageGirl = (ImageButton)view.findViewById(R.id.imageGirl);

        linearBoy = (LinearLayout)view.findViewById(R.id.linearBoy);
        linearGirl = (LinearLayout)view.findViewById(R.id.linearGirl);

        linearBoy.setOnClickListener(this);
        linearGirl.setOnClickListener(this);

        TextView tvNext = (TextView)view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_right_out, R.anim.slide_left_in);
            }
        });


        return view;

    }


    void changeBoyGirl(){



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.linearBoy:

                gender = "boy";
                tvImBoy.setTextColor(getResources().getColor(R.color.pink_text));
                tvImGirl.setTextColor(Color.parseColor("#6f6d6e"));
                imageBoyTick.setVisibility(View.VISIBLE);
                imageGirlTick.setVisibility(View.INVISIBLE);

                imageBoy.setImageResource(R.drawable.signup4_boyicon_selected);
                imageGirl.setImageResource(R.drawable.signup4_girlicon);

                imageBoy.setBackgroundResource(R.drawable.rounded_profile_gender);
                imageGirl.setBackgroundResource(R.drawable.rounded_profile_gender_deselect);

                break;

            case R.id.linearGirl:

                gender = "girl";
                tvImGirl.setTextColor(getResources().getColor(R.color.pink_text));
                tvImBoy.setTextColor(Color.parseColor("#6f6d6e"));
                imageBoyTick.setVisibility(View.INVISIBLE);
                imageGirlTick.setVisibility(View.VISIBLE);

                imageBoy.setImageResource(R.drawable.signup4_boyicon);
                imageGirl.setImageResource(R.drawable.signup4_girlicon_selected);

                imageGirl.setBackgroundResource(R.drawable.rounded_profile_gender);
                imageBoy.setBackgroundResource(R.drawable.rounded_profile_gender_deselect);

                break;


        }
    }
}



