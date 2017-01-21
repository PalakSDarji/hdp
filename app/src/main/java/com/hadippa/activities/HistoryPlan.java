package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class HistoryPlan extends AppCompatActivity {

    RecyclerView myPlanRecycler;
    ImageView imageBack;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    List<MyPlansModel.MyPlansBean> myPlansBeen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        sp = PreferenceManager.getDefaultSharedPreferences(HistoryPlan.this);
        editor = sp.edit();

        ((CustomTextView)findViewById(R.id.up_His)).setText("History");
        ((CustomTextView)findViewById(R.id.imageFilter)).setText("Recent");

        ((LinearLayout) findViewById(R.id.linearHistory)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        myPlanRecycler = (RecyclerView)findViewById(R.id.myPlanRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(HistoryPlan.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myPlanRecycler.setLayoutManager(mLayoutManager);



        myPlans();
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


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
        private static final String TAG = "CustomAdapter";

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.my_plan_list_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");

            final MyPlansModel.MyPlansBean myPlansBean = myPlansBeen.get(position);

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
            viewHolder.tvActivityDate.setText(convertDate(myPlansBean.getActivity_date()));
            viewHolder.tvActivityTime.setText(AppConstants.formatDate(myPlansBean.getActivity_time(), "HH:mm", "hh:mm a"));
            viewHolder.tvGoing.setText(myPlansBean.getPeople_going().size()+"");
            if(myPlansBean.getPeople_going_count() != null && myPlansBean.getPeople_going_count().size() > 0){
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
            }




            if(myPlansBean.getUser().getProfile_photo().isEmpty() ||
                    myPlansBean.getUser().getProfile_photo().equals("") ){

                viewHolder.profileImage.setImageResource(R.drawable.place_holder);
            }else{

                Glide.with(HistoryPlan.this)
                        .load(myPlansBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.profileImage);
            }


        }

        @Override
        public int getItemCount() {

            return myPlansBeen.size();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View vSep;
        private final RecyclerView recyclerView;
        private final LinearLayout llGoing;
        CustomTextView activityname,tvAddress,tvActivityDate,tvActivityTime,tvGoing,tvCount,tvApprovedPeople,goingWith;
        RoundedImageView profileImage;

        public ViewHolder(final View v) {
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

            llGoing = (LinearLayout) v.findViewById(R.id.llGoing);

            vSep = (View) v.findViewById(R.id.vSep);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            activityname = (CustomTextView)v.findViewById(R.id.activityname);
            tvAddress = (CustomTextView)v.findViewById(R.id.tvAddress);
            tvActivityDate = (CustomTextView)v.findViewById(R.id.tvActivityDate);
            tvActivityTime = (CustomTextView)v.findViewById(R.id.tvActivityTime);
            tvApprovedPeople = (CustomTextView)v.findViewById(R.id.tvApprovedPeople);
            tvGoing = (CustomTextView)v.findViewById(R.id.tvGoing);
            tvCount = (CustomTextView)v.findViewById(R.id.tvCount);
            goingWith= (CustomTextView)v.findViewById(R.id.goingWith);
            profileImage = (RoundedImageView)v.findViewById(R.id.profileImage);

            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(HistoryPlan.this);
            mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(mLayoutManager);

           // recyclerView.setAdapter(new Approved());



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

    class Approved extends RecyclerView.Adapter<ViewHolderApproved> {
        private static final String TAG = "CustomAdapter";

        List<MyPlansModel.MyPlansBean.PeopleGoingBean>
        activityDetailsBeen;

        public Approved(List<MyPlansModel.MyPlansBean.PeopleGoingBean> activityDetailsBeen) {
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

            MyPlansModel.MyPlansBean.PeopleGoingBean peopleGoingBean = activityDetailsBeen.get(position);

            RequestManager requestManager = Glide.with(HistoryPlan.this);
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

    //Get My Plans
    private void myPlans() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));

            Log.d("myplan>> req", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.MY_PLANS_HISTORY, requestParams,
                new FetchMyPlans());
    }

    class FetchMyPlans extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

              AppConstants.showProgressDialog(HistoryPlan.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("myplan>>", response);
                JSONObject jsonObject = new JSONObject(response);

                Gson gson = new Gson();
                MyPlansModel myPlansModel = gson.fromJson(jsonObject.toString(), MyPlansModel.class);
                if (myPlansModel.isSuccess()) {

                    Log.d("myplan>>", response);
                    myPlansBeen = myPlansModel.getMy_plans_history();
                    myPlanRecycler.setAdapter(new CustomAdapter());
                    //post json stored g\here

                } else {


                    //  AppConstants.showSnackBar(mainRel,"Invalid username or password");

                }
                Log.d("async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.my_plan)),intent.getExtras().getString("messageData"));
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

}
