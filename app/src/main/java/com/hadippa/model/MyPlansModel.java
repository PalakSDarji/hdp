package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 18-11-2016.
 */

public class MyPlansModel {


    /**
     * success : true
     * my_plans : [{"id":165,"user_id":219,"activity_type":"12","activity_details":{"activity_name":"Cafe Bistro"},"activity_location":"Opposite Galaria Mall, Akota, Vadodara","activity_location_lat":22.2968,"activity_location_lon":73.1719,"activity_date":"2016-11-24","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2016-11-18 19:45:35","updated_at":"2016-11-18 19:45:35","activity_time":"21:10","user":{"id":219,"first_name":"sahil","last_name":"dessai","gender":"male","dob":"1988-11-12","profile_photo":"http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":28},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]},{"id":166,"user_id":219,"activity_type":"3","activity_details":{"activity_name":"The Dollar Business Grow Workshop Vadodara Edition 2016-17"},"activity_location":"Sayajiganj,","activity_location_lat":22.2968,"activity_location_lon":73.1719,"activity_date":"2016-11-23","cut_off_time":"0000-00-00 00:00:00","available_till":"2016-11-23 22:10:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2016-11-18 19:53:46","updated_at":"2016-11-18 19:53:46","activity_time":"19:10","user":{"id":219,"first_name":"sahil","last_name":"dessai","gender":"male","dob":"1988-11-12","profile_photo":"http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":28},"activity":{"id":3,"activity_name":"event","activity_display_name":"Event","activity_category":{"id":1,"activity_category_name":"entertainment","activity_category_display_name":"Entertainment"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]},{"id":167,"user_id":219,"activity_type":"12","activity_details":{"activity_name":"Cafe Bistro"},"activity_location":"Opposite Galaria Mall, Akota, Vadodara","activity_location_lat":22.2968,"activity_location_lon":73.1716,"activity_date":"2016-11-25","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2016-11-18 19:55:28","updated_at":"2016-11-18 19:55:28","activity_time":"19:10","user":{"id":219,"first_name":"sahil","last_name":"dessai","gender":"male","dob":"1988-11-12","profile_photo":"http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":28},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]},{"id":168,"user_id":219,"activity_type":"8","activity_details":{"activity_name":"Amigos Cafe","from":"Mumbai","to":"Delhi"},"activity_location":"Bandra, Mumbai","activity_location_lat":22.2968,"activity_location_lon":73.1716,"activity_date":"2016-11-23","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2016-11-18 21:01:30","updated_at":"2016-11-18 21:01:30","activity_time":"18:10","user":{"id":219,"first_name":"sahil","last_name":"dessai","gender":"male","dob":"1988-11-12","profile_photo":"http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":28},"activity":{"id":8,"activity_name":"flight","activity_display_name":"Flight","activity_category":{"id":3,"activity_category_name":"travel","activity_category_display_name":"Travel"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<MyPlansBean> my_plans;

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

    public List<MyPlansBean> getMy_plans() {
        return my_plans;
    }

    public void setMy_plans(List<MyPlansBean> my_plans) {
        this.my_plans = my_plans;
    }

    public static class MyPlansBean {
        /**
         * id : 165
         * user_id : 219
         * activity_type : 12
         * activity_details : {"activity_name":"Cafe Bistro"}
         * activity_location : Opposite Galaria Mall, Akota, Vadodara
         * activity_location_lat : 22.2968
         * activity_location_lon : 73.1719
         * activity_date : 2016-11-24
         * cut_off_time : 0000-00-00 00:00:00
         * available_till : 0000-00-00 00:00:00
         * active : 1
         * privacy : null
         * hide_from : public
         * notification : 1
         * created_at : 2016-11-18 19:45:35
         * updated_at : 2016-11-18 19:45:35
         * activity_time : 21:10
         * user : {"id":219,"first_name":"sahil","last_name":"dessai","gender":"male","dob":"1988-11-12","profile_photo":"http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"age":28}
         * activity : {"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}}
         * people_going : []
         * people_approaching_count : []
         * people_going_count : []
         */

        private int id;
        private int user_id;
        private String activity_type;
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
        private UserBean user;
        private ActivityBean activity;
        private List<?> people_going;
        private List<?> people_approaching_count;
        private List<?> people_going_count;
        private boolean isOpened;

        public boolean isOpened() {
            return isOpened;
        }

        public void setOpened(boolean opened) {
            isOpened = opened;
        }

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
            /**
             * activity_name : Cafe Bistro
             */

            private String activity_name;

            public String getActivity_name() {
                return activity_name;
            }

            public void setActivity_name(String activity_name) {
                this.activity_name = activity_name;
            }
        }

        public static class UserBean {
            /**
             * id : 219
             * first_name : sahil
             * last_name : dessai
             * gender : male
             * dob : 1988-11-12
             * profile_photo : http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/http://hadipaa.dev.tnxlabs.com/assets/images/profiles/user/default.jpg
             * age_range_from : 18
             * age_range_to : 99
             * private_account : 0
             * photo_uploaded : 1
             * age : 28
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
        }

        public static class ActivityBean {
            /**
             * id : 12
             * activity_name : coffee
             * activity_display_name : Coffee
             * activity_category : {"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}
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
                 * id : 4
                 * activity_category_name : coffee
                 * activity_category_display_name : Coffee
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
    }
}
