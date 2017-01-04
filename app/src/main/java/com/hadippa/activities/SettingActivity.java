package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;
import com.hadippa.model.NotificationModel;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.tvBlockList)
    TextView tvBlockList;

    @BindView(R.id.imageBack)
    ImageView imageBack;

    @BindView(R.id.scContainer)
    NestedScrollView scContainer;

    @BindView(R.id.llNotifications)
    LinearLayout llNotifications;

    @BindView(R.id.rcvBlockList)
    RecyclerView rcvBlockList;

    @BindView(R.id.tvNotifications)
    TextView tvNotifications;

    @BindView(R.id.switchMessages)
    SwitchCompat switchMessages;

    @BindView(R.id.switchNotify)
    SwitchCompat switchNotify;

    @BindView(R.id.switchJoiningRequest)
    SwitchCompat switchJoiningRequest;

    @BindView(R.id.switchAllAlert)
    SwitchCompat switchAllAlert;

    @BindView(R.id.tvFAQ)
    TextView tvFAQ;

    @BindView(R.id.tvPrivacy)
    TextView tvPrivacy;

    private CustomAdapter customAdapter;
    private List<String> blockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        blockList =  new ArrayList<>();
        blockList.add("");
        blockList.add("");
        blockList.add("");

        tvBlockList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rcvBlockList.getVisibility() == View.VISIBLE){
                    rcvBlockList.setVisibility(View.GONE);
                }
                else{
                    rcvBlockList.setVisibility(View.VISIBLE);
                }
            }
        });

        tvNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(llNotifications.getVisibility() == View.VISIBLE){
                    llNotifications.setVisibility(View.GONE);
                }
                else{
                    llNotifications.setVisibility(View.VISIBLE);
                }
            }
        });

        imageBack = (ImageView) findViewById(R.id.imageBack);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent resultIntent = new Intent();

                setResult(Activity.RESULT_OK, resultIntent);
                finish();

                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        });

        final LinearLayoutManager mLayoutManagerHorizontal = new LinearLayoutManager(this);
        mLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);
        customAdapter = new CustomAdapter(blockList);
        rcvBlockList.setLayoutManager(mLayoutManagerHorizontal);
        rcvBlockList.setAdapter(customAdapter);

    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        private List<String> data = null;
        private LayoutInflater mInflater;

        CustomAdapter(List<String> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_block_list, viewGroup, false);

            return new ViewHolder(v);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final String str = data.get(position);
        }

        public String getItem(int position) {
            return data.get(position);
        }

        @Override
        public int getItemCount() {

            return data.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imageView;
        TextView tvBlock;
        TextView tvName;

        public ViewHolder(final View v) {
            super(v);

            tvBlock = (CustomTextView) v.findViewById(R.id.tvBlock);
            imageView = (RoundedImageView) v.findViewById(R.id.image_view);
            tvName = (TextView) v.findViewById(R.id.tvName);
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
    protected void onStop() {
        super.onStop();

        unregisterReceiver(broadcastReceiver);


    }

}
