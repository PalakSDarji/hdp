package com.hadippa.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.hadippa.AppConstants;
import com.hadippa.model.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by HP on 18-12-2016.
 */

public class ChatDBHelper {

    SQLiteDatabase sqLiteDatabase;
    String DB_NAME = "HadipaaDB";
    String TABLE_NAME = "message";

    String KEY_ID = "id";
    String KEY_USER_ID = "user_id";
    String KEY_THREAD_ID = "thread_id";
    String KEY_USER = "user";
    String KEY_MESSAGE_TYPE = "msg_type";
    String KEY_BODY = "body";
    String KEY_STATUS = "status";
    String KEY_CREATED_AT = "created_At";

    public ChatDBHelper(Context context) {

        sqLiteDatabase = context.openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);

        sqLiteDatabase.execSQL("create table if not exists " + TABLE_NAME + " (" +
                KEY_ID + " integer PRIMARY KEY," +
                KEY_USER_ID + " integer," +
                KEY_THREAD_ID + " integer," +
                KEY_USER + " text," +
                KEY_MESSAGE_TYPE + " text," +
                KEY_BODY + " text," +
                KEY_STATUS + " integer," +
                KEY_CREATED_AT + " text)");
    }

    public void insertMessage(MessageModel.ThreadBean.MessagesBean messagesBean, int status) {


        if (sqLiteDatabase != null) {


            Gson gson = new Gson();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_ID, messagesBean.getId());
            contentValues.put(KEY_USER_ID, messagesBean.getUser_id());
            contentValues.put(KEY_THREAD_ID, messagesBean.getThread_id());
            contentValues.put(KEY_USER, gson.toJson(messagesBean.getUser()));
            contentValues.put(KEY_MESSAGE_TYPE, AppConstants.MESSAGE_TYPE_TEXT);
            contentValues.put(KEY_BODY, messagesBean.getBody());
            contentValues.put(KEY_STATUS, status);
            contentValues.put(KEY_CREATED_AT, messagesBean.getCreated_at());

            long gg= sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

            Log.d("sqLiteDatabase> INsert",""+gg);

        }

    }

    public ArrayList<MessageModel.ThreadBean.MessagesBean> fetchMessagesFromThread(int thread_id) {

        ArrayList<MessageModel.ThreadBean.MessagesBean> threadBeanArrayList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_NAME + " where " + KEY_THREAD_ID + " = " + thread_id, null);

        if (cursor != null) {
            if (cursor.getCount() > 0) {

                cursor.moveToFirst();
                do {

                    MessageModel.ThreadBean.MessagesBean messagesBean = new MessageModel.ThreadBean.MessagesBean();
                    messagesBean.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    messagesBean.setThread_id(cursor.getInt(cursor.getColumnIndex(KEY_THREAD_ID)));
                    messagesBean.setUser_id(cursor.getInt(cursor.getColumnIndex(KEY_USER_ID)));
                    messagesBean.setUser(new Gson().fromJson(cursor.getString(cursor.getColumnIndex(KEY_USER)), MessageModel.ThreadBean.MessagesBean.UserBean.class));
                    messagesBean.setMessage_type(cursor.getString(cursor.getColumnIndex(KEY_MESSAGE_TYPE)));
                    messagesBean.setBody(cursor.getString(cursor.getColumnIndex(KEY_BODY)));
                    messagesBean.setCreated_at(cursor.getString(cursor.getColumnIndex(KEY_CREATED_AT)));

                    threadBeanArrayList.add(messagesBean);
                } while (cursor.moveToNext());

            }
        }


        return threadBeanArrayList;
    }

    public void deleteThread(int thread_id) {

        if (sqLiteDatabase != null) {

            sqLiteDatabase.execSQL("delete from " + TABLE_NAME + " where " + KEY_THREAD_ID + " = " + thread_id);

        }

    }

    public void updateMessage(int thread_id) {

        if (sqLiteDatabase != null) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_STATUS, 1);

            sqLiteDatabase.update(TABLE_NAME, contentValues, KEY_THREAD_ID + " =" + thread_id, null);
        }

    }
}
