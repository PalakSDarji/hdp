package com.commonclasses.notification;

/**
 * Created by HP on 27-11-2016.
 */

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hadippa.R;
import com.hadippa.activities.HomeScreen;

import java.util.Map;

/**
 * Created by android on 10/14/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Handle data payload of FCM messages.
        Log.d(TAG, "FCM  Id: " + remoteMessage.getMessageId());
        Log.d(TAG, "FCM  getFrom: " + remoteMessage.getFrom());
        Log.d(TAG, "FCM  getCollapseKey: " + remoteMessage.getCollapseKey());
        Log.d(TAG, "FCM  getTo: " + remoteMessage.getTo());
        Log.d(TAG, "FCM  getMessageType: " + remoteMessage.getMessageType());
        Log.d(TAG, "FCM  getSentTime: " + remoteMessage.getSentTime());
        Log.d(TAG, "FCM  getTtl: " + remoteMessage.getTtl());

        Log.d(TAG, "FCM  Data : " + remoteMessage.getData().toString());

        if(remoteMessage.getNotification() != null){
            Log.d(TAG, "FCM Notification Message: " +remoteMessage.getNotification().getBody());
        }

        sendNotificationPost(remoteMessage.getData());

    }


    private void sendNotificationPost(Map<String,String> stringStringMap) {


        NotificationManager mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        Log.d(TAG, "sendNotification");

        Intent intent;

        intent = new Intent(this, HomeScreen.class);




        //   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(stringStringMap.get("title"))
                        .setSmallIcon(R.drawable.com_facebook_button_icon)
                        /*.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(jobj))*/
                        .setSound(soundUri)
                        .setAutoCancel(true)
                        .setContentText(stringStringMap.get("content"));


        mBuilder.setContentIntent(contentIntent);

        mNotificationManager.notify(12, mBuilder.build());

    }

}