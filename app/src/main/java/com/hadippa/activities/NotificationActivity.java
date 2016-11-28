package com.hadippa.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Build;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.FollowersModel;
import com.hadippa.model.PeopleModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private List<String> notifications;
    private ImageView imageBack;
    private RecyclerView rcItems;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.pink_dark));
        }

        notifications = new ArrayList<>();
        for(int i =0;i<15;i++){
            notifications.add(""+i);
        }

        imageBack = (ImageView)findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent resultIntent = new Intent();

                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        rcItems = (RecyclerView) findViewById(R.id.myRecycler);

        final LinearLayoutManager mLayoutManagerHorizontal = new LinearLayoutManager(this);
        mLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);
        rcItems.setLayoutManager(mLayoutManagerHorizontal);

        customAdapter = new CustomAdapter(notifications);
        rcItems.setAdapter(customAdapter);

    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder>{

        private List<String> data = null;
        private LayoutInflater mInflater;

        CustomAdapter(List<String> data){
            this.data = data ;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notification, viewGroup, false);

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            //final String s = data.get(position);
        }

        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {

            return 20;
            //return data.size();
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
}
