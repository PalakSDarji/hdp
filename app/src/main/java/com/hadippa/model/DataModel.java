package com.hadippa.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 29-07-2016.
 */
public class DataModel{


    public List<DataModel> results;

    @SerializedName("id")
    public String id;

    @SerializedName("user_id")
    public String user_id;

    @SerializedName("activity_details")
    public JSONObject activity_details;

    @SerializedName("activity_location_lat")
    public String activity_location_lat;

    @SerializedName("activity_location_lon")
    public String activity_location_lon;

    @SerializedName("activity_date")
    public String activity_date;

    @SerializedName("active")
    public String active;

    @SerializedName("privacy")
    public String privacy;

    @SerializedName("activity_time")
    public String activity_time;

    @SerializedName("activity_location")
    public String activity_location;

    @SerializedName("mutual_friends")
    public String mutual_friends;

    @SerializedName("user")
    public String user;

    @SerializedName("activity")
    public String activity;

   /* @SerializedName("people_going")
    public JSONArray people_going;

    @SerializedName("people_approaching_count")
    public JSONArray people_approaching_count;

    @SerializedName("people_going_count")
    public JSONArray people_going_count;

    @SerializedName("follower")
    public JSONArray follower;

    @SerializedName("following")
    public JSONArray following;*/
    /*{
        "id": "4",
            "user_id": "13",
            "activity_type": "4",
            "activity_details": {
        "activity_name": "Oppa Bar"
    },
        "activity_location": "Andheri (E)",
            "activity_location_lat": "78.8797",
            "activity_location_lon": "78.8797",
            "activity_date": "2016-07-30",
            "active": "1",
            "privacy": "0",
            "created_at": "2016-06-18 00:00:00",
            "updated_at": "2016-06-18 00:00:00",
            "activity_time": "23:50",
            "mutual_friends": "",
            "user": {
        "id": "13",
                "first_name": "Parul",
                "last_name": "Thakur",
                "gender": "female",
                "dob": "1991-09-04",
                "profile_photo": "http://www.hadipaa.folives.com/assets/images/profiles/user/default.jpg",
                "age_range_from": "18",
                "age_range_to": "99",
                "private_account": "0",
                "photo_uploaded": "1",
                "age": 24,
                "profile_photo_thumbnail": "http://www.hadipaa.folives.com/assets/images/profiles/user/thumbnails/default.jpg"
    },
        "activity": {
        "id": "4",
                "activity_name": "night_club",
                "activity_display_name": "Night Club",
                "activity_category": {
            "id": "2",
                    "activity_category_name": "nightlife",
                    "activity_category_display_name": "Night Life"
        }
    },
        "people_going": [],
        "people_approaching_count": [
        {
            "id": "267",
                "activity_id": "4",
                "requester_id": "99",
                "total": "1"
        }
        ],
        "people_going_count": [],
        "follower": [],
        "following": []
    }*/

}
