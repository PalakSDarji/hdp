package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 17-12-2016.
 */

public class MessageModel {


    /**
     * success : true
     * thread : {"id":4,"activity_id":"0","subject":"Kartik Mistry","created_at":"2016-12-17 01:24:46","updated_at":"2016-12-17 01:24:46","deleted_at":null,"messages":[{"id":4,"thread_id":4,"body":"hiiiii","user_id":211,"created_at":"2016-12-17 01:24:46","updated_at":"2016-12-17 01:24:46","user":{"id":211,"first_name":"Sahil","last_name":"Desai","username":"sahil.desai28392@gmail.com","email":"sahil.desai28392@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1051934714855661,"mobile":7878345762,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-07 19:58:07","updated_at":"2016-11-07 19:59:59"}},{"id":5,"thread_id":4,"body":"hiiiii how are you ?","user_id":212,"created_at":"2016-12-17 01:24:46","updated_at":"2016-12-17 01:24:46","user":{"id":212,"first_name":"Kartik","last_name":"Mistry","username":"kartikk55@gmail.com","email":"kartikk55@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1290932840947373,"mobile":0,"mobile_verified":0,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":1,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-09 10:11:10","updated_at":"2016-11-09 10:11:10"}}],"participants":[{"id":211,"first_name":"Sahil","last_name":"Desai","username":"sahil.desai28392@gmail.com","email":"sahil.desai28392@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1051934714855661,"mobile":7878345762,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-07 19:58:07","updated_at":"2016-11-07 19:59:59"},{"id":212,"first_name":"Kartik","last_name":"Mistry","username":"kartikk55@gmail.com","email":"kartikk55@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1290932840947373,"mobile":0,"mobile_verified":0,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":1,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-09 10:11:10","updated_at":"2016-11-09 10:11:10"}]}
     */

    private boolean success;
    private ThreadBean thread;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ThreadBean getThread() {
        return thread;
    }

    public void setThread(ThreadBean thread) {
        this.thread = thread;
    }

    public static class ThreadBean {
        /**
         * id : 4
         * activity_id : 0
         * subject : Kartik Mistry
         * created_at : 2016-12-17 01:24:46
         * updated_at : 2016-12-17 01:24:46
         * deleted_at : null
         * messages : [{"id":4,"thread_id":4,"body":"hiiiii","user_id":211,"created_at":"2016-12-17 01:24:46","updated_at":"2016-12-17 01:24:46","user":{"id":211,"first_name":"Sahil","last_name":"Desai","username":"sahil.desai28392@gmail.com","email":"sahil.desai28392@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1051934714855661,"mobile":7878345762,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-07 19:58:07","updated_at":"2016-11-07 19:59:59"}},{"id":5,"thread_id":4,"body":"hiiiii how are you ?","user_id":212,"created_at":"2016-12-17 01:24:46","updated_at":"2016-12-17 01:24:46","user":{"id":212,"first_name":"Kartik","last_name":"Mistry","username":"kartikk55@gmail.com","email":"kartikk55@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1290932840947373,"mobile":0,"mobile_verified":0,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":1,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-09 10:11:10","updated_at":"2016-11-09 10:11:10"}}]
         * participants : [{"id":211,"first_name":"Sahil","last_name":"Desai","username":"sahil.desai28392@gmail.com","email":"sahil.desai28392@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1051934714855661,"mobile":7878345762,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-07 19:58:07","updated_at":"2016-11-07 19:59:59"},{"id":212,"first_name":"Kartik","last_name":"Mistry","username":"kartikk55@gmail.com","email":"kartikk55@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1290932840947373,"mobile":0,"mobile_verified":0,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":1,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-09 10:11:10","updated_at":"2016-11-09 10:11:10"}]
         */

