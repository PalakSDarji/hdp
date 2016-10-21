package com.hadippa.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hadippa.R;

import java.util.ArrayList;

public class EntertainmentActivity extends AppCompatActivity {

    private RecyclerView listPlay;
    private TextView tvHeader;
    CustomAdapter customAdapter = new CustomAdapter();
    private ArrayList<String> alCities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entertainment);

        alCities = new ArrayList<>();
        alCities.add("Mumbai");
        alCities.add("Vadodara");
        alCities.add("Ahmedabad");
        alCities.add("Surat");
        alCities.add("Banglore");
        alCities.add("Jaipur");
        alCities.add("Chennai");
        alCities.add("Pune");

        tvHeader = (TextView) findViewById(R.id.tvHeader);

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        listPlay = (RecyclerView) findViewById(R.id.listPlay);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listPlay.setLayoutManager(mLayoutManager);
        listPlay.setAdapter(customAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        //MyObject obj = (MyObject) alCities.getItemAtPosition(info.position);

        for(int i=0;i<alCities.size();i++){
            menu.add(alCities.get(i));
        }

    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_movies, viewGroup, false);

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

        private RelativeLayout rlData;

        public ViewHolder(final View v) {
            super(v);
            rlData = (RelativeLayout)v.findViewById(R.id.rlData);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(EntertainmentActivity.this, PlayDetailActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    /*ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }*/
                }
            });
        }

        public RelativeLayout getRlData() {
            return rlData;
        }


    }
}
