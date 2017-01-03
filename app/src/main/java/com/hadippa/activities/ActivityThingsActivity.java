package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.hadippa.model.UserProfile;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ActivityThingsActivity extends AppCompatActivity {

    //Demo Github
    RecyclerView myRecycler;
    ImageView imageBack;
    List<UserProfile.ActivityBeanX> activityBeanX;

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

        Gson gson = new Gson();
        UserProfile userProfile = gson.fromJson(getIntent().getExtras().getString("data"),UserProfile.class);

        if(userProfile.getActivity()!=null) {
            activityBeanX = userProfile.getActivity();

            myRecycler = (RecyclerView) findViewById(R.id.myRecycler);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            myRecycler.setLayoutManager(mLayoutManager);

            myRecycler.setAdapter(new CustomAdapter());
        }}

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


             AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }



    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
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

            final UserProfile.ActivityBeanX myPlansBean = activityBeanX.get(position);

            if(myPlansBean.getPeople_approaching_count().size() == 0){
                viewHolder.recyclerView.setVisibility(View.GONE);
            }else {
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
                viewHolder.recyclerView.setAdapter(new Approved(myPlansBean.getPeople_going()));
            }


            Log.v(TAG,"LLGoing  " + myPlansBean.getPeople_going_count());
            if(myPlansBean.getPeople_going_count() != null && myPlansBean.getPeople_going_count().size() > 0) {

                viewHolder.llGoing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO sahil. look at this
                        Log.v(TAG,"LLGoing  " + myPlansBean.isOpened());

                        myPlansBean.setOpened(!myPlansBean.isOpened());
                        notifyDataSetChanged();

                    }
                });
            }
            else{
                viewHolder.llGoing.setOnClickListener(null);
            }




            if(myPlansBean.isOpened()){
                Log.v(TAG, "myPlansBean.isOpened(): 1"+ myPlansBean.isOpened());
                viewHolder.vSep.setVisibility(View.VISIBLE);
                viewHolder.tvApprovedPeople.setVisibility(View.VISIBLE);
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
            }
            else{
                Log.v(TAG, "myPlansBean.isOpened() 2: "+ myPlansBean.isOpened());
                viewHolder.vSep.setVisibility(View.GONE);
                viewHolder.tvApprovedPeople.setVisibility(View.GONE);
                viewHolder.recyclerView.setVisibility(View.GONE);
            }

            viewHolder.activityname.setText(myPlansBean.getActivity_details().getActivity_name());
            viewHolder.tvAddress.setText(myPlansBean.getActivity_location());
            viewHolder.tvActivityDate.setText(myPlansBean.getActivity_date());
            viewHolder.tvActivityTime.setText(convertDate(myPlansBean.getActivity_time()));
            viewHolder.tvGoing.setText(myPlansBean.getPeople_going().size()+"");
          /*  if(myPlansBean.getPeople_going_count() != null && myPlansBean.getPeople_going_count().size() > 0){
                viewHolder.tvCount.setText(myPlansBean.getPeople_going_count().size()+"");

                String text = "";
                if(myPlansBean.getPeople_going().size()>2) {


                    text = "You are going with " + myPlansBean.getPeople_going().get(0).getUser().getFirst_name()
                            + " " + myPlansBean.getPeople_going().get(0).getUser().getLast_name() + " and "+
                            (myPlansBean.getPeople_going().size()-2) +" people";
                }else{

                    text = "You are going with " + myPlansBean.getPeople_going().get(0).getUser().getFirst_name()
                            + " " + myPlansBean.getPeople_going().get(0).getUser().getLast_name();

                }

                viewHolder.goingWith.setText(text);
                viewHolder.goingWith.setVisibility(View.VISIBLE);


            }
            else{
                viewHolder.goingWith.setVisibility(View.GONE);
                viewHolder.tvCount.setText("0");
            }*/




            if(myPlansBean.getUser().getProfile_photo().isEmpty() ||
                    myPlansBean.getUser().getProfile_photo().equals("") ){

                viewHolder.profileImage.setImageResource(R.drawable.place_holder);
            }else{

                Glide.with(ActivityThingsActivity.this)
                        .load(myPlansBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.profileImage);
            }

        }

        @Override
        public int getItemCount() {

            return activityBeanX.size();
        }
    }

    String convertDate(String dateInputString) {

        String stringDate = null;
        try {
            // obtain date and time from initial string
            Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).parse(dateInputString);
            // set date string
            stringDate = new SimpleDateFormat("dd MMM", Locale.US).format(date).toUpperCase(Locale.ROOT);
            // set time string

        } catch (ParseException e) {
            // wrong input
        }

        return stringDate;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {


        private View vSep;
        private  RecyclerView recyclerView;
        private  LinearLayout llGoing;
        CustomTextView activityname,tvAddress,tvActivityDate,tvActivityTime,tvGoing,tvCount,tvApprovedPeople,goingWith;
        RoundedImageView profileImage;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            llGoing = (LinearLayout) v.findViewById(R.id.llGoing);

            vSep = (View) v.findViewById(R.id.vSep);
            activityname = (CustomTextView)v.findViewById(R.id.activityname);
            tvAddress = (CustomTextView)v.findViewById(R.id.tvAddress);
            tvActivityDate = (CustomTextView)v.findViewById(R.id.tvActivityDate);
            tvActivityTime = (CustomTextView)v.findViewById(R.id.tvActivityTime);
            tvApprovedPeople = (CustomTextView)v.findViewById(R.id.tvApprovedPeople);
            tvGoing = (CustomTextView)v.findViewById(R.id.tvGoing);
          //  tvCount = (CustomTextView)v.findViewById(R.id.tvCount);
            goingWith= (CustomTextView)v.findViewById(R.id.goingWith);
            profileImage = (RoundedImageView)v.findViewById(R.id.profileImage);

            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);

            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(ActivityThingsActivity.this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);

          //  recyclerView.setAdapter(new Approved());
        }
    }

    class Approved extends RecyclerView.Adapter<ViewHolderApproved> {
        private static final String TAG = "CustomAdapter";

        List<UserProfile.ActivityBeanX.PeopleGoingBean>
                activityDetailsBeen;

        public Approved(List<UserProfile.ActivityBeanX.PeopleGoingBean> activityDetailsBeen) {
            this.activityDetailsBeen = activityDetailsBeen;
        }

        @Override
        public ViewHolderApproved onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.simple_grid_item_myplan, viewGroup, false);

            return new ViewHolderApproved(v);
        }

        @Override
        public void onBindViewHolder(ViewHolderApproved viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            UserProfile.ActivityBeanX.PeopleGoingBean peopleGoingBean = activityDetailsBeen.get(position);

            RequestManager requestManager = Glide.with(ActivityThingsActivity.this);
            requestManager.load(peopleGoingBean.getUser().getProfile_photo()).error(R.drawable.place_holder).placeholder(R.drawable.place_holder)
                    .into(viewHolder.image_view);

            viewHolder.text_view.setText(peopleGoingBean.getUser().getFirst_name()+" "+peopleGoingBean.getUser().getLast_name());
        }

        @Override
        public int getItemCount() {

            return activityDetailsBeen.size();
        }
    }


    public class ViewHolderApproved extends RecyclerView.ViewHolder {


        ImageView image_view;
        CustomTextView text_view;
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

            text_view = (CustomTextView)v.findViewById(R.id.text_view);
            image_view = (ImageView)v.findViewById(R.id.image_view);
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
