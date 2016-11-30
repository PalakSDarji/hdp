package com.hadippa.model;

import java.util.List;

/**
 * Created by HP on 30-11-2016.
 */

public class SearchModel {


    /**
     * success : true
     * users : [{"id":212,"first_name":"Kartik","last_name":"Mistry","profile_photo":"https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791","mutual_followers_count":2,"following":[{"id":360,"follower_id":219,"followed_id":212}]},{"id":213,"first_name":"kartik","last_name":"mistry","profile_photo":"default.jpg","mutual_followers_count":3,"following":[{"id":351,"follower_id":219,"followed_id":213}]},{"id":214,"first_name":"Kartik","last_name":"Mistry","profile_photo":"default.jpg","mutual_followers_count":3,"following":[]}]
     * tags : []
     * cities : {"location_suggestions":[{"id":32,"name":"Vadodara","country_id":1,"country_name":"India","discovery_enabled":0,"has_new_ad_format":1,"is_state":0,"state_id":0,"state_name":"","state_code":""}],"status":"success","has_more":0,"has_total":0}
     */

    private boolean success;
    private CitiesBean cities;
    private List<UsersBean> users;
    private List<?> tags;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CitiesBean getCities() {
        return cities;
    }

    public void setCities(CitiesBean cities) {
        this.cities = cities;
    }

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public List<?> getTags() {
        return tags;
    }

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public static class CitiesBean {
        /**
         * location_suggestions : [{"id":32,"name":"Vadodara","country_id":1,"country_name":"India","discovery_enabled":0,"has_new_ad_format":1,"is_state":0,"state_id":0,"state_name":"","state_code":""}]
         * status : success
         * has_more : 0
         * has_total : 0
         */

        private String status;
        private int has_more;
        private int has_total;
        private List<LocationSuggestionsBean> location_suggestions;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getHas_more() {
            return has_more;
        }

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }

        public int getHas_total() {
            return has_total;
        }

        public void setHas_total(int has_total) {
            this.has_total = has_total;
        }

        public List<LocationSuggestionsBean> getLocation_suggestions() {
            return location_suggestions;
        }

        public void setLocation_suggestions(List<LocationSuggestionsBean> location_suggestions) {
            this.location_suggestions = location_suggestions;
        }

        public static class LocationSuggestionsBean {
            /**
             * id : 32
             * name : Vadodara
             * country_id : 1
             * country_name : India
             * discovery_enabled : 0
             * has_new_ad_format : 1
             * is_state : 0
             * state_id : 0
             * state_name :
             * state_code :
             */

            private int id;
            private String name;
            private int country_id;
            private String country_name;
            private int discovery_enabled;
            private int has_new_ad_format;
            private int is_state;
            private int state_id;
            private String state_name;
            private String state_code;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getCountry_id() {
                return country_id;
            }

            public void setCountry_id(int country_id) {
                this.country_id = country_id;
            }

            public String getCountry_name() {
                return country_name;
            }

            public void setCountry_name(String country_name) {
                this.country_name = country_name;
            }

            public int getDiscovery_enabled() {
                return discovery_enabled;
            }

            public void setDiscovery_enabled(int discovery_enabled) {
                this.discovery_enabled = discovery_enabled;
            }

            public int getHas_new_ad_format() {
                return has_new_ad_format;
            }

            public void setHas_new_ad_format(int has_new_ad_format) {
                this.has_new_ad_format = has_new_ad_format;
            }

            public int getIs_state() {
                return is_state;
            }

            public void setIs_state(int is_state) {
                this.is_state = is_state;
            }

            public int getState_id() {
                return state_id;
            }

            public void setState_id(int state_id) {
                this.state_id = state_id;
            }

            public String getState_name() {
                return state_name;
            }

            public void setState_name(String state_name) {
                this.state_name = state_name;
            }

            public String getState_code() {
                return state_code;
            }

            public void setState_code(String state_code) {
                this.state_code = state_code;
            }
        }
    }

    public static class UsersBean {
        /**
         * id : 212
         * first_name : Kartik
         * last_name : Mistry
         * profile_photo : https://scontent.xx.fbcdn.net/v/t1.0-1/p480x480/15055759_1406021772771812_883152813864050013_n.jpg?oh=d6119d16a82588ce6f009a64fd868a01&oe=58C03791
         * mutual_followers_count : 2
         * following : [{"id":360,"follower_id":219,"followed_id":212}]
         */

        private int id;
        private String first_name;
        private String last_name;
        private String profile_photo;
        private int mutual_followers_count;
        private List<FollowingBean> following;

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

        public int getMutual_followers_count() {
            return mutual_followers_count;
        }

        public void setMutual_followers_count(int mutual_followers_count) {
            this.mutual_followers_count = mutual_followers_count;
        }

        public List<FollowingBean> getFollowing() {
            return following;
        }

        public void setFollowing(List<FollowingBean> following) {
            this.following = following;
        }

        public static class FollowingBean {
            /**
             * id : 360
             * follower_id : 219
             * followed_id : 212
             */

            private int id;
            private int follower_id;
            private int followed_id;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFollower_id() {
                return follower_id;
            }

            public void setFollower_id(int follower_id) {
                this.follower_id = follower_id;
            }

            public int getFollowed_id() {
                return followed_id;
            }

            public void setFollowed_id(int followed_id) {
                this.followed_id = followed_id;
            }
        }
    }
}
