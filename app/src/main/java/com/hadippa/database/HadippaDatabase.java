package com.hadippa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.hadippa.Hadippa;
import com.hadippa.model.Message;

import java.util.ArrayList;
import java.util.List;

public class HadippaDatabase extends SQLiteOpenHelper {

	private static HadippaDatabase instance;


    public static synchronized HadippaDatabase getInstance(Context context)
    {
        if (instance == null) {
            instance = new HadippaDatabase(context);
        }
        return instance;
    }
	
    /*
	****************************************
	* Database Configuration
	****************************************
	*/
	private static final String TAG = "HadippaDatabase";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Hadippa";
    
    /*
	****************************************
	* Table Names
	****************************************
	*/
    public static final String TABLE_MESSAGE = "message";

    /*
	****************************************
	* Message Table Column Names
	****************************************
	*/

    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_CREATE_TS = "create_ts";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_MESSAGE_ID = "message_id";
    public static final String KEY_ORIGINAL_MESSAGE_ID = "original_message_id";
    public static final String KEY_MESSAGE_STATE_ID = "message_state_id";
    public static final String KEY_MESSAGE_TYPE_ID = "message_type_id";
    public static final String KEY_MESSAGE_SOURCE_ID = "message_source_id";
    public static final String KEY_MESSAGE_DIRECTION_ID = "message_direction_id";
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_MESSAGE_BODY = "message_body";
    public static final String KEY_MESSAGE_ROUTE = "message_route";
    public static final String KEY_TARGET = "target";
    public static final String KEY_ACTION = "action";
    public static final String KEY_LEFT = "left";
    public static final String KEY_APP_DB_CREATE_TS = "app_db_create_ts";
    
    // Message Table Create Statement
    private static final String CREATE_TABLE_MESSAGE = "CREATE TABLE " +
            TABLE_MESSAGE + 
            " (" + 
	            KEY_MESSAGE_ID + " TEXT PRIMARY KEY, " + 
	            KEY_ORIGINAL_MESSAGE_ID + " TEXT, " + 
	            KEY_USER_ID + " INTEGER, " +
	            KEY_MESSAGE_STATE_ID + " INTEGER, " +
	            KEY_MESSAGE_TYPE_ID + " INTEGER, " +
	            KEY_MESSAGE_SOURCE_ID + " INTEGER, " +
	            KEY_MESSAGE_DIRECTION_ID + " INTEGER, " +	            
	            KEY_IDENTIFIER + " TEXT, " + 
	            KEY_MESSAGE_BODY + " TEXT, " + 
	            KEY_MESSAGE_ROUTE + " TEXT, " + 
	            KEY_CREATE_TS + " TEXT, " + 
	            KEY_TARGET + " TEXT, " + 
	            KEY_USER_NAME + " TEXT, " + 
	            KEY_ACTION + " TEXT, " + 
	            KEY_LEFT + " INTEGER, " +
	            KEY_APP_DB_CREATE_TS + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP " +
    		")";

