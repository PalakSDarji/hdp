package com.hadippa.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 01-08-2016.
 */
public class FollowingModel implements Serializable{


    /**
     * success : true
     * following : [{"id":317,"follower_id":219,"followed_id":4,"follow_accepted":1,"created_at":"2016-11-20 02:02:24","updated_at":"2016-11-20 02:02:24","user_relationship_status":"Following","followed":{"id":4,"first_name":"Vishal","last_name":"Patni","profile_photo":"https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal","photo_uploaded":0,"profile_photo_thumbnail":"https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal"}},{"id":351,"follower_id":219,"followed_id":213,"follow_accepted":1,"created_at":"2016-11-27 17:54:57","updated_at":"2016-11-27 17:54:57","user_relationship_status":"Following","followed":{"id":213,"first_name":"kartik","last_name":"mistry","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1,"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<FollowingBean> following;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public List<FollowingBean> getFollowing() {
        return following;
    }

    public void setFollowing(List<FollowingBean> following) {
        this.following = following;
    }

    public static class FollowingBean implements Serializable{
        /**
         * id : 317
         * follower_id : 219
         * followed_id : 4
         * follow_accepted : 1
         * created_at : 2016-11-20 02:02:24
         * updated_at : 2016-11-20 02:02:24
         * user_relationship_status : Following
         * followed : {"id":4,"first_name":"Vishal","last_name":"Patni","profile_photo":"https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal","photo_uploaded":0,"profile_photo_thumbnail":"https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal"}
         */

        private int id;
        private int follower_id;
        private int followed_id;
        private int follow_accepted;
        private String created_at;
        private String updated_at;
        private String user_relationship_status;
        private FollowedBean followed;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getFollower_id() {
            return follower_id;
        }

        public void setFollower_id(int follower_id) {
            this.follower_id = follower_id;
        }

        public int getFollowed_id() {
            return followed_id;
        }

        public void setFollowed_id(int followed_id) {
            this.followed_id = followed_id;
        }

        public int getFollow_accepted() {
            return follow_accepted;
        }

        public void setFollow_accepted(int follow_accepted) {
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

        public String getUser_relationship_status() {
            return user_relationship_status;
        }

        public void setUser_relationship_status(String user_relationship_status) {
            this.user_relationship_status = user_relationship_status;
        }

        public FollowedBean getFollowed() {
            return followed;
        }

        public void setFollowed(FollowedBean followed) {
            this.followed = followed;
        }

        public static class FollowedBean implements Serializable {
            /**
             * id : 4
             * first_name : Vishal
             * last_name : Patni
             * profile_photo : https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal
             * photo_uploaded : 0
             * profile_photo_thumbnail : https://graph.facebook.com/v2.4/10153226160688165/picture?type=normal
             */

            private int id;
            private String first_name;
            private String last_name;
            private String profile_photo;
            private int photo_uploaded;
            private String profile_photo_thumbnail;

            public int getId() {
                return id;
            }

            public void setId(int id) {
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

            public int getPhoto_uploaded() {
                return photo_uploaded;
            }

            public void setPhoto_uploaded(int photo_uploaded) {
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
}
