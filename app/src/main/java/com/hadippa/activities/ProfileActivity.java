package com.hadippa.activities;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.APIClass;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.AlbumStorageDirFactory;
import com.demo.BaseAlbumDirFactory;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.instagram.Cons;
import com.hadippa.instagram.Insta;
import com.hadippa.instagram.InstagramRequest;
import com.hadippa.instagram.InstagramSession;
import com.hadippa.instagram.InstagramUser;
import com.hadippa.model.Contact;
import com.hadippa.model.UserProfile;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yalantis.ucrop.UCrop;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

    Insta insta;

    @BindView(R.id.rvMutualFriend)
    RecyclerView rvMutualFriend;
    @BindView(R.id.rvRecentInstagram)
    RecyclerView rvRecentInstagram;
    @BindView(R.id.ivEdit)
    ImageView ivEdit;
    @BindView(R.id.tvTitleName)
    CustomTextView tvTitleName;
    @BindView(R.id.ivChat)
    ImageView ivChat;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    /* @BindView(R.id.ivProfilePic)
     SquareImageView ivProfilePic;*/
    @BindView(R.id.tvActivityVal)
    CustomTextView tvActivityVal;
    @BindView(R.id.tvApproaching2Val)
    CustomTextView tvApproaching2Val;
    @BindView(R.id.llFollowUnfollow)
    LinearLayout llFollowUnfollow;
    @BindView(R.id.ivFollowUnfollow)
    ImageView ivFollowUnfollow;
    @BindView(R.id.tvFollowUnfollow)
    CustomTextView tvFollowUnfollow;
    @BindView(R.id.tvOccupation)
    CustomTextView tvOccupation;
    @BindView(R.id.tvCompany)
    CustomTextView tvCompany;
    @BindView(R.id.tvCity)
    CustomTextView tvCity;
    @BindView(R.id.tvZodiac)
    CustomTextView tvZodiac;
    @BindView(R.id.tvLang)
    CustomTextView tvLang;
    @BindView(R.id.tvRecentInstagram)
    CustomTextView tvRecentInstagram;
    @BindView(R.id.tvMutual)
    CustomTextView tvMutual;
    @BindView(R.id.vSep2)
    View vSep2;
    @BindView(R.id.vSep3)
    View vSep3;

    @BindView(R.id.rlAppr3)
    RelativeLayout llAppr3;
    @BindView(R.id.tvApproaching3Val)
    CustomTextView tvApproaching3Val;

    SharedPreferences sp;
    SharedPreferences.Editor editor;


    UserProfile.UserBean userBean;
    List<UserProfile.ActivityBeanX> activityBeanX;
    String activityData = "";

    Uri sourceUri;
    AlbumStorageDirFactory albumStorageDirFactory = null;
    final String JPEG_FILE_PREFIX = "Hadipaa_";
    final String JPEG_FILE_SUFFIX = ".jpg";
    String currentPhotoPath;
    String base64String = "";
    private SliderLayout slider;
    HashMap<String, String> url_maps = new HashMap<String, String>();

    Button connectInstagram;

    @BindView(R.id.rlConnectInstagram)
    RelativeLayout rlConnectInstagram;

    @BindView(R.id.llConnectInstagram)
    LinearLayout llConnectInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        sp = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        editor = sp.edit();
        albumStorageDirFactory = new BaseAlbumDirFactory();
        slider = (SliderLayout) findViewById(R.id.slider);

        connectInstagram = (Button) findViewById(R.id.connectInstagram);

        rlConnectInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                insta = new Insta(ProfileActivity.this);
                insta.login();
            }
        });
        slider.setPresetTransformer(SliderLayout.Transformer.Default);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        //slider.setCustomAnimation(new DescriptionAnimation());
        slider.setDuration(4000);

        findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                intent.putExtra("data", userBean);
                //  startActivity(intent);
                startActivityForResult(intent, 239);
            }
        });

        findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(ProfileActivity.this,findViewById(R.id.ivMore));
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.block:


                                AlertDialog.Builder builder1 = new AlertDialog.Builder(ProfileActivity.this);
                                builder1.setMessage("Do you want to block this user ?");
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        "Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                block_Unblock("/block", String.valueOf(userBean.getId()));
                                                dialog.cancel();
                                            }
                                        });

                                builder1.setNegativeButton(
                                        "No",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });

                                AlertDialog alert11 = builder1.create();
                                alert11.show();


                                return true;
                            case R.id.report:

                                return true;

                        }

                        return true;
                    }
                });

                popup.show();//showing popup menu
            }


        });
        findViewById(R.id.imageBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.rlActivity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

                    Intent intent = new Intent(ProfileActivity.this, ActivityThingsActivity.class);
                    intent.putExtra("data", activityData);
                    intent.putExtra("profile","other");
                    startActivity(intent);
                    return;
                }
                if (tvActivityVal.getText().toString().equals("0")) {

                } else {
                    Intent intent = new Intent(ProfileActivity.this, ActivityThingsActivity.class);
                    intent.putExtra("data", activityData);
                    intent.putExtra("profile","my");
                    startActivity(intent);

                }
            }
        });


        llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_relationship_status.equalsIgnoreCase("Connected")
                        || user_relationship_status.equalsIgnoreCase("Following")) {

                    llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                    tvFollowUnfollow.setText("Follow");
                    tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
                    // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
                    ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);
                    follow_Unfollow(AppConstants.CONNECTION_UNFOLLOW);


                } else {

                    llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
                    tvFollowUnfollow.setText("Following");
                    tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
                    ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);

                    follow_Unfollow(AppConstants.CONNECTION_FOLLOW);

                }

            }
        });


        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ProfileActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rvMutualFriend.setLayoutManager(horizontalLayoutManagaer);

        //Sample arraylist..
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());

        MutualFriendAdapter adapter = new MutualFriendAdapter(contacts);
        //  rvMutualFriend.setAdapter(adapter);

        GridLayoutManager instagramLayoutManagaer = new GridLayoutManager(ProfileActivity.this, 4);
        rvRecentInstagram.setLayoutManager(instagramLayoutManagaer);

        dddd();

    }



    String user_relationship_status = "";

    void setFollowUnfollow(String user_relationship_status) {
        if (user_relationship_status.equalsIgnoreCase("Connected")
                || user_relationship_status.equalsIgnoreCase("Following")) {


            llFollowUnfollow.setBackgroundResource(R.drawable.rounded_following);
            tvFollowUnfollow.setText("Following");
            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.white));
            ivFollowUnfollow.setImageResource(R.drawable.ic_user_following);

        } else {

            llFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
            tvFollowUnfollow.setText("Follow");
            tvFollowUnfollow.setTextColor(getResources().getColor(R.color.pink_text));
            // viewHolder.tvFollowUnfollow.setBackgroundResource(R.drawable.rounded_followers);
            ivFollowUnfollow.setImageResource(R.drawable.ic_user_follow);


        }
    }

    private void follow_Unfollow(String type) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("followed_id", String.valueOf(userBean.getId()));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Follow_Unfollow(type));
    }

    class Follow_Unfollow extends AsyncHttpResponseHandler {

        String type;

        public Follow_Unfollow(String type) {
            this.type = type;
        }

        @Override
        public void onStart() {
            super.onStart();

            //   showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            //dismissDialog();
        }

        @Override
        public void onProgress(long bytesWritten, long totalSize) {
            super.onProgress(bytesWritten, totalSize);

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("success")) {


                   // user_relationship_status = jsonObject.getString("status");
                    userBean.setUser_relationship_status(user_relationship_status);
                    setFollowUnfollow(user_relationship_status);


                } else {

                    if(user_relationship_status.equalsIgnoreCase("Connected") || user_relationship_status.equalsIgnoreCase("Following")){
                        user_relationship_status = "Follower";
                    }else{
                        user_relationship_status = "Following";
                    }
                    setFollowUnfollow(user_relationship_status);

                }
                Log.d("async", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            setFollowUnfollow(user_relationship_status);
        }

    }

    void dddd() {


        if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {
            //llConnectInstagram.setVisibility(View.GONE);
            llFollowUnfollow.setVisibility(View.GONE);
            rvMutualFriend.setVisibility(View.GONE);
            tvRecentInstagram.setVisibility(View.GONE);
            tvMutual.setVisibility(View.GONE);
            rvRecentInstagram.setVisibility(View.GONE);
            vSep2.setVisibility(View.GONE);
            vSep3.setVisibility(View.GONE);
            llAppr3.setVisibility(View.VISIBLE);

            findViewById(R.id.ivMore).setVisibility(View.GONE);

            try {
                JSONObject jsonObject = new JSONObject(sp.getString("userData", ""));
                userBean = new UserProfile.UserBean();
                userBean.setId(jsonObject.getInt("id"));
                userBean.setFirst_name(jsonObject.getString("first_name"));
                userBean.setLast_name(jsonObject.getString("last_name"));
                userBean.setOccupation(jsonObject.getString("occupation"));
                userBean.setInterested_in(jsonObject.getString("interested_in"));
                userBean.setLanuage_known(jsonObject.getString("lanuage_known"));
                userBean.setCity(jsonObject.getString("city"));
                userBean.setCompany(jsonObject.getString("company"));
                userBean.setAge_range_from(jsonObject.getInt("age_range_from"));
                userBean.setAge_range_to(jsonObject.getInt("age_range_to"));
                userBean.setMobile(jsonObject.getLong("mobile"));
                userBean.setZodiac(jsonObject.getString("zodiac"));
                userBean.setProfile_photo(jsonObject.getString("profile_photo"));
                userBean.setPrivate_account(jsonObject.getInt("private_account"));
                userBean.setDob(jsonObject.getString("dob"));
                if(jsonObject.has("age")) {
                    userBean.setAge(jsonObject.getInt("age"));
                }
                List<UserProfile.UserBean.ProfilePhotosBean> profilePhotosBeen = new ArrayList<>();
                for(int i =0 ;i < jsonObject.getJSONArray("profile_photos").length();i++){
                    JSONObject jsonObject1 = jsonObject.getJSONArray("profile_photos").getJSONObject(i);
                    UserProfile.UserBean.ProfilePhotosBean photosBean = new UserProfile.UserBean.ProfilePhotosBean();
                    photosBean.setIdX(jsonObject1.getInt("id"));
                    photosBean.setImage(jsonObject1.getString("image"));
                    photosBean.setUser_id(jsonObject1.getInt("user_id"));
                    profilePhotosBeen.add(photosBean);
                }
              userBean.setProfile_photos(profilePhotosBeen);

                setData(userBean,true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            llFollowUnfollow.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.GONE);
            //llConnectInstagram.setVisibility(View.GONE);
        }

        fetchProfile();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {
            Intent intent = new Intent(ProfileActivity.this, EditProfilePicsActivity.class);
            intent.putExtra("data", userBean);
            intent.putExtra(AppConstants.FETCH_USER_KEY, getIntent().getExtras().getString(AppConstants.FETCH_USER_KEY));
            startActivityForResult(intent, 166);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }


    public class MutualFriendAdapter extends RecyclerView.Adapter<MutualFriendAdapter.MyViewHolder> {

        private List<Contact> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private TextView text_view;
            private ImageView image_view;

            public MyViewHolder(View view) {
                super(view);
                text_view = (TextView) view.findViewById(R.id.text_view);
                image_view = (ImageView) view.findViewById(R.id.image_view);
            }
        }

        ;

        public MutualFriendAdapter(List<Contact> contacts) {
            this.horizontalList = contacts;
        }

        @Override
        public MutualFriendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_mutual_friend, parent, false);

            return new MutualFriendAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MutualFriendAdapter.MyViewHolder holder, final int position) {
            Log.v("hadippa", "onBindViewHolder");
            holder.text_view.setText(/*horizontalList.get(position).getPersonName() + */"Sample Name");
        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    public class InstagramAdapter extends RecyclerView.Adapter<InstagramAdapter.MyViewHolder> {

        private List<UserProfile.InstagramImagesBean.DataBean> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_photo;

            public MyViewHolder(View view) {
                super(view);
                iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            }
        }

        ;

        public InstagramAdapter(List<UserProfile.InstagramImagesBean.DataBean> contacts) {
            this.horizontalList = contacts;
        }

        @Override
        public InstagramAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_instagram_pic, parent, false);

            return new InstagramAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final InstagramAdapter.MyViewHolder holder, final int position) {


            Glide.with(ProfileActivity.this)

                    .load(horizontalList.get(position).getImages().getStandard_resolution().getUrl())
                    .centerCrop().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.iv_photo);

        }

        @Override
        public int getItemCount() {
            return horizontalList.size();
        }
    }

    //MY Profile
    private void fetchProfile() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("id", getIntent().getExtras().getString(AppConstants.FETCH_USER_KEY));

            Log.d("fetchProfile>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + getIntent().getExtras().getString(AppConstants.PROFILE_KEY), requestParams,
                new FetchProfile());
    }

    class FetchProfile extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

                showProgressDialog(ProfileActivity.this, "Please Wait");
            }
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

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("fetchProfile>>", "success" + response);
                Gson gson = new Gson();
                UserProfile userProfile = gson.fromJson(response, UserProfile.class);

                if (userProfile.isSuccess()) {

                    userBean = userProfile.getUser();



                    if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

                        setData(userBean,true);
                        activityBeanX = userProfile.getActivity();
                        tvActivityVal.setText("" + userProfile.getActivity_count());
                        tvApproaching2Val.setText("" + userProfile.getApproached_by_count());
                        tvApproaching3Val.setText("" + userProfile.getApproaching_count());
                        activityData = response;

                        if (userProfile.getInstagram_images() != null) {
                            if(new Gson().toJson(userProfile.getInstagram_images()).equals("")){
                            if (userProfile.getInstagram_images().getData().size() > 0) {

                               // llConnectInstagram.setVisibility(View.GONE);
                                rvRecentInstagram.setVisibility(View.VISIBLE);
                                InstagramAdapter instaAdapter = new InstagramAdapter(userProfile.getInstagram_images().getData());
                                rvRecentInstagram.setAdapter(instaAdapter);
                                if (userBean.getGender().equals("male")) {
                                    tvRecentInstagram.setText("His instagram photos");
                                } else {
                                    tvRecentInstagram.setText("Her instagram photos");
                                }
                                tvRecentInstagram.setVisibility(View.VISIBLE);
                            }
                        }
                        }else{
                            tvRecentInstagram.setVisibility(View.GONE);
                            //llConnectInstagram.setVisibility(View.VISIBLE);
                        }

                    } else {

                        setData(userBean,false);
                        activityBeanX = userProfile.getActivity();
                        tvActivityVal.setText("" + activityBeanX.size());
                        tvApproaching2Val.setText("" + userProfile.getApproached_by_count());
                        tvApproaching3Val.setText("" + userProfile.getApproaching_count());
                        tvActivityVal.setText("" + userProfile.getActivity_count());
                        activityData = response;


                        if (userProfile.getInstagram_images() != null) {
                            if (userProfile.getInstagram_images().getData().size() > 0) {

                                llConnectInstagram.setVisibility(View.GONE);
                                rvRecentInstagram.setVisibility(View.VISIBLE);
                                InstagramAdapter instaAdapter = new
                                        InstagramAdapter(userProfile.getInstagram_images().getData());
                                rvRecentInstagram.setAdapter(instaAdapter);
                                //  if(userBean.getGender().equals("male")){
                                tvRecentInstagram.setText("Instagram photos");
                              /*  }else{
                                    tvRecentInstagram.setText("Her instagram photos");
                                }*/
                                tvRecentInstagram.setVisibility(View.VISIBLE);
                            }else {
                                tvRecentInstagram.setVisibility(View.GONE);
                            }
                        }else {
                            //llConnectInstagram.setVisibility(View.VISIBLE);
                            tvRecentInstagram.setVisibility(View.GONE);
                        }
                    }


                }

                Log.d("fetchProfile>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }


    }


    public void setTextifNotEmpty(String data, CustomTextView customTextView) {
        if (data.length() > 0) {
            customTextView.setText(data);
        } else {
            //  customTextView.setVisibility(View.GONE);
        }
    }


    void setData(UserProfile.UserBean userBean,boolean updateSlider) {

        tvTitleName.setText(userBean.getFirst_name() + " " + userBean.getLast_name() + ", " + userBean.getAge());

        setTextifNotEmpty(userBean.getOccupation(), tvOccupation);
        setTextifNotEmpty(userBean.getCity(), tvCity);
        setTextifNotEmpty(userBean.getCompany(), tvCompany);
        setTextifNotEmpty(userBean.getLanuage_known(), tvLang);
        setTextifNotEmpty(userBean.getZodiac(), tvZodiac);


        if(updateSlider){
        url_maps.clear();
        if (userBean.getProfile_photos() != null) {

            if(userBean.getProfile_photos().size()==0){
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("")
                        .image(R.drawable.ic_user_avatar_default)
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(ProfileActivity.this);

                //add your extra information
           /* textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
*/
                slider.addSlider(textSliderView);
            }else{
            for (int i = 0; i < userBean.getProfile_photos().size(); i++) {

                if (!userBean.getProfile_photos().get(i).equals("")) {

                    TextSliderView textSliderView = new TextSliderView(this);
                    // initialize a SliderLayout
                    textSliderView
                            .description("")
                            .image(userBean.getProfile_photos().get(i).getImage())
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)

                            .setOnSliderClickListener(ProfileActivity.this);

                    //add your extra information
           /* textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
*/
                    slider.addSlider(textSliderView);
                }
            }}

            if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {
                slider.stopAutoCycle();
            } else {
                slider.startAutoCycle();
                user_relationship_status = userBean.getUser_relationship_status();
                setFollowUnfollow(user_relationship_status);
            }
        }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 239 || requestCode == 166) {
                    userBean = (UserProfile.UserBean) data.getExtras().getSerializable("data");
                    setData(userBean,true);
                } else if (resultCode == RESULT_OK && requestCode == 175) {

                    sourceUri = Uri.fromFile(new File(currentPhotoPath));
                    Log.d("ImageUri>>", sourceUri.toString());
                    UCrop.Options options = new UCrop.Options();
                    options.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
                    options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
                    options.setToolbarTitle(getString(R.string.app_name));
                    options.setActiveWidgetColor(getResources().getColor(R.color.colorPrimary));
                    UCrop.of(sourceUri, sourceUri).withOptions(options).withAspectRatio(1, 1)
                            .start(ProfileActivity.this, UCrop.REQUEST_CROP);

                } else if (resultCode == RESULT_OK && requestCode == 176) {



                } else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {

                    Uri resultUri = UCrop.getOutput(data);
                    if (resultUri != null && currentPhotoPath != null) {

                        Log.d("base64String>>", base64String);
                        currentPhotoPath = null;

                    }

                } else if (resultCode == RESULT_OK && requestCode == UCrop.RESULT_ERROR) {
                    Log.d("onActivityResult", "UCrop Error");
                }
            }
        } catch (Exception e) {
        }
    }



    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(((RelativeLayout) findViewById(R.id.activity_profile)), intent.getExtras().getString("messageData"));
        }
    };

    BroadcastReceiver instagramReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.d("instagramReceiver>>",intent.getExtras().getString("code"));
            retreiveAccessToken(intent.getExtras().getString("code"));

        }
    };
    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
        registerReceiver(instagramReceiver,new IntentFilter("INSTA_GRAM"));
    }


    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
        unregisterReceiver(instagramReceiver);
    }

    //MY Profile
    private void instagramUpdate(String insta_access_token) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("code", insta_access_token);

            Log.d("fetchProfile>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + "/instagram", requestParams,
                new UpdateInsta());
    }

    class UpdateInsta extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

          //  if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

                showProgressDialog(ProfileActivity.this, "Please Wait");
          //  }
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

            try {
                String response = new String(responseBody, "UTF-8");
                Log.d("fetchProfile>>", "success" + response);


                if(new JSONObject(response).getBoolean("success")){
                    ;
                }

                Log.d("fetchProfile>>", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }


        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

        }


    }

    private void retreiveAccessToken(String code) {
        new AccessTokenTask(code).execute();
    }

    public class AccessTokenTask extends AsyncTask<URL, Integer, Long> {

        ProgressDialog progressDlg;
        String code;
        JSONObject jsonUser = new JSONObject() ;
        JSONObject jsonObj =new JSONObject();
        public AccessTokenTask(String code) {
            this.code = code;

            progressDlg = new ProgressDialog(ProfileActivity.this);

            progressDlg.setMessage("Connecting to Instagram...");
        }

        protected void onCancelled() {
            progressDlg.cancel();
        }

        protected void onPreExecute() {
            progressDlg.show();
        }

        protected Long doInBackground(URL... urls) {
            long result = 0;

            try {
                List<NameValuePair> params = new ArrayList<NameValuePair>(5);

                params.add(new BasicNameValuePair("client_id", getResources().getString(R.string.instagram_client_key)));
                params.add(new BasicNameValuePair("client_secret", getResources().getString(R.string.instagram_client_secret)));
                params.add(new BasicNameValuePair("grant_type", "authorization_code"));
                params.add(new BasicNameValuePair("redirect_uri", getResources().getString(R.string.instagram_redirect_url)));
                params.add(new BasicNameValuePair("code", code));

                Log.d("insta>>",code);
                InstagramRequest request = new InstagramRequest();
                String response = request.post(Cons.ACCESS_TOKEN_URL, params);

                if (!response.equals("")) {
                    jsonObj = (JSONObject) new JSONTokener(response).nextValue();
                    jsonUser = jsonObj.getJSONObject("user");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        protected void onPostExecute(Long result) {
            progressDlg.dismiss();

            try {
                if (jsonObj.getString("access_token") != null) {

                    //Toast.makeText(ProfileActivity.this,"Access Token > "+jsonObj.getString("access_token"),Toast.LENGTH_LONG).show();
                    instagramUpdate(jsonObj.getString("access_token"));

                } else {


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //Block/Unblock
    private void block_Unblock(String type, String id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("blocked_id", id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Block_UnBlock());
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
    class Block_UnBlock extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

              showProgressDialog(ProfileActivity.this, "Please Wait");

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

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                Log.d("blockList??", "success" + response);
                if(jsonObject.getBoolean("success")){

                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("blockList??", "ssaa  success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
            Log.d("blockList??", "xzxxssuccess exc  >>" + error.toString());
        }

    }
}
