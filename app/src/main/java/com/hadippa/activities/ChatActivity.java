package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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

    ChatDBHelper chatDBHelper ;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int threadId;

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

        findViewById(R.id.tvSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (etChat.getText().toString().trim().length() > 0) {
                    //  sendNewMessage();

                    if (getIntent().getExtras().getBoolean("newChat")) {
                        if (!chatInitiated) {
                            createChat(etChat.getText().toString(), getIntent().getExtras().getString("userid"));
                        } else {
                            updateMessage(String.valueOf(threadId), etChat.getText().toString());
                        }

                    }else{
                        updateMessage(String.valueOf(getIntent().getExtras().getInt("threadId")),
                                etChat.getText().toString());
                    }
                }
            }
        });

        findViewById(R.id.etChat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount()-1);
            }
        });

        if (getIntent().getExtras().getBoolean("newChat")) {


        } else {
          //  showChat(String.valueOf(getIntent().getExtras().getInt("threadId")));

            chatAdapter = new ChatAdapter(getIntent().getExtras().getInt("threadId"));
         //   alMessages = chatDBHelper.fetchMessagesFromThread();
        }

        //     mRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
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

                    alMessages = messageModel.getThread().getMessages();


                    mRecyclerView.setAdapter(new ChatAdapter(alMessages));

                    mRecyclerView.scrollToPosition(mRecyclerView.getAdapter().getItemCount() - 1);


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

    private void updateMessage(String thread_id, String message) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("thread_id", thread_id);
            requestParams.add("message", message);
            requestParams.add("msg_type", AppConstants.MESSAGE_TYPE_TEXT);


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


                    chatAdapter.addData(messagesBean);

                    etChat.setText("");
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


    /**
     * send message to server
     *
     * @return send status
     */
  /*  private boolean sendNewMessage() {

        String message = etChat.getText().toString().trim();
        String deviceId= preferencesHelper.getString(PreferencesHelper.DEVICE_ID);
        if (!TextUtils.isEmpty(message)) {

            Message newMessage = new Message();
            newMessage.setMessageID(Utils.randomNum());
            newMessage.setUserID(userID);
            newMessage.setMessageStateID(0);
            newMessage.setMessageTypeID(3); // Instant Message
            newMessage.setMessageSourceID(3); // Mobile Device
            newMessage.setMessageDirectionID(1); // 0 = OUT | 1 = IN
            newMessage.setIdentifier(deviceId);
            newMessage.setMessageBody(message);
            newMessage.setTarget(contact.getImei());
            //newMessage.setAction(Constants.MQTT.MESSAGE_ACTION_CREATED);
            newMessage.setLeft(false);
            newMessage.setCreateTS(Utils.getDateTimeNow(Constants.TIMESTAMP_FORMAT_WITH_TIMEZONE));

            if (mDB != null) {
                long isInserted = mDB.insertMessage(newMessage,preferencesHelper.getString(PreferencesHelper.DEVICE_ID));
                Log.v("chat","isInserted : "+ isInserted);
            }
            etChat.setText("");

            *//*MQTTInstantMessage newIM = new MQTTInstantMessage(newMessage);

            MessageService.getInstance().publishInstantMessage(newIM);
*//*
            if (!Utils.hasConnection(this)) {
                //TODO toast error msg
            }

            //update message adapter.
            refreshMessageAdapter();

            return true;
        }
        return false;
    }
*/
  /*  private void refreshMessageAdapter() {

        if (chatAdapter != null) {
            runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        //get messages from database
                        List<Message> msgListFromDb = mDB.getAllMessagesByFilter(" (identifier = '" + contact.getImei() + "' OR target = '" + contact.getImei() + "') ", "create_ts", Constants.MESSAGE_LIMIT, userID);

                        //sort messages when display to the user
                        Collections.sort(msgListFromDb, new Comparator<Message>() {
                            public int compare(Message m1, Message m2) {
                                return Utils.timestampStringToDate(m1.getCreateTS()).compareTo(Utils.timestampStringToDate(m2.getCreateTS()));
                            }
                        });
                        if(msgListFromDb != null && !msgListFromDb.isEmpty()){
                            chatAdapter.setData(msgListFromDb);
                        }
                        mRecyclerView.scrollToPosition(chatAdapter.getItemCount()-1);
                    }
                    catch (Exception e) {
                        //if (Constants.showErrorMessages) Log.e(TAG, e.toString());
                    }
                }
            });
        }
    }

  */  public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<MessageModel.ThreadBean.MessagesBean> items;
        int thread_id;

        public ChatAdapter(List<MessageModel.ThreadBean.MessagesBean> items) {
            this.items = items;
        }

        public ChatAdapter(int thread_id) {
            this.thread_id = thread_id;

            items.addAll(chatDBHelper.fetchMessagesFromThread(thread_id));
        }


        public void addData(MessageModel.ThreadBean.MessagesBean  messagesBean){

            if(messagesBean.getThread_id() == threadId){
                chatDBHelper.updateMessage(threadId);
                items.add(messagesBean);
                notifyItemInserted(getItemCount()-1);
            }else {
                //chatDBHelper.insertMessage(messagesBean, 0);
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
            holder.tvDate.setText(AppConstants.formatDate(item.getCreated_at(),"yyyy-mm-dd hh:mm:ss","hh:mm"));
            //holder.image.setImageBitmap(null);
            /*Picasso.with(holder.image.getContext()).cancelRequest(holder.image);
            Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image);*/
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
                tvDate = (CustomTextView)itemView.findViewById(R.id.tvDate);
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
                if (alMessages.get(position).getUser_id() == jsonObject.getInt("id")) {
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


    private void createChat(String msg, String receiver) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("message", msg);
            requestParams.add("receiver_id", receiver);
            requestParams.add("msg_type", AppConstants.MESSAGE_TYPE_TEXT);


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
                    Log.d("response>>", response);

                    threadId = jsonObject.getInt("thred_id");
                    showChat(String.valueOf(jsonObject.getInt("thred_id")));
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

        registerReceiver(broadcastReceiver,new IntentFilter("NEW_MESSAGE_BROADCAST"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

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
