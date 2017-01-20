package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.demo.AlbumStorageDirFactory;
import com.demo.BaseAlbumDirFactory;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.instagram.InstagramApp;
import com.hadippa.instagram.InstagramSession;
import com.hadippa.model.Contact;
import com.hadippa.model.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {

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
    @BindView(R.id.tvLangSub)
    CustomTextView tvLangSub;
    @BindView(R.id.tvRecentInstagram)
    CustomTextView tvRecentInstagram;
    @BindView(R.id.tvMutual)
    CustomTextView tvMutual;
    @BindView(R.id.vSep2)
    View vSep2;
    @BindView(R.id.vSep3)
    View vSep3;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    UserProfile userProfile1;
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
    InstagramApp instagramApp;
    InstagramSession instagramSession;

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

        instagramSession = new InstagramSession(ProfileActivity.this);

        instagramApp = new InstagramApp(ProfileActivity.this, AppConstants.INSTA_CLIENT_ID,
                AppConstants.INSTA_CLIENT_SECRET, AppConstants.INSTA_CALLBACK_URL);
        instagramApp.setListener(new InstagramApp.OAuthAuthenticationListener() {

            @Override
            public void onSuccess() {
// tvSummary.setText("Connected as " + mApp.getUserName());
// userInfoHashmap = mApp.

                Toast.makeText(ProfileActivity.this, instagramApp.getUserName() + " >> " + instagramSession.getAccessToken(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String error) {
                Toast.makeText(ProfileActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });

        connectInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                instagramApp.authorize();

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
                    return;
                }
                if (tvActivityVal.getText().toString().equals("0")) {

                } else {
                    Intent intent = new Intent(ProfileActivity.this, ActivityThingsActivity.class);
                    intent.putExtra("data", activityData);

                    startActivity(intent);

                }
            }
        });


        llFollowUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_relationship_status.equals("Connected")
                        || user_relationship_status.equals("Following")) {

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
       /* findViewById(R.id.ivProfilePic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

                    if (checkPermission()) {
                    selectImage();
                } else {
                    requestPermission();
                }
            }}
        });
*/

        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(ProfileActivity.this, LinearLayoutManager.HORIZONTAL, false);
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

        //Sample arraylist..
        List<Contact> instaContacts = new ArrayList<>();
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());
        instaContacts.add(new Contact());


        InstagramAdapter instaAdapter = new InstagramAdapter(instaContacts);
        //rvRecentInstagram.setAdapter(instaAdapter);

        /*slider.addOnPageChangeListener(this);
        ListView l = (ListView)findViewById(R.id.transformers);
        l.setAdapter(new TransformerAdapter(this));
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mDemoSlider.setPresetTransformer(((TextView) view).getText().toString());
                Toast.makeText(MainActivity.this, ((TextView) view).getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    private void connectOrDisconnectUser() {
        if (instagramApp.hasAccessToken()) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(
                    ProfileActivity.this);
            builder.setMessage("Disconnect from Instagram?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    instagramApp.resetAccessToken();
// btnConnect.setVisibility(View.VISIBLE);

// tvSummary.setText("Not connected");
                                }
                            })
                    .setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int id) {
                                    dialog.cancel();
                                }
                            });
            final AlertDialog alert = builder.create();
            alert.show();
        } else {
            instagramApp.authorize();
        }
    }

    String user_relationship_status = "";

    void setFollowUnfollow(String user_relationship_status) {
        if (user_relationship_status.equals("Connected")
                || user_relationship_status.equals("Following")) {

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

            //   AppConstants.showProgressDialog(getActivity(), "Please Wait");

        }


        @Override
        public void onFinish() {
            //AppConstants.dismissDialog();
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

                    user_relationship_status = jsonObject.getString("status");
                    setFollowUnfollow(user_relationship_status);


                } else {
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
            connectInstagram.setVisibility(View.VISIBLE);
            llFollowUnfollow.setVisibility(View.GONE);
            rvMutualFriend.setVisibility(View.GONE);
            tvRecentInstagram.setVisibility(View.GONE);
            tvMutual.setVisibility(View.GONE);
            rvRecentInstagram.setVisibility(View.GONE);
            vSep2.setVisibility(View.GONE);
            vSep3.setVisibility(View.GONE);

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
                userBean.setProfile_photo_1(jsonObject.getString("profile_photo_1"));
                userBean.setProfile_photo_2(jsonObject.getString("profile_photo_2"));
                userBean.setProfile_photo_3(jsonObject.getString("profile_photo_3"));
                userBean.setProfile_photo_4(jsonObject.getString("profile_photo_4"));
                userBean.setProfile_photo_5(jsonObject.getString("profile_photo_5"));
                userBean.setPrivate_account(jsonObject.getInt("private_account"));
                userBean.setDob(jsonObject.getString("dob"));
                userBean.setAge(jsonObject.getInt("age"));

                List<String> pp = new ArrayList<>();

                if (jsonObject.has("profile_photos")) {
                    for (int i = 0; i < jsonObject.getJSONArray("profile_photos").length(); i++) {

                        if (!jsonObject.getJSONArray("profile_photos").getString(i).equals("")) {
                            pp.add(jsonObject.getJSONArray("profile_photos").getString(i));
                        }
                    }
                }
                userBean.setProfile_photos(pp);
                setData(userBean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            llFollowUnfollow.setVisibility(View.VISIBLE);
            ivEdit.setVisibility(View.GONE);
            connectInstagram.setVisibility(View.GONE);
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

        private List<Contact> horizontalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            private ImageView iv_photo;

            public MyViewHolder(View view) {
                super(view);
                iv_photo = (ImageView) view.findViewById(R.id.iv_photo);
            }
        }

        ;

        public InstagramAdapter(List<Contact> contacts) {
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

                AppConstants.showProgressDialog(ProfileActivity.this, "Please Wait");
            }
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
                Log.d("fetchProfile>>", "success" + response);
                Gson gson = new Gson();
                UserProfile userProfile = gson.fromJson(response, UserProfile.class);

                if (userProfile.isSuccess()) {

                    userBean = userProfile.getUser();
                    setData(userBean);
                    userProfile1 = userProfile;
                    if (!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {
                        tvActivityVal.setText("" + userProfile.getActivity_count());

                    } else {
                        activityBeanX = userProfile.getActivity();
                        tvActivityVal.setText("" + activityBeanX.size());

                        activityData = response;
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


    void setData(UserProfile.UserBean userBean) {

        tvTitleName.setText(userBean.getFirst_name() + " " + userBean.getLast_name() + ", " + userBean.getAge());

        setTextifNotEmpty(userBean.getOccupation(), tvOccupation);
        setTextifNotEmpty(userBean.getCity(), tvCity);
        setTextifNotEmpty(userBean.getCompany(), tvCompany);
        setTextifNotEmpty(userBean.getLanuage_known(), tvLangSub);
        setTextifNotEmpty(userBean.getZodiac(), tvZodiac);



        slider.removeAllSliders();
        url_maps.clear();
        if (userBean.getProfile_photos() != null) {
            for (int i = 0; i < userBean.getProfile_photos().size(); i++) {

                if (!userBean.getProfile_photos().get(i).equals("")) {

                    TextSliderView textSliderView = new TextSliderView(this);
                    // initialize a SliderLayout
                    textSliderView
                            .description("")
                            .image(userBean.getProfile_photos().get(i))
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                            .setOnSliderClickListener(ProfileActivity.this);

                    //add your extra information
           /* textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);
*/
                    slider.addSlider(textSliderView);
                }
            }

            if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {
                slider.stopAutoCycle();
            }
        }
     /*   url_maps.put("a", userBean.getProfile_photo());
        if (userBean.getProfile_photo_1() != null && !(userBean.getProfile_photo_1()).equals("")) {
            url_maps.put("a", userBean.getProfile_photo_1());
        }
        if (userBean.getProfile_photo_2() != null && !(userBean.getProfile_photo_2()).equals("")) {
            url_maps.put("a", userBean.getProfile_photo_2());
        }
        if (userBean.getProfile_photo_3() != null && !(userBean.getProfile_photo_3()).equals("")) {
            url_maps.put("a", userBean.getProfile_photo_3());
        }
        if (userBean.getProfile_photo_4() != null && !(userBean.getProfile_photo_4()).equals("")) {
            url_maps.put("a", userBean.getProfile_photo_4());
        }
        if (userBean.getProfile_photo_5() != null && !(userBean.getProfile_photo_5()).equals("")) {
            url_maps.put("a", userBean.getProfile_photo_5());
        }

        for (String name : url_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description("")
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(ProfileActivity.this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            slider.addSlider(textSliderView);
        }
*/
        if (getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {


        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        dddd();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == 239 || requestCode == 166) {
                    userBean = (UserProfile.UserBean) data.getExtras().getSerializable("data");
                    setData(userBean);
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
