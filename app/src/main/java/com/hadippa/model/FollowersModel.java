package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 14-08-2016.
 */
public class FollowersModel {


    /**
     * success : true
     * followers : [{"id":309,"follower_id":211,"followed_id":219,"follow_accepted":1,"created_at":"2016-06-18 00:00:00","updated_at":"2016-06-18 00:00:00","user_relationship_status":"Follower","follower":{"id":211,"first_name":"Sahil","last_name":"Desai","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","photo_uploaded":0},"followed":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1,"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}},{"id":319,"follower_id":187,"followed_id":219,"follow_accepted":1,"created_at":"2016-11-20 02:02:24","updated_at":"2016-11-20 02:02:24","user_relationship_status":"Follower","follower":{"id":187,"first_name":"Anand","last_name":"Yadav","profile_photo":"1479983261_Anand.jpg","photo_uploaded":1},"followed":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1,"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<FollowersBean> followers;

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

    public List<FollowersBean> getFollowers() {
        return followers;
    }

    public void setFollowers(List<FollowersBean> followers) {
        this.followers = followers;
    }

    public static class FollowersBean {
        /**
         * id : 309
         * follower_id : 211
         * followed_id : 219
         * follow_accepted : 1
         * created_at : 2016-06-18 00:00:00
         * updated_at : 2016-06-18 00:00:00
         * user_relationship_status : Follower
         * follower : {"id":211,"first_name":"Sahil","last_name":"Desai","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","photo_uploaded":0}
         * followed : {"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1,"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}
         */

        private int id;
        private int follower_id;
        private int followed_id;
        private int follow_accepted;
        private String created_at;
        private String updated_at;
        private String user_relationship_status;
        private FollowerBean follower;
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

        public FollowerBean getFollower() {
            return follower;
        }

        public void setFollower(FollowerBean follower) {
            this.follower = follower;
        }

        public FollowedBean getFollowed() {
            return followed;
        }

        public void setFollowed(FollowedBean followed) {
            this.followed = followed;
        }

        public static class FollowerBean {
            /**
             * id : 211
             * first_name : Sahil
             * last_name : Desai
             * profile_photo : https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762
             * photo_uploaded : 0
             */

            private int id;
            private String first_name;
            private String last_name;
            private String profile_photo;
            private int photo_uploaded;

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
        }

        public static class FollowedBean {
            /**
             * id : 219
             * first_name : sahil
             * last_name : desaiiiii
             * profile_photo : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg
             * photo_uploaded : 1
             * profile_photo_thumbnail : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg
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
