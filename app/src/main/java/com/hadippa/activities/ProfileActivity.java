package com.hadippa.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.APIClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.SquareImageView;
import com.hadippa.fragments.main_screen.DoublePeople;
import com.hadippa.fragments.main_screen.People;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.hadippa.model.Contact;
import com.hadippa.model.UserProfile;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.rvMutualFriend) RecyclerView rvMutualFriend;
    @BindView(R.id.rvRecentInstagram) RecyclerView rvRecentInstagram;
    @BindView(R.id.ivEdit) ImageView ivEdit;
    @BindView(R.id.tvTitleName) CustomTextView tvTitleName;
    @BindView(R.id.ivChat) ImageView ivChat;
    @BindView(R.id.ivMore) ImageView ivMore;
    @BindView(R.id.ivProfilePic) SquareImageView ivProfilePic;
    @BindView(R.id.tvActivityVal) CustomTextView tvActivityVal;
    @BindView(R.id.tvApproaching2Val) CustomTextView tvApproaching2Val;
    @BindView(R.id.llFollowUnfollow) LinearLayout llFollowUnfollow;
    @BindView(R.id.ivFollowUnfollow) ImageView ivFollowUnfollow;
    @BindView(R.id.tvFollowUnfollow) CustomTextView tvFollowUnfollow;
    @BindView(R.id.tvOccupation) CustomTextView tvOccupation;
    @BindView(R.id.tvCompany) CustomTextView tvCompany;
    @BindView(R.id.tvCity) CustomTextView tvCity;
    @BindView(R.id.tvZodiac) CustomTextView tvZodiac;
    @BindView(R.id.tvLangSub) CustomTextView tvLangSub;
    @BindView(R.id.tvRecentInstagram) CustomTextView tvRecentInstagram;
    @BindView(R.id.tvMutual) CustomTextView tvMutual;
    @BindView(R.id.vSep2) View vSep2;
    @BindView(R.id.vSep3) View vSep3;
    SharedPreferences sp;

    UserProfile.UserBean userBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        sp = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);


        if(getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)){
            llFollowUnfollow.setVisibility(View.GONE);
            rvMutualFriend.setVisibility(View.GONE);
            tvRecentInstagram.setVisibility(View.GONE);
            tvMutual.setVisibility(View.GONE);
            rvRecentInstagram.setVisibility(View.GONE);
            vSep2.setVisibility(View.GONE);
            vSep3.setVisibility(View.GONE);

            try {
                JSONObject jsonObject = new JSONObject(sp.getString("userData",""));
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
                userBean.setAge(0);

                setData(userBean);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            ivEdit.setVisibility(View.GONE);
        }
        findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                intent.putExtra("data",userBean);
              //  startActivity(intent);
                startActivityForResult(intent,239);
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
                Intent intent = new Intent(ProfileActivity.this,ActivityThingsActivity.class);
                startActivity(intent);
            }
        });


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

        fetchProfile();
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

            if(!getIntent().getExtras().getString(AppConstants.PROFILE_KEY).equals(AppConstants.MY_PROFILE)) {

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
                Gson gson = new Gson();
                UserProfile userProfile = gson.fromJson(response,UserProfile.class);

                if(userProfile.isSuccess()){

                    userBean = userProfile.getUser();
                    setData(userBean);

                }

                Log.d("fetchProfile>>", "success" + response);
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



    public void setTextifNotEmpty(String data,CustomTextView customTextView){
        if(data.length() > 0){
            customTextView.setText(data);
        }else{
          //  customTextView.setVisibility(View.GONE);
        }
    }


    void setData(UserProfile.UserBean userBean){

        tvTitleName.setText(userBean.getFirst_name()+" "+userBean.getLast_name()+", "+userBean.getAge());

        setTextifNotEmpty(userBean.getOccupation(),tvOccupation);
        setTextifNotEmpty(userBean.getCity(),tvCity);
        setTextifNotEmpty(userBean.getCompany(),tvCompany);
        setTextifNotEmpty(userBean.getLanuage_known(),tvLangSub);
        setTextifNotEmpty(userBean.getZodiac(),tvZodiac);

        RequestManager requestManager = Glide.with(ProfileActivity.this);
        requestManager.load(userBean.getProfile_photo())
                .placeholder(R.color.c_efefef)
                .error(R.color.c_efefef)
                .into(ivProfilePic);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 239){
                userBean = (UserProfile.UserBean) data.getExtras().getSerializable("data");
                setData(userBean);
            }
        }
    }
}
