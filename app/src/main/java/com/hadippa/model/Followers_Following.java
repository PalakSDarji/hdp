package com.hadippa.model;

/**
 * Created by HP on 01-08-2016.
 */
public class Followers_Following {


    /**
     * id : 232
     * follower_id : 97
     * followed_id : 1
     * follow_accepted : 1
     * created_at : 2016-07-22 03:39:40
     * updated_at : 2016-07-22 03:39:40
     * followed : {"id":"1","first_name":"Ranjeet","last_name":"Srinivas","profile_photo":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/p50x50/13332751_10156884196075468_6345222256775835665_n.jpg?oh=757255cf0d867504b7ea56c1d356a15e&oe=580412A3&__gda__=1476521438_b5014ff7d352e7676d12d3cdd2524a16","photo_uploaded":"0","profile_photo_thumbnail":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/p50x50/13332751_10156884196075468_6345222256775835665_n.jpg?oh=757255cf0d867504b7ea56c1d356a15e&oe=580412A3&__gda__=1476521438_b5014ff7d352e7676d12d3cdd2524a16"}
     */

    private String id;
    private String follower_id;
    private String followed_id;
    private String follow_accepted;
    private String created_at;
    private String updated_at;
    /**
     * id : 1
     * first_name : Ranjeet
     * last_name : Srinivas
     * profile_photo : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/p50x50/13332751_10156884196075468_6345222256775835665_n.jpg?oh=757255cf0d867504b7ea56c1d356a15e&oe=580412A3&__gda__=1476521438_b5014ff7d352e7676d12d3cdd2524a16
     * photo_uploaded : 0
     * profile_photo_thumbnail : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpt1/v/t1.0-1/p50x50/13332751_10156884196075468_6345222256775835665_n.jpg?oh=757255cf0d867504b7ea56c1d356a15e&oe=580412A3&__gda__=1476521438_b5014ff7d352e7676d12d3cdd2524a16
     */

    private FollowedBean followed;

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

    public FollowedBean getFollowed() {
        return followed;
    }

    public void setFollowed(FollowedBean followed) {
        this.followed = followed;
    }

    public static class FollowedBean {
        private String id;
        private String first_name;
        private String last_name;
        private String profile_photo;
        private String photo_uploaded;
        private String profile_photo_thumbnail;

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
    }
}
