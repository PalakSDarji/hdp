package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 01-12-2016.
 */

public class NotificationModel {


    /**
     * success : true
     * notifications : [{"id":356,"sender_id":187,"receiver_id":211,"notification_type":"invite to join activity","notification_details":{"message":"Please to join this activity","activity_name":"Pink","activity_id":164},"viewed":0,"created_at":"2016-11-18 13:03:16","updated_at":"2016-11-18 13:03:16","user":{"id":187,"first_name":"Anand","last_name":"Yadav","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1479983261_Anand.jpg","photo_uploaded":1}},{"id":368,"sender_id":219,"receiver_id":211,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-11-20 01:25:19","updated_at":"2016-11-20 01:25:19","user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1}},{"id":374,"sender_id":219,"receiver_id":211,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-11-21 22:44:23","updated_at":"2016-11-21 22:44:23","user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1}},{"id":390,"sender_id":219,"receiver_id":211,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-11-27 01:40:00","updated_at":"2016-11-27 01:40:00","user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1}},{"id":391,"sender_id":219,"receiver_id":211,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-11-27 01:42:54","updated_at":"2016-11-27 01:42:54","user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1}},{"id":393,"sender_id":219,"receiver_id":211,"notification_type":"follow_start","notification_details":{"message":"started following you"},"viewed":0,"created_at":"2016-11-27 01:53:21","updated_at":"2016-11-27 01:53:21","user":{"id":219,"first_name":"sahil","last_name":"desaiiiii","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/default.jpg","photo_uploaded":1}}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<NotificationsBean> notifications;

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

    public List<NotificationsBean> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationsBean> notifications) {
        this.notifications = notifications;
    }

    public static class NotificationsBean {
        /**
         * id : 356
         * sender_id : 187
         * receiver_id : 211
         * notification_type : invite to join activity
         * notification_details : {"message":"Please to join this activity","activity_name":"Pink","activity_id":164}
         * viewed : 0
         * created_at : 2016-11-18 13:03:16
         * updated_at : 2016-11-18 13:03:16
         * user : {"id":187,"first_name":"Anand","last_name":"Yadav","profile_photo":"http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1479983261_Anand.jpg","photo_uploaded":1}
         */

        private int id;
        private int sender_id;
        private int receiver_id;
        private String notification_type;
        private NotificationDetailsBean notification_details;
        private int viewed;
        private String created_at;
        private String updated_at;
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

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public static class NotificationDetailsBean {
            /**
             * message : Please to join this activity
             * activity_name : Pink
             * activity_id : 164
             */

            private String message;
            private String activity_name;
            private int activity_id;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public String getActivity_name() {
                return activity_name;
            }

            public void setActivity_name(String activity_name) {
                this.activity_name = activity_name;
            }

            public int getActivity_id() {
                return activity_id;
            }

            public void setActivity_id(int activity_id) {
                this.activity_id = activity_id;
            }
        }

        public static class UserBean {
            /**
             * id : 187
             * first_name : Anand
             * last_name : Yadav
             * profile_photo : http://dev.tnxlabs.com/hadipaa/public/assets/images/profiles/user/1479983261_Anand.jpg
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
}
