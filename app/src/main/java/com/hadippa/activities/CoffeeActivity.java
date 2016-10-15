package com.hadippa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hadippa.AppConstants;
import com.hadippa.R;

public class CoffeeActivity extends AppCompatActivity {

    private RecyclerView listShops;
    private ImageView imageBack;
    private TextView tvHeader1;
    private int activityKey;
    CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        tvHeader1 = (TextView) findViewById(R.id.tvHeader1);
        listShops = (RecyclerView) findViewById(R.id.listShops);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShops.setLayoutManager(mLayoutManager);
        listShops.setAdapter(customAdapter);

        findViewById(R.id.tvNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CoffeeActivity.this, CreateActivityActvity.class);
                intent.putExtra(AppConstants.ACTIVITY_KEY,AppConstants.ACTIVITY_FROM_COFFEE);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        if(activityKey == AppConstants.ACTIVITY_COFFEE){

            tvHeader1.setText(getString(R.string.coffee_cafe));
        }
        else if(activityKey == AppConstants.ACTIVITY_NIGHTCLUB){

            tvHeader1.setText(getString(R.string.night_club));
        }
        else if(activityKey == AppConstants.ACTIVITY_LOUNGE){

            tvHeader1.setText(getString(R.string.lounge));
        }
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_coffee_longue_night_club, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

        }

        @Override
        public int getItemCount() {

            return 10;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //private final LinearLayout linearDate;

        public ViewHolder(final View v) {
            super(v);

            /*v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }
                }
            });*/

            //linearDate = (LinearLayout)v.findViewById(R.id.linearDate);
        }

        /*public LinearLayout getLinearDate() {
            return linearDate;
        }*/


    }
}
