package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 01-12-2016.
 */

public class NotificationModel {


    /**
     * success : true
     * notifications : [{"id":867,"sender_id":235,"receiver_id":219,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-12-22 01:04:42","updated_at":"2016-12-22 01:04:42","user_relationship_status":"Connected","user":{"id":235,"first_name":"Vishal","last_name":"Rathod","profile_photo":"1482348204_Vishal.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1482348204_Vishal.jpg"}},{"id":848,"sender_id":215,"receiver_id":219,"notification_type":"activity_request","notification_details":{"message":"wants to join your activity ","activity_name":"Amigos Cafe","activity_id":168},"viewed":1,"created_at":"2016-12-20 23:31:08","updated_at":"2016-12-21 22:48:19","user_relationship_status":"Connected","user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":847,"sender_id":215,"receiver_id":219,"notification_type":"activity_request","notification_details":{"message":"wants to join your activity ","activity_name":"Cafe Bistro","activity_id":167},"viewed":1,"created_at":"2016-12-20 23:31:07","updated_at":"2016-12-21 22:53:03","user_relationship_status":"Connected","user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":846,"sender_id":215,"receiver_id":219,"notification_type":"activity_request","notification_details":{"message":"wants to join your activity ","activity_name":"The Dollar Business Grow Workshop Vadodara Edition 2016-17","activity_id":166},"viewed":1,"created_at":"2016-12-20 23:31:05","updated_at":"2016-12-21 22:53:22","user_relationship_status":"Connected","user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":840,"sender_id":215,"receiver_id":219,"notification_type":"activity_request","notification_details":{"message":"wants to join your activity ","activity_name":"Cafe Bistro","activity_id":165},"viewed":0,"created_at":"2016-12-20 23:11:53","updated_at":"2016-12-20 23:11:53","user_relationship_status":"Connected","user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":839,"sender_id":215,"receiver_id":219,"notification_type":"activity_request","notification_details":{"message":"wants to join your activity ","activity_name":"OyePlay All India Corporate Tennis Tournament","activity_id":138},"viewed":0,"created_at":"2016-12-20 23:11:52","updated_at":"2016-12-20 23:11:52","user_relationship_status":"Connected","user":{"id":215,"first_name":"postman","last_name":"test","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":752,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"ggnbvv","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:18:07","updated_at":"2016-12-19 21:18:07","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":751,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hjdnbdjdbe","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:11:10","updated_at":"2016-12-19 21:11:10","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":748,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"udydhebdjdjwbehdudn","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:10:26","updated_at":"2016-12-19 21:10:26","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":747,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hi","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:10:11","updated_at":"2016-12-19 21:10:11","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":745,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:09:45","updated_at":"2016-12-19 21:09:45","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":744,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:09:35","updated_at":"2016-12-19 21:09:35","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":743,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"vbbbnbvvv","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:08:58","updated_at":"2016-12-19 21:08:58","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":742,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:01:36","updated_at":"2016-12-19 21:01:36","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":741,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:01:30","updated_at":"2016-12-19 21:01:30","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":738,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"yo bro","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:01:01","updated_at":"2016-12-19 21:01:01","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":737,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hiii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 21:00:47","updated_at":"2016-12-19 21:00:47","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":736,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"hii","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 20:56:23","updated_at":"2016-12-19 20:56:23","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":733,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"ggggg","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 20:45:38","updated_at":"2016-12-19 20:45:38","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}},{"id":732,"sender_id":226,"receiver_id":219,"notification_type":"New Message","notification_details":{"message":"ggghjkgh","thread_id":9,"msg_type":"1"},"viewed":0,"created_at":"2016-12-19 20:45:24","updated_at":"2016-12-19 20:45:24","user_relationship_status":"Following","user":{"id":226,"first_name":"aliakbar","last_name":"p","profile_photo":"default.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg"}}]
     * next : http://hadipaa.dev.tnxlabs.com/api/android/v1/notifications?page=2
     */

    private boolean success;
    private String next;
    private List<NotificationsBean> notifications;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<NotificationsBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsBean> notifications) {
        this.notifications = notifications;
    }

    public static class NotificationsBean {
        /**
         * id : 867
         * sender_id : 235
         * receiver_id : 219
         * notification_type : follow_start
         * notification_details : {"message":"started following you"}
         * viewed : 0
         * created_at : 2016-12-22 01:04:42
         * updated_at : 2016-12-22 01:04:42
         * user_relationship_status : Connected
         * user : {"id":235,"first_name":"Vishal","last_name":"Rathod","profile_photo":"1482348204_Vishal.jpg","photo_uploaded":1,"profile_photo_url":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1482348204_Vishal.jpg"}
         */

        private int id;
        private int sender_id;
        private int receiver_id;
        private String notification_type;
        private NotificationDetailsBean notification_details;
        private int viewed;
        private String created_at;
        private String updated_at;
        private String user_relationship_status;
        private UserBean user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getSender_id() {
            return sender_id;
        }

        public void setSender_id(int sender_id) {
            this.sender_id = sender_id;
        }

        public int getReceiver_id() {
            return receiver_id;
        }

        public void setReceiver_id(int receiver_id) {
            this.receiver_id = receiver_id;
        }

        public String getNotification_type() {
            return notification_type;
        }

        public void setNotification_type(String notification_type) {
            this.notification_type = notification_type;
        }

        public NotificationDetailsBean getNotification_details() {
            return notification_details;
        }

        public void setNotification_details(NotificationDetailsBean notification_details) {
            this.notification_details = notification_details;
        }

        public int getViewed() {
            return viewed;
        }

        public void setViewed(int viewed) {
            this.viewed = viewed;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class NotificationDetailsBean {
            /**
             * message : started following you
             *  activity_name: Amigos Cafe,
             activity_id: 168
             */

            private int activity_id;
            private String message;
            private String activity_name;

            public int getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(int activity_id) {
                this.activity_id = activity_id;
            }

            public String getActivity_name() {
                return activity_name;
            }

            public void setActivity_name(String activity_name) {
                this.activity_name = activity_name;
            }

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }
        }

        public static class UserBean {
            /**
             * id : 235
             * first_name : Vishal
             * last_name : Rathod
             * profile_photo : 1482348204_Vishal.jpg
             * photo_uploaded : 1
             * profile_photo_url : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1482348204_Vishal.jpg
             */

            private int id;
            private String first_name;
            private String last_name;
            private String profile_photo;
            private int photo_uploaded;
            private String profile_photo_url;

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

            public String getProfile_photo_url() {
                return profile_photo_url;
            }

            public void setProfile_photo_url(String profile_photo_url) {
                this.profile_photo_url = profile_photo_url;
            }
        }
    }
}
