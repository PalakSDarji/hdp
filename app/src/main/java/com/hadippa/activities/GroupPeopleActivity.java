package com.hadippa.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.model.ChatMainList;
import com.hadippa.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class GroupPeopleActivity extends AppCompatActivity {

    public static RecyclerView mRecyclerView;
    ArrayList<Contact> contacts;
    ProgressBar progressBar;
    CustomAdapter customAdapter;
    private ImageView imageBack;

    ArrayList<ChatMainList.ThreadsBean.ParticipantBean> participantBeanArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_people);

        ButterKnife.bind(this);
        ChatMainList.ThreadsBean chatMainList = (ChatMainList.ThreadsBean)getIntent().getExtras().getSerializable("threadBean");
        participantBeanArrayList.addAll(chatMainList.getParticipant());

        contacts = new ArrayList<>();
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
        contacts.add(new Contact());

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

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        customAdapter = new CustomAdapter(this, participantBeanArrayList);
        mRecyclerView.setAdapter(customAdapter);
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        LayoutInflater inflator;
        List<ChatMainList.ThreadsBean.ParticipantBean> alContacts;

        CustomAdapter(Context context, List<ChatMainList.ThreadsBean.ParticipantBean> alContacts) {

            inflator = LayoutInflater.from(context);
            this.alContacts = alContacts;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = inflator.inflate(R.layout.joined_people_item, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final ChatMainList.ThreadsBean.ParticipantBean contact = alContacts.get(position);

            viewHolder.name.setText(contact.getUser().getFirst_name() + " " + contact.getUser().getLast_name());

            RequestManager requestManager = Glide.with(GroupPeopleActivity.this);

            requestManager.load(contact.getUser().getProfile_photo())
                    .placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(viewHolder.image_view);

        }

        @Override
        public int getItemCount() {

            return alContacts.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Dummy
        private TextView name;
        ImageView image_view;

        public ViewHolder(final View v) {
            super(v);

            image_view = (ImageView) v.findViewById(R.id.image_view);
            name = (TextView) v.findViewById(R.id.text_view);
        }
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            AppConstants.showSnackBarforMessage(getCurrentFocus().getRootView(),intent.getExtras().getString("messageData"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(broadcastReceiver, new IntentFilter("SNACKBAR_MESSAGE"));
    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);
    }

}
