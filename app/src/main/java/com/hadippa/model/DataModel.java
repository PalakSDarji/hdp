package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 29-07-2016.
 */
public class DataModel{


    /**
     * id : 4
     * user_id : 13
     * activity_type : 4
     * activity_details : {"activity_name":"Oppa Bar"}
     * activity_location : Andheri (E)
     * activity_location_lat : 78.8797
     * activity_location_lon : 78.8797
     * activity_date : 2016-07-30
     * active : 1
     * privacy : 0
     * created_at : 2016-06-18 00:00:00
     * updated_at : 2016-06-18 00:00:00
     * activity_time : 23:50
     * mutual_friends :
     * user : {"id":"13","first_name":"Parul","last_name":"Thakur","gender":"female","dob":"1991-09-04","profile_photo":"http://www.hadipaa.folives.com/assets/images/profiles/user/default.jpg","age_range_from":"18","age_range_to":"99","private_account":"0","photo_uploaded":"1","age":24,"profile_photo_thumbnail":"http://www.hadipaa.folives.com/assets/images/profiles/user/thumbnails/default.jpg"}
     * activity : {"id":"4","activity_name":"night_club","activity_display_name":"Night Club","activity_category":{"id":"2","activity_category_name":"nightlife","activity_category_display_name":"Night Life"}}
     * people_going : []
     * people_approaching_count : [{"id":"267","activity_id":"4","requester_id":"99","total":"1"}]
     * people_going_count : []
     * follower : []
     * following : []
     */

    private String id;
    private String user_id;
    private String activity_type;
    /**
     * activity_name : Oppa Bar
     */

    private ActivityDetailsBean activity_details;
    private String activity_location;
    private String activity_location_lat;
    private String activity_location_lon;
    private String activity_date;
    private String active;
    private String privacy;
    private String created_at;
    private String updated_at;
    private String activity_time;
    private String mutual_friends;
    /**
     * id : 13
     * first_name : Parul
     * last_name : Thakur
     * gender : female
     * dob : 1991-09-04
     * profile_photo : http://www.hadipaa.folives.com/assets/images/profiles/user/default.jpg
     * age_range_from : 18
     * age_range_to : 99
     * private_account : 0
     * photo_uploaded : 1
     * age : 24
     * profile_photo_thumbnail : http://www.hadipaa.folives.com/assets/images/profiles/user/thumbnails/default.jpg
     */

    private UserBean user;
    /**
     * id : 4
     * activity_name : night_club
     * activity_display_name : Night Club
     * activity_category : {"id":"2","activity_category_name":"nightlife","activity_category_display_name":"Night Life"}
     */

    private ActivityBean activity;
    private List<?> people_going;
    /**
     * id : 267
     * activity_id : 4
     * requester_id : 99
     * total : 1
     */

    private List<PeopleApproachingCountBean> people_approaching_count;
    private List<?> people_going_count;
    private List<?> follower;
    private List<?> following;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getActivity_type() {
        return activity_type;
    }

    public void setActivity_type(String activity_type) {
        this.activity_type = activity_type;
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

    public String getActivity_location_lat() {
        return activity_location_lat;
    }

    public void setActivity_location_lat(String activity_location_lat) {
        this.activity_location_lat = activity_location_lat;
    }

    public String getActivity_location_lon() {
        return activity_location_lon;
    }

    public void setActivity_location_lon(String activity_location_lon) {
        this.activity_location_lon = activity_location_lon;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
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

    public List<?> getPeople_going() {
        return people_going;
    }

    public void setPeople_going(List<?> people_going) {
        this.people_going = people_going;
    }

    public List<PeopleApproachingCountBean> getPeople_approaching_count() {
        return people_approaching_count;
    }

    public void setPeople_approaching_count(List<PeopleApproachingCountBean> people_approaching_count) {
        this.people_approaching_count = people_approaching_count;
    }

    public List<?> getPeople_going_count() {
        return people_going_count;
    }

    public void setPeople_going_count(List<?> people_going_count) {
        this.people_going_count = people_going_count;
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

    public static class ActivityDetailsBean {
        private String activity_name;

        public String getActivity_name() {
            return activity_name;
        }

        public void setActivity_name(String activity_name) {
            this.activity_name = activity_name;
        }
    }

    public static class UserBean {
        private String id;
        private String first_name;
        private String last_name;
        private String gender;
        private String dob;
        private String profile_photo;
        private String age_range_from;
        private String age_range_to;
        private String private_account;
        private String photo_uploaded;
        private int age;
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

        public String getAge_range_from() {
            return age_range_from;
        }

        public void setAge_range_from(String age_range_from) {
            this.age_range_from = age_range_from;
        }

        public String getAge_range_to() {
            return age_range_to;
        }

        public void setAge_range_to(String age_range_to) {
            this.age_range_to = age_range_to;
        }

        public String getPrivate_account() {
            return private_account;
        }

        public void setPrivate_account(String private_account) {
            this.private_account = private_account;
        }

        public String getPhoto_uploaded() {
            return photo_uploaded;
        }

        public void setPhoto_uploaded(String photo_uploaded) {
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
    }

    public static class ActivityBean {
        private String id;
        private String activity_name;
        private String activity_display_name;
        /**
         * id : 2
         * activity_category_name : nightlife
         * activity_category_display_name : Night Life
         */

        private ActivityCategoryBean activity_category;

        public String getId() {
            return id;
        }

        public void setId(String id) {
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
            private String id;
            private String activity_category_name;
            private String activity_category_display_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
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

    public static class PeopleApproachingCountBean {
        private String id;
        private String activity_id;
        private String requester_id;
        private String total;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
        }

        public String getRequester_id() {
            return requester_id;
        }

        public void setRequester_id(String requester_id) {
            this.requester_id = requester_id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }
    }
}
