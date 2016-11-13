package com.hadippa.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.fragments.main_screen.People;
import com.hadippa.model.PeopleModel;

import java.util.ArrayList;
import java.util.List;
import com.hadippa.fragments.following.Followers;
import com.hadippa.model.FollowersModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class InviteToJoinActivity extends AppCompatActivity {

    RecyclerView myRecycler;
    ImageView imageBack;
    private List<PeopleModel> peopleModels;
    private CustomAdapter adapter;
    private EditText etSearch;
    ArrayList<FollowersModel> followersFollowings = new ArrayList<>();

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RelativeLayout relMain;

    public CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_to_join);

        peopleModels = new ArrayList<>();
        peopleModels.add(new PeopleModel("Palak Darji"));
        peopleModels.add(new PeopleModel("Kat Middleton"));
        peopleModels.add(new PeopleModel("Kareena Kapoor"));
        peopleModels.add(new PeopleModel("Kartick Mistry"));
        peopleModels.add(new PeopleModel("Angelina Joly"));
        peopleModels.add(new PeopleModel("Sania Mirza"));
        peopleModels.add(new PeopleModel("David Backham"));
        peopleModels.add(new PeopleModel("Shreya Ghosal"));
        peopleModels.add(new PeopleModel("Shilpa Shetty"));
        peopleModels.add(new PeopleModel("Tina Gupta"));
        peopleModels.add(new PeopleModel("Katrina Kaif"));
        peopleModels.add(new PeopleModel("Lonewolf Sniper"));
        peopleModels.add(new PeopleModel("CarryMinati"));

        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        imageBack = (ImageView)findViewById(R.id.imageBack);

        relMain = (RelativeLayout) findViewById(R.id.activity_invite_to_join);

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedId", CreateActivityActvity.selectedList);

                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        sp = PreferenceManager.getDefaultSharedPreferences(InviteToJoinActivity.this);
        editor = sp.edit();


        myRecycler = (RecyclerView) findViewById(R.id.myRecycler);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(mLayoutManager);

        fetchFollowers();
