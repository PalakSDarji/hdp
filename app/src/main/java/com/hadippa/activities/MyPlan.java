package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.MyPlansModel;
import com.kaopiz.kprogresshud.KProgressHUD;
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

public class MyPlan extends AppCompatActivity {

    RecyclerView myPlanRecycler;
    ImageView imageBack;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    List<MyPlansModel.MyPlansBean> myPlansBeen = new ArrayList<>();

    SwipeRefreshLayout swipeRefreshLayout;

    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plan);

        sp = PreferenceManager.getDefaultSharedPreferences(MyPlan.this);
        editor = sp.edit();

        findViewById(R.id.linearHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent  intent = new Intent(MyPlan.this,HistoryPlan.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_middle, R.anim.to_middle);

            }
        });
        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setResult(RESULT_CANCELED);
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        myPlanRecycler = (RecyclerView) findViewById(R.id.myPlanRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyPlan.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myPlanRecycler.setLayoutManager(mLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                myPlans();
            }
        });

        myPlanRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        swipeRefreshLayout.setDistanceToTriggerSync(50);

        myPlans();
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.my_plan)), intent.getExtras().getString("messageData"));
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
        private Context context;

        public CustomAdapter(Context context) {
            this.context = context;
        }

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

            if (myPlansBean.getPeople_going().size() == 0) {
                viewHolder.recyclerView.setVisibility(View.GONE);
            } else {
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
                viewHolder.recyclerView.setAdapter(new Approved(myPlansBean.getPeople_going()));
            }


            Log.v(TAG, "LLGoing  " + myPlansBean.getPeople_going_count());
            if (myPlansBean.getPeople_going_count() != null && myPlansBean.getPeople_going_count().size() > 0) {

                viewHolder.llGoing.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO sahil. look at this
                        Log.v(TAG, "LLGoing  " + myPlansBean.isOpened());

                        myPlansBean.setOpened(!myPlansBean.isOpened());
                        notifyDataSetChanged();

                    }
                });
            } else {
                viewHolder.llGoing.setOnClickListener(null);
            }

            if(myPlansBean.getNotification()==1){
                viewHolder.switchCompat.setChecked(true);
            }else{
                viewHolder.switchCompat.setChecked(false);
            }

            if (myPlansBean.isOpened()) {
                Log.v(TAG, "myPlansBean.isOpened(): 1" + myPlansBean.isOpened());
                viewHolder.vSep.setVisibility(View.VISIBLE);
                viewHolder.tvApprovedPeople.setVisibility(View.VISIBLE);
                viewHolder.recyclerView.setVisibility(View.VISIBLE);
                viewHolder.ivChat.setVisibility(View.VISIBLE);
            } else {
                Log.v(TAG, "myPlansBean.isOpened() 2: " + myPlansBean.isOpened());
                viewHolder.vSep.setVisibility(View.GONE);
                viewHolder.tvApprovedPeople.setVisibility(View.GONE);
                viewHolder.recyclerView.setVisibility(View.GONE);
                viewHolder.ivChat.setVisibility(View.GONE);

            }

            viewHolder.tvPersonName.setText(myPlansBean.getUser().getFirst_name()+" "+myPlansBean.getUser().getLast_name()+", "+
            myPlansBean.getUser().getAge());
            viewHolder.activityname.setText(myPlansBean.getActivity_details().getActivity_name());
            viewHolder.tvAddress.setText(myPlansBean.getActivity_location());
            viewHolder.tvActivityDate.setText(convertDate(myPlansBean.getActivity_date()));
            viewHolder.tvActivityTime.setText(AppConstants.formatDate(myPlansBean.getActivity_time(), "HH:mm", "hh:mm a"));
            viewHolder.tvGoing.setText(myPlansBean.getPeople_going().size() + "");

            setDrawable(myPlansBean.getActivity().getActivity_category().getId(),viewHolder.ivActivityIcon);

            if (myPlansBean.getPeople_going_count() != null && myPlansBean.getPeople_going_count().size() > 0) {
                viewHolder.tvCount.setText(myPlansBean.getPeople_going_count().size() + "");

                String text = "";
                if (myPlansBean.getPeople_going().size() > 2) {


                    text = "You are going with " + myPlansBean.getPeople_going().get(0).getUser().getFirst_name()
                            + " " + myPlansBean.getPeople_going().get(0).getUser().getLast_name() + " and " +
                            (myPlansBean.getPeople_going().size() - 2) + " people";
                } else {

                    text = "You are going with " + myPlansBean.getPeople_going().get(0).getUser().getFirst_name()
                            + " " + myPlansBean.getPeople_going().get(0).getUser().getLast_name();

                }

                viewHolder.goingWith.setText(text);
                viewHolder.goingWith.setVisibility(View.VISIBLE);


            } else {
                viewHolder.goingWith.setVisibility(View.GONE);
                viewHolder.tvCount.setText("0");
            }


            if (myPlansBean.getUser().getProfile_photo().isEmpty() ||
                    myPlansBean.getUser().getProfile_photo().equals("")) {

                viewHolder.profileImage.setImageResource(R.drawable.place_holder);
            } else {

                Glide.with(MyPlan.this)
                        .load(myPlansBean.getUser().getProfile_photo())
                        .error(R.drawable.place_holder)
                        .placeholder(R.drawable.place_holder)
                        .into(viewHolder.profileImage);
            }

            viewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(context);
                    View sheetView = LayoutInflater.from(context).inflate(R.layout.item_remove_activity, null);
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

        @Override
        public int getItemCount() {

            return myPlansBeen.size();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final View vSep;
        private final RecyclerView recyclerView;
        private final LinearLayout llGoing;
        CustomTextView tvPersonName, activityname, tvAddress, tvActivityDate, tvActivityTime, tvGoing, tvCount, tvApprovedPeople, goingWith;
        RoundedImageView profileImage;
        ImageView ivMore, ivChat,ivActivityIcon;
        SwitchCompat switchCompat;
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

            ivMore = (ImageView) v.findViewById(R.id.ivMore);
            ivChat = (ImageView) v.findViewById(R.id.ivChat);
            ivActivityIcon = (ImageView) v.findViewById(R.id.ivActivityIcon);

            vSep = (View) v.findViewById(R.id.vSep);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            tvPersonName = (CustomTextView) v.findViewById(R.id.tvPersonName);
            activityname = (CustomTextView) v.findViewById(R.id.activityname);
            tvAddress = (CustomTextView) v.findViewById(R.id.tvAddress);
            tvActivityDate = (CustomTextView) v.findViewById(R.id.tvActivityDate);
            tvActivityTime = (CustomTextView) v.findViewById(R.id.tvActivityTime);
            tvApprovedPeople = (CustomTextView) v.findViewById(R.id.tvApprovedPeople);
            tvGoing = (CustomTextView) v.findViewById(R.id.tvGoing);
            tvCount = (CustomTextView) v.findViewById(R.id.tvCount);
            goingWith = (CustomTextView) v.findViewById(R.id.goingWith);
            profileImage = (RoundedImageView) v.findViewById(R.id.profileImage);
            switchCompat = (SwitchCompat)v.findViewById(R.id.switchCompat);
            final LinearLayoutManager mLayoutManager = new LinearLayoutManager(MyPlan.this);
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

            RequestManager requestManager = Glide.with(MyPlan.this);
            requestManager.load(peopleGoingBean.getUser().getProfile_photo()).error(R.drawable.place_holder).placeholder(R.drawable.place_holder)
                    .into(viewHolder.image_view);

            viewHolder.text_view.setText(peopleGoingBean.getUser().getFirst_name() + " " + peopleGoingBean.getUser().getLast_name());
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

            text_view = (CustomTextView) v.findViewById(R.id.text_view);
            image_view = (ImageView) v.findViewById(R.id.image_view);
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

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.MY_PLANS, requestParams,
                new FetchMyPlans());
    }

    class FetchMyPlans extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            showProgressDialog(MyPlan.this, "Please Wait");

        }


        @Override
        public void onFinish() {

            if(swipeRefreshLayout.isRefreshing()){
                swipeRefreshLayout.setRefreshing(false);
            }
            dismissDialog();
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
                    myPlansBeen = myPlansModel.getMy_plans();
                    customAdapter = new CustomAdapter(MyPlan.this);
                    myPlanRecycler.setAdapter(customAdapter);
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
    private void deleteActivity(String activity_id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

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

            showProgressDialog(MyPlan.this, "Please Wait");

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


                    for(MyPlansModel.MyPlansBean myPlansBean : myPlansBeen){
                        if(String.valueOf(myPlansBean.getId()).equals(activity_id)){
                            myPlansBeen.remove(myPlansBean);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        setResult(RESULT_CANCELED);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }
}
