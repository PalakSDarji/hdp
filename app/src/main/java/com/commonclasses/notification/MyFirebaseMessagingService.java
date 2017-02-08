package com.commonclasses.notification;

/**
 * Created by HP on 27-11-2016.
 */

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.R;
import com.hadippa.activities.ChatActivity;
import com.hadippa.activities.HomeScreen;
import com.hadippa.database.ChatDBHelper;
import com.hadippa.model.MessageModel;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by android on 10/14/2016.
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFMService";

    // {"send_id":211,"thread_id":4,
    // "send_name":"Sahil",
    //
    // "profile_photo_thumbnail":
    // "https:\/\/scontent.xx.fbcdn.net\/v\/t1.0-1\/p480x480\/1937043_978044888911311_1642937164755094085_n.jpg?
    // oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762",
    // "msg_type":"1","message":"gggbbbf"}

    ChatDBHelper chatDBHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        chatDBHelper = new ChatDBHelper(this);
    }

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

        Log.d(TAG, "FCM  Data : " + remoteMessage.getData().get("message").toString());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "FCM Notification Message: " + remoteMessage.getNotification().getBody());
        }

        sendNotificationPost(remoteMessage.getData());

    }


    private void sendNotificationPost(Map<String, String> stringStringMap) {

        try {


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
                            .setSmallIcon(R.mipmap.ic_launcher)
                        /*.setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(jobj))*/
                            .setSound(soundUri)
                            .setAutoCancel(true);


            mBuilder.setContentIntent(contentIntent);

            JSONObject jsonObject = new JSONObject(stringStringMap.get("message").toString());
            if (jsonObject.has("thread_id")) {
                MessageModel.ThreadBean.MessagesBean messagesBean = new Gson().fromJson(stringStringMap.get("message")
                        , MessageModel.ThreadBean.MessagesBean.class);

                if (messagesBean.getThread_id() != ChatActivity.threadId) {
                    mBuilder.setContentTitle(messagesBean.getUser().getFirst_name() + " " + messagesBean.getUser().getLast_name());
                    mBuilder.setContentText(messagesBean.getBody());

                    ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
                    List<ActivityManager.RunningTaskInfo> services = activityManager
                            .getRunningTasks(Integer.MAX_VALUE);
                    boolean isActivityFound = false;

                    if (services.get(0).topActivity.getPackageName().toString()
                            .equalsIgnoreCase(this.getPackageName().toString())) {
                        isActivityFound = true;
                    }

                    if (isActivityFound) {
                        Intent sendBroadCast = new Intent("SNACKBAR_MESSAGE");
                        sendBroadCast.putExtra("messageData", messagesBean.getUser().getFirst_name()
                                + " " + messagesBean.getUser().getLast_name() + " : " + messagesBean.getBody());
                        sendBroadcast(sendBroadCast);

                    } else {

                        mNotificationManager.notify(12, mBuilder.build());
                    }
                }


                chatDBHelper.insertMessage(messagesBean, 0);
                newMessageBroadCast(stringStringMap.get("message"));
            } else {

                mBuilder.setContentTitle("Hadipaa");
                mBuilder.setContentText(stringStringMap.get("message"));

                mNotificationManager.notify(12, mBuilder.build());

            }


        } catch (Exception e) {

        }

    }

    void newMessageBroadCast(String message) {

        Intent sendBroadCast = new Intent("NEW_MESSAGE_BROADCAST");
        sendBroadCast.putExtra("messageData", message);
        sendBroadcast(sendBroadCast);


    }

}