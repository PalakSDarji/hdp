/*
package com.hadippa.tasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.hadippa.R;
import com.hadippa.database.HadippaDatabase;
import com.hadippa.model.Contact;
import com.hadippa.model.Message;
import com.hadippa.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class GetContactMessagesAsyncTask extends AsyncTask<AsyncTaskParams, Void, List<Message>> {
	
	private static final String TAG = GetContactMessagesAsyncTask.class.getSimpleName();
	
	private Context context;
	
	private static HadippaDatabase mDB;
	
	private static Contact contact;
	
	private static boolean forcedRefresh;
	
	private static long userID;
	
	ProgressDialog dialog;

    GetContactMessagesAsyncTask(Context context){
        this.context = context;
    }
	
	@Override
    protected void onPreExecute() {

		if (context != null) {
			dialog = Utils.getGenericProgressDialog(context, R.string.msg_loading_messages);
			dialog.show();
		}
    }
	
	@Override
	protected List<Message> doInBackground(AsyncTaskParams... params)
	{
		List<Message> messages = new ArrayList<>();
		
		try
		{
			Log.i(TAG, "doInBackground");
			
			mDB = HadippaDatabase.getInstance(context);
			contact = params[0].getContact();
			forcedRefresh = params[0].getForcedRefresh();
			userID = ApplicationConfig.getInstance().userID;
			
			// Check the data connection before cleaning items
			if (forcedRefresh && Utils.hasConnection(context) && contact != null && !TextUtils.isEmpty(MainApplication.getDeviceID())) {
				mDB.deleteAllMessagesByFilter(" message_state_id > 0 AND (identifier = '" + contact.getIMEI() + "' OR target = '" + contact.getIMEI() + "') ", userID);
			}

			if (contact != null && contact.getIMEI() != null) {
				messages = mDB.getAllMessagesByFilter(" (identifier = '" + contact.getIMEI() + "' OR target = '" + contact.getIMEI() + "') ", "create_ts", Constants.MESSAGE_LIMIT, userID);
			}
			
			if ((forcedRefresh || messages.size() < 1) && Utils.hasConnection(context)) {
				String dbUrl =  ApplicationConfig.getInstance().userDomainServer != null ? ApplicationConfig.getInstance().userDomainServer : Constants.DEFAULT_DB_URL;
				
				JSONObject obj = new JSONObject();
				obj.put(Constants.TARGET, contact.getIMEI());
				obj.put(Constants.IMEI, MainApplication.getDeviceID());
				obj.put(Constants.DATABASE_URL, dbUrl);
				obj.put(Constants.USER_ID, userID);
				
				JSONArray arr = new JSONArray();
				arr.put(obj);
			
				ListHelpers.parseLargeJSONFile(context, Constants.URL_PROTOCOL + Constants.URL_APPS + Constants.URL_JSON, "api_contact_messages_list", arr, mDB, Message.class);
			}
		}
		catch (Exception e)
		{
			if (Constants.showErrorMessages) Log.e(TAG, e.toString());
		}
		
		return messages;
	}
	
	@SuppressLint("DefaultLocale") @Override
	protected void onPostExecute(List<Message> messages)
	{
		try	{	
			if (forcedRefresh || ((messages == null || (messages.size() < 1)) && (mDB != null))) {
				List<Message> listMessages = mDB.getAllMessagesByFilter(" (identifier = '" + contact.getIMEI() + "' OR target = '" + contact.getIMEI() + "')", "create_ts", Constants.MESSAGE_LIMIT, userID);
				ListHelpers.loadContactMessages(listMessages);
			} else {
				ListHelpers.loadContactMessages(messages);
			}
		} catch (Exception e) {
			if (MessageChatActivity.getInstance() != null) {
				Utils.showGenericErrorDialog(MessageChatActivity.getInstance(), context, context.getApplicationContext().getResources().getString(R.string.msg_generic_error), null);
			}
		}
		
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
*/
