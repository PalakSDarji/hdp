package com.hadippa.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.IntegerRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.APIClass;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.database.ChatDBHelper;
import com.hadippa.database.HadippaDatabase;
import com.hadippa.model.ChatMainList;
import com.hadippa.model.Contact;
import com.hadippa.model.FollowingModel;
import com.hadippa.model.Message;
import com.hadippa.model.MessageModel;
import com.hadippa.utils.Constants;
import com.hadippa.utils.PreferencesHelper;
import com.hadippa.utils.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ChatActivity extends AppCompatActivity {

    public static RecyclerView mRecyclerView;
    List<MessageModel.ThreadBean.MessagesBean> alMessages = new ArrayList<>();
    ProgressBar progressBar;
    private ImageView imageBack;

    private EditText etChat;

    boolean chatInitiated = false;
    @BindView(R.id.ivMore)
    ImageView ivMore;
    @BindView(R.id.ivMoreDetail)
    ImageView ivMoreDetail;
    @BindView(R.id.rlGroupDetail)
    RelativeLayout rlGroupDetail;

    ChatDBHelper chatDBHelper;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    public static int threadId = -1;

    ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        chatDBHelper = new ChatDBHelper(this);

        getWindow().setBackgroundDrawableResource(R.drawable.chat_bg);
        sp = PreferenceManager.getDefaultSharedPreferences(ChatActivity.this);
        editor = sp.edit();

        etChat = (EditText) findViewById(R.id.etChat);

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
        // mLayoutManager.setReverseLayout(true);
        mRecyclerView.setLayoutManager(mLayoutManager);


        ((CustomTextView) findViewById(R.id.tvHeader)).setText(getIntent().getExtras().getString("userName"));

        etChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chatAdapter != null) {
                    if (chatAdapter.getItemCount() > 0) {

                        mRecyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);

                    }
                }
            }
        });

        findViewById(R.id.ivMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ChatMainList.ThreadsBean chatMainList = (ChatMainList.ThreadsBean)getIntent().getExtras().getSerializable("threadBean");
                Intent intent  = new Intent(ChatActivity.this,GroupPeopleActivity.class);
                intent.putExtra("threadBean",chatMainList);
                startActivity(intent);
            }
        });
        findViewById(R.id.tvSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etChat.getText().toString().trim().length() > 0) {
                    //  sendNewMessage();
                    int tempID = (int) new Date().getTime() / 1000;

                    if (getIntent().getExtras().getBoolean("newChat")) {

                        if (!chatInitiated) {
                            createChat(tempID, etChat.getText().toString(), getIntent().getExtras().getString("userid"));
                        } else {
                            chatInitiated = true;
                            updateMessage(tempID, String.valueOf(threadId), etChat.getText().toString());
                        }

                    } else {
                        chatInitiated = true;
                        updateMessage(tempID, String.valueOf(getIntent().getExtras().getInt("threadId")),
                                etChat.getText().toString());
                    }
                }
            }
        });


        if (getIntent().getExtras().getBoolean("newChat")) {
            chatAdapter = new ChatAdapter(-1);
            mRecyclerView.setAdapter(chatAdapter);

        } else {
            chatAdapter = new ChatAdapter(getIntent().getExtras().getInt("threadId"));
            mRecyclerView.setAdapter(chatAdapter);
            mRecyclerView.scrollToPosition(chatAdapter.getItemCount() - 1);
            //alMessages = chatDBHelper.fetchMessagesFromThread();
        }


        if(getIntent().getExtras().getInt("chatType") == 1){
            ivMore.setVisibility(View.GONE);
            ivMoreDetail.setVisibility(View.GONE
            );
        }
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();

        //mRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
    }

    private void showChat(String thread_id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("thread_id", thread_id);
            requestParams.add("msg_type", AppConstants.MESSAGE_TYPE_TEXT);


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CHAT_SHOW, requestParams,
                new ShowChat());
    }

    class ShowChat extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            alMessages.clear();
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
                MessageModel messageModel = gson.fromJson(jsonObject.toString(), MessageModel.class);
                if (jsonObject.getBoolean("success")) {

                    Log.d("showChat>>", response);
                    //post json stored g\here

                 /*   alMessages = messageModel.getThread().getMessages();


                    mRecyclerView.setAdapter(new ChatAdapter(alMessages));

                    mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);
*/

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

    private void updateMessage(int tempID, String thread_id, String message) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("temp_msg_id", String.valueOf(tempID));
            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("thread_id", thread_id);
            requestParams.add("message", message);
            requestParams.add("msg_type", AppConstants.MESSAGE_TYPE_TEXT);

            JSONObject jsonObject = new JSONObject(sp.getString("userData", ""));

            MessageModel.ThreadBean.MessagesBean.UserBean userbean = new MessageModel.ThreadBean.MessagesBean.UserBean();
            userbean.setFirst_name(jsonObject.getString("first_name"));
            userbean.setLast_name(jsonObject.getString("last_name"));
            userbean.setProfile_photo(jsonObject.getString("profile_photo"));
            userbean.setId(jsonObject.getInt("id"));

            MessageModel.ThreadBean.MessagesBean messagesBean = new MessageModel.ThreadBean.MessagesBean();
            messagesBean.setBody(message);
            messagesBean.setId(tempID);
            messagesBean.setUser(userbean);
            messagesBean.setMessage_type("");
            messagesBean.setCreated_at("");
            messagesBean.setUpdated_at("");
            messagesBean.setThread_id(Integer.parseInt(thread_id));

            chatDBHelper.insertMessage(messagesBean, 1);
            chatAdapter.addData(messagesBean);

            etChat.setText("");


            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CHAT_NEW_MESSAGE, requestParams,
                new UpdateMessage());
    }

    class UpdateMessage extends AsyncHttpResponseHandler {

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
            Log.d("updateDonut", String.format("Progress %d from %d (%2.0f%%)",
                    bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

        }


        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String response = new String(responseBody, "UTF-8");
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {

                    Log.d("response>>", response);

                    MessageModel.ThreadBean.MessagesBean messagesBean = new Gson().fromJson(jsonObject.getString("msg_data"), MessageModel.ThreadBean.MessagesBean.class);

                    chatAdapter.thread_id = messagesBean.getThread_id();

                    chatDBHelper.updateTempMessage(jsonObject.getInt("temp_msg_id"), messagesBean, 1);
                    chatAdapter.updateData(jsonObject.getInt("temp_msg_id"), messagesBean);

                    //  showChat(String.valueOf(getIntent().getExtras().getInt("threadId")));
                    Log.d("response>>", response);
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


    public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<MessageModel.ThreadBean.MessagesBean> items = new ArrayList<>();
        int thread_id;

        public ChatAdapter(int thread_id) {
            this.thread_id = thread_id;

            if (thread_id != -1) {
                items.addAll(chatDBHelper.fetchMessagesFromThread(thread_id));
            }
        }


        public void addData(MessageModel.ThreadBean.MessagesBean messagesBean) {


            if (messagesBean.getThread_id() == -1) {

                items.add(messagesBean);
                notifyItemInserted(getItemCount() - 1);
                mRecyclerView.scrollToPosition(getItemCount() - 1);

            } else if (messagesBean.getThread_id() == thread_id) {

                chatDBHelper.updateMessage(thread_id);
                items.add(messagesBean);
                notifyItemInserted(getItemCount() - 1);
                mRecyclerView.scrollToPosition(getItemCount() - 1);

            } else {
                //chatDBHelper.insertMessage(messagesBean, 0);
            }
        }


        public void updateData(int tempId, MessageModel.ThreadBean.MessagesBean messagesBean) {

            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getId() == tempId) {
                    items.set(i, messagesBean);
                    notifyItemChanged(i);
                    break;
                }
            }
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case 0:
                    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_left, parent, false);
                    return new ViewHolder(v);
                case 1:
                    View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_right, parent, false);
                    return new ViewHolder(v1);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            MessageModel.ThreadBean.MessagesBean item = items.get(position);
            holder.tvText.setText(item.getBody());
            holder.tvName.setText(item.getUser().getFirst_name());

            if (item.getCreated_at() == null || item.getCreated_at().equals("")) {
                holder.tvDate.setVisibility(View.GONE);
            } else {
                holder.tvDate.setVisibility(View.VISIBLE);
                holder.tvDate.setText(AppConstants.formatDate(item.getCreated_at(), "yyyy-mm-dd hh:mm:ss", "hh:mm"));
            }
            holder.itemView.setTag(item);


        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public TextView tvText;
            TextView tvName;
            CustomTextView tvDate;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                tvText = (TextView) itemView.findViewById(R.id.tvText);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);
            }
        }

        public void setData(List<MessageModel.ThreadBean.MessagesBean> messageList) {
            items.clear();
            items.addAll(messageList);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(sp.getString("userData", ""));
                if (items.get(position).getUser_id() == jsonObject.getInt("id")) {
                    return 1;
                } else {
                    return 0;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return 0;
        }
    }


    private void createChat(int tempID, String msg, String receiver) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("temp_msg_id", String.valueOf(tempID));
            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("message", msg);
            requestParams.add("receiver_id", receiver);
            requestParams.add("msg_type", AppConstants.MESSAGE_TYPE_TEXT);

            JSONObject jsonObject = new JSONObject(sp.getString("userData", ""));

            MessageModel.ThreadBean.MessagesBean.UserBean userbean = new MessageModel.ThreadBean.MessagesBean.UserBean();
            userbean.setFirst_name(jsonObject.getString("first_name"));
            userbean.setLast_name(jsonObject.getString("last_name"));
            userbean.setProfile_photo(jsonObject.getString("profile_photo"));
            userbean.setId(jsonObject.getInt("id"));

            MessageModel.ThreadBean.MessagesBean messagesBean = new MessageModel.ThreadBean.MessagesBean();
            messagesBean.setBody(msg);
            messagesBean.setId(tempID);
            messagesBean.setUser(userbean);
            messagesBean.setMessage_type("");
            messagesBean.setCreated_at("");
            messagesBean.setUpdated_at("");
            messagesBean.setThread_id(-1);
            chatDBHelper.insertMessage(messagesBean, 1);
            chatAdapter.addData(messagesBean);

            etChat.setText("");

            Log.d("request>>", requestParams.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + AppConstants.CHAT_CREATE, requestParams,
                new CreateUserChat());
    }

    class CreateUserChat extends AsyncHttpResponseHandler {

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
                Log.d("chat>>", "success" + response);
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.getBoolean("success")) {

                    chatInitiated = true;
                    threadId = jsonObject.getInt("thred_id");
                    MessageModel.ThreadBean.MessagesBean messagesBean = new Gson().fromJson(jsonObject.getString("msg_data"), MessageModel.ThreadBean.MessagesBean.class);

                    chatAdapter.thread_id = messagesBean.getThread_id();
                    chatDBHelper.updateTempMessage(jsonObject.getInt("temp_msg_id"), messagesBean, 1);
                    chatAdapter.updateData(jsonObject.getInt("temp_msg_id"), messagesBean);

                    // showChat(String.valueOf(jsonObject.getInt("thred_id")));
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

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, new IntentFilter("NEW_MESSAGE_BROADCAST"));

        if (getIntent().getExtras().getBoolean("newChat")) {


        } else {


            threadId = getIntent().getExtras().getInt("threadId");

            //   alMessages = chatDBHelper.fetchMessagesFromThread();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        threadId = -1;
        unregisterReceiver(broadcastReceiver);
    }


    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            
            try {
                //  JSONObject jsonObject = new JSONObject(intent.getExtras().getString("messageData"));

                MessageModel.ThreadBean.MessagesBean messagesBean = new Gson().fromJson(intent.getExtras().getString("messageData"), MessageModel.ThreadBean.MessagesBean.class);
           /* alMessages.add(messagesBean);

            mRecyclerView.getAdapter().notifyDataSetChanged();*/
                chatAdapter.addData(messagesBean);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    };

}
