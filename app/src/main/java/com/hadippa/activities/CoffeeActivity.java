package com.hadippa.activities;

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

public class CoffeeActivity extends AppCompatActivity {

    private RecyclerView listShops;
    private ImageView imageBack;
    CustomAdapter customAdapter = new CustomAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        listShops = (RecyclerView) findViewById(R.id.listShops);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listShops.setLayoutManager(mLayoutManager);
        listShops.setAdapter(customAdapter);
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_movies, viewGroup, false);

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

        private final LinearLayout linearDate;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else {
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }
                }
            });

            linearDate = (LinearLayout)v.findViewById(R.id.linearDate);
        }

        public LinearLayout getLinearDate() {
            return linearDate;
        }


    }
}
