package com.hadippa.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.ChatMainList;
import com.hadippa.model.Contact;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ChatListActivity extends AppCompatActivity {

    private static final String TAG = ChatListActivity.class.getSimpleName();

    public static RecyclerView mRecyclerView;
    List<ChatMainList.ThreadsBean> contacts = new ArrayList<>();
    ProgressBar progressBar;
    CustomAdapter customAdapter;
    private ImageView imageBack;
    @BindView(R.id.ivPlus)
    ImageView ivPlus;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);

        sp = PreferenceManager.getDefaultSharedPreferences(ChatListActivity.this);
        editor = sp.edit();

       /* contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
        contacts.add(new Contact());
*/
        imageBack = (ImageView) findViewById(R.id.imageBack);
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

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                MyChat();

            }
        });


        swipeRefreshLayout.setDistanceToTriggerSync(50);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                swipeRefreshLayout.setEnabled(topRowVerticalPosition >= 0);


            }
        });



        ivPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ChatListActivity.this, ChatPlusActivity.class);

                startActivity(intent1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        LayoutInflater inflator;
        List<ChatMainList.ThreadsBean> alContacts;

        CustomAdapter(Context context, List<ChatMainList.ThreadsBean> alContacts) {

            inflator = LayoutInflater.from(context);
            this.alContacts = alContacts;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = inflator.inflate(R.layout.chat_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final ChatMainList.ThreadsBean contact = alContacts.get(position);

            //             * date : 2016-12-23 23:12:42.000000

          //  if(contact.getChat_type()==0){
                viewHolder.name.setText(contact.getSubject());
          //  }else{

         //   }

            viewHolder.lastMsg.setText(contact.getLast_msg());
            if(contact.getUpdated_at()!=null) {
                viewHolder.tvDate.setText(contact.getUpdated_at().split(" ")[1].substring(0, 5));
            }
            RequestManager requestManager = Glide.with(ChatListActivity.this);

            requestManager.load(contact.getProfile_photo_thumbnail()).error(R.drawable.place_holder).placeholder(R.drawable.place_holder).into(viewHolder.profileImage);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatListActivity.this,ChatActivity.class);
                    intent.putExtra("newChat",false);
                    intent.putExtra("userName",contact.getSubject());
                    intent.putExtra("threadId",contact.getId());
                    intent.putExtra("chatType",contact.getChat_type());
                    intent.putExtra("threadBean",contact);

                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {

            return alContacts.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Dummy
        private TextView name,lastMsg,tvDate;
        private RoundedImageView profileImage;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent1 = new Intent(ChatListActivity.this, ChatActivity.class);
                    intent1.putExtra("contact", contacts.get(getAdapterPosition()));
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            });

            name = (TextView) v.findViewById(R.id.name);
            lastMsg = (TextView) v.findViewById(R.id.lastMsg);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            profileImage = (RoundedImageView)v.findViewById(R.id.profileImage);
        }
    }

    private void MyChat() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));


            Log.d("c", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.MY_CHAT, requestParams,
                new GetMyChat());
    }

    class GetMyChat extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            //  AppConstants.showProgressDialog(Preference.this, "Please Wait");

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

                JSONObject jsonObject = new JSONObject(response);

                Gson gson = new Gson();
                ChatMainList chatMainList = gson.fromJson(response,ChatMainList.class);

                contacts = chatMainList.getThreads();
                Toast.makeText(ChatListActivity.this,""+contacts.size(),Toast.LENGTH_SHORT).show();
                if(jsonObject.getBoolean("success")) {
                    customAdapter = new CustomAdapter(ChatListActivity.this, chatMainList.getThreads());
                    mRecyclerView.setAdapter(customAdapter);
                }
                Log.d("mychat", "success" + response);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("mychat", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.d("mychat", "success exc  >>" + error.toString());
            //  AppConstants.showSnackBar(mainRel,"Try again!");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        MyChat();
    }
}
