package com.hadippa.model;

import java.io.Serializable;

/**
 * Created by HP on 20-11-2016.
 */

public class UserProfile implements Serializable {


    /**
     * success : true
     * user : {"id":3,"first_name":"Aarya","last_name":"Vora","username":"aaryavora","email":"aaryavora@gmail.com","gender":"female","dob":30,"company":"","occupation":"","about_me":"","interested_in":"male","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":10207344382830461,"mobile":9819151843,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.fbom1-2.fna.fbcdn.net/v/t1.0-9/13501835_1262854777088513_6208341510195698306_n.jpg?oh=b93ae480f4f4527611762f0254e01508&oe=57F2330B","photo_uploaded":0,"active":1,"private_account":0,"current_lat":35.12497,"current_lon":117.13833,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2015-09-10 21:58:27","updated_at":"2015-09-10 21:58:27"}
     */

    private boolean success;
    private UserBean user;

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

    public static class UserBean implements Serializable {
        /**
         * id : 3
         * first_name : Aarya
         * last_name : Vora
         * username : aaryavora
         * email : aaryavora@gmail.com
         * gender : female
         * dob : 30
         * company :
         * occupation :
         * about_me :
         * interested_in : male
         * zodiac :
         * lanuage_known :
         * city :
         * age_range_from : 18
         * age_range_to : 99
         * fb_id : 10207344382830461
         * mobile : 9819151843
         * mobile_verified : 1
         * mobile_verification_code : 0
         * email_verified : 0
         * email_verification_code :
         * password_change_verification_code :
         * profile_photo : https://scontent.fbom1-2.fna.fbcdn.net/v/t1.0-9/13501835_1262854777088513_6208341510195698306_n.jpg?oh=b93ae480f4f4527611762f0254e01508&oe=57F2330B
         * photo_uploaded : 0
         * active : 1
         * private_account : 0
         * current_lat : 35.12497
         * current_lon : 117.13833
         * radius : 3
         * registration_complete : 3
         * device_token :
         * device_type :
         * device_id :
         * device_os_version :
         * created_at : 2015-09-10 21:58:27
         * updated_at : 2015-09-10 21:58:27
         */

        private int id;
        private String first_name;
        private String last_name;
        private String username;
        private String email;
        private String gender;
        private int dob;
        private String company;
        private String occupation;
        private String about_me;
        private String interested_in;
        private String zodiac;
        private String lanuage_known;
        private String city;
        private int age_range_from;
        private int age_range_to;
        private long fb_id;
        private long mobile;
        private int mobile_verified;
        private int mobile_verification_code;
        private int email_verified;
        private String email_verification_code;
        private String password_change_verification_code;
        private String profile_photo;
        private int photo_uploaded;
        private int active;
        private int private_account;
        private double current_lat;
        private double current_lon;
        private int radius;
        private int registration_complete;
        private String device_token;
        private String device_type;
        private String device_id;
        private String device_os_version;
        private String created_at;
        private String updated_at;

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

        public int getDob() {
            return dob;
        }

        public void setDob(int dob) {
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

        public long getFb_id() {
            return fb_id;
        }

        public void setFb_id(long fb_id) {
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

        public double getCurrent_lat() {
            return current_lat;
        }

        public void setCurrent_lat(double current_lat) {
            this.current_lat = current_lat;
        }

        public double getCurrent_lon() {
            return current_lon;
        }

        public void setCurrent_lon(double current_lon) {
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
    }
}
