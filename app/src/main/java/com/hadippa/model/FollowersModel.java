package com.hadippa.model;

/**
 * Created by HP on 14-08-2016.
 */
public class FollowersModel {


    /**
     * id : 292
     * follower_id : 97
     * followed_id : 179
     * follow_accepted : 1
     * created_at : 2016-08-07 01:40:49
     * updated_at : 2016-08-07 01:40:49
     * follower : {"id":"97","first_name":"Kartik","last_name":"Mistry","profile_photo":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xtf1/v/t1.0-1/p480x480/13925049_1297151716992152_8001429379024720044_n.jpg?oh=cd368fa76ed6f5dc63b83f125f2c0b98&oe=581CEDFA&__gda__=1477538348_61044b0a58a465d8984584ab142e7d41","photo_uploaded":"1"}
     */

    private String id;
    private String follower_id;
    private String followed_id;
    private String follow_accepted;
    private String created_at;
    private String updated_at;
    /**
     * id : 97
     * first_name : Kartik
     * last_name : Mistry
     * profile_photo : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xtf1/v/t1.0-1/p480x480/13925049_1297151716992152_8001429379024720044_n.jpg?oh=cd368fa76ed6f5dc63b83f125f2c0b98&oe=581CEDFA&__gda__=1477538348_61044b0a58a465d8984584ab142e7d41
     * photo_uploaded : 1
     */

    private FollowerBean follower;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFollower_id() {
        return follower_id;
    }

    public void setFollower_id(String follower_id) {
        this.follower_id = follower_id;
    }

    public String getFollowed_id() {
        return followed_id;
    }

    public void setFollowed_id(String followed_id) {
        this.followed_id = followed_id;
    }

    public String getFollow_accepted() {
        return follow_accepted;
    }

    public void setFollow_accepted(String follow_accepted) {
        this.follow_accepted = follow_accepted;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public FollowerBean getFollower() {
        return follower;
    }

    public void setFollower(FollowerBean follower) {
        this.follower = follower;
    }

    public static class FollowerBean {
        private String id;
        private String first_name;
        private String last_name;
        private String profile_photo;
        private String photo_uploaded;

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
    }
}
