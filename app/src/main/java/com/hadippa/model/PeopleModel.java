package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 02-08-2016.
 */
public class PeopleModel {


    /**
     * id : 3
     * first_name : Aarya
     * last_name : Vora
     * profile_photo : https://scontent.fbom1-2.fna.fbcdn.net/v/t1.0-9/13501835_1262854777088513_6208341510195698306_n.jpg?oh=b93ae480f4f4527611762f0254e01508&oe=57F2330B
     * photo_uploaded : 0
     * profile_photo_thumbnail : https://scontent.fbom1-2.fna.fbcdn.net/v/t1.0-9/13501835_1262854777088513_6208341510195698306_n.jpg?oh=b93ae480f4f4527611762f0254e01508&oe=57F2330B
     * mutual_friends : Followed by Ranjeet Srinivas +2 more
     * user_relationship_status : Following
     * follower : [{"id":"330","follower_id":"97","followed_id":"99"}]
     * following : [{"id":"346","follower_id":"179","followed_id":"3"}]
     */

    private String id;
    private String first_name;
    private String last_name;
    private String profile_photo;
    private String photo_uploaded;
    private String profile_photo_thumbnail;
    private String mutual_friends;
    private String user_relationship_status;
    private boolean isChecked;
    /**
     * id : 330
     * follower_id : 97
     * followed_id : 99
     */

    private List<FollowerBean> follower;
    /**
     * id : 346
     * follower_id : 179
     * followed_id : 3
     */

    private List<FollowingBean> following;

    //Temp
    public PeopleModel(String first_name) {
        this.first_name = first_name;
    }

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

    public String getUser_relationship_status() {
        return user_relationship_status;
    }

    public void setUser_relationship_status(String user_relationship_status) {
        this.user_relationship_status = user_relationship_status;
    }

    public List<FollowerBean> getFollower() {
        return follower;
    }

    public void setFollower(List<FollowerBean> follower) {
        this.follower = follower;
    }

    public List<FollowingBean> getFollowing() {
        return following;
    }

    public void setFollowing(List<FollowingBean> following) {
        this.following = following;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public static class FollowerBean {
        private String id;
        private String follower_id;
        private String followed_id;

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
    }

    public static class FollowingBean {
        private String id;
        private String follower_id;
        private String followed_id;

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
    }
}
