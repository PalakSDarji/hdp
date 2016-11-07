package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 07-11-2016.
 */

public class Activities {


    /**
     * success : true
     * activities : [{"id":1,"activity_name":"movie","activity_display_name":"Movie"},{"id":2,"activity_name":"theater","activity_display_name":"Theater"},{"id":3,"activity_name":"event","activity_display_name":"Event"},{"id":4,"activity_name":"night_club","activity_display_name":"Night Club"},{"id":5,"activity_name":"lounge","activity_display_name":"Lounge"},{"id":6,"activity_name":"party","activity_display_name":"Party"},{"id":7,"activity_name":"standup_comedy","activity_display_name":"Stand Up Comedy"},{"id":8,"activity_name":"flight","activity_display_name":"Flight"},{"id":9,"activity_name":"train","activity_display_name":"Train"},{"id":10,"activity_name":"bus","activity_display_name":"Bus"},{"id":11,"activity_name":"car","activity_display_name":"Car"},{"id":12,"activity_name":"coffee","activity_display_name":"Coffee"},{"id":13,"activity_name":"indoor","activity_display_name":"Indoor"},{"id":14,"activity_name":"outdoor","activity_display_name":"Outdoor"},{"id":15,"activity_name":"adventure_sports","activity_display_name":"Adventure Sports"},{"id":20,"activity_name":"hobby","activity_display_name":"Hobby"}]
     */

    private boolean success;
    /**
     * id : 1
     * activity_name : movie
     * activity_display_name : Movie
     */

    private List<ActivitiesBean> activities;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<ActivitiesBean> getActivities() {
        return activities;
    }

    public void setActivities(List<ActivitiesBean> activities) {
        this.activities = activities;
    }

    public static class ActivitiesBean {
        private int id;
        private String activity_name;
        private String activity_display_name;

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
    }
}
