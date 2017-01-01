package com.hadippa.activities;

import android.content.Context;
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

import com.hadippa.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_people);

        ButterKnife.bind(this);


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

        final GridLayoutManager mLayoutManager = new GridLayoutManager(this, 3);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        customAdapter = new CustomAdapter(this,contacts);
        mRecyclerView.setAdapter(customAdapter);
    }


    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        LayoutInflater inflator;
        List<Contact> alContacts;

        CustomAdapter(Context context, List<Contact> alContacts){

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

            final Contact contact = alContacts.get(position);
        }

        @Override
        public int getItemCount() {

            return alContacts.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        //Dummy
        private TextView name;

        public ViewHolder(final View v) {
            super(v);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    /*Intent intent1 = new Intent(ChatPlusActivity.this, ChatActivity.class);
                    intent1.putExtra("contact",contacts.get(getAdapterPosition()));
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
                }
            });

            name = (TextView) v.findViewById(R.id.name);
        }
    }
}
