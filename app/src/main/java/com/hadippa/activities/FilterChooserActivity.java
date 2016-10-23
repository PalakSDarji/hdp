package com.hadippa.activities;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hadippa.R;
import com.hadippa.model.FilterModel;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;

public class FilterChooserActivity extends AppCompatActivity {

    CustomAdapter customAdapter = new CustomAdapter();
    RecyclerView dateList;
    LayoutInflater inflater;

    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_chooser);


        inflater = getLayoutInflater();
        dateList = (RecyclerView) findViewById(R.id.dateList);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dateList.setLayoutManager(mLayoutManager);

        dateList.setAdapter(customAdapter);
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.choose_date_list_item, viewGroup, false);

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
     /*  private final TextView id;
        private final ImageView foodImage;
        private final TextView tvDonarName;
        private final TextView tvDonarPh, tvAddress, tvFoodfor, tvStatus;
        private final View typeView;*/

        private final LinearLayout linearDate;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });

            linearDate = (LinearLayout)v.findViewById(R.id.linearDate);



            linearDate.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    Drawable drawable = linearDate.getBackground();
                    if(drawable.getConstantState()==(getResources().getDrawable(R.drawable.rounded_date)).getConstantState()){
                        v.setBackgroundResource(R.drawable.rounded_date_selected);
                    }else{
                        v.setBackgroundResource(R.drawable.rounded_date);
                    }
                }
            });
        }


        public LinearLayout getLinearDate() {
            return linearDate;
        }


    }


}