    public HadippaDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
    	// Create the tables
        db.execSQL(CREATE_TABLE_MESSAGE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	Log.w(TAG, "Upgrading database. Existing contents will be lost. ["+ oldVersion + "]->[" + newVersion + "]");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGE);
        onCreate(db);
    }

    public void truncateAllTables() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	db.execSQL("delete from " + TABLE_MESSAGE);
    }
    
    /*
	****************************************
	* Message Methods
	****************************************
	*/
    
    // Insert Message
    public long insertMessage(Message message, String myDeviceId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message.getMessageID());
        values.put(KEY_ORIGINAL_MESSAGE_ID, message.getOriginalMessageID());
        values.put(KEY_MESSAGE_STATE_ID, message.getMessageStateID());
        values.put(KEY_MESSAGE_TYPE_ID, message.getMessageTypeID());
        values.put(KEY_MESSAGE_SOURCE_ID, message.getMessageSourceID());
        values.put(KEY_MESSAGE_DIRECTION_ID, message.getMessageDirectionID());
        values.put(KEY_IDENTIFIER, message.getIdentifier());
        values.put(KEY_MESSAGE_BODY, message.getMessageBody());
        values.put(KEY_MESSAGE_ROUTE, message.getMessageRoute());
        values.put(KEY_TARGET, message.getTarget());
        values.put(KEY_USER_NAME, message.getUserName());
        values.put(KEY_ACTION, message.getAction());
        values.put(KEY_USER_ID, message.getUserID());
        values.put(KEY_CREATE_TS, message.getCreateTS());

        int left = 0;
        if (myDeviceId != null) {
            left = myDeviceId.equals(message.getTarget()) ? 1 : 0;
        }

        values.put(KEY_LEFT, left);

        // Insert Row
        try {
            long key_id = db.insertOrThrow(TABLE_MESSAGE, null, values);
            if (key_id > 0) {
                return key_id;
            } else {
                throw new SQLException("Failed to insert message into table. Key: " + message.getMessageID());
            }
        } catch (SQLiteConstraintException e) {
            Log.d(TAG, "Ignoring constraint failure. " + e.toString());
        } finally {
            Log.d(TAG, "Insert Complete: KEY: " + message.getMessageID());
        }
        return 0;
    }
    
    // Update Message
    public void updateMessage(Message message, String myDeviceId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Get values
        ContentValues values = new ContentValues();
        values.put(KEY_MESSAGE_ID, message.getMessageID());
        values.put(KEY_ORIGINAL_MESSAGE_ID, message.getOriginalMessageID());
        values.put(KEY_MESSAGE_STATE_ID, message.getMessageStateID());
        values.put(KEY_MESSAGE_TYPE_ID, message.getMessageTypeID());
        values.put(KEY_MESSAGE_SOURCE_ID, message.getMessageSourceID());
        values.put(KEY_MESSAGE_DIRECTION_ID, message.getMessageDirectionID());
        values.put(KEY_IDENTIFIER, message.getIdentifier());
        values.put(KEY_MESSAGE_BODY, message.getMessageBody());
        values.put(KEY_MESSAGE_ROUTE, message.getMessageRoute());
        values.put(KEY_TARGET, message.getTarget());
        values.put(KEY_USER_NAME, message.getUserName());
        values.put(KEY_ACTION, message.getAction());
        values.put(KEY_USER_ID, message.getUserID());
        values.put(KEY_CREATE_TS, message.getCreateTS());
        int left = myDeviceId.equals(message.getTarget()) ? 1 : 0;
        values.put(KEY_LEFT, left);

        // Update the row
        db.update(TABLE_MESSAGE, values, KEY_MESSAGE_ID + " = ?", new String[]{message.getMessageID()});
    }
    
    // Delete Message
    public void deleteMessage(String message_id) {        
        String deleteQuery = "DELETE FROM " + TABLE_MESSAGE + " WHERE " + KEY_MESSAGE_ID + " = '" + message_id + "' ";
        
        Log.d(TAG, deleteQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);
        
        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }
    
    // Fetch Single Message
    public Message getMessage(String message_id) {
        SQLiteDatabase db = this.getReadableDatabase();
     
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE + " WHERE " + KEY_MESSAGE_ID + " = '" + String.valueOf(message_id) + "' ";

        Log.d(TAG, selectQuery);
        
        Cursor c = db.rawQuery(selectQuery, null);

        Message message = new Message();

        if (c != null) {
            c.moveToFirst();

            // Set values
            message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
            message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
            message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
            message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
            message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
            message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
            message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
            message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
            message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
            message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
            message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
            message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
            message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
            message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
            boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
            message.setLeft(left);

            c.close();
        }
        
        return message;
    }
    
	// Fetch All Messages
    public Message getMostRecentContactMessage(String imei, long user_id) {
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGE +  " WHERE (" + KEY_TARGET + " = '" + imei + "' OR " + KEY_IDENTIFIER + " = '" + imei + "') AND " + KEY_USER_ID + " = " + String.valueOf(user_id) + " ORDER BY " + KEY_APP_DB_CREATE_TS + " DESC LIMIT 1";
        
        Log.d(TAG, selectQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
        // Loop through all of the rows and add items to the list
        if (c != null) {
            if (c.moveToFirst()) {
                // Set values
                Message message = new Message();
                message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
                message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
                message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
                message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
                message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
                message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
                message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
                message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
                message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
                message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
                message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
                message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
                boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
                message.setLeft(left);
                return message;
            }
            c.close();
        }
        
        return null;
    }
    
    // Fetch All Message With Filters
    public List<Message> getAllMessagesByFilter(String _whereClause, String _orderByColumn, int _limit, long user_id) {
        List<Message> messages = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_MESSAGE;
        String nextComparisonOperator = " WHERE ";      
        
        if (_whereClause != null && !TextUtils.isEmpty(_whereClause)) {
        	selectQuery = selectQuery + nextComparisonOperator + _whereClause;
        	nextComparisonOperator = " AND ";
        }
        
        selectQuery = selectQuery + nextComparisonOperator + KEY_USER_ID + " = " + String.valueOf(user_id);
        
        if (_orderByColumn != null) {
        	// Use descending order to display the most recent items
        	selectQuery = selectQuery + " ORDER BY " + _orderByColumn + " DESC ";
        }
        
        if (_limit > 0) {
        	selectQuery = selectQuery + " LIMIT " + _limit;
        }
        
        Log.d(TAG, selectQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        
        // Loop through all of the rows and add items to the list in reverse order
        if (c != null) {
            if (c.moveToLast()) {
                do {
                    // Set values
                    Message message = new Message();
                    message.setMessageID(c.getString(c.getColumnIndex(KEY_MESSAGE_ID)));
                    message.setOriginalMessageID(c.getString(c.getColumnIndex(KEY_ORIGINAL_MESSAGE_ID)));
                    message.setMessageStateID(c.getInt(c.getColumnIndex(KEY_MESSAGE_STATE_ID)));
                    message.setMessageTypeID(c.getInt(c.getColumnIndex(KEY_MESSAGE_TYPE_ID)));
                    message.setMessageSourceID(c.getInt(c.getColumnIndex(KEY_MESSAGE_SOURCE_ID)));
                    message.setMessageDirectionID(c.getInt(c.getColumnIndex(KEY_MESSAGE_DIRECTION_ID)));
                    message.setIdentifier(c.getString(c.getColumnIndex(KEY_IDENTIFIER)));
                    message.setMessageBody(c.getString(c.getColumnIndex(KEY_MESSAGE_BODY)));
                    message.setMessageRoute(c.getString(c.getColumnIndex(KEY_MESSAGE_ROUTE)));
                    message.setTarget(c.getString(c.getColumnIndex(KEY_TARGET)));
                    message.setUserName(c.getString(c.getColumnIndex(KEY_USER_NAME)));
                    message.setAction(c.getString(c.getColumnIndex(KEY_ACTION)));
                    message.setUserID(c.getInt(c.getColumnIndex(KEY_USER_ID)));
                    message.setCreateTS(c.getString(c.getColumnIndex(KEY_CREATE_TS)));
                    boolean left = c.getInt(c.getColumnIndex(KEY_LEFT)) > 0;
                    message.setLeft(left);
                    messages.add(message);
                } while (c.moveToPrevious());
            }

            c.close();
        }
        
        return messages;
    }
    
    // Delete Messages By Filter
    public void deleteAllMessagesByFilter(String _whereClause, long user_id) {
        String deleteQuery = "DELETE FROM " + TABLE_MESSAGE;
        String nextComparisonOperator = " WHERE ";      
        
        if (_whereClause != null && !TextUtils.isEmpty(_whereClause)) {
        	deleteQuery = deleteQuery + nextComparisonOperator + _whereClause;
        	nextComparisonOperator = " AND ";
        }       
        
        deleteQuery = deleteQuery + nextComparisonOperator + KEY_USER_ID + " = " + String.valueOf(user_id);
        
        Log.d(TAG, deleteQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(deleteQuery, null);
        
        if (c != null) {
            c.moveToFirst();
            c.close();
        }
    }
    
    // Get Maximum Primary Key Integer Value
    public int getMessageMaxID() {        
        String selectQuery = "SELECT " + KEY_MESSAGE_ID + " as MAX FROM " + TABLE_MESSAGE + " WHERE length(" + KEY_MESSAGE_ID + ") < 8 ORDER BY " + KEY_MESSAGE_ID + " DESC LIMIT 1";
        
        Log.d(TAG, selectQuery);
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        int max = 0;
        if (c != null) {
            c.moveToFirst();
            max = c.getInt(c.getColumnIndex("MAX"));
            c.close();
        }
        
        return max;
    }
}
