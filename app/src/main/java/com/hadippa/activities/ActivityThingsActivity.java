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

import com.hadippa.R;

public class ActivityThingsActivity extends AppCompatActivity {

    RecyclerView myRecycler;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_things);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        myRecycler = (RecyclerView)findViewById(R.id.myRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(mLayoutManager);

        myRecycler.setAdapter(new CustomAdapter());


    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_activity_things, viewGroup, false);

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

        private final RecyclerView recyclerView;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityThingsActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);

            recyclerView.setAdapter(new Approved());
        }
    }

    class Approved extends RecyclerView.Adapter<ViewHolderApproved> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolderApproved onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.simple_grid_item_myplan, viewGroup, false);

            return new ViewHolderApproved(v);
        }

        @Override
        public void onBindViewHolder(ViewHolderApproved viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

        }

        @Override
        public int getItemCount() {

            return 10;
        }
    }


    public class ViewHolderApproved extends RecyclerView.ViewHolder {


        //   private final LinearLayout linearDate;

        public ViewHolderApproved(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                 /*   ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else{
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }

*/
                }
            });

            //   linearDate = (LinearLayout)v.findViewById(R.id.linearDate);

           /*  linearDate.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {

                    Drawable drawable = linearDate.getBackground();
                    if (drawable.getConstantState() == (getResources().getDrawable(R.drawable.rounded_date)).getConstantState()) {
                        v.setBackgroundResource(R.drawable.rounded_date_selected);
                    } else {
                        v.setBackgroundResource(R.drawable.rounded_date);
                    }
                }
            });*/
        }


        //  public LinearLayout getLinearDate() {
        //      return linearDate;
        // }


    }

}
