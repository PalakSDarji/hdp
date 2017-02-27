package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.hadippa.model.UserProfile;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class ActivityThingsActivity extends AppCompatActivity {

    //Demo Github
    RecyclerView myRecycler;
    ImageView imageBack;
    CustomTextView tvTitleName;
    List<UserProfile.ActivityBeanX> activityBeanX;

    CustomAdapter customAdapter;
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

        tvTitleName = (CustomTextView)findViewById(R.id.tvTitleName);

        Gson gson = new Gson();
        UserProfile userProfile = gson.fromJson(getIntent().getExtras().getString("data"),UserProfile.class);

        if(userProfile.getActivity()!=null) {

            tvTitleName.setText("Activity of "+ userProfile.getUser().getFirst_name()+" "+userProfile.getUser().getLast_name());
            activityBeanX = userProfile.getActivity();

            myRecycler = (RecyclerView) findViewById(R.id.myRecycler);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            myRecycler.setLayoutManager(mLayoutManager);
            customAdapter = new CustomAdapter();
            myRecycler.setAdapter(customAdapter);
        }}

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


             AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_things)),intent.getExtras().getString("messageData"));
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

            if(getIntent().getExtras().getString("profile").equals("other")){
                viewHolder.ivMore.setVisibility(View.INVISIBLE);
            }else{
                viewHolder.ivMore.setVisibility(View.VISIBLE);
                viewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(ActivityThingsActivity.this);
                        View sheetView = LayoutInflater.from(ActivityThingsActivity.this).inflate(R.layout.item_remove_activity, null);
                        mBottomSheetDialog.setContentView(sheetView);

                        sheetView.findViewById(R.id.tvDeleteActivity).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mBottomSheetDialog.dismiss();
                                deleteActivity(String.valueOf(myPlansBean.getId()));
                            }
                        });

                        mBottomSheetDialog.show();
                    }
                });

            }

            try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date strDate = sdf.parse(myPlansBean.getActivity_date());
            if (new Date().after(strDate)) {
                viewHolder.tvExpired.setVisibility(View.VISIBLE);
            }else{
                viewHolder.tvExpired.setVisibility(View.INVISIBLE);
            }
            }catch (Exception e){
                viewHolder.tvExpired.setText("Error");
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

            setDrawable(myPlansBean.getActivity().getActivity_category().getId(),viewHolder.ivActivityIcon);



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


    void setDrawable(int actiivty_type,ImageView view){
        switch (actiivty_type){
            case AppConstants.API_ACTIVITY_ID_MOVIE:

                view.setImageResource(R.drawable.ic_movies);
                break;
            case AppConstants.API_ACTIVITY_ID_THEATER:

                view.setImageResource(R.drawable.ic_theaterplay);
                break;
            case AppConstants.API_ACTIVITY_ID_EVENT:

                view.setImageResource(R.drawable.ic_event);
                break;
            case AppConstants.API_ACTIVITY_ID_NIGHTCLUB:

                view.setImageResource(R.drawable.ic_nightclub);
                break;
            case AppConstants.API_ACTIVITY_ID_LOUNGE:

                view.setImageResource(R.drawable.ic_lounge);
                break;
            case AppConstants.API_ACTIVITY_ID_PARTY:

                view.setImageResource(R.drawable.ic_party);
                break;
            case AppConstants.API_ACTIVITY_ID_STAND_UP_COMEDY:

                view.setImageResource(R.drawable.ic_standup_comedy);
                break;
            case AppConstants.API_ACTIVITY_ID_FLIGHT:

                view.setImageResource(R.drawable.ic_plane);
                break;
            case AppConstants.API_ACTIVITY_ID_TRAIN:

                view.setImageResource(R.drawable.ic_train);
                break;
            case AppConstants.API_ACTIVITY_ID_BUS:

                view.setImageResource(R.drawable.ic_bus);
                break;
            case AppConstants.API_ACTIVITY_ID_COFFEE:

                view.setImageResource(R.drawable.ic_coffee);
                break;
            case AppConstants.API_ACTIVITY_ID_INDOOR:

                view.setImageResource(R.drawable.ic_sports);
                break;
            case AppConstants.API_ACTIVITY_ID_OUTDOOR:

                view.setImageResource(R.drawable.ic_outdoorsports);
                break;
            case AppConstants.API_ACTIVITY_ID_AVD_SPORTS:

                view.setImageResource(R.drawable.ic_adventuresports);
                break;
            case AppConstants.API_ACTIVITY_ID_HOBBY:

                view.setImageResource(R.drawable.ic_hobby);
                break;
            case AppConstants.API_ACTIVITY_ID_FESTIVAL:

                view.setImageResource(R.drawable.ic_festival);
                break;
            case AppConstants.API_ACTIVITY_ID_OTHER:

                view.setImageResource(R.drawable.ic_movies);
                break;


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
        ImageView ivMore,ivActivityIcon;
        TextView tvExpired;
        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            tvExpired = (TextView)v.findViewById(R.id.tvExpired);
            llGoing = (LinearLayout) v.findViewById(R.id.llGoing);
            ivMore = (ImageView) v.findViewById(R.id.ivMore);
            ivActivityIcon = (ImageView) v.findViewById(R.id.ivActivityIcon);
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

            UserProfile.ActivityBeanX.PeopleGoingBean  peopleGoingBean = activityDetailsBeen.get(position);

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


    private void deleteActivity(String activity_id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ActivityThingsActivity.this);
        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("id", activity_id);

            Log.d("rollBack?>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.ACTIVITY_DELETE, requestParams,
                new DeleteActivity(activity_id));
    }

    class DeleteActivity extends AsyncHttpResponseHandler {

        String activity_id;

        public DeleteActivity(String activity_id) {
            this.activity_id = activity_id;
        }

        @Override
        public void onStart() {
            super.onStart();

           showProgressDialog(ActivityThingsActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            dismissDialog();
            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("rollBack?>>", "success" + response);

                if (jsonObject.getBoolean("success")) {


                    for(UserProfile.ActivityBeanX myPlansBean : activityBeanX){
                        if(String.valueOf(myPlansBean.getId()).equals(activity_id)){
                            activityBeanX.remove(myPlansBean);
                            break;
                        }
                    }

                    customAdapter.notifyDataSetChanged();


                } else {


                }
                Log.d("rollBack?>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("rollBack?>>", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            dismissDialog();
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    public KProgressHUD hud;

    public void showProgressDialog(Context context, String message) {
        // PROGRESS_DIALOG.show();

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(context.getResources().getColor(R.color.back_progress))

                .setLabel(message)
                .setDimAmount(0.5f)
                .setCancellable(true)
                .setAnimationSpeed(2);

        if(hud!=null){
            hud.show();
        }
    }

    public void dismissDialog() {

        if (hud != null) hud.dismiss();


    }

}
