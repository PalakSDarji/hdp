package com.hadippa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Palak on 04-10-2016.
 */
public class Contact implements Parcelable {

    private String personName;
    private String lastMsg;
    private String profilePhotoUrl;
    private long lastMsgDate;

    @SerializedName("imei")
    private String _imei;

    @Override
    public String toString() {
        return "Contact{" +
                "personName='" + personName + '\'' +
                ", lastMsg='" + lastMsg + '\'' +
                ", profilePhotoUrl='" + profilePhotoUrl + '\'' +
                ", lastMsgDate=" + lastMsgDate +
                '}';
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getLastMsg() {
        return lastMsg;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public long getLastMsgDate() {
        return lastMsgDate;
    }

    public void setLastMsgDate(long lastMsgDate) {
        this.lastMsgDate = lastMsgDate;
    }

    public Contact() {
    }

    protected Contact(Parcel in) {
        personName = in.readString();
        lastMsg = in.readString();
        profilePhotoUrl = in.readString();
        lastMsgDate = in.readLong();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(personName);
        dest.writeString(lastMsg);
        dest.writeString(profilePhotoUrl);
        dest.writeLong(lastMsgDate);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getImei() {
        return _imei;
    }

    public void setImei(String _imei) {
        this._imei = _imei;
    }
}