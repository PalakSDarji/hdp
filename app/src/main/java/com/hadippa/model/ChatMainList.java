package com.hadippa.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by HP on 16-12-2016.
 */

public class ChatMainList {


    /**
     * success : true
     * threads : [{"id":1,"subject":"TESTING","created_at":"2016-11-17 15:54:51","updated_at":"2016-11-17 18:39:26","deleted_at":null}]
     */

    @SerializedName("success")
    private boolean success;

    @SerializedName("threads")
    private List<ThreadsBean> threads;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ThreadsBean> getThreads() {
        return threads;
    }

    public void setThreads(List<ThreadsBean> threads) {
        this.threads = threads;
    }

    public static class ThreadsBean implements Serializable {
        /**
         * id : 1
         * subject : TESTING
         * created_at : 2016-11-17 15:54:51
         * updated_at : 2016-11-17 18:39:26
         * deleted_at : null
         */

        private int id;
        private String subject;
        private String created_at;
        private String updated_at;
        private Object deleted_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }
}
