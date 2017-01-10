package com.hadippa.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 20-11-2016.
 */

public class UserProfile implements Serializable {


    /**
      success : true
      user : {"id":226,"first_name":"aliakbar","last_name":"p","username":"ap@gmail.com","email":"ap@gmail.com","gender":"male","dob":"1995-12-09","company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345761,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"default.jpg","profile_photo_1":"1483721844_aliakbar.jpg","profile_photo_2":"1483820474_aliakbar.jpg","profile_photo_3":"1483820642_aliakbar.jpg","profile_photo_4":null,"profile_photo_5":null,"photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-12-09 20:59:49","updated_at":"2017-01-09 21:43:36","age":21,"profile_photos":["http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483721844_aliakbar.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483820474_aliakbar.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483820642_aliakbar.jpg"]}
      activity_count : 5
      activity : [{"id":259,"user_id":226,"activity_type":"12",
      "activity_id":18260810,"activity_details":{"activity_name":"Tea Post"},"activity_location":"G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara","activity_location_lat":22.3062,"activity_location_lon":73.1991,"activity_date":"2017-01-16","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2017-01-01 00:56:04","updated_at":"2017-01-01 00:56:04","activity_time":"03:00","user":{"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[{"id":19,"activity_id":259,"requester_id":219,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"1481390529_sahil.jpg","photo_uploaded":1}}],"people_approaching_count":[{"id":19,"activity_id":259,"requester_id":219,"total":1}],"people_going_count":[{"id":19,"activity_id":259,"requester_id":219,"total":1}]},{"id":260,"user_id":226,"activity_type":"12","activity_id":18260810,"activity_details":{"activity_name":"Tea Post"},"activity_location":"G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara","activity_location_lat":22.3061,"activity_location_lon":73.1994,"activity_date":"2017-01-16","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2017-01-01 01:06:22","updated_at":"2017-01-01 01:06:22","activity_time":"05:00","user":{"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[{"id":20,"activity_id":260,"requester_id":219,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"1481390529_sahil.jpg","photo_uploaded":1}}],"people_approaching_count":[{"id":20,"activity_id":260,"requester_id":219,"total":1}],"people_going_count":[{"id":20,"activity_id":260,"requester_id":219,"total":1}]},{"id":261,"user_id":226,"activity_type":"12","activity_id":18260810,"activity_details":{"activity_name":"Tea Post"},"activity_location":"G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara","activity_location_lat":22.3061,"activity_location_lon":73.1994,"activity_date":"2017-01-16","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2017-01-01 01:09:35","updated_at":"2017-01-01 01:09:35","activity_time":"05:00","user":{"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]},{"id":262,"user_id":226,"activity_type":"12","activity_id":18260810,"activity_details":{"activity_name":"Tea Post"},"activity_location":"G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara","activity_location_lat":22.3061,"activity_location_lon":73.1994,"activity_date":"2017-01-16","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2017-01-01 01:14:01","updated_at":"2017-01-01 01:14:01","activity_time":"07:00","user":{"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]},{"id":263,"user_id":226,"activity_type":"12","activity_id":18260810,"activity_details":{"activity_name":"Tea Post"},"activity_location":"G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara","activity_location_lat":22.3062,"activity_location_lon":73.1991,"activity_date":"2017-01-16","cut_off_time":"0000-00-00 00:00:00","available_till":"0000-00-00 00:00:00","active":1,"privacy":null,"hide_from":"public","notification":1,"created_at":"2017-01-01 01:18:19","updated_at":"2017-01-01 01:18:19","activity_time":"09:00","user":{"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21},"activity":{"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}},"people_going":[],"people_approaching_count":[],"people_going_count":[]}]
      next : null
     */

    private boolean success;
    private UserBean user;
    private int activity_count;
    private Object next;
    private List<ActivityBeanX> activity;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getActivity_count() {
        return activity_count;
    }

