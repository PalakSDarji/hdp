package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 02-08-2016.
 */
public class PeopleModel {


    /*
    * *
     * id : 12
     * first_name : Ravikant
     * last_name : Sahu
     * profile_photo : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c15.0.50.50/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?oh=31a919a3d795bc64ad02d0a1b462300e&oe=5736B82F&__gda__=1463225095_484ea4441f278e6b8895ab53a1be91a0
     * photo_uploaded : 0
     * profile_photo_thumbnail : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c15.0.50.50/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?oh=31a919a3d795bc64ad02d0a1b462300e&oe=5736B82F&__gda__=1463225095_484ea4441f278e6b8895ab53a1be91a0
     * mutual_friends :
     * user_relationship_status : null
     * follower : []
     * following : []
     */

    private String id;
    private String first_name;
    private String last_name;
    private String profile_photo;
    private String photo_uploaded;
    private String profile_photo_thumbnail;
    private String mutual_friends;
    private Object user_relationship_status;
    private List<?> follower;
    private List<?> following;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getProfile_photo() {
        return profile_photo;
    }

    public void setProfile_photo(String profile_photo) {
        this.profile_photo = profile_photo;
    }

    public String getPhoto_uploaded() {
        return photo_uploaded;
    }

    public void setPhoto_uploaded(String photo_uploaded) {
        this.photo_uploaded = photo_uploaded;
    }

    public String getProfile_photo_thumbnail() {
        return profile_photo_thumbnail;
    }

    public void setProfile_photo_thumbnail(String profile_photo_thumbnail) {
        this.profile_photo_thumbnail = profile_photo_thumbnail;
    }

    public String getMutual_friends() {
        return mutual_friends;
    }

    public void setMutual_friends(String mutual_friends) {
        this.mutual_friends = mutual_friends;
    }

    public Object getUser_relationship_status() {
        return user_relationship_status;
    }

    public void setUser_relationship_status(Object user_relationship_status) {
        this.user_relationship_status = user_relationship_status;
    }

    public List<?> getFollower() {
        return follower;
    }

    public void setFollower(List<?> follower) {
        this.follower = follower;
    }

    public List<?> getFollowing() {
        return following;
    }

    public void setFollowing(List<?> following) {
        this.following = following;
    }
}
