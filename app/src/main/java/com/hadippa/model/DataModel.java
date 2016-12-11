package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 29-07-2016.
 */
public class DataModel{


    /**
     * id : 203
     * user_id : 187
     * activity_type : 8
     * activity_id : 0
     * activity_details : {"activity_name":"Amigos Cafe","from":"Mumbai, Maharashtra, India","to":"Varanasi, Uttar Pradesh, India"}
     * activity_location : Bandra, Mumbai
     * activity_location_lat : 18.9863
     * activity_location_lon : 72.8432
     * activity_date : 2016-12-30
     * cut_off_time : 0000-00-00 00:00:00
     * available_till : 0000-00-00 00:00:00
     * active : 1
     * privacy : null
     * hide_from : public
     * notification : 1
     * created_at : 2016-12-09 21:03:51
     * updated_at : 2016-12-09 21:03:51
     * activity_time : 12:11
     * mutual_friends : Followed by raj yadav +1 more
     * user_relationship_status : Following
     * user : {"id":187,"first_name":"Anand","last_name":"Yadav","gender":"male","dob":"1986-03-22","profile_photo":"1479983261_Anand.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":30,"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/1479983261_Anand.jpg","profile_img":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1479983261_Anand.jpg"}
     * activity : {"id":8,"activity_name":"flight","activity_display_name":"Flight","activity_category":{"id":3,"activity_category_name":"travel","activity_category_display_name":"Travel"}}
     * people_going : [{"id":396,"activity_id":197,"requester_id":99,"user":{"id":99,"first_name":"Vivek","last_name":"Singh","profile_photo":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/p480x480/13330920_1202279549804951_7115238095847875146_n.jpg?oh=d89facf6e341563dd3e56942e1e6136a&oe=5823B0CA&__gda__=1482049138_1f42cfdf8dfe92c87c60bda6c3b56846","photo_uploaded":1}},{"id":397,"activity_id":197,"requester_id":187,"user":{"id":187,"first_name":"Anand","last_name":"Yadav","profile_photo":"1479983261_Anand.jpg","photo_uploaded":1}},{"id":398,"activity_id":197,"requester_id":219,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"default.jpg","photo_uploaded":1}},{"id":399,"activity_id":197,"requester_id":215,"user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1}},{"id":400,"activity_id":197,"requester_id":5,"user":{"id":5,"first_name":"Shalu","last_name":"Soni","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/14390649_10205600821740105_8116670789966471774_n.jpg?oh=a958610695afa8fd56588548044efe47&oe=58F16866","photo_uploaded":0}},{"id":401,"activity_id":197,"requester_id":211,"user":{"id":211,"first_name":"Sahil","last_name":"Desai","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","photo_uploaded":0}}]
     * people_approaching_count : [{"id":396,"activity_id":197,"requester_id":99,"total":6}]
     * people_going_count : [{"id":396,"activity_id":197,"requester_id":99,"total":6}]
     * follower : [{"id":365,"follower_id":215,"followed_id":187}]
     * following : [{"id":365,"follower_id":215,"followed_id":187}]
     */

