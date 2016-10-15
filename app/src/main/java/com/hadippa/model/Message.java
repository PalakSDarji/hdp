package com.hadippa.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;

public class Message implements Parcelable {	
	
	@SerializedName("message_id")
	private String _message_id;
	
	@SerializedName("original_message_id")
	private String _original_message_id;

	@SerializedName("user_id")
	private long _user_id;
	
	@SerializedName("message_state_id")
	private long _message_state_id;
	  
	@SerializedName("message_type_id")
	private long _message_type_id;
	
	@SerializedName("message_source_id")
	private long _message_source_id;
	
	@SerializedName("message_direction_id")
	private long _message_direction_id;
	
	@SerializedName("identifier")
	private String _identifier;
	
	@SerializedName("message_body")
	private String _message_body;
    
    @SerializedName("message_route")
    private String _message_route;
    
	@SerializedName("create_ts")
    private String _create_ts;
	
	@SerializedName("target")
	private String _target;
	
	@SerializedName("user_name")
	private String _user_name;
	
	@SerializedName("action")
	private String _action;
    
    private boolean _left;
    
	public Message() {
	}
	
	public String getMessageID() {
		return _message_id;
	}

	public void setMessageID(String message_id) {
		_message_id = message_id;
	}
	
	public String getOriginalMessageID() {
		return _original_message_id;
	}

	public void setOriginalMessageID(String original_message_id) {
		_original_message_id = original_message_id;
	}
	
	public long getUserID() {
		return _user_id;
	}

	public void setUserID(long user_id) {
		_user_id = user_id;
	}

	public long getMessageStateID() {
		return _message_state_id;
	}

	public void setMessageStateID(long message_state_id) {
		_message_state_id = message_state_id;
	}

	public long getMessageTypeID() {
		return _message_type_id;
	}

	public void setMessageTypeID(long message_type_id) {
		_message_type_id = message_type_id;
	}

	public long getMessageSourceID() {
		return _message_source_id;
	}

	public void setMessageSourceID(long message_source_id) {
		_message_source_id = message_source_id;
	}

	public long getMessageDirectionID() {
		return _message_direction_id;
	}

	public void setMessageDirectionID(long message_direction_id) {
		_message_direction_id = message_direction_id;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public void setIdentifier(String identifier) {
		_identifier = identifier;
	}

	public String getMessageBody() {
		return _message_body;
	}

	public void setMessageBody(String message_body) {
		_message_body = message_body;
	}

	public String getMessageRoute() {
		return _message_route;
	}

	public void setMessageRoute(String message_route) {
		_message_route = message_route;
	}
	
	public String getCreateTS()
	{
		return _create_ts;
	}
	
	public void setCreateTS(String create_ts)
	{
		_create_ts = create_ts;
	}
	
	public String getTarget() {
		return _target;
	}

	public void setTarget(String target) {
		_target = target;
	}
	
	public String getUserName() {
		return _user_name;
	}

	public void setUserName(String user_name) {
		_user_name = user_name;
	}
	
	public String getAction() {
		return _action;
	}

	public void setAction(String action) {
		_action = action;
	}
	
	public boolean getLeft()
	{
		return _left;
	}
	
	public void setLeft(Boolean left)
	{
		_left = left;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(_message_id);
		out.writeString(_original_message_id);
		out.writeLong(_user_id);
		out.writeLong(_message_state_id);
		out.writeLong(_message_type_id);
		out.writeLong(_message_source_id);
		out.writeLong(_message_direction_id);
		out.writeString(_identifier);		
		out.writeString(_message_body);
		out.writeString(_message_route);
		out.writeString(_create_ts);
		out.writeString(_target);
		out.writeString(_user_name);
		out.writeString(_action);
	}
	
	public static final Creator<Message> CREATOR = new Creator<Message>() {
		public Message createFromParcel(Parcel in) {
			return new Message(in);
		}

		public Message[] newArray(int size) {
			return new Message[size];
		}
	};

	private Message(Parcel in) {
		_message_id = in.readString();
		_original_message_id = in.readString();
		_user_id = in.readLong();
		_message_state_id = in.readLong();
		_message_type_id = in.readLong();
		_message_source_id = in.readLong();
		_message_direction_id = in.readLong();
		_identifier = in.readString();
		_message_body = in.readString();
		_message_route = in.readString();
		_create_ts = in.readString();
		_target = in.readString();
		_user_name = in.readString();
		_action = in.readString();
	}
	
	/*public static Message fromJsonObject(StringifiedJSONObject json) {
		try {
			String type = json.getString("_type");
			if (!type.equals("instant_message"))
				throw new JSONException("wrong type");
		} catch (JSONException e) {
			if (Constants.showErrorMessages) Log.e("Message", "Unable to deserialize Message object from JSON");
			return null;
		}
		
		Message message = new Message();
		
		try {
			Gson gson = new Gson();
	        message = gson.fromJson(json.toString(), Message.class); 
	        boolean left = MainApplication.getDeviceID().equals(message.getTarget()) ? true: false;
	        message.setLeft(left);		
		} catch (Exception e) {
			if (Constants.showErrorMessages) Log.e("Message", "Unable to deserialize Message object from JSON");
		}
		
		return message;
	}*/

}
