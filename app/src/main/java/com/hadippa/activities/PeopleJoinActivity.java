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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.fragments.main_screen.DoublePeople;
import com.hadippa.fragments.main_screen.People;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.hadippa.model.DataModel;
import com.hadippa.tindercard.SwipeFlingAdapterView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class PeopleJoinActivity extends AppCompatActivity {

    RecyclerView horizontal_recycler_view;
    public static List<DataModel> posts;
    public static MyAppAdapter myAppAdapter;
    private SwipeFlingAdapterView flingContainer;

    ArrayList<String> namesArray = new ArrayList<>();
    ArrayList<Drawable> drawables = new ArrayList<>();
    private View mMapCover;
    private RelativeLayout swipeRight;
    private RelativeLayout swipeLeft;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int i = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_join);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });
        Type listType = new TypeToken<ArrayList<DataModel>>() {
        }.getType();
        GsonBuilder gsonBuilder = new GsonBuilder();

        posts = new ArrayList<>();
        posts = (new Gson().fromJson(getIntent().getExtras().getString("similar_posts"),listType));

        /*ArrayList<DataModel> dataModels = gson.fromJson(sp.getString("posts", ""), listType);
        if(dataModels != null && dataModels.size()>0){
            posts.addAll(dataModels);
        }

        Log.d("posts>>", sp.getString("posts", ""));*/

        horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);

        mMapCover = findViewById(R.id.mapCover);
        mMapCover.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.v(AppConstants.DEBUG_TAG, "mMapCover onTouch called");

              /*  if (mLayout.getPanelState() != SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    return true;
                }
*/
                return false;
            }
        });
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);

        if(posts.size()>0) {
            myAppAdapter = new MyAppAdapter(posts, PeopleJoinActivity.this);
            flingContainer.setAdapter(myAppAdapter);
            ((TextView)findViewById(R.id.nopost)).setVisibility(View.GONE);
            ((LinearLayout)findViewById(R.id.footer)).setVisibility(View.VISIBLE);

        }else{
            flingContainer.setVisibility(View.GONE);
            ((TextView)findViewById(R.id.nopost)).setVisibility(View.VISIBLE);

            ((LinearLayout)findViewById(R.id.footer)).setVisibility(View.GONE);
        }
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {

                Log.d("LIST", "removed object!");
                //  myAppAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

                makeToast(PeopleJoinActivity.this,""+ posts.get(0).getId());

                activityJoinDecline(String.valueOf(posts.get(0).getId()),AppConstants.ACTIVITY_REQUEST_DECLINE);
                posts.remove(i);
                myAppAdapter.notifyDataSetChanged();

                // myAppAdapter.notifyDataSetChanged();
                //Do something on the left!
                //You also have access to the original object.
                //If you want to use it just cast it (String) dataObject

            }

            @Override
            public void onRightCardExit(Object dataObject) {
                makeToast(PeopleJoinActivity.this, "Right!");
                activityJoinDecline(String.valueOf(posts.get(0).getId()),AppConstants.ACTIVITY_REQUEST_JOIN);
                posts.remove(i);
                myAppAdapter.notifyDataSetChanged();
                /*al.remove(0);
                myAppAdapter.notifyDataSetChanged();*/
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {

                View view = flingContainer.getSelectedView();
                // view.findViewById(R.id.background).setAlpha(0);
                view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
                view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {

                //View view = flingContainer.getSelectedView();
                //view.findViewById(R.id.background).setAlpha(0);

                myAppAdapter.notifyDataSetChanged();
            }
        });

        swipeLeft = (RelativeLayout) findViewById(R.id.imageLeft);
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        swipeRight = (RelativeLayout) findViewById(R.id.imageRight);
        swipeRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });
    }

    private void showBottomPeopleGoing(DataModel dataModel){

        BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(PeopleJoinActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_people_going, null);

        RecyclerView horizontal_recycler_view = (RecyclerView) sheetView.findViewById(R.id.horizontal_recycler_view);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(PeopleJoinActivity.this, LinearLayoutManager.HORIZONTAL, false);
        horizontal_recycler_view.setLayoutManager(horizontalLayoutManagaer);

        dataModel.getPeople_going();

        //Sample arraylist..
        List<DoublePeople> doublePeoples = new ArrayList<>();
        doublePeoples.add(new DoublePeople(new People("palak", ""), new People("darji", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));
        doublePeoples.add(new DoublePeople(new People("kartick", ""), new People("boss", "")));

        doublePeoples.add(new DoublePeople(new People("Sahil", ""), null));

        List<DoublePeople> doublePeoples1 = new ArrayList<>();

        for(int i = 0;i < dataModel.getPeople_going().size();i++){

            DataModel.PeopleGoingBean.UserBeanX userBeanX1 =null;
            DataModel.PeopleGoingBean.UserBeanX userBeanX2 = null;

            userBeanX1 = dataModel.getPeople_going().get(i).getUser();
            i = i+1;
            if(dataModel.getPeople_going().size() > i){
                userBeanX2 = dataModel.getPeople_going().get(i).getUser();
            }

            DoublePeople doublePeople = new DoublePeople(userBeanX1,userBeanX2);
            doublePeoples1.add(doublePeople);
        }


        HorizontalAdapter adapter = new HorizontalAdapter(doublePeoples1);
        horizontal_recycler_view.setAdapter(adapter);

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }


    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }

    private void activityJoinDecline(String activity_id,String requestFor) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("activity_id", activity_id);

            requestParams.add("access_token", (sp.getString("access_token", "")));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + requestFor, requestParams,
                new ActivityJoinDecline());

    }


    class ActivityJoinDecline extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  dataScroll.setVisibility(View.GONE);
            AppConstants.showProgressDialog(PeopleJoinActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

//            updateDonut((int) ((totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {


            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("async_step_2", "success" + response);
                if(jsonObject.getBoolean("success")){

                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Could not register. try again");
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

    class MyAppAdapter extends BaseAdapter {

        public List<DataModel> parkingList = new ArrayList<>();
        public Context context;


        public void remove(int i) {

        }

        private MyAppAdapter(List<DataModel> apps, Context context) {
            this.parkingList = apps;
            this.context = context;
        }

        @Override
        public int getCount() {
            return parkingList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder {
            LinearLayout llGoing;
            ImageView imageView, coverImage;
            TextView tvGoing, tvName_Age, tvDistance,
                    tvActivityName, tvActivtyTime, tvActivtyDate, tvAddress, tvCount;
            TextView txtDesc;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;

            if (convertView == null) {

                LayoutInflater inflater = PeopleJoinActivity.this.getLayoutInflater();
                convertView = inflater.inflate(R.layout.item, parent, false);
                // configure view holder
                viewHolder = new ViewHolder();
                viewHolder.llGoing = (LinearLayout) convertView.findViewById(R.id.llGoing);
                viewHolder.tvGoing = (TextView) convertView.findViewById(R.id.tvGoing);
                viewHolder.tvName_Age = (TextView) convertView.findViewById(R.id.tvName_Age);
                viewHolder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
                viewHolder.tvActivityName = (TextView) convertView.findViewById(R.id.tvActivityName);
                viewHolder.tvActivtyTime = (TextView) convertView.findViewById(R.id.tvActivtyTime);
                viewHolder.tvActivtyDate = (TextView) convertView.findViewById(R.id.tvActivityDate);
                viewHolder.tvCount = (TextView) convertView.findViewById(R.id.tvCount);
                viewHolder.tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);
                viewHolder.coverImage = (ImageView) convertView.findViewById(R.id.coverImage);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            DataModel dataModel = parkingList.get(position);

            if(dataModel != null){

                viewHolder.tvName_Age.setText(dataModel.getUser().getFirst_name()
                        + " " + dataModel.getUser().getLast_name() + ", " +
                        "" + dataModel.getUser().getAge());

                viewHolder.tvActivityName.setText(dataModel.getActivity_details().getActivity_name());
                viewHolder.tvActivtyTime.setText(dataModel.getActivity_time());
                viewHolder.tvActivtyDate.setText(convertDate(dataModel.getActivity_date()));
                viewHolder.tvAddress.setText(dataModel.getActivity_location());
                viewHolder.tvGoing.setText(String.valueOf(dataModel.getPeople_going_count().size()) + " Going");
                viewHolder.tvCount.setText(String.valueOf(dataModel.getId()));

                Glide.with(context)
                        .load(dataModel.getUser().getProfile_photo())
                        .into(viewHolder.coverImage);


            }
            viewHolder.llGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAppAdapter.getItem(position);

                    showBottomPeopleGoing(posts.get(position));
                }
            });
            /*viewHolder.llGoing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myAppAdapter.getItem(position);

                    showBottomPeopleGoing(posts.get(position));
                }
            });*/
            //viewHolder.DataText.setText(parkingList.get(position).getDescription() + "");

            //Glide.with(MainActivity.this).load(parkingList.get(position).getImagePath()).into(viewHolder.cardImage);

            return convertView;
        }
    }

    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {

        private List<DoublePeople> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView text_view1;
            private TextView text_view2;
            private ImageView image_view1;
            private ImageView image_view2;

            private LinearLayout topLinear,bottomLinear;

            public MyViewHolder(View view) {
                super(view);
                text_view1 = (TextView) view.findViewById(R.id.text_view1);
                text_view2 = (TextView) view.findViewById(R.id.text_view2);
                image_view1 = (ImageView) view.findViewById(R.id.image_view1);
                image_view2 = (ImageView) view.findViewById(R.id.image_view2);
                topLinear = (LinearLayout)view.findViewById(R.id.topLinear);
                bottomLinear = (LinearLayout)view.findViewById(R.id.bottomLinear);
            }
        }


        public HorizontalAdapter(List<DoublePeople> horizontalList) {
            this.horizontalList = horizontalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.two_grid_item, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Log.v("hadippa", "onBindViewHolder");
            RequestManager requestManager = Glide.with(PeopleJoinActivity.this);

            if(horizontalList.get(position).getBeanX()!=null){
                holder.topLinear.setVisibility(View.VISIBLE);
                holder.text_view1.setText(horizontalList.get(position).getBeanX().getFirst_name());

                requestManager.load(horizontalList.get(position).getBeanX().getProfile_photo())
                        .placeholder(R.drawable.place_holder).into(holder.image_view1);

            }else{
                holder.topLinear.setVisibility(View.GONE);
            }
            if(horizontalList.get(position).getBeanX1()!=null){
                holder.bottomLinear.setVisibility(View.VISIBLE);
                holder.text_view2.setText(horizontalList.get(position).getBeanX1().getFirst_name());
                requestManager.load(horizontalList.get(position).getBeanX1().getProfile_photo())
                        .placeholder(R.drawable.place_holder).into(holder.image_view2);
            }else{
                holder.bottomLinear.setVisibility(View.GONE);
            }

            holder.text_view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PeopleJoinActivity.this, holder.text_view1.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });

            holder.text_view2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(PeopleJoinActivity.this, holder.text_view2.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }


    class SimpleAdapter extends BaseAdapter {

        private LayoutInflater layoutInflater;

        public SimpleAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            View view = convertView;

            if (view == null) {
                view = layoutInflater.inflate(R.layout.simple_grid_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.textView = (TextView) view.findViewById(R.id.text_view);
                viewHolder.imageView = (ImageView) view.findViewById(R.id.image_view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.textView.setText(namesArray.get(position));
            viewHolder.imageView.setImageDrawable(drawables.get(position));
            return view;
        }

        class ViewHolder {
            TextView textView;
            ImageView imageView;
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_people_join)),intent.getExtras().getString("messageData"));
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
