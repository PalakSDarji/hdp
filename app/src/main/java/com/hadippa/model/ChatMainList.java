package com.hadippa.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 16-12-2016.
 */

public class ChatMainList implements Serializable {


    /**
     * success : true
     * threads : [{"id":3,"activity_id":0,"created_by":211,"chat_type":0,"subject":"raj yadav","created_at":"2016-12-23 23:12:26","updated_at":"2016-12-23 23:12:42","deleted_at":null,"last_msg":"hello","last_message_time":{"date":"2016-12-23 23:12:42.000000","timezone_type":3,"timezone":"Asia/Kolkata"},"profile_photo_thumbnail":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=27640e207673f0f3e567ef1e4ada31a2&oe=58D8E462"}]
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

    public static class ThreadsBean implements Serializable {
        /**
         * id : 3
         * activity_id : 0
         * created_by : 211
         * chat_type : 0
         * subject : raj yadav
         * created_at : 2016-12-23 23:12:26
         * updated_at : 2016-12-23 23:12:42
         * deleted_at : null
         * last_msg : hello
         * last_message_time : {"date":"2016-12-23 23:12:42.000000","timezone_type":3,"timezone":"Asia/Kolkata"}
         * profile_photo_thumbnail : https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/1937043_978044888911311_1642937164755094085_n.jpg?oh=27640e207673f0f3e567ef1e4ada31a2&oe=58D8E462
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
        private String profile_photo_thumbnail;

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

        public String getProfile_photo_thumbnail() {
            return profile_photo_thumbnail;
        }

        public void setProfile_photo_thumbnail(String profile_photo_thumbnail) {
            this.profile_photo_thumbnail = profile_photo_thumbnail;
        }

        public static class LastMessageTimeBean {
            /**
             * date : 2016-12-23 23:12:42.000000
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
    }
}
