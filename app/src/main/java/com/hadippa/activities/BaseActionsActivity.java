package com.hadippa.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.hadippa.CustomTextView;
import com.hadippa.R;

import butterknife.ButterKnife;

/**
 * Created by Palak on 26-11-2016.
 */

public class BaseActionsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        ButterKnife.bind(this);
    }

    public void setCurrentActionView(Activity activity, int clickedTabId) {

        LinearLayout linearLayout = (LinearLayout) activity.findViewById(R.id.llActions);

        for(int i = 0;i<linearLayout.getChildCount();i++){

            final CustomTextView customTextView = (CustomTextView) linearLayout.getChildAt(i);
            if(customTextView.getId() == clickedTabId){
                customTextView.setSelected(true);
                final HorizontalScrollView horizontalScrollView = (HorizontalScrollView) activity.findViewById(R.id.hsvActivities);
                horizontalScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        horizontalScrollView.smoothScrollBy((int)customTextView.getX(), 0);
                    }
                },100);
            }
            else{
                customTextView.setSelected(false);
            }


        }







    }

}
