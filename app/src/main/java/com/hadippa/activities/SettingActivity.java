package com.hadippa.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.APIClass;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hadippa.AppConstants;
import com.hadippa.CustomTextView;
import com.hadippa.R;

import com.hadippa.model.Blocked_Data;
import com.hadippa.model.NotificationModel;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

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
    private List<Blocked_Data.BlockedListBean> blockList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);


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
        customAdapter = new CustomAdapter();
        rcvBlockList.setLayoutManager(mLayoutManagerHorizontal);


        blockList();
    }

    class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

        private LayoutInflater mInflater;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_block_list, viewGroup, false);

            return new ViewHolder(v);
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onBindViewHolder(final ViewHolder viewHolder, final int position) {

            final Blocked_Data.BlockedListBean blockedList = blockList.get(position);

            viewHolder.tvName.setText(blockedList.getBlocked().getFirst_name()+" "+blockedList.getBlocked().getLast_name());
            Glide.with(SettingActivity.this)
                    .load(blockedList.getBlocked().getProfile_photo())
                    .error(R.drawable.ic_user_avatar_default_small)
                    .placeholder(R.drawable.ic_user_avatar_default_small)
                    .into(viewHolder.imageView);
            
            viewHolder.tvBlock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(SettingActivity.this);
                    builder1.setMessage("Do you want to unblock this user ?");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    block_Unblock("/unblock", String.valueOf(blockedList.getBlocked().getId()));
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "No",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }
            });
        }

        public Blocked_Data.BlockedListBean getItem(int position) {
            return blockList.get(position);
        }

        @Override
        public int getItemCount() {

            return blockList.size();
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


            AppConstants.showSnackBarforMessage(((RelativeLayout)findViewById(R.id.activity_setting)),intent.getExtras().getString("messageData"));
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

    //Blocked List
    private void blockList() {

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);

        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();


        try {

            requestParams.add("access_token", sp.getString("access_token", ""));

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + "/blocked_list", requestParams,
                new BlockedList());
    }

    class BlockedList extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

              showProgressDialog(SettingActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
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

                if(jsonObject.getBoolean("success")){

                    Blocked_Data
                    blocked_data = (new Gson().fromJson(response,Blocked_Data.class));

                    blockList.clear();
                    blockList.addAll(blocked_data.getBlocked_list());
                    customAdapter = new CustomAdapter();
                    rcvBlockList.setAdapter(customAdapter);
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("blockList??", "success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");

            Log.d("blockList??", " fff success exc  >>" + error.toString());
        }

    }

    public KProgressHUD hud;

    public void showProgressDialog(Context context, String message) {
        // PROGRESS_DIALOG.show();

        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(context.getResources().getColor(R.color.back_progress))

                .setLabel(message)
                .setDimAmount(0.5f)
                .setCancellable(true)
                .setAnimationSpeed(2);

        if(hud!=null){
            hud.show();
        }
    }

    public void dismissDialog() {

        if (hud != null) hud.dismiss();


    }
    
    //Block/Unblock
    private void block_Unblock(String type, String id) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();

        RequestParams requestParams = new RequestParams();

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);

        try {

            requestParams.add("access_token", sp.getString("access_token", ""));
            requestParams.add("blocked_id", id);

            Log.d("request>>", requestParams.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        asyncHttpClient.post(AppConstants.BASE_URL + AppConstants.API_VERSION + type, requestParams,
                new Block_UnBlock());
    }

    class Block_UnBlock extends AsyncHttpResponseHandler {

        @Override
        public void onStart() {
            super.onStart();

            showProgressDialog(SettingActivity.this, "Please Wait");

        }


        @Override
        public void onFinish() {
            dismissDialog();
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
                Log.d("blockList??", "success" + response);
                if(jsonObject.getBoolean("success")){

                    blockList();
                }

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("blockList??", "ssaa  success exc  >>" + e.toString());
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            //  AppConstants.showSnackBar(mainRel,"Try again!");
            Log.d("blockList??", "xzxxssuccess exc  >>" + error.toString());
        }

    }
}
