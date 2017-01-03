package com.hadippa.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 16-12-2016.
 */

public class ChatMainList implements Serializable {


    /**
     * success : true
     * threads : [{"id":21,"activity_id":260,"created_by":226,"chat_type":2,"subject":"Tea Post","created_at":"2017-01-02 00:20:09","updated_at":"2017-01-02 00:20:09","deleted_at":null,"last_msg":"Messaging Start..!","last_message_time":{"date":"2017-01-02 00:20:09.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"participant":[{"id":42,"user_id":226,"thread_id":21,"created_at":"2017-01-02 00:20:09","updated_at":"2017-01-02 00:20:09","deleted_at":null,"last_read":"2017-01-02 00:20:09","user":{"id":226,"first_name":"aliakbar","last_name":"p","username":"ap@gmail.com","email":"ap@gmail.com","gender":"male","dob":"1995-12-09","company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345761,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"default.jpg","profile_photo_1":null,"profile_photo_2":null,"profile_photo_3":null,"profile_photo_4":null,"profile_photo_5":null,"photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-12-09 20:59:49","updated_at":"2017-01-02 00:19:55","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}},{"id":43,"user_id":219,"thread_id":21,"created_at":"2017-01-02 00:20:09","updated_at":"2017-01-02 00:20:09","deleted_at":null,"last_read":null,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","username":"sahil123@gmail.com","email":"sahil123@gmail.com","gender":"male","dob":"1995-11-15","company":"Baroda Coders","occupation":"App Developer","about_me":"","interested_in":"female","zodiac":"Taurus","lanuage_known":"English Hindi Gujarati Arabic Urdu","city":"Surat","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345721,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"1481390529_sahil.jpg","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-11-12 21:39:29","updated_at":"2017-01-02 00:19:19","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/1481390529_sahil.jpg"}}],"activity_type":"12"},{"id":20,"activity_id":259,"created_by":226,"chat_type":2,"subject":"Tea Post","created_at":"2017-01-02 00:20:07","updated_at":"2017-01-02 00:20:07","deleted_at":null,"last_msg":"Messaging Start..!","last_message_time":{"date":"2017-01-02 00:20:07.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"participant":[{"id":40,"user_id":226,"thread_id":20,"created_at":"2017-01-02 00:20:07","updated_at":"2017-01-02 00:20:07","deleted_at":null,"last_read":"2017-01-02 00:20:07","user":{"id":226,"first_name":"aliakbar","last_name":"p","username":"ap@gmail.com","email":"ap@gmail.com","gender":"male","dob":"1995-12-09","company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345761,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"default.jpg","profile_photo_1":null,"profile_photo_2":null,"profile_photo_3":null,"profile_photo_4":null,"profile_photo_5":null,"photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-12-09 20:59:49","updated_at":"2017-01-02 00:19:55","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}},{"id":41,"user_id":219,"thread_id":20,"created_at":"2017-01-02 00:20:07","updated_at":"2017-01-02 00:20:07","deleted_at":null,"last_read":null,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","username":"sahil123@gmail.com","email":"sahil123@gmail.com","gender":"male","dob":"1995-11-15","company":"Baroda Coders","occupation":"App Developer","about_me":"","interested_in":"female","zodiac":"Taurus","lanuage_known":"English Hindi Gujarati Arabic Urdu","city":"Surat","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345721,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"1481390529_sahil.jpg","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-11-12 21:39:29","updated_at":"2017-01-02 00:19:19","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/1481390529_sahil.jpg"}}],"activity_type":"12"},{"id":17,"activity_id":0,"created_by":219,"chat_type":1,"subject":"aliakbar p","created_at":"2016-12-31 23:35:54","updated_at":"2017-01-01 00:35:31","deleted_at":null,"last_msg":"ggfbjgfv","last_message_time":{"date":"2017-01-01 00:35:31.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<ThreadsBean> threads;

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

    public List<ThreadsBean> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadsBean> threads) {
        this.threads = threads;
    }

    public static class ThreadsBean implements Serializable{
        /**
         * id : 21
         * activity_id : 260
         * created_by : 226
         * chat_type : 2
         * subject : Tea Post
         * created_at : 2017-01-02 00:20:09
         * updated_at : 2017-01-02 00:20:09
         * deleted_at : null
         * last_msg : Messaging Start..!
         * last_message_time : {"date":"2017-01-02 00:20:09.000000","timezone_type":3,"timezone":"Asia/Kolkata"}
         * participant : [{"id":42,"user_id":226,"thread_id":21,"created_at":"2017-01-02 00:20:09","updated_at":"2017-01-02 00:20:09","deleted_at":null,"last_read":"2017-01-02 00:20:09","user":{"id":226,"first_name":"aliakbar","last_name":"p","username":"ap@gmail.com","email":"ap@gmail.com","gender":"male","dob":"1995-12-09","company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345761,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"default.jpg","profile_photo_1":null,"profile_photo_2":null,"profile_photo_3":null,"profile_photo_4":null,"profile_photo_5":null,"photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-12-09 20:59:49","updated_at":"2017-01-02 00:19:55","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}},{"id":43,"user_id":219,"thread_id":21,"created_at":"2017-01-02 00:20:09","updated_at":"2017-01-02 00:20:09","deleted_at":null,"last_read":null,"user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","username":"sahil123@gmail.com","email":"sahil123@gmail.com","gender":"male","dob":"1995-11-15","company":"Baroda Coders","occupation":"App Developer","about_me":"","interested_in":"female","zodiac":"Taurus","lanuage_known":"English Hindi Gujarati Arabic Urdu","city":"Surat","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345721,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"1481390529_sahil.jpg","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-11-12 21:39:29","updated_at":"2017-01-02 00:19:19","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/1481390529_sahil.jpg"}}]
         * activity_type : 12
         * profile_photo_thumbnail : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg
         */