//        myRecycler.setAdapter(new CustomAdapter());
        adapter = new CustomAdapter(peopleModels);
        myRecycler.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Log.d("selectedId >> 4", CreateActivityActvity.selectedList.toString());
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedId", CreateActivityActvity.selectedList);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
        overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);

    }

    private void fetchFollowers() {

        followersFollowings.clear();
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CONNECTION_FOLLOWERS, requestParams,
                new GetFollowers());

    }

    class GetFollowers extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  progressBar.setVisibility(View.VISIBLE);
            AppConstants.showProgressDialog(InviteToJoinActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            AppConstants.dismissDialog();
            // progressBar.setVisibility(View.GONE);
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
                if (jsonObject.getBoolean("success")) {

                    if (jsonObject.getJSONArray("followers").length() == 0) {
                        AppConstants.showSnackBar(relMain, "No followers yet.");
                    } else {
                        Type listType = new TypeToken<ArrayList<FollowersModel>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("followers")), listType));

                    }

                    editor.putString("myFollowers", jsonObject.getJSONArray("followers").toString());
                    editor.commit();

                    customAdapter = new CustomAdapter();
                    myRecycler.setAdapter(customAdapter);
                } else {
                    AppConstants.showSnackBar(relMain, "Could not refresh feed");
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("async", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            AppConstants.showSnackBar(relMain, "Could not refresh feed");
        }

    }

    void setPreviousData() {

        followersFollowings.clear();
        if (sp.getString("myFollowers", "").equals("")) {


        } else {
            Type listType = new TypeToken<ArrayList<FollowersModel>>() {
            }.getType();
            GsonBuilder gsonBuilder = new GsonBuilder();

            Gson gson = gsonBuilder.create();
            followersFollowings.addAll((ArrayList<FollowersModel>) gson.fromJson(String.valueOf(sp.getString("myFollowers", "")), listType));

            customAdapter = new CustomAdapter();
            myRecycler.setAdapter(customAdapter);
        }

    }


    public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {
    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        private static final String TAG = "CustomAdapter";

        private List<PeopleModel> originalData = null;
        private List<PeopleModel> filteredData = null;
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        CustomAdapter(List<PeopleModel> data){
            this.filteredData = data ;
            this.originalData = data ;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_invite_people, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            Log.d(TAG, "Element " + position + " set.");
            final FollowersModel followers_following = followersFollowings.get(position);

            final PeopleModel peopleModel = filteredData.get(position);
            Log.d(TAG, "Element " + position + " set." + peopleModel.isChecked());

            if(peopleModel.isChecked()){
                viewHolder.rbButton.setPressed(true);
            }
            else{
                viewHolder.rbButton.setPressed(false);
            }

            viewHolder.rlContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<filteredData.size();i++){
                        if(filteredData.get(i).getFirst_name().
                                equalsIgnoreCase(filteredData.get(position).getFirst_name())){
                            PeopleModel people = filteredData.get(i);
                            people.setChecked(!people.isChecked());
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            viewHolder.rbButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    for(int i=0;i<filteredData.size();i++){
                        if(filteredData.get(i).getFirst_name().
                                equalsIgnoreCase(filteredData.get(position).getFirst_name())){
                            PeopleModel people = filteredData.get(i);
                            people.setChecked(!people.isChecked());
                        }
                    }
                    notifyDataSetChanged();
                }
            });

            viewHolder.tvName.setText(""+ peopleModel.getFirst_name());
            if (followers_following != null && followers_following.getFollower() != null) {
                viewHolder.name.setText(followers_following.getFollower().getFirst_name() + " " +
                        followers_following.getFollower().getLast_name());

                Glide.with(InviteToJoinActivity.this)
                        .load(followers_following.getFollower().getProfile_photo())
                        .into(viewHolder.image_view);

                boolean found = false;
                for (int i = 0; i < CreateActivityActvity.selectedList.size(); i++) {

                    if (CreateActivityActvity.selectedList.get(i).equals(followersFollowings.get(position).getId())) {

                        found = true;
                        viewHolder.rbButton.setChecked(true);
                    }

                }


                if(!found) {
                    viewHolder.rbButton.setChecked(false);

                }


            }

            viewHolder.image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {




                    boolean found = false;
                        for (int i = 0; i < CreateActivityActvity.selectedList.size(); i++) {

                            if (CreateActivityActvity.selectedList.get(i).equals(followersFollowings.get(position).getId())) {
                                CreateActivityActvity.selectedList.remove(i);
                                found = true;
                                viewHolder.rbButton.setChecked(false);
                                break;
                            }

                        }


                    if(!found) {
                        viewHolder.rbButton.setChecked(true);
                        CreateActivityActvity.selectedList.add(followersFollowings.get(position).getId());
                    }



                }
            });


        }

        public FollowersModel getItem(int position) {
            return followersFollowings.get(position);
        }

        @Override
        public int getItemCount() {

            return filteredData.size();
            return followersFollowings.size();
        }

        public Filter getFilter() {
            return mFilter;
        }

        private class ItemFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();

                FilterResults results = new FilterResults();

                final List<PeopleModel> list = originalData;

                int count = list.size();
                final ArrayList<PeopleModel> nlist = new ArrayList<>(count);

                String filterableString ;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getFirst_name();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<PeopleModel>) results.values;
                notifyDataSetChanged();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(adapter != null) adapter.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image_view;
        CustomTextView name;
        RadioButton rbButton;

        RelativeLayout rlContainer;
        RadioButton rbButton;
        TextView tvName;

        public ViewHolder(final View v) {
            super(v);

            //  rbButton = (RadioButton)v.findViewById(R.id.rbButton);
            name = (CustomTextView) v.findViewById(R.id.text_view);
            image_view = (RoundedImageView) v.findViewById(R.id.image_view);
            rbButton = (RadioButton) v.findViewById(R.id.rbButton);
            tvName = (TextView) v.findViewById(R.id.tvName);
            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
            rbButton = (RadioButton) v.findViewById(R.id.rbButton);


        }

    }

}
