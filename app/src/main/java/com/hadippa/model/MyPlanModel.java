package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 14-08-2016.
 */
public class MyPlanModel {


    /**
     * id : 12
     * user_id : 99
     * activity_type : 1
     * activity_details : {"activity_name":"test kartik"}
     * activity_location : vadodara
     * activity_location_lat : 22.3072
     * activity_location_lon : 73.1812
     * activity_date : 2016-08-30
     * active : 1
     * privacy : 0
     * created_at : 2016-07-10 23:50:00
     * updated_at : 2016-07-10 21:31:21
     * activity_time : 23:50
     * user : {"id":"99","first_name":"Vivek","last_name":"Singh","gender":"male","dob":null,"profile_photo":"http://www.hadipaa.folives.com/assets/images/profiles/user/https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/p480x480/13330920_1202279549804951_7115238095847875146_n.jpg?oh=d89facf6e341563dd3e56942e1e6136a&oe=5823B0CA&__gda__=1482049138_1f42cfdf8dfe92c87c60bda6c3b56846","age_range_from":"18","age_range_to":"99","private_account":"0","photo_uploaded":"1","age":0}
     * activity : {"id":"1","activity_name":"movie","activity_display_name":"Movie","activity_category":{"id":"1","activity_category_name":"entertainment","activity_category_display_name":"Entertainment"}}
     * people_going : []
     * people_approaching_count : []
     * people_going_count : []
     */

    private String id;
    private String user_id;
    private String activity_type;
    /**
     * activity_name : test kartik
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
    /**
     * id : 99
     * first_name : Vivek
     * last_name : Singh
     * gender : male
     * dob : null
     * profile_photo : http://www.hadipaa.folives.com/assets/images/profiles/user/https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xta1/v/t1.0-1/p480x480/13330920_1202279549804951_7115238095847875146_n.jpg?oh=d89facf6e341563dd3e56942e1e6136a&oe=5823B0CA&__gda__=1482049138_1f42cfdf8dfe92c87c60bda6c3b56846
     * age_range_from : 18
     * age_range_to : 99
     * private_account : 0
     * photo_uploaded : 1
     * age : 0
     */

    private UserBean user;
    /**
     * id : 1
     * activity_name : movie
     * activity_display_name : Movie
     * activity_category : {"id":"1","activity_category_name":"entertainment","activity_category_display_name":"Entertainment"}
     */

    private ActivityBean activity;
    private List<?> people_going;
    private List<?> people_approaching_count;
    private List<?> people_going_count;

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

    public List<?> getPeople_approaching_count() {
        return people_approaching_count;
    }

    public void setPeople_approaching_count(List<?> people_approaching_count) {
        this.people_approaching_count = people_approaching_count;
    }

    public List<?> getPeople_going_count() {
        return people_going_count;
    }

    public void setPeople_going_count(List<?> people_going_count) {
        this.people_going_count = people_going_count;
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
        private Object dob;
        private String profile_photo;
        private String age_range_from;
        private String age_range_to;
        private String private_account;
        private String photo_uploaded;
        private int age;

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

        public Object getDob() {
            return dob;
        }

        public void setDob(Object dob) {
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
    }

    public static class ActivityBean {
        private String id;
        private String activity_name;
        private String activity_display_name;
        /**
         * id : 1
         * activity_category_name : entertainment
         * activity_category_display_name : Entertainment
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
}