        private int id;
        private int activity_id;
        private int created_by;
        private int chat_type;
        private String subject;
        private String created_at;
        private String updated_at;
        private Object deleted_at;
        private String last_msg;
        private LastMessageTimeBean last_message_time;
        private String activity_type;
        private String profile_photo_thumbnail;
        private List<ParticipantBean> participant;

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

        public int getCreated_by() {
            return created_by;
        }

        public void setCreated_by(int created_by) {
            this.created_by = created_by;
        }

        public int getChat_type() {
            return chat_type;
        }

        public void setChat_type(int chat_type) {
            this.chat_type = chat_type;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
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

        public Object getDeleted_at() {
            return deleted_at;
        }

        public void setDeleted_at(Object deleted_at) {
            this.deleted_at = deleted_at;
        }

        public String getLast_msg() {
            return last_msg;
        }

        public void setLast_msg(String last_msg) {
            this.last_msg = last_msg;
        }

        public LastMessageTimeBean getLast_message_time() {
            return last_message_time;
        }

        public void setLast_message_time(LastMessageTimeBean last_message_time) {
            this.last_message_time = last_message_time;
        }

        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }

        public String getProfile_photo_thumbnail() {
            return profile_photo_thumbnail;
        }

        public void setProfile_photo_thumbnail(String profile_photo_thumbnail) {
            this.profile_photo_thumbnail = profile_photo_thumbnail;
        }

        public List<ParticipantBean> getParticipant() {
            return participant;
        }

        public void setParticipant(List<ParticipantBean> participant) {
            this.participant = participant;
        }

        public static class LastMessageTimeBean implements Serializable {
            /**
             * date : 2017-01-02 00:20:09.000000
             * timezone_type : 3
             * timezone : Asia/Kolkata
             */

            private String date;
            private int timezone_type;
            private String timezone;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getTimezone_type() {
                return timezone_type;
            }

            public void setTimezone_type(int timezone_type) {
                this.timezone_type = timezone_type;
            }

            public String getTimezone() {
                return timezone;
            }

            public void setTimezone(String timezone) {
                this.timezone = timezone;
            }
        }

        public static class ParticipantBean implements Serializable{
            /**
             * id : 42
             * user_id : 226
             * thread_id : 21
             * created_at : 2017-01-02 00:20:09
             * updated_at : 2017-01-02 00:20:09
             * deleted_at : null
             * last_read : 2017-01-02 00:20:09
             * user : {"id":226,"first_name":"aliakbar","last_name":"p","username":"ap@gmail.com","email":"ap@gmail.com","gender":"male","dob":"1995-12-09","company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"","age_range_from":18,"age_range_to":99,"fb_id":0,"mobile":7878345761,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"default.jpg","profile_photo_1":null,"profile_photo_2":null,"profile_photo_3":null,"profile_photo_4":null,"profile_photo_5":null,"photo_uploaded":1,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":100,"registration_complete":3,"device_token":"dpBT2mlKwfE:APA91bGSH5SrLtcj2FuwcgWTeje8NJWkKUGbVI8A1SyfFGnklNijcdlv0pnWb0kf6SW3G_39A5HyJJnVWfxPX2aquDqvQi_5zj9PJsg94WLuWWVhpltFRhLm0i9mjZooKvt3LxZbFzUx","device_type":"android","device_id":"88baca3a5682275d","device_os_version":"23","created_at":"2016-12-09 20:59:49","updated_at":"2017-01-02 00:19:55","profile_photo_thumbnail":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg"}
             */

            private int id;
            private int user_id;
            private int thread_id;
            private String created_at;
            private String updated_at;
            private Object deleted_at;
            private String last_read;
            private UserBean user;

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

            public int getThread_id() {
                return thread_id;
            }

            public void setThread_id(int thread_id) {
                this.thread_id = thread_id;
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

            public Object getDeleted_at() {
                return deleted_at;
            }

            public void setDeleted_at(Object deleted_at) {
                this.deleted_at = deleted_at;
            }

            public String getLast_read() {
                return last_read;
            }

            public void setLast_read(String last_read) {
                this.last_read = last_read;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean implements Serializable {
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
                 * profile_photo_1 : null
                 * profile_photo_2 : null
                 * profile_photo_3 : null
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
                 * updated_at : 2017-01-02 00:19:55
                 * profile_photo_thumbnail : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/thumbnails/default.jpg
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
                private Object profile_photo_1;
                private Object profile_photo_2;
                private Object profile_photo_3;
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

                public Object getProfile_photo_1() {
                    return profile_photo_1;
                }

                public void setProfile_photo_1(Object profile_photo_1) {
                    this.profile_photo_1 = profile_photo_1;
                }

                public Object getProfile_photo_2() {
                    return profile_photo_2;
                }

                public void setProfile_photo_2(Object profile_photo_2) {
                    this.profile_photo_2 = profile_photo_2;
                }

                public Object getProfile_photo_3() {
                    return profile_photo_3;
                }

                public void setProfile_photo_3(Object profile_photo_3) {
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

                public String getProfile_photo_thumbnail() {
                    return profile_photo_thumbnail;
                }

                public void setProfile_photo_thumbnail(String profile_photo_thumbnail) {
                    this.profile_photo_thumbnail = profile_photo_thumbnail;
                }
            }
        }
    }
}