        private int id;
        private String activity_id;
        private String subject;
        private String created_at;
        private String updated_at;
        private Object deleted_at;
        private List<MessagesBean> messages;
        private List<ParticipantsBean> participants;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getActivity_id() {
            return activity_id;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
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

        public List<MessagesBean> getMessages() {
            return messages;
        }

        public void setMessages(List<MessagesBean> messages) {
            this.messages = messages;
        }

        public List<ParticipantsBean> getParticipants() {
            return participants;
        }

        public void setParticipants(List<ParticipantsBean> participants) {
            this.participants = participants;
        }

        public static class MessagesBean {
            /**
             * id : 4
             * thread_id : 4
             * body : hiiiii
             * user_id : 211
             * created_at : 2016-12-17 01:24:46
             * updated_at : 2016-12-17 01:24:46
             * user : {"id":211,"first_name":"Sahil","last_name":"Desai","username":"sahil.desai28392@gmail.com","email":"sahil.desai28392@gmail.com","gender":"male","dob":null,"company":"","occupation":"","about_me":"","interested_in":"both","zodiac":"","lanuage_known":"","city":"Vadodara","age_range_from":18,"age_range_to":99,"fb_id":1051934714855661,"mobile":7878345762,"mobile_verified":1,"mobile_verification_code":0,"email_verified":0,"email_verification_code":"","password_change_verification_code":"","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762","profile_photo_1":"","profile_photo_2":"","profile_photo_3":"","profile_photo_4":"","profile_photo_5":"","photo_uploaded":0,"active":1,"private_account":0,"current_lat":0,"current_lon":0,"radius":3,"registration_complete":3,"device_token":"","device_type":"","device_id":"","device_os_version":"","created_at":"2016-11-07 19:58:07","updated_at":"2016-11-07 19:59:59"}
             */

            private int id;
            private int thread_id;
            private String body;
            private int user_id;
            private String created_at;
            private String updated_at;
            private UserBean user;
            private String message_type;

            public String getMessage_type() {
                return message_type;
            }

            public void setMessage_type(String message_type) {
                this.message_type = message_type;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getThread_id() {
                return thread_id;
            }

            public void setThread_id(int thread_id) {
                this.thread_id = thread_id;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
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

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public static class UserBean {
                /**
                 * id : 211
                 * first_name : Sahil
                 * last_name : Desai
                 * username : sahil.desai28392@gmail.com
                 * email : sahil.desai28392@gmail.com
                 * gender : male
                 * dob : null
                 * company :
                 * occupation :
                 * about_me :
                 * interested_in : both
                 * zodiac :
                 * lanuage_known :
                 * city : Vadodara
                 * age_range_from : 18
                 * age_range_to : 99
                 * fb_id : 1051934714855661
                 * mobile : 7878345762
                 * mobile_verified : 1
                 * mobile_verification_code : 0
                 * email_verified : 0
                 * email_verification_code :
                 * password_change_verification_code :
                 * profile_photo : https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762
                 * profile_photo_1 :
                 * profile_photo_2 :
                 * profile_photo_3 :
                 * profile_photo_4 :
                 * profile_photo_5 :
                 * photo_uploaded : 0
                 * active : 1
                 * private_account : 0
                 * current_lat : 0
                 * current_lon : 0
                 * radius : 3
                 * registration_complete : 3
                 * device_token :
                 * device_type :
                 * device_id :
                 * device_os_version :
                 * created_at : 2016-11-07 19:58:07
                 * updated_at : 2016-11-07 19:59:59
                 */

                private int id;
                private String first_name;
                private String last_name;
                private String username;
                private String email;
                private String gender;
                private Object dob;
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
                private String profile_photo_1;
                private String profile_photo_2;
                private String profile_photo_3;
                private String profile_photo_4;
                private String profile_photo_5;
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

                public Object getDob() {
                    return dob;
                }

                public void setDob(Object dob) {
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

                public String getProfile_photo_4() {
                    return profile_photo_4;
                }

                public void setProfile_photo_4(String profile_photo_4) {
                    this.profile_photo_4 = profile_photo_4;
                }

                public String getProfile_photo_5() {
                    return profile_photo_5;
                }

                public void setProfile_photo_5(String profile_photo_5) {
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
            }
        }

        public static class ParticipantsBean {
            /**
             * id : 211
             * first_name : Sahil
             * last_name : Desai
             * username : sahil.desai28392@gmail.com
             * email : sahil.desai28392@gmail.com
             * gender : male
             * dob : null
             * company :
             * occupation :
             * about_me :
             * interested_in : both
             * zodiac :
             * lanuage_known :
             * city : Vadodara
             * age_range_from : 18
             * age_range_to : 99
             * fb_id : 1051934714855661
             * mobile : 7878345762
             * mobile_verified : 1
             * mobile_verification_code : 0
             * email_verified : 0
             * email_verification_code :
             * password_change_verification_code :
             * profile_photo : https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=c222bd311efa77a7d6e9f3864b387f73&oe=58B15762
             * profile_photo_1 :
             * profile_photo_2 :
             * profile_photo_3 :
             * profile_photo_4 :
             * profile_photo_5 :
             * photo_uploaded : 0
             * active : 1
             * private_account : 0
             * current_lat : 0
             * current_lon : 0
             * radius : 3
             * registration_complete : 3
             * device_token :
             * device_type :
             * device_id :
             * device_os_version :
             * created_at : 2016-11-07 19:58:07
             * updated_at : 2016-11-07 19:59:59
             */

            private int id;
            private String first_name;
            private String last_name;
            private String username;
            private String email;
            private String gender;
            private Object dob;
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
            private String profile_photo_1;
            private String profile_photo_2;
            private String profile_photo_3;
            private String profile_photo_4;
            private String profile_photo_5;
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

            public Object getDob() {
                return dob;
            }

            public void setDob(Object dob) {
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

            public String getProfile_photo_4() {
                return profile_photo_4;
            }

            public void setProfile_photo_4(String profile_photo_4) {
                this.profile_photo_4 = profile_photo_4;
            }

            public String getProfile_photo_5() {
                return profile_photo_5;
            }

            public void setProfile_photo_5(String profile_photo_5) {
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
        }
    }
}
