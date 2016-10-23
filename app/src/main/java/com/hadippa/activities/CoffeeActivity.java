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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hadippa.AppConstants;
import com.hadippa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoffeeActivity extends AppCompatActivity {

    private RecyclerView listShops;
    private ImageView imageBack;
    private TextView tvHeader1;
    private int activityKey;
    @BindView(R.id.ivActivityIcon) ImageView ivActivityIcon;
    CustomAdapter customAdapter = new CustomAdapter();
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        ButterKnife.bind(this);
        activityKey = getIntent().getIntExtra(AppConstants.ACTIVITY_KEY,0);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        ivActivityIcon.setImageResource(R.drawable.zomato_spoon);
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
            edtSearch.setHint(getString(R.string.select_cafe));
        }
        else if(activityKey == AppConstants.ACTIVITY_NIGHTCLUB){

            tvHeader1.setText(getString(R.string.night_club));
            edtSearch.setHint(getString(R.string.select_club));
        }
        else if(activityKey == AppConstants.ACTIVITY_LOUNGE){

            tvHeader1.setText(getString(R.string.lounge));
            edtSearch.setHint(getString(R.string.select_lounge));
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

        private RelativeLayout rlContainer;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    /*ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }*/

                    Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_SHORT).show();
                }
            });

            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
        }

        /*public LinearLayout getLinearDate() {
            return linearDate;
        }*/


    }
}
