package com.hadippa.activities;

import android.annotation.TargetApi;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.hadippa.R;

public class CategoryTab extends AppCompatActivity implements View.OnClickListener {

    RecyclerView MoviesList,cinemaList,listLongue,listCoffee,listNightClub;

    ImageView imageBack;
    CustomAdapter customAdapter = new CustomAdapter();

    TextView tvTabMovie,tvTabNightclub,tvTabLounge,tvTabCoffee,tabMovie,tabCinema;
    LinearLayout linearMovie;
    EditText edtSearch;
    ImageView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_category_tab);

        edtSearch = (EditText)findViewById(R.id.edtSearch);
        tvTabMovie = (TextView)findViewById(R.id.tvTabMovie);
        tvTabMovie.setOnClickListener(this);
        tvTabNightclub = (TextView)findViewById(R.id.tvTabNightclub);
        tvTabNightclub.setOnClickListener(this);
        tvTabLounge = (TextView)findViewById(R.id.tvTabLounge);
        tvTabLounge.setOnClickListener(this);
        tvTabCoffee = (TextView)findViewById(R.id.tvTabCoffee);
        tvTabCoffee.setOnClickListener(this);
        tabMovie = (TextView)findViewById(R.id.tabMovie);
        tabCinema = (TextView)findViewById(R.id.tabCinema);

        icon = (ImageView)findViewById(R.id.icon);
        linearMovie = (LinearLayout)findViewById(R.id.linearMovie);
        tabMovie.setOnClickListener(this);
        tabCinema.setOnClickListener(this);

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });


        MoviesList = (RecyclerView) findViewById(R.id.listMovie);
        cinemaList = (RecyclerView) findViewById(R.id.listCinema);
        listCoffee = (RecyclerView) findViewById(R.id.listCoffee);
        listLongue = (RecyclerView) findViewById(R.id.listLongue);
        listNightClub = (RecyclerView) findViewById(R.id.listNightClub);


        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(CategoryTab.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(CategoryTab.this);
        mLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(CategoryTab.this);
        mLayoutManager2.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager mLayoutManager3 = new LinearLayoutManager(CategoryTab.this);
        mLayoutManager3.setOrientation(LinearLayoutManager.VERTICAL);
        final LinearLayoutManager mLayoutManager4 = new LinearLayoutManager(CategoryTab.this);
        mLayoutManager4.setOrientation(LinearLayoutManager.VERTICAL);

        MoviesList.setLayoutManager(mLayoutManager);
        cinemaList.setLayoutManager(mLayoutManager1);
        listCoffee.setLayoutManager(mLayoutManager2);
        listNightClub.setLayoutManager(mLayoutManager3);
        listLongue.setLayoutManager(mLayoutManager4);
        MoviesList.setAdapter(customAdapter);
        cinemaList.setAdapter(new CustomAdapterCinema());
        listCoffee.setAdapter(new CustomAdapterNightLongueCoffee());
        listNightClub.setAdapter(new CustomAdapterNightLongueCoffee());
        listLongue.setAdapter(new CustomAdapterNightLongueCoffee());

    }


    void changeTabs(TextView selected,TextView unselected){

        selected.setTextColor(getResources().getColor(R.color.white));
        selected.setBackgroundColor(getResources().getColor(R.color.tabs_movie_cinema_text));

        unselected.setTextColor(getResources().getColor(R.color.tabs_movie_cinema_text));
        unselected.setBackgroundColor(getResources().getColor(R.color.white));

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tabMovie:


                changeTabs(tabMovie,tabCinema);
                MoviesList.setVisibility(View.VISIBLE);
                cinemaList.setVisibility(View.GONE);
                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_movies));
                break;

            case R.id.tabCinema:

                icon.setImageResource(R.drawable.ic_zomato);
                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_theatre));
                changeTabs(tabCinema,tabMovie);
                MoviesList.setVisibility(View.GONE);
                cinemaList.setVisibility(View.VISIBLE);

                break;

            case R.id.tvTabMovie:

                icon.setImageResource(R.drawable.ic_bookmyshow);
                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_movies));

                tvTabMovie.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment_selected));
                tvTabMovie.setTextColor(getResources().getColor(R.color.white));

                tvTabNightclub.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabNightclub.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabLounge.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabLounge.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_coffee));
                tvTabCoffee.setTextColor(getResources().getColor(R.color.filter_text));

                linearMovie.setVisibility(View.VISIBLE);
                listLongue.setVisibility(View.GONE);
                listCoffee.setVisibility(View.GONE);
                listNightClub.setVisibility(View.GONE);

                break;

            case R.id.tvTabNightclub:

                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_club));
                icon.setImageResource(R.drawable.ic_zomato);

                tvTabMovie.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabMovie.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabNightclub.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment_selected));
                tvTabNightclub.setTextColor(getResources().getColor(R.color.white));

                tvTabLounge.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabLounge.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_coffee));
                tvTabCoffee.setTextColor(getResources().getColor(R.color.filter_text));

                linearMovie.setVisibility(View.GONE);
                listLongue.setVisibility(View.GONE);
                listCoffee.setVisibility(View.GONE);
                listNightClub.setVisibility(View.VISIBLE);

                break;

            case R.id.tvTabLounge:

                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_longue));
                icon.setImageResource(R.drawable.ic_zomato);

                tvTabMovie.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabMovie.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabNightclub.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabNightclub.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabLounge.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment_selected));
                tvTabLounge.setTextColor(getResources().getColor(R.color.white));

                tvTabCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_coffee));
                tvTabCoffee.setTextColor(getResources().getColor(R.color.filter_text));

                linearMovie.setVisibility(View.GONE);
                listLongue.setVisibility(View.VISIBLE);
                listCoffee.setVisibility(View.GONE);
                listNightClub.setVisibility(View.GONE);

                break;

            case R.id.tvTabCoffee:

                edtSearch.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.mysearch),null,null,null);
                edtSearch.setHint(getResources().getString(R.string.search_coffee));
                icon.setImageResource(R.drawable.ic_zomato);

                tvTabMovie.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabMovie.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabNightclub.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabNightclub.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabLounge.setBackground(getResources().getDrawable(R.drawable.rounded_entertainment));
                tvTabLounge.setTextColor(getResources().getColor(R.color.filter_text));

                tvTabCoffee.setBackground(getResources().getDrawable(R.drawable.rounded_coffee_selected));
                tvTabCoffee.setTextColor(getResources().getColor(R.color.white));

                linearMovie.setVisibility(View.GONE);
                listLongue.setVisibility(View.GONE);
                listCoffee.setVisibility(View.VISIBLE);
                listNightClub.setVisibility(View.GONE);

                break;
        }

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
                    }else{
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }


                }
            });

           linearDate = (LinearLayout)v.findViewById(R.id.linearDate);

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


        public LinearLayout getLinearDate() {
            return linearDate;
        }


    }

    class CustomAdapterCinema extends RecyclerView.Adapter<ViewHolderCinema> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolderCinema onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.cinema_list_item, viewGroup, false);

            return new ViewHolderCinema(v);
        }

        @Override
        public void onBindViewHolder(ViewHolderCinema viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

        }

        @Override
        public int getItemCount() {

            return 10;
        }
    }


    public class ViewHolderCinema extends RecyclerView.ViewHolder {


        private final ImageView linearDate;

        public ViewHolderCinema(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onClick(View v) {


                }
            });

            linearDate = (ImageView)v.findViewById(R.id.frame);

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


        public ImageView getLinearDate() {
            return linearDate;
        }


    }


    class CustomAdapterNightLongueCoffee extends RecyclerView.Adapter<ViewHolderNightLongueCoffee> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolderNightLongueCoffee onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.list_coffee_longue_night_club, viewGroup, false);

            return new ViewHolderNightLongueCoffee(v);
        }

        @Override
        public void onBindViewHolder(ViewHolderNightLongueCoffee viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

        }

        @Override
        public int getItemCount() {

            return 10;
        }
    }


    public class ViewHolderNightLongueCoffee extends RecyclerView.ViewHolder {


        private final LinearLayout linearDate;

        public ViewHolderNightLongueCoffee(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ImageView relativeLayout = (ImageView) v.findViewById(R.id.frame);
                    if(relativeLayout.getDrawable().getConstantState()==(getResources().getDrawable(R.drawable.rounded_movie_deselect).getConstantState())){
                        relativeLayout.setImageResource(R.drawable.rounded_movie_select);
                    }else{
                        relativeLayout.setImageResource(R.drawable.rounded_movie_deselect);
                    }


                }
            });

            linearDate = (LinearLayout)v.findViewById(R.id.linearDate);

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


        public LinearLayout getLinearDate() {
            return linearDate;
        }
    }

    }
