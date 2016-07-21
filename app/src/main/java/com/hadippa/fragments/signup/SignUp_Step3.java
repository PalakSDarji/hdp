package com.hadippa.fragments.signup;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.activities.SignUp;

import java.util.Calendar;


/**
 * Created by alm-android on 01-12-2015.
 */
public class SignUp_Step3 extends Fragment  {

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    public static SignUp_Step3 newInstance(int page, String title) {
        SignUp_Step3 fragmentFirst = new SignUp_Step3();
        Log.d("FRAGMENT_LOG", "Crewated ");
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_sign_up_3, null, false);

        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = sp.edit();

        TextView tvNext = (TextView)view.findViewById(R.id.tvNext);
        tvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp.customViewPager.setCurrentItem(SignUp.customViewPager.getCurrentItem() + 1);
            }
        });

        final EditText date = (EditText)view.findViewById(R.id.date);

        date.setFocusable(false);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate("Birthdate",date);
            }
        });

        date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    selectDate("Birthdate",date);
                }
            }
        });

        return view;

    }


    void selectDate(String title, final EditText myEditTextBook) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Log.d("DATEPICKER", "Date found");

                String day = String.valueOf(dayOfMonth);
                String month = String.valueOf(monthOfYear + 1);
                if (day.length() < 2) {
                    day = '0' + day;
                }
                if (month.length() < 2) {
                    month = '0' + month;
                }

                myEditTextBook.setText(String.valueOf(year) + "-" + month + "-" + day);
                // dob.setText(checkDigit(dayOfMonth)+"/"+checkDigit(monthOfYear+1)+"/"+year);
            }
        }, year, month, day);

        datePickerDialog.setTitle(title);
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - 1000);
        datePickerDialog.show();


    }


}



