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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

            viewHolder.name.setText(contact.getSubject());


            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ChatListActivity.this,ChatActivity.class);
                    intent.putExtra("newChat",false);
                    intent.putExtra("userName",contact.getSubject());
                    intent.putExtra("threadId",contact.getId());
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
        private TextView name;
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
            profileImage = (RoundedImageView)v.findViewById(R.id.profileImage);
        }
    }

    private void MyChat() {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));


            Log.d("mychat>>", requestParams.toString());
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
