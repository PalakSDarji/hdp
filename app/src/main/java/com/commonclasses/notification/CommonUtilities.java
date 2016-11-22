package com.commonclasses.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Android on 17-05-2015.
 */
public class CommonUtilities {

 static final String SERVER_URL = "http://infinitekitchen.barodacoders.com/Api_infinite_kitchen/login_by_thirdparty";

    // Google project id
    static final String SENDER_ID = "1091219618361";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "infinite.kitchen.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */

    static void displayMessage(Context context, String message) {
        Log.d("notif>>",message);
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);

        context.sendBroadcast(intent);
    }
}
