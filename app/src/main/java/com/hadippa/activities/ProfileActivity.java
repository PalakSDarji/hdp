package com.hadippa.activities;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import com.hadippa.R;
import com.hadippa.fragments.main_screen.DoublePeople;
import com.hadippa.fragments.main_screen.People;
import com.hadippa.fragments.main_screen.ShowCardsNew;
import com.hadippa.model.Contact;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @BindView(R.id.rvMutualFriend) RecyclerView rvMutualFriend;
    @BindView(R.id.rvRecentInstagram) RecyclerView rvRecentInstagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        findViewById(R.id.ivEdit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivity(intent);
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
        rvMutualFriend.setAdapter(adapter);


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
        rvRecentInstagram.setAdapter(instaAdapter);

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

}
