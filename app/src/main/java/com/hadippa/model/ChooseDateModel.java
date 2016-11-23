package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 23-11-2016.
 */

public class ChooseDateModel {


    /**
     * success : true
     * dates : [{"full_date":"2016-11-25","activity_date":"25","activity_month":"Nov","activity_day":"Fri","activity_type":"1"},{"full_date":"2016-11-30","activity_date":"30","activity_month":"Nov","activity_day":"Wed","activity_type":"1"},{"full_date":"2016-11-24","activity_date":"24","activity_month":"Nov","activity_day":"Thu","activity_type":"12"},{"full_date":"2016-11-25","activity_date":"25","activity_month":"Nov","activity_day":"Fri","activity_type":"12"},{"full_date":"2016-11-23","activity_date":"23","activity_month":"Nov","activity_day":"Wed","activity_type":"3"},{"full_date":"2016-11-30","activity_date":"30","activity_month":"Nov","activity_day":"Wed","activity_type":"4"},{"full_date":"2016-11-28","activity_date":"28","activity_month":"Nov","activity_day":"Mon","activity_type":"7"},{"full_date":"2016-11-23","activity_date":"23","activity_month":"Nov","activity_day":"Wed","activity_type":"8"},{"full_date":"2016-11-30","activity_date":"30","activity_month":"Nov","activity_day":"Wed","activity_type":"8"},{"full_date":"2016-11-30","activity_date":"30","activity_month":"Nov","activity_day":"Wed","activity_type":"8"},{"full_date":"2016-12-01","activity_date":"01","activity_month":"Dec","activity_day":"Thu","activity_type":"8"}]
     * next : null
     */

    private boolean success;
    private Object next;
    private List<DatesBean> dates;

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

    public List<DatesBean> getDates() {
        return dates;
    }

    public void setDates(List<DatesBean> dates) {
        this.dates = dates;
    }

    public static class DatesBean {
        /**
         * full_date : 2016-11-25
         * activity_date : 25
         * activity_month : Nov
         * activity_day : Fri
         * activity_type : 1
         */

        private String full_date;
        private String activity_date;
        private String activity_month;
        private String activity_day;
        private String activity_type;

        public String getFull_date() {
            return full_date;
        }

        public void setFull_date(String full_date) {
            this.full_date = full_date;
        }

        public String getActivity_date() {
            return activity_date;
        }

        public void setActivity_date(String activity_date) {
            this.activity_date = activity_date;
        }

        public String getActivity_month() {
            return activity_month;
        }

        public void setActivity_month(String activity_month) {
            this.activity_month = activity_month;
        }

        public String getActivity_day() {
            return activity_day;
        }

        public void setActivity_day(String activity_day) {
            this.activity_day = activity_day;
        }

        public String getActivity_type() {
            return activity_type;
        }

        public void setActivity_type(String activity_type) {
            this.activity_type = activity_type;
        }
    }
}
