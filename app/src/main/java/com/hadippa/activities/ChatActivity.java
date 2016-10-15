package com.hadippa.activities;

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
import android.widget.TextView;

import com.hadippa.R;
import com.hadippa.database.HadippaDatabase;
import com.hadippa.model.Contact;
import com.hadippa.model.Message;
import com.hadippa.utils.Constants;
import com.hadippa.utils.PreferencesHelper;
import com.hadippa.utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static RecyclerView mRecyclerView;
    ArrayList<Message> alMessages;
    ProgressBar progressBar;
    ChatAdapter chatAdapter;
    private ImageView imageBack;
    private Contact contact;
    private HadippaDatabase mDb;
    private EditText etChat;
    private long userID;
    private HadippaDatabase mDB;
    private PreferencesHelper preferencesHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        userID = 10125;//Long.parseLong(preferencesHelper.getString(PreferencesHelper.PREF_USER_ID_KEY));

        mDB = HadippaDatabase.getInstance(this);
        preferencesHelper = PreferencesHelper.getInstance(this);
        contact = getIntent().getParcelableExtra("contact");
        contact.setImei("dummyImeiOfAnotherPersonYouAreChattingWith");
        mDb = HadippaDatabase.getInstance(this);

        alMessages = new ArrayList<>();

        //get old message from database and display to the user.
        List<Message> msgListFromDb = mDB.getAllMessagesByFilter(" (identifier = '" + contact.getImei() + "' OR target = '" + contact.getImei() + "') ", "create_ts", Constants.MESSAGE_LIMIT, userID);

        //sort messages when display to the user
        Collections.sort(msgListFromDb, new Comparator<Message>() {
            public int compare(Message m1, Message m2) {
                return Utils.timestampStringToDate(m1.getCreateTS()).compareTo(Utils.timestampStringToDate(m2.getCreateTS()));
            }
        });
        if(msgListFromDb != null && !msgListFromDb.isEmpty()){
            alMessages.clear();
            alMessages.addAll(msgListFromDb);
        }

        /*for(int i=0;i<10;i++){

            Message message = new Message();
            message.setMessageBody("msg body 1");

            alMessages.add(message);
        }*/

       // mDb.getAllMessagesByFilter()

        etChat = (EditText) findViewById(R.id.etChat);

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

        chatAdapter = new ChatAdapter(alMessages);
        mRecyclerView.setAdapter(chatAdapter);

        findViewById(R.id.tvSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etChat.getText().toString().trim().length() > 0) sendNewMessage();
            }
        });
    }

    /**
     * send message to server
     * @return send status
     */
    private boolean sendNewMessage() {

        String message = etChat.getText().toString().trim();
        String deviceId= preferencesHelper.getString(PreferencesHelper.DEVICE_ID);
        if (!TextUtils.isEmpty(message)) {

            Message newMessage = new Message();
            newMessage.setMessageID(String.valueOf(Utils.nextMessageId(this)));
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

            /*MQTTInstantMessage newIM = new MQTTInstantMessage(newMessage);

            MessageService.getInstance().publishInstantMessage(newIM);
*/
            if (!Utils.hasConnection(this)) {
                //TODO toast error msg
            }

            //update message adapter.
            refreshMessageAdapter();

            return true;
        }
        return false;
    }

    private void refreshMessageAdapter() {

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

    public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

        private List<Message> items;

        public ChatAdapter(List<Message> items) {
            this.items = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_msg_left, parent, false);
            return new ViewHolder(v);
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            Message item = items.get(position);
            holder.tvText.setText(item.getMessageBody());
            //holder.image.setImageBitmap(null);
            /*Picasso.with(holder.image.getContext()).cancelRequest(holder.image);
            Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image);*/
            holder.itemView.setTag(item);
        }

        @Override public int getItemCount() {
            return items.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView image;
            public TextView tvText;

            public ViewHolder(View itemView) {
                super(itemView);
                image = (ImageView) itemView.findViewById(R.id.image);
                tvText = (TextView) itemView.findViewById(R.id.tvText);
            }
        }

        public void setData(List<Message> messageList) {
            items.clear();
            items.addAll(messageList);
            notifyDataSetChanged();
        }

    }

}