    public void setActivity_count(int activity_count) {
        this.activity_count = activity_count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public List<ActivityBeanX> getActivity() {
        return activity;
    }

    public void setActivity(List<ActivityBeanX> activity) {
        this.activity = activity;
    }

    public static class UserBean implements Serializable{
        /**
         * id : 226
         * first_name : aliakbar
         * last_name : p
         * username : ap@gmail.com
         * email : ap@gmail.com
         * gender : male
         * dob : 1995-12-09
         * company :
         * occupation :
         * about_me :
         * interested_in : both
         * zodiac :
         * lanuage_known :
         * city :
         * age_range_from : 18
         * age_range_to : 99
         * fb_id : 0
         * mobile : 7878345761
         * mobile_verified : 1
         * mobile_verification_code : 0
         * email_verified : 0
         * email_verification_code :
         * password_change_verification_code :
         * profile_photo : default.jpg
         * profile_photo_1 : 1483721844_aliakbar.jpg
         * profile_photo_2 : 1483820474_aliakbar.jpg
         * profile_photo_3 : 1483820642_aliakbar.jpg
         * profile_photo_4 : null
         * profile_photo_5 : null
         * photo_uploaded : 1
         * active : 1
         * private_account : 0
         * current_lat : 0
         * current_lon : 0
         * radius : 100
         * registration_complete : 3
         * device_token : dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx
         * device_type : android
         * device_id : 88baca3a5682275d
         * device_os_version : 23
         * created_at : 2016-12-09 20:59:49
         * updated_at : 2017-01-09 21:43:36
         * age : 21
         * profile_photos : ["http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483721844_aliakbar.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483820474_aliakbar.jpg","http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1483820642_aliakbar.jpg"]
         */

        private int id;
        private String first_name;
        private String last_name;
        private String username;
        private String email;
        private String gender;
        private String dob;
        private String company;
        private String occupation;
        private String about_me;
        private String interested_in;
        private String zodiac;
        private String lanuage_known;
        private String city;
        private int age_range_from;
        private int age_range_to;
        private int fb_id;
        private long mobile;
        private int mobile_verified;
        private int mobile_verification_code;
        private int email_verified;
        private String email_verification_code;
        private String password_change_verification_code;
        private String profile_photo;
        private String profile_photo_1;
        private String profile_photo_2;
        private String profile_photo_3;
        private Object profile_photo_4;
        private Object profile_photo_5;
        private int photo_uploaded;
        private int active;
        private int private_account;
        private int current_lat;
        private int current_lon;
        private int radius;
        private int registration_complete;
        private String device_token;
        private String device_type;
        private String device_id;
        private String device_os_version;
        private String created_at;
        private String updated_at;
        private int age;
        private List<String> profile_photos;

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

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
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

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }

        public String getAbout_me() {
            return about_me;
        }

        public void setAbout_me(String about_me) {
            this.about_me = about_me;
        }

        public String getInterested_in() {
            return interested_in;
        }

        public void setInterested_in(String interested_in) {
            this.interested_in = interested_in;
        }

        public String getZodiac() {
            return zodiac;
        }

        public void setZodiac(String zodiac) {
            this.zodiac = zodiac;
        }

        public String getLanuage_known() {
            return lanuage_known;
        }

        public void setLanuage_known(String lanuage_known) {
            this.lanuage_known = lanuage_known;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public int getFb_id() {
            return fb_id;
        }

        public void setFb_id(int fb_id) {
            this.fb_id = fb_id;
        }

        public long getMobile() {
            return mobile;
        }

        public void setMobile(long mobile) {
            this.mobile = mobile;
        }

        public int getMobile_verified() {
            return mobile_verified;
        }

        public void setMobile_verified(int mobile_verified) {
            this.mobile_verified = mobile_verified;
        }

        public int getMobile_verification_code() {
            return mobile_verification_code;
        }

        public void setMobile_verification_code(int mobile_verification_code) {
            this.mobile_verification_code = mobile_verification_code;
        }

        public int getEmail_verified() {
            return email_verified;
        }

        public void setEmail_verified(int email_verified) {
            this.email_verified = email_verified;
        }

        public String getEmail_verification_code() {
            return email_verification_code;
        }

        public void setEmail_verification_code(String email_verification_code) {
            this.email_verification_code = email_verification_code;
        }

        public String getPassword_change_verification_code() {
            return password_change_verification_code;
        }

        public void setPassword_change_verification_code(String password_change_verification_code) {
            this.password_change_verification_code = password_change_verification_code;
        }

        public String getProfile_photo() {
            return profile_photo;
        }

        public void setProfile_photo(String profile_photo) {
            this.profile_photo = profile_photo;
        }

        public String getProfile_photo_1() {
            return profile_photo_1;
        }

        public void setProfile_photo_1(String profile_photo_1) {
            this.profile_photo_1 = profile_photo_1;
        }

        public String getProfile_photo_2() {
            return profile_photo_2;
        }

        public void setProfile_photo_2(String profile_photo_2) {
            this.profile_photo_2 = profile_photo_2;
        }

        public String getProfile_photo_3() {
            return profile_photo_3;
        }

        public void setProfile_photo_3(String profile_photo_3) {
            this.profile_photo_3 = profile_photo_3;
        }

        public Object getProfile_photo_4() {
            return profile_photo_4;
        }

        public void setProfile_photo_4(Object profile_photo_4) {
            this.profile_photo_4 = profile_photo_4;
        }

        public Object getProfile_photo_5() {
            return profile_photo_5;
        }

        public void setProfile_photo_5(Object profile_photo_5) {
            this.profile_photo_5 = profile_photo_5;
        }

        public int getPhoto_uploaded() {
            return photo_uploaded;
        }

        public void setPhoto_uploaded(int photo_uploaded) {
            this.photo_uploaded = photo_uploaded;
        }

        public int getActive() {
            return active;
        }

        public void setActive(int active) {
            this.active = active;
        }

        public int getPrivate_account() {
            return private_account;
        }

        public void setPrivate_account(int private_account) {
            this.private_account = private_account;
        }

        public int getCurrent_lat() {
            return current_lat;
        }

        public void setCurrent_lat(int current_lat) {
            this.current_lat = current_lat;
        }

        public int getCurrent_lon() {
            return current_lon;
        }

        public void setCurrent_lon(int current_lon) {
            this.current_lon = current_lon;
        }

        public int getRadius() {
            return radius;
        }

        public void setRadius(int radius) {
            this.radius = radius;
        }

        public int getRegistration_complete() {
            return registration_complete;
        }

        public void setRegistration_complete(int registration_complete) {
            this.registration_complete = registration_complete;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getDevice_id() {
            return device_id;
        }

        public void setDevice_id(String device_id) {
            this.device_id = device_id;
        }

        public String getDevice_os_version() {
            return device_os_version;
        }

        public void setDevice_os_version(String device_os_version) {
            this.device_os_version = device_os_version;
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

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<String> getProfile_photos() {
            return profile_photos;
        }

        public void setProfile_photos(List<String> profile_photos) {
            this.profile_photos = profile_photos;
        }
    }

    public static class ActivityBeanX  implements Serializable{
        /**
         * id : 259
         * user_id : 226
         * activity_type : 12
         * activity_id : 18260810
         * activity_details : {"activity_name":"Tea Post"}
         * activity_location : G13 Anannya Complex, Akshar Chowk, Mandvi, Vadodara
         * activity_location_lat : 22.3062
         * activity_location_lon : 73.1991
         * activity_date : 2017-01-16
         * cut_off_time : 0000-00-00 00:00:00
         * available_till : 0000-00-00 00:00:00
         * active : 1
         * privacy : null
         * hide_from : public
         * notification : 1
         * created_at : 2017-01-01 00:56:04
         * updated_at : 2017-01-01 00:56:04
         * activity_time : 03:00
         * user : {"id":226,"first_name":"aliakbar","last_name":"p","gender":"male","dob":"1995-12-09","profile_photo":"default.jpg","age_range_from":18,"age_range_to":99,"private_account":0,"photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","profile_photo_thumbnail_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg","age":21}
         * activity : {"id":12,"activity_name":"coffee","activity_display_name":"Coffee","activity_category":{"id":4,"activity_category_name":"coffee","activity_category_display_name":"Coffee"}}
         * people_going : [{"id":19,"activity_id":259,"requester_id":219,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"1481390529_sahil.jpg","photo_uploaded":1}}]
         * people_approaching_count : [{"id":19,"activity_id":259,"requester_id":219,"total":1}]
         * people_going_count : [{"id":19,"activity_id":259,"requester_id":219,"total":1}]
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
        private UserBeanX user;
        private ActivityBean activity;
        private List<PeopleGoingBean> people_going;
        private List<PeopleApproachingCountBean> people_approaching_count;
        private List<PeopleGoingCountBean> people_going_count;

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

        public UserBeanX getUser() {
            return user;
        }

        public void setUser(UserBeanX user) {
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

        public static class ActivityDetailsBean  implements Serializable{
            /**
             * activity_name : Tea Post
             */

            private String activity_name;

            public String getActivity_name() {
                return activity_name;
            }

            public void setActivity_name(String activity_name) {
                this.activity_name = activity_name;
            }
        }

        public static class UserBeanX  implements Serializable{
            /**
             * id : 226
             * first_name : aliakbar
             * last_name : p
             * gender : male
             * dob : 1995-12-09
             * profile_photo : default.jpg
             * age_range_from : 18
             * age_range_to : 99
             * private_account : 0
             * photo_uploaded : 1
             * profile_photo_url : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg
             * profile_photo_thumbnail_url : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg
             * age : 21
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
            private String profile_photo_url;
            private String profile_photo_thumbnail_url;
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

            public String getProfile_photo_url() {
                return profile_photo_url;
            }

            public void setProfile_photo_url(String profile_photo_url) {
                this.profile_photo_url = profile_photo_url;
            }

            public String getProfile_photo_thumbnail_url() {
                return profile_photo_thumbnail_url;
            }

            public void setProfile_photo_thumbnail_url(String profile_photo_thumbnail_url) {
                this.profile_photo_thumbnail_url = profile_photo_thumbnail_url;
            }

            public int getAge() {
                return age;
            }

            public void setAge(int age) {
                this.age = age;
            }
        }

        public static class ActivityBean  implements Serializable{
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

            public static class ActivityCategoryBean  implements Serializable{
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

        public static class PeopleGoingBean  implements Serializable{
            /**
             * id : 19
             * activity_id : 259
             * requester_id : 219
             * user : {"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"1481390529_sahil.jpg","photo_uploaded":1}
             */

            private int id;
            private int activity_id;
            private int requester_id;
            private UserBeanXX user;

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

            public UserBeanXX getUser() {
                return user;
            }

            public void setUser(UserBeanXX user) {
                this.user = user;
            }

            public static class UserBeanXX  implements Serializable{
                /**
                 * id : 219
                 * first_name : sahil
                 * last_name : desaiiiii
                 * profile_photo : 1481390529_sahil.jpg
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

        public static class PeopleApproachingCountBean  implements Serializable{
            /**
             * id : 19
             * activity_id : 259
             * requester_id : 219
             * total : 1
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

        public static class PeopleGoingCountBean  implements Serializable{
            /**
             * id : 19
             * activity_id : 259
             * requester_id : 219
             * total : 1
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
    }
}