    private int id;
    private int user_id;
    private String activity_type;
    private int activity_id;
    private ActivityDetailsBean activity_details;
    private String activity_location;
    private double activity_location_lat;
    private double activity_location_lon;
    private String activity_date;
    private String cut_off_time;
    private String available_till;
    private int active;
    private Object privacy;
    private String hide_from;
    private int notification;
    private String created_at;
    private String updated_at;
    private String activity_time;
    private String mutual_friends;
    private String user_relationship_status;
    private UserBean user;
    private ActivityBean activity;
    private List<PeopleGoingBean> people_going;
    private List<PeopleApproachingCountBean> people_approaching_count;
    private List<PeopleGoingCountBean> people_going_count;
    private List<FollowerBean> follower;
    private List<FollowingBean> following;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
    }

    public int getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(int activity_id) {
        this.activity_id = activity_id;
    }

    public ActivityDetailsBean getActivity_details() {
        return activity_details;
    }

    public void setActivity_details(ActivityDetailsBean activity_details) {
        this.activity_details = activity_details;
    }

    public String getActivity_location() {
        return activity_location;
    }

    public void setActivity_location(String activity_location) {
        this.activity_location = activity_location;
    }

    public double getActivity_location_lat() {
        return activity_location_lat;
    }

    public void setActivity_location_lat(double activity_location_lat) {
        this.activity_location_lat = activity_location_lat;
    }

    public double getActivity_location_lon() {
        return activity_location_lon;
    }

    public void setActivity_location_lon(double activity_location_lon) {
        this.activity_location_lon = activity_location_lon;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public String getCut_off_time() {
        return cut_off_time;
    }

    public void setCut_off_time(String cut_off_time) {
        this.cut_off_time = cut_off_time;
    }

    public String getAvailable_till() {
        return available_till;
    }

    public void setAvailable_till(String available_till) {
        this.available_till = available_till;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public Object getPrivacy() {
        return privacy;
    }

    public void setPrivacy(Object privacy) {
        this.privacy = privacy;
    }

    public String getHide_from() {
        return hide_from;
    }

    public void setHide_from(String hide_from) {
        this.hide_from = hide_from;
    }

    public int getNotification() {
        return notification;
    }

    public void setNotification(int notification) {
        this.notification = notification;
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

    public String getActivity_time() {
        return activity_time;
    }

    public void setActivity_time(String activity_time) {
        this.activity_time = activity_time;
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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public ActivityBean getActivity() {
        return activity;
    }

    public void setActivity(ActivityBean activity) {
        this.activity = activity;
    }

    public List<PeopleGoingBean> getPeople_going() {
        return people_going;
    }

    public void setPeople_going(List<PeopleGoingBean> people_going) {
        this.people_going = people_going;
    }

    public List<PeopleApproachingCountBean> getPeople_approaching_count() {
        return people_approaching_count;
    }

    public void setPeople_approaching_count(List<PeopleApproachingCountBean> people_approaching_count) {
        this.people_approaching_count = people_approaching_count;
    }

    public List<PeopleGoingCountBean> getPeople_going_count() {
        return people_going_count;
    }

    public void setPeople_going_count(List<PeopleGoingCountBean> people_going_count) {
        this.people_going_count = people_going_count;
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

    public static class ActivityDetailsBean {
        /**
         * activity_name : Amigos Cafe
         * from : Mumbai, Maharashtra, India
         * to : Varanasi, Uttar Pradesh, India
         */

        private String activity_name;
        private String from;
        private String to;

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }
    }

    public static class UserBean {
        /**
         * id : 187
         * first_name : Anand
         * last_name : Yadav
         * gender : male
         * dob : 1986-03-22
         * profile_photo : 1479983261_Anand.jpg
         * age_range_from : 18
         * age_range_to : 99
         * private_account : 0
         * photo_uploaded : 1
         * age : 30
         * profile_photo_thumbnail : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/1479983261_Anand.jpg
         * profile_img : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1479983261_Anand.jpg
         */

        private int id;
        private String first_name;
        private String last_name;
        private String gender;
        private String dob;
        private String profile_photo;
        private int age_range_from;
        private int age_range_to;
        private int private_account;
        private int photo_uploaded;
        private int age;
        private String profile_photo_thumbnail;
        private String profile_img;

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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getProfile_photo() {
            return profile_photo;
        }

        public void setProfile_photo(String profile_photo) {
            this.profile_photo = profile_photo;
        }

        public int getAge_range_from() {
            return age_range_from;
        }

        public void setAge_range_from(int age_range_from) {
            this.age_range_from = age_range_from;
        }

        public int getAge_range_to() {
            return age_range_to;
        }

        public void setAge_range_to(int age_range_to) {
            this.age_range_to = age_range_to;
        }

        public int getPrivate_account() {
            return private_account;
        }

        public void setPrivate_account(int private_account) {
            this.private_account = private_account;
        }

        public int getPhoto_uploaded() {
            return photo_uploaded;
        }

        public void setPhoto_uploaded(int photo_uploaded) {
            this.photo_uploaded = photo_uploaded;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getProfile_photo_thumbnail() {
            return profile_photo_thumbnail;
        }

        public void setProfile_photo_thumbnail(String profile_photo_thumbnail) {
            this.profile_photo_thumbnail = profile_photo_thumbnail;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }
    }

    public static class ActivityBean {
        /**
         * id : 8
         * activity_name : flight
         * activity_display_name : Flight
         * activity_category : {"id":3,"activity_category_name":"travel","activity_category_display_name":"Travel"}
         */

        private int id;
        private String activity_name;
        private String activity_display_name;
        private ActivityCategoryBean activity_category;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }

        public String getActivity_display_name() {
            return activity_display_name;
        }

        public void setActivity_display_name(String activity_display_name) {
            this.activity_display_name = activity_display_name;
        }

        public ActivityCategoryBean getActivity_category() {
            return activity_category;
        }

        public void setActivity_category(ActivityCategoryBean activity_category) {
            this.activity_category = activity_category;
        }

        public static class ActivityCategoryBean {
            /**
             * id : 3
             * activity_category_name : travel
             * activity_category_display_name : Travel
             */

            private int id;
            private String activity_category_name;
            private String activity_category_display_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getActivity_category_name() {
                return activity_category_name;
            }

            public void setActivity_category_name(String activity_category_name) {
                this.activity_category_name = activity_category_name;
            }

            public String getActivity_category_display_name() {
                return activity_category_display_name;
            }

            public void setActivity_category_display_name(String activity_category_display_name) {
                this.activity_category_display_name = activity_category_display_name;
            }
        }
    }

    public static class PeopleGoingBean {
        /**
         * id : 396
         * activity_id : 197
         * requester_id : 99
         * user : {"id":99,"first_name":"Vivek","last_name":"Singh","profile_photo":"https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/p480x480/13330920_1202279549804951_7115238095847875146_n.jpg?oh=d89facf6e341563dd3e56942e1e6136a&oe=5823B0CA&__gda__=1482049138_1f42cfdf8dfe92c87c60bda6c3b56846","photo_uploaded":1}
         */

        private int id;
        private int activity_id;
        private int requester_id;
        private UserBeanX user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public int getRequester_id() {
            return requester_id;
        }

        public void setRequester_id(int requester_id) {
            this.requester_id = requester_id;
        }

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
            this.user = user;
        }

        public static class UserBeanX {
            /**
             * id : 99
             * first_name : Vivek
             * last_name : Singh
             * profile_photo : https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/p480x480/13330920_1202279549804951_7115238095847875146_n.jpg?oh=d89facf6e341563dd3e56942e1e6136a&oe=5823B0CA&__gda__=1482049138_1f42cfdf8dfe92c87c60bda6c3b56846
             * photo_uploaded : 1
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
    }

    public static class PeopleApproachingCountBean {
        /**
         * id : 396
         * activity_id : 197
         * requester_id : 99
         * total : 6
         */

        private int id;
        private int activity_id;
        private int requester_id;
        private int total;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public int getRequester_id() {
            return requester_id;
        }

        public void setRequester_id(int requester_id) {
            this.requester_id = requester_id;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class PeopleGoingCountBean {
        /**
         * id : 396
         * activity_id : 197
         * requester_id : 99
         * total : 6
         */

        private int id;
        private int activity_id;
        private int requester_id;
        private int total;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(int activity_id) {
            this.activity_id = activity_id;
        }

        public int getRequester_id() {
            return requester_id;
        }

        public void setRequester_id(int requester_id) {
            this.requester_id = requester_id;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class FollowerBean {
        /**
         * id : 365
         * follower_id : 215
         * followed_id : 187
         */

        private int id;
        private int follower_id;
        private int followed_id;

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
    }

    public static class FollowingBean {
        /**
         * id : 365
         * follower_id : 215
         * followed_id : 187
         */

        private int id;
        private int follower_id;
        private int followed_id;

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
    }
}
