package com.hadippa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.Contact;
import com.hadippa.model.FollowingModel;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ChatPlusActivity extends AppCompatActivity {


    private static final String TAG = ChatPlusActivity.class.getSimpleName();

    public static RecyclerView mRecyclerView;
    ProgressBar progressBar;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_plus);

        ButterKnife.bind(this);

      sp = PreferenceManager.getDefaultSharedPreferences(ChatPlusActivity.this);
        editor = sp.edit();

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

       fetchFollowers();

    }



    ArrayList<FollowingModel.FollowingBean> followersFollowings = new ArrayList<>();

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RelativeLayout relMain;

    public CustomAdapter customAdapter;

    ArrayList<String> selectedList = new ArrayList<>();


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> implements Filterable {

        private List<FollowingModel.FollowingBean> originalData = null;
        private List<FollowingModel.FollowingBean> filteredData = null;
        private LayoutInflater mInflater;
        private ItemFilter mFilter = new ItemFilter();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_invite_people, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
            //Log.d(TAG, "Element " + position + " set.");
            final FollowingModel.FollowingBean followers_following = followersFollowings.get(position);

            if (followers_following != null && followers_following.getFollowed() != null) {
                viewHolder.tvName.setText(followers_following.getFollowed().getFirst_name() + " " +
                        followers_following.getFollowed().getLast_name());

                Glide.with(ChatPlusActivity.this)
                        .load(followers_following.getFollowed().getProfile_photo())
                        .into(viewHolder.image_view);

                if (selectedList.contains(String.valueOf(followersFollowings.get(position).getFollowed_id()))) {

                    viewHolder.rbButton.setSelected(true);

                } else {
                    viewHolder.rbButton.setSelected(false);

                }

            }

            viewHolder.rbButton.setVisibility(View.GONE);

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{

                        Intent  intent = new Intent(ChatPlusActivity.this,ChatActivity.class);
                        intent.putExtra("userid",String.valueOf(followers_following.getFollowed().getId()));
                        intent.putExtra("userName",followers_following.getFollowed().getFirst_name());
                        intent.putExtra("newChat",true);
                        startActivity(intent);

                    } catch (Exception e) {

                        Toast.makeText(ChatPlusActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }



       /* private void removePeople(PeopleModel peopleModel){

            if(filteredData != null && filteredData.size()>0){
                for(int i=0;i<filteredData.size();i++){
                    if(filteredData.get(i).getId().equalsIgnoreCase(peopleModel.getId())){
                        filteredData.get(i).setChecked(false);
                    }
                }
            }
            notifyDataSetChanged();
        }*/

        public FollowingModel.FollowingBean getItem(int position) {
            return followersFollowings.get(position);
        }

        @Override
        public int getItemCount() {

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

                final List<FollowingModel.FollowingBean> list = originalData;

                int count = list.size();
                final ArrayList<FollowingModel.FollowingBean> nlist = new ArrayList<>(count);

                String filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = list.get(i).getFollowed().getFirst_name();
                    if (filterableString.toLowerCase().contains(filterString)
                            || filterableString.toLowerCase().contains(list.get(i).getFollowed().getLast_name())) {
                        nlist.add(list.get(i));
                    }
                }

                results.values = nlist;
                results.count = nlist.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredData = (ArrayList<FollowingModel.FollowingBean>) results.values;
                notifyDataSetChanged();
            }
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView image_view;
        CustomTextView name;
        RelativeLayout rlContainer;
        ImageView rbButton;
        TextView tvName;

        public ViewHolder(final View v) {
            super(v);

            name = (CustomTextView) v.findViewById(R.id.text_view);
            image_view = (RoundedImageView) v.findViewById(R.id.image_view);
            tvName = (TextView) v.findViewById(R.id.tvName);
            rlContainer = (RelativeLayout) v.findViewById(R.id.rlContainer);
            rbButton = (ImageView) v.findViewById(R.id.rbButton);
        }
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

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CONNECTION_FOLLOWING, requestParams,
                new GetFollowers());

    }

    class GetFollowers extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  progressBar.setVisibility(View.VISIBLE);
            AppConstants.showProgressDialog(ChatPlusActivity.this, "Please Wait");

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

                    if (jsonObject.getJSONArray("following").length() == 0) {
                       // AppConstants.showSnackBar(relMain, "No Followings yet.");
                    } else {
                        Type listType = new TypeToken<ArrayList<FollowingModel.FollowingBean>>() {
                        }.getType();
                        GsonBuilder gsonBuilder = new GsonBuilder();

                        Gson gson = gsonBuilder.create();
                        followersFollowings = new ArrayList<>();
                        followersFollowings = (gson.fromJson(String.valueOf(jsonObject.getJSONArray("following")), listType));

                    }

                    editor.putString("myFollowings", jsonObject.getJSONArray("following").toString());
                    editor.commit();





                    customAdapter = new CustomAdapter();
                    mRecyclerView.setAdapter(customAdapter);


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

//            AppConstants.showSnackBar(relMain, "Could not refresh feed");
        }

    }

}
