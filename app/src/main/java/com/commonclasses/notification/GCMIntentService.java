package com.commonclasses.notification;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.hadippa.R;
import com.hadippa.activities.HomeScreen;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;


/**
 * Created by Android on 17-05-2015.
 */

public class GCMIntentService extends IntentService {
    public static final int NOTIFICATION_ID = 1;
    String item_id;
    public static final String TAG = "Intent gcm";
    NotificationCompat.Builder builder;

    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String message = "error", notifictionType = "error";

    String noti_type ="1";

    public GCMIntentService() {
        super("GCM" +
                "IntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final Bundle extras = intent.getExtras();
        Log.d("received>.", "" + extras.toString());

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sp.edit();
       /* GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);


        if(sp.getBoolean("loginStatus",false)) {

            String messageType = gcm.getMessageType(intent);

            if (!extras.isEmpty()) {
                if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {

                } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {

                } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {

                    try {
                       // JSONObject jsonObj = new JSONObject(extras.getString("alert"));
                      //  Log.d("received>.", jsonObj.toString());
                       *//* item_id = jsonObj.getString("donate_item_id");
                        noti_type = jsonObj.getString("noti_type");*//*
                   *//* if(sp.getBoolean("showSilentNotification",false)){
                        final Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {


                                HomeScreen.notification(a,extras);
                            }


                        });
                        thread.start();
                    }else {*//*
                        sendNotification(extras.getString("message"));
                        //  }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }*/
    }

    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }

    private void sendNotification(String jobj) {


        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d(TAG, "sendNotification");
        Intent intent = null;
      //  if(noti_type.equalsIgnoreCase("donationcancel") ||noti_type.equalsIgnoreCase("b") ){
            intent = new Intent(this, HomeScreen.class);
       // }else{
           /* intent = new Intent(this, DonationDetails.class);
            intent.putExtra("from", "notification");
            intent.putExtra("item_id", item_id);*/
      //  }

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.landing_pages_address_icon)
                        .setContentTitle("Hadipaa")

                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(jobj))
                        .setSound(soundUri)
                        .setColor(getResources().getColor(R.color.tabIndicator))
                        .setAutoCancel(true)
                        .setContentText(jobj);


        mBuilder.setContentIntent(contentIntent);

        mNotificationManager.notify(generateRandom(), mBuilder.build());

    }
}